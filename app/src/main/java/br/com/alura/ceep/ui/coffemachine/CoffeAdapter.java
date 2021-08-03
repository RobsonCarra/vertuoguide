package br.com.alura.ceep.ui.coffemachine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class CoffeAdapter extends RecyclerView.Adapter<CoffeAdapter.ViewHolder> {

    CoffeMachineData[] coffeMachineData;
    Context context;


    public CoffeAdapter (CoffeMachineData[] coffeMachineData, MainActivity activity) {
        this.coffeMachineData = coffeMachineData;
        this.context = activity;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from (parent.getContext ());
        View view = layoutInflater.inflate (R.layout.coffe_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder (view);
        return viewHolder;
    }

    public void onBindViewHolder (@NonNull @NotNull ViewHolder holder, int position) {

        final CoffeMachineData coffeMachineDataList = coffeMachineData[position];
        holder.textViewDescription.setText (coffeMachineDataList.getCoffeDescription ());
        holder.textViewSize.setText (coffeMachineDataList.getCoffeSize ());
        holder.textQtd.setText (coffeMachineDataList.getCoffeSizeTitule ());
        holder.textViewIntensity.setText (coffeMachineDataList.getCoffeIntensity ());
        holder.textInt.setText (coffeMachineDataList.getCoffeIntensityTitule ());
        holder.textViewType.setText (coffeMachineDataList.getCoffeType ());
        holder.coffeImage.setImageResource (coffeMachineDataList.getCoffeImage ());
    }

    @Override
    public int getItemCount () {
        return coffeMachineData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView coffeImage;
        TextView textViewDescription;
        TextView textViewType;
        TextView textViewSize;
        TextView textViewIntensity;
        TextView textQtd;
        TextView textInt;

        public ViewHolder (@NonNull @NotNull View itemView) {

            super (itemView);
            itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View v) {
                    itemView.getContext ().startActivity (new Intent (itemView.getContext (), DetailActivity.class));
                }
            });
            coffeImage = itemView.findViewById (R.id.coffeImagetype);
            textViewDescription = itemView.findViewById (R.id.CoffeDescription);
            textViewType = itemView.findViewById (R.id.CoffeType);
            textViewSize = itemView.findViewById (R.id.CoffeSize);
            textViewIntensity = itemView.findViewById (R.id.NumberIntensity);
            textQtd = itemView.findViewById (R.id.QtdCoffe);
            textInt = itemView.findViewById (R.id.CoffeIntensity);
        }
    }

}
