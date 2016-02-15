package com.fangshuoit.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.fangshuoit.application.FSApplication;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.Tool;

/**
 * 基本Activity类（通用方法）
 * 
 * @project NewSilkRoad
 * @Company 宁夏方硕信息技术有限公司
 * @author 周庆鹏
 * @phone 15009666891
 * @QQ 872123675
 * @Blog http://blog.csdn.net/lovelvyan
 * @date 2015-4-13
 * @time 下午2:14:15
 */

public class BaseActivity extends Activity {

	private ProgressDialog m_progressDialog = null;
	private AlertDialog m_alertdialog;

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissAlertDialog();
		dismissProgressDialog();
		FSApplication.getInstance().delActivity(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FSApplication.getInstance().addActivity(this);
	}

	public void alertDialog(int msgId) {
		alertDialog(msgId, R.string.ok);
	}

	public void alertDialog(String msgStr) {
		alertDialog(msgStr, getString(R.string.ok));
	}

	public void alertDialog(int msgId, int positiveButtonStrId) {
		alertDialog(getString(msgId), getString(positiveButtonStrId));
	}

	public void alertDialog(String msgStr, String positiveButtonStr) {
		if (m_alertdialog != null && m_alertdialog.isShowing()) {
			return;
		}
		m_alertdialog = new AlertDialog.Builder(this)
				.setMessage(msgStr)
				.setPositiveButton(positiveButtonStr,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						}).create();

		if (!this.isFinishing()) {
			m_alertdialog.show();
		}
	}

	public void dismissAlertDialog() {
		if (m_alertdialog != null && m_alertdialog.isShowing()) {
			m_alertdialog.dismiss();
		}
	}

	public void showProgressDialog(int strId) {
		showProgressDialog(getString(strId));
	}

	public void showProgressDialog(String str) {
		if (m_progressDialog != null && m_progressDialog.isShowing()) {
			m_progressDialog.setMessage(str);
			return;
		}
		m_progressDialog = null;
		m_progressDialog = ProgressDialog.show(this, "", str, true, true);
	}

	public void showProgressDialog(int strId, boolean cancelable) {
		showProgressDialog(getString(strId), cancelable);
	}

	public void showProgressDialog(String str, boolean cancelable) {
		if (m_progressDialog != null && m_progressDialog.isShowing()) {
			m_progressDialog.dismiss();
		}
		m_progressDialog = null;
		m_progressDialog = ProgressDialog.show(this, "", str, true, cancelable);
	}

	public void dismissProgressDialog() {
		if (m_progressDialog != null && m_progressDialog.isShowing()) {
			m_progressDialog.dismiss();
		}
		m_progressDialog = null;
	}

	public void showLoading(boolean cancelable) {
		showProgressDialog(R.string.netLoading, cancelable);
	}

	public void showLoading() {
		showProgressDialog(R.string.netLoading);
	}

	public void showToast(int strId) {
		Toast.makeText(this, getString(strId), Toast.LENGTH_SHORT).show();
	}

	public void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public boolean checkInternet() {
		if (!Tool.hasInternet(this)) {
			alertDialog(R.string.networkUnavailable);
			dismissProgressDialog();
			return false;
		}
		return true;
	}
}
