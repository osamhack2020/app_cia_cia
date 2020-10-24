package com.dygames.cia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.net.URL;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailTutFragment extends Fragment {
    public int tutIdx;

    UpdateTutFragment updateTutFragment = new UpdateTutFragment();
    UploadCurFragment uploadCurFragment = new UploadCurFragment();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_detail_tut, container, false);


        final View fab_delete = rootView.findViewById(R.id.detail_tut_fab_delete);
        final View fab_quit = rootView.findViewById(R.id.detail_tut_fab_quit);
        final View fab_sign = rootView.findViewById(R.id.detail_tut_fab_sign);
        final View fab_update = rootView.findViewById(R.id.detail_tut_fab_update);
        final View fab_upload = rootView.findViewById(R.id.detail_tut_fab_upload);

        rootView.findViewById(R.id.detail_tut_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_delete.setVisibility(fab_delete.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                fab_quit.setVisibility(fab_quit.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                fab_sign.setVisibility(fab_sign.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                fab_update.setVisibility(fab_update.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                fab_upload.setVisibility(fab_upload.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
            }
        });


        fab_delete.setOnClickListener(new View.OnClickListener() {
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

        fab_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTutFragment.tutIdx = tutIdx;
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, updateTutFragment).addToBackStack(null).commitAllowingStateLoss();
            }
        });

        fab_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(String.format("%s/api/class/%d/students", getResources().getString(R.string.server_address), tutIdx))
                                .addHeader("Authorization", Util.userHSID)
                                .post(new MultipartBody.Builder().addFormDataPart("", "").build())
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.code() == 200) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "강의 가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
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
                                .url(String.format("%s/api/class/%d/students", getResources().getString(R.string.server_address), tutIdx))
                                .addHeader("Authorization", Util.userHSID)
                                .delete()
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.code() == 200) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "강의 탈퇴에 성공하였습니다.", Toast.LENGTH_SHORT).show();
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

        fab_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadCurFragment.idx = tutIdx;
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, uploadCurFragment).addToBackStack(null).commitAllowingStateLoss();
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
                        updateTutFragment.targetJsonObject = jsonObject;
                        URL url = new URL(jsonObject.getString("img"));
                        final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        updateTutFragment.targetBitmap = bitmap;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView) rootView.findViewById(R.id.detail_tut_thumbnail)).setImageBitmap(bitmap);
                                try {
                                    ((TextView) rootView.findViewById(R.id.detail_tut_info_headText)).setText(String.format("%s·%s", Util.categories[jsonObject.getInt("catIdx") - 1], jsonObject.getString("userName")));
                                    ((TextView) rootView.findViewById(R.id.detail_tut_headText)).setText(String.format("%s", jsonObject.getString("title")));
                                    ((TextView) rootView.findViewById(R.id.detail_tut_desc_text)).setText(jsonObject.getString("note"));
                                    ((TextView) rootView.findViewById(R.id.detail_tut_view_text)).setText(jsonObject.getString("viewCount"));
                                    ((TextView) rootView.findViewById(R.id.detail_tut_date_text)).setText(jsonObject.getString("regdate").split(" ")[0]);

                                    String[] tags = jsonObject.getString("tags").split(",");
                                    for (int i = 0; i < tags.length; i++) {
                                        Chip chip = new Chip(getContext(), null, R.style.AppTheme);
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

        final RecyclerView tut_scroll = rootView.findViewById(R.id.detail_tut_scroll);
        tut_scroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        tut_scroll.setHasFixedSize(true);

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(String.format("%s/api/class/%d/curriculum", getResources().getString(R.string.server_address), tutIdx)).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("list");

                        final DetailTutItemAdapter.Data[] data = new DetailTutItemAdapter.Data[jsonArray.length()];

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            data[i] = new DetailTutItemAdapter.Data(object.getString("title"), BitmapFactory.decodeStream(new URL(object.getString("img")).openConnection().getInputStream()), object.getInt("idx"), object.getString("videoPath"));
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tut_scroll.setAdapter(new DetailTutItemAdapter(data));
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
                tut_layout.setPadding(Util.dpToPx(20), 0, Util.dpToPx(20), tut_layout.getRootView().findViewById(R.id.detail_tut_thumbnail).getHeight());
            }
        });
        return rootView;
    }
}
