package com.fangshuoit.activity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ab.util.AbToastUtil;
import com.fangshuoit.newsilkroad.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class EveryGoodsWebInfoActivity extends Activity {

	private WebView webView;

	private Intent intent;

	private String htmlString = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_every_goods_web);
		initView();
		initEvent();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	private void initEvent() {
		WebSettings ws = webView.getSettings();
		ws.setTextSize(WebSettings.TextSize.NORMAL);
		String htmlContent = changeImgWidth(htmlString);
		webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8",
				null);

		findViewById(R.id.img_activity_everygood_web_info_back)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

	}

	private void initView() {

		intent = getIntent();
		htmlString = intent.getStringExtra("content");
		webView = (WebView) findViewById(R.id.web_activity_everygoods_web);
	}

	public static String changeImgWidth(String htmlContent) {
		Document doc_Dis = Jsoup.parse(htmlContent);
		Elements ele_Img = doc_Dis.getElementsByTag("img");
		if (ele_Img.size() != 0) {
			for (Element e_Img : ele_Img) {
				e_Img.attr("style", "width:100%");
			}
		}
		String newHtmlContent = doc_Dis.toString();
		return newHtmlContent;
	}

}
