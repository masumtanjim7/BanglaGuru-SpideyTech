package com.banglaguru.app;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.banglaguru.app.data.JsonSeeder;
import com.banglaguru.app.data.db.AppDatabase;
import com.banglaguru.app.util.AppExecutors;

public class BanglaGuruApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences prefs = getSharedPreferences("bangla_guru_prefs", MODE_PRIVATE);
        boolean darkMode = prefs.getBoolean("dark_mode", false);
        AppCompatDelegate.setDefaultNightMode(darkMode
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);

        AppDatabase db = AppDatabase.getInstance(this);
        AppExecutors.getInstance().diskIO().execute(() -> new JsonSeeder(this, db).seedIfEmpty());
    }
}
