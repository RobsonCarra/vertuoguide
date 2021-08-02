package br.com.alura.ceep.ui.coffemachine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        RecyclerView recyclerView = findViewById (R.id.recyclerView);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));
        CoffeMachineData[] coffeMachineData = new CoffeMachineData[]{
                new CoffeMachineData ("Ristretto",
                        "10",
                        "Intensidade",
                        "É preparado de forma semelhante ao espresso," +
                                " mas com metade da água e, embora a quantidade de café seja a mesma," +
                                " uma moagem mais fina é usada para retardar sua extração." +
                                " A extração é geralmente interrompida por volta dos 15 segundos," +
                                " em vez dos 25 a 30 segundos do espresso.",
                        "Quantidade",
                        "240 ml",
                        R.mipmap.coffe_foreground),
                new CoffeMachineData ("Capuccino",
                        "10",
                        "Intensidade",
                        "É preparado de forma semelhante ao espresso," +
                                " mas com metade da água e, embora a quantidade de café seja a mesma," +
                                " uma moagem mais fina é usada para retardar sua extração." +
                                " A extração é geralmente interrompida por volta dos 15 segundos," +
                                " em vez dos 25 a 30 segundos do espresso.",
                        "Quantidade",
                        "240 ml",
                        R.mipmap.coffe_foreground)
        };
        CoffeAdapter coffeAdapter = new CoffeAdapter (coffeMachineData, MainActivity.this);
        recyclerView.setAdapter (coffeAdapter);
        coffeAdapter.notifyDataSetChanged();
    }
}