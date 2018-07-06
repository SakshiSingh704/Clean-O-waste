package com.annotation.android.annotation01.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by BlackHat on 6/26/2017.
 */

public class GetLocalityModal implements Serializable {
    @Expose
    @SerializedName("id")
    String id;

    @Expose
    @SerializedName("cityid")
    String city_id;

    @Expose
    @SerializedName("location")
    String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public GetLocalityModal(String id, String city_id, String location) {

        this.id = id;
        this.city_id = city_id;
        this.location = location;
    }

    public GetLocalityModal() {
    }
}
