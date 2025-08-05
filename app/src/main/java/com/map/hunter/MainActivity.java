package com.map.hunter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.map.hunter.databinding.ActivityMainBinding;
import com.map.hunter.helper.Helper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences sharedpref;
    private String mapTo_Load;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedpref = getSharedPreferences(Helper.APP_SHARED_PREF, Context.MODE_PRIVATE);
        mapTo_Load = sharedpref.getString(Helper.LAST_USED_MAP, Helper.direction_map);  // Fallback to a default map

        Helper.initializeWebview(this, binding.webview);
        binding.webview.loadUrl(mapTo_Load);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reload) {
            binding.webview.reload();
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String currentUrl = binding.webview.getUrl();
        if (currentUrl != null) {
            sharedpref.edit().putString(Helper.LAST_USED_MAP, currentUrl).apply();
            Helper.recordLocationFromUrl(this, currentUrl);
        }
    }
}