package id.husni.moviestvcatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Series implements Parcelable {
    private String title;
    private String overview;
    private String airingDate;
    private String posterPath;
    private double voteAverage;

    public Series(JSONObject object) {

        try {
            title = object.getString("name");
            overview = object.getString("overview");
            airingDate = object.getString("first_air_date");
            posterPath = object.getString("poster_path");
            voteAverage = object.getDouble("vote_average");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.title = title;
        this.overview = overview;
        this.airingDate = airingDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
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
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.airingDate);
        dest.writeString(this.posterPath);
        dest.writeDouble(this.voteAverage);
    }

    protected Series(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.airingDate = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readDouble();
    }

    public static final Parcelable.Creator<Series> CREATOR = new Parcelable.Creator<Series>() {
        @Override
        public Series createFromParcel(Parcel source) {
            return new Series(source);
        }

        @Override
        public Series[] newArray(int size) {
            return new Series[size];
        }
    };
}
