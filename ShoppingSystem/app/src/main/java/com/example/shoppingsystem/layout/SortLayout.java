package com.example.shoppingsystem.layout;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.activity.SearchActivity;
import com.example.shoppingsystem.Application.BaseApplication;

import java.util.Arrays;

/**
 * Created by 79124 on 2019/4/10.
 */

public class SortLayout extends LinearLayout /*implements AdapterView.OnItemClickListener*/{
//    private String[] sortData = new String[]{};
    public SortLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.sort_layout,this);
//        initSort();
//        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,sortData);
//        ListView sortListView = (ListView) findViewById(R.id.sort_list_view);
//        sortListView.setAdapter(sortAdapter);
//        sortListView.setOnItemClickListener(this);
    }

    /*
     * 分类点击事件
     */
//    @Override
//    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
//        Context context = BaseApplication.getContext();
//        Intent intent = new Intent(context,SearchActivity.class);
//        String sort = sortData[position];
//        intent.putExtra("User",user);
//        intent.putExtra("sort",sort);
//        context.startActivity(intent);
//    }
//    /*
//     * 初始化分类项
//     */
//    private void initSort(){
//        sortData = Arrays.copyOf(sortData, sortData.length+1);
//        sortData[sortData.length-1] = "水果"; //在链接好数据库的实战中，这个地方水果可以改为re.title
//        sortData = Arrays.copyOf(sortData, sortData.length+1);
//        sortData[sortData.length-1] = "休闲零食";
//        sortData = Arrays.copyOf(sortData, sortData.length+1);
//        sortData[sortData.length-1] = "茶酒冲饮";
//        sortData = Arrays.copyOf(sortData, sortData.length+1);
//        sortData[sortData.length-1] = "粮油干货";
//        sortData = Arrays.copyOf(sortData, sortData.length+1);
//        sortData[sortData.length-1] = "居家日用";
//        sortData = Arrays.copyOf(sortData, sortData.length+1);
//        sortData[sortData.length-1] = "餐饮用具";
//        sortData = Arrays.copyOf(sortData, sortData.length+1);
//        sortData[sortData.length-1] = "厨房烹饪";
//        sortData = Arrays.copyOf(sortData, sortData.length+1);
//        sortData[sortData.length-1] = "清洁用具";
//    }
}
