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

import static com.example.mymart.DBQuiries.cList;
import static com.example.mymart.DBQuiries.cListName;

public class CategoryActivity extends AppCompatActivity {

RecyclerView caterecycler;
    Toolbar toolbar;
    String title;
    homepageadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
     //    = findViewById(R.id.toolbar);
        title=getIntent().getStringExtra("title");
        setSupportActionBar(toolbar);
        caterecycler=findViewById(R.id.caterecycler);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);



      /*  sliderModelArrayList.add(new sliderModel(R.drawable.cursor,"#077AE4"));
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
        sliderModelArrayList.add(new sliderModel(R.drawable.user,"#077AE4"));*/


        ////////////////////////Banner

        //////strop ad

        //////strop ad


        /////////////horizontal product layout
     //   ArrayList<horizontalproductmodel> horizontalproductmodelArrayList=new ArrayList<>();

        /////////////horizontal product layout






        ///////// final recycler



        LinearLayoutManager finallinear=new LinearLayoutManager(this);
        finallinear.setOrientation(LinearLayoutManager.VERTICAL);
        caterecycler.setLayoutManager(finallinear);

     //   ArrayList<HomePageModel> homePageModelArrayList=new ArrayList<>();
       /* homePageModelArrayList.add(new HomePageModel(0,sliderModelArrayList));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));
        homePageModelArrayList.add(new HomePageModel(2,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(2,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));
        homePageModelArrayList.add(new HomePageModel(3,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(3,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#ffff00"));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));*/

       int listpos=0;
       for(int x=0;x<cListName.size(); x++)
       {
           if(cListName.get(x).equals(title.toUpperCase()))
           {
               listpos=x;
           }
       }
       if(listpos==0)
       { cList.add(new ArrayList<HomePageModel>());
           cListName.add(title.toUpperCase());
           adapter=new homepageadapter(cList.get(cListName.size()-1));

           DBQuiries.setFragmentdata(adapter,this,cListName.size()-1,title);
       }
       else
       {
           adapter=new homepageadapter(cList.get(listpos));

       }

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