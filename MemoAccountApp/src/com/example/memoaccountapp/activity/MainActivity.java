package com.example.memoaccountapp.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.fragment.BaseFragment;
import com.example.memoaccountapp.fragment.DetailFragment;
import com.example.memoaccountapp.fragment.ExploreFragment;
import com.example.memoaccountapp.fragment.FirstPageFragment;
import com.example.memoaccountapp.fragment.MineFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	//首页
	@ViewInject(R.id.re_firstpage)
	RelativeLayout re_firstpage;
	@ViewInject(R.id.tv_firstpage)
	TextView tv_firstpage;
	//明细
	@ViewInject(R.id.re_tribe)
	RelativeLayout re_tribe;
	@ViewInject(R.id.tv_tribe)
	TextView tv_tribe;
	//发现
	@ViewInject(R.id.re_contact)
	RelativeLayout re_contact;
	@ViewInject(R.id.tv_contact)
	TextView tv_contact;
	//我的
	@ViewInject(R.id.re_me)
	RelativeLayout re_me;
	@ViewInject(R.id.tv_me)
	TextView tv_me;
	
	FirstPageFragment firstpagefragment;
	DetailFragment detailfragment;
	ExploreFragment explorefragment;
	MineFragment minefragment;
	List<BaseFragment> fragments = new ArrayList<BaseFragment>();
	int current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 除去标题栏
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        init();
        //进入主界面默认显示“首页”
        select_firstpage();
        getFragmentManager().beginTransaction().add(R.id.fragment_container,fragments.get(0)).commit();
    }
    
    //初始化参数
    public void init(){
    	re_firstpage.setOnClickListener(this);
    	re_tribe.setOnClickListener(this);
    	re_contact.setOnClickListener(this);
    	re_me.setOnClickListener(this);
    	//初始化事务对象
    	firstpagefragment = new FirstPageFragment();
    	detailfragment = new DetailFragment();
    	explorefragment = new ExploreFragment();
    	minefragment = new MineFragment();
    	fragments.add(firstpagefragment);
    	fragments.add(detailfragment);
    	fragments.add(explorefragment);
    	fragments.add(minefragment);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int next = 0;
		switch (v.getId()) {
		case R.id.re_firstpage:
			//选中首页
			select_firstpage();
			next = 0;
			break;
		case R.id.re_tribe:
			//选中部落
			select_tribe();
			next = 1;
			break;
		case R.id.re_contact:
			//选中关系
			select_contact();
			next = 2;
			break;
		case R.id.re_me:
			//选中我的
			select_me();
			next = 3;
			break;
		
		}
		changefragment(next);
	}
	//切换fragment
	public void changefragment(int next){
		if(next!=current){
			FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
			beginTransaction.hide(fragments.get(current));
			if(!fragments.get(next).isAdded()){
				beginTransaction.add(R.id.fragment_container,fragments.get(next));
			}
			beginTransaction.show(fragments.get(next)).commit();
		}
		current = next;
	}
	//选中firstpage页面
	public void select_firstpage(){
		re_firstpage.setSelected(true);
		re_tribe.setSelected(false);
		re_contact.setSelected(false);
		re_me.setSelected(false);
		tv_firstpage.setTextColor(getResources().getColor(R.color.mian_icon_press));
		tv_tribe.setTextColor(getResources().getColor(R.color.mian_icon_up));
		tv_contact.setTextColor(getResources().getColor(R.color.mian_icon_up));
		tv_me.setTextColor(getResources().getColor(R.color.mian_icon_up));
	}
	
	//选中tribe页面
	public void select_tribe(){
		re_firstpage.setSelected(false);
		re_tribe.setSelected(true);
		re_contact.setSelected(false);
		re_me.setSelected(false);
		tv_firstpage.setTextColor(getResources().getColor(R.color.mian_icon_up));
		tv_tribe.setTextColor(getResources().getColor(R.color.mian_icon_press));
		tv_contact.setTextColor(getResources().getColor(R.color.mian_icon_up));
		tv_me.setTextColor(getResources().getColor(R.color.mian_icon_up));
		
	}
	
	//选中contact页面
	public void select_contact(){
		re_firstpage.setSelected(false);
		re_tribe.setSelected(false);
		re_contact.setSelected(true);
		re_me.setSelected(false);
		tv_firstpage.setTextColor(getResources().getColor(R.color.mian_icon_up));
		tv_tribe.setTextColor(getResources().getColor(R.color.mian_icon_up));
		tv_contact.setTextColor(getResources().getColor(R.color.mian_icon_press));
		tv_me.setTextColor(getResources().getColor(R.color.mian_icon_up));
		
	}
	
	//选中me页面
	public void select_me(){
		re_firstpage.setSelected(false);
		re_tribe.setSelected(false);
		re_contact.setSelected(false);
		re_me.setSelected(true);
		tv_firstpage.setTextColor(getResources().getColor(R.color.mian_icon_up));
		tv_tribe.setTextColor(getResources().getColor(R.color.mian_icon_up));
		tv_contact.setTextColor(getResources().getColor(R.color.mian_icon_up));
		tv_me.setTextColor(getResources().getColor(R.color.mian_icon_press));
		
	}
    
}
