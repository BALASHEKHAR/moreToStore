package com.example.mymart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
ViewPager productviewpage;
TabLayout producttablayout;
FloatingActionButton favbutton;
ViewPager productDetailsViewpager;
TabLayout productDetailsTabLayout;
boolean edit_to_fav=false;
//rating
    LinearLayout ratenowlayout;
//rating
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productviewpage=findViewById(R.id.product_image_viewpager);
        producttablayout=findViewById(R.id.image_tab_layout);
        favbutton=findViewById(R.id.add_to_fav_button);

        ArrayList<Integer> productimages=new ArrayList<>();
        productimages.add(R.drawable.ic_baseline_phone_android_24);
        productimages.add(R.drawable.ic_baseline_phone_android_24);
        productimages.add(R.drawable.ic_baseline_phone_android_24);
        productimages.add(R.drawable.ic_baseline_phone_android_24);
        productimages.add(R.drawable.ic_baseline_phone_android_24);

        ProductImagesAdapter productImagesAdapter=new ProductImagesAdapter(productimages);
        productviewpage.setAdapter(productImagesAdapter);
            producttablayout.setupWithViewPager(productviewpage,true);
            favbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edit_to_fav)
                    {
                        edit_to_fav=false;
                        favbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#6E6767")));
                    }
                    else
                    {
                        favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                        edit_to_fav=true;
                    }
                }
            });
        productDetailsTabLayout=findViewById(R.id.product_details_tablayout);
        productDetailsViewpager=findViewById(R.id.product_details_viewpager);
            productDetailsViewpager.setAdapter(new ProductDtailsViewPager(getSupportFragmentManager(),
                    productDetailsTabLayout.getTabCount()));
            productDetailsViewpager.setOnPageChangeListener(new
                    TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
            productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    productDetailsViewpager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            //rating
        ratenowlayout=findViewById(R.id.rate_now_container);
        for(int x=0;x<ratenowlayout.getChildCount();x++)
        {
            final  int star_pos=x;
            ratenowlayout.getChildAt(star_pos).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(star_pos);
                }
            });

        }
            //rating
    }

    private void setRating(int star_pos) {
        for(int x=0;x<ratenowlayout.getChildCount();x++)
        {
            ImageView star=(ImageView)ratenowlayout.getChildAt(x);
            star.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x<=star_pos)
            {
                star.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mainsearchicon)
        {
            //TODO:
            return true;

        }
        else if(item.getItemId()==android.R.id.home)
        {
            finish();
            return true;
        }
        else  if(item.getItemId()==R.id.maincart)
        {
            //TODO:
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}