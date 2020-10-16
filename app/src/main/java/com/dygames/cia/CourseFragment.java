package com.dygames.cia;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CourseFragment extends Fragment {

    View rootView;

    public static class Data {
        public String title;
        public String desc;
        public Bitmap thumbnailID;
        public int id;

        public Data(String title, String desc, Bitmap thumbnailID, int id) {
            this.title = title;
            this.desc = desc;
            this.thumbnailID = thumbnailID;
            this.id = id;
        }
    }

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null)
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_course, container, false);

        rootView.findViewById(R.id.course_layout).setPadding(Util.dpToPx(20), Util.dpToPx(20), Util.dpToPx(20), getActivity().findViewById(R.id.navigationView).getHeight());

        final ArrayList<Data> tut_data = new ArrayList<>();
        final ArrayList<Data> study_data = new ArrayList<>();

        final LinearLayout tut_result = rootView.findViewById(R.id.course_tut_result);
        final LinearLayout study_result = rootView.findViewById(R.id.course_study_result);

        final Activity activity = getActivity();

        new Thread() {
            public void run() {
                Request request = new Request.Builder()
                        .url(String.format("%s/api/class/me", getResources().getString(R.string.server_address)))
                        .addHeader("Authorization", Util.userHSID)
                        .build();
                try {
                    final Response response = new OkHttpClient().newCall(request).execute();
                    if (response.code() == 200) {
                        JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            tut_data.add(new Data(object.getString("title"), object.getString("note"), BitmapFactory.decodeStream(new URL(object.getString("img")).openConnection().getInputStream()), object.getInt("idx")));
                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < tut_data.size(); i++) {
                                    View v = inflater.inflate(R.layout.view_my_tut, tut_result, false);
                                    ((ImageView) v.findViewById(R.id.my_tut_thumbnail)).setImageBitmap(tut_data.get(i).thumbnailID);
                                    ((TextView) v.findViewById(R.id.my_tut_title)).setText(tut_data.get(i).title);
                                    ((TextView) v.findViewById(R.id.my_tut_desc)).setText(tut_data.get(i).desc);
                                    tut_result.addView(v);
                                }
                            }
                        });
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        new Thread() {
            public void run() {
                Request request = new Request.Builder()
                        .url(String.format("%s/api/study/me", getResources().getString(R.string.server_address)))
                        .addHeader("Authorization", Util.userHSID)
                        .build();
                try {
                    final Response response = new OkHttpClient().newCall(request).execute();
                    if (response.code() == 200) {
                        JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.d("DDDD", object.toString());
                            study_data.add(new Data(object.getString("title"), object.getString("note"), BitmapFactory.decodeStream(new URL(object.getString("img")).openConnection().getInputStream()), object.getInt("idx")));
                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < study_data.size(); i++) {
                                    View v = inflater.inflate(R.layout.view_my_study, study_result, false);
                                    ((ImageView) v.findViewById(R.id.my_study_thumbnail)).setImageBitmap(study_data.get(i).thumbnailID);
                                    ((TextView) v.findViewById(R.id.my_study_title)).setText(study_data.get(i).title);
                                    ((TextView) v.findViewById(R.id.my_study_desc)).setText(study_data.get(i).desc);

                                    RecyclerView member_scroll = v.findViewById(R.id.my_study_member_scroll);
                                    member_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                    member_scroll.setHasFixedSize(true);
                                    member_scroll.setAdapter(new DetailMemberAdapter(new DetailMemberAdapter.Data[]
                                            {new DetailMemberAdapter.Data("kdy", "https://cdn.sstatic.net/Img/product/enterprise/illo-ent-1-M01.png?v=83efb5300844"),
                                                    new DetailMemberAdapter.Data("kdy", "https://cdn.sstatic.net/Img/product/enterprise/illo-ent-1-M01.png?v=83efb5300844"),
                                                    new DetailMemberAdapter.Data("kdy", "https://cdn.sstatic.net/Img/product/enterprise/illo-ent-1-M01.png?v=83efb5300844"),
                                                    new DetailMemberAdapter.Data("kdy", "https://cdn.sstatic.net/Img/product/enterprise/illo-ent-1-M01.png?v=83efb5300844"),
                                                    new DetailMemberAdapter.Data("kdy", "https://cdn.sstatic.net/Img/product/enterprise/illo-ent-1-M01.png?v=83efb5300844"),
                                                    new DetailMemberAdapter.Data("kdy", "https://cdn.sstatic.net/Img/product/enterprise/illo-ent-1-M01.png?v=83efb5300844"),
                                                    new DetailMemberAdapter.Data("kdy", "https://cdn.sstatic.net/Img/product/enterprise/illo-ent-1-M01.png?v=83efb5300844"),
                                                    new DetailMemberAdapter.Data("kdy", "https://cdn.sstatic.net/Img/product/enterprise/illo-ent-1-M01.png?v=83efb5300844"),
                                                    new DetailMemberAdapter.Data("kdy", "https://cdn.sstatic.net/Img/product/enterprise/illo-ent-1-M01.png?v=83efb5300844"),
                                                    new DetailMemberAdapter.Data("kdy", "https://cdn.sstatic.net/Img/product/enterprise/illo-ent-1-M01.png?v=83efb5300844"),
                                            }));
                                    study_result.addView(v);
                                }
                            }
                        });
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return rootView;
    }
}
