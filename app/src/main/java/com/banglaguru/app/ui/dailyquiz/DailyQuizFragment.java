package com.banglaguru.app.ui.dailyquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.banglaguru.app.R;

public class DailyQuizFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daily_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_start_daily_quiz).setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("sessionType", "daily_quiz");
            args.putInt("count", 10);
            Navigation.findNavController(view).navigate(R.id.action_dailyQuiz_to_mcqQuestion, args);
        });
    }
}
