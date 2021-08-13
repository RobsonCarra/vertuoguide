package br.com.alura.ceep.ui.coffemachine.presentation.view.custom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.ui.coffemachine.R;
import br.com.alura.ceep.ui.coffemachine.domain.CoffeMachineData;
import br.com.alura.ceep.ui.coffemachine.presentation.view.DetailActivity;
import org.jetbrains.annotations.NotNull;

public class CoffeeViewHolder extends RecyclerView.ViewHolder {

  ImageView coffeImage;
  TextView textViewDescription;
  TextView textViewType;
  TextView textViewSize;
  TextView textViewIntensity;
  TextView textQtd;
  TextView textInt;

  public CoffeeViewHolder(@NotNull View itemView) {
    super(itemView);
    coffeImage = itemView.findViewById(R.id.coffeImagetype);
    textViewDescription = itemView.findViewById(R.id.coffeDescription);
    textViewType = itemView.findViewById(R.id.coffeType);
    textViewSize = itemView.findViewById(R.id.coffeSize);
    textViewIntensity = itemView.findViewById(R.id.numberIntensity);
    textQtd = itemView.findViewById(R.id.qtdCoffe);
    textInt = itemView.findViewById(R.id.coffeIntensityTitule);
  }
}
