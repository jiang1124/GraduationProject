package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.shoppingsystem.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.home_button)
    Button homeButton;
    @InjectView(R.id.search_button)
    Button searchButton;
    @InjectView(R.id.sort_button)
    Button sortButton;
    @InjectView(R.id.shopping_cart_button)
    Button shoppingCartButton;
    @InjectView(R.id.person_button)
    Button personButton;
    @InjectView(R.id.home_layout_main)
    LinearLayout homeView;
    @InjectView(R.id.sort_layout_main)
    LinearLayout sortView;
    @InjectView(R.id.shopping_cart_layout_main)
    LinearLayout shoppingCartView;
    @InjectView(R.id.person_layout_main)
    LinearLayout personView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
    }

    /*
     * 下菜单栏点击事件及搜索点击事件
     */
    @OnClick({R.id.home_button,R.id.search_button,R.id.sort_button,R.id.shopping_cart_button,R.id.person_button})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.search_button:
                EditText searchEditText = (EditText) findViewById(R.id.search_edit);
                String searchContent = searchEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                intent.putExtra("key",searchContent);
                startActivity(intent);
                break;
            case R.id.home_button:
                PageSwitch(sortView, shoppingCartView, personView, homeView);
                break;
            case R.id.sort_button:
                PageSwitch(homeView, shoppingCartView, personView, sortView);
                break;
            case R.id.shopping_cart_button:
                PageSwitch(homeView, sortView, personView, shoppingCartView);
                break;
            case R.id.person_button:
                PageSwitch(homeView, sortView, shoppingCartView, personView);
                break;
            default:
                break;
        }
    }

    /*
     * 页面切换
     */
    private final void PageSwitch(View goneView1,View goneView2,View goneView3,View visibleView){
        goneView1.setVisibility(View.GONE);
        goneView2.setVisibility(View.GONE);
        goneView3.setVisibility(View.GONE);
        visibleView.setVisibility(View.VISIBLE);
    }


}
