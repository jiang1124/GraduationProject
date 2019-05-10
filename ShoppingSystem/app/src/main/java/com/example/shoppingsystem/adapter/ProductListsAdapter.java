package com.example.shoppingsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.activity.ProductActivity;
import com.example.shoppingsystem.Entity.Product;
import com.example.shoppingsystem.util.ILoadMoreData;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 79124 on 2019/4/1.
 */

public class ProductListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Product> mProductList;
    private Context mContext;
    private int user_id = -1;

    private LayoutInflater layoutInflater;
    private static final int VIEW_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //底部FootView
    private static final int TYPE_FINISH = -1;  //完成FootView
    private int lastVisibleItemPosition;  //最后各一个项目位置
    private MyViewHolder mvHolder;
    private MyProgressViewHolder mpHolder;
    private RecyclerView.LayoutParams param;
    private ILoadMoreData iLoadMoreData;
    private boolean isAll = false;//测试总条数，利用该值判断是否加载更多，可以通过外部设置该值


    public void actionStart(Context context,Product product){
        Intent intent = new Intent(context,ProductActivity.class);
        intent.putExtra("Product",product);
        intent.putExtra("user_id",user_id);
        context.startActivity(intent);
    }

    public void setUserId(int user_id){
        this.user_id=user_id;
    }

    public ProductListsAdapter(Context context, final ILoadMoreData iLoadMoreData,
                              RecyclerView recyclerView, List<Product> productList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.mProductList = productList;
        this.iLoadMoreData = iLoadMoreData;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断是否到底部了
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItemPosition + 1 == getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //如果还有数据则加载更多
                            if (!getIsFinish()) {
                                iLoadMoreData.loadMoreData();
                            }
                        }
                    }, 1000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取最后一个项目位置
                lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private boolean getIsFinish() {
        return isAll;
    }

    public void setIsAll(boolean flag){
        isAll = flag;
    }

    /**
     * 由于包含加载更多项，因此需要加1
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mProductList == null ? 0 : mProductList.size() + 1;
    }

    //根据position判断显示item的类型
    @Override
    public int getItemViewType(int position) {
        if (position + 1 != getItemCount()) {
            return VIEW_ITEM;
        } else {
            if (isAll) {
                return TYPE_FINISH;
            } else {
                return TYPE_FOOTER;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            if(mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_card, parent, false);
            mvHolder = new MyViewHolder(view);
            mvHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = mvHolder.getAdapterPosition();
                    Product product = mProductList.get(position);
                    actionStart(mContext, product);
                }
            });
            mvHolder.productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = mvHolder.getAdapterPosition();
                    Product product = mProductList.get(position);
                    actionStart(mContext, product);
                }
            });
            return mvHolder;
        } else if (viewType == TYPE_FOOTER) {
            View view = layoutInflater.inflate(R.layout.item_swipe_footer, parent, false);
            mpHolder = new MyProgressViewHolder(view);
            return mpHolder;
        } else {
            //全部数据加载完，不在显示加载更多
            View view = layoutInflater.inflate(R.layout.item_swipe_footer, parent, false);
            mpHolder = new MyProgressViewHolder(view);
            param = (RecyclerView.LayoutParams) view.getLayoutParams();
            view.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
            view.setLayoutParams(param);
            return mpHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position + 1 != getItemCount()) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Product product = mProductList.get(position);
            Glide.with(mContext)
                    .load(product.getPro_image())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(myViewHolder.productImage);
            myViewHolder.productName.setText(product.getPro_name());
            myViewHolder.price.setText("" + product.getPro_price());
        }
    }


    /**
     * 显示内容Holder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView productImage;
        TextView productName;
        TextView price;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            productImage = (ImageView) view.findViewById(R.id.iv_product_image);
            productName = (TextView) view.findViewById(R.id.tv_product_name);
            price = (TextView) view.findViewById(R.id.tv_price_product_card);
        }
    }

    /**
     * 显示加载更多Holder
     */
    class MyProgressViewHolder extends RecyclerView.ViewHolder {
        public MyProgressViewHolder(View view) {
            super(view);
        }
    }

}