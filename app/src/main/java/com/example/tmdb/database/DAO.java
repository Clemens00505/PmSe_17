package com.example.tmdb.database;

import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tmdb.domain.Collection;
import com.example.tmdb.domain.Movie;

import java.util.List;

@Dao
public interface DAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Movie> movies);

    @Query("DELETE FROM movie_table")
    void deleteAllMovies();

    @Query("DELETE FROM collection_table")
    void deleteAllCollections();

    @Update
    void update(Movie movie);

    @Query("DELETE FROM movie_table WHERE :columnId = :objectId")
    void delete(String columnId, String objectId);

}
