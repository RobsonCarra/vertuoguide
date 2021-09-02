package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.CoffesApplication
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.presentation.custom.ItemAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import com.google.android.material.textfield.TextInputLayout
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
    private lateinit var name: TextInputLayout
    private lateinit var amount: TextView
    private lateinit var plus: Button
    private lateinit var less: Button
    private lateinit var save: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.inventory_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        listeners()
        initList()
        observers()
        lifecycleScope.launch {
            viewModel.getAll()
        }
    }

    private fun init(view: View) {
        name = view.findViewById(R.id.search_name)
        amount = view.findViewById(R.id.amount)
        plus = view.findViewById(R.id.plus)
        less = view.findViewById(R.id.less)
        save = view.findViewById(R.id.save_button)
        recyclerView = view.findViewById(R.id.coffe_recyclerview_inventory)
    }

    private fun observers() {
        viewModel.list.observe(viewLifecycleOwner) { coffes ->
            itemAdapter.list.addAll(coffes)
            itemAdapter.notifyDataSetChanged()
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
            Toast.makeText(requireContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show()
            val Intent = Intent()
            val capsule = amount.text.toString()
            Intent.putExtra(
                EXTRA_REPLY,
                capsule
            )
        }
    }

    private fun initList() {
        itemAdapter = ItemAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = itemAdapter
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.Coffe_Machine.REPLY"
    }
}