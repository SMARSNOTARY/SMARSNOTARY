package com.smarsnotary.notepeyim.smarsnotary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Map1Activity extends Activity {
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.smarsnotary.notepeyim.smarsnotary.R.layout.activity_map1);
        myWebView = findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.webview);
        // Configure related browser settings
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Configure the client to use when opening URLs
        myWebView.setWebViewClient(new WebViewClient());
        // Load the initial URL
        myWebView.loadUrl("http://maps.google.com/maps?saddr=18.573129,-72.305732&daddr=18.571701,-72.307428");
    }
}

