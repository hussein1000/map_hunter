package com.map.hunter;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import com.franmontiel.localechanger.LocaleChanger;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.map.hunter.databinding.ActivityMainBinding;
import com.map.hunter.helper.Helper;
import com.map.hunter.webview.MapHunterWebChromeClient;
import com.map.hunter.webview.MapHunterWebViewClient;

public class MainActivity extends AppCompatActivity {


    private PowerMenu powerMenu;
    private PowerMenu powerSubMenu;
    private PowerMenu powerMenuLanguage;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    public static String TAG = "MapHunterTAG";
    private String url = null;
    boolean doubleBackToExitPressedOnce = false;
    private Location currentLocation;

    public static boolean canGoBack;
    public static boolean fromMenu;
    public static String lastVisited;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Helper.initializeWebview(MainActivity.this, binding.contentMain.mainWebview);
        MapHunterWebChromeClient openMapsWebChromeClient = new MapHunterWebChromeClient(MainActivity.this, binding.contentMain.mainWebview, binding.contentMain.webviewContainer, binding.contentMain.videoLayout);
        binding.contentMain.mainWebview.setWebChromeClient(openMapsWebChromeClient);
        binding.contentMain.mainWebview.setWebViewClient(new MapHunterWebViewClient(MainActivity.this));
        SharedPreferences sharedpref = getSharedPreferences(Helper.APP_SHARED_PREF, MODE_PRIVATE);
        String mapTo_Load = sharedpref.getString(Helper.LAST_USED_MAP, Helper.base_contrib_map);

        url = mapTo_Load;
        canGoBack = true;
        fromMenu = true;
        lastVisited = mapTo_Load;
        List<String> params = getLocationParams();
        String zoom = "14";
        if( params.size() > 2 ) {
            zoom = params.get(2);
            if (zoom == null || !zoom.matches("\\d+")) {
                zoom = "14";
            }
        }

/*        if( lastVisited.contains(Helper.solar_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.solar_map + "/#"+zoom+"/" + params.get(1) + "/" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.historic_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.historic_map + "/#"+zoom+"/" + params.get(0) + "/" + params.get(1)+"/0";
            }
        }else if( lastVisited.contains(Helper.fuel_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.fuel_map + "/#"+zoom+"/" + params.get(1) + "/" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.cyclo_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.cyclo_map + "/#map="+zoom+"/" + params.get(1) + "/" + params.get(0)+"/cyclosm";
            }
        }else if( lastVisited.contains(Helper.topo_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.topo_map + "/#map="+zoom+"/" + params.get(1) + "/" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.qwant_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.qwant_map + "/#map="+zoom+"/" + params.get(1) + "/" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.ads_warning_contrib_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.ads_warning_contrib_map + "/#"+zoom+"/" + params.get(1) + "/" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.them_an_now_contrib_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.them_an_now_contrib_map + "/#"+zoom+"/" + params.get(1) + "/" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.building_contrib_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.building_contrib_map + "/#"+zoom+"/" + params.get(1) + "/" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.beer_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.beer_map + "/#"+zoom+"/" + params.get(1) + "/" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.resto_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.resto_map + "/#zoom="+zoom+"&lat=" + params.get(1) + "&lon=" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.railway_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.railway_map + "?zoom="+zoom+"&lat=" + params.get(1) + "&lon=" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.wheel_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.wheel_map + "?zoom="+zoom+"&lat=" + params.get(1) + "&lon=" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.weather_map)){
            if( params.size() > 0 ) {
                mapTo_Load = Helper.weather_map + "&zoom="+zoom+"&lat=" + params.get(1) + "&lon=" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.park_map)){
            if( params.size() > 0 ){
                mapTo_Load = Helper.park_map + "/#/?c=" + params.get(1) + "," + params.get(0)+"&z="+zoom;
            }
        }else if( lastVisited.contains(Helper.whatever_amp)){
            if( params.size() > 0 ) {
                url = Helper.whatever_amp + "/#"+zoom+"/" + params.get(1) + "/" + params.get(0);
            }
        }else if( lastVisited.contains(Helper.ben_map) ){
            if( params.size() > 0 ) {
                url = Helper.ben_map + "/@"+  params.get(0) + "," + params.get(1) +","+zoom;
            }
        }else if( lastVisited.contains(Helper.infra_map) ){
            if( params.size() > 0 ) {
                url = Helper.infra_map + "/#"+zoom+"/" + params.get(1) + "/" + params.get(0);
            }
        } else if (lastVisited.contains(Helper.openrecycle_map)) {
            if (params.size() > 0) {
                url = Helper.openrecycle_map + "/" + zoom + "/" + params.get(1) + "/" + params.get(0);
            }
        } else if (lastVisited.contains(Helper.travic_map)) {
            if (params.size() > 0) {
                mapTo_Load = Helper.travic_map + "?zoom=" + zoom + "&lat=" + params.get(1) + "&lon=" + params.get(0);
            }
        } else if (lastVisited.contains(Helper.gribrouillon)) {
            if (params.size() > 0) {
                url = Helper.gribrouillon + "/#"+zoom+"/" + params.get(1) + "/" + params.get(0);
            }
        }*/
        String title = Helper.getTitle(MainActivity.this, lastVisited);
        setTitle(title);
        if( mapTo_Load != null ) {
            binding.contentMain.mainWebview.loadUrl(mapTo_Load);
        }


/*        List<PowerMenuItem> distances = new ArrayList<>();
        distances.add(new PowerMenuItem(getString(R.string.about), true));
        powerMenu = new PowerMenu.Builder(MainActivity.this)
                .addItemList(distances)
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
                .setMenuRadius(10f) // sets the corner radius.
                .setMenuShadow(10f) // sets the shadow.
                .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent))
                .setTextGravity(Gravity.START)
                .setShowBackground(false)
            //    .setHeight(1500)
                .setSelectedTextColor(Color.WHITE)
                .setMenuColor(Color.WHITE)
                .setSelectedMenuColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();*/

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        if (Build.VERSION.SDK_INT >= 23) {
            permissionsAPI();
        }

