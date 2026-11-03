package com.banglaguru.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banglaguru.app.R;

import java.util.ArrayList;
import java.util.List;

public class MoreFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.rv_more);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        List<SimpleSectionAdapter.Section> sections = new ArrayList<>();
        sections.add(new SimpleSectionAdapter.Section("mock_test", getString(R.string.section_mock_test)));
        sections.add(new SimpleSectionAdapter.Section("daily_quiz", getString(R.string.section_daily_quiz)));
        sections.add(new SimpleSectionAdapter.Section("favorite", getString(R.string.section_favorite)));
        sections.add(new SimpleSectionAdapter.Section("progress", getString(R.string.section_progress)));
        sections.add(new SimpleSectionAdapter.Section("settings", getString(R.string.section_settings)));

        rv.setAdapter(new SimpleSectionAdapter(sections, key -> {
            int destId;
            switch (key) {
                case "mock_test": destId = R.id.action_more_to_mockTest; break;
                case "daily_quiz": destId = R.id.action_more_to_dailyQuiz; break;
                case "favorite": destId = R.id.action_more_to_favorite; break;
                case "progress": destId = R.id.action_more_to_progress; break;
                default: destId = R.id.action_more_to_settings;
            }
            Navigation.findNavController(view).navigate(destId);
        }));
    }
}
