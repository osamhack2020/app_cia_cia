package com.dygames.cia;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SignUpFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        final EditText name = rootView.findViewById(R.id.signup_name_editText);
        final EditText email = rootView.findViewById(R.id.signup_email_editText);
        final EditText pw = rootView.findViewById(R.id.signup_pw_editText);
        final EditText pwCheck = rootView.findViewById(R.id.signup_pw_check_editText);
        final EditText phone = rootView.findViewById(R.id.signup_phone_editText);


        Button signUp = rootView.findViewById(R.id.signup_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(String.format("http://cia777.cafe24.com/api/users/signup?name=%s&password=%s&email=%s&phonenm=%s", name.getText().toString().trim(), pw.getText().toString().trim(),
                                email.getText().toString().trim(), phone.getText().toString().trim().replace("-", ""))).build();
                        boolean success = false;
                        try {
                            if (client.newCall(request).execute().code() == 200) {
                                success = true;
                            } else {
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        final boolean finalSuccess = success;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (finalSuccess) {
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(SignUpFragment.this).commit();
                                    Toast.makeText(getContext(), "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(getContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }.start();
            }
        });
        return rootView;
    }
}
