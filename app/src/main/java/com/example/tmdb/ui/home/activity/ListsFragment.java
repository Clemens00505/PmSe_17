package com.example.tmdb.ui.home.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tmdb.R;
import com.example.tmdb.database.CollectionViewModel;
import com.example.tmdb.domain.Collection;
import com.example.tmdb.ui.home.adapters.CollectionAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;

public class ListsFragment extends Fragment {

    private CollectionViewModel collectionViewModel;
    private CollectionAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private final String LOG_TAG = this.getClass().getSimpleName();

    public ListsFragment() {
        // Required empty public constructor
    }

    private void createNewList(String listName) {
        // Assuming your Collection has an ID field that is auto-generated
        Collection newList = new Collection(0, listName, "Type or some other attribute");
        collectionViewModel.insertCollection(newList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rvLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CollectionAdapter(new ArrayList<>(), collection -> {
            Intent detailIntent = new Intent(getContext(), ListDetailActivity.class);
            detailIntent.putExtra("list_id", collection.getCollectionId()); // Ensure Collection implements Serializable
            startActivity(detailIntent);
        });

        // For testing, add a dummy collection item
        Collection dummyCollection = new Collection(0, "Dummy List", "Some attribute");
        List<Collection> dummyList = new ArrayList<>();
        dummyList.add(dummyCollection);
        adapter.setCollections(dummyList); // Add the dummy list to the adapter

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);

        // Initialize ViewModel and set up LiveData observation
        collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        collectionViewModel.getAllCollections().observe(getViewLifecycleOwner(), collections -> {
            // Update the UI with the latest data
            adapter.setCollections(collections);
        });

        FloatingActionButton fabAddList = view.findViewById(R.id.fabAddList);
        fabAddList.setOnClickListener(v -> {
            // Create a dialog instance
            final Dialog dialog = new Dialog(getActivity());
            // Set the custom layout for the dialog
            dialog.setContentView(R.layout.dialog_add_new_list);

            // Get the EditText and Button from the dialog layout
            EditText etNewListName = dialog.findViewById(R.id.etNewListName);
            Button buttonCreate = dialog.findViewById(R.id.buttonCreate); // Assuming you have a create button
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            // Set up the button click listeners
            if (buttonCreate != null) {
                buttonCreate.setOnClickListener(v1 -> {
                    // Here, handle the logic to create a new list with the name from etNewListName
                    String listName = etNewListName.getText().toString().trim();
                    if (!listName.isEmpty()) {
                        createNewList(listName); // Method to create a new list
                        dialog.dismiss();
                    }
                });
            } else {
                Log.e(LOG_TAG, "Create button not found in the layout.");
            }

            if (buttonCancel != null) {
                buttonCancel.setOnClickListener(v1 -> dialog.dismiss());
            } else {
                Log.e(LOG_TAG, "Cancel button not found in the layout.");
            }


            dialog.show();
        });

        return view; // This return statement is important to return the inflated view.
    }

    // This is a placeholder, replace it with your actual method to fetch collections
    public List<Collection> getCollectionsFromDataSource() {
        // Fetch your collections from the database or other data source
        return new ArrayList<>(); // Return the actual list of collections
    }

    public void setFilteredList (ArrayList<Collection> filteredList) {
        adapter.setCollections(filteredList);
    }
}
