package com.annotation.android.annotation01.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CG-DTE on 04-10-2017.
 */

public class AdminLoginPojo implements Serializable, Parcelable {
    @Expose
    @SerializedName("username")
    String username;
    @Expose
    @SerializedName("password")
    String password;
    @Expose
    @SerializedName("name")
    String name;
    @Expose
    @SerializedName("mobile")
    String mobile;
    @Expose
    @SerializedName("email")
    String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdminLoginPojo() {

    }

    public AdminLoginPojo(String username, String password, String name, String mobile, String email) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.email);
    }

    protected AdminLoginPojo(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.email = in.readString();
    }

    public static final Parcelable.Creator<AdminLoginPojo> CREATOR = new Parcelable.Creator<AdminLoginPojo>() {
        @Override
        public AdminLoginPojo createFromParcel(Parcel source) {
            return new AdminLoginPojo(source);
        }

        @Override
        public AdminLoginPojo[] newArray(int size) {
            return new AdminLoginPojo[size];
        }
    };
}
