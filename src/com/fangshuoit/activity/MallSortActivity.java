package com.fangshuoit.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.model.MallSortCell;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.fangshuoit.tool.ToastUtils;
import com.fangshuoit.view.CustomProgressDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: MallSortActivity
 * @Description: TODO 商城物品分类
 * @author 方硕IT 缪春旭
 * @date 2015-5-20 下午3:38:57
 * 
 */
public class MallSortActivity extends Activity implements OnClickListener {

	private ListView listView;

	private McxBaseAdapter<MallSortCell> adapter;

	private List<MallSortCell> cells;

	private ImageView img_shopping_car, img_back;

	private AbHttpUtil mAbHttpUtil = null;// 解析json
	private CustomProgressDialog mprogressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_mall_sort);

		initView();

		initEvent();

	}

	private void initEvent() {

		img_back.setOnClickListener(this);
		img_shopping_car.setOnClickListener(this);

		// mprogressDialog.show();
		// try {
		analysisJsonMallSort();
		// } catch (Exception e) {
		// mprogressDialog.cancel();
		// ToastUtils.show(getApplicationContext(), "获取分类失败");
		// }

	}

	private void initView() {

		mAbHttpUtil = AbHttpUtil.getInstance(MallSortActivity.this);// 初始化解析json
		mprogressDialog = CustomProgressDialog
				.createDialog(MallSortActivity.this);

		listView = (ListView) findViewById(R.id.lv_activity_mall_sort);

		img_shopping_car = (ImageView) findViewById(R.id.img_activity_mall_sort_shopping_car);
		img_back = (ImageView) findViewById(R.id.img_activity_mall_sort_back);

	}

	private void initAdapter() {
		adapter = new McxBaseAdapter<MallSortCell>(MallSortActivity.this,
				R.layout.activity_mall_sort_listview_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				MallSortCell data = getItem(position);

				View view = listCell
						.findViewById(R.id.view_activity_mall_sort_listview_cell);
				view.setBackgroundColor(changeColor(position));

				TextView tv = (TextView) listCell
						.findViewById(R.id.tv_activity_mall_sort_listview_cell);
				tv.setText(data.getName());
			}

		};

		adapter.addUpdata(cells);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MallSortActivity.this,
						SortGoodsActivity.class);
				intent.putExtra("Title", cells.get(position).getName());
				intent.putExtra("id", cells.get(position).getId());
				startActivity(intent);
			}
		});

	}

	private int changeColor(int a) {
		int color = a;
		if (a < 8) {
			color = a;
		} else if (a >= 8 && a < 15) {
			color = a - 7;
		} else {
			color = a - 15;
		}
		if (color == 0) {
			return 0xff368ff7;
		} else if (color == 1) {
			return 0xff0fc4d9;
		} else if (color == 2) {
			return 0xff84d018;
		} else if (color == 3) {
			return 0xfff3b216;
		} else if (color == 4) {
			return 0xfffe864a;
		} else if (color == 5) {
			return 0xfffe667b;
		} else if (color == 6) {
			return 0xff9456f5;
		} else if (color == 7) {
			return 0xff7678ef;
		}
		return 0xfff048de;
	}

	private void analysisJsonMallSort() {
		String url = SysConstants.SERVER + SysConstants.MALLSORT;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", LoginUtil.get_language() + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {

				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				// String message = JSONUtils.getString(content, "message", "");

				if (code.equals("0")) {
					if (!(body == "" || body == null)) {
						Gson gson = new Gson();
						cells = gson.fromJson(body,
								new TypeToken<List<MallSortCell>>() {
								}.getType());
						System.out.println("sfsadsadas="
								+ cells.get(0).getName());
						initAdapter();
					}
				} else {
					mprogressDialog.cancel();
					ToastUtils.show(getApplicationContext(),
							R.string._every_good_activity_tv_one);
				}
			}

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				mprogressDialog.cancel();
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_activity_mall_sort_shopping_car:

			Intent intent = new Intent(MallSortActivity.this,
					ShoppingCarActivity.class);
			startActivity(intent);
			activityTo();

			break;
		case R.id.img_activity_mall_sort_back:
			finish();

			break;

		default:
			break;
		}
	}

	/**
	 * @Title: activityTo
	 * @Description: TODO(自定义跳转)
	 * @param 设定文件
	 * @return void
	 */
	private void activityTo() {
		MallSortActivity.this.overridePendingTransition(
				R.anim.activity_open_translate_right,
				R.anim.activity_close_translate_lift);
	}
}
