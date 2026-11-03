package com.banglaguru.app.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.banglaguru.app.data.db.dao.FavoriteDao;
import com.banglaguru.app.data.db.dao.LessonDao;
import com.banglaguru.app.data.db.dao.McqDao;
import com.banglaguru.app.data.db.dao.ProgressDao;
import com.banglaguru.app.data.model.Favorite;
import com.banglaguru.app.data.model.Lesson;
import com.banglaguru.app.data.model.Mcq;
import com.banglaguru.app.data.model.Progress;

@Database(
        entities = {Lesson.class, Mcq.class, Favorite.class, Progress.class},
        version = 2,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract LessonDao lessonDao();

    public abstract McqDao mcqDao();

    public abstract FavoriteDao favoriteDao();

    public abstract ProgressDao progressDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "bangla_guru.db"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
