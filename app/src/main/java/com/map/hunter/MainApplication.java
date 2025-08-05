package com.map.hunter;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.annotation.NonNull;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
