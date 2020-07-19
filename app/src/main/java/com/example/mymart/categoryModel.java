package com.example.mymart;

class categoryModel {
    private int cimage;
    private String cname;

    public categoryModel(int cimage, String cname) {
        this.cimage = cimage;
        this.cname = cname;
    }

    public void setCimage(int cimage) {
        this.cimage = cimage;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getCimage() {
        return cimage;
    }

    public String getCname() {
        return cname;
    }
}
