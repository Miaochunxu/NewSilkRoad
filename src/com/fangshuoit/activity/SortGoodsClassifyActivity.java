package com.fangshuoit.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.newsilkroad.R;

public class SortGoodsClassifyActivity extends Activity {

	private LinearLayout ly_classify;
	private ListView lv_classify;

	private McxBaseAdapter<String> adapter;
	
	private List<String> a = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sort_goods_classify);

		initView();

		initEvent();

	}

	private void initEvent() {
		ly_classify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		lv_classify.setAdapter(adapter);
		
		adapter.addUpdata(a);
	}

	private void initView() {
		
		initCell();
		
		ly_classify = (LinearLayout) findViewById(R.id.ly_activity_sort_goods_classify);

		lv_classify = (ListView) findViewById(R.id.lv_activity_sort_goods_classify);

		adapter = new McxBaseAdapter<String>(SortGoodsClassifyActivity.this,
				R.layout.activity_sort_goods_classify_listview_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				String a = getItem(position);
				TextView view = (TextView) listCell
						.findViewById(R.id.tv_activity_sort_goods_classify_listview_cell);
				view.setText(a);
			}
		};

	}

	/**
	* @Title: initCell 
	* @Description: TODO(初始化数据) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void initCell() {
		
		a.add("美女001");
		a.add("美女002");
		a.add("美女003");
		a.add("美女004");
		a.add("美女005");
		a.add("美女006");
		a.add("美女007");
		a.add("美女008");
		a.add("美女009");
		a.add("美女010");
		a.add("美女011");
		a.add("美女012");
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		this.overridePendingTransition(0, R.anim.activity_close_translate_b);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			this.overridePendingTransition(0, R.anim.activity_close_translate_b);
		}
		return false;
	}
}
