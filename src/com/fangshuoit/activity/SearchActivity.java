package com.fangshuoit.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.model.SearchCell;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.LoginUtil;
import com.fangshuoit.tool.ObjectCacheUtils;
import com.fangshuoit.tool.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SearchActivity extends Activity {

	private ListView listView;

	private ImageView img_search;

	private EditText ed_search;

	private McxBaseAdapter<String> adapter;

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private ProgressDialog progressDialog;

	private TextView tv_detele;

	ArrayList<String> prizeList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		initView();

		initEvent();
	}

	private void initEvent() {

		img_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ed_search.getText().toString().trim() + "" == "") {
					ToastUtils.show(getApplicationContext(), getResources()
							.getString(R.string._search_activity_search_what));
				} else {
					ObjectCacheUtils.saveObjCache(ed_search.getText()
							.toString().trim()
							+ "");
					try {
						analysisJsonSearch(ed_search.getText().toString()
								.trim()
								+ "");
					} catch (Exception e) {
					}
				}
			}
		});

		tv_detele.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					ObjectCacheUtils.deteleFile();
					prizeList.clear();
					adapter.notifyDataSetChanged();
					ToastUtils.show(getApplicationContext(),
							R.string._search_activity_empty_on);
				} catch (Exception e) {
					ToastUtils.show(getApplicationContext(),
							R.string._search_activity_empty_no_data);
				}
			}
		});

		ed_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) { // 这里的action和在layout中设置的android:imeOptions属性是对应的.

					// 这个方法的作用就是,动作之行后的回调,在用户输入完成后,点击了输入法中的搜索按钮,就会执行这个方法

					// 返回值: 如果你处理了该事件，返回true；否则返回false。

					// TODO:这时你要在这里执行真正的搜索操作
					if (ed_search.getText().toString().trim() == null
							|| ed_search.getText().toString().trim() == "") {
						ToastUtils.show(getApplicationContext(),
								R.string._search_activity_search_what);

					} else {
						ObjectCacheUtils.saveObjCache(ed_search.getText()
								.toString().trim());
						try {
							analysisJsonSearch(ed_search.getText().toString()
									.trim());
						} catch (Exception e) {
							AbToastUtil
									.showToast(
											SearchActivity.this,
											getResources()
													.getString(
															R.string._every_good_activity_tv_seven));
						}
					}
				}
				return true;
			}
		});
	}

	private void initView() {

		tv_detele = (TextView) findViewById(R.id.activity_search_detele);

		mAbHttpUtil = AbHttpUtil.getInstance(SearchActivity.this);// 初始化解析json
		progressDialog = new ProgressDialog(SearchActivity.this);
		progressDialog.setCanceledOnTouchOutside(false);
		listView = (ListView) findViewById(R.id.lv_search);

		img_search = (ImageView) findViewById(R.id.img_search_search);

		ed_search = (EditText) findViewById(R.id.ed_search_context);

		initAdapter();
	}

	private void initAdapter() {

		adapter = new McxBaseAdapter<String>(SearchActivity.this,
				R.layout.activity_search_list_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				TextView view = (TextView) listCell
						.findViewById(R.id.tv_search_cell);
				view.setText(getItem(position).toString());
			}
		};

		for (int i = ObjectCacheUtils.readObjCache().size(); i > 0; i--) {
			prizeList.add(ObjectCacheUtils.readObjCache().get(i - 1));
		}
		adapter.addUpdata(prizeList);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					analysisJsonSearch(prizeList.get(position));
				} catch (Exception e) {
					AbToastUtil.showToast(SearchActivity.this, getResources()
							.getString(R.string._every_good_activity_tv_seven));
				}
			}
		});

	}

	/**
	 * 
	 * @Title: analysisJsonSearch
	 * @Description: TODO(搜索)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonSearch(String option) {
		String url = SysConstants.SERVER + SysConstants.SEARCH;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("option", option);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {

				if (!(content == null || content == "")) {
					Gson gson = new Gson();
					TourConstants.searchCellList = gson.fromJson(content,
							new TypeToken<List<SearchCell>>() {
							}.getType());
					LoginUtil.rember_activityFrom("SearchActivity");

					Intent intent = new Intent(SearchActivity.this,
							SortGoodsActivity.class);
					intent.putExtra("Title",
							R.string._search_activity_title_silu);
					startActivity(intent);
					progressDialog.cancel();
					finish();
				} else {
					progressDialog.cancel();
					Toast.makeText(getApplicationContext(),
							R.string._search_activity_search_no_one,
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
			}

		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
		}
		return false;
	}
}
