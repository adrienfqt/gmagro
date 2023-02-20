package com.afouquet.connexionactivity.daos;

import com.afouquet.connexionactivity.bean.Intervention;
import com.afouquet.connexionactivity.net.WSConnexionHTTPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DaoIntervention {
    private static DaoIntervention instance = null;
    private List<Intervention> interventions = new ArrayList<Intervention>();

    public List<Intervention> getInterventionLocales() {
        return interventions;
    }

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    SimpleDateFormat formatterHeure = new SimpleDateFormat("HH:mm", Locale.FRANCE);

    private DaoIntervention() {

    }

    public static DaoIntervention getInstance() {
        if (instance == null) {
            instance = new DaoIntervention();
        }
        return instance;
    }

    public void getIntervention(DelegateAsyncTask delegate) {
        String url = "action=getInterventions";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourGetIntervention(s, delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetIntervention(String s, DelegateAsyncTask delegate) {
        try {
            interventions.clear();
            JSONObject jo = new JSONObject(s);
            if (jo.getBoolean("success")) {
                JSONArray ja = jo.getJSONArray("interventions");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject interventionJson = ja.getJSONObject(i);

                    int id = interventionJson.getInt("id");
                    Date dhDeb = formatter.parse(interventionJson.getString("dh_debut"));
                    Date dhFin = formatter.parse(interventionJson.getString("dh_fin"));
                    String commentaire = interventionJson.getString("commentaire");
                    Date tempsArret = formatterHeure.parse(interventionJson.getString("temps_arret"));
                    boolean organe = interventionJson.getInt("changement_organe")==1;
                    boolean perte = interventionJson.getInt("perte")==1;
                    Date creation = formatter.parse(interventionJson.getString("dh_creation"));
                    Date dMaj = formatter.parse(interventionJson.getString("dh_derniere_maj"));
                    String mail = interventionJson.getString("intervenant_id");
                    String code = interventionJson.getString("activite_code");
                    String codeMachine = interventionJson.getString("machine_code");
                    String causeDefaut = interventionJson.getString("cause_defaut_code");
                    String causeObjet = interventionJson.getString("cause_objet_code");
                    String symptomeDefaut = interventionJson.getString("symptome_defaut_code");
                    String symptomeObjet = interventionJson.getString("symptome_objet_code");
                    Intervention interv = new Intervention(id, dhDeb, dhFin, commentaire, tempsArret, organe, perte, creation, dMaj, mail, code, codeMachine, causeDefaut, causeObjet, symptomeDefaut, symptomeObjet);
                    interventions.add(interv);
                }
                delegate.whenWSConnexionIsTerminated(interventions.isEmpty());
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }

    }
}
