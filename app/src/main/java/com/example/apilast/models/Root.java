package com.example.apilast.models;

import java.util.ArrayList;

public class Root {
    private int pagesCount;
    private ArrayList<Film> films;


    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public ArrayList<Film> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }


}
