package com.banglaguru.app.ui.lesson;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.banglaguru.app.data.model.Lesson;
import com.banglaguru.app.data.repository.ContentRepository;

import java.util.List;

public class LessonListViewModel extends AndroidViewModel {

    private final ContentRepository repository;

    public LessonListViewModel(@NonNull Application application) {
        super(application);
        repository = ContentRepository.getInstance(application);
    }

    public LiveData<List<Lesson>> getLessons(String category) {
        if ("literature".equals(category)) {
            return repository.getLiteratureLessons();
        }
        return repository.getGrammarLessons();
    }
}
