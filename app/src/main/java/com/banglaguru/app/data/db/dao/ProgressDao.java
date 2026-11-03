package com.banglaguru.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.banglaguru.app.data.model.Progress;

import java.util.List;

@Dao
public interface ProgressDao {

    @Insert
    void insert(Progress progress);

    @Query("SELECT * FROM progress ORDER BY timestamp DESC")
    LiveData<List<Progress>> getAll();

    @Query("SELECT * FROM progress WHERE category = :category ORDER BY timestamp DESC")
    LiveData<List<Progress>> getByCategory(String category);

    @Query("SELECT COUNT(*) FROM progress WHERE category = 'grammar' AND completed = 1")
    LiveData<Integer> getCompletedGrammarLessonCount();

    @Query("SELECT COUNT(*) FROM progress WHERE category = 'literature' AND completed = 1")
    LiveData<Integer> getCompletedLiteratureLessonCount();

    @Query("SELECT SUM(totalQuestions) FROM progress WHERE category IN ('mcq','mock_test','daily_quiz')")
    LiveData<Integer> getTotalQuestionsAttempted();

    @Query("SELECT SUM(correctAnswers) FROM progress WHERE category IN ('mcq','mock_test','daily_quiz')")
    LiveData<Integer> getTotalCorrectAnswers();
}
