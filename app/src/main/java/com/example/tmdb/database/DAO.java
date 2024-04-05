package com.example.tmdb.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tmdb.domain.Collection;
import com.example.tmdb.domain.ListMovies;
import com.example.tmdb.domain.Movie;

import java.util.List;


@Dao
public interface DAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovieList(ListMovies movie);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCollection(Collection collection);

    @Query("DELETE FROM movie_table")
    void deleteAllMovies();

    @Query("DELETE FROM collection_table")
    void deleteAllCollections();

    @Update
    void updateMovie(Movie movie);

    @Update
    void updateCollection(Collection collection);

    @Query("DELETE FROM movie_table WHERE :columnId = :objectId")
    void delete(String columnId, String objectId);

    @Query("SELECT * FROM collection_table")
    LiveData<List<Collection>> getAllCollections();

    @Query("SELECT * FROM collection_table")
    List<Collection> getAllCollectionsList();

    @Query("SELECT * FROM list_movie_join lm INNER JOIN movie_table m ON lm.movieId = m.movie_id WHERE lm.listId = :listId")
    List<Movie> getAllMoviesFromCollection(int listId);




}
