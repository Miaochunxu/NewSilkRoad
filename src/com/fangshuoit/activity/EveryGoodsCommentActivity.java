package com.fangshuoit.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.model.CommentData;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.DateUtil;
import com.fangshuoit.tool.JSONUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: EveryGoodsCommentActivity
 * @Description: TODO 用户评论
 * @author 方硕IT 缪春旭
 * @date 2015-6-16 上午9:59:58
 * 
 */
public class EveryGoodsCommentActivity extends Activity {

	private ListView lv_comment;

	private McxBaseAdapter<CommentData> adapter;

	private List<CommentData> datalist;

	private ImageView img_back;

	private AbHttpUtil mAbHttpUtil = null;// 解析json
	private ProgressDialog progressDialog;

	private int number = 1;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_every_goods_comment);

		initView();

		initEvent();
	}

	private void initEvent() {

		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		System.out.println(intent.getStringExtra("goodId"));
		progressDialog.show();
		try {
			analysisJsonEveryGoodsComment(number);
		} catch (Exception e) {
			progressDialog.cancel();
		}

	}

	private void initView() {

		intent = getIntent();

		mAbHttpUtil = AbHttpUtil.getInstance(EveryGoodsCommentActivity.this);// 初始化解析json
		progressDialog = new ProgressDialog(EveryGoodsCommentActivity.this);

		img_back = (ImageView) findViewById(R.id.img_activity_every_goods_comment_back);

		lv_comment = (ListView) findViewById(R.id.lv_activity_every_goods_comment);

	}

	private void analysisJsonEveryGoodsComment(int num) {
		String url = SysConstants.SERVER + SysConstants.EVERYGOODSCOMMENTS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", intent.getStringExtra("goodId"));
			params.put("option", num + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				String voList = JSONUtils.getString(body, "voList", "");
				if (code.equals("0")) {
					if (!(null == voList || voList.equals("") || voList
							.equals("[]"))) {
						// 解析商品最终的属性列表
						// 新建Gson
						Gson gson = new Gson();
						datalist = gson.fromJson(voList,
								new TypeToken<List<CommentData>>() {
								}.getType());

						initAdapter();
					} else {
						findViewById(R.id.tv_activity_every_goods_comment_null)
								.setVisibility(View.VISIBLE);
					}
				} else {
					progressDialog.cancel();
					AbToastUtil.showToast(
							EveryGoodsCommentActivity.this,
							getResources().getString(
									R.string._sort_goods_load_error));
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
				AbToastUtil.showToast(
						EveryGoodsCommentActivity.this,
						getResources().getString(
								R.string._sort_goods_load_error));
			}

		});

	}

	private void initAdapter() {
		adapter = new McxBaseAdapter<CommentData>(
				EveryGoodsCommentActivity.this,
				R.layout.activity_every_goods_comment_listview_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				CommentData data = getItem(position);

				TextView tv_title = (TextView) listCell
						.findViewById(R.id.tv_activity_every_goods_comment_listcell_title);
				TextView tv_context = (TextView) listCell
						.findViewById(R.id.tv_activity_every_goods_comment_listcell_context);
				TextView tv_time = (TextView) listCell
						.findViewById(R.id.tv_activity_every_goods_comment_listcell_time);

				tv_title.setText(data.getUsername());
				tv_context.setText(data.getContent());
				tv_time.setText(DateUtil.USATimeStringToString(
						data.getCreatTime(), "yyyy-MM-dd"));
			}
		};

		lv_comment.setAdapter(adapter);

		adapter.addUpdata(datalist);
	}
}
