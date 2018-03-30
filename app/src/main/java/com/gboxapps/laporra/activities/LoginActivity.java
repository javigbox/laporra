package com.gboxapps.laporra.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    @BindView(R.id.logo)
    ImageView logo;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // finally change the color
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.yellow_100));

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
        Animation slideUpAnimation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation1.setDuration(100);
        textLogin.startAnimation(slideUpAnimation1);
        Animation slideUpAnimation2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation2.setDuration(300);
        editEmail.startAnimation(slideUpAnimation2);
        Animation slideUpAnimation3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation3.setDuration(600);
        editPassword.startAnimation(slideUpAnimation3);
        Animation slideUpAnimation4 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation4.setDuration(900);
        buttonEnter.startAnimation(slideUpAnimation4);
        Animation slideUpAnimation5 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation5.setDuration(1200);
        textForgot.startAnimation(slideUpAnimation5);
        Animation slideUpAnimation6 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation6.setDuration(1300);
        textRegister.startAnimation(slideUpAnimation6);
        Animation slideUpAnimation7 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation7.setDuration(1500);
        buttonFacebook.startAnimation(slideUpAnimation6);
    }

}
