package com.dygames.cia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
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

        rootView.findViewById(R.id.detail_study_delete_button).setOnClickListener(new View.OnClickListener() {
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
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(DetailStudyFragment.this).commit();
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

        rootView.findViewById(R.id.detail_study_quit_button).setOnClickListener(new View.OnClickListener() {
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
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        rootView.findViewById(R.id.detail_study_quit_button).setVisibility(View.GONE);
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

        rootView.findViewById(R.id.detail_study_sign_button).setOnClickListener(new View.OnClickListener() {
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
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        rootView.findViewById(R.id.detail_study_sign_button).setVisibility(View.GONE);
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

        rootView.findViewById(R.id.detail_study_update_button).setOnClickListener(new View.OnClickListener() {
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
                    client.newCall(new Request.Builder().url(String.format("%s/api/study/%d/views", getResources().getString(R.string.server_address), studyIdx)).put(new MultipartBody.Builder().addFormDataPart("", "").build()).build()).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Request request = new Request.Builder().url(String.format("%s/api/study/%d", getResources().getString(R.string.server_address), studyIdx)).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("info");
                        updateStudyFragment.targetJsonObject = jsonObject;
                        URL url = new URL(jsonObject.getString("img"));
                        final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        updateStudyFragment.targetBitmap = bitmap;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView) rootView.findViewById(R.id.detail_study_thumbnail)).setImageBitmap(bitmap);
                                try {
                                    ((TextView) rootView.findViewById(R.id.detail_study_headText)).setText(jsonObject.getString("title"));
                                    ((TextView) rootView.findViewById(R.id.detail_study_detail_text)).setText(jsonObject.getString("note"));
                                    ((TextView) rootView.findViewById(R.id.detail_study_date_text)).setText(jsonObject.getString("signdate"));
                                    ((TextView) rootView.findViewById(R.id.detail_study_location_text)).setText(jsonObject.getString("station"));
                                    ((TextView) rootView.findViewById(R.id.detail_study_category_text)).setText(Util.categorys[jsonObject.getInt("catIdx") - 1]);
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
        member_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        member_scroll.setHasFixedSize(true);

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(String.format("%s/api/study/%d/students", getResources().getString(R.string.server_address), studyIdx)).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");

                        final DetailMemberAdapter.Data[] data = new DetailMemberAdapter.Data[jsonArray.length()];

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            data[i] = new DetailMemberAdapter.Data(object.getString("name"), object.getString("img"));
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                member_scroll.setAdapter(new DetailMemberAdapter(data));
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
                study_layout.setPadding(Util.dpToPx(20), 0, Util.dpToPx(20), 400);
            }
        });
        return rootView;
    }
}
