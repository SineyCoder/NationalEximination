package com.example.homework;

import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.homework.adapter.PagerAdapter;
import com.example.homework.base.BaseActivity;
import com.example.homework.fragment.MainFragment;
import com.example.homework.fragment.WrongQFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    MainFragment mainFragment;
    WrongQFragment wrongQFragment;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        initBottomNav();
        initViewPager();
    }

    private void initViewPager() {
        mainFragment = new MainFragment();
        wrongQFragment = new WrongQFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(mainFragment);
        fragments.add(wrongQFragment);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), PagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
    }

    private void initBottomNav() {
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_favorites, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_friends, "查询中心"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_recents, "个人中心"))
                .setFirstSelectedPosition(0)
                .setTabSelectedListener(this)
                .initialise();
        bottomNavigationBar.selectTab(0);
    }

    @Override
    public void onTabSelected(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
