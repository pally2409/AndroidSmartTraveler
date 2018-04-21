package com.example.pallaksingh.androidsmarttraveler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder> {

    private Context mCtx;
    private List<Places> placesList;

    public PlacesAdapter(Context mCtx, List<Places> placesList) {
        this.mCtx = mCtx;
        this.placesList = placesList;
    }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        PlacesViewHolder holder = new PlacesViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        Places places = placesList.get(position);
        holder.textViewTitle.setText(places.getName());
        holder.textViewShortDesc.setText(places.getShortDesc());
        holder.textViewRating.setText(places.getPlace_type());
        holder.textViewDistance.setText(places.getDistance());






    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    class PlacesViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewDistance;


        public PlacesViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewDistance = itemView.findViewById(R.id.textViewDistance);
        }
    }
}
