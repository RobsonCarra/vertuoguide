package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import com.squareup.picasso.Picasso

class InventoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  var image: ImageView = itemView.findViewById(R.id.circular_experience_coffee)
  var name: TextView = itemView.findViewById(R.id.name_coffee)
  var capsules: TextView = itemView.findViewById(R.id.capsules)

  fun bind(coffee: Coffee) {
    name.text = coffee.name
    capsules.text = coffee.capsules.toString()
    Picasso.get().load(coffee.image).into(image)
  }
}
