package com.dygames.cia;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {
    View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null)
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        rootView.findViewById(R.id.search_layout).setPadding(Util.dpToPx(20), Util.dpToPx(20), Util.dpToPx(20), getActivity().findViewById(R.id.navigationView).getHeight());

        final RecyclerView search_study_scroll = rootView.findViewById(R.id.main_search_study_scroll);
        search_study_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        search_study_scroll.setHasFixedSize(true);
        search_study_scroll.setNestedScrollingEnabled(false);
        search_study_scroll.setAdapter(new CourseAdapter(new CourseAdapter.Data[0]));

        final RecyclerView search_tut_scroll = rootView.findViewById(R.id.main_search_tut_scroll);
        search_tut_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        search_tut_scroll.setHasFixedSize(true);
        search_study_scroll.setNestedScrollingEnabled(false);
        search_tut_scroll.setAdapter(new CourseAdapter(new CourseAdapter.Data[0]));
        final Activity activity = getActivity();

        ((SearchView) rootView.findViewById(R.id.main_search_view)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                new Thread() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(String.format("%s/api/study?q=%s", getResources().getString(R.string.server_address), newText.trim())).build();
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.code() == 200) {
                                final JSONObject jsonObject = new JSONObject(response.body().string());
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                                            CourseAdapter.Data[] courseAdapterData = new CourseAdapter.Data[jsonArray.length()];

                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                courseAdapterData[i] = new CourseAdapter.Data(object.getString("title"), object.getInt("catIdx"), object.getString("userName"), object.getInt("viewCount"), object.getString("regdate"), object.getString("img"), object.getInt("idx"), true);
                                            }

                                            ((CourseAdapter) search_study_scroll.getAdapter()).data = courseAdapterData;
                                            search_study_scroll.getAdapter().notifyDataSetChanged();
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
                return false;
            }
        });
        return rootView;
    }

}
