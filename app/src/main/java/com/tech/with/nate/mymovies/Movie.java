package com.tech.with.nate.mymovies;

public class Movie {
    private String thumbnailUrl;
    private String movieName;
    private String length;
    private String rating;
    private String releaseDate;
    private String desc;
    private String year;
    private int id;

    public Movie() {
    }

    public Movie(String thumbnailUrl, String movieName, String length, String rating, String releaseDate, String desc) {
        this.thumbnailUrl = thumbnailUrl;
        this.movieName = movieName;
        this.length = length;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.desc = desc;
    }

    public Movie(String thumbnailUrl, String movieName, String length, String rating, String releaseDate, String desc, String year) {
        this.thumbnailUrl = thumbnailUrl;
        this.movieName = movieName;
        this.length = length;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.desc = desc;
        this.year = year;
    }

    public Movie(String thumbnailUrl, String movieName, String year) {
        this.thumbnailUrl = thumbnailUrl;
        this.movieName = movieName;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
