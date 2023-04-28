package com.afouquet.gmagroAndroid.daos;

import com.afouquet.gmagroAndroid.bean.Intervenant;
import com.afouquet.gmagroAndroid.net.WSConnexionHTTPS;

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
    private List<String> lesTemps=new ArrayList<>();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public  List<Intervenant> getIntervSiteLocals(){
        return intervenants;
    }

    public List<String> getLesTempsLocals() {return lesTemps;}

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
                    String mail = coJson.getString("mail");
                    Intervenant in = new Intervenant(nom,prenom,mail);
                    intervenants.add(in);
                }
            }
            delegate.whenWSConnexionIsTerminated(intervenants.isEmpty());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getTemps(){
        //"0:15","0:30","0:45","1:00","1:15","1:30","1:45","2:00","2:15","2:30","2:45","3:00","3:15","3:30","3:45","4:00"
        return 1;
    }

}
