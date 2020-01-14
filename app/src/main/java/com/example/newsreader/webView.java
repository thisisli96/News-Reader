package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().getJavaScriptEnabled(); // untuk memberi akses kita bisa menggunaka javascript

        webView.setWebViewClient(new WebViewClient());
        // webView.loadUrl("https://www.google.com");
        webView.loadData("<html><body><h1>Hello WORLD</h1><p> this is cool website</p></body></html>","text/html","UTP-8");

    }
}
