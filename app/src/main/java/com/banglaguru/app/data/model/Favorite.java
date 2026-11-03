package com.banglaguru.app.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "favorites", primaryKeys = {"itemId", "itemType"})
public class Favorite {

    @NonNull
    public String itemId;

    @NonNull
    public String itemType; // "lesson" or "mcq"

    public long addedAt;

    public Favorite() {
    }
}
