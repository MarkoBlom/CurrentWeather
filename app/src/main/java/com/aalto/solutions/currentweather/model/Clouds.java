package com.aalto.solutions.currentweather.model;

/**
 * Created by marko on 10/7/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds
{

    @SerializedName("all")
    @Expose
    public int all;

}
