package id.husni.moviestvcatalogue.model.search;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SeriesSearch implements Parcelable {
    private String title;
    private double voteAverage;
    private String release;
    private String posterPath;
    private String overView;

    public SeriesSearch(JSONObject object) {
        try {
            title = object.getString("name");
            voteAverage = object.getDouble("vote_average");
            release = object.getString("first_air_date");
            posterPath = object.getString("poster_path");
            overView = object.getString("overview");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.title = title;
        this.voteAverage = voteAverage;
        this.release = release;
        this.posterPath = posterPath;
        this.overView = overView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.release);
        dest.writeString(this.posterPath);
        dest.writeString(this.overView);
    }

    protected SeriesSearch(Parcel in) {
        this.title = in.readString();
        this.voteAverage = in.readDouble();
        this.release = in.readString();
        this.posterPath = in.readString();
        this.overView = in.readString();
    }

    public static final Parcelable.Creator<SeriesSearch> CREATOR = new Parcelable.Creator<SeriesSearch>() {
        @Override
        public SeriesSearch createFromParcel(Parcel source) {
            return new SeriesSearch(source);
        }

        @Override
        public SeriesSearch[] newArray(int size) {
            return new SeriesSearch[size];
        }
    };
}
