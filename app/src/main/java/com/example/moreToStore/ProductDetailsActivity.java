package com.example.moreToStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
    TextView cuttedprice;
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
    Dialog LoadingDialog;
    DocumentSnapshot ds;


    /////cuopens
    public static boolean edit_to_fav = false;
    //rating
 public static    LinearLayout ratenowlayout;
    LinearLayout         rating_numbers_container
    ;
    //rating
    Button buynow;
public  static  String productid;
    Button cuponreddem;
      List<ProductSpecifiModel> productSpecifiModelList=new ArrayList<>();

    ///
    FirebaseFirestore fb;
    public static boolean running_whistlist_query=false;
    public static boolean running_rating_query=false;
    @Override
    protected void onStart() {
        super.onStart();
        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null)
        {
            rewardlayout.setVisibility(View.GONE);
            cupon_redeem_layout.setVisibility(View.GONE);


        }
        else
        {           favbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#6E6767")));

            rewardlayout.setVisibility(View.VISIBLE);
            cupon_redeem_layout.setVisibility(View.VISIBLE);
        }


        if(currentUser!=null)
        {
            if(DBQuiries.myRating.size()==0)
            {
                DBQuiries.LoadRating(ProductDetailsActivity.this);
            }
            if(DBQuiries.wishList.size()==0)
            {
                DBQuiries.loadWhishList(ProductDetailsActivity.this,LoadingDialog,false);
            }
            else LoadingDialog.dismiss();

        }
        else LoadingDialog.dismiss();


        if(DBQuiries.wishList.contains(productid))
        {
            favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            edit_to_fav=true
            ;
        }else {edit_to_fav=false;
            favbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#6E6767")));
        }
    //    currentUser= FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initail_rating=-1;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        productid=getIntent().getStringExtra("PRODUCT_ID");
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
        add_to_cart_btn=findViewById(R.id.add_to_cart_btn);

        ///loading dialog
        LoadingDialog= new Dialog(ProductDetailsActivity.this);
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
                Log.d("aaa","A"+(String) ds.getId()+"A");
                    for (long x = 1; x < (long) ds.get("no_of_product_images") + 1; x++) {
                        productimages.add((String) ds.get("product_image_" + x));
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productimages);
                    productviewpage.setAdapter(productImagesAdapter);
                    producttitle.setText((String) ds.get("product_title"));
                    average_rating_miniview.setText(String.valueOf(
                            (long)ds.get("average_rating")));
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

                    totaol_ratings.setText(String.
                            valueOf((long)ds.get("total_ratings")));
                    avg_rating.setText(String.
                            valueOf((long)(ds.get("average_rating"))));
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

                    ///whistList
                    if(currentUser!=null)
                    {
                        if(DBQuiries.myRating.size()==0)
                        {
                            DBQuiries.LoadRating(ProductDetailsActivity.this);
                        }
                        if(DBQuiries.wishList.size()==0)
                        {
                            DBQuiries.loadWhishList(ProductDetailsActivity.this,LoadingDialog,false);

                        }
                        else LoadingDialog.dismiss();

                    }
                    else LoadingDialog.dismiss();


                    if(DBQuiries.wishList.contains(productid))
                    {
                        favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                        edit_to_fav=true
                                ;
   }else edit_to_fav=false;


                    ///whistList

                } else {LoadingDialog.dismiss();
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
                if(currentUser==null)
                {
                    signinDialog.show();

                }
                else {
                    //    favbutton.setEnabled(false);
                    if (!running_whistlist_query) {
                        running_whistlist_query=true;
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


                        fb.collection("USERS")
                                .document(currentUser.getUid())
                                .collection("USER_DATA")
                                .document("MY_WISHLIST")
                                .update(proId).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Map<String, Object> prooId = new HashMap<>();
                                    prooId.put("list_size", (long) DBQuiries.wishList.size() + 1);

                                    fb.collection("USERS")
                                            .document(currentUser.getUid())
                                            .collection("USER_DATA")
                                            .document("MY_WISHLIST")
                                            .update(prooId).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                                                            (boolean) ds.get("COD")));


                                                }

                                                favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                                                edit_to_fav = true;
                                                DBQuiries.wishList.add(productid);
                                                Toast.makeText(ProductDetailsActivity.this,
                                                        "Added to Favorites"
                                                        , Toast.LENGTH_LONG)
                                                        .show();
                                            } else {
                                                favbutton.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));


                                                Toast.makeText(ProductDetailsActivity.this,
                                                        task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        //    favbutton.setEnabled(true);
                                            running_whistlist_query=false;
                                        }
                                    });


                                } else {
                                    running_whistlist_query=false;
                                   // favbutton.setEnabled(true);
                                    Toast.makeText(ProductDetailsActivity.this,
                                            task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });


                    }
                }
            }}
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
                    if (currentUser == null) {
                        signinDialog.show();
                    } else {
                        if (!running_rating_query) {

                            running_rating_query=true;

                        setRating(star_pos);
                if (DBQuiries.myRatedId.contains(productid)) {

                        } else {


                            Map<String, Object> productRating = new HashMap<>();
                            productRating.put(star_pos + 1 + "_star",
                                    (long) ds.get(star_pos + 1 + "_star") + 1);
                            productRating.put("average_rating",
                                    calculateAverageRating(star_pos + 1)
                            );
                            productRating.put("total_ratings", (long)
                                    ds.get("total_ratings") + 1);


                            fb.collection("PRODUCTS")
                                    .document(productid)
                                    .update(productRating)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                final Map<String, Object> rating = new HashMap<>();
                                          rating.put("list_size",
                                                  (long)DBQuiries.myRatedId.size()+1);

                                                rating.put("product_ID_" + DBQuiries.myRatedId.size()
                                                        , productid);
                                                rating.put("rating_" + DBQuiries.myRatedId.size(),
                                                        (long) star_pos + 1);


                                                fb.collection("USERS")
                                                        .document(currentUser.getUid())
                                                        .collection("USER_DATA")
                                                        .document("MY_RATINGS")
                                                        .update(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            DBQuiries.myRatedId.add(productid);
                                                            DBQuiries.myRating.add((long)star_pos+1);

                                                            TextView textView= (TextView)
                                                                    rating_numbers_container.
                                                                            getChildAt(5-star_pos-1);

                                                            avg_rating.setText(String.valueOf(calculateAverageRating(star_pos+1)));
                                                            average_rating_miniview.setText(String.valueOf(calculateAverageRating(star_pos+1)));




                                                            totalratingsminiview.setText("( " + (long) ds.get("total_ratings") +1+ " )");
                                                            //    Log.d("aaa",(String) ds.get("product_price"));
                                                            totaol_ratings.setText(
                                                                    String.valueOf
                                                                            ((long)ds.get(
                                                                                    "total_ratings")+1));


                                                            total_rating_figure.setText(String.valueOf((long)ds.get("total_ratings")+1));



                                                            for(int x=0;x<5;x++)
                                                            {
                                                                TextView trextView= (TextView) rating_numbers_container.getChildAt(x);
                                                                trextView.setText(String.valueOf((long)ds.get((6-(x+1))+"_star")));

                                                                ProgressBar progressBar=
                                                                        (ProgressBar) ratings_progressbar_container.getChildAt(x);
                                                                progressBar.setMax((int)((long) ds.get("total_ratings"))+1);
                                                                progressBar.setProgress(Integer.parseInt(
                                                                        trextView.getText().toString()));

                                                            }

                                                            int ss=Integer.parseInt(textView.getText().toString())+1;
                                                            textView.setText(String.valueOf(
                                                                    ss));
                                                            ProgressBar progressBar=
                                                                    (ProgressBar) ratings_progressbar_container.getChildAt(5-star_pos-1);
                                                            progressBar.setMax((int)((long) ds.get("total_ratings"))+1);
                                                            progressBar.setProgress(Integer.parseInt(
                                                                    textView.getText().toString()));



                                                            Toast.makeText(
                                                                    ProductDetailsActivity.this,
                                                                    "thank you for rating",
                                                                    Toast.LENGTH_LONG
                                                            ).show();

                                                        } else {
                                                            running_rating_query=false;
                                                            setRating(initail_rating);
                                                            Toast.makeText(
                                                                    ProductDetailsActivity.this,
                                                                  "PPPPPPPP"+  task.getException().getMessage(),
                                                                    Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                running_rating_query=false;
                                                setRating(initail_rating);
                                                Toast.makeText(
                                                        ProductDetailsActivity.this,
                                                      "IIIIII"+  task.getException().getMessage(),
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
                if(currentUser==null)
                {
                    signinDialog.show();
                }
                else
                {


                Intent di = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                startActivity(di);
            } }
        });
        add_to_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser==null)
                {
                    signinDialog.show();
                }
                else
                {
                    //TODO:add to cart
                }
            }
        });
        //dialog



        signinDialog  =new Dialog(ProductDetailsActivity.this);
        signinDialog.setContentView(R.layout.signindialog);
        signinDialog.setCancelable(true);
        signinDialog.getWindow().setLayout(ViewGroup.
                LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button signIn=signinDialog.findViewById(R.id.SignInbtn);
        Button signout=signinDialog.findViewById(R.id.signupbtn);
        final Intent i=new Intent(ProductDetailsActivity.this,RegisterActivity.class);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment.disbale_close_button=true;
                SiginFragment.disbale_close_button=true;
                signinDialog.dismiss();
                signupfrag=false;
                startActivity(i);

            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment.disbale_close_button=true;
                SiginFragment.disbale_close_button=true;
                signinDialog.dismiss();
                signupfrag=true;
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
    private long  calculateAverageRating(long current_user_rating)
    {
        long totatstars=current_user_rating;
        for(int x=1;x<6;x++)
        {
          totatstars+=(x*(long)  ds.get(x+"_star"));
        }
      return   totatstars/((long)ds.get("total_ratings")+1);

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
            if(currentUser==null)
            {
                signinDialog.show();
            }
            else {


            Intent i = new Intent(
                    ProductDetailsActivity.this,
                    MainActivity.class);
            showcart = true;
            startActivity(i);
            return true;
        }   }
        return super.onOptionsItemSelected(item);

    }

}