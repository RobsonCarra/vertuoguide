package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.exceptions.BadGatewayException
import br.com.alura.ceep.ui.coffemachine.exceptions.BadRequestException
import br.com.alura.ceep.ui.coffemachine.exceptions.NoContentException
import br.com.alura.ceep.ui.coffemachine.exceptions.NotFoundException
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.presentation.custom.CoffeAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel

class HomeFragment() : Fragment() {

  private lateinit var crashButton: Button
  private lateinit var recyclerView: RecyclerView
  private lateinit var progressBar: ProgressBar

  private var coffeAdapter: CoffeAdapter = CoffeAdapter(selected = { coffee ->
    val bundle = Bundle()
    bundle.putString("uid", coffee.uid)
    val intent = Intent(context, DetailActivity::class.java)
    intent.putExtras(bundle)
    context?.startActivity(intent)
  })

  private val viewModel: CoffesViewModel by viewModels {
    CoffesViewModel.CoffesViewModelFactory(
      CoffesRepository(
        CoffesRoomDataBase.getDatabase(requireContext()).coffesDao(),
        RetrofitConfig().getClient(requireContext())
      )
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
    observers()
    viewModel.getAll()
  }

  private fun listeners() {
//        crashButton.setOnClickListener {
//            val intent = Intent(requireContext(), LoginActivity::class.java)
//            this.startActivity(intent)
//        }
  }

  private fun setup(view: View) {
    recyclerView = view.findViewById(R.id.coffe_list_recyclerview)
    crashButton = view.findViewById(R.id.coffe_now_button)
    progressBar = view.findViewById(R.id.progress)
  }

  private fun observers() {
    viewModel.list.observe(viewLifecycleOwner) { coffee ->
      recyclerView.visibility = View.VISIBLE
      coffeAdapter.list.clear()
      coffeAdapter.list.addAll(coffee)
      coffeAdapter.notifyDataSetChanged()
      progressBar.visibility = View.INVISIBLE
    }
    viewModel.error.observe(requireActivity()) { exception ->
      when (exception) {
        is NoContentException -> {
          recyclerView.visibility = View.GONE
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
  }

  fun initList() {
    progressBar.visibility = View.VISIBLE
    recyclerView.hasFixedSize()
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = coffeAdapter
  }
}



