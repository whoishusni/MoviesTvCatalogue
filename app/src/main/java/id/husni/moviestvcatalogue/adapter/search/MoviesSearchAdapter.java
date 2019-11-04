package id.husni.moviestvcatalogue.adapter.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.robertlevonyan.views.chip.Chip;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.model.search.MovieSearch;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class MoviesSearchAdapter extends RecyclerView.Adapter<MoviesSearchAdapter.ViewHolder> {
    private ArrayList<MovieSearch> movieSearches = new ArrayList<>();
    private Context context;

    public MoviesSearchAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<MovieSearch> getMovieSearches() {
        return movieSearches;
    }

    public void setMovieSearches(ArrayList<MovieSearch> items) {
        if (movieSearches != null) {
            if (movieSearches.size() > 0) {
                movieSearches.clear();
            }
        }
        movieSearches.addAll(items);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MoviesSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(id.husni.moviestvcatalogue.R.layout.movies_item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesSearchAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(movieSearches.get(position).getTitle());
        holder.tvRelease.setText(movieSearches.get(position).getRelease());
        holder.chipRating.setText(String.valueOf(movieSearches.get(position).getVoteAverage()));
        float rate = (float) movieSearches.get(position).getVoteAverage()/2;
        holder.ratingBar.setRating(rate);
        Glide.with(context)
                .load(AppUtilities.POSTER_FILM + movieSearches.get(position).getPosterPath())
                .into(holder.movieImage);

    }

    @Override
    public int getItemCount() {
        return movieSearches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvRelease;
        public Chip chipRating;
        public RatingBar ratingBar;
        public ImageView movieImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(id.husni.moviestvcatalogue.R.id.tvMovieSearchTitle);
            tvRelease = itemView.findViewById(id.husni.moviestvcatalogue.R.id.tvMovieSearchRelease);
            chipRating = itemView.findViewById(id.husni.moviestvcatalogue.R.id.chipMovieSearchRating);
            ratingBar = itemView.findViewById(id.husni.moviestvcatalogue.R.id.movieSearchRatingBar);
            movieImage = itemView.findViewById(id.husni.moviestvcatalogue.R.id.movieSearchImage);

        }
    }
}
