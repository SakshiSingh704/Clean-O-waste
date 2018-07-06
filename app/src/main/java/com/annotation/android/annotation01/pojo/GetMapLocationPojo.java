package com.annotation.android.annotation01.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CG-DTE on 02-10-2017.
 */

public class GetMapLocationPojo implements Serializable, Parcelable {
    @Expose
    @SerializedName("results")
    List<GetCurrentLocationModal> results;

    public List<GetCurrentLocationModal> getResults() {
        return results;
    }

    public void setResults(List<GetCurrentLocationModal> results) {
        this.results = results;
    }

    public GetMapLocationPojo() {

    }

    public GetMapLocationPojo(List<GetCurrentLocationModal> results) {

        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.results);
    }

    protected GetMapLocationPojo(Parcel in) {
        this.results = new ArrayList<GetCurrentLocationModal>();
        in.readList(this.results, GetCurrentLocationModal.class.getClassLoader());
    }

    public static final Parcelable.Creator<GetMapLocationPojo> CREATOR = new Parcelable.Creator<GetMapLocationPojo>() {
        @Override
        public GetMapLocationPojo createFromParcel(Parcel source) {
            return new GetMapLocationPojo(source);
        }

        @Override
        public GetMapLocationPojo[] newArray(int size) {
            return new GetMapLocationPojo[size];
        }
    };
}
