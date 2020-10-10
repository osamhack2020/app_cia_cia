package com.dygames.cia;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class UploadStudyFragment extends Fragment {
    View rootView;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) rootView.findViewById(R.id.upload_study_thumbnail);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_upload_study, container, false);

        final ImageView study_thumbnail = rootView.findViewById(R.id.upload_study_thumbnail);
        study_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 11);
            }
        });

        rootView.findViewById(R.id.upload_study_upload_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bitmap photo = ((BitmapDrawable) study_thumbnail.getDrawable()).getBitmap();

                final EditText study_title_editText = rootView.findViewById(R.id.upload_study_title_editText);
                final EditText study_desc_editText = rootView.findViewById(R.id.upload_study_desc_editText);
                final EditText study_location_editText = rootView.findViewById(R.id.upload_study_location_editText);
                final DatePicker study_date_editText = rootView.findViewById(R.id.upload_study_date_editText);

                new Thread() {
                    public void run() {

                        String fileName = "profile.png";
                        try {
                            Bitmap resizedImage = Bitmap.createScaledBitmap(photo, 512, 512, true);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream(photo.getWidth() * photo.getHeight());
                            resizedImage.compress(Bitmap.CompressFormat.PNG, 100, bos);

                            RequestBody req = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("image", fileName,
                                            RequestBody.create(MediaType.parse("image/png"), bos.toByteArray()))
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
                                .add("title", study_title_editText.getText().toString())
                                .add("img", String.format("%s%s", getResources().getString(R.string.server_address), fileName))
                                .add("note", study_desc_editText.getText().toString())
                                .add("station", study_location_editText.getText().toString())
                                .add("signdate", String.format("%d-%d-%d", study_date_editText.getYear(), study_date_editText.getMonth(), study_date_editText.getDayOfMonth()))
                                .add("maxPeople", "10")
                                .build();

                        Request request = new Request.Builder()
                                .url(String.format("%s/api/study/regist", getResources().getString(R.string.server_address)))
                                .addHeader("Authorization", Util.userHSID)
                                .post(requestBody)
                                .build();
                        try {
                            final Response response = new OkHttpClient().newCall(request).execute();

                            Log.d("DDDD", response.code() + "");

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.code() != 200) {
                                        Toast.makeText(getContext(), "스터디 생성에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "스터디 생성에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(UploadStudyFragment.this).commit();
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
