package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.exceptions.BadGatewayException
import br.com.alura.ceep.ui.coffemachine.exceptions.BadRequestException
import br.com.alura.ceep.ui.coffemachine.exceptions.NoContentException
import br.com.alura.ceep.ui.coffemachine.exceptions.NotFoundException
import br.com.alura.ceep.ui.coffemachine.helpers.CoffeesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.presentation.custom.CoffeeAdaper
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffeesViewModel
import br.com.alura.ceep.ui.coffemachine.viewmodel.config.CoffesViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class AvailableCoffeeActivity : AppCompatActivity() {
  private lateinit var recyclerView: RecyclerView
  private lateinit var progressBar: ProgressBar
  private lateinit var coffeToolbar: Toolbar
  private var coffeeAdaper: CoffeeAdaper = CoffeeAdaper(selected = { coffee ->
    val bundle = Bundle()
    bundle.putString("uid", coffee.uid)
    val intent = Intent(this, AddCoffeeActivity::class.java)
    intent.putExtras(bundle)
    this.startActivity(intent)
  })

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

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.available_coffee_activity)
    setup()
    initList()
    listeners()
    viewModel.getAll()
    observers()
  }

  private fun listeners() {
    coffeToolbar.setNavigationOnClickListener { arrow: View? ->
      onBackPressed()
    }
  }

  private fun setup() {
    recyclerView = findViewById(R.id.coffe_list_recyclerview)
    progressBar = findViewById(R.id.progress)
    coffeToolbar = findViewById(R.id.coffe_toolbar_add_coffee)
  }

  private fun observers() {
    viewModel.list.observe(this) { coffee ->
      if (coffee.isNotEmpty()) {
        recyclerView.visibility = View.VISIBLE
        coffeeAdaper.list.clear()
        coffeeAdaper.list.addAll(coffee)
        coffeeAdaper.notifyDataSetChanged()
        progressBar.visibility = View.GONE
      } else {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
      }
    }
    viewModel.error.observe(this) { exception ->
      when (exception) {
        is NoContentException -> {
          recyclerView.visibility = View.GONE
          progressBar.visibility = View.GONE
        }
        is BadRequestException -> Toast.makeText(
          this,
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
        is NotFoundException -> {
          recyclerView.visibility = View.GONE
          progressBar.visibility = View.GONE
        }
        is BadGatewayException -> Toast.makeText(
          this,
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
      }
    }
  }

  fun initList() {
    progressBar.visibility = View.VISIBLE
    recyclerView.hasFixedSize()
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = coffeeAdaper
  }
}
