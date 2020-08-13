package com.example.moreToStore;

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
        String productid=horizontalproductmodelArrayList.get(position).getProduct_ID();
        holder.setHimage(productid,res,hname,hdesc,hprice);

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

        }
        private  void  setHimage(final String productid, String res, String s, String ss, String sss)
        {
           // Log.d("uel",res);
            Glide.with(itemView.getContext()).load(res).apply(new RequestOptions()
                    .placeholder(R.drawable.placeholder)).into(himage);   hname.setText(s);    hdesc.setText(ss);hprice.setText("RS: "+sss+"/-");
                    if(!s.equals(""))
                    {
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                             intent.putExtra("PRODUCT_ID",
                                      productid );
                                itemView.getContext().startActivity(intent);
                            }
                        });
                    }
        }





    }
}
