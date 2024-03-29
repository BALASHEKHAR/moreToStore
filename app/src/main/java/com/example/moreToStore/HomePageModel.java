package com.example.moreToStore;

import java.util.ArrayList;
import java.util.List;

class HomePageModel {
    public static final int banner=0;
    public static final int stripno=1;
    public static final int horizontaproduct=2;
    public static final int gridaproduct=3;
    private  int type;
    String color;
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
    String res;


    public HomePageModel(int type, String res, String color) {
        this.type = type;
        this.res = res;
        this.color = color;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
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
List<Wishist_model> viewallmylist;

    public List<Wishist_model> getViewallmylist() {
        return viewallmylist;
    }

    public void setViewallmylist(List<Wishist_model> viewallmylist) {
        this.viewallmylist = viewallmylist;
    }

    public HomePageModel(int type, String hptitle, String background,
                         ArrayList<horizontalproductmodel> horizontalproductmodelArrayList,
                         List<Wishist_model> viewallmylist) {
        this.type = type;
        this.viewallmylist=viewallmylist;
        this.hptitle = hptitle;
        this.color=background;
        this.horizontalproductmodelArrayList = horizontalproductmodelArrayList;
    }



    public HomePageModel(int type, String hptitle, String background,ArrayList<horizontalproductmodel> horizontalproductmodelArrayList) {
        this.type = type;
        this.hptitle = hptitle;
        this.color=background;
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
