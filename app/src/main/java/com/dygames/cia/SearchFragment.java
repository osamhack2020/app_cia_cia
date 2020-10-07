package com.dygames.cia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        rootView.findViewById(R.id.search_layout).setPadding(Util.dpToPx(20), Util.dpToPx(20), Util.dpToPx(20), getActivity().findViewById(R.id.navigationView).getHeight());

        RecyclerView search_tut = rootView.findViewById(R.id.main_search_tut_scroll);
        search_tut.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        search_tut.setHasFixedSize(true);
        search_tut.setAdapter(new CourseAdapter(new CourseAdapter.Data[]
                {       new CourseAdapter.Data("타이틀 1", "설명 1", "", true),
                        new CourseAdapter.Data("타이틀 2", "설명 2", "", true),
                        new CourseAdapter.Data("타이틀 3", "설명 3", "", true),
                        new CourseAdapter.Data("타이틀 4", "설명 4", "", true),
                        new CourseAdapter.Data("타이틀 5", "설명 5", "", true),
                        new CourseAdapter.Data("타이틀 6", "설명 6", "", true),
                }));


        RecyclerView search_study = rootView.findViewById(R.id.main_search_study_scroll);
        search_study.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        search_study.setHasFixedSize(true);
        search_study.setAdapter(new CourseAdapter(new CourseAdapter.Data[]
                {       new CourseAdapter.Data("타이틀 11", "설명 11", "", false),
                        new CourseAdapter.Data("타이틀 22", "설명 22", "", false),
                        new CourseAdapter.Data("타이틀 33", "설명 33", "", false),
                        new CourseAdapter.Data("타이틀 44", "설명 44", "", false),
                        new CourseAdapter.Data("타이틀 55", "설명 55", "", false),
                        new CourseAdapter.Data("타이틀 66", "설명 66", "", false),
                }));


        ((SearchView) rootView.findViewById(R.id.main_search_view)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return rootView;
    }

}
