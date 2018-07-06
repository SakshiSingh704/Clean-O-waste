package com.annotation.android.annotation01.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BlackHat on 6/26/2017.
 */

public class GetCityPojo implements Serializable{
    @Expose
    @SerializedName("result")
    List<GetCityModal> result;

    public List<GetCityModal> getResult() {
        return result;
    }

    public void setResult(List<GetCityModal> result) {
        this.result = result;
    }

    public GetCityPojo() {

    }

    public GetCityPojo(List<GetCityModal> result) {

        this.result = result;
    }
}
