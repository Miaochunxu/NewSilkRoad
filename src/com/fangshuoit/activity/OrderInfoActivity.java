package com.fangshuoit.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.model.Adress;
import com.fangshuoit.model.LogisticsCell;
import com.fangshuoit.model.MyOrderCell;
import com.fangshuoit.model.OrderInfo;
import com.fangshuoit.model.OrderInfoBody;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.fangshuoit.view.MultiListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: OrderInfoActivity
 * @Description: TODO 订单详情
 * @author 方硕IT 缪春旭
 * @date 2015-7-8 上午9:32:55
 * 
 */
public class OrderInfoActivity extends Activity {

	private Intent intent;
	private AbHttpUtil mAbHttpUtil = null;// 解析json
	private ProgressDialog progressDialog;

	private OrderInfo info;

	private List<OrderInfoBody> lista;

	private Adress adressInfo;

	private LogisticsCell logisticsCell;

	private TextView tv_order_state, tv_order_id, tv_adress_name,
			tv_adress_phone, tv_adress, tv_mall_yunfei, tv_mall_money,
			tv_maijiainfo, tv_name_from_pay, tv_name_from_pay_title;

	private TextView tv_pay, tv_ok, tv_logistics;

	private String htmlUrl = "";

	private String stateOut = "0";

	private MultiListView listview;

	private McxBaseAdapter<MyOrderCell> adapter;

	private List<MyOrderCell> cells;

