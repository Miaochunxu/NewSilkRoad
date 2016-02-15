package com.fangshuoit.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.model.GroupTour;
import com.fangshuoit.newsilkroad.R;

/**
 * 
 * @ClassName: GroupTourActivity
 * @Description: TODO 组团游
 * @author 方硕IT 缪春旭
 * @date 2015-5-8 下午4:44:16
 * 
 */
public class GroupTourActivity extends Activity {

	private GridView gv_group_tour;

	private McxBaseAdapter<GroupTour> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_tour);

		initView();

		initEvent();

	}

	private void initEvent() {

		gv_group_tour.setAdapter(adapter);

		gv_group_tour.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(GroupTourActivity.this,
						GroupTourInfoActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initView() {

		gv_group_tour = (GridView) findViewById(R.id.gv_group_tour);
		gv_group_tour.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new McxBaseAdapter<GroupTour>(GroupTourActivity.this,
				R.layout.activity_group_tour_gv_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				GroupTour data = getItem(position);

				LinearLayout ly_img = (LinearLayout) listCell
						.findViewById(R.id.ly_group_tour_img);
				ly_img.setBackgroundResource(data.getImg());

				ImageView img_head = (ImageView) listCell
						.findViewById(R.id.img_group_tour_headimg);
				img_head.setBackgroundResource(data.getHeadimg());

				TextView tv_title = (TextView) listCell
						.findViewById(R.id.tv_group_tour_title);
				tv_title.setText(data.getTitle());

				TextView tv_username = (TextView) listCell
						.findViewById(R.id.tv_group_tour_username);
				tv_username.setText(data.getUsername());

				TextView tv_number = (TextView) listCell
						.findViewById(R.id.tv_group_tour_number);
				tv_number.setText(data.getNumber());

				TextView tv_money = (TextView) listCell
						.findViewById(R.id.tv_group_tour_money);
				tv_money.setText(data.getMoney());

				TextView tv_time = (TextView) listCell
						.findViewById(R.id.tv_group_tour_time);
				tv_time.setText(data.getTime());
			}
		};

	}
}
