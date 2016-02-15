package com.fangshuoit.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.model.Adress;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ChangeAdressActivity extends Activity implements OnClickListener {

	private ListView listView;

	private McxBaseAdapter<Adress> adapter;

	private TextView tv_fix, tv_top;
	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_adress);

		initView();

		initEvent();
	}

	private void initEvent() {
		tv_fix.setOnClickListener(this);

		findViewById(R.id.img_activity_change_adress_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
	}

	private void initView() {

		mAbHttpUtil = AbHttpUtil.getInstance(ChangeAdressActivity.this);// 初始化解析json
		progressDialog = new ProgressDialog(ChangeAdressActivity.this);

		tv_fix = (TextView) findViewById(R.id.tv_activity_change_adress_fix);
		tv_top = (TextView) findViewById(R.id.tv_activity_change_adress_top);

		listView = (ListView) findViewById(R.id.lv_activity_change_adress);

	}

	private void initAdapter() {
		adapter = new McxBaseAdapter<Adress>(ChangeAdressActivity.this,
				R.layout.header_listview_order) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				Adress data = getItem(position);
				ImageView img = (ImageView) listCell
						.findViewById(R.id.img_activity_hrader_list_logo);
				ImageView img_2 = (ImageView) listCell
						.findViewById(R.id.img_header_list_order_changeplace);

				if (data.getId().equals(LoginUtil.get_Adress_id())) {
					img.setVisibility(View.VISIBLE);
				} else {
					img.setVisibility(View.GONE);
				}
				img_2.setVisibility(View.INVISIBLE);

				TextView tv_name = (TextView) listCell
						.findViewById(R.id.tv_header_list_order_name);
				TextView tv_phone = (TextView) listCell
						.findViewById(R.id.tv_header_list_order_phone);
				TextView tv_adress = (TextView) listCell
						.findViewById(R.id.tv_header_list_order_adress);

				tv_name.setText(data.getName());
				tv_phone.setText(data.getPhone());
				tv_adress.setText(data.getProvince() + data.getCity()
						+ data.getCounty() + data.getAddress());

			}
		};

		adapter.addUpdata(TourConstants.adresses);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				LoginUtil.rember_Adress_default(
						TourConstants.adresses.get(position).getId(),
						TourConstants.adresses.get(position).getName(),
						TourConstants.adresses.get(position).getPhone(),
						TourConstants.adresses.get(position).getProvince()
								+ TourConstants.adresses.get(position)
										.getCity()
								+ TourConstants.adresses.get(position)
										.getCounty()
								+ TourConstants.adresses.get(position)
										.getAddress());
				finish();
			}
		});
	}

	/**
	 * 
	 * @Title: analysisJsonShowShoppingCar
	 * @Description: TODO(解析收货地址)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonAdress() {
		String url = SysConstants.SERVER + SysConstants.SHOWADRESS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", LoginUtil.get_UserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				if (content != null) {
					tv_fix.setText(R.string._manager_recevice_address);
					Gson gson = new Gson();
					TourConstants.adresses = gson.fromJson(content,
							new TypeToken<List<Adress>>() {
							}.getType());

					initAdapter();
				} else {
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
		case R.id.tv_activity_change_adress_fix:
			Intent intent = new Intent(getApplicationContext(),
					AdressManageActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		tv_top.setText(R.string._select_address);
		tv_fix.setText(R.string._manager_recevice_address);
		try {
			progressDialog.show();
			analysisJsonAdress();
		} catch (Exception e) {
			progressDialog.cancel();
			Toast.makeText(getApplicationContext(), R.string._check_internet,
					Toast.LENGTH_SHORT).show();
		}
		super.onResume();
	}
}
