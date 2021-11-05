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
import br.com.alura.ceep.ui.coffemachine.helpers.CoffeesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffeesViewModel
import br.com.alura.ceep.ui.coffemachine.viewmodel.config.CoffesViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class DrinkNowActivity : AppCompatActivity() {

  private val viewModel: CoffeesViewModel by viewModels {
    CoffesViewModelFactory(
      CoffeesRepository(
        CoffeesRoomDataBase.getDatabase(this).coffesDao(),
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
  private lateinit var progressBarDrinkNow: ProgressBar
  private var mLastClickTime: Long = 0
  private lateinit var drink_now_btn: Button
  private var uid: String? = null
  private var coffeName: String? = null
  private var coffeeCaps: String? = null

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.drink_now_activity)
    setup()
    layoutGone()
    listeners()
    observers()
    progressBar.visibility = View.VISIBLE
    val token = SharedPref(this).getString(SharedPref.TOKEN)
    token?.let {
      intent.extras?.getString("uid")?.let { uid ->
        viewModel.searchByUid(uid)
      }
      intent.extras?.getString("capsules")?.let { caps ->
        coffeeCaps = caps
      }
    }
  }

  private fun setup() {
    name = findViewById(R.id.name)
    description = findViewById(R.id.description)
    intensity = findViewById(R.id.intensity)
    size = findViewById(R.id.size)
    image = findViewById(R.id.image)
    capsules = findViewById(R.id.capsules)
    intensityText = findViewById(R.id.Intensity)
    quantityText = findViewById(R.id.Quantity)
    availableCapsules = findViewById(R.id.available_capsules)
    coffeToolbar = findViewById(R.id.coffe_toolbar)
    progressBar = findViewById(R.id.progress_bar_detail_activity)
    progressBarDrinkNow = findViewById(R.id.progress_bar_drink_now_activity)
    drink_now_btn = findViewById(R.id.drink_now_btn)
    setSupportActionBar(coffeToolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
  }

  private fun listeners() {
    coffeToolbar.setNavigationOnClickListener { arrow: View? ->
      onBackPressed()
    }
    drink_now_btn.setOnClickListener {
      progressBarDrinkNow.visibility = View.VISIBLE
      if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
        return@setOnClickListener
      }
      val capsules = coffeeCaps?.toInt()
      mLastClickTime = SystemClock.elapsedRealtime();
      val coffeeUser = capsules?.let { it1 ->
        CoffeeUser(
          capsules = it1 - 1,
          uid = uid.toString()
        )
      }
      uid?.let { uidNotNull ->
        coffeeUser?.uid = uidNotNull
      }
      val token = SharedPref(this).getString(SharedPref.TOKEN)
      token?.let {
        uid?.let { uid ->
          if (coffeeUser != null) {
            viewModel.save(coffeeUser)
            Toast.makeText(this, coffeName + " " + getString(R.string.drinked), Toast.LENGTH_SHORT)
              .show()
          }
        }
      }
      progressBar.visibility = View.GONE
    }
  }

  private fun observers() {
    viewModel.coffeeById.observe(this) { coffee ->
      coffeName = coffee.name
      uid = coffee.uid
      name.text = coffee.name
      description.text = coffee.description
      size.text = coffee.quantity + " ml"
      capsules.text = coffeeCaps
      intensity.text = coffee.intensity
      Picasso.get().load(coffee.image)
        .placeholder(R.drawable.ic_launcher_background)
        .into(image)
      layoutStart()
    }
    viewModel.added.observe(this) { saved ->
      if (saved) {
        val intent = Intent(this, DashboardActivity::class.java)
        this.startActivity(intent)
        progressBar.visibility = View.GONE
      }
    }
    viewModel.showLoader.observe(this) { show ->
      if (show) {
        progressBarDrinkNow.visibility = View.VISIBLE
      }
      progressBarDrinkNow.visibility = View.GONE
    }
    viewModel.errorById.observe(this) { exception ->
      when (exception) {
        is NoContentException -> {
        }
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
    drink_now_btn.visibility = View.GONE
  }

  fun layoutStart() {
    progressBar.visibility = View.GONE
    name.visibility = View.VISIBLE
    description.visibility = View.VISIBLE
    intensity.visibility = View.VISIBLE
    size.visibility = View.VISIBLE
    image.visibility = View.VISIBLE
    capsules.visibility = View.VISIBLE
    intensityText.visibility = View.VISIBLE
    quantityText.visibility = View.VISIBLE
    availableCapsules.visibility = View.VISIBLE
    drink_now_btn.visibility = View.VISIBLE
  }
}

