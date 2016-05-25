package net.maiatoday.cookies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    private static final String EXTRA_COOKIE = "cookie";
    private static final String EXTRA_COOKIE_VALUE = "value";
    private static final String EXTRA_URL = "url";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWebView = (WebView) findViewById(R.id.activity_web_view);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = getIntent().getStringExtra(EXTRA_URL);
        mWebView.loadUrl(url);

    }

    public static Intent getIntent(Context context, String cookie, String value, String url) {

        Intent i = new Intent(context, WebViewActivity.class);
        i.putExtra(EXTRA_COOKIE, cookie);
        i.putExtra(EXTRA_COOKIE_VALUE, value);
        i.putExtra(EXTRA_URL, url);

        return i;
    }
}
