package com.example.moreToStore;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.moreToStore.RegisterActivity.temp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SiginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SiginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SiginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SiginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SiginFragment newInstance(String param1, String param2) {
        SiginFragment fragment = new SiginFragment();
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

    EditText email,password;
    ProgressBar progressBar;
   public static boolean disbale_close_button=false;

    Button signinbutton;
    ImageButton close;

    TextView dontHaveAccount;
    FrameLayout parentframelayout;
    FirebaseAuth firebaseAuth;
    TextView forgotpassword;
    String EMAIL_PATTERN="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_sigin, container, false);
        dontHaveAccount=v.findViewById(R.id.signindonthaveaccount);
        parentframelayout=getActivity().findViewById(R.id.registerframelayout);
        email=v.findViewById(R.id.signinemail);
        password=v.findViewById(R.id.signinpassword);

        signinbutton=v.findViewById(R.id.signinbutton);
        close=v.findViewById(R.id.signinclose);
        progressBar=v.findViewById(R.id.signinprogressbar);
        firebaseAuth=FirebaseAuth.getInstance();
        forgotpassword=v.findViewById(R.id.signinforgotpassword);

        if(disbale_close_button)
        {
            close.setVisibility(View.GONE);
        }
        else
            close.setVisibility(View.VISIBLE);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignupFragment());
            }
        });
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
        password.addTextChangedListener(new TextWatcher() {
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
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
openAcctivity();
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=true;
                setFragment(new ForgotPasswordFragment());
            }
        });
    }
    private void setFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right,R.anim.slide_out_left).replace(parentframelayout.getId(),fragment).commit();


    }
    public void checkinputs()
    {
        if(!TextUtils.isEmpty(email.getText()))
        {
            if(!TextUtils.isEmpty(password.getText()))
            {
                signinbutton.setEnabled(true);
                signinbutton.setTextColor(Color.rgb(255,255,255));
            }
            else
            {
                signinbutton.setEnabled(false);
                signinbutton.setTextColor(Color.argb(55,255,255,255));
            }

        }
        else
        {
            signinbutton.setEnabled(false);
            signinbutton.setTextColor(Color.argb(55,255,255,255));
        }
    }
    public void  checkEmailAndPassword()
    {
        if(email.getText().toString().matches(EMAIL_PATTERN))
        {
            if(password.length()>0)
            {
                progressBar.setVisibility(View.VISIBLE);
                signinbutton.setEnabled(false);
                signinbutton.setTextColor(Color.argb(55,255,255,255));
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {

                                    openAcctivity();

                                }
                                else
                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signinbutton.setEnabled(true);
                                    signinbutton.setTextColor(Color.rgb(255,255,255));
                                    Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
            else
            {
                Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
        }

    }
    private void openAcctivity()
    {
        if(disbale_close_button==true)
        {
            disbale_close_button=false;

        }
        else
        {
            Intent i=new Intent(getActivity(),MainActivity.class);
            startActivity(i);
            disbale_close_button=false;

        }



    getActivity().finish();    }
}