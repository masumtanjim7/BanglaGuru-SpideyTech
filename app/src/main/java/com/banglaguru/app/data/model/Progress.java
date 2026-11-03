package com.banglaguru.app.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "progress")
public class Progress {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    public String category;     // "grammar" | "literature" | "mcq" | "mock_test" | "daily_quiz"
    public String refId;        // lessonId or test session label
    public boolean completed;
    public int totalQuestions;
    public int correctAnswers;
    public long timestamp;

    public Progress() {
    }
}
