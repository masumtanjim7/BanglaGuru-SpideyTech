package com.banglaguru.app.ui.favorite;

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
import com.banglaguru.app.ui.lesson.LessonAdapter;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        RecyclerView rv = view.findViewById(R.id.rv_favorites);
        TextView tvEmpty = view.findViewById(R.id.tv_empty);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        LessonAdapter adapter = new LessonAdapter(lesson -> {
            Bundle args = new Bundle();
            args.putString("lessonId", lesson.id);
            args.putString("category", lesson.category);
            Navigation.findNavController(view).navigate(R.id.action_favorite_to_lessonDetail, args);
        });
        rv.setAdapter(adapter);

        viewModel.getFavoriteLessons().observe(getViewLifecycleOwner(), lessons -> {
            adapter.submitList(lessons);
            boolean empty = lessons == null || lessons.isEmpty();
            tvEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
            rv.setVisibility(empty ? View.GONE : View.VISIBLE);
        });
    }
}
