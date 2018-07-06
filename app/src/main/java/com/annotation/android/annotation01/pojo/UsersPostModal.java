package com.annotation.android.annotation01.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CG-DTE on 05-10-2017.
 */

public class UsersPostModal implements Serializable, Parcelable {
    @Expose
    @SerializedName("postid")
    String postId;
    @Expose
    @SerializedName("title")
    String title;
    @Expose
    @SerializedName("postedby")
    String postedBy;
    @Expose
    @SerializedName("datetime")
    String dateTime;
    @Expose
    @SerializedName("location")
    String location;
    @Expose
    @SerializedName("img")
    String img;
    @Expose
    @SerializedName("status")
    String status;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UsersPostModal(String postId, String title, String postedBy, String dateTime, String location, String img, String status) {

        this.postId = postId;
        this.title = title;
        this.postedBy = postedBy;
        this.dateTime = dateTime;
        this.location = location;
        this.img = img;
        this.status = status;
    }

    public UsersPostModal() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.postId);
        dest.writeString(this.title);
        dest.writeString(this.postedBy);
        dest.writeString(this.dateTime);
        dest.writeString(this.location);
        dest.writeString(this.img);
        dest.writeString(this.status);
    }

    protected UsersPostModal(Parcel in) {
        this.postId = in.readString();
        this.title = in.readString();
        this.postedBy = in.readString();
        this.dateTime = in.readString();
        this.location = in.readString();
        this.img = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<UsersPostModal> CREATOR = new Parcelable.Creator<UsersPostModal>() {
        @Override
        public UsersPostModal createFromParcel(Parcel source) {
            return new UsersPostModal(source);
        }

        @Override
        public UsersPostModal[] newArray(int size) {
            return new UsersPostModal[size];
        }
    };
}
