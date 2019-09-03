package id.husni.moviestvcatalogue.model.favorite;

import android.os.Parcel;
import android.os.Parcelable;

public class SeriesFavorite implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String airingDate;
    private String posterPath;
    private String voteAverage;

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

    public String getAiringDate() {
        return airingDate;
    }

    public void setAiringDate(String airingDate) {
        this.airingDate = airingDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
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
        dest.writeString(this.airingDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.voteAverage);
    }

    public SeriesFavorite() {
    }

    protected SeriesFavorite(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.airingDate = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readString();
    }

    public static final Creator<SeriesFavorite> CREATOR = new Creator<SeriesFavorite>() {
        @Override
        public SeriesFavorite createFromParcel(Parcel source) {
            return new SeriesFavorite(source);
        }

        @Override
        public SeriesFavorite[] newArray(int size) {
            return new SeriesFavorite[size];
        }
    };
}
