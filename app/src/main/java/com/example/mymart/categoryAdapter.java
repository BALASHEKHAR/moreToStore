package com.example.mymart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.categoryViewHolder> {
    private ArrayList<categoryModel> categoryModelArrayList;

    public categoryAdapter(ArrayList<categoryModel> categoryModelArrayList) {
        this.categoryModelArrayList = categoryModelArrayList;
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new categoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {
categoryModel categoryModel=categoryModelArrayList.get(position);
        String icon=categoryModel.getCimage();
String name=categoryModel.getCname();
holder.setcatergoryname(name,position);
holder.setcategoryicon(icon);
    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public class categoryViewHolder extends RecyclerView.ViewHolder{
        ImageView cimage;
        TextView cname;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cimage=itemView.findViewById(R.id.cimage);
            cname=itemView.findViewById(R.id.cname);
        }
        private void setcategoryicon(String uri)
        {

            Glide.with(itemView.getContext()).load(uri).apply(new RequestOptions()
            .placeholder(R.drawable.ic_baseline_home_24)).into(cimage);
        }

        private void setcatergoryname(final String name, final int position)
        {
            cname.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position!=0){
                        Intent ci=new Intent(itemView.getContext(),CategoryActivity.class);
                        ci.putExtra("title",name);
                        itemView.getContext().startActivity(ci);
                    }
                }
            });
        }
    }
}
