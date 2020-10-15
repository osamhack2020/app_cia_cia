package com.dygames.cia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private DetailStudyFragment fragmentDetailStudy = new DetailStudyFragment();
    private DetailTutFragment fragmentDetailTut = new DetailTutFragment();

    public static class Data {
        public String title;
        public int categoryIdx;
        public String userName;
        public int viewCount;
        public String date;
        public String thumbnailID;
        public int idx;
        public boolean isTut;

        public Data(String title, int categoryIdx, String userName, int viewCount, String date, String thumbnailID, int idx, boolean isTut) {
            this.title = title;
            this.categoryIdx = categoryIdx;
            this.userName = userName;
            this.viewCount = viewCount;
            this.date = date;
            this.thumbnailID = thumbnailID;
            this.idx = idx;
            this.isTut = isTut;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView info;
        public TextView title;
        public TextView viewCount;
        public TextView date;

        public ViewHolder(View v) {
            super(v);
            this.thumbnail = v.findViewById(R.id.view_tut_thumbnail);
            this.info = v.findViewById(R.id.view_tut_info);
            this.title = v.findViewById(R.id.view_tut_title);
            this.viewCount = v.findViewById(R.id.view_course_view_text);
            this.date = v.findViewById(R.id.view_course_date_text);
        }
    }

    public Data[] data;
    Context context;

    public CourseAdapter(Data[] d) {
        this.data = d;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_course_item, parent, false);
        ViewHolder h = new ViewHolder(holderView);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final int pos = position;
        final String finalThumbnailID = this.data[pos].thumbnailID;
        new Thread() {
            public void run() {
                URL url = null;
                try {
                    url = new URL(finalThumbnailID);
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
        holder.title.setText(this.data[position].title);
        holder.info.setText(Util.categories[this.data[position].categoryIdx] + " · " + this.data[position].userName);
        holder.viewCount.setText(this.data[position].viewCount + "");
        holder.date.setText(this.data[position].date.split(" ")[0]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, data[pos].isTut ? fragmentDetailTut : fragmentDetailStudy).addToBackStack(null).commitAllowingStateLoss();
                fragmentDetailStudy.studyIdx = data[pos].idx;
                fragmentDetailTut.tutIdx = data[pos].idx;
                ((BottomNavigationView) ((FragmentActivity) v.getContext()).findViewById(R.id.navigationView)).getMenu().setGroupCheckable(0, false, true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

}
