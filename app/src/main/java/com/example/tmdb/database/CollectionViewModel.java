        package com.example.tmdb.database;

        import android.app.Application;

        import androidx.annotation.NonNull;
        import androidx.lifecycle.AndroidViewModel;
        import androidx.lifecycle.LiveData;
        import androidx.lifecycle.MutableLiveData;

        import com.example.tmdb.Api.TMDbAPI;
        import com.example.tmdb.domain.Collection;
        import com.example.tmdb.domain.Movie;

        import java.util.ArrayList;
        import java.util.List;

        import javax.inject.Inject;

        public class CollectionViewModel extends AndroidViewModel {

            @Inject
            TMDbAPI tmDbAPI;

            private final String LOG_TAG = this.getClass().getSimpleName();
            private Repository repository;
            private LiveData<List<Collection>> allCollections;

            private LiveData<List<Movie>> moviesInCollection;

            public CollectionViewModel(@NonNull Application application) {
                super(application);
                repository = new Repository(application);
                allCollections = repository.getAllCollections();
                moviesInCollection = new MutableLiveData<>();

            }
//            // Method to call repository to fetch movies for a specific list ID
//            public void fetchMoviesForCollection(int listId) {
//                tmDbAPI.getListDetail(listId,TMDbAPI.getApiKey(this.getApplication()))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(response -> {
//                            List<Movie> movies = response.getItems();
//                            ((MutableLiveData<List<Movie>>)moviesInCollection).postValue(movies);
//                        }, e -> Timber.e(e, "Error fetching now popular movies: %s", e.getMessage()));
//            }
//                tmDbAPI.getListDetail(TMDbAPI.getApiKey(this.getContext()), 1)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(response -> {
//
//                                popularMovieDataList.addAll(response.getResults());
//                                adapter.notifyDataSetChanged();
//
//                        }, e -> {
//                            Timber.e(e, "Error fetching now popular movies: %s", e.getMessage());
//
//                        });
//                tmDbAPI.getListDetail(TMDbAPI.getApiKey(this.getContext()), 1)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(response -> {
//                            List<Movie> movies = response.getItems();
//                            ((MutableLiveData<List<Movie>>)moviesInCollection).postValue(response);
//                        }, e -> {
//                            Timber.e(e, "Error fetching now popular movies: %s", e.getMessage());
//
//                        });
//                tmDbAPI.getListDetail(listId, TMDbAPI.getApiKey(context))
//                        .flatMap(listDetailResponse -> {
//                            List<Movie> movies = listDetailResponse.getItems(); // Change "getMovies()" to "getItems()" or whatever the actual method name is based on the JSON structure
//                            insertAllMovies(new ArrayList<>(movies));
//                            return Observable.just(movies);
//                        });
//                repository.fetchAndSaveMoviesFromList(listId, getApplication())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(movies -> {
//                            // Assuming you have a LiveData<List<Movie>> to post the result
//                            ((MutableLiveData<List<Movie>>)moviesInCollection).postValue(movies);
//                        }, throwable -> {
//                            // Log or handle errors here
//                            Log.e(LOG_TAG, "Error fetching movies for collection", throwable);
//                        });
//            }

//            public LiveData<List<Movie>> getMoviesInCollection() {
//                return moviesInCollection;
//            }
            public LiveData<List<Collection>> getAllCollections() {
                return allCollections;
            }

            public void insertCollection(Collection collection) {
                repository.insertCollection(collection);
            }

            public List<Movie> getAllMoviesFromCollection(int listId) {
                return repository.getAllMoviesFromCollection(listId);
            }

            // Add other methods to insert, update, delete, etc. if needed


        }
