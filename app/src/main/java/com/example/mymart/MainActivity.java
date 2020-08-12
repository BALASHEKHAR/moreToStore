package com.example.mymart;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.mymart.RegisterActivity.signupfrag;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    public static final  int HOME_FRAG=0;
    public static final  int CART_FRAG=1;
    public static final  int ORDER_FRAG=2;
    public static final  int WISHLIS_FRAG=3;
    public static final  int REWARDS_FRAG=4;
    public static final  int ACCOUNT_FRAG=5;
    public  static  DrawerLayout drawer;

    FirebaseUser currentUser;


    public  static boolean showcart=false;

    Window window;
    Toolbar toolbar;
     Dialog signinDialog;

    ImageView action_bar_logo;
FrameLayout defaultframe;
private  int currentfrag=-1;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
        if(showcart)
        {
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
    protected void onStart() {
        super.onStart();
        currentUser= FirebaseAuth.getInstance().getCurrentUser();


        if(currentUser==null)
        {
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1
            ).setEnabled(false);
        }
        else
        {
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1
            ).setEnabled(true);
        }


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
     }   return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mainsearchicon)
        {
            //TODO:
            return true;

        }
        else if(item.getItemId()==R.id.mainnotificationitem)
        {
            //TODO:
            return true;
        }
        else  if(item.getItemId()==R.id.maincart)
        {

            if(currentUser==null)
            signinDialog.show();
            else
           gotoFragment("My Cart",new MyCartFragment(),CART_FRAG);
            return true;
        }
        else if(item.getItemId()==android.R.id.home)
        {
            if(showcart)
            {
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



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);

        if(currentUser!=null)
        {
            drawer.closeDrawer(GravityCompat.START);
            int id=item.getItemId();
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
                FirebaseAuth.getInstance().signOut();
                DBQuiries.clearData();
                Intent i=new Intent(MainActivity.this,
                        RegisterActivity.class);
                startActivity(i);
                finish();

            }

            return true;
        }
        else
        {
            drawer.closeDrawer(GravityCompat.START);
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