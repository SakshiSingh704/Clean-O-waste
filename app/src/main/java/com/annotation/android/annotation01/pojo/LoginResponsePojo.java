package com.annotation.android.annotation01.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by BlackHat on 6/25/2017.
 */

public class LoginResponsePojo implements Serializable, Parcelable {

    @Expose
    @SerializedName("uid")
    String uid;
    @Expose
    @SerializedName("name")
    String name;
    @Expose
    @SerializedName("mobile")
    String mobile;
    @Expose
    @SerializedName("pass")
    String password;
    @Expose
    @SerializedName("email")
    String email;
    @Expose
    @SerializedName("code")
    String code;
    @Expose
    @SerializedName("status")
    String status;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return code;
    }

    public void setCity(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginResponsePojo(String uid, String name, String mobile, String password, String email, String code, String status) {

        this.uid = uid;
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.email = email;
        this.code = code;
        this.status = status;
    }

    public LoginResponsePojo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.password);
        dest.writeString(this.email);
        dest.writeString(this.code);
        dest.writeString(this.status);
    }

    protected LoginResponsePojo(Parcel in) {
        this.uid = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.code = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<LoginResponsePojo> CREATOR = new Parcelable.Creator<LoginResponsePojo>() {
        @Override
        public LoginResponsePojo createFromParcel(Parcel source) {
            return new LoginResponsePojo(source);
        }

        @Override
        public LoginResponsePojo[] newArray(int size) {
            return new LoginResponsePojo[size];
        }
    };
}