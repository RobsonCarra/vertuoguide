package br.com.alura.ceep.ui.coffemachine.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.ui.coffemachine.R;
import br.com.alura.ceep.ui.coffemachine.domain.CoffeMachineData;
import br.com.alura.ceep.ui.coffemachine.presentation.view.custom.CoffeAdapter;

public class HomeFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.home_fragment, container, false);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    CoffeMachineData[] coffeMachineData = new CoffeMachineData[] {
        new CoffeMachineData(
            "Ristretto",
            "10",
            "Intensidade",
            "É preparado de forma semelhante ao espresso," +
                " mas com metade da água e, embora a quantidade de café seja a mesma," +
                " uma moagem mais fina é usada para retardar sua extração." +
                " A extração é geralmente interrompida por volta dos 15 segundos," +
                " em vez dos 25 a 30 segundos do espresso.",
            "Quantidade",
            "240 ml",
            R.drawable.ristretto),
        new CoffeMachineData(
            "Capuccino",
            "6",
            "Intensidade",
            "Cappuccino, pronunciado capuchino," +
                " é uma bebida italiana preparada com café expresso e leite." +
                " Um cappuccino clássico, muito famoso no Brasil" +
                " e consiste em um terço de café expresso," +
                " um terço de leite vaporizado e um terço de espuma de leite vaporizado.",
            "Quantidade",
            "240 ml",
            R.drawable.capuccino),
    };
    CoffeAdapter coffeAdapter = new CoffeAdapter(coffeMachineData);
    recyclerView.setAdapter(coffeAdapter);
    coffeAdapter.notifyDataSetChanged();
  }
}