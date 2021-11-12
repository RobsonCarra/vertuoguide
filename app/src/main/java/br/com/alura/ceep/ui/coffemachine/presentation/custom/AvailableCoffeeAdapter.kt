package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee

class AvailableCoffeeAdapter(
  val selected: (coffee: Coffee) -> Unit
) :
  RecyclerView.Adapter<AvailableCoffeeViewHolder>() {
  var list = ArrayList<Coffee>()
  private var context: Context? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableCoffeeViewHolder {
    context = parent.context
    val layoutInflater = LayoutInflater.from(context)
    val view = layoutInflater.inflate(R.layout.available_coffee_item_list, parent, false)
    return AvailableCoffeeViewHolder(view)
  }

  override fun onBindViewHolder(holderHome: AvailableCoffeeViewHolder, position: Int) {
    holderHome.bind(list.get(position))
    holderHome.itemView.setOnClickListener {
      selected(list.get(position))
    }
    holderHome.descriptionButton.setOnClickListener {
      if (holderHome.description.visibility == View.VISIBLE) {
        holderHome.description.visibility = View.GONE
        holderHome.descriptionButton.setBackgroundResource(R.drawable.down_arrow)
      } else {
        holderHome.description.visibility = View.VISIBLE
        holderHome.descriptionButton.setBackgroundResource(R.drawable.up_arrow)
      }
    }
  }

  override fun getItemCount(): Int {
    return list.count()
  }
}