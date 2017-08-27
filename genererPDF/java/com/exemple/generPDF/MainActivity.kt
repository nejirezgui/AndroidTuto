package com.example.genererpdf

import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {


    internal var adresse = "http://10.0.2.2/tutofpdf/genererPDF.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_genPDF.setOnClickListener(View.OnClickListener {


            if (!txt_nom.text.toString().equals("") && !txt_prenom.text.toString().equals("") && !txt_adr.text.toString().equals(""))
                webConnection().execute(txt_nom.text.toString(), txt_prenom.text.toString(), txt_adr.text.toString())
            else
                Toast.makeText(this,"Verifier vos donnnes ", Toast.LENGTH_LONG).show()
        })

    }

    internal inner class webConnection : AsyncTask<String, Void, String>() {
        lateinit var pb: ProgressDialog
        var reponse = 0

        override fun onPreExecute() {
            super.onPreExecute()

            pb = ProgressDialog(this@MainActivity)
            pb.setMessage("Connexion au site web ")

            pb.show()
            //  cancel(false) ;
        }


        override fun doInBackground(vararg params: String): String? {


            val nom = params[0]
            val prenom = params[1]
            val adress = params[2]


            var resultat = ""

            try {


                val url = URL(adresse)  // construire  l url a partir de url de type string


                val conx = url.openConnection() as HttpURLConnection



                conx.requestMethod = "POST"  //methode get par defaut
                conx.doInput = true  // par defaut
                conx.doOutput = true

                val OS = conx.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(OS, "UTF-8"))
                val data = URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nom, "UTF-8") + "&" +
                        URLEncoder.encode("prenom", "UTF-8") + "=" + URLEncoder.encode(prenom, "UTF-8") + "&" +
                        URLEncoder.encode("adresse", "UTF-8") + "=" + URLEncoder.encode(adress, "UTF-8")
                bufferedWriter.write(data)
                bufferedWriter.flush()
                bufferedWriter.close()
                OS.close()
                conx.connect()





                reponse = conx.responseCode
                if (reponse == HttpsURLConnection.HTTP_OK)
                // code 200 ok
                // code 404 not found
                {
                    val `is` = conx.inputStream
                    // methode avec des streams bufferises
                    val br = BufferedReader(InputStreamReader(`is`))

                    var ligne: String? = ""
                    ligne = br.readLine()
                    while (ligne != null) {

                        resultat += ligne
                        ligne = br.readLine()
                    }
                    return resultat
                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            pb.dismiss()

            if (s == "OK") {
                Toast.makeText(this@MainActivity, "OK", Toast.LENGTH_LONG).show()

            } else
                Toast.makeText(this@MainActivity, "Erreur", Toast.LENGTH_LONG).show()


        }
    }
}
