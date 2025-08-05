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

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.franmontiel.localechanger.LocaleChanger;
import java.util.Locale;

import com.map.hunter.helper.Helper;

import static com.map.hunter.helper.Helper.SUPPORTED_LOCALES;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            SharedPreferences sharedpreferences = getSharedPreferences(Helper.APP_SHARED_PREF, android.content.Context.MODE_PRIVATE);
            String defaultLocaleString = sharedpreferences.getString(Helper.SET_DEFAULT_LOCALE_NEW, null);
            if (defaultLocaleString != null) {
                Locale defaultLocale;
                if (defaultLocaleString.equals("zh-CN"))
                    defaultLocale = Locale.SIMPLIFIED_CHINESE;
                else if (defaultLocaleString.equals("zh-TW"))
                    defaultLocale = Locale.TRADITIONAL_CHINESE;
                else
                    defaultLocale = new Locale(defaultLocaleString);
                SUPPORTED_LOCALES.add(defaultLocale);
            } else {
                SUPPORTED_LOCALES.add(Locale.getDefault());
            }
            LocaleChanger.initialize(getApplicationContext(), SUPPORTED_LOCALES);
        } catch (Exception ignored) { }

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleChanger.onConfigurationChanged();
    }
}
