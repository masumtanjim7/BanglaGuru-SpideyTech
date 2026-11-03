package com.banglaguru.app.ui.lesson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banglaguru.app.R;
import com.banglaguru.app.data.model.Mcq;

import java.util.ArrayList;
import java.util.List;

public class LessonMcqAdapter extends RecyclerView.Adapter<LessonMcqAdapter.VH> {

    private List<Mcq> mcqs = new ArrayList<>();

    public void submitList(List<Mcq> newList) {
        mcqs = newList == null ? new ArrayList<>() : newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson_mcq, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Mcq mcq = mcqs.get(position);
        holder.question.setText((position + 1) + ". " + mcq.question);
        holder.optionA.setText("A. " + mcq.optionA);
        holder.optionB.setText("B. " + mcq.optionB);
        holder.optionC.setText("C. " + mcq.optionC);
        holder.optionD.setText("D. " + mcq.optionD);
        holder.answer.setText("সঠিক উত্তর: " + mcq.correctAnswer + " — " + mcq.explanation);
    }

    @Override
    public int getItemCount() {
        return mcqs.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView question, optionA, optionB, optionC, optionD, answer;

        VH(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.tv_question);
            optionA = itemView.findViewById(R.id.tv_option_a);
            optionB = itemView.findViewById(R.id.tv_option_b);
            optionC = itemView.findViewById(R.id.tv_option_c);
            optionD = itemView.findViewById(R.id.tv_option_d);
            answer = itemView.findViewById(R.id.tv_answer_explanation);
        }
    }
}
