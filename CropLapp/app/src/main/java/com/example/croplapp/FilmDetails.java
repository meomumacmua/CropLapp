/*
* FilmDetails.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Film object
* Include: Contructor, get/set method
*/
package com.example.croplapp;

public class FilmDetails {

    public String filmname;
    public String filmprice;
    public String filmstatus;
    public String filmimage;
    public String iso;
    public String shot;
    public String link;
    public int nPic;

//    public FilmDetails() {
//    }
//
//    public FilmDetails(String filmName, String filmPrice, String filmImage, String filmStatus) {
//        this.filmname = filmName;
//        this.filmprice = filmPrice;
//        this.filmimage = filmImage;
//        this.filmstatus = filmStatus;
//    }
//
    // Constructor
    public FilmDetails(String filmname,String filmprice, String iso, String shot, int nPic,String link) {
        this.filmname = filmname;
        this.filmprice = filmprice;
        this.iso = iso;
        this.shot = shot;
        this.link = link;
        this.nPic = nPic;
    }

    // Constructor
    public FilmDetails(String filmDetail)
    {
        String[] array = filmDetail.split(",");
        this.filmname = array[0];
        this.filmprice = array[2];
        this.filmimage = array[3].trim();
        this.filmstatus = array[4];
        this.iso = array[6];
        this.shot = array[7];
        this.link = array[8].trim();
        this.nPic = array[8].split(";").length;
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

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getShot() {
        return shot;
    }

    public void setShot(String shot) {
        this.shot = shot;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getnPic() {
        return nPic;
    }

    public void setnPic(int nPic) {
        this.nPic = nPic;
    }
}
