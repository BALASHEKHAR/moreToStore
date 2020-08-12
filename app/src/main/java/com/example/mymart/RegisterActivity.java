package com.example.mymart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class RegisterActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    static boolean temp=false;
    public  static boolean signupfrag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        frameLayout=findViewById(R.id.registerframelayout);
        if(signupfrag==true)
        {
            signupfrag=false;
            setdefaultFragment(new SignupFragment());
        }
        else
        {
            signupfrag=true;
            setdefaultFragment(new SiginFragment());

        }

    }

    private void setdefaultFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(),fragment).commit();

    }
    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_left,R.anim.slide_out_from_right).replace(frameLayout.getId(),fragment).commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            SiginFragment.disbale_close_button=false;
            SignupFragment.disbale_close_button=false;
            if(temp)
            {
                temp=false;
                setFragment(new SiginFragment());

                return false;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}