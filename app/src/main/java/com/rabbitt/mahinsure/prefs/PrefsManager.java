package com.rabbitt.mahinsure.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {

    // Shared preferences file name
    public static final String PREF_NAME = "USER_PREFS";
    private static final String LOGIN = "IsFirstTimeLaunch";

    private static final String REF_PREF = "REF_PREF";
    private static final String REF_NO = "REF_NO";
    private static final String V_NO = "V_NO";

    public static final String USER_ID = "USER_ID";



    private SharedPreferences pref, r_pref;
    private SharedPreferences.Editor editor, r_editor;

    public PrefsManager(Context context) {

        int PRIVATE_MODE = 0;

        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        r_pref = context.getSharedPreferences(REF_PREF, PRIVATE_MODE);
        r_editor = r_pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(LOGIN, false);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(LOGIN, isFirstTime);
        editor.commit();
    }

    public String getRefNo()
    {
        return r_pref.getString(REF_NO,"");
    }

    public String getvNo()
    {
        return r_pref.getString(V_NO, "");
    }

    public void setRefNo(String refNo, String v_no)
    {
        r_editor.putString(REF_NO, refNo);
        r_editor.putString(V_NO, v_no);
        r_editor.commit();
    }
}
