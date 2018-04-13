package com.gboxapps.laporra.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gboxapps.laporra.R;
import com.gboxapps.laporra.util.ExifUtil;
import com.gboxapps.laporra.util.Util;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;
import rebus.permissionutils.SimpleCallback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

public class RegisterActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 2;
    private static final int SELECT_PICTURE = 1;
    private static final int REQUEST_CAMERA = 6;
    private static final int REQUEST_GALLERY = 5;

    Toolbar toolbar;
    ProgressDialog pdLoading;

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

    private String encodedImage = "";
    private Bitmap bitmap; //For attached images
    private long unixTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // finally change the color
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        pdLoading = new ProgressDialog(this);

        initToolbar();
        setupWidgets();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {

                if (data != null) {

                    Uri mImageUri = data.getData();

                    Glide
                            .with(getApplicationContext())
                            .asBitmap()
                            .load(mImageUri)
                            .into(new SimpleTarget<Bitmap>(256, 256) {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    bitmap = resource;

                                    if (bitmap != null) {
                                        encodedImage = Util.encodeImage(bitmap); //BASE64
                                        imageUser.setImageBitmap(bitmap);
                                    } else {
                                        Snackbar.make(content, R.string.bitmap_error, Snackbar.LENGTH_LONG).show();
                                    }

                                }
                            });
                }


            } else if (requestCode == TAKE_PICTURE) {

                //Get our saved file into a bitmap object:
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "IMG_" + unixTime + ".jpg");
                //Bitmap bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700);
                Uri mImageUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);
                Bitmap bitmap = null;
                try {
                    bitmap = Util.getThumbnail(getApplicationContext(), mImageUri);
                    bitmap = ExifUtil.rotateBitmap(RegisterActivity.this, file.getCanonicalPath(), bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (bitmap != null) {
                    encodedImage = Util.encodeImage(bitmap); //BASE64
                    imageUser.setImageBitmap(bitmap);
                } else {
                    Snackbar.make(content, R.string.bitmap_error, Snackbar.LENGTH_LONG).show();
                }
            }
        }

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

    @OnClick(R.id.image_user)
    public void checkPermissionCamera() {
        //Permisos en tiempo de ejecucion
        PermissionManager.with(RegisterActivity.this)
                .permission(PermissionEnum.WRITE_EXTERNAL_STORAGE, PermissionEnum.READ_EXTERNAL_STORAGE, PermissionEnum.CAMERA)
                .askagain(true)
                .callback(new SimpleCallback() {
                    @Override
                    public void result(boolean allPermissionsGranted) {
                        if (allPermissionsGranted)
                            showSelectMediaOption();
                    }
                })
                .ask();
    }

    /**
     * Manejamos respuesta de Runtime Permission
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(requestCode, permissions, grantResults);
    }

    /**
     * Diálogo para seleccionar el método de subir media
     */
    private void showSelectMediaOption() {

        //Abro dialgo
        final Dialog dialog = new Dialog(RegisterActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_choose_media_only_image);

        TextView mTextSeleccionaImagen = (TextView) dialog.findViewById(R.id.selecciona_imagen);
        TextView mTextHacerImagen = (TextView) dialog.findViewById(R.id.hacer_foto);

        mTextSeleccionaImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                pickPhotoGallery();
            }
        });

        mTextHacerImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                takePhoto();
            }
        });

        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void pickPhotoGallery() {
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion > Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_GALLERY);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant
                return;
            }
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent,
                getString(R.string.choose_photo)), SELECT_PICTURE);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void takePhoto() {
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion > Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant
                return;
            }
        }

        unixTime = System.currentTimeMillis() / 1000L;
        Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "IMG_" + unixTime + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file));
        startActivityForResult(intent, TAKE_PICTURE);
    }

}
