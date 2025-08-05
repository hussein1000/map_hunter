package com.map.hunter.helper;
/* Copyright 2019 Thomas Schneider
 *
 * This file is a part of OpenMultiMaps
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * OpenMultiMaps is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with OpenMultiMaps; if not,
 * see <http://www.gnu.org/licenses>. */
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

    //URLs of maps
    // - Displacements
    public static String direction_map = "https://maps.openrouteservice.org";
    public static String cyclo_map = "https://www.cyclosm.org";
    public static String topo_map = "https://opentopomap.org";
    public static String park_map = "https://www.freeparking.world";
    public static String fuel_map = "https://openfuelmap.net";
    public static String railway_map = "https://www.openrailwaymap.org/mobile.php";
    public static String ben_map = "https://benmaps.fr";
    public static String travic_map = "https://mobility.portal.geops.io";

    // - Life-skills
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

    // - Hobbies
    public static String snow_map = "http://opensnowmap.org/";
    public static String historic_map = "https://histosm.org/";
    public static String french_breweries = "http://sp3r4z.fr/breweries/";

    // - Regional
    public static String breton_map = "https://kartenn.openstreetmap.bzh";
    public static String occitan_map = "https://tile.openstreetmap.fr/?zoom=9&lat=43.82067&lon=1.235&layers=0000000B0FFFFFF";
    public static String basque_map = "https://tile.openstreetmap.fr/?zoom=11&lat=43.36289&lon=-1.45081&layers=00000000BFFFFFF";

    // - Contributions
    public static String base_contrib_map = "https://www.openstreetmap.org";
    public static String theme_contrib_map = "https://www.mapcontrib.xyz";
    public static String ads_warning_contrib_map = "https://openadvertmap.pavie.info";
    public static String building_contrib_map = "https://openlevelup.net";
    public static String them_an_now_contrib_map = "https://mvexel.github.io/thenandnow/";
    public static String hydrant_contrib_map = "https://www.osmhydrant.org";
    public static String whatever_amp = "http://openwhatevermap.xyz/";
    public static String infra_map = "https://openinframap.org";
    public static String complete_map = "https://mapcomplete.osm.be";


    public static String getTitle(Context context, String url) {
        String title = context.getString(R.string.app_name);
        if( url.contains(Helper.direction_map)){
            title = context.getString(R.string.itinerary);
        }else if( url.contains(Helper.cyclo_map)){
            title = context.getString(R.string.cycle_paths);
        }else if( url.contains(Helper.topo_map)){
            title = context.getString(R.string.topographic);
        }else if( url.contains(Helper.park_map)){
            title = context.getString(R.string.free_parkings);
        }else if( url.contains(Helper.fuel_map)){
            title = context.getString(R.string.fuel);
        }else if( url.contains(Helper.railway_map)){
            title = context.getString(R.string.railway);
        }else if( url.contains(Helper.ben_map) ){
            title = context.getString(R.string.benmaps);
        }else if( url.contains(Helper.travic_map) ){
            title = context.getString(R.string.transit);
        }else if( url.contains(Helper.wheel_map)){
            title = context.getString(R.string.accessible_places);
        }else if( url.contains(Helper.beer_map)){
            title = context.getString(R.string.beer);
        }else if( url.contains(Helper.solar_map)){
            title = context.getString(R.string.solar_panel);
        }else if( url.contains(Helper.weather_map)){
            title = context.getString(R.string.weather);
        }else if( url.contains(Helper.qwant_map)){
            title = context.getString(R.string.qwant_map);
        } else if (url.contains(Helper.openrecycle_map)) {
            title = context.getString(R.string.recycle);
        } else if (url.contains(Helper.queer_map)) {
            title = context.getString(R.string.queer_map);
        } else if (url.contains(Helper.water_map)) {
            title = context.getString(R.string.water_map);
        } else if (url.contains(Helper.snow_map)) {
            title = context.getString(R.string.ski_snow);
        } else if (url.contains(Helper.french_breweries)) {
            title = context.getString(R.string.french_breweries);
        } else if (url.contains(Helper.breton_map)) {
            title = context.getString(R.string.breton);
        } else if (url.contains(Helper.occitan_map)) {
            title = context.getString(R.string.occitan);
        } else if (url.contains(Helper.basque_map)) {
            title = context.getString(R.string.basque);
        } else if (url.contains(Helper.theme_contrib_map)) {
            title = context.getString(R.string.thematic_maps);
        }else if (url.contains(Helper.hydrant_contrib_map)) {
            title = context.getString(R.string.hydrant);
        }else if (url.contains(Helper.cartovrac_map)) {
            title = context.getString(R.string.reduce_waste);
        }else if (url.contains(Helper.gribrouillon)) {
            title = context.getString(R.string.colorize);
        } else if( url.contains(Helper.resto_map)){
            title = context.getString(R.string.vegetarian_restaurants);
        } else if( url.contains(Helper.historic_map)){
            title = context.getString(R.string.historic_places);
        }else if( url.contains(Helper.ads_warning_contrib_map)){
            title = context.getString(R.string.billboard_advertises);
        }else if( url.contains(Helper.them_an_now_contrib_map)){
            title = context.getString(R.string.then_and_now);
        }else if( url.contains(Helper.building_contrib_map)){
            title = context.getString(R.string.interior_buildings);
        }else if( url.contains(Helper.whatever_amp)){
            title = context.getString(R.string.whatever);
        }else if( url.contains(Helper.infra_map) ){
            title = context.getString(R.string.infrastructure);
        } else if( url.contains(Helper.complete_map)) {
            title = context.getString(R.string.mapcomplete);
        }
        return title;
    }

    public static String APP_SHARED_PREF = "app_shared_prefs";
    public static String LAST_USED_MAP = "last_used_map";
    public static String LAST_LOCATION = "last_location";
    public static String SET_DEFAULT_LOCALE_NEW = "set_default_locale_new";

    //Patterns to detect location in URLs
    private static final Pattern pattern1 =  Pattern.compile("/#(map=)?(\\d+)/([+-]?([0-9]*[.])?[0-9]+)/([+-]?([0-9]*[.])?[0-9]+)");
    private static final Pattern pattern2 =  Pattern.compile("/#/\\?c=(([+-]?([0-9]*)[.])?[0-9]+),([+-]?([0-9]*[.])?[0-9]+)&z=([0-9]*)");
    private static final Pattern pattern3 =  Pattern.compile("/@(([+-]?([0-9]*)[.])?[0-9]+),([+-]?([0-9]*[.])?[0-9]+),([0-9]*)");
    private static final Pattern patternZoom =  Pattern.compile("zoom=([0-9]*)");
    private static final Pattern patternLat =  Pattern.compile("lat=([+-]?([0-9]*[.])?[0-9]+)");
    private static final Pattern patternLon =  Pattern.compile("lon=([+-]?([0-9]*[.])?[0-9]+)");

    public static ArrayList<Locale> SUPPORTED_LOCALES = new ArrayList<Locale>() {{
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
    }};
    


    /**
     * Record and returns current location from a URL
     * @param context Context of the application
     * @param url String, current URL to analyze
     */
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
                    if( url.contains(historic_map)){ //For historic places lat and long are reverted
                        lat = matcher.group(5);
                        lon = matcher.group(3);
                    }else{
                        lat = matcher.group(3);
                        lon = matcher.group(5);
                    }
                    zoom = matcher.group(2);
                }else{
                    matcher = pattern2.matcher(url);
                    if( matcher.find()) {
                        lat = matcher.group(1);
                        lon = matcher.group(4);
                        zoom = matcher.group(6);
                    }/*else{ //TODO: currently not used.
                        matcher = pattern3.matcher(url);
                        if( matcher.find()) {
                            lat = matcher.group(4);
                            lon = matcher.group(1);
                            zoom = matcher.group(6);
                        }
                    }*/
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
        webView.getSettings().setAppCachePath(context.getCacheDir().getPath());
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        }
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
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
