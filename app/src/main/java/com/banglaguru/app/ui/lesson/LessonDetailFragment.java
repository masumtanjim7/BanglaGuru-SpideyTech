package com.banglaguru.app.ui.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banglaguru.app.R;

public class LessonDetailFragment extends Fragment {

    private LessonDetailViewModel viewModel;
    private String lessonId;
    private String category;
    private boolean currentFavoriteState = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lesson_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lessonId = getArguments() != null ? getArguments().getString("lessonId") : null;
        category = getArguments() != null ? getArguments().getString("category", "grammar") : "grammar";
        viewModel = new ViewModelProvider(this).get(LessonDetailViewModel.class);

        TextView tvTitle = view.findViewById(R.id.tv_title);
        ImageButton btnFavorite = view.findViewById(R.id.btn_favorite);
        MaterialButton btnMarkComplete = view.findViewById(R.id.btn_mark_complete);
        View cardWriterImage = view.findViewById(R.id.card_writer_image);
        ImageView ivWriter = view.findViewById(R.id.iv_writer);

        viewModel.getLesson(lessonId).observe(getViewLifecycleOwner(), lesson -> {
            if (lesson == null) return;
            tvTitle.setText(lesson.title);

            boolean isWriter = "writer".equals(lesson.type);

            // Section labels differ for author profiles vs. generic lessons.
            bindBlock(view, R.id.block_definition, isWriter ? R.string.label_writer_bio : R.string.label_definition);
            bindBlock(view, R.id.block_explanation, isWriter ? R.string.label_writer_life : R.string.label_explanation);
            bindBlock(view, R.id.block_rules, isWriter ? R.string.label_writer_works : R.string.label_rules);
            bindBlock(view, R.id.block_examples, isWriter ? R.string.label_writer_famous : R.string.label_examples);
            bindBlock(view, R.id.block_notes, isWriter ? R.string.label_writer_notes : R.string.label_important_notes);
            bindBlock(view, R.id.block_exam_facts, R.string.label_exam_facts);

            setBlockContent(view, R.id.block_definition, lesson.definition);
            setBlockContent(view, R.id.block_explanation, lesson.explanation);
            setBlockContent(view, R.id.block_rules, lesson.rules);
            setBlockContent(view, R.id.block_examples, lesson.examples);
            setBlockContent(view, R.id.block_notes, lesson.importantNotes);
            setBlockContent(view, R.id.block_exam_facts, lesson.examFacts);

            // Show a portrait for author profiles; fall back to a placeholder if the
            // named drawable isn't bundled yet.
            if (isWriter) {
                cardWriterImage.setVisibility(View.VISIBLE);
                int resId = 0;
                if (!TextUtils.isEmpty(lesson.imageName) && getContext() != null) {
                    resId = getResources().getIdentifier(
                            lesson.imageName, "drawable", getContext().getPackageName());
                }
                ivWriter.setImageResource(resId != 0 ? resId : R.drawable.ic_writer_placeholder);
            } else {
                cardWriterImage.setVisibility(View.GONE);
            }
        });

        viewModel.isFavorite(lessonId).observe(getViewLifecycleOwner(), isFav -> {
            currentFavoriteState = isFav != null && isFav;
            btnFavorite.setImageResource(currentFavoriteState ? R.drawable.ic_star_filled : R.drawable.ic_star_outline);
        });

        btnFavorite.setOnClickListener(v -> viewModel.toggleFavorite(lessonId, !currentFavoriteState));

        btnMarkComplete.setOnClickListener(v -> {
            viewModel.markLessonCompleted(category, lessonId);
            btnMarkComplete.setText(R.string.btn_completed);
            btnMarkComplete.setAlpha(0.92f);
        });

        RecyclerView rvMcqs = view.findViewById(R.id.rv_lesson_mcqs);
        rvMcqs.setLayoutManager(new LinearLayoutManager(getContext()));
        LessonMcqAdapter mcqAdapter = new LessonMcqAdapter();
        rvMcqs.setAdapter(mcqAdapter);
        viewModel.getLessonMcqs(lessonId).observe(getViewLifecycleOwner(), mcqAdapter::submitList);
    }

    private void bindBlock(View root, int blockId, int labelRes) {
        View block = root.findViewById(blockId);
        TextView label = block.findViewById(R.id.tv_block_label);
        label.setText(getString(labelRes));
    }

    private void setBlockContent(View root, int blockId, String content) {
        View block = root.findViewById(blockId);
        if (TextUtils.isEmpty(content)) {
            block.setVisibility(View.GONE);
            return;
        }
        block.setVisibility(View.VISIBLE);
        TextView contentView = block.findViewById(R.id.tv_block_content);
        contentView.setText(content);
    }
}
