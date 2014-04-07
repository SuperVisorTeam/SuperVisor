package com.gdut.supervisor;


import android.app.ActivityGroup;
import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;

import com.gdut.adapter.SupervisorAdapter;

@SuppressWarnings("deprecation")
public class SupervisorActivity extends ActivityGroup {

    /**
     * 该viewPager 用于加载三个表单
     */
    private ViewPager viewPager; 
    /**
     * 一个viewpager的指示器，效果就是一个横的粗的下划线
     */
    private PagerTitleStrip pagerTabStrip;
    //
    private PagerTitleStrip pagerTitleStrip;
    /**
     * 装载在viewpager中的Adpter
     */
    SupervisorAdapter  pagerAdpter;
    
	protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.form);
        //实例化控件
		 viewPager=(ViewPager)findViewById(R.id.viewpager);		 
		 pagerTabStrip=(PagerTitleStrip)findViewById(R.id.pagertab);
		 
		 //为viewpager加载adapter	
		 pagerAdpter =new SupervisorAdapter(SupervisorActivity.this);
    	 viewPager.setAdapter(pagerAdpter);
		}



}
