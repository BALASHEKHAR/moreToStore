package com.example.moreToStore;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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

       // banner.setImageResource(sliderModelArrayList.get(position).getBanner());

        Glide.with(container.getContext()).load(sliderModelArrayList.get(position)
        .getBanner()).apply(new RequestOptions().placeholder(R.drawable.bplaceholder))
                .into(banner);
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
