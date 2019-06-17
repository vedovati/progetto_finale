package com.example.vedo.applicazioneclient;

import android.content.Intent;
import android.graphics.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.camerakit.CameraKitView;
import com.example.vedo.applicazioneclient.Class.AdapterMedicinale;
import com.example.vedo.applicazioneclient.Class.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CameraActivity extends AppCompatActivity {

    ImageView btnCamera;
    ImageView btnMenu;
    private CameraKitView cameraKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnMenu = (ImageView) findViewById(R.id.btnMenu);
        btnCamera = (ImageView) findViewById(R.id.btnCamera);
        cameraKitView = findViewById(R.id.camera);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // vai al menu
                Intent intent = new Intent(CameraActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, final byte[] photo) {
                        sendPhoto(photo);
                    }
                });
            }
        });

    }

    private void sendPhoto(final byte[] photo) {

        StringRequest stringRequest = new StringRequest (Request.Method.POST, "http://192.168.1.108:8081", // TODO cambiare ip/url server python
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // ritorno dati
                        JSONObject jsonObject = null;
                        try { // scompongo dati
                            jsonObject = new JSONObject(response);
                            JSONArray medicinali = jsonObject.getJSONArray("dati");

                            String nome = medicinali.getJSONObject(0).get("nome").toString();

                            if (nome != "") {
                                Intent intent = new Intent(CameraActivity.this, MedicinaleActivity.class);

                                //Create the bundle
                                Bundle bundle = new Bundle();

                                //Add your data to bundle
                                bundle.putString("nomeMedicina", nome);

                                //Add the bundle to the intent
                                intent.putExtras(bundle);

                                startActivity(intent);
                            } else {
                                Toast.makeText(CameraActivity.this, "nessun medicnale identificato", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CameraActivity.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CameraActivity.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() { // parametri da passare al server
                Map<String, String> params;
                params = new HashMap<>();

                byte[] photoEncoded = Base64.encode(photo, 0); // codifica la foto in base64

                params.put("photo", new String(photoEncoded));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this); // dichiarazione classe ritorno dati
        requestQueue.add(stringRequest); // esegue tutto

    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
