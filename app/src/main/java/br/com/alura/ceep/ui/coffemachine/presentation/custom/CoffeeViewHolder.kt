package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.PhotoHelper
import com.squareup.picasso.Picasso

class CoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  var image: ImageView
  var description: TextView
  var name: TextView
  var size: TextView
  var intensity: TextView
  var capsules: TextView

  init {
    image = itemView.findViewById(R.id.image_coffe)
    description = itemView.findViewById(R.id.description)
    name = itemView.findViewById(R.id.name)
    size = itemView.findViewById(R.id.size)
    intensity = itemView.findViewById(R.id.intensity)
    capsules = itemView.findViewById(R.id.capsules)
  }

  fun bind(coffee: Coffee) {
    description.text = coffee.description
    name.text = coffee.name
    size.text = coffee.quantity
    intensity.text = coffee.intensity
    capsules.text = coffee.capsules.toString()
    Picasso.get().load(coffee.image)
      .placeholder(R.drawable.ic_launcher_background)
      .into(image)
  }
}