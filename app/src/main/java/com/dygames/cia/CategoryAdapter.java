package com.dygames.cia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements Filterable {

    private DetailStudyFragment fragmentDetailStudy = new DetailStudyFragment();
    private DetailTutFragment fragmentDetailTut = new DetailTutFragment();


    public ArrayList<Data> unFilteredlist;
    public ArrayList<Data> filteredList;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                int catIdx = Integer.parseInt(constraint.toString());
                ArrayList<Data> filteringList = new ArrayList<>();
                for (Data data : unFilteredlist) {
                    if (data.catIdx == catIdx) {
                        filteringList.add(data);
                    }
                }
                filteredList = filteringList;
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<Data>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class Data {
        public int catIdx;
        public int idx;
        public boolean isTut;
        public String title;
        public String desc;
        public Bitmap thumbnailID;

        public Data(String title, String desc, Bitmap thumbnailID, int idx, boolean isTut, int catIdx) {
            this.title = title;
            this.desc = desc;
            this.thumbnailID = thumbnailID;
            this.idx = idx;
            this.isTut = isTut;
            this.catIdx = catIdx;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView title;
        public TextView desc;

        public ViewHolder(View v) {
            super(v);
            this.thumbnail = v.findViewById(R.id.course_bar_thumbnail);
            this.title = v.findViewById(R.id.course_bar_title);
            this.desc = v.findViewById(R.id.course_bar_desc);
        }
    }

    Context context;

    public CategoryAdapter(ArrayList<Data> d) {
        this.unFilteredlist = d;
        this.filteredList = d;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_course_bar, parent, false);
        ViewHolder h = new ViewHolder(holderView);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final int pos = position;
        holder.title.setText(this.filteredList.get(position).title);
        holder.desc.setText(this.filteredList.get(position).desc);
        holder.thumbnail.setImageBitmap(this.filteredList.get(position).thumbnailID);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, filteredList.get(pos).isTut ? fragmentDetailTut : fragmentDetailStudy).addToBackStack(null).commitAllowingStateLoss();
                fragmentDetailStudy.studyIdx = filteredList.get(pos).idx;
                fragmentDetailTut.tutIdx = filteredList.get(pos).idx;
                ((BottomNavigationView) ((FragmentActivity) v.getContext()).findViewById(R.id.navigationView)).getMenu().setGroupCheckable(0, false, true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

}
