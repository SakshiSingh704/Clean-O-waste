package com.annotation.android.annotation01.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BlackHat on 6/26/2017.
 */

public class GetLocalityPojo implements Serializable {
    @Expose
    @SerializedName("result")
    List<GetLocalityModal> result;

    public List<GetLocalityModal> getResult() {
        return result;
    }

    public void setResult(List<GetLocalityModal> result) {
        this.result = result;
    }

    public GetLocalityPojo() {

    }

    public GetLocalityPojo(List<GetLocalityModal> result) {

        this.result = result;
    }
}
