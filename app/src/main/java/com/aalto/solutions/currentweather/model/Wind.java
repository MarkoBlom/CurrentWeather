package com.aalto.solutions.currentweather.model;

/**
 * Created by marko on 10/7/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind
{
    @SerializedName("speed")
    @Expose
    public float speed;

    @SerializedName("deg")
    @Expose
    public float deg;
}
