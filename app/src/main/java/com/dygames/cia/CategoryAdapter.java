package com.dygames.cia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

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
        public String date;
        public int viewCount;
        public Bitmap thumbnailID;
        public String tags;
        public String name;

        public Data(String title, String date, Bitmap thumbnailID, int idx, boolean isTut, int catIdx, int viewCount, String tags, String name) {
            this.title = title;
            this.date = date;
            this.thumbnailID = thumbnailID;
            this.idx = idx;
            this.isTut = isTut;
            this.catIdx = catIdx;
            this.viewCount = viewCount;
            this.tags = tags;
            this.name = name;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView title;
        public TextView info;
        public TextView name;
        public TextView viewCount;
        public ChipGroup chipGroup;

        public ViewHolder(View v) {
            super(v);
            this.thumbnail = v.findViewById(R.id.course_bar_thumbnail);
            this.title = v.findViewById(R.id.course_bar_title);
            this.info = v.findViewById(R.id.course_bar_info);
            this.name = v.findViewById(R.id.course_bar_name_text);
            this.viewCount = v.findViewById(R.id.course_bar_view_text);
            this.chipGroup = v.findViewById(R.id.course_tag_parent);
        }
    }

    Context context;

    public void Init(ArrayList<Data> d) {
        this.unFilteredlist = d;
        this.filteredList = d;
    }

    public CategoryAdapter(ArrayList<Data> d) {
        Init(d);
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
        holder.info.setText(String.format("%s · %s", Util.categories[this.filteredList.get(position).catIdx - 1], this.filteredList.get(position).date.split(" ")[0]));
        holder.thumbnail.setImageBitmap(this.filteredList.get(position).thumbnailID);
        holder.name.setText(this.filteredList.get(position).name);
        holder.viewCount.setText(this.filteredList.get(position).viewCount + "");
        holder.chipGroup.removeAllViews();

        String[] tags = this.filteredList.get(position).tags.split(",");
        for (int i = 0; i < tags.length; i++) {
            Chip chip = new Chip(context, null, R.style.AppTheme);
            chip.setTextSize(12);
            chip.setText(tags[i].trim());
            chip.setClickable(true);
            chip.setFocusable(true);
            chip.setChipCornerRadius(10);
            holder.chipGroup.addView(chip);
        }
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
        Log.d("DDDD", filteredList.size() + "");
        return filteredList.size();
    }

}
