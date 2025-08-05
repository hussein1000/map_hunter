package com.map.hunter.helper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Base64;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static android.content.Context.MODE_PRIVATE;
import com.map.hunter.MainActivity;
import com.map.hunter.R;

public class Helper {

    public static int LOCATION_REFRESH_TIME = 60000;
    public static int LOCATION_REFRESH_DISTANCE = 100;

    public static String direction_map = "https://maps.openrouteservice.org";

    public static String base_contrib_map = "https://www.openstreetmap.org";

    public static String getTitle(Context context, String url) {
        String title = context.getString(R.string.app_name);
        return title;
    }

    public static String APP_SHARED_PREF = "app_shared_prefs";
    public static String LAST_USED_MAP = "last_used_map";
    public static String LAST_LOCATION = "last_location";
    public static String SET_DEFAULT_LOCALE_NEW = "set_default_locale_new";

    private static final Pattern pattern1 =  Pattern.compile("/#(map=)?(\\d+)/([+-]?([0-9]*[.])?[0-9]+)/([+-]?([0-9]*[.])?[0-9]+)");
    private static final Pattern pattern2 =  Pattern.compile("/#/\\?c=(([+-]?([0-9]*)[.])?[0-9]+),([+-]?([0-9]*[.])?[0-9]+)&z=([0-9]*)");
    private static final Pattern pattern3 =  Pattern.compile("/@(([+-]?([0-9]*)[.])?[0-9]+),([+-]?([0-9]*[.])?[0-9]+),([0-9]*)");
    private static final Pattern patternZoom =  Pattern.compile("zoom=([0-9]*)");
    private static final Pattern patternLat =  Pattern.compile("lat=([+-]?([0-9]*[.])?[0-9]+)");
    private static final Pattern patternLon =  Pattern.compile("lon=([+-]?([0-9]*[.])?[0-9]+)");

/*    public static ArrayList<Locale> SUPPORTED_LOCALES = new ArrayList<Locale>() {{
        add(new Locale("en"));
        add(new Locale("fr"));
        add(new Locale("de"));
        add(new Locale("eu"));
        add(new Locale("oc"));
        add(new Locale("es"));
        add(new Locale("pt"));
        add(new Locale("nl"));
        add(new Locale("hu"));
        add(new Locale("sv"));
        add(new Locale("zh-TW"));
    }};*/
    

    public static void recordLocationFromUrl(Context context, String url){
        SharedPreferences sharedpref = context.getSharedPreferences(Helper.APP_SHARED_PREF, MODE_PRIVATE);
        if( url != null){
            String zoom = null;
            String lat = null;
            String lon = null;
            if( url.contains("zoom") && url.contains("lat") && url.contains("long")){
                Matcher matcher = patternZoom.matcher(url);
                if( matcher.find()) {
                    zoom = matcher.group(1);
                }
                matcher = patternLat.matcher(url);
                if( matcher.find()) {
                    lat = matcher.group(1);
                }
                matcher = patternLon.matcher(url);
                if( matcher.find()) {
                    lon = matcher.group(1);
                }
            }else {
                Matcher matcher = pattern1.matcher(url);
                if( matcher.find()) {
/*                    if( url.contains(historic_map)){
                        lat = matcher.group(5);
                        lon = matcher.group(3);
                    }else{*/
                    lat = matcher.group(3);
                    lon = matcher.group(5);
//                    }
                    zoom = matcher.group(2);
                }else{
                    matcher = pattern2.matcher(url);
                    if( matcher.find()) {
                        lat = matcher.group(1);
                        lon = matcher.group(4);
                        zoom = matcher.group(6);
                    }
                }
            }

            if( zoom != null && lat != null && lon != null){
                SharedPreferences.Editor editor = sharedpref.edit();
                editor.putString(Helper.LAST_LOCATION,lon + "," + lat+","+zoom);
                editor.apply();
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static void initializeWebview(Context context, WebView webView) {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setGeolocationDatabasePath(context.getFilesDir().getPath() );
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
/*        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        }*/
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
/*        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }



    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void injectScriptFile(Activity activity, WebView view, String scriptFile) {
        InputStream input;
        try {
            input = activity.getAssets().open(scriptFile);
            byte[] buffer = new byte[input.available()];
            //noinspection ResultOfMethodCallIgnored
            input.read(buffer);
            input.close();

            // String-ify the script byte-array using BASE64 encoding !!!
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            view.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var script = document.createElement('script');" +
                    "script.type = 'text/javascript';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "script.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(script)" +
                    "})()");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @SuppressWarnings({"ResultOfMethodCallIgnored", "unused", "RedundantSuppression"})
    public static void injectCSS(Activity activity, WebView view, String cssFile) {
        try {
            InputStream inputStream = activity.getAssets().open(cssFile);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            view.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
