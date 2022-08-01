package br.com.alura.ceep.ui.coffemachine.presentation.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.domain.Experience

class ExperienceAdapter (
  val selected: (experience: Experience) -> Unit,
  // val onCoffeeNow: (coffee: Coffee) -> Unit
) :
  RecyclerView.Adapter<ExperienceViewHolder>() {
    var list = ArrayList<Experience>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
      context = parent.context
      val layoutInflater = LayoutInflater.from(context)
      val view = layoutInflater.inflate(R.layout.experience_item_list, parent, false)
      return ExperienceViewHolder(view)
    }

    override fun onBindViewHolder(holderHome: ExperienceViewHolder, position: Int) {
      holderHome.bind(list.get(position))
      holderHome.itemView.setOnClickListener {
        selected(list.get(position))
      }
      // holderHome.coffeeNow.setOnClickListener {
      //   onCoffeeNow(list.get(position))
      // }
    }

    override fun getItemCount(): Int {
      return list.count()
    }
}