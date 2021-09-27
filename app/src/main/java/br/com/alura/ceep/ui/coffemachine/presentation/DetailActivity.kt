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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.helpers.PhotoHelper
import br.com.alura.ceep.ui.coffemachine.presentation.custom.CoffeAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import com.squareup.picasso.Picasso
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
    private lateinit var capsules: TextView
    private lateinit var image: ImageView
    private lateinit var coffeToolbar: Toolbar

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_detail)
        setup()
        listeners()
        observers()
        intent.extras?.getString("uid")?.let { uid ->
            viewModel.searchByUid(uid, this)
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
        setSupportActionBar(coffeToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun listeners() {
        coffeToolbar.setNavigationOnClickListener { arrow: View? ->
            onBackPressed()
        }
    }

    private fun observers() {
        viewModel.coffeeById.observe(this) { coffee ->
            name.text = coffee.name
            description.text = coffee.description
            size.text = coffee.quantity + " ml"
            intensity.text = coffee.intensity
            capsules.text = coffee.capsules.toString()
            PhotoHelper.loadStorageImage("Coffees/photos",coffee.image) { url ->
                if (url.isNotEmpty()){
                    Picasso.get().load(url)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(image)
                }
            }
        }
    }
}
