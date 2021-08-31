package br.com.alura.ceep.ui.coffemachine.presentation.view.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.CoffeMachineDataItem

class ItemAdapter(var coffeMachineDataItem: Array<CoffeMachineDataItem>) :
    RecyclerView.Adapter<ItemViewHolder>() {
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.inventory_item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val coffeMachineDataList = coffeMachineDataItem[position]
        holder.textViewType.text = coffeMachineDataList.CoffeType
        holder.coffeImage.setImageResource(coffeMachineDataList.CoffeImage)
    }

    override fun getItemCount(): Int {
        return coffeMachineDataItem.size
    }
}