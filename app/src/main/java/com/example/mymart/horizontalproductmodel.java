package com.example.mymart;

class horizontalproductmodel {
    int pimage;
    String pname;
    String pdesc;
    String pprice;

    public horizontalproductmodel(int pimage, String pname, String pdesc, String pprice) {
        this.pimage = pimage;
        this.pname = pname;
        this.pdesc = pdesc;
        this.pprice = pprice;
    }

    public int getPimage() {
        return pimage;
    }

    public void setPimage(int pimage) {
        this.pimage = pimage;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }
}
