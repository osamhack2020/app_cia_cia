package com.dygames.cia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private DetailStudyFragment fragmentDetailStudy = new DetailStudyFragment();
    private DetailTutFragment fragmentDetailTut = new DetailTutFragment();

    public static class Data {
        public String title;
        public String desc;
        public int thumbnailID;
        public boolean isTut;

        public Data(String title, String desc, int thumbnailID, boolean isTut) {
            this.title = title;
            this.desc = desc;
            this.thumbnailID = thumbnailID;
            this.isTut = isTut;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView title;
        public TextView desc;

        public ViewHolder(View v) {
            super(v);
            this.thumbnail = v.findViewById(R.id.view_tut_thumbnail);
            this.title = v.findViewById(R.id.view_tut_title);
            this.desc = v.findViewById(R.id.view_tut_desc);
        }
    }

    Data[] data;

    public CourseAdapter(Data[] d) {
        this.data = d;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_course_item, parent, false);
        ViewHolder h = new ViewHolder(holderView);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = position;
        holder.thumbnail.setBackgroundResource(this.data[position].thumbnailID);
        holder.title.setText(this.data[position].title);
        holder.desc.setText(this.data[position].desc);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, data[pos].isTut ? fragmentDetailTut : fragmentDetailStudy).commitAllowingStateLoss();
                ((BottomNavigationView) ((FragmentActivity) v.getContext()).findViewById(R.id.navigationView)).getMenu().setGroupCheckable(0, false, true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

}
