package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.presentation.DetailActivity

class CoffeAdapter() :
    RecyclerView.Adapter<CoffeeViewHolder>() {

    private var context: Context? = null
    var list = ArrayList<Coffee>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.coffe_item_list, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        holder.itemView.setOnClickListener { v: View? ->
            val bundle = Bundle()
            bundle.putParcelable("coffe", list[position])
            bundle.putString("nome", list[position].description)
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
        holder.bind(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}