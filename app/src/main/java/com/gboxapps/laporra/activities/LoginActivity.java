package com.gboxapps.laporra.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.gboxapps.laporra.R;
import com.gboxapps.laporra.util.FontUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 1;

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
        setContentView(R.layout.activity_login);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == APP_REQUEST_CODE){
            AccountKitLoginResult accountKitLoginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if(accountKitLoginResult.getError() != null){
                //Mostrar mensaje de error de facebook
            } else {
                //YEAH!
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                
            }
        }
    }

    public void onLogin(final LoginType loginType){
        final Intent intent = new Intent(this, AccountKitActivity.class);

        AccountKitConfiguration.AccountKitConfigurationBuilder accountKitConfigurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        loginType,
                        AccountKitActivity.ResponseType.TOKEN);
                final AccountKitConfiguration  configuration = accountKitConfigurationBuilder.build();

                intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configuration);
                startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @OnClick(R.id.button_facebook)
    public void onFacebookLogin(){
        onLogin(LoginType.EMAIL);
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
