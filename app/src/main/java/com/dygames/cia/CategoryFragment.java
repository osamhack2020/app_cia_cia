package com.dygames.cia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CategoryFragment extends Fragment {
    public class Data {
        public String title;
        public String desc;
        public int thumbnailID;

        public Data(String title, String desc, int thumbnailID) {
            this.title = title;
            this.desc = desc;
            this.thumbnailID = thumbnailID;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        CategoryFragment.Data[] tut_data = new CategoryFragment.Data[]{new CategoryFragment.Data("타이틀1", "설명 1", R.drawable.ic_launcher_foreground),
                new CategoryFragment.Data("타이틀2", "설명 2", R.drawable.ic_launcher_foreground),
                new CategoryFragment.Data("타이틀3", "설명 3", R.drawable.ic_launcher_foreground),
                new CategoryFragment.Data("타이틀4", "설명 4", R.drawable.ic_launcher_foreground),
                new CategoryFragment.Data("타이틀5", "설명 5", R.drawable.ic_launcher_foreground),
                new CategoryFragment.Data("타이틀6", "설명 6", R.drawable.ic_launcher_foreground),
                new CategoryFragment.Data("타이틀7", "설명 7", R.drawable.ic_launcher_foreground),};
        LinearLayout category_layout = rootView.findViewById(R.id.category_layout);

        for (int i = 0; i < tut_data.length; i++) {
            View v = inflater.inflate(R.layout.view_course_bar, category_layout, false);
            ((ImageView) v.findViewById(R.id.course_bar_thumbnail)).setBackgroundResource(tut_data[i].thumbnailID);
            ((TextView) v.findViewById(R.id.course_bar_title)).setText(tut_data[i].title);
            ((TextView) v.findViewById(R.id.course_bar_desc)).setText(tut_data[i].desc);
            category_layout.addView(v);
        }

        final TextView study_headText = rootView.findViewById(R.id.category_study_headText);
        final TextView tut_headText = rootView.findViewById(R.id.category_tut_headText);

        study_headText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                study_headText.setTextColor(0xFF000000);
                tut_headText.setTextColor(0xFF909090);
            }
        });
        tut_headText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tut_headText.setTextColor(0xFF000000);
                study_headText.setTextColor(0xFF909090);
            }
        });

        Spinner category_spinner = rootView.findViewById(R.id.category_spinner);
        String[] spinnerArray = {"경제", "과학", "기술"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        category_spinner.setAdapter(spinnerArrayAdapter);

        Spinner sort_spinner = rootView.findViewById(R.id.sort_spinner);
        String[] sortArray = {"인기순", "날짜순", "이름순"};
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sortArray); //selected item will look like a spinner set from XML
        sortAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        sort_spinner.setAdapter(sortAdapter);
        return rootView;
    }
}
