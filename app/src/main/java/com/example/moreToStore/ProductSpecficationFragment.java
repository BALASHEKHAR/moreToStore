package com.example.moreToStore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductSpecficationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductSpecficationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductSpecficationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductSpecficationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductSpecficationFragment newInstance(String param1, String param2) {
        ProductSpecficationFragment fragment = new ProductSpecficationFragment();
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
    public static List<ProductSpecifiModel> productSpecifiModelList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_product_specfication, container, false);
        recyclerView=v.findViewById(R.id.productSpecificationRecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(v.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

      /*  productSpecifiModelList.add(new ProductSpecifiModel(0,"GENERAL"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));

        productSpecifiModelList.add(new ProductSpecifiModel(0,"DISPLAY"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));

        productSpecifiModelList.add(new ProductSpecifiModel(0,"PERFORMANCE"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));
        productSpecifiModelList.add(new ProductSpecifiModel(1,"RAM","8GB"));

*/
        ProductSpecificationAdapter productSpecificationAdapter=new
                ProductSpecificationAdapter(productSpecifiModelList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();
        return v;
    }
}