package com.example.asus.shopping;

import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView webView = findViewById(R.id.web);
        webView.loadUrl(getIntent().getStringExtra("url"));
    }
}
