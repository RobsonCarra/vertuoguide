package br.com.alura.ceep.ui.coffemachine.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

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
    private lateinit var coffeToolbar: Toolbar
    private lateinit var save: Button

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_coffee_activity)
        setup()
        listeners()
        initList()
        observers()
        lifecycleScope.launch {
            viewModel.getAll()
        }
    }

    private fun setup() {
        putName = findViewById(R.id.put_name)
        putDescription = findViewById(R.id.put_description)
        putIntensity = findViewById(R.id.put_intensity)
        save = findViewById(R.id.save_button)
        coffeToolbar = findViewById(R.id.coffe_toolbar)
        setSupportActionBar(coffeToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun listeners() {
        coffeToolbar.setNavigationOnClickListener { arrow: View? ->
            onBackPressed()
        }
        save.setOnClickListener { v: View? ->
            val name = putName.text.toString()
            val description = putDescription.text.toString()
            val intensity = putIntensity.text.toString()
            val quantity = "240 ml"
            val capsules = 0
            if (name.isNotEmpty() && description.isNotEmpty() && intensity.isNotEmpty()) {
                val coffee = Coffee(
                    0, name, capsules.toLong(), description,
                    intensity, quantity, R.drawable.capuccino
                )
                viewModel.add(coffee)
            } else if (name.isNotEmpty() && description.isNotEmpty() && intensity.isEmpty()) {
                Toast.makeText(
                    this, "please enter the value of" +
                            " the intensity of the coffee",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (name.isNotEmpty() && description.isEmpty() && intensity.isEmpty()) {
                Toast.makeText(
                    this, "please enter the value of" +
                            " the description and the intensity of the coffee",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (name.isEmpty() && description.isNotEmpty() && intensity.isNotEmpty()) {
                Toast.makeText(
                    this, "please enter the name of the coffee",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (name.isEmpty() && description.isEmpty() && intensity.isNotEmpty()) {
                Toast.makeText(
                    this, "please enter the name and the descripiton of the coffee",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (name.isNotEmpty() && description.isEmpty() && intensity.isNotEmpty()) {
                Toast.makeText(
                    this, "please enter the descripiton of the coffee",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this, "please enter the datas of the coffee",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initList() {
    }

    private fun observers() {
        viewModel.list.observe(this) { coffes ->
        }
        viewModel.added.observe(this) { saved ->
            if (saved) {
                Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show()
            }
            viewModel.getAll()
        }
    }
}
