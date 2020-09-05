package com.example.moreToStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.moreToStore.MainActivity.showcart;
import static com.example.moreToStore.RegisterActivity.signupfrag;
import static com.example.moreToStore.cart_item_mode.CART_ITEM;

public class ProductDetailsActivity extends AppCompatActivity {
    ViewPager productviewpage;
    TabLayout producttablayout;
    public static FloatingActionButton favbutton;
    ViewPager productDetailsViewpager;
    TabLayout productDetailsTabLayout;
    TextView producttitle;
    LinearLayout add_to_cart_btn;
    LinearLayout cupon_redeem_layout;
    TextView totalratingsminiview;
    TextView productprice;
    TextView badgeCount;
    TextView cuttedprice;
    public static MenuItem cartitem;
    TextView totaol_ratings;
    TextView rewardtitle;
    TextView rewarddesc;
    TextView avg_rating;
    TextView productbodyonly;
    TextView average_rating_miniview;
    TextView cod_indicator_textview;
    TextView total_rating_figure;
    ConstraintLayout rewardlayout;
    ImageView cod_indicator_imageview;
    Dialog signinDialog;
    FirebaseUser currentUser;
    String product_desc;
    String product_other_details;
    public static int initail_rating;
    ConstraintLayout product_datails_only, product_datails_tabs_container;
    static LinearLayout cclinearLayout;
    static LinearLayout ratings_progressbar_container;
    public static TextView cuoprntitle;
    public static TextView cuoprnexpdate;
    public static TextView cuoprnBody;
    static RecyclerView ccrecyclerView;
    Dialog LoadingDialog;
    DocumentSnapshot ds;
    public static boolean edit_to_fav = false;
    public static boolean already_added_cart = false;
    public static LinearLayout ratenowlayout;
    LinearLayout rating_numbers_container;
    Button buynow;
    public static String productid;
    Button cuponreddem;
    List<ProductSpecifiModel> productSpecifiModelList = new ArrayList<>();
    FirebaseFirestore fb;
    public static boolean running_whistlist_query = false;
    public static boolean running_cart_query = false;
    public static boolean running_rating_query = false;
    public static Activity productdetailsactivity;


    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            rewardlayout.setVisibility(View.GONE);
            cupon_redeem_layout.setVisibility(View.GONE);


        } else {
            favbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#6E6767")));

            rewardlayout.setVisibility(View.VISIBLE);
            cupon_redeem_layout.setVisibility(View.VISIBLE);
        }


        if (currentUser != null) {
            if (DBQuiries.myRating.size() == 0) {
                DBQuiries.LoadRating(ProductDetailsActivity.this);
            }

            //  } else LoadingDialog.dismiss();

            if (DBQuiries.wishList.size() == 0) {
                DBQuiries.loadWhishList(ProductDetailsActivity.this, LoadingDialog, false);
            } else LoadingDialog.dismiss();

        } else LoadingDialog.dismiss();

        if (DBQuiries.myRatedId.contains(productid)) {
            int ind = DBQuiries.myRatedId.indexOf(productid);
            //      setRating(Integer.parseInt(String.valueOf((long)DBQuiries.)));
            initail_rating = Integer.parseInt(String.valueOf((long) DBQuiries.myRating.get(ind))) - 1;
            setRating(initail_rating);
        }

        if (DBQuiries.cartList.contains(productid)) {
            //    favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            already_added_cart = true
            ;
        } else already_added_cart = false;
        if (DBQuiries.wishList.contains(productid)) {
            favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            edit_to_fav = true
            ;
        } else {
            edit_to_fav = false;
            favbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#6E6767")));
        }
        invalidateOptionsMenu();
        //    currentUser= FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initail_rating = -1;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        productid = getIntent().getStringExtra("PRODUCT_ID");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productviewpage = findViewById(R.id.product_image_viewpager);
        producttablayout = findViewById(R.id.image_tab_layout);
        favbutton = findViewById(R.id.add_to_fav_button);
        cuponreddem = findViewById(R.id.redeembtn);
        producttitle = findViewById(R.id.producttitle);
        cupon_redeem_layout = findViewById(R.id.cupon_redeem_layout);
        rewardlayout = findViewById(R.id.rewardlayout);
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
        add_to_cart_btn = findViewById(R.id.add_to_cart_btn);

        ///loading dialog
        LoadingDialog = new Dialog(ProductDetailsActivity.this);
        LoadingDialog.setContentView(R.layout.loading_progress_dialog);
        LoadingDialog.setCancelable(false);
        LoadingDialog.getWindow().
                setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        LoadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.sliderbackground));
        LoadingDialog.show();

        ///loading dialog
        //firestore

        final ArrayList<String> productimages = new ArrayList<>();
        fb = FirebaseFirestore.getInstance();
        fb.collection("PRODUCTS").document(
                productid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    ds = task.getResult();
                    //    Log.d("aaa", "A" + (String) ds.getId() + "A");
                    for (long x = 1; x < (long) ds.get("no_of_product_images") + 1; x++) {
                        productimages.add((String) ds.get("product_image_" + x));
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productimages);
                    productviewpage.setAdapter(productImagesAdapter);
                    producttitle.setText((String) ds.get("product_title"));
                    average_rating_miniview.setText(String.valueOf(
                            (String) ds.get("average_rating")));
                    totalratingsminiview.setText("( " + (long) ds.get("total_ratings") + " )");
                    //    Log.d("aaa",(String) ds.get("product_price"));
                    productprice.setText((String) ds.get("product_price"));
                    cuttedprice.setText((String) ds.get("cutted_price"));
                    if ((boolean) (ds.get("COD"))) {
                        cod_indicator_imageview.setVisibility(View.VISIBLE);
                        cod_indicator_textview.setVisibility(View.VISIBLE);

                    } else {
                        cod_indicator_imageview.setVisibility(View.INVISIBLE);
                        cod_indicator_textview.setVisibility(View.INVISIBLE);
                    }
                    rewardtitle.setText((long) ds.get("free_coupens") + "-" +
                            (String) ds.get("free_coupen_title") + " available");
                    rewarddesc.setText((String) ds.get("free_coupen_body"));

                    if ((boolean) ds.get("use_tab_layout")) {
                        product_datails_tabs_container.setVisibility(View.VISIBLE);
                        product_datails_only.setVisibility(View.GONE);

                        product_desc = (String) ds.get("product_description");
                        product_other_details = (String) ds.get("product_other_details");

                        for (long x = 1; x < (long) ds.get("total_spec_titles") + 1; x++) {
                            productSpecifiModelList.add(
                                    new ProductSpecifiModel(0, (String) ds.get("spec_title_" + x)));
                            for (long y = 1; y < (long) ds.get("spec_title_" + x + "_total_fields") + 1; y++) {
                                productSpecifiModelList.add(
                                        new ProductSpecifiModel(1, (String) ds.get("spec_title_" + x + "_field_" + y + "_name"),
                                                (String) ds.get("spec_title_" + x + "_field_" + y + "_value")));
                            }
                        }


                    } else {
                        productbodyonly.setText((String) ds.get("product_description"));
                        product_datails_tabs_container.setVisibility(View.GONE);
                        product_datails_only.setVisibility(View.VISIBLE);
                    }

                    totaol_ratings.setText(String.
                            valueOf((long) ds.get("total_ratings")) + " Ratings");
                    avg_rating.setText(
                            (String) (ds.get("average_rating")));
                    for (int x = 0; x < 5; x++) {
                        TextView textView = (TextView) rating_numbers_container.getChildAt(x);
                        textView.setText(String.valueOf((long) ds.get((6 - (x + 1)) + "_star")));
                        ProgressBar progressBar = (ProgressBar) ratings_progressbar_container.getChildAt(x);
                        progressBar.setMax((int) ((long) ds.get("total_ratings")));
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long) ds.get((6 - x - 1) + "_star"))));

                    }

                    Log.d("bbbb", product_desc);
                    total_rating_figure.setText(String.valueOf((long) ds.get("total_ratings")));
                    productDetailsViewpager.setAdapter(new
                            ProductDtailsViewPager(getSupportFragmentManager(),
                            productDetailsTabLayout.getTabCount(), product_desc,
                            product_other_details, productSpecifiModelList));

                    ///whistList
                    if (currentUser != null) {
                        if (DBQuiries.myRating.size() == 0) {
                            DBQuiries.LoadRating(ProductDetailsActivity.this);
                        }
                        if (DBQuiries.cartList.size() == 0) {
                            DBQuiries.loadCartList(
                                    ProductDetailsActivity.this,
                                    LoadingDialog, false
                                    , badgeCount, new TextView(ProductDetailsActivity.this));
                        } //else LoadingDialog.dismiss();

                        if (DBQuiries.wishList.size() == 0) {
                            DBQuiries.loadWhishList(ProductDetailsActivity.this, LoadingDialog, false);

                        } else LoadingDialog.dismiss();

                    } else LoadingDialog.dismiss();

                    if (DBQuiries.cartList.contains(productid)) {
                        //    favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                        already_added_cart = true
                        ;
                    } else already_added_cart = false;
                    if (DBQuiries.wishList.contains(productid)) {
                        favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                        edit_to_fav = true
                        ;
                    } else edit_to_fav = false;


                    ///whistList

                    if ((boolean) ds.get("in_stock")) {
                        add_to_cart_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (currentUser == null) {
                                    signinDialog.show();

                                } else {

                                    //    favbutton.setEnabled(false);
                                    if (!running_cart_query) {
                                        running_cart_query = true;
                                        if (already_added_cart) {
                                            running_cart_query = false;
                                            Toast.makeText(ProductDetailsActivity.this,
                                                    "Already added to cart", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Map<String, Object> addproId = new HashMap<>();
                                            addproId.put("product_ID_" + String.valueOf((long)
                                                    DBQuiries.cartList.size()), productid);
                                            addproId.put("list_size", (long) DBQuiries.cartList.size() + 1);


                                            fb.collection("USERS")
                                                    .document(currentUser.getUid())
                                                    .collection("USER_DATA")
                                                    .document("MY_CART")
                                                    .update(addproId).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {


                                                    if (task.isSuccessful()) {
                                                        if (DBQuiries.cart_item_modelList.size() != 0) {
                                                            DBQuiries.cart_item_modelList.add(0, new cart_item_mode(
                                                                    CART_ITEM,
                                                                    productid,
                                                                    (String) ds.get("product_image_1"),
                                                                    (String) ds.get("product_title"),
                                                                    (long) ds.get("free_coupens"),
                                                                    (String) ds.get("product_price"),
                                                                    (String) ds.get("cutted_price"),
                                                                    (long) 1, (long) 0,
                                                                    (long) 123, (boolean) ds.get("in_stock")
                                                            ));
                                                        }

                                                        //  favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                                                        already_added_cart = true;
                                                        DBQuiries.cartList.add(productid);
                                                        Toast.makeText(ProductDetailsActivity.this,
                                                                "Added to Cart"
                                                                , Toast.LENGTH_LONG)
                                                                .show();
                                                        invalidateOptionsMenu();
                                                        running_cart_query = false;
                                                    } else {
                                                        running_cart_query = false;
                                                        //    favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));


                                                        Toast.makeText(ProductDetailsActivity.this,
                                                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                    //    favbutton.setEnabled(true);

                                                }
                                            });


                                        }
                                    }


                                }


                            }
                        });


                    } else {
                        buynow.setVisibility(View.GONE);
                        TextView outofstock = (TextView) add_to_cart_btn.getChildAt(0);
                        outofstock.setText("Out of stock");
                        outofstock.setTextColor(getResources().getColor(R.color.colorPrimary));
                        outofstock.setCompoundDrawables(null, null, null, null);


                    }

                } else {
                    LoadingDialog.dismiss();
                    Toast.makeText(
                            ProductDetailsActivity.this,
                            task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
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


        producttablayout.setupWithViewPager(productviewpage, true);
        favbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signinDialog.show();

                } else {

                    //    favbutton.setEnabled(false);
                    if (!running_whistlist_query) {
                        running_whistlist_query = true;
                        if (edit_to_fav) {
                            int indes = DBQuiries.wishList.indexOf(productid);
                            DBQuiries.removeWhistList(indes,
                                    ProductDetailsActivity.this);

                            edit_to_fav = false;
                            favbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#6E6767")));
                        } else {
                            Map<String, Object> proId = new HashMap<>();
                            proId.put("product_ID_" + String.valueOf((long)
                                    DBQuiries.wishList.size()), productid);

                            proId.put("list_size", (long) DBQuiries.wishList.size() + 1);

                            fb.collection("USERS")
                                    .document(currentUser.getUid())
                                    .collection("USER_DATA")
                                    .document("MY_WISHLIST")
                                    .update(proId).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {


                                        if (DBQuiries.wishist_modelList.size() != 0) {
                                            DBQuiries.wishist_modelList.add(new Wishist_model(
                                                    productid,
                                                    (String) ds.get("product_image_1"),
                                                    (String) ds.get("product_full_title"),
                                                    (long) ds.get("free_coupens"),
                                                    (String) ds.get("average_rating"),
                                                    (long) ds.get("total_ratings"),
                                                    (String) ds.get("product_price"),
                                                    (String) ds.get("cutted_price"),
                                                    (boolean) ds.get("COD"),
                                                    (boolean) ds.get("in_stock")));


                                        }
                                        edit_to_fav = true;
                                        favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                                        DBQuiries.wishList.add(productid);
                                        Toast.makeText(ProductDetailsActivity.this,
                                                "Added to Favorites"
                                                , Toast.LENGTH_LONG)
                                                .show();
                                    } else {
                                        favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));


                                        // favbutton.setEnabled(true);
                                        Toast.makeText(ProductDetailsActivity.this,
                                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    running_whistlist_query = false;
                                }
                            });


                        }
                    }
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
                    if (currentUser == null) {
                        signinDialog.show();
                    } else {
                        if (star_pos != initail_rating) {


                            if (!running_rating_query) {

                                running_rating_query = true;

                                setRating(star_pos);
                                Map<String, Object> updateRating = new HashMap<>();
                                if (DBQuiries.myRatedId.contains(productid)) {


                                    TextView oldRating = (TextView) rating_numbers_container.
                                            getChildAt(5 - initail_rating - 1);
                                    TextView finalRating = (TextView) rating_numbers_container.
                                            getChildAt(5 - star_pos - 1);


                                    updateRating.put(initail_rating + 1 + "_star",
                                            Long.parseLong(oldRating.getText().toString()));
                                    updateRating.put(star_pos + 1 + "_star",
                                            Long.parseLong(finalRating.getText().toString()) + 1);
                                    updateRating.put("average_rating",
                                            (calculateAverageRating((long) star_pos - initail_rating, true)));

                                    updateRating.put("total_ratings", (long)
                                            ds.get("total_ratings") + 1);


                                } else {


                                    //      Map<String, Object> productRating = new HashMap<>();
                                    updateRating.put(star_pos + 1 + "_star",
                                            (long) ds.get(star_pos + 1 + "_star") + 1);
                                    updateRating.put("average_rating",
                                            (calculateAverageRating(star_pos + 1, false))
                                    );
                                    updateRating.put("total_ratings", (long)
                                            ds.get("total_ratings") + 1);
                                }

                                fb.collection("PRODUCTS")
                                        .document(productid)
                                        .update(updateRating)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Map<String, Object> myRating = new HashMap<>();

                                                    if (DBQuiries.myRatedId.contains(productid)) {
                                                        myRating.put("rating_" + DBQuiries.myRatedId.indexOf(productid),
                                                                (long) star_pos + 1);

                                                    } else {
                                                        //     final Map<String, Object> rating = new HashMap<>();
                                                        myRating.put("list_size",
                                                                (long) DBQuiries.myRatedId.size() + 1);

                                                        myRating.put("product_ID_" + DBQuiries.myRatedId.size()
                                                                , productid);
                                                        myRating.put("rating_" + DBQuiries.myRatedId.size(),
                                                                (long) star_pos + 1);


                                                    }


                                                    fb.collection("USERS")
                                                            .document(currentUser.getUid())
                                                            .collection("USER_DATA")
                                                            .document("MY_RATINGS")
                                                            .update(myRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {


                                                                if (DBQuiries.myRatedId.contains(productid)) {
                                                                    DBQuiries.myRating.set(DBQuiries.myRatedId.indexOf(productid), (long) star_pos + 1);


                                                                    TextView oldRating = (TextView) rating_numbers_container.
                                                                            getChildAt(5 - initail_rating - 1);
                                                                    TextView finalRating = (TextView) rating_numbers_container.
                                                                            getChildAt(5 - star_pos - 1);

                                                                    //   int ss =;
                                                                    //     Log.d("OO",String.valueOf( Integer.parseInt(oldRating.getText().toString()) - 1));
                                                                    oldRating.setText(String.valueOf(Integer.parseInt(oldRating.getText().toString()) - 1));
                                                                    finalRating.setText(String.valueOf(Integer.parseInt(finalRating.getText().toString()) + 1));


                                                                } else {


                                                                    DBQuiries.myRatedId.add(productid);
                                                                    DBQuiries.myRating.add((long) star_pos + 1);

                                                                    TextView textView = (TextView) rating_numbers_container.
                                                                            getChildAt(5 - star_pos - 1);

                                                                    int ss = Integer.parseInt(textView.getText().toString()) + 1;
                                                                    textView.setText(String.valueOf(
                                                                            ss));


                                                                    avg_rating.setText((calculateAverageRating(star_pos + 1, false)));
                                                                    average_rating_miniview.setText((calculateAverageRating(star_pos + 1, false)));


                                                                    totalratingsminiview.setText("( " + ((long) ds.get("total_ratings") + 1) + " ) Ratings");

                                                                    totaol_ratings.setText(
                                                                            String.valueOf
                                                                                    (((long) ds.get(
                                                                                            "total_ratings") + 1)) + " Ratings");


                                                                    total_rating_figure.setText(String.valueOf((long) ds.get("total_ratings") + 1));


                                                                    Toast.makeText(
                                                                            ProductDetailsActivity.this,
                                                                            "thank you for rating",
                                                                            Toast.LENGTH_LONG
                                                                    ).show();
                                                                }


                                                                for (int x = 0; x < 5; x++) {
                                                                    TextView trextView = (TextView) rating_numbers_container.getChildAt(x);
                                                                    trextView.setText(String.valueOf((long) ds.get((6 - (x + 1)) + "_star")));
                                                                    //    Log.d("OO", String.valueOf((long) ds.get((6 - (x + 1)) + "_star")));
                                                                    ProgressBar progressBar =
                                                                            (ProgressBar) ratings_progressbar_container.getChildAt(x);
                                                                    int maxProgress = Integer.parseInt(total_rating_figure.getText().toString());
                                                                    progressBar.setMax(maxProgress);


                                                                    progressBar.setProgress(Integer.parseInt(
                                                                            trextView.getText().toString()));


                                                                }
                                                                initail_rating = star_pos;
                                                                avg_rating.setText((calculateAverageRating(0, true)));
                                                                average_rating_miniview.setText(
                                                                        (calculateAverageRating(0, true)));

                                                                if (DBQuiries.wishList.contains(productid) && DBQuiries.wishist_modelList.size() != 0) {
                                                                    int ind = DBQuiries.wishList.indexOf(productid);
                                                                    DBQuiries.
                                                                            wishist_modelList.get(ind).setRating(avg_rating.getText().toString());
                                                                    DBQuiries.
                                                                            wishist_modelList.get(ind).setTotalRating(Long.parseLong(total_rating_figure.getText().toString()));

                                                                }
                                                                running_rating_query = false;
                                                            } else {
                                                                running_rating_query = false;
                                                                setRating(initail_rating);
                                                                Toast.makeText(
                                                                        ProductDetailsActivity.this,
                                                                        "PPPPPPPP" + task.getException().getMessage(),
                                                                        Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    running_rating_query = false;
                                                    setRating(initail_rating);
                                                    Toast.makeText(
                                                            ProductDetailsActivity.this,
                                                            "IIIIII" + task.getException().getMessage(),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                            }

                        }
                    }
                }
            });

        }
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentUser == null) {
                    signinDialog.show();
                } else {
                    DeliveryActivity.fromcart=false;
                    LoadingDialog.show();
                    productdetailsactivity=ProductDetailsActivity.this;

                    DeliveryActivity.cart_item_modelList = new ArrayList<>();
                    DeliveryActivity.cart_item_modelList.clear();
                    DeliveryActivity.cart_item_modelList.add(new cart_item_mode(
                            CART_ITEM,
                            productid,
                            (String) ds.get("product_image_1"),
                            (String) ds.get("product_title"),
                            (long) ds.get("free_coupens"),
                            (String) ds.get("product_price"),
                            (String) ds.get("cutted_price"),
                            (long) 1, (long) 0,
                            (long) 123, (boolean) ds.get("in_stock")
                    ));
                    DeliveryActivity.cart_item_modelList.add(new cart_item_mode(cart_item_mode.TOTAL_AMOUNT));

                    if (DBQuiries.address_modelList.size() == 0)
                        DBQuiries.loadAddresses(ProductDetailsActivity.this, LoadingDialog);
                    else {
                        LoadingDialog.dismiss();
                        Intent i = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                        startActivity(i);


                    }

                }
            }
        });
        //dialog


        signinDialog = new Dialog(ProductDetailsActivity.this);
        signinDialog.setContentView(R.layout.signindialog);
        signinDialog.setCancelable(true);
        signinDialog.getWindow().setLayout(ViewGroup.
                LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button signIn = signinDialog.findViewById(R.id.SignInbtn);
        Button signout = signinDialog.findViewById(R.id.signupbtn);
        final Intent i = new Intent(ProductDetailsActivity.this, RegisterActivity.class);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment.disbale_close_button = true;
                SiginFragment.disbale_close_button = true;
                signinDialog.dismiss();
                signupfrag = false;
                startActivity(i);

            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment.disbale_close_button = true;
                SiginFragment.disbale_close_button = true;
                signinDialog.dismiss();
                signupfrag = true;
                startActivity(i);
            }
        });


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

    private String calculateAverageRating(long current_user_rating, boolean update) {
        double totatstars = current_user_rating;
        for (int x = 1; x < 6; x++) {
            TextView textView = (TextView)
                    rating_numbers_container.getChildAt(5 - x);
            totatstars += (x * Long.parseLong(textView.getText().toString()));
        }
        if (update) {
            return String.valueOf(totatstars / Long.parseLong(total_rating_figure.getText().toString())).substring(0, 3);
        } else {
            return String.valueOf(totatstars / (Long.parseLong(total_rating_figure.getText().toString()) + 1)).substring(0, 3);
        }


    }

    public static void setRating(int star_pos) {

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

        cartitem = menu.findItem(R.id.maincart);


        cartitem.setActionView(R.layout.badge_layout);
        ImageView bagI = cartitem.getActionView().findViewById(R.id.badge_icon);
        badgeCount = cartitem.getActionView().findViewById(R.id.badge_count);

        if (currentUser != null) {
            if (DBQuiries.cartList.size() == 0) {
                DBQuiries.loadCartList(ProductDetailsActivity.this,
                        LoadingDialog,
                        false, badgeCount, new TextView(ProductDetailsActivity.this));
            } //else LoadingDialog.dismiss();
            else {
                badgeCount.setVisibility(View.VISIBLE);


                if (DBQuiries.cartList.size() < 10) {
                    badgeCount.setText(String.valueOf(DBQuiries.cartList.size()));
                } else badgeCount.setText("10+");
            }
        }
        bagI.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
        cartitem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentUser == null) {
                    signinDialog.show();
                } else {


                    Intent i = new Intent(
                            ProductDetailsActivity.this,
                            MainActivity.class);
                    showcart = true;
                    startActivity(i);

                }
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mainsearchicon) {
            //TODO:
            return true;

        } else if (item.getItemId() == android.R.id.home) {
            productdetailsactivity=null;
            finish();
            return true;
        } else if (item.getItemId() == R.id.maincart) {
            if (currentUser == null) {
                signinDialog.show();
            } else {


                Intent i = new Intent(
                        ProductDetailsActivity.this,
                        MainActivity.class);
                showcart = true;
                startActivity(i);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        productdetailsactivity=null;
        super.onBackPressed();
    }
}