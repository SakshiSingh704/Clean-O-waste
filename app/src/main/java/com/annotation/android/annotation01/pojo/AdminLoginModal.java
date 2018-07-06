package com.annotation.android.annotation01.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CG-DTE on 04-10-2017.
 */

public class AdminLoginModal implements Serializable, Parcelable {
    @Expose
    @SerializedName("result")
    List<AdminLoginPojo> result;

    public List<AdminLoginPojo> getResult() {
        return result;
    }

    public void setResult(List<AdminLoginPojo> result) {
        this.result = result;
    }

    public AdminLoginModal(List<AdminLoginPojo> result) {

        this.result = result;
    }

    public AdminLoginModal() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.result);
    }

    protected AdminLoginModal(Parcel in) {
        this.result = in.createTypedArrayList(AdminLoginPojo.CREATOR);
    }

    public static final Parcelable.Creator<AdminLoginModal> CREATOR = new Parcelable.Creator<AdminLoginModal>() {
        @Override
        public AdminLoginModal createFromParcel(Parcel source) {
            return new AdminLoginModal(source);
        }

        @Override
        public AdminLoginModal[] newArray(int size) {
            return new AdminLoginModal[size];
        }
    };
}
