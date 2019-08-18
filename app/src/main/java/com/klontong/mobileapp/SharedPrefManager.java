package com.klontong.mobileapp;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Rifky on 05-Nov-18.
 */

public class SharedPrefManager {

    private static final String SP_KLONTONG_APP = "spKlontongApp";
    public static final String SP_NAMA_WRG = "spNamaWarung";
    public static final String SP_ALREADY = "spSudahLogin";

    Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        preferences = context.getSharedPreferences(SP_KLONTONG_APP, MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }

    public void saveSPString(String keySP, String value) {
        editor.putString(keySP, value);
        editor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        editor.putBoolean(keySP, value);
        editor.commit();
    }

    public String getSpNamaWrg() {
        return preferences.getString(SP_NAMA_WRG, "");
    }

    boolean getSpAlready() {
        return preferences.getBoolean(SP_ALREADY, false);
    }
}
