package com.annotation.android.annotation01.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BlackHat on 6/27/2017.
 */

public class GetPostListPojo implements Serializable {
    @Expose
    @SerializedName("result")
    List<GetPostListModal> result;

    public List<GetPostListModal> getResult() {
        return result;
    }

    public void setResult(List<GetPostListModal> result) {
        this.result = result;
    }

    public GetPostListPojo(List<GetPostListModal> result) {

        this.result = result;
    }

    public GetPostListPojo() {

    }
}
