package com.example.moreToStore;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.moreToStore.RegisterActivity.signupfrag;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    public static final  int HOME_FRAG=0;
    public static final  int CART_FRAG=1;
    public static final  int ORDER_FRAG=2;
    public static final  int WISHLIS_FRAG=3;

    public static final  int REWARDS_FRAG=4;
    public static final  int ACCOUNT_FRAG=5;
    public static boolean resetmain=false;
    public  static  DrawerLayout drawer;
    public  static Activity mainActivity;

    FirebaseUser currentUser;
    TextView badgeCount;

    CircleImageView profileView;
    TextView email,fullname;
    ImageView addProfileIcon;



    public  static boolean showcart=false;

    Window window;
    Toolbar toolbar;
     Dialog signinDialog;
       Dialog cancelDialog;

    ImageView action_bar_logo;
FrameLayout defaultframe;
private  int currentfrag=-1;
  public  static NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //cancel gialog
        cancelDialog= new Dialog(MainActivity.this);
        cancelDialog.setContentView(R.layout.order_cancel_dialog);
        cancelDialog.setCancelable(true);
        cancelDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // cancelDialog
        cancelDialog.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDialog.dismiss();
                FirebaseAuth.getInstance().signOut();
                DBQuiries.clearData();
                Intent i=new Intent(MainActivity.this,
                        RegisterActivity.class);
                startActivity(i);
                finish();


            }
        });
        cancelDialog.findViewById(R.id.noBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDialog.dismiss();
            }
        });



        action_bar_logo=findViewById(R.id.action_bar_logo);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

         drawer = findViewById(R.id.drawer_layout);

       navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        defaultframe=findViewById(R.id.mainframlayout);


        profileView=navigationView.getHeaderView(0).findViewById(R.id.header_image);
        fullname=navigationView.getHeaderView(0).findViewById(R.id.header_name);
        email=navigationView.getHeaderView(0).findViewById(R.id.header_email);
        addProfileIcon=navigationView.getHeaderView(0).findViewById(R.id.plusicon);



        if(showcart)
        {
            mainActivity=this;
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("My Cart", new MyCartFragment(),-2);
        }
        else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                    drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setfragment(new HomeFragment(),HOME_FRAG);
        }


        signinDialog  =new Dialog(MainActivity.this);
        signinDialog.setContentView(R.layout.signindialog);
        signinDialog.setCancelable(true);
        signinDialog.getWindow().setLayout(ViewGroup.
                LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button signIn=signinDialog.findViewById(R.id.SignInbtn);
        Button signout=signinDialog.findViewById(R.id.signupbtn);
        final Intent i=new Intent(MainActivity.this,RegisterActivity.class);
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

    @Override
    protected void onPause() {
        super.onPause();
        DBQuiries.checkNotifications(true,null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null) {
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1
            ).setEnabled(false);
        }
        else {



            if(DBQuiries.email==null) {
                FirebaseFirestore.getInstance().collection("USERS").document(currentUser.getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DBQuiries.fullname = task.getResult().getString("fullname");
                            DBQuiries.email = task.getResult().getString("email");
                            DBQuiries.profile = task.getResult().getString("profile");

                            fullname.setText(DBQuiries.fullname);
                            email.setText(DBQuiries.email);
                            if (DBQuiries.profile.equals("")) {
                                addProfileIcon.setVisibility(View.VISIBLE);
                            } else {
                                addProfileIcon.setVisibility(View.INVISIBLE);
                                Glide.with(MainActivity.this).load(DBQuiries.profile).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(profileView);

                            }
                        } else {
                            fullname.setText(DBQuiries.fullname);
                            email.setText(DBQuiries.email);
                            if (DBQuiries.profile.equals("")) {
                                profileView.setImageResource(R.drawable.user);
                                addProfileIcon.setVisibility(View.VISIBLE);
                            } else {
                                addProfileIcon.setVisibility(View.INVISIBLE);
                                Glide.with(MainActivity.this).load(DBQuiries.profile).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(profileView);

                            }
                        }
                    }
                });
            }
            else
            {

            }
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);
        }
        if(resetmain)
        {
            action_bar_logo.setVisibility(View.VISIBLE);
            resetmain=false;
            setfragment(new HomeFragment(),HOME_FRAG);
            navigationView.getMenu().getItem(0).setChecked(true);

        }

invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);

        }
        else
        {
            if(currentfrag==HOME_FRAG)
          {currentfrag=-1;
          super.onBackPressed();}
            else
            {
                if(showcart)
                {
                    mainActivity=null;
                    showcart=false;
                    finish();
                }
                else{


              //  getSupportActionBar().setDisplayShowTitleEnabled(false);
                action_bar_logo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setfragment(new HomeFragment(),HOME_FRAG);
                navigationView.getMenu().getItem(0).setChecked(true);
            }  }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(currentfrag==HOME_FRAG)
       {       getSupportActionBar().setDisplayShowTitleEnabled(true);
           getMenuInflater().inflate(R.menu.main, menu);
           MenuItem cartitem=menu.findItem(R.id.maincart);
               cartitem.setActionView(R.layout.badge_layout);
               ImageView bagI=cartitem.getActionView().findViewById(R.id.badge_icon);
               badgeCount=cartitem.getActionView().findViewById(R.id.badge_count);
               // bagC.setText(String.valueOf(DBQuiries.cartList.size()));


           bagI.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
if(currentUser!=null)
{
    if (DBQuiries.cartList.size() == 0) {
 //       badgeCount.setVisibility(View.INVISIBLE);
        DBQuiries.loadCartList(MainActivity.this,
                new Dialog(MainActivity.this),
                false,badgeCount,new TextView(MainActivity.this));
    } //else LoadingDialog.dismiss();
    else {

            badgeCount.setVisibility(View.VISIBLE);



        if(DBQuiries.cartList.size()<10)
        {
            badgeCount.setText(String.valueOf(DBQuiries.cartList.size()));
        }
        else  badgeCount.setText("10+");
    }

}
cartitem.getActionView().setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(currentUser==null)
            signinDialog.show();
        else
            gotoFragment("My Cart",new MyCartFragment(),CART_FRAG);

    }
});

     MenuItem notifytem=menu.findItem(R.id.mainnotificationitem);
           notifytem.setActionView(R.layout.badge_layout);
           ImageView notifyIcon=notifytem.getActionView().findViewById(R.id.badge_icon);
           notifyIcon.setImageResource(R.drawable.ic_baseline_notifications_24);
           TextView notifyCount=notifytem.getActionView().findViewById(R.id.badge_count);

           if(currentUser!=null)
           {
               DBQuiries.checkNotifications(false,notifyCount);
           }
           notifytem.getActionView().setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent noi=new Intent(MainActivity.this,notificationActivity.class);
                   startActivity(noi);
               }
           });

       }   return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mainsearchicon)
        {
         Intent searchI=new Intent(this,searchActivity.class);
         startActivity(searchI);
            return true;

        }
         if(item.getItemId()==R.id.mainnotificationitem)
        {
          Intent noi=new Intent(this,notificationActivity.class);
          startActivity(noi);
            return true;
        }
          if(item.getItemId()==R.id.maincart)
        {

            if(currentUser==null)
            signinDialog.show();
            else
           gotoFragment("My Cart",new MyCartFragment(),CART_FRAG);
            return true;
        }
         if(item.getItemId()==android.R.id.home)
        {
            if(showcart)
            {
                mainActivity=null;
                showcart=false;
                finish();
                return  true;
            }

        }
        return super.onOptionsItemSelected(item);

    }
    private void gotoFragment(String title,Fragment fragment,int currentfrag)
    {

        getSupportActionBar().setTitle(title);
        action_bar_logo.setVisibility(View.GONE);
        invalidateOptionsMenu();
        setfragment(fragment,currentfrag);
        if(currentfrag==CART_FRAG)
        navigationView.getMenu().getItem(3).setChecked(true);
    }


MenuItem menuItem;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        menuItem=item;

        if(currentUser!=null)
        {

            drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);


                    int id=menuItem.getItemId();
                    if(id==R.id.nav_orders)
                    {
                        gotoFragment("My Orders",new MyordersFragment(),ORDER_FRAG);
                    }
                    else if(id==R.id.nav_rewards)
                    {
                        gotoFragment("My Rewars",new MyRewardsFragment(),REWARDS_FRAG);

                    }
                    else  if(id==R.id.nav_cart)
                    {
                        gotoFragment("My Cart",new MyCartFragment(),CART_FRAG);

                    }
                    else if(id==R.id.nav_whatlist)
                    {
                        gotoFragment("My Wishlist",new WishlistFragment(),WISHLIS_FRAG);

                    }
                    else if(id==R.id.nav_account)
                    {
                        gotoFragment("My Account",new MyAccountFragment(),ACCOUNT_FRAG);

                    }
                    else if(id==R.id.nav_mall)
                    {
                        //  getSupportActionBar().setDisplayShowTitleEnabled(false);
                        action_bar_logo.setVisibility(View.VISIBLE);
                        invalidateOptionsMenu();
                        setfragment(new HomeFragment(),HOME_FRAG);
                    }
                    else if(id==R.id.nav_signout)
                    {
                        cancelDialog.show();
                    }
                    drawer.removeDrawerListener(this);

                }
            });


            return true;
        }
        else
        {

            signinDialog.show();
            return false;
        }



    }
    public void setfragment(Fragment fragment,int fragnumber)
    {
        if(fragnumber!=currentfrag)
        {
            if(fragnumber==REWARDS_FRAG)
            {
                toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));
                window.setStatusBarColor(Color.parseColor("#5B04B1"));
            }
            else
            {                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            }
        currentfrag=fragnumber;
       getSupportFragmentManager().beginTransaction().
               setCustomAnimations(R.anim.fade_n,R.anim.fade_out).replace(defaultframe.getId(),fragment).commit();
    }
    }
}