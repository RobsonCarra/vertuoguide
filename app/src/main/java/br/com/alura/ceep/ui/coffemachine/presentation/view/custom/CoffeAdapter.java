package br.com.alura.ceep.ui.coffemachine.presentation.view.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.ui.coffemachine.R;
import br.com.alura.ceep.ui.coffemachine.domain.CoffeMachineData;
import br.com.alura.ceep.ui.coffemachine.presentation.view.DetailActivity;
import org.jetbrains.annotations.NotNull;

public class CoffeAdapter extends RecyclerView.Adapter<CoffeeViewHolder> {

  private Context context;
  CoffeMachineData[] coffeMachineData;

  public CoffeAdapter(CoffeMachineData[] coffeMachineData) {
    this.coffeMachineData = coffeMachineData;
  }

  @NonNull
  @NotNull
  @Override
  public CoffeeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View view = layoutInflater.inflate(R.layout.coffe_item_list, parent, false);
    return new CoffeeViewHolder(view);
  }

  public void onBindViewHolder(@NonNull @NotNull CoffeeViewHolder holder, int position) {
    holder.itemView.setOnClickListener(v -> {
      Bundle bundle = new Bundle();
      bundle.putSerializable("coffe", coffeMachineData[position]);
      bundle.putString("nome", coffeMachineData[position].getCoffeDescription());
      Intent intent = new Intent(context, DetailActivity.class);
      intent.putExtras(bundle);
      context.startActivity(intent);
    });
    final CoffeMachineData coffeMachineDataList = coffeMachineData[position];
    holder.textViewDescription.setText(coffeMachineDataList.getCoffeDescription());
    holder.textViewSize.setText(coffeMachineDataList.getCoffeSize());
    holder.textQtd.setText(coffeMachineDataList.getCoffeSizeTitule());
    holder.textViewIntensity.setText(coffeMachineDataList.getCoffeIntensity());
    holder.textInt.setText(coffeMachineDataList.getCoffeIntensityTitule());
    holder.textViewType.setText(coffeMachineDataList.getCoffeType());
    holder.coffeImage.setImageResource(coffeMachineDataList.getCoffeImage());
  }

  @Override
  public int getItemCount() {
    return coffeMachineData.length;
  }
}
