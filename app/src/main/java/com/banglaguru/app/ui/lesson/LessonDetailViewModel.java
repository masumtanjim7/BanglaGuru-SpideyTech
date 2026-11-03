package com.banglaguru.app.ui.lesson;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.banglaguru.app.data.model.Lesson;
import com.banglaguru.app.data.model.Mcq;
import com.banglaguru.app.data.model.Progress;
import com.banglaguru.app.data.repository.ContentRepository;

import java.util.List;

public class LessonDetailViewModel extends AndroidViewModel {

    private final ContentRepository repository;

    public LessonDetailViewModel(@NonNull Application application) {
        super(application);
        repository = ContentRepository.getInstance(application);
    }

    public LiveData<Lesson> getLesson(String id) {
        return repository.getLessonById(id);
    }

    public LiveData<List<Mcq>> getLessonMcqs(String lessonId) {
        return repository.getMcqsByLesson(lessonId);
    }

    public LiveData<Boolean> isFavorite(String lessonId) {
        return repository.isFavorite(lessonId, "lesson");
    }

    public void toggleFavorite(String lessonId, boolean shouldBeFavorite) {
        repository.toggleFavorite(lessonId, "lesson", shouldBeFavorite);
    }

    public void markLessonCompleted(String category, String lessonId) {
        Progress p = new Progress();
        p.category = category;
        p.refId = lessonId;
        p.completed = true;
        p.timestamp = System.currentTimeMillis();
        repository.recordProgress(p);
    }
}
