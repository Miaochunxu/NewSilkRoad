package com.fangshuoit.UnionPay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.fangshuoit.activity.OrderInfoActivity;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;

public abstract class BaseActivity extends Activity implements Callback,
		Runnable {
	public static final String LOG_TAG = "PayDemo";
	private Context mContext = null;
	private int mGoodsIdx = 0;
	private Handler mHandler = null;
	private ProgressDialog mLoadingDialog = null;

	public static final int PLUGIN_VALID = 0;
	public static final int PLUGIN_NOT_INSTALLED = -1;
	public static final int PLUGIN_NEED_UPGRADE = 2;

	/*****************************************************************
	 * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
	 *****************************************************************/
	private final String mMode = "01";

	private static String tnTemp = "";

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private ProgressDialog progressDialog;

	// private static final String TN_URL_01 =
	// "http://202.101.25.178:8080/sim/gettn";

	private final View.OnClickListener mClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.e(LOG_TAG, " " + v.getTag());
			mGoodsIdx = (Integer) v.getTag();

			mLoadingDialog = ProgressDialog.show(mContext, // context
					"", // title
					"" + "", // message
					true); // 进度是否是不确定的，这只和创建进度条有关

			analysisJsonPayWithUnionPay();
			/*************************************************
			 * 步骤1：从网络开始,获取交易流水号即TN
			 ************************************************/
		}
	};

	public abstract void doStartUnionPayPlugin(Activity activity, String tn,
			String mode);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		mHandler = new Handler(this);

		setContentView(R.layout.activity_change_payway);

		mAbHttpUtil = AbHttpUtil.getInstance(BaseActivity.this);

		LinearLayout layout = (LinearLayout) findViewById(R.id.ly_activity_change_payway);

		// Button btn0 = (Button) findViewById(R.id.btn0);
		layout.setTag(0);
		layout.setOnClickListener(mClickListener);

		TextView tv = (TextView) findViewById(R.id.tv_unionpay);
		tv.setTextSize(16);
		updateTextView(tv);
	}

	public abstract void updateTextView(TextView tv);

	@Override
	public boolean handleMessage(Message msg) {
		Log.e(LOG_TAG, " " + "" + msg.obj);
		if (mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}

		String tn = "";
		if (msg.obj == null || ((String) msg.obj).length() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string._union_pay__error_show);
			builder.setMessage(R.string.networkError);
			builder.setNegativeButton(R.string._ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
		} else {
			tn = (String) msg.obj;
			/*************************************************
			 * 步骤2：通过银联工具类启动支付插件
			 ************************************************/
			doStartUnionPayPlugin(this, tn, mMode);
		}

		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*************************************************
		 * 步骤3：处理银联手机支付控件返回的支付结果
		 ************************************************/
		if (data == null) {
			return;
		}
		progressDialog = new ProgressDialog(BaseActivity.this);
		String msg = "";
		/*
		 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
		 */
		String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			msg = getResources().getString(R.string._union_pay_success);
		} else if (str.equalsIgnoreCase("fail")) {
			msg = getResources().getString(R.string._union_pay_fail);
		} else if (str.equalsIgnoreCase("cancel")) {
			msg = getResources().getString(R.string._union_pay_cancel);
		}

		final String a = msg;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string._union_pay__finish_show_title);
		builder.setMessage(msg);
		builder.setInverseBackgroundForced(true);
		// builder.setCustomTitle();
		builder.setNegativeButton(R.string._ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (a.equals(getResources().getString(
								R.string._union_pay_success))) {
							progressDialog
									.setMessage(getString(R.string._union_pay__tiaozhuan));
							progressDialog.show();
							try {
								analysisJsonPayResult();
							} catch (Exception e) {
								progressDialog.cancel();
							}
						}
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	@Override
	public void run() {

		String tn = tnTemp;

		Message msg = mHandler.obtainMessage();
		msg.obj = tn;
		mHandler.sendMessage(msg);
	}

	int startpay(Activity act, String tn, int serverIdentifier) {
		return 0;
	}

	/**
	 * 
	 * @Title: analysisJsonPayWithUnionPay
	 * @Description: TODO(获取tn)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonPayWithUnionPay() {
		String url = SysConstants.SERVER + SysConstants.PAYORDER;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			if (TourConstants.payOrderId != "") {
				params.put("tid", TourConstants.payOrderId);
				params.put("num", "unio");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String tn = JSONUtils.getString(content, "tn", "");
				String success = JSONUtils.getString(content, "success", "");
				if (success.equals("ok")) {
					tnTemp = tn;
				}
			}

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				new Thread(BaseActivity.this).start();
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
			}

		});

	}

	private void analysisJsonPayResult() {
		String url = SysConstants.SERVER + SysConstants.PAYORDERRESULT;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("memo", "success");
			params.put("opid", TourConstants.payOrderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				if (content.equals("Ok")) {
					Intent intent = new Intent(BaseActivity.this,
							OrderInfoActivity.class);
					intent.putExtra("orderId", TourConstants.OrderId);
					LoginUtil.rember_activityFrom("PayActivity");
					startActivity(intent);
					finish();
				} else {
					progressDialog.cancel();
					AbToastUtil.showToast(BaseActivity.this, R.string._faile);
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
}
