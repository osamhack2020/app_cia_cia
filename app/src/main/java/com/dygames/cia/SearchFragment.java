package com.dygames.cia;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {
    View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null)
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        rootView.findViewById(R.id.search_layout).setPadding(0, 0, 0, getActivity().findViewById(R.id.navigationView).getHeight());

        final RecyclerView search_study_scroll = rootView.findViewById(R.id.main_search_study_scroll);
        search_study_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        search_study_scroll.setHasFixedSize(true);
        search_study_scroll.setNestedScrollingEnabled(false);
        search_study_scroll.setAdapter(new CategoryAdapter(new ArrayList<CategoryAdapter.Data>()));

        final RecyclerView search_tut_scroll = rootView.findViewById(R.id.main_search_tut_scroll);
        search_tut_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        search_tut_scroll.setHasFixedSize(true);
        search_study_scroll.setNestedScrollingEnabled(false);
        search_tut_scroll.setAdapter(new CategoryAdapter(new ArrayList<CategoryAdapter.Data>()));
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
                                JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                                final ArrayList<CategoryAdapter.Data> categoryAdapterData = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    categoryAdapterData.add(new CategoryAdapter.Data(object.getString("title"), object.getString("note"), BitmapFactory.decodeStream(new URL(object.getString("img")).openConnection().getInputStream()), object.getInt("idx"), false, object.getInt("catIdx"), object.getInt("viewCount"), object.getString("tags"), object.getString("userName")));
                                }

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((CategoryAdapter) search_study_scroll.getAdapter()).Init(categoryAdapterData);
                                        search_study_scroll.getAdapter().notifyDataSetChanged();
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
                        Request request = new Request.Builder().url(String.format("%s/api/class?q=%s", getResources().getString(R.string.server_address), newText.trim())).build();
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.code() == 200) {
                                JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                                final ArrayList<CategoryAdapter.Data> categoryAdapterData = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    categoryAdapterData.add(new CategoryAdapter.Data(object.getString("title"), object.getString("note"), BitmapFactory.decodeStream(new URL(object.getString("img")).openConnection().getInputStream()), object.getInt("idx"), false, object.getInt("catIdx"), object.getInt("viewCount"), object.getString("tags"), object.getString("userName")));
                                }

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((CategoryAdapter) search_tut_scroll.getAdapter()).Init(categoryAdapterData);
                                        search_tut_scroll.getAdapter().notifyDataSetChanged();
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
