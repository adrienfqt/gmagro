package com.afouquet.gmagroAndroid.daos;

import com.afouquet.gmagroAndroid.bean.Activite;
import com.afouquet.gmagroAndroid.net.WSConnexionHTTPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DaoActivite {
    private static DaoActivite instance = null;

    private List<Activite>activites = new ArrayList<Activite>();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public List<Activite> getActivitesLocales(){
        return activites;
    }
    private DaoActivite() {

    }

    public static DaoActivite getInstance() {
        if (instance == null) {
            instance = new DaoActivite();
        }
        return instance;
    }

    public void getActivites(DelegateAsyncTask delegate) {
        String url = "action=getActivites";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourGetActivites(s, delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetActivites(String s,DelegateAsyncTask delegate){
        activites.clear();
        try {
            JSONObject jo = new JSONObject(s);
            if(jo.getBoolean("success")){
                JSONArray ja = jo.getJSONArray("activites");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject activiteJson = ja.getJSONObject(i);
                    String code = activiteJson.getString("code");
                    String libelle = activiteJson.getString("libelle");
                    Activite a = new Activite(code,libelle);
                    activites.add(a);
                }
            }
            delegate.whenWSConnexionIsTerminated(activites.isEmpty());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
