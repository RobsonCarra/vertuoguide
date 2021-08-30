package br.com.alura.ceep.ui.coffemachine.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.CoffeMachineData
import br.com.alura.ceep.ui.coffemachine.presentation.view.custom.CoffeAdapter

class HomeFragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val coffeMachineData = arrayOf(
            CoffeMachineData(
                "Ristretto",
                "10",
                "Intensidade",
                "É preparado de forma semelhante ao espresso," +
                        " mas com metade da água e, embora a quantidade de café seja a mesma," +
                        " uma moagem mais fina é usada para retardar sua extração." +
                        " A extração é geralmente interrompida por volta dos 15 segundos," +
                        " em vez dos 25 a 30 segundos do espresso.",
                "Quantidade",
                "240 ml",
                R.drawable.ristretto
            ),
            CoffeMachineData(
                "Capuccino",
                "6",
                "Intensidade",
                ("Cappuccino, pronunciado capuchino," +
                        " é uma bebida italiana preparada com café expresso e leite." +
                        " Um cappuccino clássico, muito famoso no Brasil" +
                        " e consiste em um terço de café expresso," +
                        " um terço de leite vaporizado e um terço de espuma de leite vaporizado."),
                "Quantidade",
                "240 ml",
                R.drawable.capuccino
            )
        )
        val coffeAdapter = CoffeAdapter(coffeMachineData)
        recyclerView.adapter = coffeAdapter
        coffeAdapter.notifyDataSetChanged()
    }
}