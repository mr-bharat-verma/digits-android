package com.example.app.digits;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthConfig;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.example.app.R;
import com.twitter.sdk.android.core.TwitterAuthToken;

public class CustomPhoneNumberActivity extends Activity {
    private Button digitsSubmitPhoneButton;
    private EditText digitsPhoneNumberEditText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.digits_activity_custom_phone_number);

        final AuthCallback callback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                if (session.getAuthToken() instanceof TwitterAuthToken) {
                    setResult(DigitsMainActivity.CUSTOM_LOGIN_REQUEST);
                    finish();
                }
            }

            @Override
            public void failure(DigitsException error) {
                Toast.makeText(CustomPhoneNumberActivity.this, "Digits login failed",
                        Toast.LENGTH_LONG).show();
            }
        };

        digitsSubmitPhoneButton = (Button) findViewById(R.id.submit_phone_button);
        digitsPhoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);
        digitsSubmitPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNumber = digitsPhoneNumberEditText.getText().toString();

                DigitsAuthConfig.Builder digitsAuthConfigBuilder = new DigitsAuthConfig.Builder()
                        .withAuthCallBack(callback)
                        .withPhoneNumber(phoneNumber)
                        .withEmailCollection()
                        .withThemeResId(R.style.LightTheme);

                Digits.authenticate(digitsAuthConfigBuilder.build());
            }
        });
    }
}