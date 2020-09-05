package com.example.moreToStore;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.moreToStore.DBQuiries.cList;
import static com.example.moreToStore.DBQuiries.cListName;
import static com.example.moreToStore.DBQuiries.categoryModels;
import static com.example.moreToStore.MainActivity.drawer;


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
    Button refreshbtn;
    categoryAdapter categoryAdapter;
    RecyclerView finalrecycler;

    ArrayList<categoryModel> fakecategory = new ArrayList<>();

    FirebaseFirestore firebaseFirestore;
    ///////////grid product layout

    ///////////grid product layout
    ImageView Noconnection;
    public static SwipeRefreshLayout swipe_refresh;
    ConnectivityManager cm;
    NetworkInfo nf;

    homepageadapter adapter;
    List<HomePageModel> homePageModelfakeList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home2, container, false);
        recyclerView = v.findViewById(R.id.categoryrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        finalrecycler = v.findViewById(R.id.finalrecycler);
        LinearLayoutManager finallinear = new LinearLayoutManager(getActivity());
        finallinear.setOrientation(LinearLayoutManager.VERTICAL);
        finalrecycler.setLayoutManager(finallinear);
        refreshbtn = v.findViewById(R.id.refreshbtn);
/////home fake list


        ArrayList<sliderModel> sliderModelList = new ArrayList<>();
        sliderModelList.add(new sliderModel("null", "#dfdfdf"));
        sliderModelList.add(new sliderModel("null", "#dfdfdf"));
        sliderModelList.add(new sliderModel("null", "#dfdfdf"));
        sliderModelList.add(new sliderModel("null", "#dfdfdf"));
        sliderModelList.add(new sliderModel("null", "#dfdfdf"));
        sliderModelList.add(new sliderModel("null", "#dfdfdf"));
        sliderModelList.add(new sliderModel("null", "#dfdfdf"));

        ArrayList<horizontalproductmodel> horizontalproductmodelList =
                new ArrayList<>();
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));
        horizontalproductmodelList.add(new horizontalproductmodel("",
                "", "", "", ""));

        homePageModelfakeList.add(new HomePageModel(
                0, sliderModelList
        ));
        homePageModelfakeList.add(new HomePageModel(
                1, "",
                "#dfdfdf"
        ));
        homePageModelfakeList.add(new HomePageModel(
                2, "",
                "#dfdfdf", horizontalproductmodelList,
                new ArrayList<Wishist_model>()
        ));
        homePageModelfakeList.add(new HomePageModel(
                3, "",
                "#dfdfdf", horizontalproductmodelList
        ));


/////home fake list


        ///catergory fake list
        fakecategory.add(new categoryModel("",
                ""));
        fakecategory.add(new categoryModel("",
                ""));
        fakecategory.add(new categoryModel("",
                ""));
        fakecategory.add(new categoryModel("",
                ""));
        fakecategory.add(new categoryModel("",
                ""));
        fakecategory.add(new categoryModel("",
                ""));
        fakecategory.add(new categoryModel("",
                ""));
        fakecategory.add(new categoryModel("",
                ""));
        fakecategory.add(new categoryModel("",
                ""));


        ///catergory fake list

        categoryAdapter = new categoryAdapter(fakecategory);


        adapter = new homepageadapter(homePageModelfakeList);


        swipe_refresh = v.findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeColors(
                getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary),
                getContext().getResources().getColor(R.color.colorPrimary)
        );
        Noconnection = v.findViewById(R.id.noconnection);
        cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        nf = cm.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            recyclerView.setVisibility(View.VISIBLE);
            refreshbtn.setVisibility(View.GONE);
            finalrecycler.setVisibility(View.VISIBLE);
            Noconnection.setVisibility(View.GONE);
            recyclerView.setAdapter(categoryAdapter);


            if (categoryModels.size() == 0) {
                DBQuiries.setCatergories(recyclerView, getContext());
            } else {
                categoryAdapter.notifyDataSetChanged();

            }
            categoryAdapter = new categoryAdapter(categoryModels);
            recyclerView.setAdapter(categoryAdapter);


            if (cList.size() == 0) {
                cList.add(new ArrayList<HomePageModel>());
                cListName.add("HOME");


                DBQuiries.setFragmentdata(
                        finalrecycler, getContext(), 0, "Home");
            } else {
                adapter = new homepageadapter(cList.get(0));
                adapter.notifyDataSetChanged();

            }
            finalrecycler.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            recyclerView.setVisibility(View.GONE);
            finalrecycler.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.noconnection).into(Noconnection);
            Noconnection.setVisibility(View.VISIBLE);
            refreshbtn.setVisibility(View.VISIBLE);
        }


        ///////// final recycler

        /////refresh
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadPage();
            }
        });
        /////refresh
        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadPage();
            }
        });
        return v;
    }

    private void reloadPage() {

        /*categoryModels.clear();
        cList.clear();
        cListName.clear();*/

        nf = cm.getActiveNetworkInfo();
        DBQuiries.clearData();
        if (nf != null && nf.isConnected() == true) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            Noconnection.setVisibility(View.GONE);
            refreshbtn.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            finalrecycler.setVisibility(View.VISIBLE);
            categoryAdapter = new categoryAdapter(fakecategory);
            adapter = new homepageadapter(homePageModelfakeList);
            recyclerView.setAdapter(categoryAdapter);
            finalrecycler.setAdapter(adapter);
            DBQuiries.setCatergories(recyclerView, getContext());
            cList.add(new ArrayList<HomePageModel>());
            cListName.add("HOME");
            //  adapter=new homepageadapter(cList.get(0));

            DBQuiries.setFragmentdata(finalrecycler, getContext(), 0, "Home");


        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            Toast.makeText(getContext(),
                    "NO internet Connection", Toast.LENGTH_LONG).show();
            recyclerView.setVisibility(View.GONE);
            finalrecycler.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.drawable.noconnection).into(Noconnection);
            Noconnection.setVisibility(View.VISIBLE);
            refreshbtn.setVisibility(View.VISIBLE);
            swipe_refresh.setRefreshing(false);


        }
    }


}