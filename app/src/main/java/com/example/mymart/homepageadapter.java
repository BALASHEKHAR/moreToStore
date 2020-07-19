package com.example.mymart;

import android.graphics.Color;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class homepageadapter extends RecyclerView.Adapter {
    private ArrayList<HomePageModel> homePageModelArrayList;

    public homepageadapter(ArrayList<HomePageModel> homePageModelArrayList) {
        this.homePageModelArrayList = homePageModelArrayList;
    }

    @Override
    public int getItemCount() {
        return homePageModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
      switch (homePageModelArrayList.get(position).getType())
      {
          case 0:
              return HomePageModel.banner;
          case 1:
              return HomePageModel.stripno;
          case 2:
              return HomePageModel.horizontaproduct;
          case 3:
              return HomePageModel.gridaproduct;
          default:
              return -1;

      }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     switch (viewType)
     {
         case HomePageModel.banner:
             View bsv= LayoutInflater.from(parent.getContext()).inflate(R.layout.slidingadbanner,parent,false);
             return new BannerSliderViewHolder(bsv);
         case HomePageModel.stripno:
             View sdv= LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout,parent,false);
             return new StripAdViewHolder(sdv);
         case HomePageModel.horizontaproduct:
             View hsv= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontalscroollayout,parent,false);
             return new HorizontalViewHolder(hsv);
         case HomePageModel.gridaproduct:
             View gpl= LayoutInflater.from(parent.getContext()).inflate(R.layout.gridproductlayout,parent,false);
             return new GridViewHolder(gpl);


         default:
             return null;
     }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
switch (homePageModelArrayList.get(position).getType())
{
    case HomePageModel.banner:
        ArrayList<sliderModel> sliderModelArrayList=homePageModelArrayList.get(position).getSliderModelArrayList();
        ((BannerSliderViewHolder)holder).setBannersliderviewpager(sliderModelArrayList);
        break;
    case HomePageModel.stripno:
        int res=homePageModelArrayList.get(position).getRes();
        String color=homePageModelArrayList.get(position).getColor();
        ((StripAdViewHolder)holder).setstrpad(res,color);
        break;
    case HomePageModel.horizontaproduct:
        String title=homePageModelArrayList.get(position).getHptitle();
        ArrayList<horizontalproductmodel> horizontalproductmodelArrayList=homePageModelArrayList.get(position)
                .getHorizontalproductmodelArrayList();
        ((HorizontalViewHolder)holder).sethorizontalproduct(horizontalproductmodelArrayList,title);
        break;
    case HomePageModel.gridaproduct:
        String gtitle=homePageModelArrayList.get(position).getHptitle();
        ArrayList<horizontalproductmodel> ghorizontalproductmodelArrayList=homePageModelArrayList.get(position)
                .getHorizontalproductmodelArrayList();
        ((GridViewHolder)holder).setgridlayout(ghorizontalproductmodelArrayList,gtitle);
        break;

    default:


}
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {
        int currentpage=2;
        Timer timer;
        ViewPager bannersliderviewpager;
        long DELAY_TIME=2000;
        long PERIOD_TIME=2000;
        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannersliderviewpager=itemView.findViewById(R.id.banner_slider_viewpager);

        }
        private void setBannersliderviewpager(final ArrayList<sliderModel> sliderModelArrayList)
        {



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
                        pagelooper(sliderModelArrayList);

                    }

                }

            };
            bannersliderviewpager.addOnPageChangeListener(onPageChangeListener);
            bannerslideshowstart(sliderModelArrayList);
            bannersliderviewpager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pagelooper(sliderModelArrayList);
                    bannerslideshowstop();
                    if(event.getAction()==MotionEvent.ACTION_UP)
                    {
                        bannerslideshowstart(sliderModelArrayList);
                    }
                    return false;
                }
            });
        }
        private void pagelooper(ArrayList<sliderModel> sliderModelArrayList)
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
        private void bannerslideshowstart(final ArrayList<sliderModel> sliderModelArrayList)
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
    }
    public class StripAdViewHolder extends  RecyclerView.ViewHolder{
        ImageView stripimage;
        LinearLayout striplayout;
        public StripAdViewHolder(@NonNull View v) {
            super(v);
            stripimage=v.findViewById(R.id.stripimage);
            striplayout=v.findViewById(R.id.stripadlayout);
        }
        public void setstrpad(int res,String color)
        {
            stripimage.setImageResource(res);
            stripimage.setBackgroundColor(Color.parseColor(color));
        }
    }
    public class HorizontalViewHolder extends  RecyclerView.ViewHolder {
        TextView hsltitle;
        Button hslmore;
        RecyclerView hslrecyclerView;


        public HorizontalViewHolder(@NonNull View v) {
            super(v);
            hslrecyclerView=v.findViewById(R.id.horizontalscrolllayourecyclerview);
            hsltitle=v.findViewById(R.id.horizontalscrolllayouttitle);
            hslmore=v.findViewById(R.id.horizontalscrolllayoutbutton);
        }
        private void sethorizontalproduct(ArrayList<horizontalproductmodel> horizontalproductmodelArrayList,String title)
        {
            hsltitle.setText(title);
            if(horizontalproductmodelArrayList.size()>8)
                hslmore.setVisibility(View.VISIBLE);
            else
                hslmore.setVisibility(View.INVISIBLE);


            horizontalproductscrolladapter horizontalproductscrolladapter=new horizontalproductscrolladapter(horizontalproductmodelArrayList);
            LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(itemView.getContext());
            linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            hslrecyclerView.setLayoutManager(linearLayoutManager1);
            hslrecyclerView.setAdapter(horizontalproductscrolladapter);
            horizontalproductscrolladapter.notifyDataSetChanged();
        }
    }
    public class GridViewHolder extends  RecyclerView.ViewHolder {
        TextView gname;
        Button gmore;
        GridView ggrid;


        public GridViewHolder(@NonNull View v) {
            super(v);
            gname=v.findViewById(R.id.gridtitle);
            gmore=v.findViewById(R.id.gridmore);
            ggrid=v.findViewById(R.id.gridgrid);
        }
        private void setgridlayout(ArrayList<horizontalproductmodel> horizontalproductmodelArrayList,String title)
        {
            gname.setText(title);
            ggrid.setAdapter(new gridproductadapter(horizontalproductmodelArrayList));
        }
    }
}
