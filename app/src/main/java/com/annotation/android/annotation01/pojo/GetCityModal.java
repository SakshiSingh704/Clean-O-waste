package com.annotation.android.annotation01.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by BlackHat on 6/26/2017.
 */

public class GetCityModal implements Serializable {
    @Expose
    @SerializedName("id")
    String id;
    @Expose
    @SerializedName("cityname")
    String cityName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public GetCityModal(String id, String cityName) {

        this.id = id;
        this.cityName = cityName;
    }

    public GetCityModal() {

    }
}
