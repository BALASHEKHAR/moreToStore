package com.example.mymart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.mymart.MainActivity.showcart;

public class ProductDetailsActivity extends AppCompatActivity {
    ViewPager productviewpage;
    TabLayout producttablayout;
    FloatingActionButton favbutton;
    ViewPager productDetailsViewpager;
    TabLayout productDetailsTabLayout;
    TextView producttitle;
    TextView totalratingsminiview;
    TextView productprice;
    TextView cuttedprice;
    TextView totaol_ratings;
    TextView rewardtitle;
    TextView rewarddesc;
    TextView avg_rating;
    TextView productbodyonly;
    TextView average_rating_miniview;
    TextView cod_indicator_textview;
    TextView total_rating_figure;
    ImageView cod_indicator_imageview;

      String product_desc;
      String product_other_details;
   //   int tab_position=-1;

    //productDesc
    ConstraintLayout product_datails_only, product_datails_tabs_container;
    //productDesc
/////cuopens
    static LinearLayout cclinearLayout;
    static LinearLayout ratings_progressbar_container;
    public static TextView cuoprntitle;
    public static TextView cuoprnexpdate;
    public static TextView cuoprnBody;
    static RecyclerView ccrecyclerView;

    /////cuopens
    boolean edit_to_fav = false;
    //rating
    LinearLayout ratenowlayout;
    LinearLayout         rating_numbers_container
    ;
    //rating
    Button buynow;

    Button cuponreddem;
      List<ProductSpecifiModel> productSpecifiModelList=new ArrayList<>();

    ///
    FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productviewpage = findViewById(R.id.product_image_viewpager);
        producttablayout = findViewById(R.id.image_tab_layout);
        favbutton = findViewById(R.id.add_to_fav_button);
        cuponreddem = findViewById(R.id.redeembtn);
        producttitle = findViewById(R.id.producttitle);
        productprice = findViewById(R.id.productprice);
        rewardtitle = findViewById(R.id.rewardtitle);
        totaol_ratings = findViewById(R.id.totaol_ratings);
        avg_rating = findViewById(R.id.avg_rating);
        rewarddesc = findViewById(R.id.rewarddesc);
        total_rating_figure = findViewById(R.id.total_rating_figure);
        rating_numbers_container = findViewById(R.id.rating_numbers_container);
        cuttedprice = findViewById(R.id.cuttedprice);
        productbodyonly = findViewById(R.id.productbodyonly);
        cod_indicator_textview = findViewById(R.id.cod_indicator_textview);
        average_rating_miniview = findViewById(R.id.productrating_miniview);
        totalratingsminiview = findViewById(R.id.totalratingsminiview);
        cod_indicator_imageview = findViewById(R.id.cod_indicator_imageview);
        product_datails_tabs_container = findViewById(R.id.product_datails_tags_container);
        product_datails_only = findViewById(R.id.productonlycontainer);
        ratings_progressbar_container = findViewById(R.id.ratings_progressbar_container);

        //firestore
        final ArrayList<String> productimages = new ArrayList<>();
        fb = FirebaseFirestore.getInstance();
        fb.collection("PRODUCTS").document(
                "uTweeCamEo6w72aM8C7j").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();
                    Log.d("aaa",(String) ds.get("product_title"));
                    for (long x = 1; x < (long) ds.get("no_of_product_images") + 1; x++) {
                        productimages.add((String) ds.get("product_image_" + x));
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productimages);
                    productviewpage.setAdapter(productImagesAdapter);
                    producttitle.setText((String) ds.get("product_title"));
                    average_rating_miniview.setText((String) ds.get("average_rating"));
                    totalratingsminiview.setText("( " + (long) ds.get("total_ratings") + " )");
                    Log.d("aaa",(String) ds.get("product_price"));
                    productprice.setText((String) ds.get("product_price"));
                    cuttedprice.setText((String) ds.get("cutted_price"));
                    if ((boolean) (ds.get("COD"))) {
                        cod_indicator_imageview.setVisibility(View.VISIBLE);
                        cod_indicator_textview.setVisibility(View.VISIBLE);

                    } else {
                        cod_indicator_imageview.setVisibility(View.INVISIBLE);
                        cod_indicator_textview.setVisibility(View.INVISIBLE);
                    }
                    rewardtitle.setText((long) ds.get("free_coupens")+"-" +
                            (String) ds.get("free_coupen_title")+" available");
                    rewarddesc.setText((String) ds.get("free_coupen_body"));

                    if ((boolean) ds.get("use_tab_layout")) {
                        product_datails_tabs_container.setVisibility(View.VISIBLE);
                        product_datails_only.setVisibility(View.GONE);

                        product_desc = (String) ds.get("product_description");
                        product_other_details=(String)ds.get("product_other_details");

                        for (long x = 1; x < (long) ds.get("total_spec_titles") + 1; x++) {
                            productSpecifiModelList.add(
                                    new ProductSpecifiModel(0, (String) ds.get("spec_title_" + x)));
                            for (long  y = 1; y < (long) ds.get("spec_title_" + x + "_total_fields") + 1; y++) {
                                productSpecifiModelList.add(
                                        new ProductSpecifiModel(1, (String) ds.get("spec_title_" + x + "_field_" + y + "_name"),
                                                (String) ds.get("spec_title_" + x + "_field_" + y + "_value")));
                            }
                        }


                    } else {
                        productbodyonly.setText((String)ds.get("product_description"));
                        product_datails_tabs_container.setVisibility(View.GONE);
                        product_datails_only.setVisibility(View.VISIBLE);
                    }

