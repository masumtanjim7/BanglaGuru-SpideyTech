package com.banglaguru.app.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mcqs")
public class Mcq {

    @PrimaryKey
    @NonNull
    public String id;

    public String category;     // "grammar" or "literature"
    public String lessonId;     // links to Lesson.id, nullable for mixed/random pool
    public String question;
    public String optionA;
    public String optionB;
    public String optionC;
    public String optionD;
    public String correctAnswer; // "A" | "B" | "C" | "D"
    public String explanation;

    public Mcq() {
    }

    @NonNull
    public String getId() {
        return id;
    }
}
