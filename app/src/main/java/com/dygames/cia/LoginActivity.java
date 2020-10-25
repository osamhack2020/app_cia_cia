package com.dygames.cia;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    SignUpFragment signUpFragment = new SignUpFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.login_id_editText);
        final EditText pw = findViewById(R.id.login_pw_editText);

        TextView signup_text = findViewById(R.id.login_signup_text);
        signup_text.setPaintFlags(signup_text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.login_frameLayout, signUpFragment).addToBackStack(null).commitAllowingStateLoss();
            }
        });
        ((EditText) findViewById(R.id.login_pw_editText)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        findViewById(R.id.login_head_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent0 = new Intent(getApplicationContext(), MainActivity.class);
                intent0.putExtra("studyIdx", 2);
                PendingIntent intent1 = PendingIntent.getActivity(LoginActivity.this, 0, intent0, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(new NotificationChannel("cia", "CIA", NotificationManager.IMPORTANCE_HIGH));
                    builder = new NotificationCompat.Builder(LoginActivity.this)
                            .setSmallIcon(R.drawable.vlogo)
                            .setContentTitle("비즈니스 스터디")
                            .setContentText("비즈니스 스터디 알림입니다.")
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true)
                            .setChannelId("cia")
                            .setContentIntent(intent1);
                }

                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, builder.build());

            }
        });

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pw.getText().length() <= 0 || email.getText().length() <= 0) {
                    Toast.makeText(LoginActivity.this, "로그인 정보를 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(String.format("%s/api/users/signin?password=%s&email=%s", getResources().getString(R.string.server_address), pw.getText().toString().trim(),
                                email.getText().toString().trim())).build();
                        boolean success = false;
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.code() == 200) {
                                List<String> cookies = response.headers("Set-Cookie");
                                for (int i = 0; i < cookies.size(); i++) {
                                    if (cookies.get(i).contains("HSID")) {
                                        Util.userHSID = cookies.get(i).replace("HSID=", "").replace("; Path=/", "");
                                    }
                                }
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
                                    Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
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


    }
}
