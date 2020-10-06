package com.dygames.cia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailTutItemAdapter extends RecyclerView.Adapter<DetailTutItemAdapter.ViewHolder> {

    public DetailTutItemAdapter(Data[] data) {
        this.data = data;
    }

    public static class Data {
        public String title;
        public int thumbnailID;

        public Data(String title, int thumbnailID) {
            this.title = title;
            this.thumbnailID = thumbnailID;
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

    Data[] data;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_detail_tut_item, parent, false);
        ViewHolder h = new ViewHolder(holderView);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(this.data[position].title);
        holder.thumbnail.setBackgroundResource(this.data[position].thumbnailID);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

}
