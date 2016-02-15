package com.fangshuoit.activity;

import com.fangshuoit.newsilkroad.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LogisticsWebActivity extends Activity {

	private WebView webView;

	private Intent intent;

	private ProgressDialog progressDialog;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logistics);

		intent = getIntent();

		progressDialog = new ProgressDialog(LogisticsWebActivity.this);
		progressDialog.setCanceledOnTouchOutside(false);
		webView = (WebView) findViewById(R.id.webView_activity_logistics);

		webView.loadUrl(intent.getStringExtra("htmlUrl"));

		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				if (newProgress == 100) {
					// 网页加载完成
					progressDialog.cancel();
				} else {
					// 加载中
					progressDialog.show();
				}

			}
		});

		findViewById(R.id.img_activity_logistics_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

	}

}
