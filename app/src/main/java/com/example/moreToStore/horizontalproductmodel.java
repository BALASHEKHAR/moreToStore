package com.example.moreToStore;

class horizontalproductmodel {
    String pimage;
    String pname;
    String pdesc;
    String pprice;
    String product_ID;

    public horizontalproductmodel(String product_ID,
                                  String pimage,
                                  String pname, String pdesc, String pprice) {
        this.pimage = pimage;
        this.pname = pname;
        this.pdesc = pdesc;
        this.product_ID=product_ID;
        this.pprice = pprice;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
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
