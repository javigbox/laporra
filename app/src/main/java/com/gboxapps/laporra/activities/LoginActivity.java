package com.gboxapps.laporra.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gboxapps.laporra.R;
import com.gboxapps.laporra.util.FontUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 1;

    @BindView(R.id.content)
    LinearLayout content;

    @BindView(R.id.scroll)
    ScrollView scrollView;

    @BindView(R.id.text_login)
    TextView textLogin;

    @BindView(R.id.edit_email)
    EditText editEmail;

    @BindView(R.id.edit_password)
    EditText editPassword;

    @BindView(R.id.text_forgot)
    TextView textForgot;

    @BindView(R.id.button_enter)
    Button buttonEnter;

    @BindView(R.id.text_register)
    TextView textRegister;

    @BindView(R.id.button_facebook)
    LoginButton buttonFacebook;

    CallbackManager callbackManager;
    Animation slideUpAnimation, slideDownAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setupWidgets();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setupWidgets() {
        buttonFacebook.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        buttonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Snackbar.make(content, error.getMessage(), Snackbar.LENGTH_LONG);
            }
        });

        slideUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);

        slideDownAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down_animation);

        buttonEnter.setTypeface(FontUtils.getFuturaNdExtraBoldIt(this));
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);

        startSlideUpAnimations();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void startSlideUpAnimations() {
        slideUpAnimation.setDuration(600);
        textLogin.startAnimation(slideUpAnimation);
        slideUpAnimation.setDuration(700);
        editEmail.startAnimation(slideUpAnimation);
        slideUpAnimation.setDuration(800);
        editPassword.startAnimation(slideUpAnimation);
        slideUpAnimation.setDuration(900);
        buttonEnter.startAnimation(slideUpAnimation);
        slideUpAnimation.setDuration(1000);
        textForgot.startAnimation(slideUpAnimation);
        slideUpAnimation.setDuration(1100);
        textRegister.startAnimation(slideUpAnimation);
    }

}
