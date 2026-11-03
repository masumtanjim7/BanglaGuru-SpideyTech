package com.banglaguru.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.banglaguru.app.data.model.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE itemId = :itemId AND itemType = :itemType)")
    LiveData<Boolean> isFavorite(String itemId, String itemType);

    @Query("SELECT * FROM favorites WHERE itemType = :itemType ORDER BY addedAt DESC")
    LiveData<List<Favorite>> getFavoritesByType(String itemType);

    @Query("SELECT * FROM favorites ORDER BY addedAt DESC")
    LiveData<List<Favorite>> getAllFavorites();
}
