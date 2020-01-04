package id.husni.moviestvcatalogue.model.release;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieRelease implements Parcelable {

    private String title;
    private String posterPath;

    public MovieRelease(JSONObject object) {
        try {
            title = object.getString("title");
            posterPath = object.getString("poster_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.title = title;
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
    }

    protected MovieRelease(Parcel in) {
        this.title = in.readString();
        this.posterPath = in.readString();
    }

    public static final Creator<MovieRelease> CREATOR = new Creator<MovieRelease>() {
        @Override
        public MovieRelease createFromParcel(Parcel source) {
            return new MovieRelease(source);
        }

        @Override
        public MovieRelease[] newArray(int size) {
            return new MovieRelease[size];
        }
    };
}
