package com.gboxapps.laporra.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.gboxapps.laporra.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.image)
    CircleImageView imageView;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.email)
    TextView editEmail;

    @BindView(R.id.close_session)
    Button closeSession;

    Profile currentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null){
            getUserDataFacebook(accessToken);

         }
    }

    public void getUserDataFacebook(final AccessToken accesToken){
        GraphRequest request = GraphRequest.newMeRequest(
                accesToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        // Application code
                        String email = "";
                        String token = "";
                        try {
                            email = object.getString("email");
                            token = accesToken.getToken();
                            currentProfile = Profile.getCurrentProfile();
                            name.setText(currentProfile.getFirstName() + " " + currentProfile.getLastName());
                            editEmail.setText(email);
                            Picasso.with(MainActivity.this)
                                    .load(currentProfile.getProfilePictureUri(200,200))
                                    .into(imageView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @OnClick(R.id.close_session)
    public void closeSession(){
        LoginManager.getInstance().logOut();

        Intent intent = null;
        intent = new Intent(MainActivity.this,
                LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
