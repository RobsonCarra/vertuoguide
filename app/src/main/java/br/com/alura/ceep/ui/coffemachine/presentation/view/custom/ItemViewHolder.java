package br.com.alura.ceep.ui.coffemachine.presentation.view.custom;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import br.com.alura.ceep.ui.coffemachine.R;
public class ItemViewHolder extends RecyclerView.ViewHolder {

    ImageView coffeImage;
    TextView textViewType;

    public ItemViewHolder(@NotNull View itemView) {
        super(itemView);
        coffeImage = itemView.findViewById(R.id.circularImageView);
        textViewType = itemView.findViewById(R.id.coffeItemType);
    }
}
