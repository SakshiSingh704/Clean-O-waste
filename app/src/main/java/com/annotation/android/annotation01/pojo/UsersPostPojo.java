package com.annotation.android.annotation01.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CG-DTE on 05-10-2017.
 */

public class UsersPostPojo implements Serializable, Parcelable {
    @Expose
    @SerializedName("result")
    List<UsersPostModal> result;

    public List<UsersPostModal> getResult() {
        return result;
    }

    public void setResult(List<UsersPostModal> result) {
        this.result = result;
    }

    public UsersPostPojo() {

    }

    public UsersPostPojo(List<UsersPostModal> result) {

        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.result);
    }

    protected UsersPostPojo(Parcel in) {
        this.result = in.createTypedArrayList(UsersPostModal.CREATOR);
    }

    public static final Parcelable.Creator<UsersPostPojo> CREATOR = new Parcelable.Creator<UsersPostPojo>() {
        @Override
        public UsersPostPojo createFromParcel(Parcel source) {
            return new UsersPostPojo(source);
        }

        @Override
        public UsersPostPojo[] newArray(int size) {
            return new UsersPostPojo[size];
        }
    };
}
