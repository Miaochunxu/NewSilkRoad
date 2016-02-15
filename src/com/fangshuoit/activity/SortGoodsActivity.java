package com.fangshuoit.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.model.SearchCell;
import com.fangshuoit.model.SortGoodsCell;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.pulltorefresh.library.PullToRefreshBase;
import com.fangshuoit.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.fangshuoit.pulltorefresh.library.PullToRefreshListView;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: SortGoodsActivity
 * @Description: TODO 商品列表
 * @author 方硕IT 缪春旭
 * @date 2015-5-23 下午12:44:49
 * 
 */
public class SortGoodsActivity extends Activity implements OnClickListener {

	private PullToRefreshListView listView;
	private List<SortGoodsCell> dataGD;

	private TextView tv_fenlei, tv_paixu, tv_chandi, tv_title;

	private McxBaseAdapter<SortGoodsCell> adapter;

	private ImageView img_shopping_car, img_back;

	private Intent intent, intent1;

	private ImageLoader imageLoader;

	private AbHttpUtil mAbHttpUtil = null;// 解析json
	private ProgressDialog progressDialog;

	@SuppressWarnings("unused")
	private Bundle bundle;

	private SearchCell searchCell;

	private int numberForLoad = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sort_goods);

		initView();

		initEvent();
	}

	private void initEvent() {

		img_back.setOnClickListener(this);
		img_shopping_car.setOnClickListener(this);

		tv_fenlei.setOnClickListener(this);
		tv_paixu.setOnClickListener(this);
		tv_chandi.setOnClickListener(this);
		progressDialog.show();
		try {
			if (LoginUtil.get_activityFrom().equals("SearchActivity")) {
				dataGD = new ArrayList<SortGoodsCell>();
				for (int i = 0; i < TourConstants.searchCellList.size(); i++) {
					searchCell = TourConstants.searchCellList.get(i);
					dataGD.add(new SortGoodsCell(searchCell.getId(), searchCell
							.getLogo(), searchCell.getReservation(), searchCell
							.getName(), "0", searchCell.getJians()));
				}
				LoginUtil.rember_activityFrom("");
				
				initApadter(dataGD);
				progressDialog.cancel();
			} else {
				analysisJsonSortGoods(intent1.getStringExtra("id"));
			}
		} catch (Exception e) {
			progressDialog.cancel();
			Toast.makeText(getApplicationContext(),
					R.string._every_good_activity_tv_one, Toast.LENGTH_SHORT)
					.show();
		}
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				Log.e("TAG", "onPullDownToRefresh");
				listView.postDelayed(new Runnable() {

					@Override
					public void run() {
						try {
							// 这里写下拉刷新的任务
							analysisJsonSortGoodsUpdata(intent1
									.getStringExtra("id"));
						} catch (Exception e) {
							Toast.makeText(SortGoodsActivity.this,
									R.string._refurbish_error,
									Toast.LENGTH_SHORT).show();
						}
					}
				}, 1000);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				listView.postDelayed(new Runnable() {
					@Override
					public void run() {
						try {
							numberForLoad += 10;
							// 这里写上拉加载的任务
							analysisJsonSortGoodsLoadMore(
									intent1.getStringExtra("id"), numberForLoad);
						} catch (Exception e) {
							Toast.makeText(SortGoodsActivity.this,
									R.string._refurbish_null,
									Toast.LENGTH_SHORT).show();
						}
					}
				}, 1000);
			}
		});
	}

	private void initView() {

		imageLoader = new ImageLoader(SortGoodsActivity.this, true);

		mAbHttpUtil = AbHttpUtil.getInstance(SortGoodsActivity.this);// 初始化解析json
		progressDialog = new ProgressDialog(SortGoodsActivity.this);
		progressDialog.setCanceledOnTouchOutside(false);
		intent1 = getIntent();

		bundle = new Bundle();

		img_shopping_car = (ImageView) findViewById(R.id.img_activity_sort_goods_shopping_car);
		img_back = (ImageView) findViewById(R.id.img_activity_sort_goods_back);

		tv_fenlei = (TextView) findViewById(R.id.tv_activity_sort_goods_fenlei);
		tv_paixu = (TextView) findViewById(R.id.tv_activity_sort_goods_paixu);
		tv_chandi = (TextView) findViewById(R.id.tv_activity_sort_goods_chandi);
		tv_title = (TextView) findViewById(R.id.tv_activity_sort_goods_title);

		tv_title.setText(intent1.getStringExtra("Title"));

		listView = (PullToRefreshListView) findViewById(R.id.lv_activity_sort_goods);

	}

	/**
	 * 
	 * @Title: analysisJsonTourHomeLYXD
	 * @Description: TODO(商品列表信息显示)
	 * @param @param id 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonSortGoods(String id) {
		String url = SysConstants.SERVER + SysConstants.MALLSORTGOODS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("typeId", id);
			params.put("num", "10");
			params.put("location", "0");
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
					if (!(null == body || body.equals("") || body
							.equals("null"))) {
						// 新建Gson
						Gson gson = new Gson();
						// 获取旅游首页信息
						dataGD = gson.fromJson(body,
								new TypeToken<List<SortGoodsCell>>() {
								}.getType());

						initApadter(dataGD);
					}
				} else {
					progressDialog.cancel();
					AbToastUtil.showToast(SortGoodsActivity.this,
							R.string._data_error_load);
				}
			}

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				progressDialog.cancel();
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				progressDialog.cancel();
				AbToastUtil.showToast(SortGoodsActivity.this,
						R.string._data_error_load);
			}

		});
	}

	/**
	 * 
	 * @Title: analysisJsonSortGoodsUpdata
	 * @Description: TODO(刷新方法)
	 * @param @param id 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonSortGoodsUpdata(String id) {
		String url = SysConstants.SERVER + SysConstants.MALLSORTGOODS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("typeId", id);
			params.put("num", "10");
			params.put("location", "0");
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
					if (!(null == body || body.equals("") || body
							.equals("null"))) {
						// 新建Gson
						Gson gson = new Gson();
						// 获取旅游首页信息
						dataGD = gson.fromJson(body,
								new TypeToken<List<SortGoodsCell>>() {
								}.getType());
						adapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				progressDialog.cancel();

				listView.onRefreshComplete();
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
			}

		});
	}

	/**
	 * 
	 * @Title: analysisJsonSortGoodsLoadMore
	 * @Description: TODO(上拉加载)
	 * @param @param id 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonSortGoodsLoadMore(String id, int number) {
		String url = SysConstants.SERVER + SysConstants.MALLSORTGOODS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("typeId", id);
			params.put("num", number + 10 + "");
			params.put("location", number + "");
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
					if (!(null == body || body.equals("") || body.equals("[]") || body
							.equals("null"))) {
						// 新建Gson
						Gson gson = new Gson();
						// 获取旅游首页信息
						dataGD = gson.fromJson(body,
								new TypeToken<List<SortGoodsCell>>() {
								}.getType());
						adapter.notifyDataSetChanged();
					} else {
						AbToastUtil.showToast(getApplicationContext(),
								R.string._refurbish_null);
						numberForLoad -= 10;
					}
				} else {
					AbToastUtil.showToast(getApplicationContext(),
							R.string._sort_goods_load_error);
					numberForLoad -= 10;
				}
			}

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				progressDialog.cancel();
				listView.onRefreshComplete();
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				numberForLoad -= 10;
			}

		});
	}

	private void initApadter(List<SortGoodsCell> listcell) {
		adapter = new McxBaseAdapter<SortGoodsCell>(SortGoodsActivity.this,
				R.layout.activity_sort_goods_listview_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				SortGoodsCell data = getItem(position);

				ImageView image = (ImageView) listCell
						.findViewById(R.id.img_activity_sort_goods_listview);

				TextView tv_title = (TextView) listCell
						.findViewById(R.id.tv_activity_sort_goods_listview_title);
				TextView tv_money = (TextView) listCell
						.findViewById(R.id.tv_activity_sort_goods_listview_money);
				TextView tv_comment = (TextView) listCell
						.findViewById(R.id.tv_activity_sort_goods_listview_comment);
				TextView tv_title_comment = (TextView) listCell
						.findViewById(R.id.tv_activity_sort_goods_listview_title_comment);

				tv_title.setText(data.getName());
				tv_money.setText(data.getPrice());
				tv_comment.setText(data.getCommentNo());
				tv_title_comment.setText(data.getJians());

				String imgu = IfHttpToStart
						.initUr(data.getLogo(), "220", "200");

				imageLoader.DisplayImage(imgu, image);
			}
		};

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				SortGoodsCell da = (SortGoodsCell) parent
						.getItemAtPosition(position);

				Intent intent = new Intent(SortGoodsActivity.this,
						EveryGoodsActivity.class);
				intent.putExtra("GoodId", da.getId());
				startActivity(intent);
			}
		});
		adapter.addUpdata(listcell);
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_activity_sort_goods_fenlei:

			// 调用销量排序

			// bundle.putString("bottomsort", "fenlei");
			// intent = new Intent(SortGoodsActivity.this,
			// SortGoodsClassifyActivity.class);
			// intent.putExtras(bundle);
			// startActivity(intent);
			// this.overridePendingTransition(R.anim.activity_open_translate_b,
			// 0);
			break;
		case R.id.tv_activity_sort_goods_paixu:

			// 调用价格排序

			// bundle.putString("bottomsort", "paixu");
			// intent = new Intent(SortGoodsActivity.this,
			// SortGoodsClassifyActivity.class);
			// intent.putExtras(bundle);
			// startActivity(intent);
			// this.overridePendingTransition(R.anim.activity_open_translate_b,
			// 0);
			break;
		case R.id.tv_activity_sort_goods_chandi:
			// 产地
			intent = new Intent(SortGoodsActivity.this,
					ChangePlaceActivity.class);
			startActivity(intent);

			// bundle.putString("bottomsort", "chandi");
			// intent = new Intent(SortGoodsActivity.this,
			// SortGoodsClassifyActivity.class);
			// intent.putExtras(bundle);
			// startActivity(intent);
			// this.overridePendingTransition(R.anim.activity_open_translate_b,
			// 0);
			break;
		case R.id.img_activity_sort_goods_shopping_car:

			intent = new Intent(SortGoodsActivity.this,
					ShoppingCarActivity.class);
			startActivity(intent);
			// activityTo();
			break;
		case R.id.img_activity_sort_goods_back:
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
	// private void activityTo() {
	// SortGoodsActivity.this.overridePendingTransition(
	// R.anim.activity_open_translate_right,
	// R.anim.activity_close_translate_lift);
	// }
}
