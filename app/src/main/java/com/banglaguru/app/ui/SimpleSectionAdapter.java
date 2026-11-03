package com.banglaguru.app.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banglaguru.app.R;

import java.util.List;

public class SimpleSectionAdapter extends RecyclerView.Adapter<SimpleSectionAdapter.VH> {

    public interface OnSectionClick {
        void onClick(String key);
    }

    public static class Section {
        public final String key;
        public final String title;

        public Section(String key, String title) {
            this.key = key;
            this.title = title;
        }
    }

    private final List<Section> sections;
    private final OnSectionClick listener;

    public SimpleSectionAdapter(List<Section> sections, OnSectionClick listener) {
        this.sections = sections;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_section, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Section section = sections.get(position);
        holder.title.setText(section.title);
        holder.itemView.setOnClickListener(v -> listener.onClick(section.key));
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView title;

        VH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_section_title);
        }
    }
}
