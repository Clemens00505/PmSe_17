package com.example.tmdb.database;

//import static com.example.tmdb.Api.TMDbAPI.TMDb_API_KEY;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.tmdb.Api.CreateListRequest;
import com.example.tmdb.Api.CreateListResponse;
import com.example.tmdb.Api.TMDbAPI;
import com.example.tmdb.domain.Collection;
import com.example.tmdb.domain.ListMovies;
import com.example.tmdb.domain.Movie;
import com.example.tmdb.ui.home.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import rx.Observable;

public class Repository {

    private final DAO dao;
    private TMDbAPI tmDbAPI;


    public Repository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        dao = db.getDAO();

    }
//    void insertMovieInCollection(Movie movie) {
//        new insertMovieAsyncTask(dao).execute(movie);
//    }
//
//    void insertAllMovies(ArrayList<Movie> movies) {
//        for(Movie movie : movies) {
//            new insertMovieAsyncTask(dao).execute(movie);
//        }
//    }

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
    public void insertMovieInList(ListMovies movieInList) {
        new insertMovieListAsyncTask(dao).execute(movieInList);
    }

    public LiveData<List<Collection>> getAllCollections() {
        return dao.getAllCollections();
    }
    public List<Collection> getAllCollectionsList() {
        List<Collection> list;
        try {
            list = new selectCollections(dao).execute().get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<Movie> getAllMoviesFromCollection(int listId) {
        List<Movie> list;
        try {
            list = new getMoviesTask(dao).execute(listId).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


//    public Observable<List<Movie>> fetchAndSaveMoviesFromList(int listId, Context context) {
//        return tmDbAPI.getListDetail(listId, TMDbAPI.getApiKey(context))
//                .flatMap(listDetailResponse -> {
//                    List<Movie> movies = listDetailResponse.getItems(); // Change "getMovies()" to "getItems()" or whatever the actual method name is based on the JSON structure
//                    insertAllMovies(new ArrayList<>(movies));
//                    return Observable.just(movies);
//                });
//    }
//    public Observable<CreateListResponse> createListInApi(CreateListRequest createListRequest) {
//        return tmDbAPI.createList(createListRequest)
//                .flatMap(createListResponse -> {
//                    Log.d("Repository", "List created with ID: " + createListResponse.getList_id());
//                    return Observable.just(createListResponse);
//                });
//    }

    public static class selectCollections extends AsyncTask<Void, Void, List<Collection>> {
        private DAO asyncTaskDao;

        selectCollections(DAO dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected List doInBackground(Void... voids) {
            return asyncTaskDao.getAllCollectionsList();
        }
    }
    public static class getMoviesTask extends AsyncTask<Integer, Void, List<Movie>> {
        private DAO asyncTaskDao;


        getMoviesTask(DAO dao) {
            asyncTaskDao = dao;

        }


        @Override
        protected List<Movie> doInBackground(Integer... integers) {
            return asyncTaskDao.getAllMoviesFromCollection(integers[0]);
        }
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

    public static class insertMovieListAsyncTask extends AsyncTask<ListMovies, Void, Void> {
        private DAO asyncTaskDao;
        insertMovieListAsyncTask(DAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ListMovies... listMovies) {
            asyncTaskDao.insertMovieList(listMovies[0]);
            return null;
        }
    }
//    public static class insertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
//        private DAO asyncTaskDao;
//        insertMovieAsyncTask(DAO dao) {
//            asyncTaskDao = dao;
//        }
//        @Override
//        protected Void doInBackground(Movie... movies) {
//            asyncTaskDao.insertMovie(movies[0]);
//            return null;
//        }
//    }

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
