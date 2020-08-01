package com.example.mymart;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class gridproductadapter extends BaseAdapter {
    ArrayList<horizontalproductmodel> horizontalproductmodelArrayList;

    public gridproductadapter(ArrayList<horizontalproductmodel> horizontalproductmodelArrayList) {
        this.horizontalproductmodelArrayList = horizontalproductmodelArrayList;
    }

    @Override
    public int getCount() {
        return 4;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
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
                    parent.getContext().startActivity(intent);
                }
            });
            ImageView productimage=v.findViewById(R.id.hsproductimage);
            TextView productname=v.findViewById(R.id.hsproducttitle);
            TextView productdesc=v.findViewById(R.id.hsproductdesc);
            TextView productprice=v.findViewById(R.id.hsproductprice);
            productimage.setImageResource(horizontalproductmodelArrayList.get(position).getPimage());
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
