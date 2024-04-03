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

    public CollectionAdapter(List<Collection> collections) {
        this.collections = collections;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collection collection = collections.get(position);
        holder.tvListTitle.setText(collection.getName());
        // Set more data here if necessary
    }

    @Override
    public int getItemCount() {
        return collections != null ? collections.size() : 0;
    }

    public void setCollections(List<Collection> newCollections) {
        this.collections = newCollections;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvListTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListTitle = itemView.findViewById(R.id.tvListTitle);
            // Initialize other views here if you have more
        }
    }

}
