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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.model.TicketCell;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.AsyncImageLoader;
import com.fangshuoit.tool.AsyncImageLoader.ImageCallback;

/**
 * 
 * @ClassName: FoodActivity
 * @Description: TODO 美食列表
 * @author 方硕IT 缪春旭
 * @date 2015-5-25 下午6:11:02
 * 
 */
public class FoodActivity extends Activity {

	private GridView gv_ticket;
	private McxBaseAdapter<TicketCell> adapter;

	AsyncImageLoader asyncImageLoader;// 加载图片

	private List<TicketCell> a = new ArrayList<TicketCell>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food);

		initView();

		initEvent();

	}

	// 初始化数据
	private void initCell() {
		a.add(new TicketCell(
				"http://img5.imgtn.bdimg.com/it/u=3933569337,2438978682&fm=21&gp=0.jpg",
				"美食哦~", "猜猜好不好吃", "9"));
		a.add(new TicketCell(
				"http://img0.imgtn.bdimg.com/it/u=2420809094,62511904&fm=21&gp=0.jpg",
				"美食哦~", "猜猜好不好吃", "99"));
		a.add(new TicketCell(
				"http://img3.imgtn.bdimg.com/it/u=4006113687,2607209332&fm=21&gp=0.jpg",
				"美食哦~", "猜猜好不好吃", "999"));
		a.add(new TicketCell(
				"http://img2.imgtn.bdimg.com/it/u=2979044674,4294609616&fm=21&gp=0.jpg",
				"美食哦~", "猜猜好不好吃", "9999"));
		a.add(new TicketCell(
				"http://img2.imgtn.bdimg.com/it/u=3030445410,4134922296&fm=21&gp=0.jpg",
				"美食哦~", "猜猜好不好吃", "99999"));
		a.add(new TicketCell(
				"http://img0.imgtn.bdimg.com/it/u=2801306157,702856119&fm=21&gp=0.jpg",
				"美食哦~", "猜猜好不好吃", "1"));
		a.add(new TicketCell(
				"http://img0.imgtn.bdimg.com/it/u=100468151,3605209555&fm=21&gp=0.jpg",
				"美食哦~", "猜猜好不好吃", "11"));
		a.add(new TicketCell(
				"http://img3.imgtn.bdimg.com/it/u=67987514,1823530075&fm=21&gp=0.jpg",
				"美食哦~", "猜猜好不好吃", "111"));
		a.add(new TicketCell(
				"http://img4.imgtn.bdimg.com/it/u=1755319500,2693054120&fm=21&gp=0.jpg",
				"美食哦~", "猜猜好不好吃", "1111"));
	}

	private void initEvent() {
		
		findViewById(R.id.img_activity_food_search)
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FoodActivity.this,
						SearchActivity.class);
				startActivity(intent);
			}
		});

		gv_ticket.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(FoodActivity.this,
						FoodInfoActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_open_translate,
						R.anim.activity_close_translate);
			}
		});
		gv_ticket.setAdapter(adapter);

		adapter.addUpdata(a);

	}

	private void initView() {
		asyncImageLoader = new AsyncImageLoader(this);// 初始化

		initCell();

		gv_ticket = (GridView) findViewById(R.id.gv_activity_food);
		gv_ticket.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new McxBaseAdapter<TicketCell>(FoodActivity.this,
				R.layout.activity_ticket_gridview_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				TicketCell data = getItem(position);
				ImageView image = (ImageView) listCell
						.findViewById(R.id.img_ticket_buy);
				// 开始加载图片
				image.setTag(data.getImg());
				Bitmap cachedImage = asyncImageLoader.loadBitmap(data.getImg(),
						new ImageCallback() {
							public void imageLoaded(Bitmap imageDrawable,
									String imageUrl) {
								ImageView imageViewByTag = (ImageView) gv_ticket
										.findViewWithTag(imageUrl);
								if (imageViewByTag != null) {
									imageViewByTag
											.setImageBitmap(imageDrawable);
								}
							}
						});
				// 如果找不到图片,就显示默认的空的图片
				if (cachedImage == null) {
					image.setImageResource(R.drawable.image_loading);
				} else {
					image.setImageBitmap(cachedImage);
				}

				TextView tv_big = (TextView) listCell
						.findViewById(R.id.tv_tivket_buy_big);
				tv_big.setText(data.getBigtv());
				TextView tv_small = (TextView) listCell
						.findViewById(R.id.tv_tivket_buy_small);
				tv_small.setText(data.getSmalltv());
				TextView tv_price = (TextView) listCell
						.findViewById(R.id.tv_tivket_buy_price);
				tv_price.setText(data.getPrice());
			}
		};

	}

}
