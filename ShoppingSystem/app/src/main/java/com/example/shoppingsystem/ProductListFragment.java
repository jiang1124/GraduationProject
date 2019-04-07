package com.example.shoppingsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by 79124 on 2019/4/1.
 */

public class ProductListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.product_list_fragment,containter,false);
        return view;
    }
}
