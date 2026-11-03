package com.banglaguru.app.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.banglaguru.app.data.db.AppDatabase;
import com.banglaguru.app.data.model.Favorite;
import com.banglaguru.app.data.model.Lesson;
import com.banglaguru.app.data.model.Mcq;
import com.banglaguru.app.data.model.Progress;
import com.banglaguru.app.util.AppExecutors;

import java.util.List;

/**
 * Single entry point for all data access, used by ViewModels.
 */
public class ContentRepository {

    private static volatile ContentRepository instance;

    private final AppDatabase db;

    private ContentRepository(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public static ContentRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (ContentRepository.class) {
                if (instance == null) {
                    instance = new ContentRepository(context);
                }
            }
        }
        return instance;
    }

    // ---------- Lessons ----------

    public LiveData<List<Lesson>> getGrammarLessons() {
        return db.lessonDao().getLessonsByCategory("grammar");
    }

    public LiveData<List<Lesson>> getLiteratureLessons() {
        return db.lessonDao().getLessonsByCategory("literature");
    }

    public LiveData<Lesson> getLessonById(String id) {
        return db.lessonDao().getLessonById(id);
    }

    // ---------- MCQs ----------

    public LiveData<List<Mcq>> getMcqsByLesson(String lessonId) {
        return db.mcqDao().getByLesson(lessonId);
    }

    /** Blocking call — must be invoked off the main thread (use AppExecutors.diskIO()). */
    public List<Mcq> getPracticeSet(String category, int count) {
        if (category == null || category.equals("random")) {
            return db.mcqDao().getRandomMixed(count);
        }
        return db.mcqDao().getRandomByCategory(category, count);
    }

    public List<Mcq> getDailyQuizSet(int count) {
        return db.mcqDao().getDailyQuiz(count);
    }

    public List<Mcq> getMockTestSet(int count) {
        return db.mcqDao().getRandomMixed(count);
    }

    // ---------- Favorites ----------

    public LiveData<List<Favorite>> getAllFavorites() {
        return db.favoriteDao().getAllFavorites();
    }

    public LiveData<Boolean> isFavorite(String itemId, String itemType) {
        return db.favoriteDao().isFavorite(itemId, itemType);
    }

    public void toggleFavorite(String itemId, String itemType, boolean shouldBeFavorite) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            Favorite f = new Favorite();
            f.itemId = itemId;
            f.itemType = itemType;
            f.addedAt = System.currentTimeMillis();
            if (shouldBeFavorite) {
                db.favoriteDao().insert(f);
            } else {
                db.favoriteDao().delete(f);
            }
        });
    }

    public LiveData<List<Lesson>> getFavoriteLessons(List<String> ids) {
        return db.lessonDao().getLessonsByIds(ids);
    }

    public LiveData<List<Mcq>> getFavoriteMcqs(List<String> ids) {
        return db.mcqDao().getByIds(ids);
    }

    // ---------- Progress ----------

    public void recordProgress(Progress progress) {
        AppExecutors.getInstance().diskIO().execute(() -> db.progressDao().insert(progress));
    }

    public LiveData<List<Progress>> getAllProgress() {
        return db.progressDao().getAll();
    }

    public LiveData<Integer> getCompletedGrammarLessonCount() {
        return db.progressDao().getCompletedGrammarLessonCount();
    }

    public LiveData<Integer> getCompletedLiteratureLessonCount() {
        return db.progressDao().getCompletedLiteratureLessonCount();
    }

    public LiveData<Integer> getTotalQuestionsAttempted() {
        return db.progressDao().getTotalQuestionsAttempted();
    }

    public LiveData<Integer> getTotalCorrectAnswers() {
        return db.progressDao().getTotalCorrectAnswers();
    }
}
