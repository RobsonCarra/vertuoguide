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
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.CoffesApplication
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.presentation.custom.ItemAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import com.google.android.material.textfield.TextInputEditText
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
    private lateinit var putName: TextInputEditText
    private lateinit var putDescription: TextInputEditText
    private lateinit var putIntensity: TextInputEditText
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
        putName = view.findViewById(R.id.put_name)
        putDescription = view.findViewById(R.id.put_description)
        putIntensity = view.findViewById(R.id.put_intensity)
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

        viewModel.added.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                Toast.makeText(requireContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show()
            }
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
            val name = putName.text.toString()
            val description = putDescription.text.toString()
            val intensity = putIntensity.text.toString()
            val amountConverted = amount.text.toString().toLong()
            val quantity = "240 ml"
            if (name.isNotEmpty() && description.isNotEmpty() && intensity.isNotEmpty()) {
                val coffee = Coffee(
                    0, name, amountConverted, description,
                    intensity, quantity, R.drawable.capuccino
                )
                viewModel.add(coffee)
            } else if (name.isNotEmpty() && description.isNotEmpty() && intensity.isEmpty()) {
                Toast.makeText(
                    requireContext(), "please enter the value of" +
                            " the intensity of the coffee",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (name.isNotEmpty() && description.isEmpty() && intensity.isEmpty()) {
                Toast.makeText(
                    requireContext(), "please enter the value of" +
                            " the intensity and the descripiton of the coffee",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (name.isEmpty() && description.isNotEmpty() && intensity.isNotEmpty()) {
                Toast.makeText(
                    requireContext(), "please enter the name of the coffee",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (name.isEmpty() && description.isEmpty() && intensity.isNotEmpty()) {
                Toast.makeText(
                    requireContext(), "please enter the name and the descripiton of the coffee",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(), "please enter the descripiton of the coffee",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun initList() {
        itemAdapter = ItemAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = itemAdapter
    }
}
