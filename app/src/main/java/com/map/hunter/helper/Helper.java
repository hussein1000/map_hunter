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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

import com.map.hunter.R;

public class Helper {

    public static int LOCATION_REFRESH_TIME = 60000;
    public static int LOCATION_REFRESH_DISTANCE = 100;

    // Map URLs
    public static String direction_map = "https://maps.openrouteservice.org";
    public static String cyclo_map = "https://www.cyclosm.org";
    public static String topo_map = "https://opentopomap.org";
    public static String park_map = "https://www.freeparking.world";
    public static String fuel_map = "https://openfuelmap.net";
    public static String railway_map = "https://www.openrailwaymap.org/mobile.php";
    public static String ben_map = "https://benmaps.fr";
    public static String travic_map = "https://mobility.portal.geops.io";
    public static String resto_map = "https://openvegemap.netlib.re";
    public static String wheel_map = "https://wheelmap.org";
    public static String beer_map = "https://openbeermap.github.io";
    public static String solar_map = "https://opensolarmap.org/";
    public static String weather_map = "https://openweathermap.org/weathermap?basemap=map&cities=true&layer=clouds";
    public static String qwant_map = "https://www.qwant.com/maps/";
    public static String openrecycle_map = "https://openrecyclemap.org/map";
    public static String cartovrac_map = "https://cartovrac.fr";
    public static String gribrouillon = "https://gribrouillon.fr/";
    public static String queer_map = "https://map.qiekub.org/";
    public static String water_map = "https://water-map.org/";
    public static String snow_map = "http://opensnowmap.org/";
    public static String historic_map = "https://histosm.org/";
    public static String french_breweries = "http://sp3r4z.fr/breweries/";

    // Regional maps
    public static String breton_map = "https://kartenn.openstreetmap.bzh";
    public static String occitan_map = "https://tile.openstreetmap.fr/?zoom=9&lat=43.82067&lon=1.235&layers=0000000B0FFFFFF";
    public static String basque_map = "https://tile.openstreetmap.fr/?zoom=11&lat=43.36289&lon=-1.45081&layers=00000000BFFFFFF";

    public static String APP_SHARED_PREF = "app_shared_prefs";
    public static String LAST_USED_MAP = "last_used_map";
    public static String LAST_LOCATION = "last_location";

    private static final Pattern pattern1 = Pattern.compile("/#(map=)?(\\d+)/([+-]?([0-9]*[.])?[0-9]+)/([+-]?([0-9]*[.])?[0-9]+)");
    private static final Pattern pattern2 = Pattern.compile("/#/\\?c=(([+-]?([0-9]*)[.])?[0-9]+),([+-]?([0-9]*[.])?[0-9]+)&z=([0-9]*)");
    private static final Pattern patternZoom = Pattern.compile("zoom=([0-9]*)");
    private static final Pattern patternLat = Pattern.compile("lat=([+-]?([0-9]*[.])?[0-9]+)");
    private static final Pattern patternLon = Pattern.compile("lon=([+-]?([0-9]*[.])?[0-9]+)");

    public static String getTitle(Context context, String url) {
        String title = "Map Hunter";
        if (url.contains(direction_map)) {
            title = context.getString(R.string.itinerary);
        } else if (url.contains(cyclo_map)) {
            title = context.getString(R.string.cycle_paths);
        } else if (url.contains(topo_map)) {
            title = context.getString(R.string.topographic);
        } else if (url.contains(park_map)) {
            title = context.getString(R.string.free_parkings);
        } else if (url.contains(fuel_map)) {
            title = context.getString(R.string.fuel);
        } else if (url.contains(railway_map)) {
            title = context.getString(R.string.railway);
        } else if (url.contains(ben_map)) {
            title = context.getString(R.string.benmaps);
        } else if (url.contains(travic_map)) {
            title = context.getString(R.string.transit);
        } else if (url.contains(wheel_map)) {
            title = context.getString(R.string.accessible_places);
        } else if (url.contains(beer_map)) {
            title = context.getString(R.string.beer);
        } else if (url.contains(solar_map)) {
            title = context.getString(R.string.solar_panel);
        } else if (url.contains(weather_map)) {
            title = context.getString(R.string.weather);
        } else if (url.contains(qwant_map)) {
            title = context.getString(R.string.qwant_map);
        } else if (url.contains(openrecycle_map)) {
            title = context.getString(R.string.recycle);
        } else if (url.contains(queer_map)) {
            title = context.getString(R.string.queer_map);
        } else if (url.contains(water_map)) {
            title = context.getString(R.string.water_map);
        } else if (url.contains(snow_map)) {
            title = context.getString(R.string.ski_snow);
        } else if  (url.contains(cartovrac_map)) {
            title = context.getString(R.string.reduce_waste);
        } else if (url.contains(gribrouillon)) {
            title = context.getString(R.string.colorize);
        } else if (url.contains(resto_map)) {
            title = context.getString(R.string.vegetarian_restaurants);
        } else if (url.contains(historic_map)) {
            title = context.getString(R.string.historic_places);
        }
        return title;
    }

    public static void recordLocationFromUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences(APP_SHARED_PREF, MODE_PRIVATE);
        if (url != null) {
            String zoom = null;
            String lat = null;
            String lon = null;

            if (url.contains("zoom") && url.contains("lat") && url.contains("long")) {
                Matcher matcher = patternZoom.matcher(url);
                if (matcher.find()) zoom = matcher.group(1);
                matcher = patternLat.matcher(url);
                if (matcher.find()) lat = matcher.group(1);
                matcher = patternLon.matcher(url);
                if (matcher.find()) lon = matcher.group(1);
            } else {
                Matcher matcher = pattern1.matcher(url);
                if (matcher.find()) {
                    if (url.contains(historic_map)) {
                        lat = matcher.group(5);
                        lon = matcher.group(3);
                    } else {
                        lat = matcher.group(3);
                        lon = matcher.group(5);
                    }
                    zoom = matcher.group(2);
                } else {
                    matcher = pattern2.matcher(url);
                    if (matcher.find()) {
                        lat = matcher.group(1);
                        lon = matcher.group(4);
                        zoom = matcher.group(6);
                    }
                }
            }

            if (zoom != null && lat != null && lon != null) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(LAST_LOCATION, lon + "," + lat + "," + zoom);
                editor.apply();
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static void initializeWebview(Context context, WebView webView) {
        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(true);
        settings.setAllowContentAccess(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setSupportMultipleWindows(false);
        settings.setGeolocationDatabasePath(context.getFilesDir().getPath());
        settings.setGeolocationEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
webView.getSettings().setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            settings.setPluginState(WebSettings.PluginState.ON);
        }

        settings.setMediaPlaybackRequiresUserGesture(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.setBackgroundColor(Color.TRANSPARENT);
    }

    public static void injectScriptFile(Activity activity, WebView view, String scriptFile) {
        try {
            InputStream input = activity.getAssets().open(scriptFile);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();

            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            view.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var script = document.createElement('script');" +
                    "script.type = 'text/javascript';" +
                    "script.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(script)" +
                    "})()");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}