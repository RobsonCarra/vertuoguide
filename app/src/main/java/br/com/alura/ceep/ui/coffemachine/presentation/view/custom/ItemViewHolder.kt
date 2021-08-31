package br.com.alura.ceep.ui.coffemachine.presentation.view.custom

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var coffeImage: ImageView
    var textViewType: TextView

    init {
        coffeImage = itemView.findViewById(R.id.circularImageView)
        textViewType = itemView.findViewById(R.id.coffeItemType)
    }
}