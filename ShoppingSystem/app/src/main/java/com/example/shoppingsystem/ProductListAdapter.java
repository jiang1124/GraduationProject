package com.example.shoppingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 79124 on 2019/4/1.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private List<Product> mProductList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View productView;
        ImageView productImage;
        TextView productName;

        public ViewHolder(View view) {
            super(view);
            productView = view;
            productImage = (ImageView) view.findViewById(R.id.product_image);
            productName = (TextView) view.findViewById(R.id.product_name);
        }
    }

    public ProductListAdapter(List<Product> productList,Context context){
        mProductList = productList;
        this.context = context;
    }

    public static void actionStart(Context context,Product product){
        Intent intent = new Intent(context,ProductActivity.class);
        intent.putExtra("Product",product);
        context.startActivity(intent);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.productView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Product product = mProductList.get(position);
                ProductListAdapter.actionStart(context,product);
            }
        });
        holder.productImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Product product = mProductList.get(position);
                ProductListAdapter.actionStart(context,product);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Product product = mProductList.get(position);
        holder.productImage.setImageResource(product.getProductImageId());
        holder.productName.setText(product.getProductName());
    }
    @Override
    public int getItemCount(){
        return mProductList.size();
    }
}
