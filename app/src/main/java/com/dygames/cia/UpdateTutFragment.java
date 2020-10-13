package com.dygames.cia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateTutFragment extends Fragment {
    public int tutIdx;
    public JSONObject targetJsonObject;
    public Bitmap targetBitmap;
    View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_update_study, container, false);

        final ImageView tut_thumbnail = rootView.findViewById(R.id.update_tut_thumbnail);
        final Spinner spinner = rootView.findViewById(R.id.update_tut_category_spinner);
        final EditText tut_title_editText = rootView.findViewById(R.id.update_tut_title_editText);
        final EditText tut_desc_editText = rootView.findViewById(R.id.update_tut_desc_editText);
        final EditText tut_tag_editText = rootView.findViewById(R.id.update_tut_tag_editText);

        try {
            tut_thumbnail.setImageBitmap(targetBitmap);
            tut_title_editText.setText(targetJsonObject.getString("title"));
            tut_desc_editText.setText(targetJsonObject.getString("note"));
            tut_tag_editText.setText(targetJsonObject.getString("tags"));
            spinner.setSelection(targetJsonObject.getInt("catIdx") - 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Util.categorys);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        tut_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 11);
            }
        });

        rootView.findViewById(R.id.update_study_upload_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bitmap photo = ((BitmapDrawable) tut_thumbnail.getDrawable()).getBitmap();

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
                                .add("title", tut_title_editText.getText().toString())
                                .add("img", String.format("%s%s", getResources().getString(R.string.server_address), fileName))
                                .add("note", tut_desc_editText.getText().toString())
                                .add("tags", tut_tag_editText.getText().toString())
                                .add("catIdx", (spinner.getSelectedItemPosition() + 1) + "")
                                .build();

                        Request request = new Request.Builder()
                                .url(String.format("%s/api/class/%d", getResources().getString(R.string.server_address), tutIdx))
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
                                        Toast.makeText(getContext(), "강의 수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "강의 수정에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(UpdateTutFragment.this).commit();
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
