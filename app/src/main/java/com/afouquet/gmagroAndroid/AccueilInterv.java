package com.afouquet.gmagroAndroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afouquet.gmagroAndroid.bean.Intervention;
import com.afouquet.gmagroAndroid.daos.DaoIntervenant;
import com.afouquet.gmagroAndroid.daos.DaoIntervention;
import com.afouquet.gmagroAndroid.daos.DelegateAsyncTask;

public class AccueilInterv extends AppCompatActivity {

    private ArrayAdapter<Intervention> arrayAdapterIntervention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_interv);
        arrayAdapterIntervention = new ArrayAdapter<Intervention>(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getInterventionLocales());
        ((ListView) findViewById(R.id.lvInterventionsAccueil)).setAdapter(arrayAdapterIntervention);

        DaoIntervention.getInstance().getIntervention(new DelegateAsyncTask() {
            @Override
            public void whenWSConnexionIsTerminated(Object result) {
                if ((boolean) result) {
                    Toast.makeText(AccueilInterv.this,"liste vide",Toast.LENGTH_LONG).show();
                }else{
                    arrayAdapterIntervention.notifyDataSetChanged();
                    Log.d("verif","e");
                }
            }
        });
        findViewById(R.id.buttonDecoAccueil).setOnClickListener((View view) -> {
            DaoIntervenant.getInstance().deconnexionMembre(new DelegateAsyncTask() {
                @Override
                public void whenWSConnexionIsTerminated(Object result) {
                    if ((boolean) result) {
                        finish();
                    } else {
                        Toast.makeText(AccueilInterv.this, "déconnexion échouée", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        findViewById(R.id.buttonNouvelleInterAccueil).setOnClickListener((View view)->{
                Intent leIntent = new Intent(AccueilInterv.this,NouvelleIntervention.class);
                startActivityForResult(leIntent,1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            DaoIntervention.getInstance().getIntervention(new DelegateAsyncTask() {
                @Override
                public void whenWSConnexionIsTerminated(Object result) {
                    arrayAdapterIntervention.notifyDataSetChanged();

                }
            });
        }
    }
}