                    totaol_ratings.setText(String.valueOf((long)ds.get("total_ratings")));
                    avg_rating.setText((String)(ds.get("average_rating")));
                    for(int x=0;x<5;x++)
                    {
                        TextView textView= (TextView) rating_numbers_container.getChildAt(x);
                        textView.setText(String.valueOf((long)ds.get((6-(x+1))+"_star")));
                        ProgressBar progressBar= (ProgressBar) ratings_progressbar_container.getChildAt(x);
                        progressBar.setMax((int)((long) ds.get("total_ratings")));
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long)ds.get((6-x-1)+"_star"))));

                    }

                    Log.d("bbbb",product_desc);
                    total_rating_figure.setText(String.valueOf((long)ds.get("total_ratings")));
                    productDetailsViewpager.setAdapter(new ProductDtailsViewPager(getSupportFragmentManager(),
                            productDetailsTabLayout.getTabCount(),product_desc,product_other_details,productSpecifiModelList));


                } else {
                    Toast.makeText(ProductDetailsActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

        //firestore
        //cupon dialog
        final Dialog dialog = new Dialog(ProductDetailsActivity.this);
        dialog.setContentView(R.layout.cupons_redeem_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView ham = dialog.findViewById(R.id.toggleImg);
        ccrecyclerView = dialog.findViewById(R.id.cupoensrecyclerView);
        cclinearLayout = dialog.findViewById(R.id.selectedcuponens);

        cuoprntitle = dialog.findViewById(R.id.cupoentitle);
        cuoprnBody = dialog.findViewById(R.id.cupoenbody);
        cuoprnexpdate = dialog.findViewById(R.id.cupoenvalidity);


        TextView orinalprice = dialog.findViewById(R.id.originalprice);
        TextView dicountdiscountpriceprice = dialog.findViewById(R.id.originalprice);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ccrecyclerView.setLayoutManager(linearLayoutManager);
        List<Reward_model> reward_modelList = new ArrayList<>();
        reward_modelList.add(new Reward_model("Cashback",
                "GET 20% OFF on any product above Rs.500/ and below Rs.2500/-", "till 2nd ,June 2016"));
        reward_modelList.add(new Reward_model("Discount",
                "GET 20% OFF on any product above Rs.500/ and below Rs.2500/-", "till 2nd ,June 2016"));
        reward_modelList.add(new Reward_model("Buy 1 get 1 free"
                , "GET 20% OFF on any product above Rs.500/ and below Rs.2500/-", "till 2nd ,June 2016"));
        reward_modelList.add(new Reward_model("Cashback"
                , "GET 20% OFF on any product above Rs.500/ and below Rs.2500/-", "till 2nd ,June 2016"));
        reward_modelList.add(new Reward_model("Discount"
                , "GET 20% OFF on any product above Rs.500/ and below Rs.2500/-", "till 2nd ,June 2016"));
        reward_modelList.add(new Reward_model("Summer sale"
                , "GET 20% OFF on any product above Rs.500/ and below Rs.2500/-", "till 2nd ,June 2016"));
        reward_modelList.add(new Reward_model("Cashback"
                , "GET 20% OFF on any product above Rs.500/ and below Rs.2500/-", "till 2nd ,June 2016"));
        RewardAdapter rewardAdapter = new RewardAdapter(reward_modelList, true);
        ccrecyclerView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();
        ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglerecycler();
            }
        });


        cuponreddem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.show();
            }
        });
        //cupon dialog
        buynow = findViewById(R.id.buy_now_btn);

      /*  ArrayList<Integer> productimages=new ArrayList<>();
        productimages.add(R.drawable.ic_baseline_phone_android_24);
        productimages.add(R.drawable.ic_baseline_phone_android_24);
        productimages.add(R.drawable.ic_baseline_phone_android_24);
        productimages.add(R.drawable.ic_baseline_phone_android_24);
        productimages.add(R.drawable.ic_baseline_phone_android_24);*/


        producttablayout.setupWithViewPager(productviewpage, true);
        favbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_to_fav) {
                    edit_to_fav = false;
                    favbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#6E6767")));
                } else {
                    favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    edit_to_fav = true;
                }
            }
        });
        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);
        productDetailsViewpager = findViewById(R.id.product_details_viewpager);


        productDetailsViewpager.setOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tab_position=tab.getPosition();
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
        ratenowlayout = findViewById(R.id.rate_now_container);
        for (int x = 0; x < ratenowlayout.getChildCount(); x++) {
            final int star_pos = x;
            ratenowlayout.getChildAt(star_pos).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(star_pos);
                }
            });

        }
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent di = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                startActivity(di);
            }
        });
        //rating
    }

    public static void togglerecycler() {
        if (ccrecyclerView.getVisibility() == View.GONE) {
            ccrecyclerView.setVisibility(View.VISIBLE);
            cclinearLayout.setVisibility(View.GONE);

        } else {
            ccrecyclerView.setVisibility(View.GONE);
            cclinearLayout.setVisibility(View.VISIBLE);

        }
    }

    private void setRating(int star_pos) {
        for (int x = 0; x < ratenowlayout.getChildCount(); x++) {
            ImageView star = (ImageView) ratenowlayout.getChildAt(x);
            star.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= star_pos) {
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
        if (item.getItemId() == R.id.mainsearchicon) {
            //TODO:
            return true;

        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.maincart) {
            Intent i = new Intent(
                    ProductDetailsActivity.this,
                    MainActivity.class);
            showcart = true;
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}