package com.example.mymart;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class homepageadapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelArrayList;
RecyclerView.RecycledViewPool recycledViewPool;
    private int lastPos=-1;

    public homepageadapter(List<HomePageModel> homePageModelArrayList) {
        this.homePageModelArrayList = homePageModelArrayList;
        recycledViewPool=new RecyclerView.RecycledViewPool();
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
        String res=homePageModelArrayList.get(position).getRes();

        String color=homePageModelArrayList.get(position).getColor();
        Log.d("color",color);
        ((StripAdViewHolder)holder).setstrpad(res,color);
        break;
    case HomePageModel.horizontaproduct:
        List<Wishist_model> wishist_models=homePageModelArrayList.get(position).getViewallmylist();
        String  coloor=homePageModelArrayList.get(position).getColor();
        String title=homePageModelArrayList.get(position).getHptitle();
        ArrayList<horizontalproductmodel> horizontalproductmodelArrayList=homePageModelArrayList.get(position)
                .getHorizontalproductmodelArrayList();
        ((HorizontalViewHolder)holder).sethorizontalproduct(horizontalproductmodelArrayList,title,coloor,wishist_models);
        break;
    case HomePageModel.gridaproduct:
        String colooor=homePageModelArrayList.get(position).getColor();
        String gtitle=homePageModelArrayList.get(position).getHptitle();
        ArrayList<horizontalproductmodel> ghorizontalproductmodelArrayList=homePageModelArrayList.get(position)
                .getHorizontalproductmodelArrayList();
        ((GridViewHolder)holder).setgridlayout(ghorizontalproductmodelArrayList,gtitle,colooor);
        break;

    default:
return;

}

///animation
        if(lastPos<position)
        {
            lastPos=position;
            Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext()
                    ,R.anim.fade_n);
            holder.itemView.setAnimation(animation);
        }

///animation
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {
        int currentpage;
        Timer timer;
        ViewPager bannersliderviewpager;
        long DELAY_TIME=3000;
        long PERIOD_TIME=3000;
        ArrayList<sliderModel> arrangedList;
        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannersliderviewpager=itemView.findViewById(R.id.banner_slider_viewpager);

        }
        private void setBannersliderviewpager(final ArrayList<sliderModel> sliderModelArrayList)
        {
            currentpage=2;
            if(timer!=null)
                timer.cancel();
arrangedList=new ArrayList<>();
for(int i=0;i<sliderModelArrayList.size();i++){
    arrangedList.add(i,sliderModelArrayList.get(i));
}
arrangedList.add(0,sliderModelArrayList.get(sliderModelArrayList.size()-2));
arrangedList.add(0,sliderModelArrayList.get(sliderModelArrayList.size()-1));
arrangedList.add(0,sliderModelArrayList.get(0));
arrangedList.add(0,sliderModelArrayList.get(1));

            SliderAdapter sliderAdapter=new SliderAdapter(arrangedList);
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
                        pagelooper(arrangedList);

                    }

                }

            };
            bannersliderviewpager.addOnPageChangeListener(onPageChangeListener);
            bannerslideshowstart(arrangedList);
            bannersliderviewpager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pagelooper(arrangedList);
                    bannerslideshowstop();
                    if(event.getAction()==MotionEvent.ACTION_UP)
                    {
                        bannerslideshowstart(arrangedList);
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
        public void setstrpad(String res,String color)
        {
           // stripimage.setImageResource(res);
            Glide.with(itemView.getContext()).load(res).apply(new RequestOptions().placeholder(
                    R.drawable.bplaceholder)).into(stripimage);
            stripimage.setBackgroundColor(Color.parseColor(color));
        }
    }
    public class HorizontalViewHolder extends  RecyclerView.ViewHolder {
        TextView hsltitle;
        Button hslmore;
        RecyclerView hslrecyclerView;
        LinearLayout containerr;


        public HorizontalViewHolder(@NonNull View v) {
            super(v);
            containerr=v.findViewById(R.id.containerr);
            hslrecyclerView=v.findViewById(R.id.horizontalscrolllayourecyclerview);
            hslrecyclerView.setRecycledViewPool(recycledViewPool);
            hsltitle=v.findViewById(R.id.horizontalscrolllayouttitle);
            hslmore=v.findViewById(R.id.horizontalscrolllayoutbutton);
        }
        private void sethorizontalproduct(ArrayList<horizontalproductmodel> horizontalproductmodelArrayList,
                                          final String title, String parsecolor, final List<Wishist_model> viewallproductList)
        {
            containerr.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(parsecolor)));
            hsltitle.setText(title);
            if(horizontalproductmodelArrayList.size()>8)
            {    hslmore.setVisibility(View.VISIBLE);
            hslmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewAllActivity.wishist_modelList=viewallproductList;
                    Intent i=new Intent(itemView.getContext(),ViewAllActivity.class);
                    i.putExtra("layout_code",0);
                    i.putExtra("title",title);
                    itemView.getContext().startActivity(i);
                }
            });}
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
        GridLayout ggrid;
        ConstraintLayout containerw;

        public GridViewHolder(@NonNull View v) {
            super(v);

            containerw=v.findViewById(R.id.containerw);
            gname=v.findViewById(R.id.gridtitle);
            gmore=v.findViewById(R.id.gridmore);
            ggrid=v.findViewById(R.id.gridgrid);
        }
        private void setgridlayout(final ArrayList<horizontalproductmodel> horizontalproductmodelArrayList, final String title, String color)
        {
            containerw.setBackgroundColor(Color.parseColor(color));
            gname.setText(title);
           // ggrid.setAdapter(new gridproductadapter(horizontalproductmodelArrayList));
            for(int i=0;i<4;i++)
            {
                ImageView productImage=ggrid.getChildAt(i).findViewById(R.id.hsproductimage);
                TextView productTitle=ggrid.getChildAt(i).findViewById(R.id.hsproducttitle);
                TextView productDesc=ggrid.getChildAt(i).findViewById(R.id.hsproductdesc);
                TextView productPrice=ggrid.getChildAt(i).findViewById(R.id.hsproductprice);


          //      productImage.setImageResource(horizontalproductmodelArrayList.get(i).getPimage());
                Glide.with(itemView.getContext()).load(horizontalproductmodelArrayList.get(i).getPimage()).
                        apply(new RequestOptions().placeholder(R.drawable.placeholder
                )).into(productImage);

                productTitle.setText(horizontalproductmodelArrayList.get(i).getPname());
                productDesc.setText(horizontalproductmodelArrayList.get(i).getPdesc());
                productPrice.setText("RS: "+horizontalproductmodelArrayList.get(i).getPprice()+"/-");

                ggrid.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
                if(!title.equals(""))
                {
                    final int finalI = i;
                    ggrid.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent ii=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                        ii.putExtra("PRODUCT_ID",
                                horizontalproductmodelArrayList.get(finalI).getProduct_ID());
                            itemView.getContext().startActivity(ii);
                        }
                    });
                }


            }


           // ggrid.setAdapter(new gridproductadapter(horizontalproductmodelArrayList));


            gmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!title.equals(""))
                    {
                        ViewAllActivity.horizontalproductmodelArrayList=horizontalproductmodelArrayList;
                        Intent i=new Intent(itemView.getContext(),ViewAllActivity.class);
                        i.putExtra("layout_code",1);
                        i.putExtra("title",title);
                        itemView.getContext().startActivity(i);
                    }

                }
            });
        }
    }
}
