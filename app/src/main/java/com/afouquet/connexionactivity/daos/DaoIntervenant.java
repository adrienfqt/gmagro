package com.afouquet.connexionactivity.daos;

import com.afouquet.connexionactivity.bean.CauseDefaut;
import com.afouquet.connexionactivity.bean.CauseObjet;
import com.afouquet.connexionactivity.bean.Intervenant;
import com.afouquet.connexionactivity.net.WSConnexionHTTPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DaoIntervenant {
    private static DaoIntervenant instance = null;
    private List<Intervenant> intervenants=new ArrayList<>();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public  List<Intervenant> getIntervSiteLocals(){
        return intervenants;
    }
    private DaoIntervenant() {

    }

    public static DaoIntervenant getInstance() {
        if (instance == null) {
            instance = new DaoIntervenant();
        }
        return instance;
    }

    public void connexionMembre(String login, String passwd, DelegateAsyncTask delegate) {
        String url = "action=connexion" +
                "&login=" + login +
                "&mdp=" + passwd;
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourConnexion(s, delegate, passwd);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourConnexion(String s,DelegateAsyncTask delegate, String passwd){
        try {
            JSONObject jo = new JSONObject(s);
            delegate.whenWSConnexionIsTerminated(jo.getBoolean("success"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deconnexionMembre(DelegateAsyncTask delegate) {
        String url = "action=deconnexion";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourDeconnexion(s,delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourDeconnexion(String s,DelegateAsyncTask delegate){
        try {
            JSONObject jo = new JSONObject(s);
            delegate.whenWSConnexionIsTerminated(jo.getBoolean("success"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getIntervSite(DelegateAsyncTask delegate) {
        String url = "action=getIntervSite";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourgetIntervSite(s,delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourgetIntervSite(String s,DelegateAsyncTask delegate){
        try {
            intervenants.clear();
            JSONObject jo = new JSONObject(s);
            if(jo.getBoolean("success")){
                JSONArray ja = jo.getJSONArray("intervenants_site");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject coJson = ja.getJSONObject(i);
                    String nom = coJson.getString("nom");
                    String prenom = coJson.getString("prenom");
                    Intervenant in = new Intervenant(nom,prenom);
                    intervenants.add(in);
                }
            }
            delegate.whenWSConnexionIsTerminated(intervenants.isEmpty());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
