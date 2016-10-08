package com.aalto.solutions.currentweather.model;

/**
 * Created by marko on 10/7/2016.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityWeather
{
    @SerializedName("coord")
    @Expose
    public Coord coord;

    @SerializedName("weather")
    @Expose
    public List<Weather> weather = new ArrayList<Weather>();

    @SerializedName("base")
    @Expose
    public String base;

    @SerializedName("main")
    @Expose
    public Main main;

    @SerializedName("wind")
    @Expose
    public Wind wind;

    @SerializedName("clouds")
    @Expose
    public Clouds clouds;

    @SerializedName("dt")
    @Expose
    public int dt;

    @SerializedName("sys")
    @Expose
    public Sys sys;

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("cod")
    @Expose
    public int cod;

}
