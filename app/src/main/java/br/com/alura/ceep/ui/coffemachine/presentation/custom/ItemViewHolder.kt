package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var coffeImage: ImageView = itemView.findViewById(R.id.circularImageView)
    var textViewType: TextView = itemView.findViewById(R.id.coffeItemType)

    fun bind(coffee: Coffee) {
        coffeImage.setImageResource(coffee.image)
        textViewType.text = coffee.name
    }
}