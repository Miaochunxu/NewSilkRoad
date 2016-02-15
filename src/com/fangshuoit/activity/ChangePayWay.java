package com.fangshuoit.activity;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fangshuoit.UnionPay.BaseActivity;
import com.fangshuoit.newsilkroad.R;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

public class ChangePayWay extends BaseActivity {

	// private LinearLayout ly_pay_yinlian;

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_change_payway);
	//
	// }
	//
	// private void initEvent() {
	// ly_pay_yinlian.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	//
	// }
	// });
	// }
	//
	// private void initView() {
	// ly_pay_yinlian = (LinearLayout)
	// findViewById(R.id.ly_activity_change_payway);
	// findViewById(R.id.img_activity_change_payway_back).setOnClickListener(
	// new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// finish();
	// }
	// });
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finish();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
		UPPayAssistEx.startPayByJAR(activity, PayActivity.class, null, null,
				tn, mode);
	}

	@Override
	public void updateTextView(TextView tv) {
		String txt = "接入指南：\n1:拷贝sdk目录下的UPPayAssistEx.jar到libs目录下\n"
				+ "2:根据需要拷贝sdk/jar/data.bin（或sdkPro/jar/data.bin）至工程的res/drawable目录下\n"
				+ "3:根据需要拷贝sdk/jar/XXX/XXX.so（或sdkPro/jar/XXX/XXX.so）libs目录下\n"
				+ "4:根据需要拷贝sdk/jar/UPPayPluginEx.jar（或sdkPro/jar/UPPayPluginExPro.jar）到工程的libs目录下\n"
				+ "5:获取tn后通过UPPayAssistEx.startPayByJar(...)方法调用控件";
		tv.setText(txt);
		tv.setVisibility(View.GONE);

		findViewById(R.id.img_activity_change_payway_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

	}

}
