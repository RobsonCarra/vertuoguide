package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee

class HomeCoffeeAdapter(
  val selected: (coffee: Coffee) -> Unit,
  val onCoffeeNow: (coffee: Coffee) -> Unit
) :
  RecyclerView.Adapter<HomeCoffeeViewHolder>() {
  var list = ArrayList<Coffee>()
  private var context: Context? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCoffeeViewHolder {
    context = parent.context
    val layoutInflater = LayoutInflater.from(context)
    val view = layoutInflater.inflate(R.layout.home_coffe_item_list, parent, false)
    return HomeCoffeeViewHolder(view)
  }

  override fun onBindViewHolder(holderHome: HomeCoffeeViewHolder, position: Int) {
    holderHome.bind(list.get(position))
    holderHome.itemView.setOnClickListener { v: View? ->
      selected(list.get(position))
    }
    holderHome.coffeeNow.setOnClickListener { v: View? ->
      onCoffeeNow(list.get(position))
    }
  }

  override fun getItemCount(): Int {
    return list.count()
  }
}