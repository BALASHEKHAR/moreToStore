package com.example.mymart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

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

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    public static final  int HOME_FRAG=0;
    public static final  int CART_FRAG=1;
    public static final  int ORDER_FRAG=2;
    public static final  int WISHLIS_FRAG=3;
    public static final  int REWARDS_FRAG=4;
    public static final  int ACCOUNT_FRAG=5;

    Window window;
    Toolbar toolbar;

    ImageView action_bar_logo;
FrameLayout defaultframe;
private static int currentfrag=-1;
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
       navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        defaultframe=findViewById(R.id.mainframlayout);
        setfragment(new HomeFragment(),HOME_FRAG);

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
            super.onBackPressed();
            else
            {
              //  getSupportActionBar().setDisplayShowTitleEnabled(false);
                action_bar_logo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setfragment(new HomeFragment(),HOME_FRAG);
                navigationView.getMenu().getItem(0).setChecked(true);
            }
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
            gotoFragment("My Cart",new MyCartFragment(),CART_FRAG);
            return true;
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

        }
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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