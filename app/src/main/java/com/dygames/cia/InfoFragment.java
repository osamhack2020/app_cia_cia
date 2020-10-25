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
    View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null)
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_info, container, false);

        final Activity activity = getActivity();

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(String.format("%s/api/users/profile", getResources().getString(R.string.server_address)))
                        .addHeader("Authorization", Util.userHSID)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("user");
                        String img = jsonObject.getString("img");
                        Bitmap bitmap = null;
                        if (img.compareTo("null") != 0)
                            bitmap = BitmapFactory.decodeStream(new URL(img).openConnection().getInputStream());

                        final Bitmap finalBitmap = bitmap;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ((TextView) rootView.findViewById(R.id.info_name)).setText(jsonObject.getString("name"));
                                    ((TextView) rootView.findViewById(R.id.info_mail)).setText(jsonObject.getString("email"));
                                    ((TextView) rootView.findViewById(R.id.info_desc)).setText(jsonObject.getString("regdate").split(" ")[0] + " · " + jsonObject.getString("phonenm"));
                                    if (finalBitmap != null)
                                        ((ImageView) rootView.findViewById(R.id.info_profile_image)).setImageBitmap(finalBitmap);
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


        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody req = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("", "")
                        .build();

                Request request = new Request.Builder()
                        .url(String.format("%s/api/study/manage", getResources().getString(R.string.server_address)))
                        .addHeader("Authorization", Util.userHSID)
                        //.post(req)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Log.d("DDDDA", response.code() + "");
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView study_created_scroll = rootView.findViewById(R.id.info_study_created_scroll);
                                study_created_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                study_created_scroll.setHasFixedSize(true);
                                try {
                                    CourseAdapter.Data[] courseAdapterData = new CourseAdapter.Data[jsonArray.length()];
                                    ((TextView) rootView.findViewById(R.id.info_value_created_study)).setText(jsonArray.length() + "");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        courseAdapterData[i] = new CourseAdapter.Data(object.getString("title"), object.getInt("catIdx"), object.getString("userName"), object.getInt("viewCount"), object.getString("regdate"), object.getString("img"), object.getInt("idx"), false);
                                    }

                                    study_created_scroll.setAdapter(new CourseAdapter(courseAdapterData));
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

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody req = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("", "")
                        .build();

                Request request = new Request.Builder()
                        .url(String.format("%s/api/study/me", getResources().getString(R.string.server_address)))
                        .addHeader("Authorization", Util.userHSID)
                        //.post(req)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Log.d("DDDDB", response.code() + "");
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView study_my_scroll = rootView.findViewById(R.id.info_study_my_scroll);
                                study_my_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                study_my_scroll.setHasFixedSize(true);
                                try {
                                    CourseAdapter.Data[] courseAdapterData = new CourseAdapter.Data[jsonArray.length()];
                                    ((TextView) rootView.findViewById(R.id.info_value_my_study)).setText(jsonArray.length() + "");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        courseAdapterData[i] = new CourseAdapter.Data(object.getString("title"), object.getInt("catIdx"), object.getString("userName"), object.getInt("viewCount"), object.getString("regdate"), object.getString("img"), object.getInt("idx"), false);
                                    }

                                    study_my_scroll.setAdapter(new CourseAdapter(courseAdapterData));
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

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody req = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("", "")
                        .build();

                Request request = new Request.Builder()
                        .url(String.format("%s/api/class/manage", getResources().getString(R.string.server_address)))
                        .addHeader("Authorization", Util.userHSID)
                        //.post(req)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView tut_created_scroll = rootView.findViewById(R.id.info_tut_created_scroll);
                                tut_created_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                tut_created_scroll.setHasFixedSize(true);
                                try {
                                    CourseAdapter.Data[] courseAdapterData = new CourseAdapter.Data[jsonArray.length()];
                                    ((TextView) rootView.findViewById(R.id.info_value_created_tut)).setText(jsonArray.length() + "");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        courseAdapterData[i] = new CourseAdapter.Data(object.getString("title"), object.getInt("catIdx"), object.getString("userName"), object.getInt("viewCount"), object.getString("regdate"), object.getString("img"), object.getInt("idx"), true);
                                    }

                                    tut_created_scroll.setAdapter(new CourseAdapter(courseAdapterData));
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

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody req = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("", "")
                        .build();

                Request request = new Request.Builder()
                        .url(String.format("%s/api/class/me", getResources().getString(R.string.server_address)))
                        .addHeader("Authorization", Util.userHSID)
                        //.post(req)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView tut_my_scroll = rootView.findViewById(R.id.info_tut_my_scroll);
                                tut_my_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                tut_my_scroll.setHasFixedSize(true);
                                try {
                                    CourseAdapter.Data[] courseAdapterData = new CourseAdapter.Data[jsonArray.length()];
                                    ((TextView) rootView.findViewById(R.id.info_value_my_tut)).setText(jsonArray.length() + "");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        courseAdapterData[i] = new CourseAdapter.Data(object.getString("title"), object.getInt("catIdx"), object.getString("userName"), object.getInt("viewCount"), object.getString("regdate"), object.getString("img"), object.getInt("idx"), true);
                                    }

                                    tut_my_scroll.setAdapter(new CourseAdapter(courseAdapterData));
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
