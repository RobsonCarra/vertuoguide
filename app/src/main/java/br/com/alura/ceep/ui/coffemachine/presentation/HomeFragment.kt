package br.com.alura.ceep.ui.coffemachine.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.CoffesApplication
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.presentation.custom.CoffeAdapter
import br.com.alura.ceep.ui.coffemachine.presentation.custom.ItemAdapter
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.nio.file.Files.list
import java.util.Collections.list

class HomeFragment() : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var coffeAdapter: CoffeAdapter
    private lateinit var viewModel: CoffesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
        initList()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        listeners()
        observers()
        load()
        lifecycleScope.launch {
            viewModel.getAll()
        }
    }

    private fun listeners() {
    }

    private fun setup(view: View) {
        recyclerView = view.findViewById(R.id.coffe_list_recyclerview)

    }

    private fun observers() {
        viewModel.list.observe(viewLifecycleOwner) { coffes ->
            coffeAdapter.list.addAll(coffes)
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
