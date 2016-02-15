package com.fangshuoit.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.model.OrderPayWay;
import com.fangshuoit.model.ShoppingCarListCell;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.fangshuoit.tool.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: OrderActivity
 * @Description: TODO 订单确认
 * @author 方硕IT 缪春旭
 * @date 2015-6-30 下午1:47:41
 * 
 */
public class OrderActivity extends Activity implements OnClickListener {

	private ListView listView;

	private McxBaseAdapter<ShoppingCarListCell> adapter;

	private ImageView img_back;

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private ProgressDialog progressDialog;

	private TextView tv_name, tv_phone, tv_adress, tv_toOrder, tv_money;

	private Intent intent01;

	private List<OrderPayWay> listPayWay;

	private ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		initView();

		initEvent();
	}

	private void initEvent() {
		img_back.setOnClickListener(this);
		tv_toOrder.setOnClickListener(this);
	}

	@SuppressLint("InflateParams")
	private void initView() {

		intent01 = getIntent();

		mAbHttpUtil = AbHttpUtil.getInstance(OrderActivity.this);// 初始化解析json
		progressDialog = new ProgressDialog(OrderActivity.this);

		imageLoader = new ImageLoader(OrderActivity.this, true);

		img_back = (ImageView) findViewById(R.id.img_activity_order_back);

		listView = (ListView) findViewById(R.id.lv_order);

		listView.addHeaderView(LayoutInflater.from(this).inflate(
				R.layout.header_listview_order, null));

		tv_toOrder = (TextView) findViewById(R.id.tv_activity_order_inorder);
		tv_money = (TextView) findViewById(R.id.tv_activity_allmomey);

		tv_name = (TextView) listView
				.findViewById(R.id.tv_header_list_order_name);
		tv_phone = (TextView) listView
				.findViewById(R.id.tv_header_list_order_phone);
		tv_adress = (TextView) listView
				.findViewById(R.id.tv_header_list_order_adress);

		initapapter();

	}

	private void initapapter() {
		adapter = new McxBaseAdapter<ShoppingCarListCell>(OrderActivity.this,
				R.layout.activity_shopping_car_listview_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				ShoppingCarListCell data = getItem(position);
				CheckBox cbox = (CheckBox) listCell
						.findViewById(R.id.cb_activity_shopping_car_listview_cell);
				cbox.setVisibility(View.GONE);

				ImageView img = (ImageView) listCell
						.findViewById(R.id.img_activity_shopping_car_listview_cell);

				// 完成界面的商品数量
				TextView tv_number = (TextView) listCell
						.findViewById(R.id.tv_activity_shopping_car_listview_cell_number);

				TextView tv_title = (TextView) listCell
						.findViewById(R.id.tv_activity_shopping_car_listview_cell_title);
				TextView tv_context = (TextView) listCell
						.findViewById(R.id.tv_activity_shopping_car_listview_cell_type);
				TextView tv_attribute = (TextView) listCell
						.findViewById(R.id.tv_activity_shopping_car_listview_cell_attribute);
				TextView tv_money = (TextView) listCell
						.findViewById(R.id.tv_activity_shopping_car_listview_cell_money);

				tv_title.setText(data.getName());
				tv_context.setText(data.getJians());
				tv_attribute.setText(R.string._order_activity_goods_type
						+ data.getAttrList());
				tv_money.setText(data.getPrice());
				tv_number.setText(data.getGoodNo());

				String imgurl = IfHttpToStart.initUr(data.getLogo(), "150",
						"150");
				// 开始加载图片
				imageLoader.DisplayImage(imgurl, img);
			}
		};

		listView.setAdapter(adapter);

		adapter.addUpdata(TourConstants.shoppingcarList);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					Intent intent = new Intent(getApplicationContext(),
							ChangeAdressActivity.class);
					startActivity(intent);
				} else {

				}
			}
		});
	}

	/**
	 * 
	 * @Title: analysisJsonAddOrder
	 * @Description: TODO(订单添加)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonAddOrder() {
		String url = SysConstants.SERVER + SysConstants.ADDORDER;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", LoginUtil.get_UserId());
			params.put("idList", intent01.getStringExtra("goodId"));
			params.put("addessId", LoginUtil.get_Adress_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String body = JSONUtils.getString(content, "body", "");
				if (code.equals("0")) {
					Toast.makeText(OrderActivity.this,
							R.string._order_activity_goods_place_off,
							Toast.LENGTH_SHORT).show();

					Gson gson = new Gson();
					listPayWay = gson.fromJson(body,
							new TypeToken<List<OrderPayWay>>() {
							}.getType());
					TourConstants.payOrderId = listPayWay.get(0)
							.getReservation().toString();
					TourConstants.OrderId = listPayWay.get(0).getId();
					Intent intent = new Intent(OrderActivity.this,
							ChangePayWay.class);
					startActivity(intent);
					finish();
				} else {
					ToastUtils.show(getApplicationContext(),
							R.string._order_activity_goods_place_on);
					progressDialog.cancel();
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
			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_activity_order_back:
			finish();
			break;
		case R.id.tv_activity_order_inorder:

			if (tv_name.getText() == "" || tv_name.getText() == null
					|| tv_phone.getText() == "" || tv_phone.getText() == null
					|| tv_adress.getText() == "" || tv_adress.getText() == null) {
				Toast.makeText(getApplicationContext(),
						R.string._order_activity_place_adress,
						Toast.LENGTH_SHORT).show();
			} else {
				progressDialog.show();
				System.out.println("intent01.getStringExtra(FactivityFrom)="
						+ intent01.getStringExtra("activityFrom"));
				try {
					if (intent01.getStringExtra("activityFrom")
							.equals("buynow")) {
						analysisJsonToShoppingCar(LoginUtil.get_UserId(),
								intent01.getStringExtra("goodIdList"),
								intent01.getStringExtra("number"));
					} else {
						// 生成订单
						analysisJsonAddOrder();
					}
				} catch (Exception e) {
					progressDialog.cancel();
					ToastUtils.show(getApplicationContext(),
							R.string._order_activity_goods_place_on);
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		tv_name.setText(LoginUtil.get_Adress_name());
		tv_phone.setText(LoginUtil.get_Adress_phone());
		tv_adress.setText(LoginUtil.get_Adress_adress());
		tv_money.setText(intent01.getStringExtra("allMoney"));
		super.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finish();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/**
	 * 
	 * @Title:analysisJsonToShoppingCar
	 * @Description:TODO 加入购物车
	 * @param @param tid
	 * @param @param idList
	 * @return void
	 * @throwsTo
	 */

	private void analysisJsonToShoppingCar(String tid, String idList,
			String number) {
		String url = SysConstants.SERVER + SysConstants.SHOPPINGCAR;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", tid);
			params.put("idList", idList);
			params.put("num", number + "");
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
						analysisJsonAddOrder();
					} else {
					}
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
