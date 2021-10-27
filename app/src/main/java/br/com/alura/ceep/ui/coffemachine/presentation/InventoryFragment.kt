package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.presentation.custom.ItemAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class InventoryFragment : Fragment() {

  private val viewModel: CoffesViewModel by viewModels {
    CoffesViewModel.CoffesViewModelFactory(
      CoffesRepository(
        CoffesRoomDataBase.getDatabase(requireContext()).coffesDao(),
        RetrofitConfig().getClient(requireContext())
      )
    )
  }

  private lateinit var itemAdapter: ItemAdapter
  private lateinit var putName: TextInputEditText
  private lateinit var new: Button
  private lateinit var recyclerView: RecyclerView
  private lateinit var progressBar: ProgressBar

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
  }

  private fun observers() {
    viewModel.list.observe(viewLifecycleOwner) { coffes ->
      itemAdapter.list.clear()
      itemAdapter.list.addAll(coffes)
      itemAdapter.notifyDataSetChanged()
      progressBar.visibility = View.INVISIBLE

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
