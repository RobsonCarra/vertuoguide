package br.com.alura.ceep.ui.coffemachine.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.CoffesApplication
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel

class HomeFragment() : Fragment() {

  private lateinit var recyclerView: RecyclerView;

  private val viewModel: CoffesViewModel by viewModels {
    CoffesViewModel.CoffesViewModelFactory(
      (context?.applicationContext as CoffesApplication).coffesRepository
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
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = LinearLayoutManager(requireContext())
    listeners()
    load()
  }

  private fun listeners() {
  }

  private fun setup(view: View) {
    recyclerView = view.findViewById(R.id.recyclerView)
  }

  private fun load() {
  }
}
