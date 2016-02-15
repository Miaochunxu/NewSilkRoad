package com.fangshuoit.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ab.view.sliding.AbSlidingPlayView;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.AsyncImageLoader;

public class GroupTourInfoActivity extends Activity {

	private AbSlidingPlayView mySlidingPlayView = null;// 初始化滑动
	AsyncImageLoader asyncImageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_tour_info);

		initView();

		initEvent();

	}

	private void initEvent() {

	}

	private void initView() {
		updataContent();
	}

	private void updataContent() {
		mySlidingPlayView = (AbSlidingPlayView) findViewById(R.id.mySlidingPlayView);
		asyncImageLoader = new AsyncImageLoader(GroupTourInfoActivity.this);
		// String imageUrl01 =
		// "http://a.36krcnd.com/photo/2015/9e21faf754e50cc4a92996a03d0553a9.jpg";
		// String imageUrl02 =
		// "http://a.36krcnd.com/photo/2015/34f992c263234674973a0aa0873ac17e.png";
		// String imageUrl03 =
		// "http://a.36krcnd.com/photo/2015/688f1fae1ee24b2427c30a47a20752b4.jpg";
		LayoutInflater mInflater = LayoutInflater
				.from(GroupTourInfoActivity.this);
		final View mPlayView = mInflater.inflate(R.layout.play_view_item, null);
		ImageView mPlayImage = (ImageView) mPlayView
				.findViewById(R.id.mPlayImage);
		mPlayImage.setBackgroundResource(R.drawable.appstart);
		// loadImg(imageUrl01, mPlayImage);
		final View mPlayView1 = mInflater
				.inflate(R.layout.play_view_item, null);
		ImageView mPlayImage1 = (ImageView) mPlayView1
				.findViewById(R.id.mPlayImage);
		mPlayImage1.setBackgroundResource(R.drawable.appstart);
		// loadImg(imageUrl02, mPlayImage1);
		final View mPlayView2 = mInflater
				.inflate(R.layout.play_view_item, null);
		ImageView mPlayImage2 = (ImageView) mPlayView2
				.findViewById(R.id.mPlayImage);
		mPlayImage2.setBackgroundResource(R.drawable.appstart);
		// loadImg(imageUrl03, mPlayImage2);
		mySlidingPlayView.setNavHorizontalGravity(Gravity.RIGHT);
		mySlidingPlayView.addView(mPlayView);
		mySlidingPlayView.addView(mPlayView1);
		mySlidingPlayView.addView(mPlayView2);
		mySlidingPlayView.startPlay();
		mySlidingPlayView
				.setOnItemClickListener(new AbSlidingPlayView.AbOnItemClickListener() {
					@Override
					public void onClick(int position) {
						// AbToastUtil.showToast(getActivity(), "点击" +
						// position);
						// Intent intent = new Intent();
						// intent.setClass(getActivity(),
						// ContentViewActivity.class);
						// startActivity(intent);
					}
				});
	}

}
