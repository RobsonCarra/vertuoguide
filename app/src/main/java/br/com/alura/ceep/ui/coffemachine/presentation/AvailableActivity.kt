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
import androidx.core.widget.doAfterTextChanged
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
import br.com.alura.ceep.ui.coffemachine.presentation.custom.AvailableCoffeeAdapter
import br.com.alura.ceep.ui.coffemachine.presentation.custom.HomeCoffeeAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffeesViewModel
import br.com.alura.ceep.ui.coffemachine.viewmodel.config.CoffesViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class AvailableActivity : AppCompatActivity() {
  private lateinit var recyclerView: RecyclerView
  private lateinit var progressBar: ProgressBar
  private lateinit var coffeToolbar: Toolbar
  private lateinit var putName: TextInputEditText

  private var availableCoffeeAdapter: AvailableCoffeeAdapter =
    AvailableCoffeeAdapter(selected = { coffee ->
      val bundle = Bundle()
      bundle.putString(UID, coffee.uid)
      val intent = Intent(this, AddActivity::class.java)
      intent.putExtras(bundle)
      this.startActivity(intent)
    })

  private val viewModel: CoffeesViewModel by viewModels {
    CoffesViewModelFactory(
      CoffeesRepository(
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
    watchers()
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
    putName = findViewById(R.id.put_name)
  }

  private fun observers() {
    viewModel.list.observe(this) { coffee ->
      if (coffee.isNotEmpty()) {
        recyclerView.visibility = View.VISIBLE
        availableCoffeeAdapter.list.clear()
        availableCoffeeAdapter.list.addAll(coffee)
        availableCoffeeAdapter.notifyDataSetChanged()
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
    viewModel.coffeeFiltered.observe(this) { list ->
      availableCoffeeAdapter.list.clear()
      availableCoffeeAdapter.list.addAll(list)
      availableCoffeeAdapter.notifyDataSetChanged()
    }
  }

  private fun watchers() {
    putName.doAfterTextChanged { typed ->
      typed?.let {
        if (it.count() >= 3) {
          viewModel.searchByName(typed.toString(), false)
        } else if (it.count() == 0) {
          viewModel.searchByUser()
        }
      }
    }
  }

  fun initList() {
    progressBar.visibility = View.VISIBLE
    recyclerView.hasFixedSize()
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = availableCoffeeAdapter
  }

  companion object {
    const val UID = "uid"
  }
}
