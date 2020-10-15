package com.dygames.cia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailTutItemAdapter extends RecyclerView.Adapter<DetailTutItemAdapter.ViewHolder> {

    public DetailTutItemAdapter(Data[] data) {
        this.data = data;
    }

    public static class Data {
        public String title;
        public Bitmap thumbnailID;
        public int idx;

        public Data(String title, Bitmap thumbnailID, int idx) {
            this.title = title;
            this.thumbnailID = thumbnailID;
            this.idx = idx;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public ViewHolder(@NonNull View v) {
            super(v);

            this.title = v.findViewById(R.id.detail_tut_item_title);
            this.thumbnail = v.findViewById(R.id.detail_tut_item_thumbnail);
        }
    }

    Context context;
    Data[] data;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_detail_tut_item, parent, false);
        context = parent.getContext();
        ViewHolder h = new ViewHolder(holderView);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText(this.data[position].title);
        holder.thumbnail.setImageBitmap(this.data[position].thumbnailID);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setTitle("회차 삭제");
                dlgAlert.setMessage("이 회차를 삭제하시겠습니까?");
                dlgAlert.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        new Thread() {
                            public void run() {
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder().url(String.format("%s/api/class/curriculum/%d", context.getResources().getString(R.string.server_address), data[position].idx)).delete().build();
                                try {
                                    Response response = client.newCall(request).execute();
                                    if (response.code() == 200) {
                                        Log.d("DDDD", position + " : 회차 삭제 완료");
                                    } else {
                                        Log.d("DDDD", position + " : 회차 삭제 실패");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                });
                dlgAlert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dlgAlert.create().show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

}
