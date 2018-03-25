package com.gboxapps.laporra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.gboxapps.laporra.R;

/**
 * Created by Javi on 24/03/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    private boolean isAppLogged = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(isAppLogged){
                    Intent intent = null;
                    //TODO: Alternar cuando esté logeado
                    intent = new Intent(SplashActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    if (accessToken != null){
                        //Está logeado en facebook
                        Intent intent = new Intent(SplashActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Fetch the profile, which will trigger the onCurrentProfileChanged receiver
//                        Profile.fetchProfileForCurrentAccessToken();
                        Intent intent = null;
                        //TODO: Alternar cuando esté logeado
                        intent = new Intent(SplashActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

        }, SPLASH_DISPLAY_LENGTH);


    }
}
