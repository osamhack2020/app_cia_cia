package com.dygames.cia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        public int thumbnailID;

        public Data(String name, int thumbnailID) {
            this.name = name;
            this.thumbnailID = thumbnailID;
        }
    }

    Data[] data;

    public DetailMemberAdapter(Data[] data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_detail_study_member, parent, false);
        DetailMemberAdapter.ViewHolder h = new DetailMemberAdapter.ViewHolder(holderView);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(data[position].name);
        holder.thumbnail.setBackgroundResource(data[position].thumbnailID);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

}
