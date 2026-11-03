package com.banglaguru.app.ui.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.banglaguru.app.data.model.Favorite;
import com.banglaguru.app.data.model.Lesson;
import com.banglaguru.app.data.repository.ContentRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private final ContentRepository repository;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = ContentRepository.getInstance(application);
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return repository.getAllFavorites();
    }

    public LiveData<List<Lesson>> getFavoriteLessons() {
        return Transformations.switchMap(repository.getAllFavorites(), favorites -> {
            List<String> ids = new ArrayList<>();
            for (Favorite f : favorites) {
                if ("lesson".equals(f.itemType)) ids.add(f.itemId);
            }
            return repository.getFavoriteLessons(ids);
        });
    }
}
