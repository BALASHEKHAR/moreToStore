package com.example.mymart;

class categoryModel {
    private String cimage;
    private String cname;

    public categoryModel(String cimage, String cname) {
        this.cimage = cimage;
        this.cname = cname;
    }

    public void setCimage(String cimage) {
        this.cimage = cimage;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCimage() {
        return cimage;
    }

    public String getCname() {
        return cname;
    }
}
