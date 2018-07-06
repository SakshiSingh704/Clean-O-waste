package com.annotation.android.annotation01.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by BlackHat on 6/27/2017.
 */

public class GetPostListModal implements Serializable, Parcelable {
    @Expose
    @SerializedName("postid")
    String postid;
    @Expose
    @SerializedName("title")
    String title;
    @Expose
    @SerializedName("postedby")
    String postedby;
    @Expose
    @SerializedName("datetime")
    String datetime;
    @Expose
    @SerializedName("location")
    String location;
   @Expose
    @SerializedName("img")
    String img;
    @Expose
    @SerializedName("status")
    String status;

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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

    public GetPostListModal(String postid, String title, String postedby, String datetime, String location, String img, String status) {

        this.postid = postid;
        this.title = title;
        this.postedby = postedby;
        this.datetime = datetime;
        this.location = location;
        this.img = img;
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.postid);
        dest.writeString(this.title);
        dest.writeString(this.postedby);
        dest.writeString(this.datetime);
        dest.writeString(this.location);
        dest.writeString(this.img);
        dest.writeString(this.status);
    }

    protected GetPostListModal(Parcel in) {
        this.postid = in.readString();
        this.title = in.readString();
        this.postedby = in.readString();
        this.datetime = in.readString();
        this.location = in.readString();
        this.img = in.readString();
        this.status = in.readString();
    }

    public static final Creator<GetPostListModal> CREATOR = new Creator<GetPostListModal>() {
        @Override
        public GetPostListModal createFromParcel(Parcel source) {
            return new GetPostListModal(source);
        }

        @Override
        public GetPostListModal[] newArray(int size) {
            return new GetPostListModal[size];
        }
    };
}
