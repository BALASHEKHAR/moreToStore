package com.example.mymart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

RecyclerView caterecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        caterecycler=findViewById(R.id.caterecycler);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ////////


        ////////////////////////Banner
        ArrayList<sliderModel> sliderModelArrayList=new ArrayList<>();

        sliderModelArrayList.add(new sliderModel(R.drawable.cursor,"#077AE4"));
        sliderModelArrayList.add(new sliderModel(R.drawable.erroricon,"#077AE4"));
        sliderModelArrayList.add(new sliderModel(R.drawable.close_icon,"#077AE4"));

        sliderModelArrayList.add(new sliderModel(R.drawable.user,"#077AE4"));
        sliderModelArrayList.add(new sliderModel(R.drawable.search,"#077AE4"));
        sliderModelArrayList.add(new sliderModel(R.drawable.redmail,"#077AE4"));
        sliderModelArrayList.add(new sliderModel(R.drawable.search,"#077AE4"));
        sliderModelArrayList.add(new sliderModel(R.drawable.redmail,"#077AE4"));
        sliderModelArrayList.add(new sliderModel(R.drawable.cursor,"#077AE4"));

        sliderModelArrayList.add(new sliderModel(R.drawable.erroricon,"#077AE4"));
        sliderModelArrayList.add(new sliderModel(R.drawable.close_icon,"#077AE4"));
        sliderModelArrayList.add(new sliderModel(R.drawable.user,"#077AE4"));


        ////////////////////////Banner

        //////strop ad

        //////strop ad


        /////////////horizontal product layout
        ArrayList<horizontalproductmodel> horizontalproductmodelArrayList=new ArrayList<>();
        horizontalproductmodelArrayList.add(new horizontalproductmodel(R.drawable.ic_baseline_phone_android_24,
                "redmi 24","SD 635 processor","RS.5999"));
        horizontalproductmodelArrayList.add(new horizontalproductmodel(R.drawable.ic_baseline_phone_android_24,
                "redmi 24","SD 635 processor","RS.5999"));
        horizontalproductmodelArrayList.add(new horizontalproductmodel(R.drawable.ic_baseline_phone_android_24,
                "redmi 24","SD 635 processor","RS.5999"));
        horizontalproductmodelArrayList.add(new horizontalproductmodel(R.drawable.ic_baseline_phone_android_24,
                "redmi 24","SD 635 processor","RS.5999"));
        horizontalproductmodelArrayList.add(new horizontalproductmodel(R.drawable.ic_baseline_phone_android_24,
                "redmi 24","SD 635 processor","RS.5999"));
        horizontalproductmodelArrayList.add(new horizontalproductmodel(R.drawable.ic_baseline_phone_android_24,
                "redmi 24","SD 635 processor","RS.5999"));
        horizontalproductmodelArrayList.add(new horizontalproductmodel(R.drawable.ic_baseline_phone_android_24,
                "redmi 24","SD 635 processor","RS.5999"));
        horizontalproductmodelArrayList.add(new horizontalproductmodel(R.drawable.ic_baseline_phone_android_24,
                "redmi 24","SD 635 processor","RS.5999"));
        horizontalproductmodelArrayList.add(new horizontalproductmodel(R.drawable.ic_baseline_phone_android_24,
                "redmi 24","SD 635 processor","RS.5999"));
        horizontalproductmodelArrayList.add(new horizontalproductmodel(R.drawable.ic_baseline_phone_android_24,
                "redmi 24","SD 635 processor","RS.5999"));
        horizontalproductmodelArrayList.add(new horizontalproductmodel(R.drawable.ic_baseline_phone_android_24,
                "redmi 24","SD 635 processor","RS.5999"));

        /////////////horizontal product layout






        ///////// final recycler



        LinearLayoutManager finallinear=new LinearLayoutManager(this);
        finallinear.setOrientation(LinearLayoutManager.VERTICAL);
        caterecycler.setLayoutManager(finallinear);

        ArrayList<HomePageModel> homePageModelArrayList=new ArrayList<>();
        homePageModelArrayList.add(new HomePageModel(0,sliderModelArrayList));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));
        homePageModelArrayList.add(new HomePageModel(2,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(2,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));
        homePageModelArrayList.add(new HomePageModel(3,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(3,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#ffff00"));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));
        homepageadapter adapter=new homepageadapter(homePageModelArrayList);
        caterecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
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
        return super.onOptionsItemSelected(item);

    }
}