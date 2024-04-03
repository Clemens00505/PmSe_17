package com.example.tmdb.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.tmdb.domain.Collection;
import com.example.tmdb.domain.Movie;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private final DAO dao;


    Repository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        dao = db.getDAO();
    }
    void insertMovie(Movie movie) {
        new insertMovieAsyncTask(dao).execute(movie);
    }

    void insertAllMovies(ArrayList<Movie> movies) {
        for(Movie movie : movies) {
            new insertMovieAsyncTask(dao).execute(movie);
        }
    }

    void insertCollection(Collection collection) {
        new insertCollectionAsyncTask(dao).execute(collection);
    }

    void insertAllCollections(ArrayList<Collection> collections) {
        for(Collection collection : collections) {
            new insertCollectionAsyncTask(dao).execute(collection);
        }
    }

    void updateMovie(Movie movie) {
        new updateMovieAsyncTask(dao).execute();
    }

    void updateCollection(Collection collection) {
        new updateCollectionAsyncTask(dao).execute();
    }

    void deleteAllMovies(){
        new deleteAllMoviesAsyncTask(dao).execute();
    }

    void deleteAllLists() {
        new deleteAllCollectionsAsyncTask(dao).execute();
    }

    public LiveData<List<Collection>> getAllCollections() {
        return dao.getAllCollections();
    }


    public static class insertCollectionAsyncTask extends AsyncTask<Collection, Void, Void> {
        private DAO asyncTaskDao;
        insertCollectionAsyncTask(DAO dao) {
            asyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Collection... collections) {
            asyncTaskDao.insertCollection(collections[0]);
            return null;
        }
    }
    public static class insertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private DAO asyncTaskDao;
        insertMovieAsyncTask(DAO dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Movie... movies) {
            asyncTaskDao.insertMovie(movies[0]);
            return null;
        }
    }

    public static class updateMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private DAO asyncTaskDao;

        updateMovieAsyncTask(DAO dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            asyncTaskDao.updateMovie(movies[0]);
            return null;
        }
    }

    public static class updateCollectionAsyncTask extends AsyncTask<Collection, Void, Void> {
        private DAO asyncTaskDao;

        updateCollectionAsyncTask(DAO dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Collection... collections) {
            asyncTaskDao.updateCollection(collections[0]);
            return null;
        }
    }

    public static class deleteAllMoviesAsyncTask extends AsyncTask<Void, Void, Void> {
        private DAO asyncTaskDao;
        deleteAllMoviesAsyncTask(DAO dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAllMovies();
            return null;
        }
    }

    public static class deleteAllCollectionsAsyncTask extends AsyncTask<Void, Void, Void> {
        private DAO asyncTaskDao;
        deleteAllCollectionsAsyncTask(DAO dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAllCollections();
            return null;
        }
    }


}
