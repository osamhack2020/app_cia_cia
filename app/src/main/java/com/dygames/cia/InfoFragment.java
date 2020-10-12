package com.dygames.cia;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InfoFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        LinearLayout info_list = rootView.findViewById(R.id.info_list);
        for (int i = 0; i < info_list.getChildCount(); i++) {
            ((ConstraintLayout) info_list.getChildAt(i)).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
        }

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody req = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("", "")
                        .build();
                Request request = new Request.Builder()
                        .url(String.format("%s/api/users/profile", getResources().getString(R.string.server_address)))
                        .addHeader("Authorization", Util.userHSID)
                        .post(req)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("user");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ((TextView) rootView.findViewById(R.id.info_name)).setText(jsonObject.getString("name"));
                                    ((TextView) rootView.findViewById(R.id.info_desc)).setText(jsonObject.getString("phonenm"));

                                    new Thread() {
                                        public void run() {
                                            URL url = null;
                                            try {
                                                url = new URL(jsonObject.getString("img"));
                                                final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                                ((Activity) getActivity()).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ((ImageView) rootView.findViewById(R.id.info_profile_image)).setImageBitmap(bitmap);
                                                    }
                                                });
                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }.start();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return rootView;
    }
}
