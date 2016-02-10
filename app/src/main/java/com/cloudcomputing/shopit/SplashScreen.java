package com.cloudcomputing.shopit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cloudcomputing.networkcalls.GooglePlayServiceHelper;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {

    SharedPreferences mSharedPreferences;
    GooglePlayServiceHelper mGooglePlayServiceHelper;

    @Override
    protected void onResume() {
        super.onResume();
        if(GooglePlayServiceHelper.mGoogleApiClient ==null)
        {
            mGooglePlayServiceHelper = new GooglePlayServiceHelper();
            if(mGooglePlayServiceHelper.checkPlayServices(this))
            {
                mGooglePlayServiceHelper.buildGoogleApiClient(ApplicationInstance.getInstance());
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(GooglePlayServiceHelper.mGoogleApiClient !=null)
        {
            GooglePlayServiceHelper.mGoogleApiClient.connect();
        }
        mGooglePlayServiceHelper.mGoogleApiClient.connect();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (hasSharedPreference()) {
            //createShatedPreferences();
            startActivity(new Intent(this, MainActivity.class));
        } else {


            startActivity(new Intent(this, MainScreen.class));

        }

        finish();
        // Set up the user interaction to manually show or hide the system UI.


    }


    public boolean hasSharedPreference() {
        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(Constants.LOGIN_USER_ID, null) == null;

    }

    public void createShatedPreferences() {
        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(Constants.LOGIN_USER_ID, "id");
        mEditor.putString(Constants.LOGIN_USER_TOKEN, "password");
        mEditor.commit();
    }

}
