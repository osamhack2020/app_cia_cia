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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailStudyFragment extends Fragment {
    public int studyIdx = 1;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_detail_study, container, false);

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(String.format("%s/api/study/%d", getResources().getString(R.string.server_address), studyIdx)).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("info");
                        URL url = new URL(jsonObject.getString("img"));
                        final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView) rootView.findViewById(R.id.detail_study_thumbnail)).setImageBitmap(bitmap);
                                try {
                                    ((TextView) rootView.findViewById(R.id.detail_study_headText)).setText(jsonObject.getString("title"));
                                    ((TextView) rootView.findViewById(R.id.detail_study_detail_text)).setText(jsonObject.getString("note"));
                                    ((TextView) rootView.findViewById(R.id.detail_study_date_text)).setText(jsonObject.getString("signdate"));
                                    ((TextView) rootView.findViewById(R.id.detail_study_location_text)).setText(jsonObject.getString("station"));
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

        RecyclerView member_scroll = rootView.findViewById(R.id.detail_study_member_scroll);
        member_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        member_scroll.setHasFixedSize(true);
        member_scroll.setAdapter(new DetailMemberAdapter(new DetailMemberAdapter.Data[]
                {/*new DetailMemberAdapter.Data("김도엽 1", R.drawable.ic_launcher_background),
                        new DetailMemberAdapter.Data("김도엽 2", R.drawable.ic_launcher_background),
                        new DetailMemberAdapter.Data("김도엽 3", R.drawable.ic_launcher_background),
                        new DetailMemberAdapter.Data("김도엽 4", R.drawable.ic_launcher_background),
                        new DetailMemberAdapter.Data("김도엽 5", R.drawable.ic_launcher_background),
                        new DetailMemberAdapter.Data("김도엽 6", R.drawable.ic_launcher_background),*/
                }));

        final ConstraintLayout study_layout = (ConstraintLayout) rootView.findViewById(R.id.detail_study_layout);
        study_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                study_layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ((FrameLayout.LayoutParams) study_layout.getLayoutParams()).setMargins(0, study_layout.getRootView().findViewById(R.id.detail_study_thumbnail).getHeight(), 0, 0);
                study_layout.setPadding(Util.dpToPx(20), 0, Util.dpToPx(20), 400);
            }
        });
        return rootView;
    }
}
