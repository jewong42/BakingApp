package com.jewong.bakingapp.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.jewong.bakingapp.data.Video;

@Dao
@TypeConverters({DataConverter.class})
public interface FavoritesDao {

    @Query("SELECT * FROM favorites WHERE databaseId == 0")
    LiveData<Video> loadVideo();

    @Query("SELECT * FROM favorites WHERE databaseId == 0")
    Video loadVideoValue();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVideo(Video video);

    @Delete
    void deleteVideo(Video video);

}
