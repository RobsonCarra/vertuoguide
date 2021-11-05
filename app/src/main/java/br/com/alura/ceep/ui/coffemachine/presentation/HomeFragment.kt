package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.CoffeeUser
import br.com.alura.ceep.ui.coffemachine.exceptions.BadGatewayException
import br.com.alura.ceep.ui.coffemachine.exceptions.BadRequestException
import br.com.alura.ceep.ui.coffemachine.exceptions.NoContentException
import br.com.alura.ceep.ui.coffemachine.exceptions.NotFoundException
import br.com.alura.ceep.ui.coffemachine.helpers.CoffeesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.presentation.custom.HomeCoffeeAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffeesViewModel
import br.com.alura.ceep.ui.coffemachine.viewmodel.config.CoffesViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class HomeFragment() : Fragment() {

  private lateinit var recyclerView: RecyclerView
  private lateinit var progressBar: ProgressBar
  private lateinit var addCoffeesButton: Button
  private lateinit var addCoffeesMsg: TextView

  private var homeCoffeeAdapter: HomeCoffeeAdapter = HomeCoffeeAdapter(selected = { coffee ->
    val bundle = Bundle()
    bundle.putString("uid", coffee.uid)
    bundle.putString("capsules", coffee.capsules.toString())
    val intent = Intent(context, DrinkNowActivity::class.java)
    intent.putExtras(bundle)
    context?.startActivity(intent)
  }, onCoffeeNow = { coffee ->
    recyclerView.visibility = View.GONE
    progressBar.visibility = View.VISIBLE
    val uid = coffee.uid
    val capsules = coffee.capsules
    val coffeeUser = capsules.let { it1 ->
      CoffeeUser(
        capsules = it1 - 1,
        uid = uid
      )
    }
    viewModel.save(coffeeUser)
    viewModel.added.observe(this) { saved ->
      if (saved) {
        Toast.makeText(requireContext(), coffee.name + " drinked", Toast.LENGTH_SHORT).show()
        viewModel.searchByUser()
      }
    }
  }
  )

  private val viewModel: CoffeesViewModel by viewModels {
    CoffesViewModelFactory(
      CoffeesRepository(
        CoffeesRoomDataBase.getDatabase(requireContext()).coffesDao(),
        RetrofitConfig().getClient(requireContext())
      ),
      FirebaseAuth.getInstance(),
      SharedPref(requireContext())
    )
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.home_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setup(view)
    initList()
    listeners()
    viewModel.searchByUser()
    observers()
  }

  private fun listeners() {
    addCoffeesButton.setOnClickListener {
      val intent = Intent(context, AvailableActivity::class.java)
      context?.startActivity(intent)
    }
  }

  private fun setup(view: View) {
    recyclerView = view.findViewById(R.id.coffe_list_recyclerview)
    progressBar = view.findViewById(R.id.progress)
    addCoffeesButton = view.findViewById(R.id.add_coffees_btn)
    addCoffeesMsg = view.findViewById(R.id.add_coffee_msg)
  }

  private fun observers() {
    viewModel.listByUser.observe(viewLifecycleOwner) { coffee ->
      if (coffee.isNotEmpty()) {
        recyclerView.visibility = View.VISIBLE
        homeCoffeeAdapter.list.clear()
        homeCoffeeAdapter.list.addAll(coffee)
        homeCoffeeAdapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
        addCoffeesButton.visibility = View.GONE
        addCoffeesMsg.visibility = View.GONE
      } else {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        addCoffeesButton.visibility = View.VISIBLE
        addCoffeesMsg.visibility = View.VISIBLE
      }
    }
    viewModel.errorByUser.observe(requireActivity()) { exception ->
      when (exception) {
        is NoContentException -> {
          recyclerView.visibility = View.GONE
          progressBar.visibility = View.GONE
          addCoffeesButton.visibility = View.VISIBLE
          addCoffeesMsg.visibility = View.VISIBLE
        }
        is BadRequestException -> Toast.makeText(
          requireContext(),
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
        is NotFoundException -> {
          recyclerView.visibility = View.GONE
          progressBar.visibility = View.GONE
          addCoffeesButton.visibility = View.VISIBLE
          addCoffeesMsg.visibility = View.VISIBLE
        }
        is BadGatewayException -> Toast.makeText(
          requireContext(),
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
      }
    }
  }

  fun initList() {
    progressBar.visibility = View.VISIBLE
    recyclerView.hasFixedSize()
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = homeCoffeeAdapter
  }
}



