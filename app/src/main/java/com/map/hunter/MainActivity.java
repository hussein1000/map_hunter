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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.Map;
import com.map.hunter.databinding.ActivityMainBinding;
import com.map.hunter.helper.Helper;
import com.map.hunter.webview.MapHunterWebChromeClient;
import com.map.hunter.webview.MapHunterWebViewClient;

public class MainActivity extends AppCompatActivity {


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

        String title = Helper.getTitle(MainActivity.this, lastVisited);
        setTitle(title);
        if( mapTo_Load != null ) {
            binding.contentMain.mainWebview.loadUrl(mapTo_Load);
        }

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
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
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
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<>();
            perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, R.string.all_granted, Toast.LENGTH_SHORT)
                        .show();
            } else {
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
