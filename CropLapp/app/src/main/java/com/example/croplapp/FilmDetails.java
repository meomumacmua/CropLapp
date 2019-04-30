package com.example.croplapp;

public class FilmDetails {

    public String filmname;
    public String filmprice;
    public String filmstatus;
    public String filmimage;

    public FilmDetails() {
    }

    public FilmDetails(String filmName, String filmPrice, String filmImage, String filmStatus) {
        this.filmname = filmName;
        this.filmprice = filmPrice;
        this.filmimage = filmImage;
        this.filmstatus = filmStatus;
    }


    public String getFilmname() {
        return filmname;
    }

    public void setFilmname(String filmName) {
        this.filmname = filmName;
    }

    public String getFilmprice() {
        return filmprice;
    }

    public void setFilmprice(String filmPrice) {
        this.filmprice = filmPrice;
    }

    public String getFilmstatus() {
        return filmstatus;
    }

    public void setFilmstatus(String filmStatus) {
        this.filmstatus = filmStatus;
    }

    public String getFilmimage() {
        return filmimage;
    }

    public void setFilmimage(String filmImage) {
        this.filmimage = filmImage;
    }
}
