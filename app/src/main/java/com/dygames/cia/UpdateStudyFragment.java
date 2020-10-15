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

public class UpdateStudyFragment extends Fragment {
    public int studyIdx;
    public JSONObject targetJsonObject;
    public Bitmap targetBitmap;
    View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_update_study, container, false);

        final ImageView study_thumbnail = rootView.findViewById(R.id.update_study_thumbnail);
        final Spinner spinner = rootView.findViewById(R.id.update_study_category_spinner);
        final EditText study_title_editText = rootView.findViewById(R.id.update_study_title_editText);
        final EditText study_desc_editText = rootView.findViewById(R.id.update_study_desc_editText);
        final EditText study_location_editText = rootView.findViewById(R.id.update_study_location_editText);
        final DatePicker study_date_editText = rootView.findViewById(R.id.update_study_date_editText);

        try {
            study_thumbnail.setImageBitmap(targetBitmap);
            study_title_editText.setText(targetJsonObject.getString("title"));
            study_desc_editText.setText(targetJsonObject.getString("note"));
            String[] date = targetJsonObject.getString("signdate").split(" ")[0].split("-");
            study_date_editText.updateDate(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
            study_location_editText.setText(targetJsonObject.getString("station"));
            spinner.setSelection(targetJsonObject.getInt("catIdx") - 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Util.categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        study_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 11);
            }
        });

        rootView.findViewById(R.id.update_study_upload_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bitmap photo = ((BitmapDrawable) study_thumbnail.getDrawable()).getBitmap();

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
                                .add("catIdx", (spinner.getSelectedItemPosition() + 1) + "")
                                .build();

                        Request request = new Request.Builder()
                                .url(String.format("%s/api/study/%d", getResources().getString(R.string.server_address), studyIdx))
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
                                        Toast.makeText(getContext(), "스터디 수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "스터디 수정에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(UpdateStudyFragment.this).commit();
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
