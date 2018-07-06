package com.annotation.android.annotation01.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BlackHat on 7/8/2017.
 */

public class GetSinglePostPojo implements Serializable {
    @Expose
    @SerializedName("result")
    List<GetSinglePostList> result;

    public List<GetSinglePostList> getResult() {
        return result;
    }

    public void setResult(List<GetSinglePostList> result) {
        this.result = result;
    }

    public GetSinglePostPojo(List<GetSinglePostList> result) {

        this.result = result;
    }

    public GetSinglePostPojo() {

    }
}
