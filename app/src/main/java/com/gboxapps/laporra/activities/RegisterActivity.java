package com.gboxapps.laporra.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gboxapps.laporra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;

    @BindView(R.id.content)
    LinearLayout content;

    @BindView(R.id.text_register)
    TextView textRegister;

    @BindView(R.id.image_user)
    ImageView imageUser;

    @BindView(R.id.edit_email)
    EditText editEmail;

    @BindView(R.id.edit_username)
    EditText editUsername;

    @BindView(R.id.edit_password)
    EditText editPassword;

    @BindView(R.id.edit_password_repeat)
    EditText editPasswordRepeat;

    @BindView(R.id.button_register)
    Button buttonRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // finally change the color
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        initToolbar();
        setupWidgets();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle("");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void setupWidgets() {
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);

        startSlideUpAnimations();
    }

    public void startSlideUpAnimations() {

        Animation slideUpAnimation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation1.setDuration(100);
        textRegister.startAnimation(slideUpAnimation1);

        Animation slideUpAnimation2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation2.setDuration(300);
        imageUser.startAnimation(slideUpAnimation2);

        Animation slideUpAnimation3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation3.setDuration(600);
        editEmail.startAnimation(slideUpAnimation3);

        Animation slideUpAnimation4 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation4.setDuration(900);
        editUsername.startAnimation(slideUpAnimation4);

        Animation slideUpAnimation5 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation5.setDuration(1200);
        editPassword.startAnimation(slideUpAnimation5);

        Animation slideUpAnimation6 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation6.setDuration(1300);
        editPasswordRepeat.startAnimation(slideUpAnimation6);

        Animation slideUpAnimation7 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_animation);
        slideUpAnimation7.setDuration(1500);
        buttonRegister.startAnimation(slideUpAnimation7);
    }
}
