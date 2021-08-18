package br.com.alura.ceep.ui.coffemachine.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.ui.coffemachine.R;
import br.com.alura.ceep.ui.coffemachine.domain.CoffeMachineDataItem;
import br.com.alura.ceep.ui.coffemachine.presentation.view.custom.CoffeAdapter;
import br.com.alura.ceep.ui.coffemachine.presentation.view.custom.ItemAdapter;

import com.google.android.material.textfield.TextInputLayout;

public class InventoryFragment extends Fragment {

  private TextInputLayout name;
  private TextView amount;
  private Button plus;
  private Button less;
  private Button save;
  private RecyclerView recyclerView;
  public static final String EXTRA_REPLY =
          "com.example.android.Coffe_Machine.REPLY";

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.inventory_fragment, container, false);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    init(view);
    listeners();
    initList();
  }

  private void init(View view) {
    name = view.findViewById(R.id.name);
    amount = view.findViewById(R.id.amount);
    plus = view.findViewById(R.id.plus);
    less = view.findViewById(R.id.less);
    save = view.findViewById(R.id.save);
    recyclerView = view.findViewById(R.id.recyclerView);
  }

  private void listeners() {
    plus.setOnClickListener(v -> {
      String amountNotConverted = amount.getText().toString();
      int amountConverted = Integer.parseInt(amountNotConverted);
      int total = amountConverted + 1;
      String amountFormatted = String.valueOf(total);
      amount.setText(amountFormatted);
    });
    less.setOnClickListener(v -> {
      String amountNotConverted = amount.getText().toString();
      int amountConverted = Integer.parseInt(amountNotConverted);
      if (amountConverted > 0) {
        int total = amountConverted - 1;
        String amountFormatted = String.valueOf(total);
        amount.setText(amountFormatted);
      }
    });
    save.setOnClickListener(v -> {
      Toast.makeText(requireContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show();
        Intent Intent = new Intent();
          String capsule = amount.getText ().toString();
          Intent.putExtra (EXTRA_REPLY,capsule);
        })
    ;};

  private void initList() {
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    CoffeMachineDataItem[] coffeMachineDataItem = new CoffeMachineDataItem[] {
        new CoffeMachineDataItem(
                "Ristretto",
                "",
                R.drawable.ristretto),
        new CoffeMachineDataItem (
                "Capuccino",
                "",
                R.drawable.capuccino),
    };
    ItemAdapter itemAdapter = new ItemAdapter (coffeMachineDataItem);
    recyclerView.setAdapter(itemAdapter);
    itemAdapter.notifyDataSetChanged();
  }
}