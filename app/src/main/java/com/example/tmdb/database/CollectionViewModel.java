        package com.example.tmdb.database;

        import android.app.Application;
        import android.util.Log;

        import androidx.annotation.NonNull;
        import androidx.lifecycle.AndroidViewModel;
        import androidx.lifecycle.LiveData;
        import androidx.lifecycle.MutableLiveData;
        import androidx.lifecycle.ViewModel;

        import com.example.tmdb.domain.Collection;
        import com.example.tmdb.domain.Movie;

        import java.util.List;

        import rx.android.schedulers.AndroidSchedulers;
        import rx.schedulers.Schedulers;

        public class CollectionViewModel extends AndroidViewModel {

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
            // Method to call repository to fetch movies for a specific list ID
            public void fetchMoviesForCollection(int listId) {
                repository.fetchAndSaveMoviesFromList(listId, getApplication())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(movies -> {
                            // Assuming you have a LiveData<List<Movie>> to post the result
                            ((MutableLiveData<List<Movie>>)moviesInCollection).postValue(movies);
                        }, throwable -> {
                            // Log or handle errors here
                            Log.e(LOG_TAG, "Error fetching movies for collection", throwable);
                        });
            }

            public LiveData<List<Movie>> getMoviesInCollection() {
                return moviesInCollection;
            }
            public LiveData<List<Collection>> getAllCollections() {
                return allCollections;
            }

            public void insertCollection(Collection collection) {
                repository.insertCollection(collection);
            }

            // Add other methods to insert, update, delete, etc. if needed


        }
