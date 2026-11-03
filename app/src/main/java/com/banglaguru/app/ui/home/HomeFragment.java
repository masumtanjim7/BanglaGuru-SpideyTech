package com.banglaguru.app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banglaguru.app.R;
import com.banglaguru.app.ui.SimpleSectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        TextView tvLessonsCompleted = view.findViewById(R.id.tv_lessons_completed);
        TextView tvQuestionsAttempted = view.findViewById(R.id.tv_questions_attempted);

        viewModel.getCompletedGrammarLessonCount().observe(getViewLifecycleOwner(), grammarCount -> {
            Integer litCount = viewModel.getCompletedLiteratureLessonCount().getValue();
            int total = (grammarCount == null ? 0 : grammarCount) + (litCount == null ? 0 : litCount);
            tvLessonsCompleted.setText(String.valueOf(total));
        });
        viewModel.getCompletedLiteratureLessonCount().observe(getViewLifecycleOwner(), litCount -> {
            Integer grammarCount = viewModel.getCompletedGrammarLessonCount().getValue();
            int total = (grammarCount == null ? 0 : grammarCount) + (litCount == null ? 0 : litCount);
            tvLessonsCompleted.setText(String.valueOf(total));
        });
        viewModel.getTotalQuestionsAttempted().observe(getViewLifecycleOwner(), count ->
                tvQuestionsAttempted.setText(String.valueOf(count == null ? 0 : count)));

        RecyclerView rv = view.findViewById(R.id.rv_home_sections);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        List<SimpleSectionAdapter.Section> sections = new ArrayList<>();
        sections.add(new SimpleSectionAdapter.Section("grammar", getString(R.string.section_grammar_title)));
        sections.add(new SimpleSectionAdapter.Section("literature", getString(R.string.section_literature_title)));
        sections.add(new SimpleSectionAdapter.Section("mcq", getString(R.string.section_mcq_practice)));
        sections.add(new SimpleSectionAdapter.Section("mock_test", getString(R.string.section_mock_test)));
        sections.add(new SimpleSectionAdapter.Section("daily_quiz", getString(R.string.section_daily_quiz)));
        sections.add(new SimpleSectionAdapter.Section("favorite", getString(R.string.section_favorite)));
        sections.add(new SimpleSectionAdapter.Section("progress", getString(R.string.section_progress)));
        sections.add(new SimpleSectionAdapter.Section("settings", getString(R.string.section_settings)));

        rv.setAdapter(new SimpleSectionAdapter(sections, this::navigateToSection));
    }

    private void navigateToSection(String key) {
        Bundle args = new Bundle();
        switch (key) {
            case "grammar":
            case "literature":
                args.putString("category", key);
                Navigation.findNavController(requireView()).navigate(R.id.lessonListFragment, args);
                break;
            case "mcq":
                Navigation.findNavController(requireView()).navigate(R.id.mcqSetupFragment);
                break;
            case "mock_test":
                Navigation.findNavController(requireView()).navigate(R.id.mockTestFragment);
                break;
            case "daily_quiz":
                Navigation.findNavController(requireView()).navigate(R.id.dailyQuizFragment);
                break;
            case "favorite":
                Navigation.findNavController(requireView()).navigate(R.id.favoriteFragment);
                break;
            case "progress":
                Navigation.findNavController(requireView()).navigate(R.id.progressFragment);
                break;
            case "settings":
                Navigation.findNavController(requireView()).navigate(R.id.settingsFragment);
                break;
        }
    }
}
