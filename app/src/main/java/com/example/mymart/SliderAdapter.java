package com.example.mymart;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

class SliderAdapter extends PagerAdapter {
    ArrayList<sliderModel> sliderModelArrayList;

    public SliderAdapter(ArrayList<sliderModel> sliderModelArrayList) {
        this.sliderModelArrayList = sliderModelArrayList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View v= LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout,container,false);
        ImageView banner=v.findViewById(R.id.banner_slide);
        LinearLayout bannerContainer=v.findViewById(R.id.bannercontainer);
        bannerContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sliderModelArrayList.get(position).getColor())));

        banner.setImageResource(sliderModelArrayList.get(position).getBanner());

        container.addView(v);
        return v;
    }

    @Override
    public int getCount() {
        return sliderModelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((View)object);
    }
}
