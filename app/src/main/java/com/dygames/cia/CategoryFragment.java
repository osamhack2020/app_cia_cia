package com.dygames.cia;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CategoryFragment extends Fragment {
    public int catIdx;

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        final RecyclerView tut_scroll = rootView.findViewById(R.id.category_tut_scroll);
        final RecyclerView study_scroll = rootView.findViewById(R.id.category_study_scroll);
        final Spinner category_spinner = rootView.findViewById(R.id.category_spinner);

        tut_scroll.setAdapter(new CategoryAdapter(new ArrayList<CategoryAdapter.Data>()));
        study_scroll.setAdapter(new CategoryAdapter(new ArrayList<CategoryAdapter.Data>()));

        study_scroll.setVisibility(View.VISIBLE);
        tut_scroll.setVisibility(View.INVISIBLE);

        final Activity activity = getActivity();

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(String.format("%s/api/study?page=1&rowBlockCount=10", getResources().getString(R.string.server_address))).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                        final ArrayList<CategoryAdapter.Data> study_data = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            study_data.add(new CategoryAdapter.Data(object.getString("title"), object.getString("note"), BitmapFactory.decodeStream(new URL(object.getString("img")).openConnection().getInputStream()), object.getInt("idx"), false, object.getInt("catIdx"), object.getInt("viewCount"), object.getString("tags"), object.getString("userName")));
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                study_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                study_scroll.setHasFixedSize(true);
                                study_scroll.setAdapter(new CategoryAdapter(study_data));
                                ((CategoryAdapter) study_scroll.getAdapter()).getFilter().filter((catIdx + 1) + "");
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
                Request request = new Request.Builder().url(String.format("%s/api/class?page=1&rowBlockCount=10", getResources().getString(R.string.server_address))).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                        final ArrayList<CategoryAdapter.Data> tut_data = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            tut_data.add(new CategoryAdapter.Data(object.getString("title"), object.getString("date"), BitmapFactory.decodeStream(new URL(object.getString("img")).openConnection().getInputStream()), object.getInt("idx"), true, object.getInt("catIdx"), object.getInt("viewCount"), object.getString("tags"), object.getString("userName")));
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tut_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                tut_scroll.setHasFixedSize(true);
                                tut_scroll.setAdapter(new CategoryAdapter(tut_data));
                                ((CategoryAdapter) tut_scroll.getAdapter()).getFilter().filter((catIdx + 1) + "");
                                category_spinner.setSelection(catIdx);
                            }
                        });
                    } else {
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        final TextView study_headText = rootView.findViewById(R.id.category_study_headText);
        final TextView tut_headText = rootView.findViewById(R.id.category_tut_headText);

        study_headText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                study_headText.setTextColor(0xFF000000);
                tut_headText.setTextColor(0xFF909090);
                study_scroll.setVisibility(View.VISIBLE);
                tut_scroll.setVisibility(View.INVISIBLE);
            }
        });
        tut_headText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tut_headText.setTextColor(0xFF000000);
                study_headText.setTextColor(0xFF909090);
                study_scroll.setVisibility(View.INVISIBLE);
                tut_scroll.setVisibility(View.VISIBLE);
            }
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Util.categories);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(spinnerArrayAdapter);
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (study_scroll.getAdapter() != null)
                    ((CategoryAdapter) study_scroll.getAdapter()).getFilter().filter((position + 1) + "");
                if (tut_scroll.getAdapter() != null)
                    ((CategoryAdapter) tut_scroll.getAdapter()).getFilter().filter((position + 1) + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/*
        Spinner sort_spinner = rootView.findViewById(R.id.sort_spinner);
        String[] sortArray = {"인기순", "날짜순", "이름순"};
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sortArray);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort_spinner.setAdapter(sortAdapter);*/
        return rootView;
    }
}
