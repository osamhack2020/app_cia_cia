package com.dygames.cia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailTutFragment extends Fragment {
    public int tutIdx;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_detail_tut, container, false);

        rootView.findViewById(R.id.detail_tut_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(String.format("%s/api/class/%d", getResources().getString(R.string.server_address), tutIdx))
                                .addHeader("Authorization", Util.userHSID)
                                .delete()
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.code() == 200) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(DetailTutFragment.this).commit();
                                    }
                                });
                            } else {
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });


        rootView.findViewById(R.id.detail_tut_update_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateStudyFragment.studyIdx = studyIdx;
                // ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, updateStudyFragment).addToBackStack(null).commitAllowingStateLoss();
            }
        });

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                try {
                    client.newCall(new Request.Builder().url(String.format("%s/api/class/%d/views", getResources().getString(R.string.server_address), tutIdx)).put(new MultipartBody.Builder().addFormDataPart("", "").build()).build()).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Request request = new Request.Builder().url(String.format("%s/api/class/%d", getResources().getString(R.string.server_address), tutIdx)).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("info");
                        //updateStudyFragment.targetJsonObject = jsonObject;
                        URL url = new URL(jsonObject.getString("img"));
                        final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        //updateStudyFragment.targetBitmap = bitmap;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView) rootView.findViewById(R.id.detail_tut_thumbnail)).setImageBitmap(bitmap);
                                try {
                                    ((TextView) rootView.findViewById(R.id.detail_tut_headText)).setText(jsonObject.getString("title"));
                                    ((TextView) rootView.findViewById(R.id.detail_tut_detail_text)).setText(jsonObject.getString("note"));
                                    ((TextView) rootView.findViewById(R.id.detail_tut_category_text)).setText(Util.categorys[jsonObject.getInt("catIdx") - 1]);
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

        final RecyclerView member_scroll = rootView.findViewById(R.id.detail_tut_scroll);
        member_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        member_scroll.setHasFixedSize(true);

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(String.format("%s/api/class/%d/curriculum", getResources().getString(R.string.server_address), tutIdx)).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");

                        final DetailMemberAdapter.Data[] data = new DetailMemberAdapter.Data[jsonArray.length()];

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            //data[i] = new DetailMemberAdapter.Data(object.getString("name"), object.getString("img"));
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //member_scroll.setAdapter(new DetailMemberAdapter(data));
                            }
                        });
                    } else {
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        final ConstraintLayout tut_layout = (ConstraintLayout) rootView.findViewById(R.id.detail_tut_layout);
        tut_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tut_layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ((FrameLayout.LayoutParams) tut_layout.getLayoutParams()).setMargins(0, tut_layout.getRootView().findViewById(R.id.detail_tut_thumbnail).getHeight(), 0, 0);
                tut_layout.setPadding(Util.dpToPx(20), 0, Util.dpToPx(20), 400);
            }
        });
        return rootView;
    }
}
