package com.example.mymart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
RecyclerView recyclerView;
    categoryAdapter categoryAdapter;
    ////////////////////////Banner
    int currentpage=2;
    Timer timer;
    ViewPager bannersliderviewpager;
    ArrayList<sliderModel> sliderModelArrayList=new ArrayList<>();
       long DELAY_TIME=2000;
    long PERIOD_TIME=2000;

    ////////////////////////Banner

    /////strip ad
    ImageView stripimage;
    LinearLayout striplayout;

    /////strip ad

    /////////////horizontal product layout
    TextView hsltitle;
    Button hslmore;
    RecyclerView hslrecyclerView;
    ArrayList<horizontalproductmodel> horizontalproductmodelArrayList=new ArrayList<>();
    /////////////horizontal product layout


    ///////////grid product layout


    TextView gname;
    Button gmore;
    GridView ggrid;
    ///////////grid product layout

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home2, container, false);
        recyclerView=v.findViewById(R.id.categoryrecyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<categoryModel> categoryModels=new ArrayList<>();
        categoryModels.add(new categoryModel(1,"home"));
        categoryModels.add(new categoryModel(1,"phones"));
        categoryModels.add(new categoryModel(1,"electronics"));
        categoryModels.add(new categoryModel(1,"shops"));
        categoryModels.add(new categoryModel(1,"others"));
        categoryModels.add(new categoryModel(1,"lllll"));
        categoryModels.add(new categoryModel(1,"some"));
        categoryModels.add(new categoryModel(1,"home"));
        categoryModels.add(new categoryModel(1,"phones"));
        categoryModels.add(new categoryModel(1,"electronics"));
        categoryModels.add(new categoryModel(1,"shops"));
        categoryModels.add(new categoryModel(1,"others"));
        categoryModels.add(new categoryModel(1,"lllll"));
        categoryModels.add(new categoryModel(1,"some"));
        categoryAdapter=new categoryAdapter(categoryModels);
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();


        ////////////////////////Banner
        bannersliderviewpager=v.findViewById(R.id.banner_slider_viewpager);

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



        SliderAdapter sliderAdapter=new SliderAdapter(sliderModelArrayList);
        bannersliderviewpager.setAdapter(sliderAdapter);
        bannersliderviewpager.setClipToPadding(false);
        bannersliderviewpager.setPageMargin(20);
        bannersliderviewpager.setCurrentItem(currentpage);

        ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentpage=position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE)
                {
                    pagelooper();

                }

            }

        };
        bannersliderviewpager.addOnPageChangeListener(onPageChangeListener);
        bannerslideshowstart();
        bannersliderviewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pagelooper();
                bannerslideshowstop();
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    bannerslideshowstart();
                }
                return false;
            }
        });



        ////////////////////////Banner

        //////strop ad
        stripimage=v.findViewById(R.id.stripimage);
        striplayout=v.findViewById(R.id.stripadlayout);
        stripimage.setImageResource(R.mipmap.logo);

        //////strop ad


        /////////////horizontal product layout
        hslrecyclerView=v.findViewById(R.id.horizontalscrolllayourecyclerview);
        hsltitle=v.findViewById(R.id.horizontalscrolllayouttitle);
        hslmore=v.findViewById(R.id.horizontalscrolllayoutbutton);
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
        horizontalproductscrolladapter horizontalproductscrolladapter=new horizontalproductscrolladapter(horizontalproductmodelArrayList);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        hslrecyclerView.setLayoutManager(linearLayoutManager1);
        hslrecyclerView.setAdapter(horizontalproductscrolladapter);
        horizontalproductscrolladapter.notifyDataSetChanged();
        /////////////horizontal product layout


        ///////////grid product layout
        gname=v.findViewById(R.id.gridtitle);
        gmore=v.findViewById(R.id.gridmore);
        ggrid=v.findViewById(R.id.gridgrid);
        ggrid.setAdapter(new gridproductadapter(horizontalproductmodelArrayList));
        ///////////grid product layout



        ///////// final recycler



        RecyclerView finalrecycler=v.findViewById(R.id.finalrecycler);
        LinearLayoutManager finallinear=new LinearLayoutManager(getActivity());
        finallinear.setOrientation(LinearLayoutManager.VERTICAL);
        finalrecycler.setLayoutManager(finallinear);
        ArrayList<HomePageModel> homePageModelArrayList=new ArrayList<>();
        homePageModelArrayList.add(new HomePageModel(0,sliderModelArrayList));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));
        homePageModelArrayList.add(new HomePageModel(2,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(2,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));
        homePageModelArrayList.add(new HomePageModel(3,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(3,"DEALS OF THE DAY",horizontalproductmodelArrayList));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));
        homePageModelArrayList.add(new HomePageModel(0,sliderModelArrayList));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#ffff00"));
        homePageModelArrayList.add(new HomePageModel(0,sliderModelArrayList));
        homePageModelArrayList.add(new HomePageModel(1,R.mipmap.logo,"#000000"));
        homepageadapter adapter=new homepageadapter(homePageModelArrayList);
        finalrecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        ///////// final recycler
        return v;
    }
    ////////////////////////Banner
    private void pagelooper()
    {
        if(currentpage==sliderModelArrayList.size()-2)
        {
            currentpage=2;
            bannersliderviewpager.setCurrentItem(currentpage,false);
        }
        if(currentpage==1)
        {
            currentpage=sliderModelArrayList.size()-3;
            bannersliderviewpager.setCurrentItem(currentpage,false);
        }

    }
    private void bannerslideshowstart()
    {
        final Handler handler=new Handler();
        final Runnable update=new Runnable() {
            @Override
            public void run() {
                if(currentpage>=sliderModelArrayList.size())
                {
                    currentpage=1;
                }
                bannersliderviewpager.setCurrentItem(currentpage++,true);
            }
        };
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);

    }
    private void bannerslideshowstop()
    {
        timer.cancel();

    }
    ////////////////////////Banner

}