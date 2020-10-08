package com.dygames.cia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.login_id_editText);
        final EditText pw = findViewById(R.id.login_pw_editText);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(String.format("http://cia777.cafe24.com/api/users/signin?password=%s&email=%s", pw.getText().toString().trim(),
                                email.getText().toString().trim())).build();
                        boolean success = false;
                        try {
                            if (client.newCall(request).execute().code() == 200) {
                                success = true;
                                getFragmentManager().popBackStack();
                            } else {
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        final boolean finalSuccess = success;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (finalSuccess) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else
                                    Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }.start();
            }
        });

        final SignUpFragment signUpFragment = new SignUpFragment();

        findViewById(R.id.login_signup_button).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.login_frameLayout, signUpFragment).addToBackStack(null).commitAllowingStateLoss();
                    }
                });
    }
}
