package com.example.mymart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.ViewHolder> {
    List<ProductSpecifiModel> productSpecifiModelList;

    public ProductSpecificationAdapter(List<ProductSpecifiModel> productSpecifiModelList) {
        this.productSpecifiModelList = productSpecifiModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case ProductSpecifiModel.SPECIFICATINO_TITLE:
                TextView title=new TextView(parent.getContext());
                title.setTypeface(null, Typeface.BOLD);
                title.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(setDp(16,parent.getContext()),setDp(16,parent.getContext()),setDp(16,parent.getContext()),setDp(8,parent.getContext()));
                title.setLayoutParams(layoutParams);
                return  new ViewHolder(title);
            case ProductSpecifiModel.SPECIFICATINO_BIDY:
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specifi_item,parent,false);
                return new ViewHolder(view);
            default:return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       switch (productSpecifiModelList.get(position).getType())
       {
           case ProductSpecifiModel.SPECIFICATINO_TITLE:
               holder.settitle(productSpecifiModelList.get(position).getTitle());
               break;
           case ProductSpecifiModel.SPECIFICATINO_BIDY:
               String fname=productSpecifiModelList.get(position).getFeaturename();
               String fvalue=productSpecifiModelList.get(position).getFeaturevalue();
               holder.setfeatures(fname,fvalue);
               break;
           default:return;
       }

    }

    @Override
    public int getItemCount() { //
        return productSpecifiModelList.size();

    }

    @Override
    public int getItemViewType(int position) {
        switch (productSpecifiModelList.get(position).getType())
        {
            case 0:
                return ProductSpecifiModel.SPECIFICATINO_TITLE;
            case 1:
                return ProductSpecifiModel.SPECIFICATINO_BIDY;
            default:
                return -1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView featureName,featureValue,title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }
        private void settitle(String titleText)
        {
            title= (TextView) itemView;
            title.setText(titleText);

        }
        private  void setfeatures(String featureame,String featurealue)
        {
            featureName=itemView.findViewById(R.id.futureName);
            featureValue=itemView.findViewById(R.id.featureValue);
            featureName.setText(featureame);
            featureValue.setText(featurealue);
        }


    }
    private int setDp(int dp, Context context)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
}
