package com.example.tmdb.ui.home.activity;

import static com.example.tmdb.Api.TMDbAPI.getApiKey;

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
import android.widget.Toast;

import com.example.tmdb.Api.CreateListRequest;
import com.example.tmdb.Api.CreateListResponse;
import com.example.tmdb.Api.ResponseRequestToken;
import com.example.tmdb.Api.ResponseSession;
import com.example.tmdb.Api.TMDbAPI;
import com.example.tmdb.App;
import com.example.tmdb.R;
import com.example.tmdb.database.CollectionViewModel;
import com.example.tmdb.domain.Collection;
import com.example.tmdb.ui.detail.MovieDetailActivity;
import com.example.tmdb.ui.home.adapters.CollectionAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import retrofit2.Call;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListsFragment extends Fragment {

    @Inject
    TMDbAPI tmDbAPI;

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

        //ResponseRequestToken requestTokenResponse = tmDbAPI.getRequestToken(getApiKey(this.getContext()));

        //String requestToken = requestTokenResponse.getRequestToken();
        ;
        String requestToken = getRequestToken();
        Log.i("ListFragment", "Requesttoken: " + requestToken);
        String sessionId = getSessionId("lala");
        Log.i("ListFragment", "Sessionid: " + sessionId);

        Collection newList = new Collection(listName);

        CreateListRequest requestBody = new CreateListRequest(listName);
        AtomicBoolean apiListAdded = new AtomicBoolean(false);
        tmDbAPI.createList(getApiKey(this.getContext()), sessionId, requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    apiListAdded.set(true);
                    newList.setCollectionId(response.getList_id());
                    Log.i("listFragment", "list id geset: " + newList.getCollectionId());
                    // Handle successful list creation
                    Log.i("ListFragment", "List aangemaakt");
                }, throwable -> {
                    // Handle error
                    Log.i("ListFragment", "List niet aangemaakt");
                });

        // Assuming your Collection has an ID field that is auto-generated
        if (apiListAdded.get()) {
            collectionViewModel.insertCollection(newList);
        } else {
            Toast.makeText(this.getContext(), "nee is niet gelukt", Toast.LENGTH_SHORT).show();
        }





    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance().appComponent().inject(this);

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
        Collection dummyCollection = new Collection("Dummy List");
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

    private String parseRequestTokenFromResponse(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("request_token");
    }

    public String getRequestToken() {
        AtomicReference<String> requestToken = new AtomicReference<>("");
        Log.i("LALA", "RequestToken initialize: " + requestToken);
        tmDbAPI.getRequestToken(getApiKey(this.getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.i("LALA", "response" + response.getRequest_token());
                    requestToken.set(response.getRequest_token());
                    Log.i("LALA", "RequestToken set: " + requestToken);

                });
        Log.i("LALA", "RequestToken return: "+ requestToken);
        return requestToken.toString();

    }

    public String getSessionId(String requestToken) {
        AtomicReference<String> sessionId = new AtomicReference<>("");
        tmDbAPI.getSession(getApiKey(this.getContext()), requestToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    sessionId.set(response.getSessionId());
                });

        return sessionId.toString();
    }
}
