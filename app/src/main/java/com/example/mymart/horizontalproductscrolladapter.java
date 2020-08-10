package com.example.mymart;

import android.content.Intent;
import android.util.Log;
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

class horizontalproductscrolladapter extends RecyclerView.Adapter<horizontalproductscrolladapter.Viewholder> {
    ArrayList<horizontalproductmodel> horizontalproductmodelArrayList;

    public horizontalproductscrolladapter(ArrayList<horizontalproductmodel> horizontalproductmodelArrayList) {
        this.horizontalproductmodelArrayList = horizontalproductmodelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item,parent,false);
      return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String res=horizontalproductmodelArrayList.get(position).getPimage();
        String hname=horizontalproductmodelArrayList.get(position).getPname();
        String hdesc=horizontalproductmodelArrayList.get(position).getPdesc();
        String hprice=horizontalproductmodelArrayList.get(position).getPprice();
        holder.setHimage(res);
        holder.setHname(hname);
        holder.setHdesc(hdesc);
        holder.setHprice(hprice);

    }

    @Override
    public int getItemCount() {
        if(horizontalproductmodelArrayList.size()>=8)
            return 8;
        else
        return horizontalproductmodelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView himage;
        TextView hname,hdesc,hprice;
        View i;
        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            himage=itemView.findViewById(R.id.hsproductimage);
            hname=itemView.findViewById(R.id.hsproducttitle);
            hdesc=itemView.findViewById(R.id.hsproductdesc);
            hprice=itemView.findViewById(R.id.hsproductprice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
        private  void  setHimage(String res)
        {
           // Log.d("uel",res);
            Glide.with(itemView.getContext()).load(res).apply(new RequestOptions()
                    .placeholder(R.drawable.ic_baseline_home_24)).into(himage);
        }
        public void setHname(String s)
        {
            hname.setText(s);

        }
        public void setHdesc(String s)
        {
            hdesc.setText(s);

        }
        public void setHprice(String s)
        {
hprice.setText("RS: "+s+"/-");

        }

    }
}
