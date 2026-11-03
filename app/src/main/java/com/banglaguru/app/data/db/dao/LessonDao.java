package com.banglaguru.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.banglaguru.app.data.model.Lesson;

import java.util.List;

@Dao
public interface LessonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Lesson> lessons);

    @Query("SELECT COUNT(*) FROM lessons")
    int count();

    @Query("SELECT * FROM lessons WHERE category = :category ORDER BY `order` ASC")
    LiveData<List<Lesson>> getLessonsByCategory(String category);

    @Query("SELECT * FROM lessons WHERE id = :id LIMIT 1")
    LiveData<Lesson> getLessonById(String id);

    @Query("SELECT * FROM lessons WHERE id IN (:ids)")
    LiveData<List<Lesson>> getLessonsByIds(List<String> ids);
}
