package com.aalto.solutions.currentweather.model;

/**
 * Created by marko on 10/7/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys
{
    @SerializedName("message")
    @Expose
    public float message;

    @SerializedName("country")
    @Expose
    public String country;

    @SerializedName("sunrise")
    @Expose
    public int sunrise;

    @SerializedName("sunset")
    @Expose
    public int sunset;
}
