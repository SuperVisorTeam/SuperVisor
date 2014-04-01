package com.gdut.adapter;

import java.util.ArrayList;
import java.util.List;

import com.gdut.supervisor.FirstItemActivity;
import com.gdut.supervisor.SecondItemActivity;
import com.gdut.supervisor.ThirdItemActivity;
import com.gdut.supervisor.SupervisorActivity;


import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * 该自定义的SupervisorAdapter继承于PagerAdapter,用于加载三个表单的view。
 * @三个表单(即三个页卡)的view由方法ActivityGroup.getLocalActivityManager().startActivity(String ActivityNmae,
*	Intent intent).getDecorView()得到。
*
*@所有成员变量的赋值都在自定义的构造函数SupervisorAdapter(Context context)中实现，
*包括每个页面的标题提供者List<String> titleList，每个页卡提供者List<View> viewList
 */
public class SupervisorAdapter extends PagerAdapter {
	/**
	 * 存储SupervisorActivity的上下文对象 
	 */
Context context;
/**
 * 把需要滑动的页卡添加到这个list中  
 */	
	private List<View> viewList;
	
	/**
	 * 该List存放三个表单的标题的标题
	 */
  private List<String> titleList;
	/**
	 * 三个表单的视图参数
	 */	
  private LayoutParams param;
  //默认的构造函数
  public SupervisorAdapter(){}
  
  @SuppressWarnings("deprecation")
  //自定义的构造函数
public SupervisorAdapter(Context context)
  {
	    //获取SupervisorActivity的上下文对象
	    this.context=context;
	    //为三个表单设置标题，存于titleList中
	    titleList = new ArrayList<String>();// 每个页面的Title数据  
	    titleList.add("表单一");  
	    titleList.add("表单二");  
	    titleList.add("表单三"); 
	    
	    //获取三个表单Activity对应的view,存放于viewList中，
	    viewList=new ArrayList<View>();
	    //设置三个表单view的参数，使表单充满其所在布局
	    param = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	    //得到三个表单的布局
	    //得到第一个表单FirstItemActivity对于的view
	    View view=((SupervisorActivity)context).getLocalActivityManager().startActivity("FirstItemActivity",
				           new Intent(context,FirstItemActivity.class))
				           .getDecorView();
	    view.setLayoutParams(param);
	    viewList.add(view);
	    
	    //  得到第二个表单SecondItemActivity对于的view
	             view=((SupervisorActivity)context).getLocalActivityManager().startActivity("SecondItemActivity",
				          new Intent(context,SecondItemActivity.class))
				         .getDecorView();
	    view.setLayoutParams(param);
	    viewList.add(view);
	    
	    //  得到第三个表单ThirdItemActivity对于的view
	           view=((SupervisorActivity)context).getLocalActivityManager().startActivity("ThirdItemActivity",
				         new Intent(context, ThirdItemActivity.class))
				         .getDecorView();
	    view.setLayoutParams(param);
	    viewList.add(view);
  }
  
  /*
   * 以下都是父类PagerAdapter的未实现方法的实现
   */
  @Override
	public void destroyItem(ViewGroup container, int position,
			Object object) {
		// TODO Auto-generated method stub
		container.removeView(viewList.get(position));  
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}
	 /**
	   * 获取相应页面的标题，由titleList 提供
	   */
	@Override
	public CharSequence getPageTitle(int position) {

		// TODO Auto-generated method stub
	   return titleList.get(position);
	}
	  /**
	   * 初始化相应页面的布局，布局由viewList提供
	   */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(viewList.get(position));
		return viewList.get(position);  
	}
	  /**
	   * 得到该viewpager加载的所有页面的总页数
	   */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;  
	}

}
