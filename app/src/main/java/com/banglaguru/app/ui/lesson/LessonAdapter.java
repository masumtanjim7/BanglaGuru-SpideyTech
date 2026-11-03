package com.banglaguru.app.ui.lesson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banglaguru.app.R;
import com.banglaguru.app.data.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.VH> {

    public interface OnLessonClick {
        void onClick(Lesson lesson);
    }

    private List<Lesson> lessons = new ArrayList<>();
    private final OnLessonClick listener;

    public LessonAdapter(OnLessonClick listener) {
        this.listener = listener;
    }

    public void submitList(List<Lesson> newList) {
        lessons = newList == null ? new ArrayList<>() : newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.order.setText(String.valueOf(lesson.order));
        holder.title.setText(lesson.title);
        holder.itemView.setOnClickListener(v -> listener.onClick(lesson));
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView order;
        TextView title;

        VH(View itemView) {
            super(itemView);
            order = itemView.findViewById(R.id.tv_lesson_order);
            title = itemView.findViewById(R.id.tv_lesson_title);
        }
    }
}
