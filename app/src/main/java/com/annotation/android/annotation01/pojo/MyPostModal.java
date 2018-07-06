package com.annotation.android.annotation01.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by BlackHat on 6/26/2017.
 */

public class MyPostModal implements Serializable, Parcelable {
    @Expose
    @SerializedName("pid")
    String pid;
    @Expose
    @SerializedName("title")
    String title;
    @Expose
    @SerializedName("description")
    String description;
    @Expose
    @SerializedName("city")
    String city;
    @Expose
    @SerializedName("location")
    String location;
    @Expose
    @SerializedName("cityid")
    String cityid;
    @Expose
    @SerializedName("locationid")
    String locationid;
    @Expose
    @SerializedName("status")
    String status;
    @Expose
    @SerializedName("datentime")
    String datentime;
    @Expose
    @SerializedName("uid")
    String uid;
    @Expose
    @SerializedName("image")
    String image;
    @Expose
    @SerializedName("address")
    String address;
    @Expose
    @SerializedName("rate")
    String rate;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatentime() {
        return datentime;
    }

    public void setDatentime(String datentime) {
        this.datentime = datentime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public MyPostModal(String pid, String title, String description, String city, String location, String cityid, String locationid, String status, String datentime, String uid, String image, String address, String rate) {

        this.pid = pid;
        this.title = title;
        this.description = description;
        this.city = city;
        this.location = location;
        this.cityid = cityid;
        this.locationid = locationid;
        this.status = status;
        this.datentime = datentime;
        this.uid = uid;
        this.image = image;
        this.address = address;
        this.rate = rate;
    }

    public MyPostModal() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pid);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.city);
        dest.writeString(this.location);
        dest.writeString(this.cityid);
        dest.writeString(this.locationid);
        dest.writeString(this.status);
        dest.writeString(this.datentime);
        dest.writeString(this.uid);
        dest.writeString(this.image);
        dest.writeString(this.address);
        dest.writeString(this.rate);
    }

    protected MyPostModal(Parcel in) {
        this.pid = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.city = in.readString();
        this.location = in.readString();
        this.cityid = in.readString();
        this.locationid = in.readString();
        this.status = in.readString();
        this.datentime = in.readString();
        this.uid = in.readString();
        this.image = in.readString();
        this.address = in.readString();
        this.rate = in.readString();
    }

    public static final Parcelable.Creator<MyPostModal> CREATOR = new Parcelable.Creator<MyPostModal>() {
        @Override
        public MyPostModal createFromParcel(Parcel source) {
            return new MyPostModal(source);
        }

        @Override
        public MyPostModal[] newArray(int size) {
            return new MyPostModal[size];
        }
    };
}
