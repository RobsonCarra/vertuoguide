package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.domain.Experience
import com.squareup.picasso.Picasso

class ExperienceViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView) {

  var image: ImageView
  var description: TextView
  var name: TextView

  init {
    image = itemView.findViewById(R.id.circular_experience_coffee)
    description = itemView.findViewById(R.id.experience_coffee)
    name = itemView.findViewById(R.id.name_coffee)
  }

  fun bind(experience: Experience) {
    description.text = experience.experienceDescription
    name.text = experience.coffeeName
    // Picasso.get().load(experience.coffee_image).fit().centerCrop().into(image)
  }
}