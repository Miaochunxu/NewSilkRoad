package com.fangshuoit.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.model.TourRecord;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.AsyncImageLoader;
import com.fangshuoit.tool.AsyncImageLoader.ImageCallback;

/**
 * @ClassName: TourRecordActivity
 * @Description: TODO 游记列表
 * @author 方硕IT 缪春旭
 * @date 2015-5-21 上午9:25:25
 * 
 */
public class TourRecordActivity extends Activity {

	private GridView gv_tour_record;

	private McxBaseAdapter<TourRecord> adapter;

	AsyncImageLoader asyncImageLoader;// 加载图片

	private List<TourRecord> a = new ArrayList<TourRecord>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tour_record);

		initView();

		initEvent();

	}

	private void initCell() {
		a.add(new TourRecord(
				"丝路重新走，体验古人风情",
				"泡泡蛋蛋",
				"9999",
				"2015-05-05",
				"http://7xi7ke.com2.z0.glb.qiniucdn.com/img/o_19lo83cgm1jbgf6rg4vjofql7a.jpg？imageView2/1/w/320/h/150",
				"http://img1.imgtn.bdimg.com/it/u=4118234664,1321792697&fm=23&gp=0.jpg"));
		a.add(new TourRecord(
				"新丝绸之路，体验不一样的路途",
				"泡泡",
				"999",
				"2015-05-05",
				"http://img3.imgtn.bdimg.com/it/u=3133536891,1702628104&fm=21&gp=0.jpg",
				"http://img3.imgtn.bdimg.com/it/u=1803270980,3484990373&fm=21&gp=0.jpg"));
		a.add(new TourRecord(
				"走丝路",
				"蛋蛋",
				"99",
				"2015-05-05",
				"http://img3.imgtn.bdimg.com/it/u=3521048934,4019463781&fm=21&gp=0.jpg",
				"http://img5.imgtn.bdimg.com/it/u=3114090356,2058213901&fm=21&gp=0.jpg"));
		a.add(new TourRecord(
				"宁夏游记",
				"泡蛋",
				"9",
				"2015-05-05",
				"http://img4.imgtn.bdimg.com/it/u=3468126096,2804350268&fm=21&gp=0.jpg",
				"http://img3.imgtn.bdimg.com/it/u=1031304898,3183698213&fm=21&gp=0.jpg"));
		a.add(new TourRecord(
				"游丝路，游宁夏",
				"泡泡蛋蛋",
				"1756",
				"2015-05-05",
				"http://img4.imgtn.bdimg.com/it/u=2928561642,2692880570&fm=21&gp=0.jpg",
				"http://img3.imgtn.bdimg.com/it/u=2651535528,2459304775&fm=21&gp=0.jpg"));
		a.add(new TourRecord(
				"丝路风情，媚人无数",
				"咒法发",
				"9999",
				"2015-05-05",
				"http://img4.imgtn.bdimg.com/it/u=1136908627,707605815&fm=21&gp=0.jpg",
				"http://img4.imgtn.bdimg.com/it/u=3391222478,2503943670&fm=21&gp=0.jpg"));
		a.add(new TourRecord(
				"走还不走，这是一个问题",
				"蛋",
				"8888",
				"2015-05-05",
				"http://img4.imgtn.bdimg.com/it/u=3441562284,3078190556&fm=21&gp=0.jpg",
				"http://img1.imgtn.bdimg.com/it/u=1470328435,2469215874&fm=21&gp=0.jpg"));
	}

	private void initEvent() {

		gv_tour_record.setAdapter(adapter);

		gv_tour_record.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(TourRecordActivity.this,
						TourRecordInfoActivity.class);
				startActivity(intent);
			}
		});
		adapter.addUpdata(a);
	}

	private void initView() {

		initCell();
		asyncImageLoader = new AsyncImageLoader(this);// 初始化
		gv_tour_record = (GridView) findViewById(R.id.gv_tour_rccord);
		gv_tour_record.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new McxBaseAdapter<TourRecord>(TourRecordActivity.this,
				R.layout.activity_tour_record_gv_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				TourRecord data = getItem(position);

				ImageView img_bg = (ImageView) listCell
						.findViewById(R.id.img_tour_record_img);

				ImageView img_head = (ImageView) listCell
						.findViewById(R.id.img_tour_record_headimg);
				// 开始加载背景图片
				img_bg.setTag(data.getImg());
				Bitmap cachedImage = asyncImageLoader.loadBitmap(data.getImg(),
						new ImageCallback() {
							public void imageLoaded(Bitmap imageDrawable,
									String imageUrl) {
								ImageView imageViewByTag = (ImageView) gv_tour_record
										.findViewWithTag(imageUrl);
								if (imageViewByTag != null) {
									imageViewByTag
											.setImageBitmap(imageDrawable);
								}
							}
						});
				// 如果找不到图片,就显示默认的空的图片
				if (cachedImage == null) {
					img_bg.setImageResource(R.drawable.image_loading);
				} else {
					img_bg.setImageBitmap(cachedImage);
				}
				// 开始加载头像图片
				img_head.setTag(data.getHeadimg());
				Bitmap cachedImage_head = asyncImageLoader.loadBitmap(
						data.getHeadimg(), new ImageCallback() {
							public void imageLoaded(Bitmap imageDrawable,
									String imageUrl) {
								ImageView imageViewByTag = (ImageView) gv_tour_record
										.findViewWithTag(imageUrl);
								if (imageViewByTag != null) {
									imageViewByTag
											.setImageBitmap(imageDrawable);
								}
							}
						});
				// 如果找不到图片,就显示默认的空的图片
				if (cachedImage_head == null) {
					img_head.setImageResource(R.drawable.image_loading);
				} else {
					img_head.setImageBitmap(cachedImage);
				}

				TextView tv_title = (TextView) listCell
						.findViewById(R.id.tv_tour_record_title);
				tv_title.setText(data.getTitle());

				TextView tv_username = (TextView) listCell
						.findViewById(R.id.tv_tour_record_username);
				tv_username.setText(data.getUsername());

				TextView tv_number = (TextView) listCell
						.findViewById(R.id.tv_tour_record_number);
				tv_number.setText(data.getNumber());

				TextView tv_time = (TextView) listCell
						.findViewById(R.id.tv_tour_record_time);
				tv_time.setText(data.getTime());
			}
		};

	}

}
