package com.annotation.android.annotation01.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CG-DTE on 02-10-2017.
 */

public class GetCurrentLocationModal implements Serializable, Parcelable {
    @Expose
    @SerializedName("formatted_address")
    String formatted_address;

    public GetCurrentLocationModal() {
    }

    public String getFormatted_address() {

        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public GetCurrentLocationModal(String formatted_address) {

        this.formatted_address = formatted_address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.formatted_address);
    }

    protected GetCurrentLocationModal(Parcel in) {
        this.formatted_address = in.readString();
    }

    public static final Parcelable.Creator<GetCurrentLocationModal> CREATOR = new Parcelable.Creator<GetCurrentLocationModal>() {
        @Override
        public GetCurrentLocationModal createFromParcel(Parcel source) {
            return new GetCurrentLocationModal(source);
        }

        @Override
        public GetCurrentLocationModal[] newArray(int size) {
            return new GetCurrentLocationModal[size];
        }
    };
}
