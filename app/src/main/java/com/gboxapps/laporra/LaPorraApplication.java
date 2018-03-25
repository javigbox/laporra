package com.gboxapps.laporra;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Javi on 24/03/2018.
 */

public class LaPorraApplication extends Application {

    public Context context;

    private static LaPorraApplication mSharedInstance;

    public LaPorraApplication() {
        mSharedInstance = this;
    }

    public static LaPorraApplication getInstance() {
        return mSharedInstance;
    }

    public void onCreate() {
        super.onCreate();
        //TODO: Descomentar
//        Fabric.with(this, new Crashlytics());

        context = this.getApplicationContext();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Mosk Thin 100.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }
}
