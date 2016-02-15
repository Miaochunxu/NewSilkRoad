package com.fangshuoit.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.model.PlaceId;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ChangePlaceActivity extends Activity {

	@SuppressWarnings("unused")
	private LinearLayout ly_change_place;

	private TextView tv_changeText;

	private McxBaseAdapter<PlaceId> adapter;

	private List<PlaceId> place;// jieshou

	private GridView gv_change_place;

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_place);

		initView();

		initEvent();

	}

	private void initEvent() {
		tv_changeText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

		// 获取地点
		analysisJsonChangePlace();
	}

	private void initView() {

		mAbHttpUtil = AbHttpUtil.getInstance(this);// 初始化解析json

		ly_change_place = (LinearLayout) findViewById(R.id.ly_activity_change_place);
		tv_changeText = (TextView) findViewById(R.id.tv_activity_change_place);
		gv_change_place = (GridView) findViewById(R.id.gv_activity_change_place);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		this.overridePendingTransition(R.anim.activity_close_scale, 0);
		return true;
	}

	/**
	 * 获取地点
	 * 
	 * @author miaochunxu
	 */
	public void analysisJsonChangePlace() {
		String url = SysConstants.SERVER + SysConstants.CHANGE_PLACE;
		AbRequestParams params = new AbRequestParams();
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String placed = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");

				if (code.equals("0")) {
					if (!(placed == null || placed.equals("") || placed
							.equals("null"))) {
						Gson gson = new Gson();
						place = gson.fromJson(placed,
								new TypeToken<List<PlaceId>>() {
								}.getType());

						adapter = new McxBaseAdapter<PlaceId>(
								ChangePlaceActivity.this,
								R.layout.activity_change_place_gv_cell) {

							@Override
							protected void initListCell(int position,
									View listCell, ViewGroup parent) {
								PlaceId data = getItem(position);

								TextView tv = (TextView) listCell
										.findViewById(R.id.tv_activity_change_place_place);
								tv.setText(data.getName());
							}
						};

						// gridView监听事件
						gv_change_place
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										PlaceId a = (PlaceId) parent
												.getItemAtPosition(position);
										LoginUtil.remberPlace(a.getName());
										LoginUtil.remberPlaceId(a.getId());
										finish();
									}
								});

						// 绑定适配器
						gv_change_place.setAdapter(adapter);

						adapter.addUpdata(place);
					}
				} else if (code.equals("-1")) {
					AbToastUtil.showToast(ChangePlaceActivity.this, R.string._faile);
					finish();
				}
			}

			@Override
			public void onStart() {

			}

			@Override
			public void onFinish() {
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
			}

		});

	}
}
