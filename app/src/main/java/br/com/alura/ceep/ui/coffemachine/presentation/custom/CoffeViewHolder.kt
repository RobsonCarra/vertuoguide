package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee

class CoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var coffeImage: ImageView
    var textViewDescription: TextView
    var textViewType: TextView
    var textViewSize: TextView
    var textViewIntensity: TextView
    var textQtd: TextView
    var textInt: TextView

    init {
        coffeImage = itemView.findViewById(R.id.coffeImagetype)
        textViewDescription = itemView.findViewById(R.id.coffeDescription)
        textViewType = itemView.findViewById(R.id.coffeType)
        textViewSize = itemView.findViewById(R.id.coffeSize)
        textViewIntensity = itemView.findViewById(R.id.numberIntensity)
        textQtd = itemView.findViewById(R.id.qtdCoffe)
        textInt = itemView.findViewById(R.id.coffeIntensityTitule)
    }

    fun bind(coffee: Coffee){

    }
}