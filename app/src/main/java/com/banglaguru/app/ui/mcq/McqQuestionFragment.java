package com.banglaguru.app.ui.mcq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.banglaguru.app.R;
import com.banglaguru.app.data.model.Mcq;

import java.util.List;

public class McqQuestionFragment extends Fragment {

    private McqViewModel viewModel;

    private TextView tvProgress;
    private TextView tvQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbA, rbB, rbC, rbD;
    private MaterialButton btnPrevious, btnNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcq_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(McqViewModel.class);

        tvProgress = view.findViewById(R.id.tv_progress);
        tvQuestion = view.findViewById(R.id.tv_question);
        rgOptions = view.findViewById(R.id.rg_options);
        rbA = view.findViewById(R.id.rb_a);
        rbB = view.findViewById(R.id.rb_b);
        rbC = view.findViewById(R.id.rb_c);
        rbD = view.findViewById(R.id.rb_d);
        btnPrevious = view.findViewById(R.id.btn_previous);
        btnNext = view.findViewById(R.id.btn_next);

        Bundle args = getArguments();
        String sessionType = args != null ? args.getString("sessionType", "mcq") : "mcq";
        String topic = args != null ? args.getString("topic", "random") : "random";
        int count = args != null ? args.getInt("count", 10) : 10;

        if (savedInstanceState == null) {
            switch (sessionType) {
                case "mock_test":
                    viewModel.loadMockTest(count);
                    break;
                case "daily_quiz":
                    viewModel.loadDailyQuiz(count);
                    break;
                default:
                    viewModel.loadPracticeSet(topic, count);
            }
        }

        rgOptions.setOnCheckedChangeListener((group, checkedId) -> {
            String selected = null;
            if (checkedId == R.id.rb_a) selected = "A";
            else if (checkedId == R.id.rb_b) selected = "B";
            else if (checkedId == R.id.rb_c) selected = "C";
            else if (checkedId == R.id.rb_d) selected = "D";
            if (selected != null) {
                viewModel.answerCurrent(selected);
            }
        });

        btnPrevious.setOnClickListener(v -> {
            viewModel.previousQuestion();
            renderCurrentQuestion();
        });

        btnNext.setOnClickListener(v -> {
            List<Mcq> questions = viewModel.getQuestions().getValue();
            Integer idx = viewModel.getCurrentIndex().getValue();
            if (questions != null && idx != null && idx == questions.size() - 1) {
                viewModel.finishAndRecordProgress();
                Navigation.findNavController(view).navigate(R.id.action_mcqQuestion_to_mcqResult);
            } else {
                viewModel.nextQuestion();
                renderCurrentQuestion();
            }
        });

        viewModel.getQuestions().observe(getViewLifecycleOwner(), questions -> renderCurrentQuestion());
        viewModel.getCurrentIndex().observe(getViewLifecycleOwner(), idx -> renderCurrentQuestion());
    }

    private void renderCurrentQuestion() {
        List<Mcq> questions = viewModel.getQuestions().getValue();
        Integer idx = viewModel.getCurrentIndex().getValue();
        if (questions == null || questions.isEmpty() || idx == null || idx >= questions.size()) {
            return;
        }
        Mcq mcq = questions.get(idx);
        tvProgress.setText(getString(R.string.question_progress, idx + 1, questions.size()));
        tvQuestion.setText(mcq.question);

        rgOptions.setOnCheckedChangeListener(null);
        rbA.setText("A. " + mcq.optionA);
        rbB.setText("B. " + mcq.optionB);
        rbC.setText("C. " + mcq.optionC);
        rbD.setText("D. " + mcq.optionD);

        String savedAnswer = viewModel.getAnswerFor(mcq.id);
        rgOptions.clearCheck();
        if ("A".equals(savedAnswer)) rbA.setChecked(true);
        else if ("B".equals(savedAnswer)) rbB.setChecked(true);
        else if ("C".equals(savedAnswer)) rbC.setChecked(true);
        else if ("D".equals(savedAnswer)) rbD.setChecked(true);

        rgOptions.setOnCheckedChangeListener((group, checkedId) -> {
            String selected = null;
            if (checkedId == R.id.rb_a) selected = "A";
            else if (checkedId == R.id.rb_b) selected = "B";
            else if (checkedId == R.id.rb_c) selected = "C";
            else if (checkedId == R.id.rb_d) selected = "D";
            if (selected != null) {
                viewModel.answerCurrent(selected);
            }
        });

        btnPrevious.setEnabled(idx > 0);
        btnNext.setText(idx == questions.size() - 1 ? getString(R.string.btn_submit) : getString(R.string.btn_next));
    }
}
