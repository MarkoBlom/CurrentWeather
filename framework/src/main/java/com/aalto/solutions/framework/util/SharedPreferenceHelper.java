package com.aalto.solutions.framework.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by marko on 10/7/2016.
 */

// NOTE: This could be more generic with self defined keys and value types.
// Not with disciplined APIs like now
public class SharedPreferenceHelper
{
    private static SharedPreferences mSettings;
    private static final String LAST_URL = "LAST_REQUEST_URL";

    //=======================================================================

    public static void saveLastRequestedCity(final String aCity , Context aCtx )
    {
        if(aCity.length()>0)
        {
            mSettings = PreferenceManager.getDefaultSharedPreferences(aCtx);
            mSettings.edit().putString(LAST_URL, aCity).commit();
        }
    }

    //=======================================================================

    public static String getLastRequestedCity( Context aCtx )
    {
        mSettings = PreferenceManager.getDefaultSharedPreferences(aCtx);
        return mSettings.getString(LAST_URL, "");
    }

    //=======================================================================
}
