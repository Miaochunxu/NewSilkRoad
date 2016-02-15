package com.fangshuoit.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.fangshuoit.adapter.McxBaseAdapterNoIf;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.model.MyOrderCell;
import com.fangshuoit.model.MyOrderCellOne;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.fangshuoit.tool.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: MyorderActivity
 * @Description: TODO 我的订单
 * @author 方硕IT 缪春旭
 * @date 2015-7-20 上午9:59:09
 * 
 */
public class MyorderActivity extends FragmentActivity implements
		OnClickListener {

	private View mAvatarView = null;

	private ListView listView;

	private List<MyOrderCellOne> listone;
	// 存储body数据
	private McxBaseAdapterNoIf<MyOrderCellOne> adapter;

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private ProgressDialog progressDialog;

	private TextView tv_noorder;

	private ImageLoader imageLoader;

	private ImageView img_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);

		initView();

		initEvent();
	}

	private void initEvent() {
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initView() {

		img_back = (ImageView) findViewById(R.id.img_activity_myorder_back);

		imageLoader = new ImageLoader(MyorderActivity.this, true);

		mAbHttpUtil = AbHttpUtil.getInstance(MyorderActivity.this);//
		// 初始化解析json
		progressDialog = new ProgressDialog(MyorderActivity.this);

		listView = (ListView) findViewById(R.id.lv_activity_myorder);

		tv_noorder = (TextView) findViewById(R.id.tv_activity_noorder);
		tv_noorder.setVisibility(View.GONE);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return false;
		}
		return false;
	}

	/**
	 * 
	 * @Title: analysisJsonShowOrder
	 * @Description: TODO(拿到body里面的值)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonShowOrder() {
		String url = SysConstants.SERVER + SysConstants.MORDERSHOW;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", LoginUtil.get_UserId());
			params.put("option", "all");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String body = JSONUtils.getString(content, "body", "");
				String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					if (!(body == null || body == "")) {
						Gson gson = new Gson();
						listone = gson.fromJson(body,
								new TypeToken<List<MyOrderCellOne>>() {
								}.getType());
						initAdapter();
					} else {
						progressDialog.cancel();
						AbToastUtil.showToast(MyorderActivity.this,
								R.string._data_error_load);
					}

				} else if (message.equals("null.error!")) {
					listone = new ArrayList<MyOrderCellOne>();
					initAdapter();
					tv_noorder.setVisibility(View.VISIBLE);
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
			}

		});

	}

	/**
	 * 
	 * @Title: analysisJsonDeteleOrder
	 * @Description: TODO(删除订单)
	 * @param @param orderId 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonDeteleOrder(String orderId) {
		String url = SysConstants.SERVER + SysConstants.DETELEORDER;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("order.id", orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					Toast.makeText(MyorderActivity.this,
							R.string._my_order_activity_detele_off,
							Toast.LENGTH_SHORT).show();
					analysisJsonShowOrder();
					AbDialogUtil.removeDialog(mAvatarView);
				} else if (message.equals("find.unexit")) {
					progressDialog.cancel();
					AbDialogUtil.removeDialog(mAvatarView);
					Toast.makeText(MyorderActivity.this,
							R.string._my_order_activity_detele_no_order,
							Toast.LENGTH_SHORT).show();
				} else {
					progressDialog.cancel();
					AbDialogUtil.removeDialog(mAvatarView);
					Toast.makeText(MyorderActivity.this,
							R.string._fix_adress_activity_tv_foue,
							Toast.LENGTH_SHORT).show();
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
				AbDialogUtil.removeDialog(mAvatarView);
				Toast.makeText(MyorderActivity.this,
						R.string._fix_adress_activity_tv_foue,
						Toast.LENGTH_SHORT).show();
			}

		});

	}

	private void initAdapter() {
		adapter = new McxBaseAdapterNoIf<MyOrderCellOne>(MyorderActivity.this,
				R.layout.fragment_myorder_list_cell) {

			@SuppressLint("InflateParams")
			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				MyOrderCellOne dataone = getItem(position);

				// TextView tv_orderid = (TextView) listCell
				// .findViewById(R.id.tv_fragment_myorder_list_no);
				TextView tv_state = (TextView) listCell
						.findViewById(R.id.tv_fragment_myorder_list_state);
				if (dataone.getPayState().equals("2")) {
					tv_state.setText(R.string._comments_yes);
					tv_state.setTextColor(0xff4a4a4a);
				} else if (dataone.getPayState().equals("1")) {
					tv_state.setText(R.string._trading_success);
					tv_state.setTextColor(0xff4a4a4a);
				} else if (dataone.getOutState().equals("1")) {
					tv_state.setText(R.string._order_info_activity_delivery_goods_on);
					tv_state.setTextColor(0xff07d515);
				} else if (dataone.getPaymentState().equals("1")) {
					tv_state.setText(R.string._my_order_activity_pay_off);
					tv_state.setTextColor(0xff07d515);
				} else if (dataone.getPaymentState().equals("0")) {
					tv_state.setText(R.string._my_order_activity_pay_on);
					tv_state.setTextColor(0xfffe0663);
				}
				// tv_orderid.setText(dataone.getOrderId());

				Gson gson = new Gson();
				List<MyOrderCell> listtwo = gson.fromJson(listone.get(position)
						.getOrder(), new TypeToken<List<MyOrderCell>>() {
				}.getType());

				LinearLayout layout = (LinearLayout) listCell
						.findViewById(R.id.ly_fragment_myorder_listcell);

				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				for (int i = 0; i < listtwo.size(); i++) {
					View viewp = LayoutInflater
							.from(listCell.getContext())
							.inflate(
									R.layout.activity_shopping_car_listview_cell,
									null);

					// #########
					CheckBox box = (CheckBox) viewp
							.findViewById(R.id.cb_activity_shopping_car_listview_cell);
					box.setVisibility(View.GONE);

					TextView tv_name = (TextView) viewp
							.findViewById(R.id.tv_activity_shopping_car_listview_cell_title);
					TextView tv_jians = (TextView) viewp
							.findViewById(R.id.tv_activity_shopping_car_listview_cell_type);
					TextView tv_attribute = (TextView) viewp
							.findViewById(R.id.tv_activity_shopping_car_listview_cell_attribute);
					TextView tv_money = (TextView) viewp
							.findViewById(R.id.tv_activity_shopping_car_listview_cell_money);
					TextView tv_no = (TextView) viewp
							.findViewById(R.id.tv_activity_shopping_car_listview_cell_number);

					ImageView img = (ImageView) viewp
							.findViewById(R.id.img_activity_shopping_car_listview_cell);

					tv_name.setText(listtwo.get(i).getGoodName());
					tv_jians.setText(listtwo.get(i).getGoodJians());
					tv_money.setText(listtwo.get(i).getGoodPrice());
					tv_no.setText(listtwo.get(i).getPayNo());
					tv_attribute.setText(listtwo.get(i).getAttributeName());

					String url = IfHttpToStart.initUr(listtwo.get(i)
							.getGoodPhoto(), "80", "80");
					imageLoader.DisplayImage(url, img);
					// ##########
					viewp.setLayoutParams(lp);
					layout.addView(viewp);
				}

			}

		};

		adapter.addUpdata(listone);
		listView.setAdapter(adapter);

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@SuppressLint("InflateParams")
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				LayoutInflater inflater = LayoutInflater
						.from(MyorderActivity.this);// 创建一个View对象,将定义好的布局放入其中
				mAvatarView = inflater.inflate(R.layout.choose_avatar, null);
				Button albumButton = (Button) mAvatarView
						.findViewById(R.id.choose_album);
				albumButton.setText(R.string._my_order_activity_detele_order);

				Button camButton = (Button) mAvatarView
						.findViewById(R.id.choose_cam);

				camButton.setVisibility(View.GONE);
				Button cancelButton = (Button) mAvatarView
						.findViewById(R.id.choose_cancel);
				cancelButton.setVisibility(View.GONE);

				final int pot = position;
				albumButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						progressDialog.show();
						try {
							analysisJsonDeteleOrder(listone.get(pot)
									.getOrderId());
						} catch (Exception e) {
							progressDialog.cancel();
							AbDialogUtil.removeDialog(mAvatarView);
							AbToastUtil
									.showToast(
											MyorderActivity.this,
											getResources()
													.getString(
															R.string._fix_adress_activity_tv_detele_on_chance));
						}
					}
				});
				AbDialogUtil.showDialog(mAvatarView, Gravity.BOTTOM);
				return true;
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MyorderActivity.this,
						OrderInfoActivity.class);
				intent.putExtra("orderId", listone.get(position).getOrderId());
				intent.putExtra("state", listone.get(position).getPayState());
				intent.putExtra("OrderInfo", listone.get(position).getOrder());
				LoginUtil.rember_activityFrom("MyorderActivity");
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void onResume() {
		progressDialog.show();
		try {
			analysisJsonShowOrder();
		} catch (Exception e) {
			progressDialog.cancel();
			ToastUtils.show(getApplicationContext(),
					R.string._every_good_activity_tv_one);
		}
		super.onResume();
	}
}
