package com.dygames.cia;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

public class UploadCurFragment extends Fragment {
    View rootView;
    public int idx;

    Uri video_URI;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == Activity.RESULT_OK && null != data) {
            video_URI = data.getData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_upload_cur, container, false);

        final ImageView cur_thumbnail = rootView.findViewById(R.id.upload_cur_thumbnail);
        cur_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://media/external/images/media");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent, 12);
            }
        });

        rootView.findViewById(R.id.upload_cur_upload_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bitmap photo = ((BitmapDrawable) cur_thumbnail.getDrawable()).getBitmap();

                final EditText cur_title_editText = rootView.findViewById(R.id.upload_cur_title_editText);
                final EditText cur_desc_editText = rootView.findViewById(R.id.upload_cur_desc_editText);

                new Thread() {
                    public void run() {

                        ContentResolver contentResolver = getContext().getContentResolver();
                        final String contentType = contentResolver.getType(video_URI);
                        AssetFileDescriptor fd = null;

                        try {
                            fd = contentResolver.openAssetFileDescriptor(video_URI, "r");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                        final AssetFileDescriptor finalFd = fd;
                        RequestBody videoFile = new RequestBody() {
                            @Override
                            public long contentLength() {
                                return finalFd.getDeclaredLength();
                            }

                            @Override
                            public MediaType contentType() {
                                return MediaType.parse(contentType);
                            }

                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void writeTo(BufferedSink sink) throws IOException {
                                try (InputStream is = finalFd.createInputStream()) {
                                    sink.writeAll(Okio.buffer(Okio.source(is)));
                                }
                            }
                        };

                        String fileName = "profile.png";
                        try {
                            RequestBody req = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("video", fileName, videoFile)
                                    .build();

                            Request request = new Request.Builder()
                                    .url(String.format("%s/file/upload", getResources().getString(R.string.server_address)))
                                    .post(req)
                                    .build();

                            OkHttpClient client = new OkHttpClient();
                            Response response = client.newCall(request).execute();
                            fileName = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        final RequestBody requestBody = new FormBody.Builder()
                                .add("title", cur_title_editText.getText().toString())
                                .add("img", String.format("%s%s", getResources().getString(R.string.server_address), fileName))
                                .add("note", cur_desc_editText.getText().toString())
                                .add("videopath", "")
                                .add("numb", "0")
                                .add("classIdx", idx + "")
                                .build();

                        Request request = new Request.Builder()
                                .url(String.format("%s/api/curriculum/regist", getResources().getString(R.string.server_address)))
                                .addHeader("Authorization", Util.userHSID)
                                .post(requestBody)
                                .build();
                        try {
                            final Response response = new OkHttpClient().newCall(request).execute();

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.code() != 200) {
                                        Toast.makeText(getContext(), "강의 회차 생성에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "강의 회차 생성에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(UploadCurFragment.this).commit();
                                    }
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        return rootView;
    }
}