	private LinearLayout ly_bottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_info);

		initView();

		initEvent();
	}

	private void initEvent() {

		findViewById(R.id.img_activity_order_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

		// progressDialog.show();
		// try {
		// analysisJsonShowOrderOne();
		// } catch (Exception e) {
		// progressDialog.cancel();
		// }

		// 查看物流
		tv_logistics.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				progressDialog.show();
				if (stateOut.equals("1")) {
					try {
						analysisJsonShowLogistis();
					} catch (Exception e) {
						progressDialog.cancel();
						AbToastUtil.showToast(OrderInfoActivity.this, "error");
					}
				} else {
					progressDialog.cancel();
					AbToastUtil.showToast(
							OrderInfoActivity.this,
							getResources().getString(
									R.string._information_logistics_null));
					tv_logistics.setEnabled(false);
				}
			}
		});

		// 确定收货
		tv_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				progressDialog.show();
				try {
					analysisJsonOrderOk();
				} catch (Exception e) {
					progressDialog.cancel();
					AbToastUtil.showToast(
							OrderInfoActivity.this,
							getResources().getString(
									R.string._information_order_no));
				}
			}
		});

		// // 立即评价
		// tv_comment.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// if (et_comment.getText().equals("")
		// || et_comment.getText() == null) {
		// AbToastUtil.showToast(OrderInfoActivity.this,
		// getResources().getString(R.string._comments_some));
		// } else {
		// progressDialog.show();
		// try {
		// analysisJsonOrderComment();
		// } catch (Exception e) {
		// progressDialog.cancel();
		// AbToastUtil.showToast(
		// OrderInfoActivity.this,
		// getResources().getString(
		// R.string._information_order_no));
		// }
		// }
		// }
		// });
	}

	private void initView() {
		mAbHttpUtil = AbHttpUtil.getInstance(OrderInfoActivity.this);//
		// 初始化解析json
		progressDialog = new ProgressDialog(OrderInfoActivity.this);
		progressDialog.setCanceledOnTouchOutside(false);
		intent = getIntent();

		tv_order_state = (TextView) findViewById(R.id.tv_activity_orderinfo_order_type);
		tv_order_id = (TextView) findViewById(R.id.tv_activity_orderinfo_order_number);
		tv_adress_name = (TextView) findViewById(R.id.tv_activity_orderinfo_adress_name);
		tv_adress_phone = (TextView) findViewById(R.id.tv_activity_orderinfo_adress_phone);
		tv_adress = (TextView) findViewById(R.id.tv_activity_orderinfo_adress_adress);
		tv_mall_yunfei = (TextView) findViewById(R.id.tv_activity_orderinfo_mall_yunfei);
		tv_mall_money = (TextView) findViewById(R.id.tv_activity_orderinfo_mall_money);
		tv_maijiainfo = (TextView) findViewById(R.id.tv_activity_orderinfo_maijiainfo);
		tv_pay = (TextView) findViewById(R.id.tv_activity_order_info_pay);
		tv_ok = (TextView) findViewById(R.id.tv_activity_order_info_quedingshouhuo);
		tv_logistics = (TextView) findViewById(R.id.tv_activity_order_info_logistics);
		tv_name_from_pay = (TextView) findViewById(R.id.tv_activity_orderinfo_mall_name_one);
		tv_name_from_pay_title = (TextView) findViewById(R.id.tv_activity_orderinfo_mall_name_one_title);
		ly_bottom = (LinearLayout) findViewById(R.id.ly_activity_order_info_bottom);
		// tv_comment = (TextView)
		// findViewById(R.id.tv_activity_order_info_comments);

	}

	/**
	 * 
	 * @Title: analysisJsonShowLogistis
	 * @Description: TODO(查看物流)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonShowLogistis() {
		String url = SysConstants.SERVER + SysConstants.LOGISTICS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", logisticsCell.getSumbers());
			params.put("option", logisticsCell.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("tid" + logisticsCell.getSumbers() + ",option="
				+ logisticsCell.getName());
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				if (content.equals("error")) {
					progressDialog.cancel();
					AbToastUtil.showToast(
							OrderInfoActivity.this,
							getResources().getString(
									R.string._information_logistics_null));
				} else {
					htmlUrl = content;
					System.out.println("htmlUrl=" + htmlUrl);
					Intent intent3 = new Intent(OrderInfoActivity.this,
							LogisticsWebActivity.class);
					intent3.putExtra("htmlUrl", htmlUrl);
					startActivity(intent3);
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
	 * @Title: analysisJsonShowOrderOne
	 * @Description: TODO(订单详细信息)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonShowOrderOne() {
		String url = SysConstants.SERVER + SysConstants.SHOWORDERINFO;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", intent.getStringExtra("orderId"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {

				String code = JSONUtils.getString(content, "code", "");
				String body = JSONUtils.getString(content, "body", "");

				// String order = JSONUtils.getString(body, "order", "");
				// String orderbody = JSONUtils.getString(order, "body", "");
				// String outState = JSONUtils
				// .getString(orderbody, "outState", "");
				// String reservation = JSONUtils.getString(orderbody,
				// "reservation", "");
				// String id = JSONUtils.getString(orderbody, "id", "");
				// String DAddress = JSONUtils.getString(body, "DAddress", "");
				// System.out.println("aaaaaaaaaaaaa" + DAddress.toString());
				// String DAddressBody = JSONUtils.getString(DAddress, "body",
				// "");
				// String DExpress = JSONUtils
				// .getString(orderbody, "DExpress", "");
				// String DExpressBody = JSONUtils.getString(DExpress, "body",
				// "");
				// String goodNameList = JSONUtils.getString(orderbody,
				// "goodNameList", "");
				if (code.equals("0")) {
					if (!(body == null || body == "" || body == "[]")) {
						Gson gson = new Gson();
						lista = gson.fromJson(body,
								new TypeToken<List<OrderInfoBody>>() {
								}.getType());

						info = lista.get(0).getOrderInfo();

						adressInfo = info.getAdress();
						if (info.getOutState().equals("1")) {
							logisticsCell = info.getLogisticsCell();
							stateOut = info.getOutState();
						}
						initViewCell(info.getReservation(), info.getId(), info
								.getOutState(), lista.get(0).getGoodNameList());
					} else {
						progressDialog.cancel();
						AbToastUtil.showToast(OrderInfoActivity.this,
								R.string._every_good_activity_tv_one);
					}
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
	 * @Title:analysisJsonOrderComment
	 * @Description:TODO 立即评价
	 * @return void
	 * @throwsTo
	 */
	private void analysisJsonOrderComment() {
		String url = SysConstants.SERVER + SysConstants.ORDERCOMMENTNOW;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			/*
			 * tid(订单编号) comment.praiseNo 商品编号;comment.content 评论内容;
			 * comment.username 用户名称;comment.opId 用户编号; comment.reservation
			 * 商品属性;
			 */
			params.put("tid", intent.getStringExtra("orderId"));
			params.put("comment.praiseNo", intent.getStringExtra("orderId"));
			// params.put("comment.content", et_comment.getText().toString());
			params.put("comment.username", LoginUtil.get_UserName());
			params.put("comment.opId", LoginUtil.get_UserId());
			params.put("comment.reservation", intent.getStringExtra("orderId"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {

				} else {
					progressDialog.cancel();
					AbToastUtil.showToast(OrderInfoActivity.this,
							getResources().getString(R.string._comments_error));
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
	 * @Title:analysisJsonOrderOk
	 * @Description:TODO 确定收货
	 * @param
	 * @return void
	 * @throwsTo
	 */
	private void analysisJsonOrderOk() {
		String url = SysConstants.SERVER + SysConstants.ORDEROK;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", intent.getStringExtra("orderId"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					if (message.equals("success")) {
						AbToastUtil.showToast(
								OrderInfoActivity.this,
								getResources().getString(
										R.string._information_order_ok));
					}
				} else {
					progressDialog.cancel();
					AbToastUtil.showToast(
							OrderInfoActivity.this,
							getResources().getString(
									R.string._information_order_no));
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
	 * @Title: initViewCell
	 * @Description: TODO(数据加载)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initViewCell(final String selId, final String orderid,
			String outstate, String goodNameList) {
		if (info.getPayState().equals("2")) {
			tv_order_state.setText(getResources().getString(
					R.string._comments_yes));
			ly_bottom.setVisibility(View.GONE);

		} else if (info.getPayState().equals("1")) {
			tv_order_state.setText(getResources().getString(
					R.string._trading_success));
			ly_bottom.setVisibility(View.GONE);
			initAdapter();
		} else if (outstate.equals("1")) {
			tv_order_state
					.setText(R.string._order_info_activity_delivery_goods_on);
			tv_ok.setVisibility(View.VISIBLE);
			tv_logistics.setVisibility(View.VISIBLE);
		} else if (info.getPaymentState().equals("1")) {
			tv_order_state.setText(R.string._my_order_activity_pay_off);
		} else if (info.getPaymentState().equals("0")) {
			tv_order_state.setText(R.string._my_order_activity_pay_on);
			tv_pay.setVisibility(View.VISIBLE);
		}

		// 立即支付
		tv_pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TourConstants.payOrderId = selId;
				TourConstants.OrderId = orderid;
				Intent intent2 = new Intent(OrderInfoActivity.this,
						ChangePayWay.class);
				startActivity(intent2);
			}
		});

		tv_order_id.setText(info.getId());

		tv_name_from_pay_title.setVisibility(View.VISIBLE);
		tv_name_from_pay.setText(goodNameList);
		tv_adress_name.setText(adressInfo.getName());
		tv_adress_phone.setText(adressInfo.getPhone());
		tv_adress.setText(adressInfo.getAddress());
		// tv_mall_name.setText(info.getOrderName());
		tv_mall_yunfei.setText("20");
		tv_mall_money.setText("￥" + info.getOrderPrice());
		tv_maijiainfo.setText(info.getMerchantName());
	}

	private void initAdapter() {
		listview = (MultiListView) findViewById(R.id.mulv_activity_order_info);

		Gson gson = new Gson();
		cells = gson.fromJson(intent.getStringExtra("OrderInfo"),
				new TypeToken<List<MyOrderCell>>() {
				}.getType());

		adapter = new McxBaseAdapter<MyOrderCell>(OrderInfoActivity.this,
				R.layout.activity_order_info_list_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				MyOrderCell data = getItem(position);
				TextView tv_mall_name = (TextView) listCell
						.findViewById(R.id.tv_activity_orderinfo_mall_name);
				TextView tv_mall_comment = (TextView) listCell
						.findViewById(R.id.tv_activity_order_info_comments);
				tv_mall_name.setText(data.getGoodName());

				final EditText editText = (EditText) listCell
						.findViewById(R.id.et_activity_order_info_comment);

				tv_mall_comment.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

//						if ((editText.getText().toString() + "") == ""
//								|| (editText.getText().toString() + "") == null) {
//							AbToastUtil.showToast(OrderInfoActivity.this, "");
//						} else {
							AbToastUtil.showToast(OrderInfoActivity.this,
									editText.getText().toString());
//						}
					}
				});
			}
		};

		listview.setAdapter(adapter);
		adapter.addUpdata(cells);
	}

	@Override
	protected void onResume() {
		progressDialog.show();
		try {
			analysisJsonShowOrderOne();
		} catch (Exception e) {
			progressDialog.cancel();
		}
		super.onResume();
	}
}
