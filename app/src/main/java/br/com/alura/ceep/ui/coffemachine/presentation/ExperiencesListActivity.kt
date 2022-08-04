package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.helpers.AnalyticsHelper
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.presentation.custom.ExperienceAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffeesViewModel
import br.com.alura.ceep.ui.coffemachine.viewmodel.config.CoffesViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ExperiencesListActivity: AppCompatActivity() {
  private val viewModel: CoffeesViewModel by viewModels {
    CoffesViewModelFactory(
      CoffeesRepository(
        RetrofitConfig().getClient(this)
      ),
      FirebaseAuth.getInstance(),
      SharedPref(this)
    )
  }

  private lateinit var experienceAdapter: ExperienceAdapter
  // private lateinit var inventoryAdapter: InventoryAdapter
  private lateinit var putName: TextInputEditText
  private lateinit var new: Button
  private lateinit var recyclerView: RecyclerView
  private lateinit var progressBar: ProgressBar
  private lateinit var addCoffeesButton: Button

  private val analyticsHelper: AnalyticsHelper by lazy {
    AnalyticsHelper(this)
  }

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.experience_list_activity)
    setup()
    listeners()
    initList()
    val token = SharedPref(this).getString(SharedPref.TOKEN)
    token?.let {
      viewModel.getAllExperiences()
    }
  }
  private fun setup() {
    putName = findViewById(R.id.confirm_password)
    new = findViewById(R.id.new_coffee_inventory_btn)
    recyclerView = findViewById(R.id.coffe_recyclerview_experiences)
    progressBar = findViewById(R.id.progress_bar_inventory)
    addCoffeesButton = findViewById(R.id.add_coffees_inventory_btn)
    addCoffeesButton.visibility = View.GONE
  }
  private fun listeners() {
    new.setOnClickListener {
      analyticsHelper.log(AnalyticsHelper.INVENTORY_NEW_COFFEE_CLICKED)

      val intent = Intent(this, AvailableActivity::class.java)
      this.startActivity(intent)
    }
    addCoffeesButton.setOnClickListener {
      analyticsHelper.log(AnalyticsHelper.INVENTORY_ADD_COFFEE_CLICKED)
      val intent = Intent(this, AvailableActivity::class.java)
      this.startActivity(intent)
    }
    viewModel.listExperience.observe(this) { list ->
      // analyticsHelper.log(AnalyticsHelper.INVENTORY_FILTERED)
      experienceAdapter.list.clear()
      experienceAdapter.list.addAll(list)
      experienceAdapter.notifyDataSetChanged()
    }
  }
  fun initList() {
    progressBar.visibility = View.GONE
    recyclerView.visibility = View.VISIBLE
    experienceAdapter = ExperienceAdapter(selected = { selected ->
    })
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = experienceAdapter
  }

  companion object {
    const val UID = "uid"
    const val CAPSULES = "capsules"
  }

  // private fun onChangeCoffedata(selected: Coffee) {
  //   analyticsHelper.log(AnalyticsHelper.INVENTORY_ATT_COFFEE_CLICKED)
  //   val bundle = Bundle()
  //   bundle.putString(UID, selected.uid)
  //   bundle.putString(CAPSULES, selected.capsules.toString())
  //   val intent = Intent(this, AddActivity::class.java)
  //   intent.putExtras(bundle)
  //   this.startActivity(intent)
  // }

  // private fun observers() {
  //   // viewModel.listByUser.observe(viewLifecycleOwner) { coffees ->
  //   //   if (coffees.isNotEmpty()) {
  //   //     inventoryAdapter.list.clear()
  //   //     inventoryAdapter.list.addAll(coffees)
  //   //     inventoryAdapter.notifyDataSetChanged()
  //   //     progressBar.visibility = View.GONE
  //   //     recyclerView.visibility = View.VISIBLE
  //   //     analyticsHelper.log(AnalyticsHelper.INVENTORY_SUCESS)
  //   //   } else {
  //   //     recyclerView.visibility = View.GONE
  //   //     progressBar.visibility = View.GONE
  //   //     addCoffeesButton.visibility = View.VISIBLE
  //   //   }
  //   // }
  //   viewModel.coffeeFiltered.observe(viewLifecycleOwner, { list ->
  //     analyticsHelper.log(AnalyticsHelper.INVENTORY_FILTERED)
  //     inventoryAdapter.list.clear()
  //     inventoryAdapter.list.addAll(list)
  //     inventoryAdapter.notifyDataSetChanged()
  //   })
  //   viewModel.errorByUser.observe(requireActivity()) { exception ->
  //     analyticsHelper.log(AnalyticsHelper.INVENTORY_ERROR_BY_USER)
  //     when (exception) {
  //       is NoContentException -> {
  //         recyclerView.visibility = View.GONE
  //         progressBar.visibility = View.GONE
  //         addCoffeesButton.visibility = View.VISIBLE
  //       }
  //       is BadRequestException -> Toast.makeText(
  //         requireContext(),
  //         exception.message,
  //         Toast.LENGTH_SHORT
  //       ).show()
  //       is NotFoundException -> Toast.makeText(
  //         requireContext(),
  //         exception.message,
  //         Toast.LENGTH_SHORT
  //       ).show()
  //       is BadGatewayException -> Toast.makeText(
  //         requireContext(),
  //         exception.message,
  //         Toast.LENGTH_SHORT
  //       ).show()
  //     }
  //   }
  //   viewModel.errorByName.observe(requireActivity()) { exception ->
  //     analyticsHelper.log(AnalyticsHelper.INVENTORY_ERROR_BY_NAME)
  //     when (exception) {
  //       is NoContentException -> Toast.makeText(
  //         requireContext(),
  //         exception.message,
  //         Toast.LENGTH_SHORT
  //       ).show()
  //       is BadRequestException -> Toast.makeText(
  //         requireContext(),
  //         exception.message,
  //         Toast.LENGTH_SHORT
  //       ).show()
  //       is NotFoundException -> Toast.makeText(
  //         requireContext(),
  //         getString(R.string.coffee_not_found),
  //         Toast.LENGTH_SHORT
  //       ).show()
  //       is BadGatewayException -> Toast.makeText(
  //         requireContext(),
  //         exception.message,
  //         Toast.LENGTH_SHORT
  //       ).show()
  //     }
  //   }
  // }

  // private fun watchers() {
  //   putName.doAfterTextChanged { typed ->
  //     analyticsHelper.log(AnalyticsHelper.INVENTORY_CLICKED)
  //     typed?.let {
  //       if (it.count() >= 3) {
  //         viewModel.searchByName(typed.toString(), true)
  //       } else if (it.count() == 0) {
  //         viewModel.searchByUser()
  //       }
  //     }
  //     analyticsHelper.log(AnalyticsHelper.INVENTORY_SEARCHED)
  //   }
  // }
}