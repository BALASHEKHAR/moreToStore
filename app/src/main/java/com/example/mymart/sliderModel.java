package com.example.mymart;

class sliderModel {
    int banner;
    String color;

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public sliderModel(int banner, String color) {
        this.banner = banner;
        this.color = color;
    }
}
