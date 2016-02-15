package com.fangshuoit.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tourFragment.ChineseTourGroupFragment;
import com.fangshuoit.tourFragment.ChineseTourSelfFragment;

/**
 * 
 * @ClassName: ChineseTourActivity
 * @Description: TODO 国内游
 * @author 方硕IT 缪春旭
 * @date 2015-6-15 上午9:19:22
 * 
 */
public class ChineseTourActivity extends FragmentActivity implements
		OnClickListener {

	private ViewPager pager;

	private LinearLayout ly_self, ly_group;

	private TextView tv_self, tv_group, tv_title;

	private View v_self, v_group;

	private Fragment fg_self, fg_group;

	private FragmentPagerAdapter fragmentadapter;

	private List<Fragment> fragmentList;

	private Intent whereform;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_chinese);

		initView();

		initEvent();
	}

	private void initEvent() {

		tv_title.setText(whereform.getStringExtra("topTitle"));

		ly_self.setOnClickListener(this);
		ly_group.setOnClickListener(this);

		pager.setAdapter(fragmentadapter);

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				int CurrentItem = pager.getCurrentItem();

				restColor();

				switch (CurrentItem) {
				case 0:
					tv_self.setTextColor(0xff368ff7);
					v_self.setBackgroundColor(0xff368ff7);
					break;
				case 1:
					tv_group.setTextColor(0xff368ff7);
					v_group.setBackgroundColor(0xff368ff7);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	private void initView() {

		whereform = getIntent();

		pager = (ViewPager) findViewById(R.id.vp_tour_chinese);

		ly_self = (LinearLayout) findViewById(R.id.ly_activity_tour_chinese_self);
		ly_group = (LinearLayout) findViewById(R.id.ly_activity_tour_chinese_group);

		tv_self = (TextView) findViewById(R.id.tv_activity_tour_chinese_self);
		tv_group = (TextView) findViewById(R.id.tv_activity_tour_chinese_group);
		tv_title = (TextView) findViewById(R.id.tv_activity_tour_chinese);

		v_self = findViewById(R.id.view_activity_tour_chinese_self);
		v_group = findViewById(R.id.view_activity_tour_chinese_group);

		fragmentList = new ArrayList<Fragment>();

		fg_self = new ChineseTourSelfFragment();
		fg_group = new ChineseTourGroupFragment();

		fragmentList.add(fg_self);
		fragmentList.add(fg_group);

		fragmentadapter = new FragmentPagerAdapter(
				this.getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragmentList.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragmentList.get(arg0);
			}
		};

	}

	/**
	 * 
	 * @Title: restColor
	 * @Description: TODO(初始化颜色)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void restColor() {
		tv_self.setTextColor(0xff444444);
		v_self.setBackgroundColor(0xffffffff);
		tv_group.setTextColor(0xff444444);
		v_group.setBackgroundColor(0xffffffff);
	}

	@Override
	public void onClick(View v) {
		restColor();
		switch (v.getId()) {
		case R.id.ly_activity_tour_chinese_self:
			pager.setCurrentItem(0);
			tv_self.setTextColor(0xff368ff7);
			v_self.setBackgroundColor(0xff368ff7);
			break;
		case R.id.ly_activity_tour_chinese_group:
			pager.setCurrentItem(1);
			tv_group.setTextColor(0xff368ff7);
			v_group.setBackgroundColor(0xff368ff7);
			break;

		default:
			break;
		}
	}
}
