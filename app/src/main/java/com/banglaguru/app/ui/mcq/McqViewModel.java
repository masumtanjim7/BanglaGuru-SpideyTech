package com.banglaguru.app.ui.mcq;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.banglaguru.app.data.model.Mcq;
import com.banglaguru.app.data.model.Progress;
import com.banglaguru.app.data.repository.ContentRepository;
import com.banglaguru.app.util.AppExecutors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class McqViewModel extends AndroidViewModel {

    private final ContentRepository repository;

    private final MutableLiveData<List<Mcq>> questions = new MutableLiveData<>();
    private final MutableLiveData<Integer> currentIndex = new MutableLiveData<>(0);
    private final Map<String, String> userAnswers = new HashMap<>();

    private String sessionCategory; // mcq | mock_test | daily_quiz

    public McqViewModel(@NonNull Application application) {
        super(application);
        repository = ContentRepository.getInstance(application);
    }

    public void loadPracticeSet(String topicCategory, int count) {
        sessionCategory = "mcq";
        loadInternal(() -> repository.getPracticeSet(topicCategory, count));
    }

    public void loadMockTest(int count) {
        sessionCategory = "mock_test";
        loadInternal(() -> repository.getMockTestSet(count));
    }

    public void loadDailyQuiz(int count) {
        sessionCategory = "daily_quiz";
        loadInternal(() -> repository.getDailyQuizSet(count));
    }

    private void loadInternal(java.util.concurrent.Callable<List<Mcq>> loader) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                List<Mcq> result = loader.call();
                AppExecutors.getInstance().mainThread().execute(() -> {
                    questions.setValue(result);
                    currentIndex.setValue(0);
                    userAnswers.clear();
                });
            } catch (Exception ignored) {
            }
        });
    }

    public MutableLiveData<List<Mcq>> getQuestions() {
        return questions;
    }

    public MutableLiveData<Integer> getCurrentIndex() {
        return currentIndex;
    }

    public void answerCurrent(String selectedOption) {
        List<Mcq> qs = questions.getValue();
        Integer idx = currentIndex.getValue();
        if (qs == null || idx == null || idx >= qs.size()) return;
        userAnswers.put(qs.get(idx).id, selectedOption);
    }

    public String getAnswerFor(String mcqId) {
        return userAnswers.get(mcqId);
    }

    public void nextQuestion() {
        List<Mcq> qs = questions.getValue();
        Integer idx = currentIndex.getValue();
        if (qs == null || idx == null) return;
        if (idx < qs.size() - 1) {
            currentIndex.setValue(idx + 1);
        }
    }

    public void previousQuestion() {
        Integer idx = currentIndex.getValue();
        if (idx != null && idx > 0) {
            currentIndex.setValue(idx - 1);
        }
    }

    public int getCorrectCount() {
        List<Mcq> qs = questions.getValue();
        if (qs == null) return 0;
        int correct = 0;
        for (Mcq m : qs) {
            String given = userAnswers.get(m.id);
            if (given != null && given.equalsIgnoreCase(m.correctAnswer)) {
                correct++;
            }
        }
        return correct;
    }

    public int getAnsweredCount() {
        return userAnswers.size();
    }

    public void finishAndRecordProgress() {
        List<Mcq> qs = questions.getValue();
        if (qs == null || qs.isEmpty()) return;
        Progress p = new Progress();
        p.category = sessionCategory;
        p.totalQuestions = qs.size();
        p.correctAnswers = getCorrectCount();
        p.completed = true;
        p.timestamp = System.currentTimeMillis();
        repository.recordProgress(p);
    }
}
