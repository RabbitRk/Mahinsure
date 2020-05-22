package com.rabbitt.mahinsure.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {

    // Shared preferences file name
    private static final String PREF_NAME = "USER_PREFS";
    private static final String LOGIN = "IsFirstTimeLaunch";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public PrefsManager(Context context) {

        int PRIVATE_MODE = 0;

        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(LOGIN, false);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(LOGIN, isFirstTime);
        editor.commit();
    }
}
