package br.com.alura.ceep.ui.coffemachine;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import org.parceler.Parcels;

import java.lang.invoke.MethodType;

public class MainActivity extends AppCompatActivity {

    private boolean parcelable;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        RecyclerView recyclerView = findViewById (R.id.recyclerView);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));
        CoffeMachineData[] coffeMachineData = new CoffeMachineData[]{

                new CoffeMachineData (
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
                        R.mipmap.coffe_foreground),

                new CoffeMachineData (
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

        CoffeAdapter coffeAdapter = new CoffeAdapter (coffeMachineData, MainActivity.this);
        recyclerView.setAdapter (coffeAdapter);
        coffeAdapter.notifyDataSetChanged();
    }
}
