package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.presentation.custom.ItemAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import kotlinx.coroutines.launch

class InventoryFragment : Fragment() {

    private val viewModel: CoffesViewModel by viewModels {
        CoffesViewModel.CoffesViewModelFactory(
            CoffesRepository(
                CoffesRoomDataBase.getDatabase(requireContext()).coffesDao()
            )
        )
    }

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var putName: AutoCompleteTextView
    private lateinit var amount: TextView
    private lateinit var plus: Button
    private lateinit var less: Button
    private lateinit var save: Button
    private lateinit var new: Button
    private lateinit var recyclerView: RecyclerView

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
        lifecycleScope.launch {
            viewModel.getAll()
        }
    }

    private fun setup(view: View) {
        putName = view.findViewById(R.id.put_name)
        amount = view.findViewById(R.id.amount)
        plus = view.findViewById(R.id.plus)
        less = view.findViewById(R.id.less)
        save = view.findViewById(R.id.save_button)
        new = view.findViewById(R.id.new_coffe_button)
        recyclerView = view.findViewById(R.id.coffe_recyclerview_inventory)

    }

    private fun observers() {
        viewModel.list.observe(viewLifecycleOwner) { coffes ->
            itemAdapter.list.addAll(coffes)
            itemAdapter.notifyDataSetChanged()
        }
        viewModel.coffee.observe(viewLifecycleOwner, Observer {
            val adapter =
                ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, it)
            putName.setAdapter(adapter)
        })

        viewModel.added.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                Toast.makeText(requireContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show()
            }
            viewModel.getAll()
        }


    }

    private fun listeners() {
        plus.setOnClickListener { v: View? ->
            val amountNotConverted = amount.text.toString()
            val amountConverted = amountNotConverted.toInt()
            val total = amountConverted + 1
            val amountFormatted = total.toString()
            amount.text = amountFormatted
        }
        less.setOnClickListener { v: View? ->
            val amountNotConverted = amount.text.toString()
            val amountConverted = amountNotConverted.toInt()
            if (amountConverted > 0) {
                val total = amountConverted - 1
                val amountFormatted = total.toString()
                amount.text = amountFormatted
            }
        }
        save.setOnClickListener { v: View? ->
////            val name = putName.text.toString()
//            val amountConverted = amount.text.toString().toLong()
////            if (name.isNotEmpty()) {
////
////            } else () {
////                Toast.makeText(
////                    requireContext(), "adada",
////                    Toast.LENGTH_SHORT
////                ).show()
////            }


        }
        new.setOnClickListener { v: View? ->
            val intent = Intent(context, NewCoffeeActivity::class.java)
            context?.startActivity(intent)
        }
    }

    private fun initList() {
        itemAdapter = ItemAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = itemAdapter
    }
}
