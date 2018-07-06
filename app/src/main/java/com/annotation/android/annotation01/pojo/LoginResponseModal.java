package com.annotation.android.annotation01.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BlackHat on 6/25/2017.
 */

public class LoginResponseModal implements Serializable, Parcelable {
    @Expose
    @SerializedName("result")
    List<LoginResponsePojo> result;

    public List<LoginResponsePojo> getResult() {
        return result;
    }

    public void setResult(List<LoginResponsePojo> result) {
        this.result = result;
    }

    public LoginResponseModal() {

    }

    public LoginResponseModal(List<LoginResponsePojo> result) {

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

    protected LoginResponseModal(Parcel in) {
        this.result = in.createTypedArrayList(LoginResponsePojo.CREATOR);
    }

    public static final Parcelable.Creator<LoginResponseModal> CREATOR = new Parcelable.Creator<LoginResponseModal>() {
        @Override
        public LoginResponseModal createFromParcel(Parcel source) {
            return new LoginResponseModal(source);
        }

        @Override
        public LoginResponseModal[] newArray(int size) {
            return new LoginResponseModal[size];
        }
    };
}
