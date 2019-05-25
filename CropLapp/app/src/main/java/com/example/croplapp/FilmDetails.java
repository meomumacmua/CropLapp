package com.example.croplapp;

public class FilmDetails {

    public String filmname;
    public String filmprice;
    public String filmstatus;
    public String filmimage;

    //them
    public int image;
    public String title;
    public String desc;
    //end
    public FilmDetails() {
    }

    public FilmDetails(String filmName, String filmPrice, String filmImage, String filmStatus) {
        this.filmname = filmName;
        this.filmprice = filmPrice;
        this.filmimage = filmImage;
        this.filmstatus = filmStatus;
    }

    //them
    public FilmDetails(int image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }
    //end


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

    //them
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    //end
}
