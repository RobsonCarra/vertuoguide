package br.com.alura.ceep.ui.coffemachine.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.CoffeMachineDataItem
import br.com.alura.ceep.ui.coffemachine.presentation.view.custom.ItemAdapter
import com.google.android.material.textfield.TextInputLayout

class InventoryFragment : Fragment() {
    private var name: TextInputLayout? = null
    private var amount: TextView? = null
    private var plus: Button? = null
    private var less: Button? = null
    private var save: Button? = null
    private var recyclerView: RecyclerView? = null
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
    }

    private fun init(view: View) {
        name = view.findViewById(R.id.name)
        amount = view.findViewById(R.id.amount)
        plus = view.findViewById(R.id.plus)
        less = view.findViewById(R.id.less)
        save = view.findViewById(R.id.save)
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    private fun listeners() {
        plus!!.setOnClickListener { v: View? ->
            val amountNotConverted = amount!!.text.toString()
            val amountConverted = amountNotConverted.toInt()
            val total = amountConverted + 1
            val amountFormatted = total.toString()
            amount!!.text = amountFormatted
        }
        less!!.setOnClickListener { v: View? ->
            val amountNotConverted = amount!!.text.toString()
            val amountConverted = amountNotConverted.toInt()
            if (amountConverted > 0) {
                val total = amountConverted - 1
                val amountFormatted = total.toString()
                amount!!.text = amountFormatted
            }
        }
        save!!.setOnClickListener { v: View? ->
            Toast.makeText(requireContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show()
            val Intent = Intent()
            val capsule = amount!!.text.toString()
            Intent.putExtra(
                EXTRA_REPLY,
                capsule
            )
        }
    }

    private fun initList() {
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        val coffeMachineDataItem = arrayOf(
            CoffeMachineDataItem(
                "Ristretto",
                "",
                R.drawable.ristretto
            ),
            CoffeMachineDataItem(
                "Capuccino",
                "",
                R.drawable.capuccino
            )
        )
        val itemAdapter = ItemAdapter(coffeMachineDataItem)
        recyclerView!!.adapter = itemAdapter
        itemAdapter.notifyDataSetChanged()
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.Coffe_Machine.REPLY"
    }
}