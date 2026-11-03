package com.banglaguru.app.ui.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banglaguru.app.R;

public class LessonListFragment extends Fragment {

    private LessonListViewModel viewModel;
    private String category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lesson_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        category = getArguments() != null ? getArguments().getString("category", "grammar") : "grammar";
        viewModel = new ViewModelProvider(this).get(LessonListViewModel.class);

        RecyclerView rv = view.findViewById(R.id.rv_lessons);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        LessonAdapter adapter = new LessonAdapter(lesson -> {
            Bundle args = new Bundle();
            args.putString("lessonId", lesson.id);
            args.putString("category", category);
            Navigation.findNavController(view).navigate(R.id.action_lessonList_to_lessonDetail, args);
        });
        rv.setAdapter(adapter);

        viewModel.getLessons(category).observe(getViewLifecycleOwner(), adapter::submitList);
    }
}
