package com.fangshuoit.activity;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;

public class LoginActivity extends Activity implements OnClickListener {

	private Button btn_register, btn_login;

	private Bundle bundle;

	private ImageView img_back;

	private EditText et_loginname, et_password;

	private AbHttpUtil mAbHttpUtil = null;

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initView();

		initEvent();

	}

	private void initEvent() {
		btn_register.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		img_back.setOnClickListener(this);
	}

	private void initView() {

		mAbHttpUtil = AbHttpUtil.getInstance(this);

		progressDialog = new ProgressDialog(LoginActivity.this);

		et_loginname = (EditText) findViewById(R.id.et_loginname);
		et_password = (EditText) findViewById(R.id.et_pasw);

		img_back = (ImageView) findViewById(R.id.img_activity_login_back);

		bundle = new Bundle();

		SMSSDK.initSDK(this, "70a2376aee9c", "d5714475bb73fc81322b03b1988c389f");

		btn_register = (Button) findViewById(R.id.btn_login_regist);
		btn_login = (Button) findViewById(R.id.btn_login_login);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login_regist:
			// 打开注册页面
			RegisterPage registerPage = new RegisterPage();
			registerPage.setRegisterCallback(new EventHandler() {
				public void afterEvent(int event, int result, Object data) {
					// 解析注册结果
					if (result == SMSSDK.RESULT_COMPLETE) {
						@SuppressWarnings("unchecked")
						HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
						// String country = (String) phoneMap.get("country");
						String phone = (String) phoneMap.get("phone");

						Intent intent = new Intent(LoginActivity.this,
								RegisterActivity.class);
						bundle.putString("username", phone);
						intent.putExtras(bundle);
						startActivity(intent);

						// 提交用户信息
						// registerUser(country, phone);
					}
				}
			});
			registerPage.show(this);

			break;

		case R.id.btn_login_login:

			if (et_loginname.getText().toString().equals("")
					|| et_loginname.getText().toString().equals(null)) {
				Toast.makeText(LoginActivity.this,
						R.string._login_activity_tv_one, Toast.LENGTH_SHORT)
						.show();
			} else if (et_password.getText().toString().equals("")
					|| et_password.getText().toString().equals(null)) {
				Toast.makeText(LoginActivity.this,
						R.string._login_activity_tv_two, Toast.LENGTH_SHORT)
						.show();
			} else {
				progressDialog
						.setMessage(getString(R.string._login_activity_tv_login_in));
				progressDialog.show();
				try {
					submitLoginData(et_loginname.getText().toString(),
							et_password.getText().toString());
				} catch (Exception e) {
					progressDialog.cancel();
					Toast.makeText(LoginActivity.this,
							R.string._login_activity_tv_login_on,
							Toast.LENGTH_SHORT).show();
				}
			}

			break;
		case R.id.img_activity_login_back:
			this.finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		if (LoginUtil.get_activityFrom().equals("RegisterActivity")) {

			et_loginname.setText(LoginUtil.get_UserName());
			et_password.setText(LoginUtil.get_UserPassw());

			progressDialog
					.setMessage(getString(R.string._login_activity_tv_login_in));
			progressDialog.show();
			try {
				submitLoginData(LoginUtil.get_UserName(),
						LoginUtil.get_UserPassw());
			} catch (Exception e) {
				progressDialog.cancel();
				Toast.makeText(LoginActivity.this,
						R.string._login_activity_tv_login_on,
						Toast.LENGTH_SHORT).show();
			}

		} else {
			try {
				et_loginname.setText(LoginUtil.get_UserName());
			} catch (Exception e) {
			}
		}
		super.onResume();
	}

	/**
	 * @author FangshuoIT 缪春旭
	 * @Title: submitLoginData
	 * @Description: TODO(json解析登陆)
	 * @param 设定文件
	 * @return void 返回类型
	 */
	private void submitLoginData(String user, String pas) {
		String url = SysConstants.SERVER + SysConstants.LOGIN;
		AbRequestParams params = new AbRequestParams();
		try {
			params.put("tempUser.loginName", user);
			params.put("tempUser.loginPassword", pas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int statusCode, String content) {
				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				String userId = JSONUtils.getString(body, "userId", "");
				if (code.equals("0")) {
					LoginUtil.remberIsLogin(true);
					LoginUtil.remberUerId(userId);
					progressDialog.cancel();
					Toast.makeText(LoginActivity.this, R.string.__login_activity_tv_login_off,
							Toast.LENGTH_SHORT).show();
					LoginUtil.rember_activityFrom("LoginActivity");
					finish();

				} else {
					progressDialog.cancel();
					Toast.makeText(LoginActivity.this, R.string._login_activity_tv_login_on,
							Toast.LENGTH_SHORT).show();
				}
			}

			// 开始执行前
			@Override
			public void onStart() {

			}

			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				progressDialog.cancel();
			}

			public void onFinish() {
				progressDialog.cancel();
			};
		});
	}

}
