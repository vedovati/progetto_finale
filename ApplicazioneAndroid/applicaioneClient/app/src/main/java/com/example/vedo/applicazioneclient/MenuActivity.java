package com.example.vedo.applicazioneclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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

import static java.util.logging.Logger.global;

public class MenuActivity extends AppCompatActivity {

    //private ScrollView menuScrollView;
    //private LinearLayout cardLayout;
    private RecyclerView cardContainer;

    private AdapterMedicinale adpterMedicinale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //menuScrollView = (ScrollView) findViewById(R.id.menuScrollView);
        //cardLayout = (LinearLayout) findViewById(R.id.cardLayout);

        cardContainer = (RecyclerView) findViewById(R.id.cardContainer);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        cardContainer.setLayoutManager(layoutManager);

        getMedicinali();

    }

    private void getMedicinali() {

        final String funz = "getMedicinali";

        StringRequest stringRequest = new StringRequest (Request.Method.POST, Api.API_URL, // ip/url server php
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // ritorno dati
                        JSONObject jsonObject = null;
                        try { // scompongo dati
                            jsonObject = new JSONObject(response);
                            JSONArray medicinali = jsonObject.getJSONArray("dati");

                            adpterMedicinale = new AdapterMedicinale(medicinali, MenuActivity.this);
                            cardContainer.setAdapter(adpterMedicinale);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MenuActivity.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MenuActivity.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() { // parametri da passare al server
                Map<String, String> params;
                params = new HashMap<>();

                params.put("funz", funz);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this); // dichiarazione classe ritorno dati
        requestQueue.add(stringRequest); // esegue tutto

    }

}
