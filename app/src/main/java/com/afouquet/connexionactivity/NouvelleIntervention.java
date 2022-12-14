package com.afouquet.connexionactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.afouquet.connexionactivity.daos.DaoActivite;

public class NouvelleIntervention extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_intervention);
        findViewById(R.id.buttonRetourNvelle).setOnClickListener((View view)->{
            finish();
        });
        ArrayAdapter<String> adapterActivite = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, DaoActivite.getInstance().getActivitesLocales());

    }
}