        if( getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
/*        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_language) {
            createLanguageMenu(binding.mainLayout);
        } else if (item.getItemId() == R.id.action_about) {*/
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
      //  }
        return super.onOptionsItemSelected(item);
    }

    public void showProgressDialog(){
        binding.contentMain.progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressDialog(){
        binding.contentMain.progressBar.setVisibility(View.GONE);
    }

    public void setProgressDialog(int progress){
        binding.contentMain.progressBar.setProgress(progress);
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleChanger.onConfigurationChanged();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }

/*    private final OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            SharedPreferences sharedpref = getSharedPreferences(Helper.APP_SHARED_PREF, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpref.edit();
            powerMenu.setSelectedPosition(position);
            List<String> params = getLocationParams();
            String zoom = "14";
            if( params.size() > 2 ) {
                zoom = params.get(2);
                if (zoom == null || !zoom.matches("\\d+")) {
                    zoom = "14";
                }
            }
            String finalZoom = zoom;
            View footerView = powerSubMenu.getFooterView();
            TextView close = footerView.findViewById(R.id.close_dialog);
            close.setOnClickListener(view -> powerSubMenu.dismiss());

        }
    };*/





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<>();
            perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            // Check for ACCESS_FINE_LOCATION
            if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, R.string.all_granted, Toast.LENGTH_SHORT)
                        .show();
            } else {
                // Permission Denied
                Toast.makeText(MainActivity.this, R.string.permission_denied, Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void permissionsAPI() {
        List<String> permissionsNeeded = new ArrayList<>();

        final List<String> permissionsList = new ArrayList<>();
        if (!addPermission(permissionsList))
            permissionsNeeded.add(getString(R.string.show_location));

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {

                // Need Rationale
                StringBuilder message = new StringBuilder(getString(R.string.access_needed, permissionsNeeded.get(0)));

                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message.append(", ").append(permissionsNeeded.get(i));

                showMessageOKCancel(message.toString(),
                        (dialog, which) -> requestPermissions(permissionsList.toArray(new String[0]),
                                REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS));
                return;
            }
            requestPermissions(permissionsList.toArray(new String[0]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        binding.contentMain.mainWebview.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.contentMain.mainWebview.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.contentMain.mainWebview.destroy();
    }


    @Override
    public void onBackPressed() {

        if ( !canGoBack) {
            canGoBack = true;
            fromMenu = true;
            binding.contentMain.mainWebview.loadUrl(lastVisited);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.click_back_to_exit), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), okListener)
                .setNegativeButton(getString(R.string.cancel), null)
                .create()
                .show();
    }

    public Location getCurrentLocation(){
        return this.currentLocation;
    }


    public List<String> getLocationParams(){
        List<String> params = new ArrayList<>();
        if( currentLocation != null){
            params.add(0, String.valueOf(currentLocation.getLongitude()));
            params.add(1, String.valueOf(currentLocation.getLatitude()));
            params.add(2, String.valueOf(currentLocation.getAltitude()));
        }else{
            SharedPreferences sharedpref = getSharedPreferences(Helper.APP_SHARED_PREF, MODE_PRIVATE);
            String location = sharedpref.getString(Helper.LAST_LOCATION, null);
            if( location != null && location.split(",").length > 1){
                params.add(0, location.split(",")[0]);
                params.add(1, location.split(",")[1]);
                params.add(2, location.split(",")[2]);
            }
        }
        return params;
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull final Location location) {
            currentLocation = location;
            SharedPreferences sharedpref = getSharedPreferences(Helper.APP_SHARED_PREF, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpref.edit();
            editor.putString(Helper.LAST_LOCATION,currentLocation.getLongitude() + "," + currentLocation.getLatitude()+","+currentLocation.getAltitude());
            editor.apply();
            if (currentLocation != null) {
                if( url.equals(Helper.ben_map)) {
                    String coord = "{\"app\":{\"mapCoords\":[" + currentLocation.getLongitude() + "," + currentLocation.getLatitude() + ",10]}}";
                    binding.contentMain.mainWebview.loadUrl("javascript:window.localStorage.setItem(\"persistedState\", '" + coord + "')");
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    };

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList) {

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            // Check for Rationale Option
            return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
        }else{
            LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            assert mLocationManager != null;
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Helper.LOCATION_REFRESH_TIME,
                    Helper.LOCATION_REFRESH_DISTANCE, mLocationListener);
        }
        return true;
    }
}
