package com.afouquet.connexionactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.afouquet.connexionactivity.bean.Activite;
import com.afouquet.connexionactivity.bean.CauseDefaut;
import com.afouquet.connexionactivity.bean.CauseObjet;
import com.afouquet.connexionactivity.bean.Machine;
import com.afouquet.connexionactivity.daos.DaoActivite;
import com.afouquet.connexionactivity.daos.DaoCSOD;
import com.afouquet.connexionactivity.daos.DaoIntervention;
import com.afouquet.connexionactivity.daos.DelegateAsyncTask;
import android.app.TimePickerDialog;
import android.widget.TimePicker;

import java.util.Calendar;

public class NouvelleIntervention extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_intervention);
        findViewById(R.id.buttonRetourNvelle).setOnClickListener((View view)->{
            finish();
        });

       // SPINNER ACTIVITEEE////////////////////////////////////////////////////////////////////////////

        ArrayAdapter<Activite> adapterActivite = new ArrayAdapter<Activite>(this,
                android.R.layout.simple_spinner_item,DaoActivite.getInstance().getActivitesLocales());

        adapterActivite.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinnerActiviteNvelle)).setAdapter(adapterActivite);

        DaoActivite.getInstance().getActivites(new DelegateAsyncTask() {
            @Override
            public void whenWSConnexionIsTerminated(Object result) {
                if((boolean) result){
                    Toast.makeText(NouvelleIntervention.this,"liste activité vide",Toast.LENGTH_LONG).show();
                }else{
                    adapterActivite.notifyDataSetChanged();
                }
            }
        });

        // SPINNER MACHINEEEEE ////////////////////////////////////////////////////////////////////////////
        ArrayAdapter<Machine> adapterMachine = new ArrayAdapter<Machine>(NouvelleIntervention.this,
                android.R.layout.simple_spinner_item, DaoIntervention.getInstance().getMachinesLocales());

        adapterMachine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinnerMachineNvelle)).setAdapter(adapterMachine);

        DaoIntervention.getInstance().getMachines(new DelegateAsyncTask() {
            @Override
            public void whenWSConnexionIsTerminated(Object result) {
                if((boolean)result){
                    Toast.makeText(NouvelleIntervention.this,"liste machine vide",Toast.LENGTH_LONG).show();
                }else{
                    adapterMachine.notifyDataSetChanged();
                }
            }
        });

        //TIMEPICKERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        findViewById(R.id.buttonTime).setOnClickListener((View view)->{
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            ((EditText)findViewById(R.id.editTime)).setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();

        });

        //CAUSES DEFAUUUUTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
        ArrayAdapter<CauseDefaut> adapterCd = new ArrayAdapter<CauseDefaut>(NouvelleIntervention.this,
                android.R.layout.simple_spinner_item, DaoCSOD.getInstance().getCausesDefautsLocales());
        adapterCd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinnerCDefautNvelle)).setAdapter(adapterCd);

        DaoCSOD.getInstance().getCd(new DelegateAsyncTask() {
            @Override
            public void whenWSConnexionIsTerminated(Object result) {
                if((boolean) result){
                    Toast.makeText(NouvelleIntervention.this,"liste causes défaults vide",Toast.LENGTH_LONG).show();
                }else{
                    adapterCd.notifyDataSetChanged();
                }
            }
        });

        //CAUSES OBJETSS
        ArrayAdapter<CauseObjet> adapterCo = new ArrayAdapter<CauseObjet>(NouvelleIntervention.this,
                android.R.layout.simple_spinner_item, DaoCSOD.getInstance().getCausesObjetsLocales());
        adapterCo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinnerCObjetNvelle)).setAdapter(adapterCo);
        DaoCSOD.getInstance().getCo(new DelegateAsyncTask() {
            @Override
            public void whenWSConnexionIsTerminated(Object result) {
                if((boolean) result){
                    Toast.makeText(NouvelleIntervention.this,"liste causes objets vide",Toast.LENGTH_LONG).show();
                }else{
                    adapterCo.notifyDataSetChanged();
                }
            }
        });

    }
}