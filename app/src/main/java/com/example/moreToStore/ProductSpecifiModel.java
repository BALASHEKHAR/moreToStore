package com.example.moreToStore;

class ProductSpecifiModel {
    public  static final int SPECIFICATINO_TITLE=0;
    public  static final int SPECIFICATINO_BIDY=1;

    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    ///body
String featurename,
        featurevalue;
    public ProductSpecifiModel(int type,String featurename, String featurevalue) {
        this.type=type;
        this.featurename = featurename;
        this.featurevalue = featurevalue;
    }

    public String getFeaturename() {
        return featurename;
    }

    public void setFeaturename(String featurename) {
        this.featurename = featurename;
    }

    public String getFeaturevalue() {
        return featurevalue;
    }

    public void setFeaturevalue(String featurevalue) {
        this.featurevalue = featurevalue;
    }
    ////body

    ///title
    String title;
    public ProductSpecifiModel(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

///title
}
