package com.fangshuoit.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;

public class FixPasswActivity extends BaseActivity {

	private EditText edt_new, edt_again, edt_begi_passw;
	private Button ok;
	private String before_password, new_password, again_password;
	private String useid;
	private ProgressDialog progressDialog;
	private AbHttpUtil mAbHttpUtil = null;

	private ImageView img_back;

	@Override
	@SuppressLint({ "HandlerLeak", "NewApi" })
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fix_pasw);
		initView();
		initEvent();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_activity_fixpaws_back);

		mAbHttpUtil = AbHttpUtil.getInstance(this);
		progressDialog = new ProgressDialog(this);

		edt_again = (EditText) findViewById(R.id.edt_again);
		edt_new = (EditText) findViewById(R.id.edt_please);
		edt_begi_passw = (EditText) findViewById(R.id.edt_begi_passw);
		ok = (Button) findViewById(R.id.ok);
	}

	private void initEvent() {

		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				before_password = edt_begi_passw.getText().toString().trim();
				new_password = edt_new.getText().toString().trim();
				again_password = edt_again.getText().toString().trim();
				useid = LoginUtil.get_UserId();
				if (((!(before_password.length() >= 6)) || (!(before_password
						.length() <= 13)))
						|| (!(new_password.length() >= 6))
						|| (!(new_password.length() <= 13))
						|| (!(again_password.length() >= 6))
						|| (!(again_password.length() <= 13))) {
					Toast.makeText(FixPasswActivity.this,
							R.string._fix_passw_activity_tv_one,
							Toast.LENGTH_LONG).show();
					edt_begi_passw.setText(null);
					edt_new.setText(null);
					edt_again.setText(null);

				} else if (before_password.equals(new_password)
						|| before_password.equals(again_password)) {
					Toast.makeText(FixPasswActivity.this,
							R.string._fix_passw_activity_tv_two,
							Toast.LENGTH_LONG).show();
				} else if (!new_password.equals(again_password)) {
					Toast.makeText(FixPasswActivity.this,
							R.string._fix_passw_activity_tv_three,
							Toast.LENGTH_LONG).show();
					edt_again.setText(null);

				} else {
					submitFixPassWordData();
				}
			}
		});
	}

	/**
	 * 修改密码，提交到服务器
	 */
	private void submitFixPassWordData() {
		String url = SysConstants.SERVER + SysConstants.FIXPASSWORD;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("user.Id", useid);// 用户ID
			params.put("user.loginPassword", before_password);// 原密码
			params.put("idList", new_password);// 新密码
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int statusCode, String content) {
				String code = JSONUtils.getString(content, "code", "");
				if (code.equals("0")) {
					AbToastUtil.showToast(FixPasswActivity.this,
							R.string._fix_passw_activity_tv_fix_off);
					finish();
				} else if (code.equals("-1")) {
					AbToastUtil.showToast(FixPasswActivity.this,
							R.string._fix_passw_activity_tv_fix_on);
				}
			}

			// 开始执行前
			@Override
			public void onStart() {
				progressDialog
						.setMessage(getString(R.string._fix_passw_activity_tv_wait));
				progressDialog.show();
			}

			// 操作失败
			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				AbToastUtil.showToast(FixPasswActivity.this,
						R.string._fix_passw_activity_tv_fix_on_chance);
			}

			// 操作结束
			public void onFinish() {
				progressDialog.cancel();
			};
		});
	}
}
