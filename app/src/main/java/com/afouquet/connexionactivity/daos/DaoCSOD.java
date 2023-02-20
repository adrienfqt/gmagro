package com.afouquet.connexionactivity.daos;

import com.afouquet.connexionactivity.bean.CauseDefaut;
import com.afouquet.connexionactivity.bean.Intervention;
import com.afouquet.connexionactivity.net.WSConnexionHTTPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaoCSOD {

    private List<CauseDefaut> causesDefauts = new ArrayList<CauseDefaut>();
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


}
