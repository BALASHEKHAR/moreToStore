package com.example.moreToStore;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

class ProductDtailsViewPager extends FragmentPagerAdapter {
    int tabs;
    String product_descc;
    String product_otherr;
    List<ProductSpecifiModel> productSpecifiModelListt;
    public ProductDtailsViewPager(@NonNull FragmentManager fm,int tabCount,
                                  String product_descc,String product_otherr,List<ProductSpecifiModel> productSpecifiModelListt) {
        super(fm);
        this.product_descc=product_descc;
        this.productSpecifiModelListt=productSpecifiModelListt;
        this.product_otherr=product_otherr;
        tabs=tabCount;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:


             ProductDescriptionFragment p=new ProductDescriptionFragment();
             p.bodyy=(product_descc);



                return p;

            case 1:
                ProductSpecficationFragment.productSpecifiModelList=productSpecifiModelListt;
                return new ProductSpecficationFragment();

            case 2:
                ProductDescriptionFragment pp=new ProductDescriptionFragment();
                pp.bodyy=(product_otherr);
                return pp;


            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
