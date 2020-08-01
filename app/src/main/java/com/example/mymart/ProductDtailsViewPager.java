package com.example.mymart;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

class ProductDtailsViewPager extends FragmentPagerAdapter {
    int tabs;
    public ProductDtailsViewPager(@NonNull FragmentManager fm,int tabCount) {
        super(fm);
        tabs=tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new ProductDescriptionFragment();

            case 1:
                return new ProductSpecficationFragment();

            case 2:
                return new ProductDescriptionFragment();

            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
