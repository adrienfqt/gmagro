package com.afouquet.connexionactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.afouquet.connexionactivity.bean.Activite;
import com.afouquet.connexionactivity.bean.CauseDefaut;
import com.afouquet.connexionactivity.bean.CauseObjet;
import com.afouquet.connexionactivity.bean.Intervenant;
import com.afouquet.connexionactivity.bean.IntervenantTps;
import com.afouquet.connexionactivity.bean.Machine;
import com.afouquet.connexionactivity.bean.SymptomeDefaut;
import com.afouquet.connexionactivity.bean.SymptomeObjet;
import com.afouquet.connexionactivity.daos.DaoActivite;
import com.afouquet.connexionactivity.daos.DaoCSOD;
import com.afouquet.connexionactivity.daos.DaoIntervenant;
import com.afouquet.connexionactivity.daos.DaoIntervention;
import com.afouquet.connexionactivity.daos.DelegateAsyncTask;
import android.app.TimePickerDialog;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NouvelleIntervention extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private List<IntervenantTps> listIntervenantTps = new ArrayList<>();

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

        //DATE PICKERRRRR
        findViewById(R.id.buttonDateNvelle).setOnClickListener((View view)->{
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            ((EditText)findViewById(R.id.editDateNvelle)).setText(dayOfMonth+"/"+monthOfYear+"/"+year);

                        }
                    },
                    // on below line we are passing year,
                    // month and day for selected date in our date picker.
                    mYear, mMonth, mDay);
            // at last we are calling show to
            // display our date picker dialog.
            datePickerDialog.show();
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

        //Symptômes défauts
        ArrayAdapter<SymptomeDefaut> adapterSd = new ArrayAdapter<SymptomeDefaut>(NouvelleIntervention.this,
                android.R.layout.simple_spinner_item, DaoCSOD.getInstance().getSymptomeDefautsLocales());
        adapterSd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinnerSDefautNvelle)).setAdapter(adapterSd);
        DaoCSOD.getInstance().getSd(new DelegateAsyncTask() {
            @Override
            public void whenWSConnexionIsTerminated(Object result) {
                if((boolean) result){
                    Toast.makeText(NouvelleIntervention.this,"liste symptomes défauts vide",Toast.LENGTH_LONG).show();
                }else{
                    adapterSd.notifyDataSetChanged();
                }
            }
        });

        //Symptômes objets
        ArrayAdapter<SymptomeObjet> adapterSo = new ArrayAdapter<SymptomeObjet>(NouvelleIntervention.this,
                android.R.layout.simple_spinner_item, DaoCSOD.getInstance().getSymptomeObjetsLocales());
        adapterSo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinnerSObjetNvelle)).setAdapter(adapterSo);
        DaoCSOD.getInstance().getSo(new DelegateAsyncTask() {
            @Override
            public void whenWSConnexionIsTerminated(Object result) {
                if((boolean) result){
                    Toast.makeText(NouvelleIntervention.this,"liste Symptomes objets vide",Toast.LENGTH_LONG).show();
                }else{
                    adapterSo.notifyDataSetChanged();
                }
            }
        });

        // SPINNER Intervenants////////////////////////////////////////////////////////////////////////////

        ArrayAdapter<Intervenant> adapterIntervenant = new ArrayAdapter<Intervenant>(this,
                android.R.layout.simple_spinner_item, DaoIntervenant.getInstance().getIntervSiteLocals());

        adapterIntervenant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinnerIntervNvelle)).setAdapter(adapterIntervenant);

        DaoIntervenant.getInstance().getIntervSite(new DelegateAsyncTask() {
            @Override
            public void whenWSConnexionIsTerminated(Object result) {
                if((boolean) result){
                    Toast.makeText(NouvelleIntervention.this,"liste intervenants site vide",Toast.LENGTH_LONG).show();
                }else{
                    adapterIntervenant.notifyDataSetChanged();
                }
            }
        });


        //Ajout Intervenant TPS
        ArrayAdapter<IntervenantTps> adapterItps = new ArrayAdapter<IntervenantTps>(NouvelleIntervention.this,
                android.R.layout.simple_list_item_1, this.listIntervenantTps);
        ((ListView)(findViewById(R.id.lvintervNvelle))).setAdapter(adapterItps);

        findViewById(R.id.buttonAddIntervnvelle).setOnClickListener((View view)->{
            Intervenant inter = (Intervenant) ((Spinner)findViewById(R.id.spinnerIntervNvelle)).getSelectedItem();
            String leTemps= (String) ((Spinner)findViewById(R.id.spinnerTempsNvelle)).getSelectedItem();
            IntervenantTps itps= new IntervenantTps(inter,leTemps);
            listIntervenantTps.add(itps);
            adapterItps.notifyDataSetChanged();
            DaoIntervenant.getInstance().getIntervSiteLocals().remove(inter);
            adapterIntervenant.notifyDataSetChanged();
        });

        ((ListView)findViewById(R.id.lvintervNvelle)).setOnItemLongClickListener((parent, view, position, id) -> {
            IntervenantTps s = (IntervenantTps) adapterItps.getItem(position);
            listIntervenantTps.remove(s);
            adapterItps.notifyDataSetChanged();
            DaoIntervenant.getInstance().getIntervSiteLocals().add(s.getIntervenant());
            adapterIntervenant.notifyDataSetChanged();
            return true;
        });


    }
}