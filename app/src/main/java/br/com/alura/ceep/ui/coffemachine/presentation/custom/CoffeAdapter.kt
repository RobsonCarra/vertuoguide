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

class CoffeAdapter(var coffees: List<Coffee>) :
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
      bundle.putParcelable("coffe", coffees[position])
      bundle.putString("nome", coffees[position].decription)
      val intent = Intent(context, DetailActivity::class.java)
      intent.putExtras(bundle)
      context?.startActivity(intent)
    }
    holder.bind(coffees[position])
    // val coffeMachineDataList = coffeMachineData[position]
    // holder.textViewDescription.text = coffeMachineDataList.CoffeDescription
    // holder.textViewSize.text = coffeMachineDataList.CoffeSize
    // holder.textQtd.text = coffeMachineDataList.CoffeSizeTitule
    // holder.textViewIntensity.text = coffeMachineDataList.CoffeIntensity
    // holder.textInt.text = coffeMachineDataList.CoffeIntensityTitule
    // holder.textViewType.text = coffeMachineDataList.Coffetype
    // holder.coffeImage.setImageResource(coffeMachineDataList.CoffeImage)
  }

  override fun getItemCount(): Int {
    return coffees.size
  }
}