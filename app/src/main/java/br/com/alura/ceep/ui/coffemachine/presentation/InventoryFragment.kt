package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.exceptions.BadGatewayException
import br.com.alura.ceep.ui.coffemachine.exceptions.BadRequestException
import br.com.alura.ceep.ui.coffemachine.exceptions.NoContentException
import br.com.alura.ceep.ui.coffemachine.exceptions.NotFoundException
import br.com.alura.ceep.ui.coffemachine.helpers.CoffeesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.presentation.custom.InventoryAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffeesViewModel
import br.com.alura.ceep.ui.coffemachine.viewmodel.config.CoffesViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class InventoryFragment : Fragment() {

  private val viewModel: CoffeesViewModel by viewModels {
    CoffesViewModelFactory(
      CoffeesRepository(
        RetrofitConfig().getClient(requireContext())
      ),
      FirebaseAuth.getInstance(),
      SharedPref(requireContext())
    )
  }

  private lateinit var inventoryAdapter: InventoryAdapter
  private lateinit var putName: TextInputEditText
  private lateinit var new: Button
  private lateinit var recyclerView: RecyclerView
  private lateinit var progressBar: ProgressBar
  private lateinit var addCoffeesButton: Button

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.inventory_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setup(view)
    listeners()
    initList()
    observers()
    watchers()
    viewModel.searchByUser()
    progressBar.visibility = View.VISIBLE
    val token = SharedPref(requireContext()).getString(SharedPref.TOKEN)
    token?.let {
      lifecycleScope.launch {
        // viewModel.getAll(viewLifecycleOwner, token)
      }
    }
  }

  private fun onChangeCoffedata(selected: Coffee) {
    val bundle = Bundle()
    bundle.putString(UID, selected.uid)
    bundle.putString(CAPSULES, selected.capsules.toString())
    val intent = Intent(context, AddActivity::class.java)
    intent.putExtras(bundle)
    context?.startActivity(intent)
  }

  private fun setup(view: View) {
    putName = view.findViewById(R.id.confirm_password)
    new = view.findViewById(R.id.new_coffee_inventory_btn)
    recyclerView = view.findViewById(R.id.coffe_recyclerview_inventory)
    progressBar = view.findViewById(R.id.progress_bar_inventory)
    addCoffeesButton = view.findViewById(R.id.add_coffees_inventory_btn)
    addCoffeesButton.visibility = View.GONE
  }

  private fun listeners() {
    new.setOnClickListener { v: View? ->
      val intent = Intent(context, AvailableActivity::class.java)
      context?.startActivity(intent)
    }
    addCoffeesButton.setOnClickListener {
      val intent = Intent(context, AvailableActivity::class.java)
      context?.startActivity(intent)
    }
  }

  private fun observers() {
    viewModel.listByUser.observe(viewLifecycleOwner) { coffees ->
      if (coffees.isNotEmpty()) {
        inventoryAdapter.list.clear()
        inventoryAdapter.list.addAll(coffees)
        inventoryAdapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
      } else {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        addCoffeesButton.visibility = View.VISIBLE
      }
    }
    viewModel.coffeeFiltered.observe(viewLifecycleOwner, { list ->
      inventoryAdapter.list.clear()
      inventoryAdapter.list.addAll(list)
      inventoryAdapter.notifyDataSetChanged()
    })
    viewModel.added.observe(viewLifecycleOwner) { saved ->
      if (saved) {
        Toast.makeText(requireContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show()
      }
      viewModel.searchByUser()
      viewModel.errorByUser.observe(viewLifecycleOwner) { coffes ->
        progressBar.visibility = View.GONE
      }
      val token = SharedPref(requireContext()).getString(SharedPref.TOKEN)
      token?.let {
        // viewModel.getAll(viewLifecycleOwner, token)
      }
    }
    viewModel.errorByUser.observe(requireActivity()) { exception ->
      when (exception) {
        is NoContentException -> {
          recyclerView.visibility = View.GONE
          progressBar.visibility = View.GONE
          addCoffeesButton.visibility = View.VISIBLE
        }
        is BadRequestException -> Toast.makeText(
          requireContext(),
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
        is NotFoundException -> Toast.makeText(
          requireContext(),
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
        is BadGatewayException -> Toast.makeText(
          requireContext(),
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
      }
    }
    viewModel.errorByName.observe(requireActivity()) { exception ->
      when (exception) {
        is NoContentException -> Toast.makeText(
          requireContext(),
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
        is BadRequestException -> Toast.makeText(
          requireContext(),
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
        is NotFoundException -> Toast.makeText(
          requireContext(),
          getString(R.string.coffee_not_found),
          Toast.LENGTH_SHORT
        ).show()
        is BadGatewayException -> Toast.makeText(
          requireContext(),
          exception.message,
          Toast.LENGTH_SHORT
        ).show()
      }
    }
  }

  private fun watchers() {
    putName.doAfterTextChanged { typed ->
      typed?.let {
        if (it.count() >= 3) {
          viewModel.searchByName(typed.toString(), true)
        } else if (it.count() == 0) {
          viewModel.searchByUser()
        }
      }
    }
  }

  fun initList() {
    progressBar.visibility = View.VISIBLE
    recyclerView.visibility = View.GONE
    inventoryAdapter = InventoryAdapter(selected = { selected ->
      onChangeCoffedata(selected)
    })
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = LinearLayoutManager(requireContext())
    recyclerView.adapter = inventoryAdapter
  }
  companion object {
    const val UID = "uid"
    const val CAPSULES = "capsules"
  }
}

