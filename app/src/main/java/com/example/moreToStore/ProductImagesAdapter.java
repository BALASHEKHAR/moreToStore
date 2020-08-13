package com.example.moreToStore;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

class ProductImagesAdapter extends PagerAdapter {
    ArrayList<String> productImages;

    public ProductImagesAdapter(ArrayList<String> productImages) {
        this.productImages = productImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
      //  return super.instantiateItem(container, position);
        ImageView image=new ImageView(container.getContext());
       // image.setImageResource(productImages.get(position));
        Glide.with(container.getContext()).load(productImages.get(position))
                .apply(new RequestOptions().placeholder(R.drawable.bplaceholder)).into(image);
        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((ImageView)object);
    }

    @Override
    public int getCount() {
        return productImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
