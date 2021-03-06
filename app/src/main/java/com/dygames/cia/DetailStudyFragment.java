package com.dygames.cia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailStudyFragment extends Fragment {
    public int studyIdx = 1;

    UpdateStudyFragment updateStudyFragment = new UpdateStudyFragment();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_detail_study, container, false);

        final View fab_delete = rootView.findViewById(R.id.detail_study_fab_delete);
        final View fab_quit = rootView.findViewById(R.id.detail_study_fab_quit);
        final View fab_sign = rootView.findViewById(R.id.detail_study_fab_sign);
        final View fab_update = rootView.findViewById(R.id.detail_study_fab_update);

        rootView.findViewById(R.id.detail_study_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_delete.setVisibility(fab_delete.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                fab_quit.setVisibility(fab_quit.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                fab_sign.setVisibility(fab_sign.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                fab_update.setVisibility(fab_update.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
            }
        });

        final Activity activity = getActivity();
        final Context context = getContext();

        fab_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(String.format("%s/api/study/%d", getResources().getString(R.string.server_address), studyIdx))
                                .addHeader("Authorization", Util.userHSID)
                                .delete()
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.code() == 200) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction().remove(DetailStudyFragment.this).commit();
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

        fab_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(String.format("%s/api/study/%d/students", getResources().getString(R.string.server_address), studyIdx))
                                .addHeader("Authorization", Util.userHSID)
                                .delete()
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.code() == 200) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
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

        fab_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody req = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("", "")
                                .build();

                        Request request = new Request.Builder()
                                .url(String.format("%s/api/study/%d/students", getResources().getString(R.string.server_address), studyIdx))
                                .addHeader("Authorization", Util.userHSID)
                                .post(req)
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.code() == 200) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
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

        fab_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudyFragment.studyIdx = studyIdx;
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, updateStudyFragment).addToBackStack(null).commitAllowingStateLoss();
            }
        });
        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                try {
                    client.newCall(new Request.Builder().url(String.format("%s/api/study/%d/views", activity.getResources().getString(R.string.server_address), studyIdx)).put(new MultipartBody.Builder().addFormDataPart("", "").build()).build()).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Request request = new Request.Builder().url(String.format("%s/api/study/%d", activity.getResources().getString(R.string.server_address), studyIdx)).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("info");
                        updateStudyFragment.targetJsonObject = jsonObject;
                        URL url = new URL(jsonObject.getString("img"));
                        final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        updateStudyFragment.targetBitmap = bitmap;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView) rootView.findViewById(R.id.detail_study_thumbnail)).setImageBitmap(bitmap);
                                try {
                                    ((TextView) rootView.findViewById(R.id.detail_study_headText)).setText(jsonObject.getString("title"));
                                    ((TextView) rootView.findViewById(R.id.detail_study_view_text)).setText(jsonObject.getString("viewCount"));
                                    ((TextView) rootView.findViewById(R.id.detail_study_date_text)).setText(jsonObject.getString("regdate").split(" ")[0]);
                                    ((TextView) rootView.findViewById(R.id.detail_study_member_text)).setText("최대 " + jsonObject.getString("maxPeople") + "명");
                                    ((TextView) rootView.findViewById(R.id.detail_study_desc_text)).setText(jsonObject.getString("note"));
                                    ((TextView) rootView.findViewById(R.id.detail_study_info_date_text)).setText(jsonObject.getString("signdate").split(" ")[0]);
                                    ((TextView) rootView.findViewById(R.id.detail_study_location_text)).setText(jsonObject.getString("station"));
                                    ((TextView) rootView.findViewById(R.id.detail_study_info_headText)).setText(String.format("%s · %s", /*Util.categories[jsonObject.getInt("catIdx") - 1]*/"", jsonObject.getString("userName")));

                                    String[] tags = jsonObject.getString("tags").split(",");
                                    for (int i = 0; i < tags.length; i++) {
                                        Chip chip = new Chip(context, null, R.style.AppTheme);
                                        chip.setTextSize(12);
                                        chip.setText(tags[i].trim());
                                        chip.setClickable(true);
                                        chip.setFocusable(true);
                                        chip.setChipCornerRadius(10);
                                        ((ChipGroup) rootView.findViewById(R.id.detail_tag_parent)).addView(chip);
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

        final RecyclerView member_scroll = rootView.findViewById(R.id.detail_study_member_scroll);
        member_scroll.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        member_scroll.setHasFixedSize(true);

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(String.format("%s/api/study/%d/students", activity.getResources().getString(R.string.server_address), studyIdx)).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");

                        final DetailMemberAdapter.Data[] data = new DetailMemberAdapter.Data[jsonArray.length()];

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            data[i] = new DetailMemberAdapter.Data(object.getString("name"), object.getString("img"));
                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                member_scroll.setAdapter(new DetailMemberAdapter(data));
                                member_scroll.getAdapter().notifyDataSetChanged();
                            }
                        });


                    } else {
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        final ConstraintLayout study_layout = (ConstraintLayout) rootView.findViewById(R.id.detail_study_layout);
        study_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                study_layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ((FrameLayout.LayoutParams) study_layout.getLayoutParams()).setMargins(0, study_layout.getRootView().findViewById(R.id.detail_study_thumbnail).getHeight(), 0, 0);
                study_layout.setPadding(Util.dpToPx(20), 0, Util.dpToPx(20), study_layout.getRootView().findViewById(R.id.detail_study_thumbnail).getHeight());
            }
        });
        return rootView;
    }
}
