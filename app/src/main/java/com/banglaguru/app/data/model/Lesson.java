package com.banglaguru.app.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lessons")
public class Lesson {

    @PrimaryKey
    @NonNull
    public String id;

    public String category;      // "grammar" or "literature"
    public int order;
    public String title;
    public String definition;          // সংজ্ঞা
    public String explanation;         // বিস্তারিত ব্যাখ্যা
    public String rules;               // নিয়ম
    public String examples;            // উদাহরণ
    public String importantNotes;      // গুরুত্বপূর্ণ নোট
    public String examFacts;           // পরীক্ষায় আসা তথ্য
    public String type;                // null/"" for generic lessons, "writer" for author profiles
    public String imageName;           // drawable resource name for a portrait (e.g. "writer_rabindranath")

    public Lesson() {
    }

    @NonNull
    public String getId() {
        return id;
    }
}
