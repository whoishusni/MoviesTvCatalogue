package id.husni.moviestvcatalogue.model.release;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieRelease implements Parcelable {

    private String title;

    public MovieRelease(JSONObject object) {
        try {
            title = object.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
    }

    protected MovieRelease(Parcel in) {
        this.title = in.readString();
    }

    public static final Parcelable.Creator<MovieRelease> CREATOR = new Parcelable.Creator<MovieRelease>() {
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
