package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.presentation.Login.view.LoginActivity
import br.com.alura.ceep.ui.coffemachine.presentation.custom.CoffeAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class HomeFragment() : Fragment() {

    private lateinit var crashButton: Button
    private lateinit var recyclerView: RecyclerView

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
                CoffesRoomDataBase.getDatabase(requireContext()).coffesDao()
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
        load()
        lifecycleScope.launch {
            viewModel.getAll(viewLifecycleOwner)
        }
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
    }

    private fun observers() {
        viewModel.added.observe(viewLifecycleOwner) { coffee ->
            viewModel.getAll(viewLifecycleOwner)
        }
        viewModel.list.observe(viewLifecycleOwner) { coffee ->
            coffeAdapter.list.addAll(coffee)
            coffeAdapter.notifyDataSetChanged()
        }
    }

    fun initList() {
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = coffeAdapter
    }

    private fun load() {
    }
}



