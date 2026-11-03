package com.banglaguru.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.banglaguru.app.data.model.Mcq;

import java.util.List;

@Dao
public interface McqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Mcq> mcqs);

    @Query("SELECT COUNT(*) FROM mcqs")
    int count();

    @Query("SELECT * FROM mcqs WHERE category = :category ORDER BY RANDOM() LIMIT :limit")
    List<Mcq> getRandomByCategory(String category, int limit);

    @Query("SELECT * FROM mcqs ORDER BY RANDOM() LIMIT :limit")
    List<Mcq> getRandomMixed(int limit);

    @Query("SELECT * FROM mcqs WHERE lessonId = :lessonId")
    LiveData<List<Mcq>> getByLesson(String lessonId);

    @Query("SELECT * FROM mcqs WHERE id IN (:ids)")
    LiveData<List<Mcq>> getByIds(List<String> ids);

    @Query("SELECT * FROM mcqs ORDER BY RANDOM() LIMIT :limit")
    List<Mcq> getDailyQuiz(int limit);
}
