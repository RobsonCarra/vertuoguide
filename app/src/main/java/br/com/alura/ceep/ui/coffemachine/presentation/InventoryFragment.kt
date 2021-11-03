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
import br.com.alura.ceep.ui.coffemachine.helpers.CoffeesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.presentation.custom.ItemAdapter
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
        CoffeesRoomDataBase.getDatabase(requireContext()).coffesDao(),
        RetrofitConfig().getClient(requireContext())
      ),
      FirebaseAuth.getInstance(),
      SharedPref(requireContext())
    )
  }

  private lateinit var itemAdapter: ItemAdapter
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
    val token = SharedPref(requireContext()).getString(SharedPref.TOKEN)
    token?.let {
      lifecycleScope.launch {
        // viewModel.getAll(viewLifecycleOwner, token)
      }
    }
  }

  private fun onChangeCoffedata(selected: Coffee) {
    val bundle = Bundle()
    bundle.putParcelable("coffe", selected)
    val intent = Intent(requireContext(), NewCoffeeActivity::class.java)
    intent.putExtras(bundle)
    requireContext().startActivity(intent)
  }

  private fun setup(view: View) {
    putName = view.findViewById(R.id.confirm_password)
    new = view.findViewById(R.id.new_coffe_button)
    recyclerView = view.findViewById(R.id.coffe_recyclerview_inventory)
    progressBar = view.findViewById(R.id.progress_bar_inventory)
    addCoffeesButton = view.findViewById(R.id.add_coffees_inventory_btn)
    addCoffeesButton.visibility = View.INVISIBLE
  }

  private fun observers() {
    viewModel.list.observe(viewLifecycleOwner) { coffes ->
      itemAdapter.list.clear()
      itemAdapter.list.addAll(coffes)
      itemAdapter.notifyDataSetChanged()
      progressBar.visibility = View.INVISIBLE
      addCoffeesButton.visibility = View.INVISIBLE

    }
    viewModel.coffeeFiltered.observe(viewLifecycleOwner, { list ->
      itemAdapter.list.clear()
      itemAdapter.list.addAll(list)
      itemAdapter.notifyDataSetChanged()
    })
    viewModel.added.observe(viewLifecycleOwner) { saved ->
      if (saved) {
        Toast.makeText(requireContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show()
      }
      viewModel.getAll()
      viewModel.error.observe(viewLifecycleOwner) { coffes ->
        progressBar.visibility = View.INVISIBLE
        addCoffeesButton.visibility = View.VISIBLE
      }
      val token = SharedPref(requireContext()).getString(SharedPref.TOKEN)
      token?.let {
        // viewModel.getAll(viewLifecycleOwner, token)
      }
    }
  }

  private fun listeners() {
    new.setOnClickListener { v: View? ->
      val intent = Intent(context, NewCoffeeActivity::class.java)
      context?.startActivity(intent)
    }
    addCoffeesButton.setOnClickListener {
      val intent = Intent(context, NewCoffeeActivity::class.java)
      context?.startActivity(intent)
    }
  }

  private fun watchers() {
    putName.doAfterTextChanged { typed ->
      typed?.let {
        if (it.count() >= 3) {
          viewModel.searchByName(typed.toString(), viewLifecycleOwner)
        } else if (it.count() == 0) {
          val token = SharedPref(requireContext()).getString(SharedPref.TOKEN)
          token?.let {
            // viewModel.getAll(viewLifecycleOwner, token)
          }
        }
      }
    }
  }

  fun initList() {
    progressBar.visibility = View.VISIBLE
    itemAdapter = ItemAdapter(selected = { selected ->
      onChangeCoffedata(selected)
    })
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = LinearLayoutManager(requireContext())
    recyclerView.adapter = itemAdapter
  }
}
