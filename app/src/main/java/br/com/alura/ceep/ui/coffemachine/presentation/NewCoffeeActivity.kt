package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import com.google.android.material.textfield.TextInputEditText

class NewCoffeeActivity : AppCompatActivity() {

  private val viewModel: CoffesViewModel by viewModels {
    CoffesViewModel.CoffesViewModelFactory(
      CoffesRepository(
        CoffesRoomDataBase.getDatabase(this).coffesDao()
      )
    )
  }

  private lateinit var putName: TextInputEditText
  private lateinit var putDescription: TextInputEditText
  private lateinit var putIntensity: TextInputEditText
  private lateinit var putQuantity: TextInputEditText
  private lateinit var putCapsules: TextInputEditText
  private lateinit var coffeToolbar: Toolbar
  private lateinit var save: Button
  private var id: Long? = null
  private var uid: String? = null

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.new_coffee_activity)
    setup()
    listeners()
    observers()
    load()
  }

  private fun load() {
    (intent.extras?.getParcelable("coffe") as Coffee?)?.let { coffee ->
      coffee.id?.let { coffeeId ->
        id = coffeeId
      }
      uid = coffee.uid
      putName.setText(coffee.name)
      putDescription.setText(coffee.description)
      putQuantity.setText(coffee.quantity)
      putCapsules.setText(coffee.capsules.toString())
      putIntensity.setText(coffee.intensity)
    }
  }

  private fun setup() {
    putName = findViewById(R.id.put_password)
    putDescription = findViewById(R.id.put_description)
    putIntensity = findViewById(R.id.put_intensity)
    putQuantity = findViewById(R.id.put_quantity)
    putCapsules = findViewById(R.id.put_capsules)
    save = findViewById(R.id.save_button)
    coffeToolbar = findViewById(R.id.coffe_toolbar)
    setSupportActionBar(coffeToolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun listeners() {
    coffeToolbar.setNavigationOnClickListener {
      onBackPressed()
    }
    save.setOnClickListener {
      val name = putName.text.toString()
      val description = putDescription.text.toString()
      val intensity = putIntensity.text.toString().toInt()
      val quantity = putQuantity.text.toString().toInt()
      val capsules = putCapsules.text.toString().toInt()
      if (name.isEmpty()) {
        Toast.makeText(
          this, "Please enter the name of the coffee",
          Toast.LENGTH_SHORT
        ).show()
        return@setOnClickListener
      }
      if (description.isEmpty()) {
        Toast.makeText(
          this, "please enter the description of the coffee",
          Toast.LENGTH_SHORT
        ).show()
        return@setOnClickListener
      }
      if (intensity <= 0) {
        Toast.makeText(
          this, "please enter the value of the coffee intensity",
          Toast.LENGTH_SHORT
        ).show()
        return@setOnClickListener
      }
      if (quantity <= 0) {
        Toast.makeText(
          this, "please enter the value of the coffee quantity",
          Toast.LENGTH_SHORT
        ).show()
        return@setOnClickListener
      }
      if (capsules <= 0) {
        Toast.makeText(
          this, "please enter the value of quantity of capsules",
          Toast.LENGTH_SHORT
        ).show()
        return@setOnClickListener
      }
      val coffee = Coffee(
        id = id,
        name = name,
        capsules = capsules,
        description = description,
        intensity = intensity.toString(),
        quantity = quantity.toString()
      )
      uid?.let { uidNotNull ->
        coffee.uid = uidNotNull
        coffee.image = uidNotNull + ".jpg"
      }
      viewModel.save(coffee)
      val intent = Intent(this, DashboardActivity::class.java)
      this.startActivity(intent)
    }
  }

  private fun observers() {
    viewModel.added.observe(this) { saved ->
      if (saved) {
        Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DashboardActivity::class.java)
        this.startActivity(intent)
      }
    }
  }
}







