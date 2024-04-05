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
import com.example.tmdb.Api.ResponseRequestToken;
import com.example.tmdb.Api.ResponseSession;
import com.example.tmdb.Api.SessionResponse;
import com.example.tmdb.Api.TMDbAPI;
import com.example.tmdb.App;
import com.example.tmdb.Dagger.Modules.HttpClientModule;
import com.example.tmdb.R;
import com.example.tmdb.database.CollectionViewModel;
import com.example.tmdb.domain.Collection;
import com.example.tmdb.ui.home.adapters.CollectionAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListsFragment extends Fragment {

    @Inject
    TMDbAPI tmDbAPI;

    private CollectionViewModel collectionViewModel;
    private CollectionAdapter adapter;

    private final String LOG_TAG = this.getClass().getSimpleName();

    public ListsFragment() {
        // Required empty public constructor
    }


    //public void createNewList(String listName) {
//        AtomicReference<ResponseRequestToken> response = null;
//        response = getRequestToken();
//        ResponseRequestToken responseNormal = response.get();
//        String responseToken = responseNormal.getRequest_token();
//        SessionResponse requestBody = new SessionResponse(responseToken);
//        Call<SessionResponse> call = tmDbAPI.getSession(requestBody, getApiKey(this.getContext()));
//        tmDbAPI.getRequestToken(getApiKey(this.getContext()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    // Handle successful request token retrieval
//                    String requestToken = response.getRequest_token(); // Access token only after successful response
//                    String requestTokenHuh = response.getRequest_token();
//                        RequestBody requestBody = new SessionResponse(requestTokenHuh);
//                    //SessionResponse sessionResponseLaa = new SessionResponse(requestTokenHuh);
//                    // Call createSession with the retrieved request token and handle response/error
//                    tmDbAPI.getSession(requestBody, getApiKey(this.getContext()))
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(sessionResponse -> {
//                                // Handle successful session creation
//                                String sessionId = sessionResponse.getSessionId(); // Access session ID only after successful response
//                                Log.i("doet ie het?", sessionId);
//                                // Use the requestToken and sessionId for further actions (create list)
//                                // ...
//                            }, throwable -> {
//                                // Handle error during getSession
//                                Log.e("TMDB", "Error creating session", throwable);
//                                // Show an informative error message to the user
//                                Toast.makeText(this.getContext(), "Failed to create session", Toast.LENGTH_SHORT).show();
//                            });
//                }, throwable -> {
//                    // Handle error during getRequestToken
//                    Log.e("TMDB", "Error fetching request token", throwable);
//                    // Show an informative error message to the user
//                    Toast.makeText(this.getContext(), "Failed to get request token", Toast.LENGTH_SHORT).show();
//                });
//   }
//        AtomicReference<ResponseRequestToken> response = getRequestToken();
//        ResponseRequestToken responseNormal = response.get();
//        String responseToken = responseNormal.getRequest_token();
//        AtomicReference<ResponseSession> session = createSession(responseToken);
//        ResponseSession responseSession = session.get();
//        String sessionId = responseSession.getSessionId();


//        tmDbAPI.getRequestToken(getApiKey(this.getContext()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                // Chain successful request token to getSession call using flatMap
//                .flatMap(requestToken -> tmDbAPI.getSession(requestToken)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .toSingle() // Convert to Single for chaining with createList
//                )
//                // Chain successful session ID to createList call using another flatMap
//                .flatMap(sessionId -> tmDbAPI.createList(listName, sessionId)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .toSingle() // Convert to Single for chaining
//                )
//                .subscribe(
//                        newList -> {
//                            // Handle successful list creation
//                            // Update UI with the new list
//                            // ...
//                        },
//                        error -> {
//                            // Log the error
//                            Log.e("TMDB", "Error creating list", error);
//                            // Show an informative error message to the user based on error code (e.g., 400 Bad Request)
//                            Toast.makeText(context, "Failed to create list", Toast.LENGTH_SHORT).show();
//                        }
//                );


//    public void createNewList(String listName) {
//        AtomicReference<String> requestTokenString = new AtomicReference<>("");
//        AtomicReference<String> sessionidString = new AtomicReference<>("");
//        Context context = this.getContext();
//        tmDbAPI.getRequestToken(getApiKey(context))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        requestToken -> {
//                            // Handle successful request token retrieval
//                            // ...
//                            requestTokenString.set(requestToken.getRequest_token());
//                        },
//                        error -> {
//                            // Log the error
//                            Log.e("TMDB", "Error fetching request token", error);
//                            // Show an informative error message to the user
//                            Toast.makeText(context, "Failed to get request token", Toast.LENGTH_SHORT).show();
//                        }
//                )
//                // Chain successful request token to getSession call
//                .flatMap(requestToken -> tmDbAPI.getSession(getApiKey(context), requestTokenString.get())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .toSingle() // Convert to Single for chaining with createList
//                        .subscribe(
//                                sessionId -> {
//                                    // Handle successful session ID retrieval
//                                    // ...
//                                    sessionidString = sessionId.getSessionId();
//                                },
//                                error -> {
//                                    // Log the error
//                                    Log.e("TMDB", "Error fetching session ID", error);
//                                    // Show an informative error message to the user
//                                    Toast.makeText(context, "Failed to get session ID", Toast.LENGTH_SHORT).show();
//                                }
//                        )
//                )
//                // Chain successful session ID to createList call
//                .flatMap(sessionId -> tmDbAPI.createList(listName, sessionId)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .toSingle() // Convert to Single for chaining
//                        .subscribe(
//                                newList -> {
//                                    // Handle successful list creation
//                                    // Update UI with the new list
//                                    // ...
//                                },
//                                error -> {
//                                    // Log the error
//                                    Log.e("TMDB", "Error creating list", error);
//                                    // Show an informative error message to the user based on error code (e.g., 400 Bad Request)
//                                    Toast.makeText(context, "Failed to create list", Toast.LENGTH_SHORT).show();
//                                }
//                        )
//                );
//    }
//
   private void createNewList(String listName) {
       Collection newList = new Collection(listName);
       collectionViewModel.insertCollection(newList);

//        requestTokenLALA = new AtomicReference<>("");
//        getRequestToken().subscribe(requestToken -> {
//            requestTokenLALA.set(requestToken);
//            }, throwable -> {
//            Log.e("LALA", "Error fetching request token: " + throwable.getMessage());
//            }
//        );
       // String requestToken = requestTokenLALA.get();
        //String sessionId = createSession(requestToken);



//        CreateListRequest requestBody = new CreateListRequest(listName);
//        AtomicBoolean apiListAdded = new AtomicBoolean(false);
//        tmDbAPI.createList(getApiKey(this.getContext()), sessionId, requestBody)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    apiListAdded.set(true);
//                    newList.setCollectionId(response.getList_id());
//                    Log.i("listFragment", "list id geset: " + newList.getCollectionId());
//                    // Handle successful list creation
//                    Log.i("ListFragment", "List aangemaakt");
//                }, throwable -> {
//                    // Handle error
//                    Log.i("ListFragment", "List niet aangemaakt");
//                });
//
//        // Assuming your Collection has an ID field that is auto-generated
//        if (apiListAdded.get()) {

//        } else {
//            Toast.makeText(this.getContext(), getString(R.string.nee_is_niet_gelukt_toast), Toast.LENGTH_SHORT).show();
//        }





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

//    private String parseRequestTokenFromResponse(String response) throws JSONException {
//        JSONObject jsonObject = new JSONObject(response);
//        return jsonObject.getString("request_token");
//    }

//    public Observable<String> getRequestToken() {
//        return tmDbAPI.getRequestToken(getApiKey(this.getContext()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(response -> response.getRequest_token())
//                .doOnNext(requestToken -> Log.i("LALA", "RequestToken set: " + requestToken));
//    }
//    public AtomicReference<ResponseRequestToken> getRequestToken() {
//        AtomicReference<ResponseRequestToken> requestToken = new AtomicReference<ResponseRequestToken>();
//        tmDbAPI.getRequestToken(getApiKey(this.getContext()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    // Handle the response (e.g., extract the request token)
//                    requestToken.set(response);
//                    //Log.i("MyActivity", "Request token: " + requestToken.get().getRequest_token());
//                    // Now proceed to ask the user for permission
//                    // (redirect the user to the authentication URL)
//                }, throwable -> {
//                    // Handle any errors (network issues, API failure, etc.)
//                    Log.e("MyActivity", "Error fetching request token: " + throwable.getMessage());
//                });
//        return requestToken;
//    }
//    private AtomicReference<ResponseSession> createSession(String authorizedRequestToken) {
//        AtomicReference<ResponseSession> responseSession = new AtomicReference<>();
//        SessionResponse sessionResponse = new SessionResponse(authorizedRequestToken);
//        Call<SessionResponse> call = tmDbAPI.getSession(requestBody, apiKey);
//        tmDbAPI.getSession(sessionResponse, getApiKey(this.getContext())
//                .subscribeOn(Schedulers.io()) // Execute on I/O thread
//                .observeOn(AndroidSchedulers.mainThread()) // Observe on main thread
//                .subscribe(response -> {
//                    // Handle the response (e.g., extract the session ID)
//                    responseSession.set(response);
//                    Log.i("MyActivity", "Session ID: " + responseSession);
//                    // Now you can use this session ID for authorized actions
//                }, throwable -> {
//                    // Handle any errors (network issues, API failure, etc.)
//                    Log.e("MyActivity", "Error creating session: " + throwable.getMessage());
//                });
//        return responseSession;
//    }


//    public void getRequestToken() {
//        Log.i("LALA", "RequestToken initialize: " + requestTokenAPI);
//        tmDbAPI.getRequestToken(getApiKey(this.getContext()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    Log.i("LALA", "response" + response.getRequest_token());
//                    requestTokenAPI.set(response.getRequest_token());
//                    Log.i("LALA", "RequestToken set: " + requestTokenAPI);
//
//                });
//        Log.i("LALA", "RequestToken return: "+ requestTokenAPI);
//
//
//    }

//    public String getSessionId(String requestToken) {
//        AtomicReference<String> sessionId = new AtomicReference<>("");
//        tmDbAPI.getSession(getApiKey(this.getContext()), requestToken)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    sessionId.set(response.getSessionId());
//                });
//
//        return sessionId.toString();
//    }


}
