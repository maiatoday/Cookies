package net.maiatoday.cookies;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    private static final String EXTRA_COOKIE = "cookie";
    private static final String EXTRA_COOKIE_VALUE = "value";
    private static final String EXTRA_URL = "url";
    private WebView mWebView;

    //http://stackoverflow.com/questions/2566485/webview-and-cookies-on-android

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cookie = getIntent().getStringExtra(EXTRA_COOKIE);
        String cookieValue = getIntent().getStringExtra(EXTRA_COOKIE_VALUE);
        String url = getIntent().getStringExtra(EXTRA_URL);

        setContentView(R.layout.activity_web_view);
        mWebView = (WebView) findViewById(R.id.activity_web_view);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // use cookies to remember a logged in status
        final CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            final CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
            cookieManager.removeAllCookie();
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie(cookie, cookieValue);
            cookieSyncManager.sync();
        } else {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {

                }
            });
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
            cookieManager.setCookie(cookie, cookieValue);
            cookieManager.flush();
        }
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
