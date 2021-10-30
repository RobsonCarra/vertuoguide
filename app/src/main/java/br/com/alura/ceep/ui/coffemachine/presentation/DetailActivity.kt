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
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.domain.CoffeeUser
import br.com.alura.ceep.ui.coffemachine.exceptions.BadGatewayException
import br.com.alura.ceep.ui.coffemachine.exceptions.BadRequestException
import br.com.alura.ceep.ui.coffemachine.exceptions.NoContentException
import br.com.alura.ceep.ui.coffemachine.exceptions.NotFoundException
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.helpers.PhotoHelper
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import br.com.alura.ceep.ui.coffemachine.viewmodel.config.CoffesViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

  private val viewModel: CoffesViewModel by viewModels {
    CoffesViewModelFactory(
      CoffesRepository(
        CoffesRoomDataBase.getDatabase(this).coffesDao(),
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
  private lateinit var coffeToolbar: Toolbar
  private lateinit var progressBar: ProgressBar
  private lateinit var putCapsules: TextInputEditText
  private var mLastClickTime: Long = 0
  private lateinit var save_btn: Button
  private var uid: String? = null

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.item_detail)
    setup()
    listeners()
    observers()
    progressBar.visibility = View.VISIBLE
    val token = SharedPref(this).getString(SharedPref.TOKEN)
    token?.let {
      intent.extras?.getString("uid")?.let { uid ->
        viewModel.searchByUid(uid)
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
    coffeToolbar = findViewById(R.id.coffe_toolbar)
    progressBar = findViewById(R.id.progress_bar_detail_activity)
    putCapsules = findViewById(R.id.put_capsules_now)
    save_btn = findViewById(R.id.save_btn)
    setSupportActionBar(coffeToolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
  }

  private fun listeners() {
    coffeToolbar.setNavigationOnClickListener { arrow: View? ->
      onBackPressed()
    }
    save_btn.setOnClickListener {
      progressBar.visibility = View.VISIBLE
      if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
        return@setOnClickListener
      }
      val capsules = putCapsules.text.toString().toInt()
      mLastClickTime = SystemClock.elapsedRealtime();
      val coffeeUser = CoffeeUser(
        capsules = capsules,
        uid = uid.toString()
      )
      uid?.let { uidNotNull ->
        coffeeUser.uid = uidNotNull
      }
      val token = SharedPref(this).getString(SharedPref.TOKEN)
      token?.let {
        uid?.let { uid ->
          viewModel.save(coffeeUser)
        }
      }
      progressBar.visibility = View.GONE
    }
  }

  private fun observers() {
    viewModel.coffeeById.observe(this) { coffee ->
      uid = coffee.uid
      name.text = coffee.name
      description.text = coffee.description
      size.text = coffee.quantity + " ml"
      intensity.text = coffee.intensity
      Picasso.get().load(coffee.image)
        .placeholder(R.drawable.ic_launcher_background)
        .into(image)
    }
    progressBar.visibility = View.GONE

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

    viewModel.added.observe(this) { saved ->
      if (saved) {
        Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DashboardActivity::class.java)
        this.startActivity(intent)
        progressBar.visibility = View.GONE
      }
    }
  }
}

