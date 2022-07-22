package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.domain.Experience
import br.com.alura.ceep.ui.coffemachine.helpers.AnalyticsHelper
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.presentation.custom.InventoryAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffeesViewModel
import br.com.alura.ceep.ui.coffemachine.viewmodel.config.CoffesViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ExperienceCoffee: AppCompatActivity() {

  private val viewModel: CoffeesViewModel by viewModels {
    CoffesViewModelFactory(
      CoffeesRepository(
        RetrofitConfig().getClient(this)
      ),
      FirebaseAuth.getInstance(),
      SharedPref(this)
    )
  }

  private lateinit var putTaskName: AutoCompleteTextView
  private lateinit var putTaskDescription: TextInputEditText
  private lateinit var registerButton: Button
  // private lateinit var recyclerView: RecyclerView
  private lateinit var inventoryAdapter: InventoryAdapter
  // private lateinit var progressBar: ProgressBar
  private val analyticsHelper: AnalyticsHelper by lazy {
    AnalyticsHelper(this)
  }

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.add_experience)
    setup()
    listeners()
    viewModel.getAllExperiences()
    initList()
    val token = SharedPref(this).getString(SharedPref.TOKEN)
    token?.let {
      viewModel.getAllExperiences()
    }
  }

  private fun setup() {
    putTaskName = findViewById(R.id.put_task_title)
    putTaskDescription = findViewById(R.id.put_task_description)
    registerButton = findViewById(R.id.registerTaskButton)
  }

  private fun listeners(){
    val coffeeName = putTaskName.text.toString()
    val putTaskDescription = putTaskDescription.text.toString()
    registerButton.setOnClickListener {
      val experience = Experience (
        coffeeName = coffeeName,
        experienceDescription = putTaskDescription
        )
      viewModel.saveExperience(experience)
      SharedPref(this).clear()
      val intent = Intent(this, ExperiencesListActivity::class.java)
      this.startActivity(intent)
    }
    // if (putTask.isEmpty()) {
    // }
  }

  fun initList() {
    // progressBar.visibility = View.VISIBLE
    // recyclerView.visibility = View.GONE
    inventoryAdapter = InventoryAdapter(selected = { selected ->
      onChangeCoffedata(selected)
    })
    // recyclerView.setHasFixedSize(true)
    // recyclerView.layoutManager = LinearLayoutManager(this)
    // recyclerView.adapter = inventoryAdapter
  }

  private fun onChangeCoffedata(selected: Coffee) {
    analyticsHelper.log(AnalyticsHelper.INVENTORY_ATT_COFFEE_CLICKED)
    val bundle = Bundle()
    bundle.putString(InventoryFragment.UID, selected.uid)
    bundle.putString(InventoryFragment.CAPSULES, selected.capsules.toString())
    val intent = Intent(this, AddActivity::class.java)
    intent.putExtras(bundle)
    this.startActivity(intent)
  }
}