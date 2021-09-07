package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.presentation.DetailActivity
import br.com.alura.ceep.ui.coffemachine.presentation.NewCoffeeActivity


class ItemAdapter(val selected: (coffee: Coffee) -> Unit) :
    RecyclerView.Adapter<ItemViewHolder>() {
    var list = ArrayList<Coffee>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(context).inflate(R.layout.inventory_item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list.get(position))
        holder.itemView.setOnClickListener { v: View? ->
            selected(list.get(position))
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}