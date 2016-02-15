package com.fangshuoit.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;

/**
 * @ClassName: RegisterActivity
 * @Description: TODO 用户注册
 * @author 方硕IT 缪春旭
 * @date 2015-5-25 下午1:52:38
 * 
 */
public class RegisterActivity extends Activity {

	private EditText et_username, et_nickname, et_pasw, et_paswagain;

	private Button register;

	private CheckBox checkBox;

	private AbHttpUtil mAbHttpUtil = null;

	private ProgressDialog progressDialog;

	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initView();

		initEvent();

	}

	private void initEvent() {

		et_username.setText(bundle.getString("username"));

		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String username = bundle.getString("username");
				String nickname = et_nickname.getText().toString().trim();
				String pasw = et_pasw.getText().toString().trim();
				String paswagain = et_paswagain.getText().toString().trim();

				if (nickname.equals("") || nickname.equals(null)) {
					Toast.makeText(RegisterActivity.this,
							R.string._register_activity_nickname_no_null,
							Toast.LENGTH_SHORT).show();
				} else if (pasw.equals("") || pasw.equals(null)) {
					Toast.makeText(RegisterActivity.this,
							R.string._register_activity_pasw_no_null,
							Toast.LENGTH_SHORT).show();
				} else if (pasw.length() < 6 || pasw.length() > 13) {
					Toast.makeText(RegisterActivity.this,
							R.string._register_activity_pasw_length,
							Toast.LENGTH_SHORT).show();
				} else if (paswagain.equals("") || paswagain.equals(null)) {
					Toast.makeText(RegisterActivity.this,
							R.string._register_activity_place_pasw_again,
							Toast.LENGTH_SHORT).show();
				} else if (!pasw.equals(paswagain)) {
					Toast.makeText(RegisterActivity.this,
							R.string._register_activity_place_pasw_no_like,
							Toast.LENGTH_SHORT).show();
					et_pasw.setText("");
					et_paswagain.setText("");
				} else if (!checkBox.isChecked()) {
					Toast.makeText(RegisterActivity.this,
							R.string._register_activity_rules,
							Toast.LENGTH_SHORT).show();
				} else {
					LoginUtil.rember_nickname(nickname);
					LoginUtil.rember_Count(username, paswagain);
					submitRegisterData(username, paswagain, nickname);
				}
			}

		});
	}

	private void initView() {

		bundle = getIntent().getExtras();

		mAbHttpUtil = AbHttpUtil.getInstance(this);

		progressDialog = new ProgressDialog(RegisterActivity.this);

		et_username = (EditText) findViewById(R.id.et_register_name);
		et_nickname = (EditText) findViewById(R.id.et_register_nickname);
		et_pasw = (EditText) findViewById(R.id.et_register_pasw);
		et_paswagain = (EditText) findViewById(R.id.et_register_paswagain);

		register = (Button) findViewById(R.id.btn_register_regist);

		checkBox = (CheckBox) findViewById(R.id.chb_register_read);
	}

	/**
	 * @author 缪春旭
	 * @Title: submitRegisterData
	 * @Description: TODO(用户注册)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void submitRegisterData(String username, String pas, String nick) {
		String url = SysConstants.SERVER + SysConstants.REGISTER;
		AbRequestParams params = new AbRequestParams();
		try {
			params.put("user.loginName", username);
			params.put("user.loginPassword", pas);
			params.put("tempVo.nickName", nick);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int statusCode, String content) {
				// String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					progressDialog.cancel();
					Toast.makeText(RegisterActivity.this,
							R.string._register_activity_register_yes,
							Toast.LENGTH_SHORT).show();
					LoginUtil.rember_activityFrom("RegisterActivity");
					finish();

				} else if (message.equals("loginName")) {
					progressDialog.cancel();
					Toast.makeText(RegisterActivity.this,
							R.string._register_activity_register_no_one,
							Toast.LENGTH_SHORT).show();
					LoginUtil.emptyData();
				} else {
					progressDialog.cancel();
					Toast.makeText(RegisterActivity.this,
							R.string._register_activity_tv_one,
							Toast.LENGTH_SHORT).show();
					LoginUtil.emptyData();
				}
			}

			// 开始执行前
			@Override
			public void onStart() {
				progressDialog
						.setMessage(getString(R.string._register_activity_login_in));
				progressDialog.show();
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
