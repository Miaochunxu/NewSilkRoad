package com.fangshuoit.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.model.MyCollectionCell;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.DateUtil;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: MyCollectionActivity
 * @Description: TODO 我的收藏
 * @author 方硕IT 缪春旭
 * @date 2015-7-23 下午5:16:03
 * 
 */
public class MyCollectionActivity extends AbActivity {

	private ListView listView;

	private View mAvatarView = null;

	private McxBaseAdapter<MyCollectionCell> adapter;

	private List<MyCollectionCell> collectionList;

	private AbHttpUtil mAbHttpUtil = null;

	private ProgressDialog progressDialog;

	private ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		initView();
		initEvent();
	}

	private void initEvent() {
		progressDialog.show();
		try {
			analysisJsonShowCollection();
		} catch (Exception e) {
			progressDialog.cancel();
			AbToastUtil.showToast(MyCollectionActivity.this, getResources()
					.getString(R.string._data_error_load));
		}

		findViewById(R.id.img_activity_collection_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
	}

	private void initView() {

		imageLoader = new ImageLoader(getApplicationContext(), true);

		mAbHttpUtil = AbHttpUtil.getInstance(this);
		progressDialog = new ProgressDialog(MyCollectionActivity.this);

		listView = (ListView) findViewById(R.id.lv_activity_collection);
	}

	private void initAdapter() {
		adapter = new McxBaseAdapter<MyCollectionCell>(
				MyCollectionActivity.this,
				R.layout.activity_collection_list_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				MyCollectionCell data = getItem(position);

				TextView tv_time = (TextView) listCell
						.findViewById(R.id.tv_activity_collection_list_cell_time);
				TextView tv_context = (TextView) listCell
						.findViewById(R.id.tv_activity_collection_list_cell_context);
				TextView tv_title = (TextView) listCell
						.findViewById(R.id.tv_activity_collection_list_cell_title);

				tv_time.setText(DateUtil.USATimeStringToString(data
						.getCreatTime()));
				tv_title.setText(data.getGoodName());
				tv_context.setText(data.getGoodJians());

				ImageView img = (ImageView) listCell
						.findViewById(R.id.img_activity_collection_list_cell);

				String imgurl = IfHttpToStart.initUr(data.getGoodLogo(), "120",
						"100");

				imageLoader.DisplayImage(imgurl, img);
			}
		};

		adapter.addUpdata(collectionList);
		listView.setAdapter(adapter);

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@SuppressLint("InflateParams")
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				LayoutInflater inflater = LayoutInflater
						.from(MyCollectionActivity.this);// 创建一个View对象,将定义好的布局放入其中
				mAvatarView = inflater.inflate(R.layout.choose_avatar, null);
				Button albumButton = (Button) mAvatarView
						.findViewById(R.id.choose_album);
				albumButton.setText(R.string._every_good_activity_tv_six);

				Button camButton = (Button) mAvatarView
						.findViewById(R.id.choose_cam);

				camButton.setVisibility(View.GONE);
				Button cancelButton = (Button) mAvatarView
						.findViewById(R.id.choose_cancel);
				cancelButton.setVisibility(View.GONE);

				final int pot = position;
				albumButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						progressDialog.show();
						try {
							analysisJsonConnectionDetele(collectionList
									.get(pot).getCollectionId());

						} catch (Exception e) {
							progressDialog.cancel();
							AbDialogUtil.removeDialog(mAvatarView);
							AbToastUtil
									.showToast(
											MyCollectionActivity.this,
											getResources()
													.getString(
															R.string._fix_adress_activity_tv_detele_on_chance));
						}
					}
				});
				AbDialogUtil.showDialog(mAvatarView, Gravity.BOTTOM);
				return true;
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MyCollectionCell d = collectionList.get(position);

				Intent intent = new Intent(getApplicationContext(),
						EveryGoodsActivity.class);
				intent.putExtra("GoodId", d.getGoodId());
				startActivity(intent);
			}
		});
	}

	/**
	 * 
	 * @Title: analysisJsonShowCollection
	 * @Description: TODO(显示所有收藏)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonShowCollection() {
		String url = SysConstants.SERVER + SysConstants.SHOWCOLLECTION;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", LoginUtil.get_UserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String body = JSONUtils.getString(content, "body", "");
				if (code.equals("0")) {
					if (!(body == null || body == "")) {
						Gson gson = new Gson();
						collectionList = gson.fromJson(body,
								new TypeToken<List<MyCollectionCell>>() {
								}.getType());
						initAdapter();
						progressDialog.cancel();
					}
				} else {
					progressDialog.cancel();
					Toast.makeText(getApplicationContext(),
							R.string._every_good_activity_tv_one,
							Toast.LENGTH_SHORT).show();
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
	 * @Title: analysisJsonConnectionDetele
	 * @Description: TODO(删除收藏)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonConnectionDetele(String collectionId) {
		String url = SysConstants.SERVER + SysConstants.DETELECOLLECTION;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", collectionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					progressDialog.cancel();
					if (message.toString().equals("success")) {
						analysisJsonShowCollection();
						adapter.notifyDataSetChanged();
						AbDialogUtil.removeDialog(mAvatarView);
						Toast.makeText(getApplicationContext(),
								R.string._every_good_activity_tv_six,
								Toast.LENGTH_SHORT).show();
					} else {
						AbDialogUtil.removeDialog(mAvatarView);
						Toast.makeText(getApplicationContext(),
								R.string._every_good_activity_tv_five,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					AbDialogUtil.removeDialog(mAvatarView);
					Toast.makeText(getApplicationContext(),
							R.string._every_good_activity_tv_five,
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				AbDialogUtil.removeDialog(mAvatarView);
				progressDialog.cancel();
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				progressDialog.cancel();
				AbDialogUtil.removeDialog(mAvatarView);
				Toast.makeText(getApplicationContext(),
						R.string._every_good_activity_tv_five,
						Toast.LENGTH_SHORT).show();
			}

		});
	}
}
