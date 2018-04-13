package com.gboxapps.laporra.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {

    private static final String TAG = PrefUtils.class.getSimpleName();

    private PrefUtils() {
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Consts.SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Gets a boolean preference value.
     *
     * @param context the context
     * @param keyId the key id
     * @param defaultValue the default value
     */
    public static boolean getBoolean(Context context, String keyId, boolean defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(keyId, defaultValue);
    }

    /**
     * Sets a boolean preference value.
     *
     * @param context the context
     * @param keyId the key id
     * @param value the value
     */
    public static void setBoolean(Context context, String keyId, boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(keyId, value);
        editor.apply();
    }

    /**
     * Gets an integer preference value.
     *
     * @param context the context
     * @param keyId the key id
     * @param defaultValue the default value
     */
    public static int getInt(Context context, String keyId, int defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getInt(keyId, defaultValue);
    }

    /**
     * Sets an integer preference value.
     *
     * @param context the context
     * @param keyId the key id
     * @param value the value
     */
    public static void setInt(Context context, String keyId, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(keyId, value);
        editor.apply();
    }

    /**
     * Gets a float preference value.
     *
     * @param context the context
     * @param keyId the key id
     * @param defaultValue the default value
     */
    public static float getFloat(Context context, String keyId, float defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getFloat(keyId, defaultValue);
    }

    /**
     * Sets a float preference value.
     *
     * @param context the context
     * @param keyId the key id
     * @param value the value
     */
    public static void setFloat(Context context, String keyId, float value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(keyId, value);
        editor.apply();
    }

    /**
     * Gets a long preference value.
     *
     * @param context the context
     * @param keyId the key id
     */
    public static long getLong(Context context, String keyId, long defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getLong(keyId, defaultValue);
    }

    /**
     * Sets a long preference value.
     *
     * @param context the context
     * @param keyId the key id
     * @param value the value
     */
    public static void setLong(Context context, String keyId, long value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(keyId, value);
        editor.apply();
    }

    /**
     * Gets a string preference value.
     *
     * @param context the context
     * @param keyId the key id
     * @param defaultValue default value
     */
    public static String getString(Context context, String keyId, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getString(keyId, defaultValue);
    }

    /**
     * Sets a string preference value.
     *
     * @param context the context
     * @param keyId the key id
     * @param value the value
     */
    public static void setString(Context context, String keyId, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keyId, value);
        editor.apply();
    }

    public static void remove(Context context, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = getSharedPreferences(context);
        String registrationId = PrefUtils.getString(context,Consts.PREF_PROPERTY_REG_ID,"");
        if (registrationId.isEmpty()) {
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = PrefUtils.getInt(context,Consts.PREF_PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = Util.getAppVersionCode(context);
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    public static void storeRegistrationId(Context context, String regId) {
        int appVersion = Util.getAppVersionCode(context);
        PrefUtils.setString(context,Consts.PREF_PROPERTY_REG_ID,regId);
        PrefUtils.setInt(context,Consts.PREF_PROPERTY_APP_VERSION,appVersion);
    }

}
