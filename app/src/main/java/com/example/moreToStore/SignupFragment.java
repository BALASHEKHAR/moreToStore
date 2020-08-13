package com.example.moreToStore;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
    TextView alreadyhaveAccount;
    FrameLayout parentframelayout;
    EditText email,name,password,cnfpassword;
    Button signupbutton;
    ImageButton closebutton;
    ProgressBar signupprogressbar;
    FirebaseAuth firebaseAuth;
    String EMAIL_PATTERN="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    FirebaseFirestore firebaseFirestore;
    public static boolean disbale_close_button=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View v=inflater.inflate(R.layout.fragment_signup, container, false);
     alreadyhaveAccount=v.findViewById(R.id.signuphaveaccount);
     parentframelayout=getActivity().findViewById(R.id.registerframelayout);
     name=v.findViewById(R.id.signupusername);
        email=v.findViewById(R.id.signupemail);
        password=v.findViewById(R.id.signuppassword);
        cnfpassword=v.findViewById(R.id.signupconfirmpassword);
        signupbutton=v.findViewById(R.id.signupbutton);
        closebutton=v.findViewById(R.id.signupclose);
        signupprogressbar=v.findViewById(R.id.signupprogressbar);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        if(disbale_close_button)
        {
            closebutton.setVisibility(View.GONE);
        }
        else
            closebutton.setVisibility(View.VISIBLE);

     return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyhaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SiginFragment());
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
        name.addTextChangedListener(new TextWatcher() {
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
        cnfpassword.addTextChangedListener(new TextWatcher() {
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
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               checkEmailAndPassword();
            }
        });
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                main();
            }
        });
    }
    private void setFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_left,R.anim.slide_out_from_right).replace(parentframelayout.getId(),fragment).commit();


    }
    public void checkinputs(){
        if(!TextUtils.isEmpty(email.getText()))
        {
            if(!TextUtils.isEmpty(name.getText()))
            {
                    if(!TextUtils.isEmpty(password.getText()) && password.length()>=8)
                    {
                        if(!TextUtils.isEmpty(cnfpassword.getText()) && cnfpassword.length()>=8)
                        {
                            signupbutton.setEnabled(true);
                            signupbutton.setTextColor(Color.rgb(255,255,255));

                        }
                        else
                        {
                            signupbutton.setEnabled(false);
                            signupbutton.setTextColor(Color.argb(55,255,255,255));
                        }


                    }
                    else
                    {
                        signupbutton.setEnabled(false);
                        signupbutton.setTextColor(Color.argb(55,255,255,255));
                    }
            }
            else {
                signupbutton.setEnabled(false);
                signupbutton.setTextColor(Color.argb(55,255,255,255));

            }
        }
        else
        {
            signupbutton.setEnabled(false);
            signupbutton.setTextColor(Color.argb(55,255,255,255));

        }
    }
    public void  checkEmailAndPassword(){
        Drawable erroricon=getResources().getDrawable(R.drawable.erroricon);
        erroricon.setBounds(0,0,erroricon.getIntrinsicWidth(),erroricon.getIntrinsicHeight());
        if(email.getText().toString().matches(EMAIL_PATTERN))
        {
            if(password.getText().toString().equals(cnfpassword.getText().toString()))
            {
                signupprogressbar.setVisibility(View.VISIBLE);
                signupbutton.setEnabled(false);
                signupbutton.setTextColor(Color.argb(55,255,255,255));
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Map<String,Object> user=new HashMap<>();
                                        user.put("name",name.getText().toString());
                                        firebaseFirestore.collection("USERS").
                                                document(firebaseAuth.getUid())
                                                .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    CollectionReference
                                                            userDataRef=firebaseFirestore.collection
                                                            ("USERS").document(
                                                            firebaseAuth.getUid()).
                                                            collection("USER_DATA");


                                                    final List<String> documentName=new ArrayList<>();
                                                    documentName.add("MY_WISHLIST");
                                                    documentName.add("MY_RATINGS");


                                                    //maps
                                                    Map<String,Object> wishlistMap=new HashMap<>();
                                                    wishlistMap.put("list_size",(long)0);
                                                    Map<String,Object> ratingMap=new HashMap<>();
                                                    ratingMap.put("list_size",(long)0);
                                                    //maps

                                                    List<Map<String,Object>> documentFields=new ArrayList<>();
                                                    documentFields.add(wishlistMap);
                                                    documentFields.add(ratingMap);

                                                    for(int x=0;x<documentName.size();x++)
                                                    {
                                                        final int finalX = x;
                                                        userDataRef.document(documentName.get(x))
                                                                .set(documentFields.get(x))
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful())
                                                                        {
                                                                            if(finalX ==documentName.size()-1)
                                                                            main();
                                                                        }
                                                                        else
                                                                        {
                                                                            signupbutton.setEnabled(true);
                                                                            signupbutton.setTextColor(Color.rgb(255,255,255));
                                                                            signupprogressbar.setVisibility(View.INVISIBLE);
                                                                            Toast.makeText(getActivity(),
                                                                                    task.getException().getMessage(),
                                                                                    Toast.LENGTH_LONG).show();

                                                                        }
                                                                    }
                                                                });

                                                    }





    }
                                                else {

                                                    Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });

                                    }
                                    else
                                    {
                                        signupbutton.setEnabled(true);
                                        signupbutton.setTextColor(Color.rgb(255,255,255));
                                        signupprogressbar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                            }
                        });

            }
            else {
                cnfpassword.setError("password does not matched",erroricon);
            }

        }
        else
        {
            email.setError("Invalid Email Address",erroricon);

        }


    }
    private void main()
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



        getActivity().finish();
    }

}