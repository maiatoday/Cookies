package net.maiatoday.cookies;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    private static final String EXTRA_COOKIE = "cookie";
    private static final String EXTRA_COOKIE_VALUE = "value";
    private static final String EXTRA_URL = "url";
    private WebView mWebView;

    //http://stackoverflow.com/questions/2566485/webview-and-cookies-on-android

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cookie = getIntent().getStringExtra(EXTRA_COOKIE);
        cookie="login_82e5d2c56bdd0811318f0cf078b78bfc";
        String cookieValue = getIntent().getStringExtra(EXTRA_COOKIE_VALUE);
        cookieValue = "fac3569f376055696d251af98849f81cd864565c";
        String url = getIntent().getStringExtra(EXTRA_URL);

        setContentView(R.layout.activity_web_view);
        mWebView = (WebView) findViewById(R.id.activity_web_view);
        mWebView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String cookieString = cookie+"="+cookieValue+"; Expires=Tue, 31 May 2016 18:45:20 GMT";

        // use cookies to remember a logged in status
        final CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            final CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
            cookieManager.removeAllCookie();
            cookieManager.setAcceptCookie(true);
//            cookieManager.setCookie(cookie, cookieValue);
            cookieManager.setCookie("http://10.0.0.165", cookieString);
            cookieSyncManager.sync();
        } else {
            final String finalCookie = cookie;
            final String finalCookieValue = cookieValue;
//            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
//                @Override
//                public void onReceiveValue(Boolean value) {
//                    Log.d(TAG, "onReceiveValue: ");
//
//                    cookieManager.setAcceptThirdPartyCookies(mWebView, true);
//                    cookieManager.setCookie(finalCookie, finalCookieValue);
//                    cookieManager.flush();
//                }
//            });
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
            cookieManager.setCookie("https://bqa-ke.oam.cool", cookieString);
//            cookieManager.setCookie(cookie, cookieValue);
            cookieManager.flush();
        }
        Map<String, String> abc = new HashMap<String, String>();
        abc.put(cookie, cookieValue);
        mWebView.loadUrl(url, abc);
    }

    public static Intent getIntent(Context context, String cookie, String value, String url) {

        Intent i = new Intent(context, WebViewActivity.class);
        i.putExtra(EXTRA_COOKIE, cookie);
        i.putExtra(EXTRA_COOKIE_VALUE, value);
        i.putExtra(EXTRA_URL, url);

        return i;
    }
}
