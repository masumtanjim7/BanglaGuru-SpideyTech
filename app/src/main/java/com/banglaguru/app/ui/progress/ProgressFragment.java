package com.banglaguru.app.ui.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.banglaguru.app.R;

public class ProgressFragment extends Fragment {

    private ProgressViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProgressViewModel.class);

        bindStat(view, R.id.stat_grammar, getString(R.string.progress_grammar_lessons));
        bindStat(view, R.id.stat_literature, getString(R.string.progress_literature_lessons));
        bindStat(view, R.id.stat_questions, getString(R.string.progress_total_questions));
        bindStat(view, R.id.stat_correct, getString(R.string.progress_total_correct));

        viewModel.getCompletedGrammarLessonCount().observe(getViewLifecycleOwner(), count ->
                setStatValue(view, R.id.stat_grammar, count));
        viewModel.getCompletedLiteratureLessonCount().observe(getViewLifecycleOwner(), count ->
                setStatValue(view, R.id.stat_literature, count));
        viewModel.getTotalQuestionsAttempted().observe(getViewLifecycleOwner(), count ->
                setStatValue(view, R.id.stat_questions, count));
        viewModel.getTotalCorrectAnswers().observe(getViewLifecycleOwner(), count ->
                setStatValue(view, R.id.stat_correct, count));
    }

    private void bindStat(View root, int statId, String label) {
        View stat = root.findViewById(statId);
        TextView labelView = stat.findViewById(R.id.tv_stat_label);
        labelView.setText(label);
    }

    private void setStatValue(View root, int statId, Integer value) {
        View stat = root.findViewById(statId);
        TextView valueView = stat.findViewById(R.id.tv_stat_value);
        valueView.setText(String.valueOf(value == null ? 0 : value));
    }
}
