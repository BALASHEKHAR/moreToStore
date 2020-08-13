package com.example.moreToStore;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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

    EditText email;
    Button resetbutton;
    TextView goback;
    FrameLayout parent;
    FirebaseAuth firebaseAuth;
    ViewGroup viewGroup;
    ImageView imageicon;
    TextView texticon;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View v= inflater.inflate(R.layout.fragment_forgot_password, container, false);
   email=v.findViewById(R.id.forgotemail);
     resetbutton=v.findViewById(R.id.forgotbutton);
     resetbutton.setBackgroundColor(getResources().getColor(R.color.rbuttonRed));
     goback=v.findViewById(R.id.forgotback);
     parent=getActivity().findViewById(R.id.registerframelayout);
     firebaseAuth=FirebaseAuth.getInstance();

     viewGroup=v.findViewById(R.id.forgotcontainer);
     imageicon=v.findViewById(R.id.forgotemailredicon);
     texticon=v.findViewById(R.id.forgottextmail);
     progressBar=v.findViewById(R.id.forgotredprogressbar);





     return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SiginFragment());
            }
        });
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //TransitionManager.beginDelayedTransition(viewGroup);

                TransitionManager.beginDelayedTransition(viewGroup);
                imageicon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                resetbutton.setEnabled(false);
                resetbutton.setBackgroundColor(getResources().getColor(R.color.rbuttonRed));
                firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0,imageicon.getWidth()/2,imageicon.getHeight()/2);
                                    scaleAnimation.setDuration(100);
                                    scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                    scaleAnimation.setRepeatMode(Animation.REVERSE);
                                    scaleAnimation.setRepeatCount(1);

                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener(){
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            texticon.setText("Recovery email sent successfully ! check your inbox");
                                            texticon.setTextColor(getResources().getColor(R.color.success));
                                            progressBar.setVisibility(View.INVISIBLE);
                                            TransitionManager.beginDelayedTransition(viewGroup);
                                            texticon.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                            imageicon.setImageResource(R.drawable.greenmail);

                                        }
                                    });

                                    imageicon.startAnimation(scaleAnimation);
                                    resetbutton.setBackgroundColor(getResources().getColor(R.color.buttonRed));
                                    resetbutton.setEnabled(true);

                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    TransitionManager.beginDelayedTransition(viewGroup);
                                    texticon.setText(task.getException().getMessage());
                                    texticon.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    texticon.setVisibility(View.VISIBLE);
                                    resetbutton.setEnabled(true);
                                    resetbutton.setBackgroundColor(getResources().getColor(R.color.buttonRed));
                                    Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }


                            }
                        });

            }
        });

    }

    public void checkinputs()
    {
        if(!TextUtils.isEmpty(email.getText()))
        {
            resetbutton.setEnabled(true);
            resetbutton.setBackgroundColor(getResources().getColor(R.color.buttonRed));
        }
        else
        {
            resetbutton.setBackgroundColor(getResources().getColor(R.color.rbuttonRed));
            resetbutton.setEnabled(false);
        }
    }
    private void setFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right,R.anim.slide_out_left).replace(parent.getId(),fragment).commit();


    }
}