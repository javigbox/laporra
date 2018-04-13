package com.gboxapps.laporra.api;

import android.content.Context;

import com.gboxapps.laporra.util.Consts;
import com.gboxapps.laporra.util.PrefUtils;

public class AuthHelper {

  public static String AUTH_TOKEN;

  public static void loadToken(Context context) {
    AUTH_TOKEN = PrefUtils.getString(context, Consts.PREF_TOKEN_USER, null);
  }

  public static String getToken() {
    return AUTH_TOKEN;
  }

  public static void setToken(Context context, String token) {
    PrefUtils.setString(context, Consts.PREF_TOKEN_USER, token);
    AUTH_TOKEN = token;
  }

  public static void reset(Context context) {
    PrefUtils.setString(context, Consts.PREF_TOKEN_USER, null);
    AUTH_TOKEN = null;
  }
}
