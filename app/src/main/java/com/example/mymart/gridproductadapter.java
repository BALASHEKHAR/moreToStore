package com.example.mymart;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

class gridproductadapter extends BaseAdapter {
    ArrayList<horizontalproductmodel> horizontalproductmodelArrayList;

    public gridproductadapter(ArrayList<horizontalproductmodel> horizontalproductmodelArrayList) {
        this.horizontalproductmodelArrayList = horizontalproductmodelArrayList;
    }

    @Override
    public int getCount() {
        return horizontalproductmodelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View v;
        if(convertView==null)
        {

    v=LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item,null);
            v.setElevation(0);
            v.setBackgroundColor(Color.parseColor("#ffffff"));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(parent.getContext(),ProductDetailsActivity.class);
               //     Log.d("AAA",horizontalproductmodelArrayList.get(position).getProduct_ID());
                    intent.putExtra("PRODUCT_ID",horizontalproductmodelArrayList.get(position).getProduct_ID());
                    parent.getContext().startActivity(intent);
                }
            });
            ImageView productimage=v.findViewById(R.id.hsproductimage);
            TextView productname=v.findViewById(R.id.hsproducttitle);
            TextView productdesc=v.findViewById(R.id.hsproductdesc);
            TextView productprice=v.findViewById(R.id.hsproductprice);
         //   productimage.setImageResource(horizontalproductmodelArrayList.get(position).getPimage());
            Glide.with(parent.getContext()).load(horizontalproductmodelArrayList.get(position).getPimage()).
                    apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(productimage);
            productname.setText(horizontalproductmodelArrayList.get(position).getPname());
            productdesc.setText(horizontalproductmodelArrayList.get(position).getPdesc());
            productprice.setText(horizontalproductmodelArrayList.get(position).getPprice());

        }
        else
        {
v=convertView;
        }
        return v;
    }
}
