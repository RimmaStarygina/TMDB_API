package com.example.tmdb.model;

import java.io.Serializable;

public class MovieObject implements Serializable {
    String rate;
    String name;
    String overview;
    String img;

    public MovieObject() {
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "MovieObject{" +
                "id='" + rate + '\'' +
                ", name='" + name + '\'' +
                ", overview='" + overview + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
