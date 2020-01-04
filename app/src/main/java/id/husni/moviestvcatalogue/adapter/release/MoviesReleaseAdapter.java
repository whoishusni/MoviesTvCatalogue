package id.husni.moviestvcatalogue.adapter.release;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.model.release.MovieRelease;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class MoviesReleaseAdapter extends RecyclerView.Adapter<MoviesReleaseAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MovieRelease> movieReleases = new ArrayList<>();

    public MoviesReleaseAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<MovieRelease> getMovieReleases() {
        return movieReleases;
    }

    public void setMovieReleases(ArrayList<MovieRelease> items) {
        if (movieReleases != null) {
            if (movieReleases.size() > 0) {
                movieReleases.clear();
            }
        }
        movieReleases.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesReleaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.release_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesReleaseAdapter.ViewHolder holder, int position) {
        holder.movieTitle.setText(movieReleases.get(position).getTitle());
        Glide.with(context)
                .load(AppUtilities.POSTER_FILM + movieReleases.get(position).getPosterPath())
                .into(holder.imagePoster);
    }

    @Override
    public int getItemCount() {
        return movieReleases.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView movieTitle;
        private ImageView imagePoster;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieTitle = itemView.findViewById(R.id.movieReleaseTitle);
            imagePoster = itemView.findViewById(R.id.movieReleaseImage);
        }
    }
}
