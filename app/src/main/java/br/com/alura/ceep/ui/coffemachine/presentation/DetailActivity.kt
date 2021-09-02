package br.com.alura.ceep.ui.coffemachine.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

  private val viewModel: CoffesViewModel by viewModels {
    CoffesViewModel.CoffesViewModelFactory(
      CoffesRepository(
        CoffesRoomDataBase.getDatabase(this).coffesDao()
      )
    )
  }
  private lateinit var name: TextView
  private lateinit var description: TextView
  private lateinit var intensity: TextView
  private lateinit var size: TextView
  private lateinit var image: ImageView
  private lateinit var coffeToolbar: Toolbar

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.item_detail)
    setup()
    listeners()
    initList()
    observers()
    lifecycleScope.launch {
      viewModel.getAll()
    }
  }

  private fun setup() {
    name = findViewById(R.id.name)
    description = findViewById(R.id.description)
    intensity = findViewById(R.id.intensity)
    size = findViewById(R.id.size)
//        val coffeIntensityTitule = findViewById<TextView>(R.id.coffeIntensityTitule)
//        val coffeQuantityTitule = findViewById<TextView>(R.id.quantityTitule)
    image = findViewById(R.id.image_coffe)
    coffeToolbar = findViewById(R.id.coffe_toolbar)
    setSupportActionBar(coffeToolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    val bundle = intent.extras
    if (bundle != null) {
      val coffee = bundle.getParcelable("coffe") as Coffee?
      name.text = coffee?.name
      description.text = coffee?.description
      size.text = coffee?.quantity
//            coffeIntensityTitule.text = coffeMachine.CoffeIntensityTitule
//            coffeQuantityTitule.text = coffeMachine.CoffeSizeTitule
      intensity.text = coffee?.intensity
      image.setImageResource(coffee!!.image)
    }
  }

  private fun listeners() {
    coffeToolbar.setNavigationOnClickListener { arrow: View? ->
      onBackPressed()
    }
  }

  private fun initList() {
  }

  private fun observers() {
    viewModel.list.observe(this) { coffes ->
    }
  }
}
