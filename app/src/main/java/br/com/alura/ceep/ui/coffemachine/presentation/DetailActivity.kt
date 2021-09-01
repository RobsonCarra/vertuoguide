package br.com.alura.ceep.ui.coffemachine.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.alura.ceep.ui.coffemachine.CoffesApplication
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel.CoffesViewModelFactory
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

  private val viewModel: CoffesViewModel by viewModels {
    CoffesViewModel.CoffesViewModelFactory(CoffesApplication.repository(this))
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.item_detail)
    val coffeToolbar = findViewById<View>(R.id.coffeToolbar) as Toolbar
    setSupportActionBar(coffeToolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    coffeToolbar.setNavigationOnClickListener { arrow: View? -> onBackPressed() }

    viewModel.list.observe(this) { users ->
    }
    lifecycleScope.launch {
      viewModel.getAllCoffes()
    }

//        val coffeType = findViewById<TextView>(R.id.coffeType)
//        val coffeDescription = findViewById<TextView>(R.id.coffeDescription)
//        val coffeIntensity = findViewById<TextView>(R.id.numberIntensity)
//        val coffeSize = findViewById<TextView>(R.id.coffeSize)
////        val coffeIntensityTitule = findViewById<TextView>(R.id.coffeIntensityTitule)
////        val coffeQuantityTitule = findViewById<TextView>(R.id.quantityTitule)
//        val coffeImage = findViewById<ImageView>(R.id.coffeImagetype)
////        val bundle = intent.extras
////        if (bundle != null) {
////            val coffeMachine = bundle.getSerializable("coffe") as CoffeMachineData?
//            coffeType.text = viewModel.
//            coffeDescription.text = coffes.description
//            coffeSize.text = coffes.quantity
////            coffeIntensityTitule.text = coffeMachine.CoffeIntensityTitule
////            coffeQuantityTitule.text = coffeMachine.CoffeSizeTitule
//            coffeIntensity.text = coffes.intensity
//            coffeImage.setImageResource(coffes.image)
  }
}
