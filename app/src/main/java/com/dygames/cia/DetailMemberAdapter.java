package com.dygames.cia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailMemberAdapter extends RecyclerView.Adapter<DetailMemberAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.detail_member_name);
            this.thumbnail = itemView.findViewById(R.id.detail_member_thumbnail);
        }
    }

    public static class Data {
        public String name;
        public String thumbnailID;

        public Data(String name, String thumbnailID) {
            this.name = name;
            this.thumbnailID = thumbnailID;
        }
    }

    Data[] data;
    Context context;

    public DetailMemberAdapter(Data[] data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_detail_study_member, parent, false);
        DetailMemberAdapter.ViewHolder h = new DetailMemberAdapter.ViewHolder(holderView);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(data[position].name);

        new Thread() {
            public void run() {
                URL url = null;
                try {
                    url = new URL(data[position].thumbnailID);
                    final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.thumbnail.setImageBitmap(bitmap);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

}
