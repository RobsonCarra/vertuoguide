package br.com.alura.ceep.ui.coffemachine.presentation.view.custom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.CoffeMachineData
import br.com.alura.ceep.ui.coffemachine.presentation.view.DetailActivity

class CoffeAdapter(var coffeMachineData: Array<CoffeMachineData>) :
    RecyclerView.Adapter<CoffeeViewHolder>() {
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.coffe_item_list, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        holder.itemView.setOnClickListener { v: View? ->
            val bundle = Bundle()
            bundle.putSerializable("coffe", coffeMachineData[position])
            bundle.putString("nome", coffeMachineData[position].CoffeDescription)
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
        val coffeMachineDataList = coffeMachineData[position]
        holder.textViewDescription.text = coffeMachineDataList.CoffeDescription
        holder.textViewSize.text = coffeMachineDataList.CoffeSize
        holder.textQtd.text = coffeMachineDataList.CoffeSizeTitule
        holder.textViewIntensity.text = coffeMachineDataList.CoffeIntensity
        holder.textInt.text = coffeMachineDataList.CoffeIntensityTitule
        holder.textViewType.text = coffeMachineDataList.Coffetype
        holder.coffeImage.setImageResource(coffeMachineDataList.CoffeImage)
    }

    override fun getItemCount(): Int {
        return coffeMachineData.size
    }
}