package br.com.alura.ceep.ui.coffemachine.presentation.view.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.ui.coffemachine.R;
import br.com.alura.ceep.ui.coffemachine.domain.CoffeMachineDataItem;

import org.jetbrains.annotations.NotNull;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private Context context;
    CoffeMachineDataItem[] coffeMachineDataItem;

    public ItemAdapter(CoffeMachineDataItem[] coffeMachineDataItem) {
        this.coffeMachineDataItem = coffeMachineDataItem;
    }
    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.inventory_item_list, parent, false);
        return new ItemViewHolder (view);
    }
    public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position) {
        final CoffeMachineDataItem coffeMachineDataList = coffeMachineDataItem[position];
        holder.textViewType.setText(coffeMachineDataList.getCoffeType());
        holder.coffeImage.setImageResource(coffeMachineDataList.getCoffeImage());
    }
    @Override
    public int getItemCount() {
        return coffeMachineDataItem.length;
    }
}
