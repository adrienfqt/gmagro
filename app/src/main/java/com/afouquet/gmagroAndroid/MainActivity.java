package com.afouquet.gmagroAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afouquet.gmagroAndroid.daos.DaoIntervenant;
import com.afouquet.gmagroAndroid.daos.DelegateAsyncTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonValiderConnexion).setOnClickListener((View view)->{
            String login = ((TextView)findViewById(R.id.editLoginConnexion)).getText().toString();
            String mdp = ((TextView)findViewById(R.id.editPasswordConnexion)).getText().toString();
            DaoIntervenant.getInstance().connexionMembre(login, mdp, new DelegateAsyncTask() {
                @Override
                public void whenWSConnexionIsTerminated(Object result) {

                    if((boolean) result){
                        Intent leIntent = new Intent(MainActivity.this,AccueilInterv.class);
                        startActivity(leIntent);
                        Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Connexion échouée, erreur login/mdp", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}