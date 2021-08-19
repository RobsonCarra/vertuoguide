package br.com.alura.ceep.ui.coffemachine.presentation.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.CoffeMachineData

class DetailActivity : AppCompatActivity() {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_detail)
        val coffeToolbar = findViewById<View>(R.id.coffeToolbar) as Toolbar
        setSupportActionBar(coffeToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        coffeToolbar.setNavigationOnClickListener { arrow: View? -> onBackPressed() }
        val coffeType = findViewById<TextView>(R.id.coffeType)
        val coffeDescription = findViewById<TextView>(R.id.coffeDescription)
        val coffeIntensity = findViewById<TextView>(R.id.numberIntensity)
        val coffeSize = findViewById<TextView>(R.id.coffeSize)
        val coffeIntensityTitule = findViewById<TextView>(R.id.coffeIntensityTitule)
        val coffeQuantityTitule = findViewById<TextView>(R.id.quantityTitule)
        val coffeImage = findViewById<ImageView>(R.id.coffeImagetype)
        val bundle = intent.extras
        if (bundle != null) {
            val coffeMachine = bundle.getSerializable("coffe") as CoffeMachineData?
            coffeType.text = coffeMachine!!.coffeType
            coffeDescription.text = coffeMachine.coffeDescription
            coffeSize.text = coffeMachine.coffeSize
            coffeIntensityTitule.text = coffeMachine.coffeIntensityTitule
            coffeQuantityTitule.text = coffeMachine.coffeSizeTitule
            coffeIntensity.text = coffeMachine.coffeIntensity
            coffeImage.setImageResource(coffeMachine.coffeImage)
        }
    }
}