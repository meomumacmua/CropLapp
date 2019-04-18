package com.example.croplapp;

public class FilmDetails {

    public String filmname;
    public String filmprice;
    public String filmstatus;
    public String filmimage;

    public FilmDetails() {
    }

    public String getFilmname() {
        return filmname;
    }

    public void setFilmname(String filmname) {
        this.filmname = filmname;
    }

    public String getFilmprice() {
        return filmprice;
    }

    public void setFilmprice(String filmprice) {
        this.filmprice = filmprice;
    }

    public String getFilmstatus() {
        return filmstatus;
    }

    public void setFilmstatus(String filmstatus) {
        this.filmstatus = filmstatus;
    }

    public String getFilmimage() {
        return filmimage;
    }

    public void setFilmimage(String filmstatus) {
        this.filmstatus = filmstatus;
    }

    public FilmDetails(String filname, String filmprice, String filmimage, String filmstatus) {
        this.filmname = filname;
        this.filmprice = filmprice;
        this.filmimage = filmimage;
        this.filmstatus = filmstatus;
    }



}
