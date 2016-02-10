package com.cloudcomputing.shopit;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudcomputing.gcm.QuickstartPreferences;
import com.cloudcomputing.gcm.RegistrationIntentService;
import com.cloudcomputing.models.ResultListener;
import com.cloudcomputing.networkcalls.Network;

public class RegistrationActivity extends AppCompatActivity implements ResultListener {

    EditText mNameTextBox, mPasswordTextBox, mConfirmPasswordTextBox, mEmailTextBox;
    Button mOkayButton;
    TextView mWarningText;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mNameTextBox = (EditText) findViewById(R.id.nameTextBox);
        mPasswordTextBox = (EditText) findViewById(R.id.passwordTextBox);
        mEmailTextBox = (EditText) findViewById(R.id.emailaddressTextBox);
        mConfirmPasswordTextBox = (EditText) findViewById(R.id.confirmPasswordTextBox);
        mOkayButton = (Button) findViewById(R.id.okayButton);
        mWarningText = (TextView) findViewById(R.id.warningText);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.d("Token Sent", "Success");
                } else {
                    Log.d("Token Sent", "Fail");
                }
            }
        };


        Intent intent = new Intent(RegistrationActivity.this, RegistrationIntentService.class);
        startService(intent);

        mOkayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mNameTextBox.getText() != null && mPasswordTextBox.getText() != null && mConfirmPasswordTextBox.getText() != null
                        && mConfirmPasswordTextBox.getText().toString().equals(mPasswordTextBox.getText().toString()) && !mEmailTextBox.getText().toString().isEmpty()) {


                    if (!Constants.token.isEmpty()) {
                        String signUp = String.format(Constants.GETSIGNUP, mEmailTextBox.getText().toString(), mPasswordTextBox.getText().toString(), mNameTextBox.getText().toString(), Constants.token);
                        Network.getInstance().SignUp(signUp, RegistrationActivity.this);
                        mProgressDialog = ProgressDialog.show(RegistrationActivity.this, "Signing up", "Please Wait!");
                    }


                }


            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResult(boolean result) {

        mProgressDialog.cancel();
        if (result) {
            Constants.USERNAME = mNameTextBox.getText().toString();
            startActivity(new Intent(RegistrationActivity.this, MainScreen.class));
        } else {
            mWarningText.setVisibility(View.VISIBLE);
        }
    }
}
