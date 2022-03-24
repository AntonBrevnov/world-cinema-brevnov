package com.example.worldcinemabrevnov.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldcinemabrevnov.R;
import com.example.worldcinemabrevnov.network.models.MovieResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieHolder> {
    public class MovieHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView mPreview;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.movie_title);
            mPreview = itemView.findViewById(R.id.movie_preview);
        }

        public void setMovieTitle(String title) {
            mTitle.setText(title);
        }

        public void setMoviePreview(String imageUrl) {
            Picasso.with(mContext)
                    .load(imageUrl).into(mPreview);
        }
    }

    private List<MovieResponse> mMovies;
    private Context mContext;

    public MoviesListAdapter(Context context, List<MovieResponse> movies) {
        mContext = context;
        mMovies = movies;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movies_list_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        final MovieResponse movie = mMovies.get(position);

        holder.setMovieTitle(movie.getName());
        holder.setMoviePreview("http://cinema.areas.su/up/images/" + movie.getPoster());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
