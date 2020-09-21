package com.example.moreToStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.moreToStore.DBQuiries.cList;
import static com.example.moreToStore.DBQuiries.cListName;

public class CategoryActivity extends AppCompatActivity {
    List<HomePageModel> homePageModelfakeList=new ArrayList<>();

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

////home fake list


        ArrayList<sliderModel> sliderModelList = new ArrayList<>();
        sliderModelList.add(new sliderModel("null", "#ffffff"));
        sliderModelList.add(new sliderModel("null", "#ffffff"));
        sliderModelList.add(new sliderModel("null", "#ffffff"));
        sliderModelList.add(new sliderModel("null", "#ffffff"));
        sliderModelList.add(new sliderModel("null", "#ffffff"));
        sliderModelList.add(new sliderModel("null", "#ffffff"));
        sliderModelList.add(new sliderModel("null", "#ffffff"));

        ArrayList<horizontalproductmodel> horizontalproductmodelList =
                new ArrayList<>();
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));

        homePageModelfakeList.add(new HomePageModel(
                0,sliderModelList
        ));
        homePageModelfakeList.add(new HomePageModel(
                1,"",
                "#ffffff"
        ));
        homePageModelfakeList.add(new HomePageModel(
                2,"",
                "#ffffff",horizontalproductmodelList,
                new ArrayList<Wishist_model>()
        ));
        homePageModelfakeList.add(new HomePageModel(
                3,"",
                "#ffffff",horizontalproductmodelList
        ));


/////home fake list



        LinearLayoutManager finallinear=new LinearLayoutManager(this);
        finallinear.setOrientation(LinearLayoutManager.VERTICAL);
        caterecycler.setLayoutManager(finallinear);
        adapter=new homepageadapter(homePageModelfakeList);

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


           DBQuiries.loadFragmentdata(caterecycler,this,cListName.size()-1,title);
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
            Intent searchI=new Intent(this,searchActivity.class);
            startActivity(searchI);
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