package com.annotation.android.annotation01.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BlackHat on 6/26/2017.
 */

public class MyPostPojo implements Serializable {
    @Expose
    @SerializedName("result")
    List<MyPostModal> result;

    public List<MyPostModal> getResult() {
        return result;
    }

    public void setResult(List<MyPostModal> result) {
        this.result = result;
    }

    public MyPostPojo(List<MyPostModal> result) {

        this.result = result;
    }

    public MyPostPojo() {

    }
}
