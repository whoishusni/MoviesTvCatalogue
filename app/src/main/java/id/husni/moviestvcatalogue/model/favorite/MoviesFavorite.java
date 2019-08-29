package id.husni.moviestvcatalogue.model.favorite;

import android.os.Parcel;
import android.os.Parcelable;

public class MoviesFavorite implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String releaseDate;
    private String posterPath;
    private double voteAverage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeDouble(this.voteAverage);
    }

    public MoviesFavorite() {
    }

    protected MoviesFavorite(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readDouble();
    }

    public static final Parcelable.Creator<MoviesFavorite> CREATOR = new Parcelable.Creator<MoviesFavorite>() {
        @Override
        public MoviesFavorite createFromParcel(Parcel source) {
            return new MoviesFavorite(source);
        }

        @Override
        public MoviesFavorite[] newArray(int size) {
            return new MoviesFavorite[size];
        }
    };
}
