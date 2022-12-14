package com.afouquet.connexionactivity.daos;

import com.afouquet.connexionactivity.net.WSConnexionHTTPS;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DaoIntervenant {
    private static DaoIntervenant instance = null;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

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

}
