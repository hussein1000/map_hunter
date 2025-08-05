package com.map.hunter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.franmontiel.localechanger.LocaleChanger;

import java.util.List;


import com.map.hunter.databinding.ActivityAboutBinding;


public class AboutActivity  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAboutBinding binding = ActivityAboutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            binding.aboutVersion.setText(getResources().getString(R.string.about_vesrion, version));
        } catch (PackageManager.NameNotFoundException ignored) {}

        setTitle(R.string.about_the_app);
        if( getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        //Developer Github
        content = new SpannableString(binding.github.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.github.setText(content);
        binding.github.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hussein1000/map_hunter"));
            startActivity(browserIntent);
        });

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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




}
