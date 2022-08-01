package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.CoffeeUser
import br.com.alura.ceep.ui.coffemachine.exceptions.BadGatewayException
import br.com.alura.ceep.ui.coffemachine.exceptions.BadRequestException
import br.com.alura.ceep.ui.coffemachine.exceptions.NoContentException
import br.com.alura.ceep.ui.coffemachine.exceptions.NotFoundException
import br.com.alura.ceep.ui.coffemachine.helpers.AnalyticsHelper
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffeesViewModel
import br.com.alura.ceep.ui.coffemachine.viewmodel.config.CoffesViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class AddActivity : AppCompatActivity() {

  private val viewModel: CoffeesViewModel by viewModels {
    CoffesViewModelFactory(
      CoffeesRepository(
        RetrofitConfig().getClient(this)
      ),
      FirebaseAuth.getInstance(),
      SharedPref(this)
    )
  }

  private lateinit var name: TextView
  private lateinit var description: TextView
  private lateinit var intensity: TextView
  private lateinit var size: TextView
  private lateinit var capsules: TextView
  private lateinit var image: ImageView
  private lateinit var quantityText: TextView
  private lateinit var intensityText: TextView
  private lateinit var availableCapsules: TextView
  private lateinit var coffeToolbar: Toolbar
  private lateinit var progressBar: ProgressBar
  private lateinit var progressBarSaved: ProgressBar
  private lateinit var putCapsules: TextInputEditText
  private var mLastClickTime: Long = 0
  private lateinit var save_btn: Button
  private var uid: String? = null
  private var coffeeCaps: String? = null
  private val analyticsHelper: AnalyticsHelper by lazy {
    AnalyticsHelper(this)
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.add_coffee_activity)
    setup()
    layoutGone()
    listeners()
    observers()
    progressBar.visibility = View.VISIBLE
    val token = SharedPref(this).getString(SharedPref.TOKEN)
    token?.let {
      intent.extras?.getString(UID)?.let { uid ->
        viewModel.searchByUid(uid)
      }
      intent.extras?.getString(CAPSULES)?.let { caps ->
        coffeeCaps = caps
      }
    }
    analyticsHelper.log(AnalyticsHelper.ADD_OPENED)
  }

  private fun setup() {
    name = findViewById(R.id.name_coffee)
    description = findViewById(R.id.description)
    intensity = findViewById(R.id.intensity)
    size = findViewById(R.id.size)
    image = findViewById(R.id.image)
    capsules = findViewById(R.id.capsules)
    intensityText = findViewById(R.id.Intensity)
    quantityText = findViewById(R.id.quantity)
    availableCapsules = findViewById(R.id.available_capsules)
    coffeToolbar = findViewById(R.id.coffe_toolbar)
    progressBar = findViewById(R.id.progress_bar_detail_activity)
    progressBarSaved = findViewById(R.id.progress_bar_add_activity)
    putCapsules = findViewById(R.id.put_capsules_now)
    save_btn = findViewById(R.id.save_btn)
    setSupportActionBar(coffeToolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
  }

  private fun listeners() {
    coffeToolbar.setNavigationOnClickListener { onBackPressed() }
    save_btn.setOnClickListener {
      analyticsHelper.log(AnalyticsHelper.ADD_CLICKED)
      progressBarSaved.visibility = View.VISIBLE
      progressBar.visibility = View.VISIBLE
      if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
        return@setOnClickListener
      }
      val capsules = putCapsules.text.toString()
      mLastClickTime = SystemClock.elapsedRealtime();
      if (capsules.isNotEmpty()) {
        val caps = capsules.toInt()
        val coffeeUser = CoffeeUser(
          capsules = caps,
          uid = uid.toString()
        )
        uid?.let { uidNotNull ->
          coffeeUser.uid = uidNotNull
        }
        viewModel.save(coffeeUser)
      } else {
        putCapsules.error = getString(R.string.invalid_capsules)
        putCapsules.requestFocus()
        progressBarSaved.visibility = View.GONE
      }
      progressBar.visibility = View.GONE
    }
  }

  private fun observers() {
    viewModel.coffeeById.observe(this) { coffee ->
      uid = coffee.uid
      name.text = coffee.name
      description.text = coffee.description
      size.text = coffee.quantity + ml
      capsules.text = coffeeCaps
      intensity.text = coffee.intensity
      Picasso.get().load(coffee.image)
        .into(image)
      layoutStart()
      progressBar.visibility = View.GONE
    }

    viewModel.errorById.observe(this) { exception ->
      analyticsHelper.log(AnalyticsHelper.ADD_ERROR)
      when (exception) {
        is NoContentException -> Toast.makeText(
          this,
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
        is BadRequestException -> Toast.makeText(
          this,
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
        is NotFoundException -> Toast.makeText(
          this,
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
        is BadGatewayException -> Toast.makeText(
          this,
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
      }
    }

    viewModel.added.observe(this) { saved ->
      analyticsHelper.log(AnalyticsHelper.ADD_ADDED)
      if (saved) {
        Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DashboardActivity::class.java)
        this.startActivity(intent)
        progressBar.visibility = View.GONE
      }
    }
  }

  fun layoutGone() {
    progressBar.visibility = View.VISIBLE
    coffeToolbar.visibility = View.VISIBLE
    name.visibility = View.GONE
    description.visibility = View.GONE
    intensity.visibility = View.GONE
    size.visibility = View.GONE
    image.visibility = View.GONE
    capsules.visibility = View.GONE
    intensityText.visibility = View.GONE
    quantityText.visibility = View.GONE
    availableCapsules.visibility = View.GONE
    save_btn.visibility = View.GONE
    putCapsules.visibility = View.GONE
  }

  fun layoutStart() {
    if (coffeeCaps?.isNotEmpty() == true) {
      availableCapsules.visibility = View.VISIBLE
      capsules.visibility = View.VISIBLE
    }
    progressBar.visibility = View.GONE
    name.visibility = View.VISIBLE
    description.visibility = View.VISIBLE
    intensity.visibility = View.VISIBLE
    size.visibility = View.VISIBLE
    image.visibility = View.VISIBLE
    intensityText.visibility = View.VISIBLE
    quantityText.visibility = View.VISIBLE
    save_btn.visibility = View.VISIBLE
    putCapsules.visibility = View.VISIBLE
  }

  companion object {
    const val UID = "uid"
    const val CAPSULES = "capsules"
    const val ml = " ml"
  }
}

