package com.gboxapps.laporra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.gboxapps.laporra.R;

/**
 * Created by Javi on 24/03/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = null;
                //TODO: Alternar cuando esté logeado
                intent = new Intent(SplashActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }, SPLASH_DISPLAY_LENGTH);

        //Comprobar sesión de facebook
        AccessToken accessToken = AccountKit.getCurrentAccessToken();
        if(accessToken != null){
            //Lancar main activity
        } else {
            //Lanzar login activity
        }

    }
}
