package com.nejirezgui.connectionj2ee;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    String url_serv = "http://10.0.2.2:8080/ConnectionAndroid/servlet";

    TextView txt_somme;
    EditText ed_nbre1, ed_nbre2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        ed_nbre1 = (EditText) findViewById(R.id.ed_nbre1);
        ed_nbre2 = (EditText) findViewById(R.id.ed_nbre2);
        txt_somme = (TextView) findViewById(R.id.txt_somme);


    }

    public void Calculer(View v) {

        // ajouter le teste de validite de donnees des edittexts
        // ajouter aussi la disponobilite de reseau

        new Webservice().execute(url_serv, ed_nbre1.getText().toString(), ed_nbre2.getText().toString());


    }

    class Webservice extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            Toast.makeText(MainActivity.this, "Connexion au webservice ", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Integer doInBackground(String... params) {

            int somme = 0;

            try {
                URL url = new URL(params[0]);

                String par_nbre1 = params[1];
                String par_nbre2 = params[2];


                HttpURLConnection conx = (HttpURLConnection) url.openConnection();
                conx.setRequestMethod("GET");
                conx.setDoOutput(true);
                OutputStream os = conx.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
                writer.write("var1=" + par_nbre1 + "&" + "var2=" + par_nbre2);
                writer.flush();
                writer.close();
                conx.connect();


                int reponse = conx.getResponseCode();

                if (reponse == HttpsURLConnection.HTTP_OK) {


                    InputStream is = conx.getInputStream();

                    BufferedReader br = new BufferedReader(new InputStreamReader(is));

                    String ligne = "", result = "";

                    while ((ligne = br.readLine()) != null) {

                        result += ligne;

                    }


                    JSONObject jo = new JSONObject(result);
                    somme = Integer.parseInt(jo.getString("somme"));
                    return somme;

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;


        }

        protected void onPostExecute(Integer i) {

            txt_somme.setText("" + i);


        }
    }
}
