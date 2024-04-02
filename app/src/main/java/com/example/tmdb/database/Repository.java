package com.example.tmdb.database;

import android.app.Application;
import android.os.AsyncTask;

import com.example.tmdb.domain.Collection;
import com.example.tmdb.domain.Movie;

import java.util.ArrayList;

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

    void deleteAllMovies(){
        new deleteAllAsyncTask(dao, "movie_table").execute();
    }

    void deleteAllLists() {
        new deleteAllAsyncTask(dao, "collection_table").execute();
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
    public static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private DAO asyncTaskDao;
        String mytableName;
        deleteAllAsyncTask(DAO dao, String tableName) {
            asyncTaskDao = dao;
            mytableName = tableName;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            if (mytableName.equals("movie_table")) {
                asyncTaskDao.deleteAllMovies();
            } else if (mytableName.equals("collection_table")) {
                asyncTaskDao.deleteAllCollections();
            }
            return null;
        }
    }

}
