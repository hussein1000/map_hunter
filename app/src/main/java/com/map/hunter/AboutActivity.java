package com.map.hunter;
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
import com.map.hunter.drawer.ContributorsDrawer;
import com.map.hunter.entity.Contributor;
import com.map.hunter.helper.ContributorsData;


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
        //Developer click for Mastodon account
        SpannableString content = new SpannableString(binding.developerMastodon.getText().toString());
        content.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AboutActivity.this,R.color.colorAccent)), 0, content.length(), 0);
        binding.developerMastodon.setText(content);
        binding.developerMastodon.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://framapiaf.org/@fedilab"));
            startActivity(browserIntent);
        });



        //Developer Github
        content = new SpannableString(binding.github.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.github.setText(content);
        binding.github.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/stom79"));
            startActivity(browserIntent);
        });

        //Developer Framagit

        content = new SpannableString(binding.framagit.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.framagit.setText(content);
        binding.framagit.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://framagit.org/tom79"));
            startActivity(browserIntent);
        });

        //Developer Codeberg
        content = new SpannableString(binding.codeberg.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.codeberg.setText(content);
        binding.codeberg.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://codeberg.org/tom79"));
            startActivity(browserIntent);
        });

        //Developer donation
        content = new SpannableString(binding.developerDonation.getText().toString());
        content.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AboutActivity.this,R.color.colorAccent)), 0, content.length(), 0);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.developerDonation.setText(content);
        binding.developerDonation.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fedilab.app/page/donations/"));
            startActivity(browserIntent);
        });


        //Idea click for Mastodon account
        content = new SpannableString(binding.bristow69Mastodon.getText().toString());
        content.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AboutActivity.this,R.color.colorAccent)), 0, content.length(), 0);
        binding.bristow69Mastodon.setText(content);
        binding.bristow69Mastodon.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://framapiaf.org/@Bristow_69"));
            startActivity(browserIntent);
        });

        content = new SpannableString(binding.license.getText().toString());
        content.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AboutActivity.this,R.color.colorAccent)), 0, content.length(), 0);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.license.setText(content);
        binding.license.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gnu.org/licenses/quick-guide-gplv3.fr.html"));
            startActivity(browserIntent);
        });



        List<Contributor> contributors = ContributorsData.getContributions(AboutActivity.this);
        ContributorsDrawer contributorsDrawer = new ContributorsDrawer(contributors);
        binding.listOfContributors.setLayoutManager(new LinearLayoutManager(AboutActivity.this));
        binding.listOfContributors.setNestedScrollingEnabled(false);
        binding.listOfContributors.setAdapter(contributorsDrawer);
    }



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
