package com.example.apilast.models;

import java.util.ArrayList;

public class RootTrailer {
    public int total;
    public ArrayList<Trailer> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Trailer> getItems() {
        return items;
    }

    public void setItems(ArrayList<Trailer> items) {
        this.items = items;
    }
}
