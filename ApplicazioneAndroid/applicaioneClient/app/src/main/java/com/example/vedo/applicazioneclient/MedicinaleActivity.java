package com.example.vedo.applicazioneclient;

import android.graphics.drawable.BitmapDrawable;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import java.util.Locale;
import java.util.Map;

public class MedicinaleActivity extends AppCompatActivity {

    TextView nome, descrizione, fogliettoIllustrativo;
    ImageView img;
    ImageView audioNome, audioDescrizione, audioFoglietto;
    String nomeMedicina;

    TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicinale);
        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        nomeMedicina = bundle.getString("nomeMedicina");

        // ottenere viste
        nome = (TextView) findViewById(R.id.nome);
        descrizione = (TextView) findViewById(R.id.descrizione);
        fogliettoIllustrativo = (TextView) findViewById(R.id.foglietto_illustrativo);
        img = (ImageView) findViewById(R.id.img);

        audioNome = (ImageView) findViewById(R.id.audioNome);
        audioDescrizione = (ImageView) findViewById(R.id.audioDescrizione);
        audioFoglietto = (ImageView) findViewById(R.id.audioFoglietto);

        mTTS = new TextToSpeech(MedicinaleActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ITALIAN);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        audioNome.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        audioNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(nome.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        audioDescrizione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(descrizione.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        audioFoglietto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(fogliettoIllustrativo.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

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
                            img.setImageResource(Api.getResId(medicinale.getJSONObject(0).get("img_path").toString(), R.drawable.class));

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
