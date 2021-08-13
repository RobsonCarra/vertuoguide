package br.com.alura.ceep.ui.coffemachine.presentation.view;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.com.alura.ceep.ui.coffemachine.domain.CoffeMachineData;
import br.com.alura.ceep.ui.coffemachine.R;

public class DetailActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.item_detail);
        androidx.appcompat.widget.Toolbar coffeToolbar = (Toolbar) findViewById (R.id.coffeToolbar);
        setSupportActionBar (coffeToolbar);
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
        coffeToolbar.setNavigationOnClickListener (arrow -> onBackPressed ());
        TextView coffeType = findViewById (R.id.coffeType);
        TextView coffeDescription = findViewById (R.id.coffeDescription);
        TextView coffeIntensity = findViewById (R.id.numberIntensity);
        TextView coffeSize = findViewById (R.id.coffeSize);
        TextView coffeIntensityTitule = findViewById (R.id.coffeIntensityTitule);
        TextView coffeQuantityTitule = findViewById (R.id.quantityTitule);
        ImageView coffeImage = findViewById (R.id.coffeImagetype);
        Bundle bundle = getIntent ().getExtras ();
        if (bundle != null) {
            CoffeMachineData coffeMachine = (CoffeMachineData) bundle.getSerializable ("coffe");
            coffeType.setText (coffeMachine.getCoffeType ());
            coffeDescription.setText (coffeMachine.getCoffeDescription ());
            coffeSize.setText (coffeMachine.getCoffeSize ());
            coffeIntensityTitule.setText (coffeMachine.getCoffeIntensityTitule ());
            coffeQuantityTitule.setText (coffeMachine.getCoffeSizeTitule ());
            coffeIntensity.setText (coffeMachine.getCoffeIntensity ());
            coffeImage.setImageResource (coffeMachine.getCoffeImage ());
        }
    }
}

