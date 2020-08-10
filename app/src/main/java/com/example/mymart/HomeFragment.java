package com.example.mymart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.mymart.DBQuiries.cList;
import static com.example.mymart.DBQuiries.cListName;
import static com.example.mymart.DBQuiries.categoryModels;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
RecyclerView recyclerView;
    categoryAdapter categoryAdapter;
    RecyclerView finalrecycler;



    FirebaseFirestore firebaseFirestore;
    ///////////grid product layout

    ///////////grid product layout
    ImageView Noconnection;

    homepageadapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home2, container, false);



        Noconnection=v.findViewById(R.id.noconnection);
        ConnectivityManager cm=(ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf=cm.getActiveNetworkInfo();
        if(nf!=null && nf.isConnected()==true)
        {
            Noconnection.setVisibility(View.GONE);


            recyclerView=v.findViewById(R.id.categoryrecyclerview);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);


            categoryAdapter=new categoryAdapter(categoryModels);
            recyclerView.setAdapter(categoryAdapter);

            if(categoryModels.size()==0)
            {
                DBQuiries.setCatergories(categoryAdapter,getContext());
            }
            else
            {
                categoryAdapter.notifyDataSetChanged();

            }

            finalrecycler=v.findViewById(R.id.finalrecycler);
            LinearLayoutManager finallinear=new LinearLayoutManager(getActivity());
            finallinear.setOrientation(LinearLayoutManager.VERTICAL);
            finalrecycler.setLayoutManager(finallinear);




            if(cList.size()==0)
            { cList.add(new ArrayList<HomePageModel>());
                cListName.add("HOME");
                adapter=new homepageadapter(cList.get(0));

                DBQuiries.setFragmentdata(adapter,getContext(),0,"Home");
            }
            else
            { adapter=new homepageadapter(cList.get(0));
                adapter.notifyDataSetChanged();

            }
            finalrecycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();



        }
        else
        {
            Glide.with(this).load(R.drawable.noconnection).into(Noconnection);
            Noconnection.setVisibility(View.VISIBLE);
        }




        ///////// final recycler
        return v;
    }


}