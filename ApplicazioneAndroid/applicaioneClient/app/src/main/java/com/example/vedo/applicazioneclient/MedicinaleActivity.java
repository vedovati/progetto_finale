package com.example.vedo.applicazioneclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vedo.applicazioneclient.Class.AdapterMedicinale;
import com.example.vedo.applicazioneclient.Class.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MedicinaleActivity extends AppCompatActivity {

    TextView nome, descrizione, fogliettoIllustrativo;
    String nomeMedicina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicinale);
        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        nomeMedicina = bundle.getString("nomeMedicina");

        nome = (TextView) findViewById(R.id.nome);
        descrizione = (TextView) findViewById(R.id.descrizione);
        fogliettoIllustrativo = (TextView) findViewById(R.id.foglietto_illustrativo);

        getMedicinale();

    }

    private void getMedicinale() {

        final String funz = "getMedicinaleSpecifico";

        StringRequest stringRequest = new StringRequest (Request.Method.POST, Api.API_URL, // ip/url server php
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // ritorno dati
                        JSONObject jsonObject = null;
                        try { // scompongo dati
                            jsonObject = new JSONObject(response);
                            JSONArray medicinale = jsonObject.getJSONArray("dati");
                            nome.setText(medicinale.getJSONObject(0).get("nome").toString());
                            descrizione.setText(medicinale.getJSONObject(0).get("descrizione").toString());
                            fogliettoIllustrativo.setText(medicinale.getJSONObject(0).get("foglietto_illustrativo").toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MedicinaleActivity.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MedicinaleActivity.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() { // parametri da passare al server
                Map<String, String> params;
                params = new HashMap<>();

                params.put("funz", funz);
                params.put("nome", nomeMedicina);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this); // dichiarazione classe ritorno dati
        requestQueue.add(stringRequest); // esegue tutto

    }
}
