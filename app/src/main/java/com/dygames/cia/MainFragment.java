package com.dygames.cia;

import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainFragment extends Fragment {

    private com.dygames.cia.CategoryFragment categoryFragment = new com.dygames.cia.CategoryFragment();
    private com.dygames.cia.UploadStudyFragment uploadStudyFragment = new com.dygames.cia.UploadStudyFragment();
    private com.dygames.cia.UploadTutFragment uploadTutFragment = new com.dygames.cia.UploadTutFragment();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView.findViewById(R.id.main_layout).setPadding(Util.dpToPx(20), Util.dpToPx(100), Util.dpToPx(20), getActivity().findViewById(R.id.navigationView).getHeight());
        final CustomActionBar actionBar = rootView.findViewById(R.id.main_actionbar);
        final ScrollView main_scrollView = (ScrollView) rootView.findViewById(R.id.main_layout).getParent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            main_scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    actionBar.setColor(scrollY);
                }
            });
        } else {
            main_scrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    actionBar.setColor(main_scrollView.getScrollY());
                    return false;
                }
            });
        }


        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(String.format("%s/api/class/recommend?limitCount=5", getResources().getString(R.string.server_address))).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView recommend_tut = rootView.findViewById(R.id.recommend_tut_scroll);
                                recommend_tut.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                recommend_tut.setHasFixedSize(true);
                                try {
                                    CourseAdapter.Data[] courseAdapterData = new CourseAdapter.Data[jsonArray.length()];

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        courseAdapterData[i] = new CourseAdapter.Data(object.getString("title"), object.getString("note"), object.getString("img"), object.getInt("idx"), true);
                                    }

                                    recommend_tut.setAdapter(new CourseAdapter(courseAdapterData));
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
                Request request = new Request.Builder().url(String.format("%s/api/study/recommend?limitCount=5", getResources().getString(R.string.server_address))).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView recommend_study = rootView.findViewById(R.id.recommend_study_scroll);
                                recommend_study.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                recommend_study.setHasFixedSize(true);
                                try {
                                    CourseAdapter.Data[] courseAdapterData = new CourseAdapter.Data[jsonArray.length()];

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        courseAdapterData[i] = new CourseAdapter.Data(object.getString("title"), object.getString("note"), object.getString("img"), object.getInt("idx"), false);
                                    }

                                    recommend_study.setAdapter(new CourseAdapter(courseAdapterData));
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

        RecyclerView trend_tut = rootView.findViewById(R.id.trend_tut_scroll);
        trend_tut.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        trend_tut.setHasFixedSize(true);
        trend_tut.setAdapter(new CourseAdapter(new CourseAdapter.Data[]
                {new CourseAdapter.Data("타이틀 111", "설명 111", "", 0, true),
                        new CourseAdapter.Data("타이틀 222", "설명 222", "", 0, true),
                        new CourseAdapter.Data("타이틀 333", "설명 333", "", 0, true),
                        new CourseAdapter.Data("타이틀 444", "설명 444", "", 0, true),
                        new CourseAdapter.Data("타이틀 555", "설명 555", "", 0, true),
                        new CourseAdapter.Data("타이틀 666", "설명 666", "", 0, true),
                }));

        RecyclerView trend_study = rootView.findViewById(R.id.trend_study_scroll);
        trend_study.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        trend_study.setHasFixedSize(true);
        trend_study.setAdapter(new CourseAdapter(new CourseAdapter.Data[]
                {new CourseAdapter.Data("타이틀 1111", "설명 1111", "", 0, false),
                        new CourseAdapter.Data("타이틀 2222", "설명 2222", "", 0, false),
                        new CourseAdapter.Data("타이틀 3333", "설명 3333", "", 0, false),
                        new CourseAdapter.Data("타이틀 4444", "설명 4444", "", 0, false),
                        new CourseAdapter.Data("타이틀 5555", "설명 5555", "", 0, false),
                        new CourseAdapter.Data("타이틀 6666", "설명 6666", "", 0, false),
                }));

        final View fab_study = rootView.findViewById(R.id.fab_study_upload);
        final View fab_tut = rootView.findViewById(R.id.fab_tut_upload);
        rootView.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_study.setVisibility(fab_study.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                fab_tut.setVisibility(fab_tut.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
            }
        });

        fab_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, uploadStudyFragment).addToBackStack(null).commitAllowingStateLoss();
            }
        });

        fab_tut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, uploadTutFragment).addToBackStack(null).commitAllowingStateLoss();
            }
        });

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(String.format("%s/api/study/cat", getResources().getString(R.string.server_address))).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Util.categorys = new String[jsonArray.length()];
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        Util.categorys[i] = object.getString("name");
                                        Chip chip = new Chip(getContext(), null, R.style.Widget_MaterialComponents_Chip_Action);
                                        chip.setText(object.getString("name"));
                                        chip.setTextAppearance(R.style.TextStyle);
                                        chip.setClickable(true);
                                        chip.setFocusable(true);
                                        chip.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                transaction.replace(R.id.frameLayout, categoryFragment).addToBackStack(null).commitAllowingStateLoss();
                                            }
                                        });
                                        ((ChipGroup) rootView.findViewById(R.id.category_chipgroup)).addView(chip);
                                    }
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
