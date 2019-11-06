package id.husni.moviestvcatalogue.adapter;

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

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.model.Movies;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private ArrayList<Movies> movies = new ArrayList<>();
    private Context context;

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movies> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movies> items) {
        if (movies.size() > 0) {
            movies.clear();
        }
        movies.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movies_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(movies.get(position).getTitle());
        holder.chipRating.setText(String.valueOf(movies.get(position).getVoteAverage()));
        holder.tvReleaseDate.setText(String.valueOf(movies.get(position).getReleaseDate()));
        float bintangRating = (float) (movies.get(position).getVoteAverage() / 2);
        holder.ratingBar.setRating(bintangRating);
        Glide.with(context)
                .load(AppUtilities.POSTER_FILM + movies.get(position).getPosterPath())
                .into(holder.imageMovies);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Chip chipRating;
        TextView tvTitle;
        TextView tvReleaseDate;
        RatingBar ratingBar;
        ImageView imageMovies;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            chipRating = itemView.findViewById(R.id.chipMovieRating);
            tvReleaseDate = itemView.findViewById(R.id.tvMovieRelease);
            ratingBar = itemView.findViewById(R.id.movieRatingBar);
            imageMovies = itemView.findViewById(R.id.movieImage);
        }
    }
}
