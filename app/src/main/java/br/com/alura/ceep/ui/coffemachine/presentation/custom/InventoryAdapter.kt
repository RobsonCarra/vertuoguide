package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee

class InventoryAdapter(
  val selected: (coffee: Coffee) -> Unit
) :
  RecyclerView.Adapter<InventoryViewHolder>() {
  var list = ArrayList<Coffee>()
  private var context: Context? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
    context = parent.context
    val view =
      LayoutInflater.from(context).inflate(R.layout.inventory_item_list, parent, false)
    return InventoryViewHolder(view)
  }

  override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
    holder.bind(list.get(position))
    holder.itemView.setOnClickListener {
      selected(list.get(position))
    }
  }

  override fun getItemCount(): Int {
    return list.count()
  }
}