package com.example.vedo.applicazioneclient;

import android.content.Intent;
import android.graphics.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class CameraActivity extends AppCompatActivity {

    ImageView btnCamera;
    ImageView btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnMenu = (ImageView) findViewById(R.id.btnMenu);
        btnCamera = (ImageView) findViewById(R.id.btnCamera);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

    }
}
