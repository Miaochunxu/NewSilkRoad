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
import com.fangshuoit.tool.IfHttpToStart;

/**
 * @ClassName: EntranceTicketActivity
 * @Description: TODO 门票列表Activity
 * @author 方硕IT 缪春旭
 * @date 2015-5-8 上午9:50:26
 * 
 */
public class EntranceTicketActivity extends Activity {

	private GridView gv_ticket;
	private McxBaseAdapter<TicketCell> adapter;

	AsyncImageLoader asyncImageLoader;// 加载图片

	private List<TicketCell> a = new ArrayList<TicketCell>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entrance_ticket);

		initView();

		initEvent();

	}

	// 初始化数据
	private void initCell() {
		a.add(new TicketCell(
				"http://d.hiphotos.baidu.com/image/h%3D360/sign=ac9e54d40e55b31983f9847373a88286/503d269759ee3d6d56a69f1e47166d224e4ade86.jpg",
				"美女哦~", "猜猜她是谁", "9"));
		a.add(new TicketCell(
				"http://h.hiphotos.baidu.com/image/h%3D360/sign=ce91f88eb251f819ee25054ceab54a76/d6ca7bcb0a46f21f498a20f6f4246b600d33aedd.jpg",
				"美女哦~", "猜猜她是谁", "99"));
		a.add(new TicketCell(
				"http://c.hiphotos.baidu.com/image/h%3D360/sign=0f85514ed4c8a786a12a4c085708c9c7/5bafa40f4bfbfbed81fec7cb7df0f736aec31fda.jpg",
				"美女哦~", "猜猜她是谁", "999"));
		a.add(new TicketCell(
				"http://e.hiphotos.baidu.com/image/h%3D360/sign=a4bc198c9a3df8dcb93d8997fd1072bf/aec379310a55b3193fb7950e46a98226cffc177a.jpg",
				"美女哦~", "猜猜她是谁", "9999"));
		a.add(new TicketCell(
				"http://f.hiphotos.baidu.com/image/h%3D360/sign=a8638f5d3a01213fd03348da64e636f8/fc1f4134970a304e006c1b7ed2c8a786c8175ce3.jpg",
				"美女哦~", "猜猜她是谁", "99999"));
		a.add(new TicketCell(
				"http://c.hiphotos.baidu.com/image/h%3D360/sign=09e55879fbdcd100d29cfe27428b47be/78310a55b319ebc4856784ed8126cffc1e1716a2.jpg",
				"美女哦~", "猜猜她是谁", "1"));
		a.add(new TicketCell(
				"http://e.hiphotos.baidu.com/image/h%3D360/sign=713d95ce9b25bc31345d079e6ede8de7/8694a4c27d1ed21b523c6ee5af6eddc451da3f3c.jpg",
				"美女哦~", "猜猜她是谁", "11"));
		a.add(new TicketCell(
				"http://c.hiphotos.baidu.com/image/h%3D360/sign=e14b1a680db30f242a9aea05f894d192/a8014c086e061d9590b9863179f40ad162d9ca38.jpg",
				"美女哦~", "猜猜她是谁", "111"));
		a.add(new TicketCell(
				"http://g.hiphotos.baidu.com/image/h%3D360/sign=02f1abd0f21f3a2945c8d3c8a924bce3/fd039245d688d43f09319bfc7f1ed21b0ef43b27.jpg",
				"美女哦~", "猜猜她是谁", "1111"));
	}

	private void initEvent() {

		gv_ticket.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(EntranceTicketActivity.this,
						TicketInfoActivity.class);
				startActivity(intent);
			}
		});
		gv_ticket.setAdapter(adapter);

		adapter.addUpdata(a);

	}

	private void initView() {
		asyncImageLoader = new AsyncImageLoader(this);// 初始化

		findViewById(R.id.img_activity_entrance_ticket_search)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(EntranceTicketActivity.this,
								SearchActivity.class);
						startActivity(intent);
					}
				});

		initCell();

		gv_ticket = (GridView) findViewById(R.id.gv_ticket);
		gv_ticket.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new McxBaseAdapter<TicketCell>(EntranceTicketActivity.this,
				R.layout.activity_ticket_gridview_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				TicketCell data = getItem(position);
				ImageView image = (ImageView) listCell
						.findViewById(R.id.img_ticket_buy);

				String ur = IfHttpToStart.initUr(data.getImg(), "100", "100");
				// 开始加载图片
				image.setTag(ur);
				Bitmap cachedImage = asyncImageLoader.loadBitmap(ur,
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
