package com.map.hunter;
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
