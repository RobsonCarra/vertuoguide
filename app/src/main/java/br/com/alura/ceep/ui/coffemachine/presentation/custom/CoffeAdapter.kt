package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.presentation.DetailActivity
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel

class CoffeAdapter(val selected: (coffee: Coffee) -> Unit) :
    RecyclerView.Adapter<CoffeeViewHolder>() {
    var list = ArrayList<Coffee>()
    private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.coffe_item_list, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        holder.bind(list.get(position))
        holder.itemView.setOnClickListener { v: View? ->
            selected(list.get(position))
            val intent = Intent(context, DetailActivity::class.java)
            context?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.count()
    }
}