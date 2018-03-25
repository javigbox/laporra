package com.gboxapps.laporra.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gboxapps.laporra.R;
import com.gboxapps.laporra.util.FontUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

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

    Animation slideUpAnimation, slideDownAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

       setupWidgets();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setupWidgets(){
        slideUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);

        slideDownAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down_animation);

        buttonEnter.setTypeface(FontUtils.getFuturaNdExtraBoldIt(this));
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);

        startSlideUpAnimations();

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
