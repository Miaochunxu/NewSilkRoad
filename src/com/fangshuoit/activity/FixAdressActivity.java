package com.fangshuoit.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;

public class FixAdressActivity extends Activity implements OnClickListener {

	private EditText et_sheng, et_diqu, et_jiedao, et_adress, et_name,
			et_phone;

	private String sheng, diqu, jiedao, adress, name, phone;

	private TextView tv_submit;

	private ImageView img_detele;

	private Intent intent;

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private ProgressDialog progressDialog;

	private int pot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fix_adress);

		initView();

		initEvent();
	}

	private void initEvent() {
		img_detele.setOnClickListener(this);
		tv_submit.setOnClickListener(this);
		if (intent.getStringExtra("addorfix") != null
				&& intent.getStringExtra("addorfix").equals("add")) {
			img_detele.setVisibility(View.GONE);
		}
	}

	private void initView() {

		intent = getIntent();

		mAbHttpUtil = AbHttpUtil.getInstance(FixAdressActivity.this);// 初始化解析json
		progressDialog = new ProgressDialog(FixAdressActivity.this);

		et_sheng = (EditText) findViewById(R.id.et_sheng);
		et_diqu = (EditText) findViewById(R.id.et_diqu);
		et_jiedao = (EditText) findViewById(R.id.et_jiedao);
		et_adress = (EditText) findViewById(R.id.et_adress);
		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);

		img_detele = (ImageView) findViewById(R.id.img_activity_fix_adress_detele);

		tv_submit = (TextView) findViewById(R.id.tv_activity_fix_adress_fix);
		fixView();

		findViewById(R.id.img_activity_fix_adress_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
	}

	/**
	 * 
	 * @Title: fixView
	 * @Description: TODO(修改方法，获取前面页面的值)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void fixView() {
		try {
			pot = intent.getExtras().getInt("position");
			if (intent.getStringExtra("addorfix") == null) {
				et_sheng.setText(TourConstants.adresses.get(pot).getProvince());
				et_diqu.setText(TourConstants.adresses.get(pot).getCity());
				et_jiedao.setText(TourConstants.adresses.get(pot).getCounty());
				et_adress.setText(TourConstants.adresses.get(pot).getAddress());
				et_name.setText(TourConstants.adresses.get(pot).getName());
				et_phone.setText(TourConstants.adresses.get(pot).getPhone());
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					R.string._fix_adress_activity_tv_one, Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * 
	 * @Title: analysisJsonAdress
	 * @Description: TODO(添加地址)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonAdress() {
		String url = SysConstants.SERVER + SysConstants.ADDADRESS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("address.userId", LoginUtil.get_UserId());
			params.put("address.province", sheng);
			params.put("address.city", diqu);
			params.put("address.county", jiedao);
			params.put("address.address", adress);
			params.put("address.name", name);
			params.put("address.phone", phone);
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
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								R.string._fix_adress_activity_tv_two,
								Toast.LENGTH_SHORT).show();
						finish();
					} else if (message.equals("find.unexit")) {
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								R.string._fix_adress_activity_tv_three,
								Toast.LENGTH_SHORT).show();
					} else {
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								R.string._fix_adress_activity_tv_foue,
								Toast.LENGTH_SHORT).show();
					}
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

	/**
	 * 
	 * @Title: analysisJsonFixAdress
	 * @Description: TODO(修改收货地址)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonFixAdress() {
		String url = SysConstants.SERVER + SysConstants.FIXADRESS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("address.id", TourConstants.adresses.get(pot).getId());
			params.put("address.userId", LoginUtil.get_UserId());
			params.put("address.province", sheng);
			params.put("address.city", diqu);
			params.put("address.county", jiedao);
			params.put("address.address", adress);
			params.put("address.name", name);
			params.put("address.phone", phone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("TourConstants.adresses.get(pot).getId()="
				+ TourConstants.adresses.get(pot).getId());
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					if (message.equals("success")) {
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								R.string.__fix_adress_activity_tv_five,
								Toast.LENGTH_SHORT).show();
						finish();
					} else if (message.equals("find.unexit")) {
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								"修改失败，不存在当前地址信息", Toast.LENGTH_SHORT).show();
					} else {
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								R.string._fix_adress_activity_tv_foue,
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(getApplicationContext(),
							R.string._fix_adress_activity_tv_seven,
							Toast.LENGTH_SHORT).show();
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

	/**
	 * 
	 * @Title: analysisJsonDeteleAdress
	 * @Description: TODO(删除收货地址)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonDeteleAdress() {
		String url = SysConstants.SERVER + SysConstants.DETELEADRESS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("address.id", TourConstants.adresses.get(pot).getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("德玛西亚=" + TourConstants.adresses.get(pot).getId());
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					if (message.equals("success")) {
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								R.string.__fix_adress_activity_tv_detele,
								Toast.LENGTH_SHORT).show();
						finish();
					} else if (message.equals("find.unexit")) {
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								R.string.__fix_adress_activity_tv_detele_on,
								Toast.LENGTH_SHORT).show();
					} else {
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								R.string._fix_adress_activity_tv_foue,
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(getApplicationContext(),
							R.string._fix_adress_activity_tv_seven,
							Toast.LENGTH_SHORT).show();
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
		sheng = et_sheng.getText().toString().trim();
		diqu = et_diqu.getText().toString().trim();
		jiedao = et_jiedao.getText().toString().trim();
		adress = et_adress.getText().toString().trim();
		name = et_name.getText().toString().trim();
		phone = et_phone.getText().toString().trim();

		switch (v.getId()) {
		case R.id.img_activity_fix_adress_detele:
			progressDialog.show();
			try {
				analysisJsonDeteleAdress();
			} catch (Exception e) {
				progressDialog.cancel();
				Toast.makeText(getApplicationContext(),
						R.string._fix_adress_activity_tv_detele_on_chance,
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.tv_activity_fix_adress_fix:
			if (sheng.equals("") || sheng == null || diqu.equals("")
					|| diqu == null || jiedao.equals("") || jiedao == null
					|| adress.equals("") || adress == null) {
				Toast.makeText(getApplicationContext(),
						R.string._fix_adress_activity_tv_place_in_adress,
						Toast.LENGTH_SHORT).show();
			} else if (name.equals("")) {
				Toast.makeText(getApplicationContext(),
						R.string._fix_adress_activity_tv_who_adress,
						Toast.LENGTH_SHORT).show();
			} else if (phone.equals("")) {
				Toast.makeText(getApplicationContext(),
						R.string._fix_adress_activity_tv_who_phone,
						Toast.LENGTH_SHORT).show();
			} else {
				if (intent.getStringExtra("addorfix") != null
						&& intent.getStringExtra("addorfix").equals("add")) {
					progressDialog
							.setMessage(getString(R.string._fix_adress_activity_tv_wait));
					progressDialog.show();
					try {
						// 执行添加地址方法
						analysisJsonAdress();
					} catch (Exception e) {
						progressDialog.cancel();
					}
				} else {
					progressDialog
							.setMessage(getString(R.string._fix_adress_activity_tv_wait));
					progressDialog.show();
					try {
						// 执行修改方法
						analysisJsonFixAdress();
					} catch (Exception e) {
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								R.string._fix_adress_activity_tv_fix_on,
								Toast.LENGTH_SHORT).show();
					}
				}
			}

			break;
		default:
			break;
		}
	}
}
