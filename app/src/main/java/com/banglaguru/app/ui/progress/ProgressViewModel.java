package com.banglaguru.app.ui.progress;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.banglaguru.app.data.repository.ContentRepository;

public class ProgressViewModel extends AndroidViewModel {

    private final ContentRepository repository;

    public ProgressViewModel(@NonNull Application application) {
        super(application);
        repository = ContentRepository.getInstance(application);
    }

    public LiveData<Integer> getCompletedGrammarLessonCount() {
        return repository.getCompletedGrammarLessonCount();
    }

    public LiveData<Integer> getCompletedLiteratureLessonCount() {
        return repository.getCompletedLiteratureLessonCount();
    }

    public LiveData<Integer> getTotalQuestionsAttempted() {
        return repository.getTotalQuestionsAttempted();
    }

    public LiveData<Integer> getTotalCorrectAnswers() {
        return repository.getTotalCorrectAnswers();
    }
}
