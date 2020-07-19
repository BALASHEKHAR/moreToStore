package com.example.mymart;

import java.util.ArrayList;

class HomePageModel {
    public static final int banner=0;
    public static final int stripno=1;
    public static final int horizontaproduct=2;
    public static final int gridaproduct=3;
    private  int type;

    ////banner slider
    ArrayList<sliderModel> sliderModelArrayList;
    public HomePageModel(int type, ArrayList<sliderModel> sliderModelArrayList) {
        this.type = type;
        this.sliderModelArrayList = sliderModelArrayList;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public ArrayList<sliderModel> getSliderModelArrayList() {
        return sliderModelArrayList;
    }
    public void setSliderModelArrayList(ArrayList<sliderModel> sliderModelArrayList) {
        this.sliderModelArrayList = sliderModelArrayList;
    }
    ////banner slider

    //strip ad
    int res;
    String color;

    public HomePageModel(int type, int res, String color) {
        this.type = type;
        this.res = res;
        this.color = color;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    //strip ad

    ///horizontal product and grid product
    String hptitle;
    ArrayList<horizontalproductmodel> horizontalproductmodelArrayList;

    public HomePageModel(int type, String hptitle, ArrayList<horizontalproductmodel> horizontalproductmodelArrayList) {
        this.type = type;
        this.hptitle = hptitle;
        this.horizontalproductmodelArrayList = horizontalproductmodelArrayList;
    }

    public String getHptitle() {
        return hptitle;
    }

    public void setHptitle(String hptitle) {
        this.hptitle = hptitle;
    }

    public ArrayList<horizontalproductmodel> getHorizontalproductmodelArrayList() {
        return horizontalproductmodelArrayList;
    }

    public void setHorizontalproductmodelArrayList(ArrayList<horizontalproductmodel> horizontalproductmodelArrayList) {
        this.horizontalproductmodelArrayList = horizontalproductmodelArrayList;
    }
///horizontal product and grid product




}
