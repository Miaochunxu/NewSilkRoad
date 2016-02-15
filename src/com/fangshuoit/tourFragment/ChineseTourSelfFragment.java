package com.fangshuoit.tourFragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.model.TourChineseAndForgien;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.AsyncImageLoader;
import com.fangshuoit.tool.AsyncImageLoader.ImageCallback;

public class ChineseTourSelfFragment extends Fragment {

	private View view;

	private ListView listView;

	AsyncImageLoader asyncImageLoader;// 加载图片
	
	private List<TourChineseAndForgien> cell;

	private McxBaseAdapter<TourChineseAndForgien> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_chinese_tour, container,
				false);

		initView();

		initEvent();

		return view;
	}

	private void initEvent() {
		
		adapter.addUpdata(cell);
		
		listView.setAdapter(adapter);
		
		
	}

	private void initView() {

		//添加测试数据
		initCell();
		
		asyncImageLoader = new AsyncImageLoader(getActivity());// 初始化

		listView = (ListView) view.findViewById(R.id.lv_fragment_chinese_tour);

		adapter = new McxBaseAdapter<TourChineseAndForgien>(getActivity(),
				R.layout.activity_ticket_gridview_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				TourChineseAndForgien data = getItem(position);

				ImageView image = (ImageView) listCell
						.findViewById(R.id.img_ticket_buy);
				// 开始加载图片
				image.setTag(data.getImg());
				Bitmap cachedImage = asyncImageLoader.loadBitmap(data.getImg(),
						new ImageCallback() {
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
					image.setImageResource(R.drawable.image_loading);
				} else {
					image.setImageBitmap(cachedImage);
				}

				TextView tv_big = (TextView) listCell
						.findViewById(R.id.tv_tivket_buy_big);
				tv_big.setText(data.getTitle());
				TextView tv_small = (TextView) listCell
						.findViewById(R.id.tv_tivket_buy_small);
				tv_small.setText(data.getContext());
				TextView tv_price = (TextView) listCell
						.findViewById(R.id.tv_tivket_buy_price);
				tv_price.setText(data.getMoney());
			}
		};

	}

	private void initCell() {
		cell = new ArrayList<TourChineseAndForgien>();
		
		cell.add(new TourChineseAndForgien("", "新疆6日游", "自助式入住酒店，可选择跟团，纯玩", "1245"));
		cell.add(new TourChineseAndForgien("", "新疆6日游", "自助式入住酒店，可选择跟团，纯玩", "1245"));
		cell.add(new TourChineseAndForgien("", "新疆6日游", "自助式入住酒店，可选择跟团，纯玩", "1245"));
		cell.add(new TourChineseAndForgien("", "新疆6日游", "自助式入住酒店，可选择跟团，纯玩", "1245"));
		cell.add(new TourChineseAndForgien("", "新疆6日游", "自助式入住酒店，可选择跟团，纯玩", "1245"));
		cell.add(new TourChineseAndForgien("", "新疆6日游", "自助式入住酒店，可选择跟团，纯玩", "1245"));
		cell.add(new TourChineseAndForgien("", "新疆6日游", "自助式入住酒店，可选择跟团，纯玩", "1245"));
	}
}
