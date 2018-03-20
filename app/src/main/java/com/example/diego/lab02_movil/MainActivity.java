package com.example.diego.lab02_movil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    static final int ACTIVITY_RESPONSE = 1;
    private TextView usernameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameText = findViewById(R.id.usernameText);

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String email = sharedPref.getString("email", null);
        String pass = sharedPref.getString("email", null);

        if (email == null || pass == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, ACTIVITY_RESPONSE);
        }
        else {
            usernameText.setText(email);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ACTIVITY_RESPONSE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra("email");
                String pass = data.getStringExtra("pass");
                usernameText.setText(email);

                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("email", email);
                editor.putString("pass", pass);
                editor.apply();
            }
        }
    }

    /** Called when the user taps the Login button */
    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, ACTIVITY_RESPONSE);
    }

    /** Called when the user taps the Logout button */
    public void logout(View view) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", null);
        editor.putString("pass", null);
        editor.apply();
        usernameText.setText(null);
        login(view);
    }
}
