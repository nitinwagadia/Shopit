package com.cloudcomputing.shopit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcomputing.animation.Animation;
import com.cloudcomputing.models.ResultListener;
import com.cloudcomputing.networkcalls.Network;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ResultListener {

    FrameLayout mErrorLayout;
    LoginButton mFacebookButton;
    Button mLoginButton;
    TextView mEmailTextBox, mPasswordTextBox, mSignUpTextView;
    public static String url = "http://firstrestexample-env.elasticbeanstalk.com/myhellocall/responsehello";
    private CallbackManager mCallbackManager;
    private SharedPreferences mSharedPreferences;
    public final String mSignupString = "Dont'have an account.. SignUp here";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics mDisplayMetric = getResources().getDisplayMetrics();
        Constants.SCREEN_WIDTH = mDisplayMetric.widthPixels;
        Constants.SCREEN_HEIGHT = mDisplayMetric.heightPixels;

        mCallbackManager = CallbackManager.Factory.create();
        if (Build.VERSION.SDK_INT >= 21) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.mainactivity_exit_transition);
            getWindow().setExitTransition(transition);
        }

        setContentView(R.layout.activity_main);

        mFacebookButton = (LoginButton) findViewById(R.id.facebookButton);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mEmailTextBox = (TextView) findViewById(R.id.emailTextBox);
        mPasswordTextBox = (TextView) findViewById(R.id.passwordTextBox);
        mErrorLayout = (FrameLayout) findViewById(R.id.errorLayout);
        mSignUpTextView = (TextView) findViewById(R.id.signupTextView);
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
        SpannableString spannableString = new SpannableString(mSignupString);
        spannableString.setSpan(new ForegroundColorSpan(MainActivity.this.getResources().getColor(android.R.color.holo_blue_dark)), 22, mSignupString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSignUpTextView.setText(spannableString);

        mErrorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
            }
        });
        mFacebookButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        mFacebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        Log.e("LoginActivity", response.toString());
                        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                        mEditor.putString(Constants.LOGIN_USER_ID, loginResult.getAccessToken().getUserId());
                        mEditor.putString(Constants.LOGIN_USER_TOKEN, loginResult.getAccessToken().getToken());
                        mEditor.commit();
                        gotoNextActivity();
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {

                Toast.makeText(MainActivity.this, "FB login Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {

                Animation.ErrorLayoutAnimation(mErrorLayout, getResources().getDimension(R.dimen.translate_left_right));
                Toast.makeText(MainActivity.this, "FB login Error", Toast.LENGTH_SHORT).show();

            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String query = Constants.GETLOGIN;
                String formattedQuery = String.format(query, mEmailTextBox.getText().toString(), mPasswordTextBox.getText().toString());
                Constants.USERNAME = mEmailTextBox.getText().toString();
                if (!mEmailTextBox.getText().toString().isEmpty() && !mPasswordTextBox.getText().toString().isEmpty())
                    Network.getInstance().Login(formattedQuery, MainActivity.this);
                else
                    Toast.makeText(MainActivity.this, "Wrong username or passoword", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void gotoNextActivity() {

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);
        startActivity(new Intent(this, MainScreen.class), activityOptionsCompat.toBundle());
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResult(boolean result) {
        if (result)
            gotoNextActivity();
        else
            Toast.makeText(MainActivity.this, "Wrong username or passoword", Toast.LENGTH_SHORT).show();
    }
}


