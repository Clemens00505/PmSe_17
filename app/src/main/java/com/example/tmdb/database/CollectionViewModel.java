package com.example.tmdb.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tmdb.domain.Collection;

import java.util.List;

public class CollectionViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Collection>> allCollections;

    public CollectionViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCollections = repository.getAllCollections();
    }

    public LiveData<List<Collection>> getAllCollections() {
        return allCollections;
    }

    public void insertCollection(Collection collection) {
        repository.insertCollection(collection);
    }

    // Add other methods to insert, update, delete, etc. if needed
}
