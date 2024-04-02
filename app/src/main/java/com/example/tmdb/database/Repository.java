package com.example.tmdb.database;

import android.app.Application;
import android.os.AsyncTask;

public class Repository {

    private final DAO dao;


    Repository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        dao = db.getDAO();
    }

    void deleteAllMovies(){
        new deleteAllAsyncTask(dao, "movie_table").execute();
    }

    void deleteAllLists() {
        new deleteAllAsyncTask(dao, "collection_table").execute();
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
