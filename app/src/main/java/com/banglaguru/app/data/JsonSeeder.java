package com.banglaguru.app.data;

import android.content.Context;
import android.util.Log;

import com.banglaguru.app.data.db.AppDatabase;
import com.banglaguru.app.data.model.Lesson;
import com.banglaguru.app.data.model.Mcq;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * Loads JSON content from assets/ into Room on first launch only.
 * Runs on a background thread; call from AppExecutors.diskIO().
 */
public class JsonSeeder {

    private static final String TAG = "JsonSeeder";

    private final Context context;
    private final AppDatabase db;
    private final Gson gson = new Gson();

    public JsonSeeder(Context context, AppDatabase db) {
        this.context = context;
        this.db = db;
    }

    public void seedIfEmpty() {
        if (db.lessonDao().count() == 0) {
            db.lessonDao().insertAll(readList("grammar_lessons.json", Lesson.class));
            db.lessonDao().insertAll(readList("literature_lessons.json", Lesson.class));
        }
        if (db.mcqDao().count() == 0) {
            db.mcqDao().insertAll(readList("mcq_grammar.json", Mcq.class));
            db.mcqDao().insertAll(readList("mcq_literature.json", Mcq.class));
        }
    }

    private <T> List<T> readList(String assetFile, Class<T> clazz) {
        try (InputStream is = context.getAssets().open(assetFile);
             InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            Log.e(TAG, "Failed to read " + assetFile, e);
            return Collections.emptyList();
        }
    }
}
