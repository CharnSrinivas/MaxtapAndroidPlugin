package com.example.maxtap_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ContentId extends AppCompatActivity {
    EditText content_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_id);
        content_id = findViewById(R.id.content_id);
        findViewById(R.id.start_btn).setOnClickListener((View btn)->{
            Intent intent = new Intent(ContentId.this,BrightcovePlayerIntegration.class);
            intent.putExtra("content_id",content_id.getText().toString());
            startActivity(intent);
        });
    }
}
