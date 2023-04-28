package com.afouquet.gmagroAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.afouquet.gmagroAndroid.bean.Activite;
import com.afouquet.gmagroAndroid.bean.CauseDefaut;
import com.afouquet.gmagroAndroid.bean.CauseObjet;
import com.afouquet.gmagroAndroid.bean.Intervenant;
import com.afouquet.gmagroAndroid.bean.IntervenantTps;
import com.afouquet.gmagroAndroid.bean.Machine;
import com.afouquet.gmagroAndroid.bean.SymptomeDefaut;
import com.afouquet.gmagroAndroid.bean.SymptomeObjet;
import com.afouquet.gmagroAndroid.daos.DaoActivite;
import com.afouquet.gmagroAndroid.daos.DaoCSOD;
import com.afouquet.gmagroAndroid.daos.DaoIntervenant;
import com.afouquet.gmagroAndroid.daos.DaoIntervention;
import com.afouquet.gmagroAndroid.daos.DelegateAsyncTask;
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

                            monthOfYear = monthOfYear +1;
                            ((EditText)findViewById(R.id.editDateNvelle)).setText(mYear+"/"+monthOfYear+"/"+dayOfMonth);

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

        //Supression Intervenant TPS
        ((ListView)findViewById(R.id.lvintervNvelle)).setOnItemLongClickListener((parent, view, position, id) -> {
            IntervenantTps s = (IntervenantTps) adapterItps.getItem(position);
            listIntervenantTps.remove(s);
            adapterItps.notifyDataSetChanged();
            DaoIntervenant.getInstance().getIntervSiteLocals().add(s.getIntervenant());
            adapterIntervenant.notifyDataSetChanged();
            return true;
        });


        // VISIBILITE DATE ET HEURE DE LA FIN DE l'INTERVENTION
        ((CheckBox)(findViewById(R.id.checkInterTermineeNvelle))).setOnClickListener((View v) ->{
            if (((CheckBox)(findViewById(R.id.checkInterTermineeNvelle))).isChecked()){
                ((Button)findViewById(R.id.buttonHeureTerminNvelle)).setVisibility(View.VISIBLE);
                ((Button)findViewById(R.id.buttonDateTermineNvelle)).setVisibility(View.VISIBLE);
                ((EditText)findViewById(R.id.editHeureTermineNvelle)).setVisibility(View.VISIBLE);
                ((EditText)findViewById(R.id.editDateTermineNvelle)).setVisibility(View.VISIBLE);
            }else{
                ((Button)findViewById(R.id.buttonHeureTerminNvelle)).setVisibility(View.INVISIBLE);
                ((Button)findViewById(R.id.buttonDateTermineNvelle)).setVisibility(View.INVISIBLE);
                ((EditText)findViewById(R.id.editHeureTermineNvelle)).setVisibility(View.INVISIBLE);
                ((EditText)findViewById(R.id.editDateTermineNvelle)).setVisibility(View.INVISIBLE);
            }
        });


        //DATE PICKER INTERVENTION TERMINEE
        findViewById(R.id.buttonDateTermineNvelle).setOnClickListener((View view)->{
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {

                    monthOfYear = monthOfYear +1;
                    ((EditText)findViewById(R.id.editDateTermineNvelle)).setText(mYear+"/"+monthOfYear+"/"+dayOfMonth);

                }
            },
                    // on below line we are passing year,
                    // month and day for selected date in our date picker.
                    mYear, mMonth, mDay);
            // at last we are calling show to
            // display our date picker dialog.
            datePickerDialog.show();
        });

        //TIME PICKER INTERVENTION TERMINEE
        findViewById(R.id.buttonHeureTerminNvelle).setOnClickListener((View view)->{
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            ((EditText)findViewById(R.id.editHeureTermineNvelle)).setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();

        });

        // VISIBILITE DUREE DU TEMPS ARRET
        ((CheckBox)(findViewById(R.id.cbArretMachineNvelle))).setOnClickListener((View v) ->{
            if (((CheckBox)(findViewById(R.id.cbArretMachineNvelle))).isChecked()){
                ((Button)findViewById(R.id.buttonDureeArretNvelle)).setVisibility(View.VISIBLE);
                ((EditText)findViewById(R.id.editDureeArretNvelle)).setVisibility(View.VISIBLE);
            }else{
                ((Button)findViewById(R.id.buttonDureeArretNvelle)).setVisibility(View.INVISIBLE);
                ((EditText)findViewById(R.id.editDureeArretNvelle)).setVisibility(View.INVISIBLE);
            }
        });


        //Duree PICKER ARRET MACHINE
        findViewById(R.id.buttonDureeArretNvelle).setOnClickListener((View view)->{
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            ((EditText)findViewById(R.id.editDureeArretNvelle)).setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();

        });

        //ADD INTERVENTION
        findViewById(R.id.buttonAddIntervention).setOnClickListener((View v)->{
            String dh_deb= ((EditText)(findViewById(R.id.editDateNvelle))).getText().toString()+" "+((EditText)(findViewById(R.id.editTime))).getText().toString();
            String dh_fin = null;
            String commentaire=null;
            String tpsArret=null;
            String chOrgane=null;
            String perte = null;
            String activite = null;
            String machine = null;
            String cd = null;
            String co = null;
            String sd = null;
            String so = null;

            if (!((EditText)(findViewById(R.id.editHeureTermineNvelle))).getText().toString().isEmpty() &&
                    !((EditText)(findViewById(R.id.editDateTermineNvelle))).getText().toString().isEmpty()){
                dh_fin = ((EditText)(findViewById(R.id.editDateTermineNvelle))).getText().toString() +" "+((EditText)(findViewById(R.id.editHeureTermineNvelle))).getText().toString();
            }

            if (!((EditText)(findViewById(R.id.editComNvelle))).getText().toString().isEmpty()){
                commentaire = ((EditText)(findViewById(R.id.editComNvelle))).getText().toString();
            }

            if (!((EditText)(findViewById(R.id.editDureeArretNvelle))).getText().toString().isEmpty()){
                tpsArret = ((EditText)(findViewById(R.id.editDureeArretNvelle))).getText().toString();
            }

            if (((CheckBox)(findViewById(R.id.cbChangOrganeNvelle))).isChecked()){
                chOrgane = "1";
            }else{
                chOrgane="0";
            }

            if (((CheckBox)(findViewById(R.id.cbPertesNvelle))).isChecked()){
                perte = "1";
            }else{
                perte="0";
            }

            if (!((Spinner)(findViewById(R.id.spinnerActiviteNvelle))).getSelectedItem().toString().isEmpty()){
                activite = ((Spinner)(findViewById(R.id.spinnerActiviteNvelle))).getSelectedItem().toString();
            }

            if (!((Spinner)(findViewById(R.id.spinnerMachineNvelle))).getSelectedItem().toString().isEmpty()){
                machine = ((Spinner)(findViewById(R.id.spinnerMachineNvelle))).getSelectedItem().toString();
            }

            if (!((Spinner)(findViewById(R.id.spinnerCDefautNvelle))).getSelectedItem().toString().isEmpty()){
                cd = ((CauseDefaut)((Spinner)(findViewById(R.id.spinnerCDefautNvelle))).getSelectedItem()).getCode();
            }

            if (!((Spinner)(findViewById(R.id.spinnerCObjetNvelle))).getSelectedItem().toString().isEmpty()){
                co = ((CauseObjet)((Spinner)(findViewById(R.id.spinnerCObjetNvelle))).getSelectedItem()).getCode();
            }

            if (!((Spinner)(findViewById(R.id.spinnerSDefautNvelle))).getSelectedItem().toString().isEmpty()){
                sd = ((SymptomeDefaut)((Spinner)(findViewById(R.id.spinnerSDefautNvelle))).getSelectedItem()).getCode();
            }

            if (!((Spinner)(findViewById(R.id.spinnerSObjetNvelle))).getSelectedItem().toString().isEmpty()){
                so = ((SymptomeObjet)((Spinner)(findViewById(R.id.spinnerSObjetNvelle))).getSelectedItem()).getCode();
            }

            DaoIntervention.getInstance().addIntervention(dh_deb,dh_fin,commentaire,tpsArret,chOrgane,perte,activite,machine,cd,co,sd,so,new DelegateAsyncTask() {
                @Override
                public void whenWSConnexionIsTerminated(Object result) {
                    if((boolean) result){
                        Toast.makeText(NouvelleIntervention.this,"Intervention ajoutée",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(NouvelleIntervention.this,"ERREUR",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            for (IntervenantTps inter : listIntervenantTps){
                DaoIntervention.getInstance().addInterventionInter(inter.getIntervenant().getMail(), inter.getTps(), new DelegateAsyncTask() {
                    @Override
                    public void whenWSConnexionIsTerminated(Object result) {
                        if((boolean) result){
                            setResult(RESULT_OK);
                            finish();
                            Toast.makeText(NouvelleIntervention.this,"Tout ajouté",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(NouvelleIntervention.this,"ERREUR",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}