package com.colis.colis_mobile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomActivity extends AppCompatActivity {


    TextView identifierText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        identifierText = findViewById(R.id.identifyTextId);

        identifierText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(WelcomActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }
}