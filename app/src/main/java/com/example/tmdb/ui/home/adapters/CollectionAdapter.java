package com.example.tmdb.ui.home.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tmdb.R;
import com.example.tmdb.domain.Collection;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private List<Collection> collections;
    private OnCollectionClickListener listener;

    public CollectionAdapter(List<Collection> collections, OnCollectionClickListener listener) {
        this.collections = collections;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_collection, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collection collection = collections.get(position);
        holder.tvListTitle.setText(collection.getName());
        // Additional binding logic here if necessary
    }

    @Override
    public int getItemCount() {
        return collections != null ? collections.size() : 0;
    }

    public void setCollections(List<Collection> newCollections) {
        collections = newCollections; // Update your collections list
        notifyDataSetChanged(); // Notify any registered observers that the data set has changed.
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvListTitle;

        public ViewHolder(@NonNull View itemView, final OnCollectionClickListener listener) {
            super(itemView);
            tvListTitle = itemView.findViewById(R.id.tvListTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Collection clickedItem = collections.get(position); // Direct access to collections
                        listener.onCollectionClick(clickedItem);
                    }
                }
            });
        }
    }

    public interface OnCollectionClickListener {
        void onCollectionClick(Collection collection);
    }
}
