package com.gdut.supervisor.ui;


import com.gdut.supervisor.R;
import com.gdut.supervisor.adapter.SupervisorAdapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerTitleStrip;
import android.os.Bundle;
/**
 * 包含三表单的FragmentActivity
 */
public class SupervisorActivity extends FragmentActivity  {

    /**
     * 该viewPager 用于加载三个表单
     */
    private ViewPager viewPager; 
    /**
     * 一个viewpager的指示器，效果就是一个横的粗的下划线
     */
    private PagerTabStrip pagerTabStrip;
    //
    private PagerTitleStrip pagerTitleStrip;
    /**
     * 装载在viewpager中的Adpter
     */
    SupervisorAdapter  pagerAdpter;
    /**
     * 装控
     */
    FragmentManager  myFragmentManager;
	protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.form);
        //实例化控件
		 viewPager=(ViewPager)findViewById(R.id.viewpager);		 
		 pagerTabStrip=(PagerTabStrip)findViewById(R.id.pagertab);
		 //
		 myFragmentManager=getSupportFragmentManager();
		 //为viewpager加载adapter	
		 SupervisorAdapter mySupervisorAdapter=new SupervisorAdapter(myFragmentManager);
		 viewPager.setAdapter(mySupervisorAdapter);
		}



}
