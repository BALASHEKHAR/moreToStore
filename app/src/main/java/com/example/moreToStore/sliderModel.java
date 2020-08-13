package com.example.moreToStore;

class sliderModel {
    String banner;
    String color;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public sliderModel(String banner, String color) {
        this.banner = banner;
        this.color = color;
    }
}
