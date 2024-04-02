package com.example.tmdb.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.tmdb.domain.Movie;
import com.example.tmdb.domain.Collection;

import java.util.List;

@Dao
public interface DAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Collection collection);

    @Query("DELETE FROM movie_table")
    void deleteAllMovies();

    @Query("DELETE FROM collection_table")
    void deleteAllCollections();

    @Update
    void update(Movie movie);

    @Update
    void update(Collection collection);

    @Query("SELECT * FROM movie_table")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movie_table WHERE genre = :genre")
    LiveData<List<Movie>> getAllMoviesByGenre(String genre);

    @Query("SELECT * FROM movie_table WHERE release_date = :releaseDate")
    LiveData<List<Movie>> getAllMoviesByReleaseDate(String releaseDate);

    @Query("SELECT * FROM movie_table WHERE rating = :rating")
    LiveData<List<Movie>> getAllMoviesByRating(int rating);
}
