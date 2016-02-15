package com.fangshuoit.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.model.GuideListCell;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.AsyncImageLoader;
import com.fangshuoit.tool.AsyncImageLoader.ImageCallback;

/**
 * 
 * @ClassName: GuideListActivity
 * @Description: TODO 导游
 * @author 方硕IT 缪春旭
 * @date 2015-6-8 上午10:15:49
 * 
 */
public class GuideListActivity extends Activity {

	private ListView listView;

	private McxBaseAdapter<GuideListCell> adapter;

	private List<GuideListCell> guideCell;

	private TextView tv_title;

	private Intent whereform;
	AsyncImageLoader asyncImageLoader;// 加载图片

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initView();
		initEvevt();

	}

	private void initEvevt() {
		// 标题
		tv_title.setText(whereform.getStringExtra("topTitle"));

		findViewById(R.id.img_activity_guide_search).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getApplicationContext(),
								SearchActivity.class);
						startActivity(intent);
					}
				});
		listView.setAdapter(adapter);
		adapter.addUpdata(guideCell);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(GuideListActivity.this,
						GuideEveryOneActivity.class);
				startActivity(intent);
			}
		});

	}

	private void initView() {

		whereform = getIntent();

		initcell();

		tv_title = (TextView) findViewById(R.id.tv_activity_guide_title_top);

		listView = (ListView) findViewById(R.id.lv_activity_guide);

		asyncImageLoader = new AsyncImageLoader(this);// 初始化

		adapter = new McxBaseAdapter<GuideListCell>(GuideListActivity.this,
				R.layout.activity_guide_listcell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				GuideListCell data = getItem(position);

				ImageView img = (ImageView) listCell
						.findViewById(R.id.img_activity_guide_img);
				TextView tv_title = (TextView) listCell
						.findViewById(R.id.tv_activity_guide_listcell_title);
				TextView tv_context = (TextView) listCell
						.findViewById(R.id.tv_activity_guide_context);
				TextView tv_time = (TextView) listCell
						.findViewById(R.id.tv_activity_guide_time);

				tv_title.setText(data.getTitle());
				tv_context.setText(data.getContext());
				tv_time.setText(data.getTime());

				// 开始加载图片
				img.setTag(data.getLogo());
				Bitmap cachedImage = asyncImageLoader.loadBitmap(
						data.getLogo(), new ImageCallback() {
							public void imageLoaded(Bitmap imageDrawable,
									String imageUrl) {
								ImageView imageViewByTag = (ImageView) listView
										.findViewWithTag(imageUrl);
								if (imageViewByTag != null) {
									imageViewByTag
											.setImageBitmap(imageDrawable);
								}
							}
						});
				// 如果找不到图片,就显示默认的空的图片
				if (cachedImage == null) {
					img.setImageResource(R.drawable.image_loading);
				} else {
					img.setImageBitmap(cachedImage);
				}

			}
		};

	}

	private void initcell() {
		guideCell = new ArrayList<GuideListCell>();

		guideCell
				.add(new GuideListCell(
						"http://img4.imgtn.bdimg.com/it/u=1511158991,1600151525&fm=21&gp=0.jpg",
						"泡泡蛋蛋", "线路：长安-地中海", "导游时长：18年"));
		guideCell
				.add(new GuideListCell(
						"http://img4.imgtn.bdimg.com/it/u=1955512388,1156473968&fm=21&gp=0.jpg",
						"泡泡蛋蛋", "线路：长安-地中海", "导游时长：18年"));
		guideCell
				.add(new GuideListCell(
						"http://img3.imgtn.bdimg.com/it/u=661414054,384007049&fm=21&gp=0.jpg",
						"泡泡蛋蛋", "线路：长安-地中海", "导游时长：18年"));

	}

}
