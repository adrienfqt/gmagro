package com.afouquet.connexionactivity.daos;

import com.afouquet.connexionactivity.bean.CauseDefaut;
import com.afouquet.connexionactivity.bean.CauseObjet;
import com.afouquet.connexionactivity.bean.Intervention;
import com.afouquet.connexionactivity.bean.SymptomeDefaut;
import com.afouquet.connexionactivity.bean.SymptomeObjet;
import com.afouquet.connexionactivity.net.WSConnexionHTTPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaoCSOD {

    private List<CauseDefaut> causesDefauts = new ArrayList<CauseDefaut>();
    private List<CauseObjet> causeObjets = new ArrayList<CauseObjet>();
    private List<SymptomeDefaut> symptomeDefauts = new ArrayList<SymptomeDefaut>();
    private List<SymptomeObjet> symptomeObjets = new ArrayList<>();
    private static DaoCSOD instance = null;
    private DaoCSOD(){}

    public static DaoCSOD getInstance() {
        if (instance == null) {
            instance = new DaoCSOD();
        }
        return instance;
    }

    public  List<CauseDefaut> getCausesDefautsLocales(){
        return causesDefauts;
    }
    public  List<CauseObjet> getCausesObjetsLocales(){
        return causeObjets;
    }
    public  List<SymptomeDefaut> getSymptomeDefautsLocales(){
        return symptomeDefauts;
    }
    public  List<SymptomeObjet> getSymptomeObjetsLocales(){
        return symptomeObjets;
    }


    public void getCd(DelegateAsyncTask delegate) {
        String url = "action=getCd";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourgetCd(s, delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourgetCd(String s, DelegateAsyncTask delegate) {

        try {
            causesDefauts.clear();
            JSONObject jo = new JSONObject(s);
            if(jo.getBoolean("success")){
                JSONArray ja = jo.getJSONArray("causes_défaults");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject cdJson = ja.getJSONObject(i);
                    String code = cdJson.getString("code");
                    String libelle = cdJson.getString("libelle");
                    String uai = cdJson.getString("site_uai");
                    CauseDefaut cd = new CauseDefaut(code,libelle,uai);
                    causesDefauts.add(cd);
                }
            }
            delegate.whenWSConnexionIsTerminated(causesDefauts.isEmpty());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getCo(DelegateAsyncTask delegate) {
        String url = "action=getCo";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourgetCo(s, delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourgetCo(String s, DelegateAsyncTask delegate) {

        try {
            causeObjets.clear();
            JSONObject jo = new JSONObject(s);
            if(jo.getBoolean("success")){
                JSONArray ja = jo.getJSONArray("causes_objets");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject coJson = ja.getJSONObject(i);
                    String code = coJson.getString("code");
                    String libelle = coJson.getString("libelle");
                    String uai = coJson.getString("site_uai");
                    CauseObjet co= new CauseObjet(code,libelle,uai);
                    causeObjets.add(co);
                }
            }
            delegate.whenWSConnexionIsTerminated(causesDefauts.isEmpty());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getSd(DelegateAsyncTask delegate) {
        String url = "action=getSd";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourgetSd(s, delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourgetSd(String s, DelegateAsyncTask delegate) {

        try {
            symptomeDefauts.clear();
            JSONObject jo = new JSONObject(s);
            if(jo.getBoolean("success")){
                JSONArray ja = jo.getJSONArray("symptomes_defauts");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject sDJson = ja.getJSONObject(i);
                    String code = sDJson.getString("code");
                    String libelle = sDJson.getString("libelle");
                    String uai = sDJson.getString("site_uai");
                    SymptomeDefaut sd = new SymptomeDefaut(code,libelle,uai);
                    symptomeDefauts.add(sd);
                }
            }
            delegate.whenWSConnexionIsTerminated(causesDefauts.isEmpty());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getSo(DelegateAsyncTask delegate) {
        String url = "action=getSo";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourgetSo(s, delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourgetSo(String s, DelegateAsyncTask delegate) {

        try {
            symptomeObjets.clear();
            JSONObject jo = new JSONObject(s);
            if(jo.getBoolean("success")){
                JSONArray ja = jo.getJSONArray("symptomes_objets");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject sDJson = ja.getJSONObject(i);
                    String code = sDJson.getString("code");
                    String libelle = sDJson.getString("libelle");
                    String uai = sDJson.getString("site_uai");
                    SymptomeObjet so = new SymptomeObjet(code,libelle,uai);
                    symptomeObjets.add(so);
                }
            }
            delegate.whenWSConnexionIsTerminated(causesDefauts.isEmpty());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
