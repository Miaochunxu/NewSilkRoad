package com.fangshuoit.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.model.ShoppingCarListCell;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: ShoppingCarActivity
 * @Description: TODO 购物车
 * @author 方硕IT 缪春旭
 * @date 2015-5-26 下午2:43:18
 * 
 */
public class ShoppingCarActivity extends Activity implements OnClickListener {

	private ListView listView;

	private TextView tv_edit, tv_allmoney, tv_inorder;

	private CheckBox checkBox;

	private ImageView img_back;

	private ImageLoader imageLoader;

	private boolean ifAll, ifAllnot;// 判断checkbox是否全部被选中

	private McxBaseAdapter<ShoppingCarListCell> adapter;

	private List<ShoppingCarListCell> showshoppingcar;

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private ProgressDialog progressDialog;

	private StringBuffer finishId;

	private boolean ifone = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_car);

		initView();
		initEvent();
	}

	private void initEvent() {

		tv_edit.setOnClickListener(this);
		img_back.setOnClickListener(this);
		tv_inorder.setOnClickListener(this);

	}

	private void initView() {

		mAbHttpUtil = AbHttpUtil.getInstance(ShoppingCarActivity.this);// 初始化解析json
		progressDialog = new ProgressDialog(ShoppingCarActivity.this);

		imageLoader = new ImageLoader(ShoppingCarActivity.this, true);

		listView = (ListView) findViewById(R.id.lv_activity_shopping_car);

		tv_edit = (TextView) findViewById(R.id.tv_activity_shopping_car_edit);

		tv_allmoney = (TextView) findViewById(R.id.tv_activity_shoppingcar_allmoney);

		tv_inorder = (TextView) findViewById(R.id.btn_activity_shopping_inorder);

		checkBox = (CheckBox) findViewById(R.id.cb_activity_shopping_car);

		img_back = (ImageView) findViewById(R.id.img_activity_shopping_back);

		progressDialog
				.setMessage(getString(R.string._shopping_car_activity_load_now));
		progressDialog.show();

	}

	// adapter刷新方法
	private void dataChanged() {
		// 通知listView刷新
		adapter.notifyDataSetChanged();
	}

	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_activity_shopping_car_edit:
			if (tv_edit.getText().equals(
					getResources().getString(
							R.string._shopping_car_activity_tv_edit))) {
				tv_edit.setText(R.string._shopping_car_activity_tv_finish);

				adapter.setEdit(true);
				dataChanged();
			} else {
				// 记录商品数量修改的id
				StringBuffer goodsId = new StringBuffer();
				// 记录商品数量修改的number
				StringBuffer goodsNumber = new StringBuffer();
				for (int i = 0; i < showshoppingcar.size(); i++) {
					if (!showshoppingcar.get(i).getGoodNo()
							.equals(adapter.getIsNumber().get(i))) {
						goodsId.append(showshoppingcar.get(i).getId() + ";");
						goodsNumber.append(adapter.getIsNumber().get(i) + ";");
					}
				}
				if (!(goodsId.toString() == "" || goodsId == null || goodsId
						.toString() == null)) {
					progressDialog.show();
					try {
						analysisJsonFixShoppingCarNumber(goodsId.toString(),
								goodsNumber.toString());
					} catch (Exception e) {
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								R.string._shopping_car_activity_error,
								Toast.LENGTH_SHORT).show();
					}
				}
				tv_edit.setText(getResources().getString(
						R.string._shopping_car_activity_tv_edit));
				adapter.setEdit(false);
				dataChanged();
			}
			break;
		case R.id.img_activity_shopping_back:
			adapter.setEdit(false);
			this.finish();
			break;
		case R.id.btn_activity_shopping_inorder:
			TourConstants.shoppingcarList.clear();
			finishId = new StringBuffer("");
			for (int i = 0; i < showshoppingcar.size(); i++) {
				if (adapter.getIsSelected().get(i)) {
					finishId.append(showshoppingcar.get(i).getId() + ";");
					TourConstants.shoppingcarList.add(showshoppingcar.get(i));
				}
			}
			if (finishId.toString().equals("") || finishId == null) {
				Toast.makeText(getApplicationContext(),
						R.string._shopping_car_activity_settle_what,
						Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent(getApplicationContext(),
						OrderActivity.class);
				intent.putExtra("goodId", finishId.toString());
				intent.putExtra("activityFrom", "ShoppingCarActivity");
				System.out.println("goodId=" + finishId.toString());
				intent.putExtra("allMoney", tv_allmoney.getText());
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			McxBaseAdapter.setEdit(false);
			finish();
			// this.overridePendingTransition(0,
			// R.anim.activity_close_translate_right);
		}
		return false;
	}

	/**
	 * 
	 * @Title: analysisJsonToShoppingCar
	 * @Description: TODO(解析购物车数据)
	 * @param @param tid
	 * @param @param idList 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonShowShoppingCar() {
		String url = SysConstants.SERVER + SysConstants.SHOWSHOPPINGCAR;
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
				// String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {

					Gson gson = new Gson();
					showshoppingcar = gson.fromJson(body,
							new TypeToken<List<ShoppingCarListCell>>() {
							}.getType());

					initAdapter();
					progressDialog.cancel();

				} else {
					progressDialog.cancel();
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
	 * @Title: analysisJsonFixShoppingCarNumber
	 * @Description: TODO(修改购物车商品数量)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonFixShoppingCarNumber(String goodsId,
			String goodsNumber) {
		String url = SysConstants.SERVER + SysConstants.FIXSHOPPINGCARNUMBER;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", LoginUtil.get_UserId());
			params.put("idList", goodsId.substring(0, goodsId.length() - 1));
			params.put("num",
					goodsNumber.substring(0, goodsNumber.length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String body = JSONUtils.getString(content, "body", "");
				if (code.equals("0")) {
					Gson gson = new Gson();
					showshoppingcar = gson.fromJson(body,
							new TypeToken<List<ShoppingCarListCell>>() {
							}.getType());
					analysisJsonShowShoppingCar();
					progressDialog.cancel();
				} else {
					progressDialog.cancel();
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
	 * @Title: analysisJsonDeleteonemall
	 * @Description: TODO(购物车内删除商品)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonDeleteonemall(String mallid, final int pot) {
		String url = SysConstants.SERVER + SysConstants.DELETESHOPPINGCAR;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", LoginUtil.get_UserId());
			params.put("idList", mallid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				if (code.equals("0")) {
					progressDialog.cancel();
					Toast.makeText(getApplicationContext(),
							R.string._shopping_car_activity_detele_on,
							Toast.LENGTH_SHORT).show();
					adapter.remove(pot);
					dataChanged();
				} else {
					progressDialog.cancel();
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
	 * @Title: initAdapter
	 * @Description: TODO(adapter处理)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initAdapter() {
		adapter = new McxBaseAdapter<ShoppingCarListCell>(
				ShoppingCarActivity.this,
				R.layout.activity_shopping_car_listview_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				final ShoppingCarListCell data = getItem(position);
				ImageView img = (ImageView) listCell
						.findViewById(R.id.img_activity_shopping_car_listview_cell);

				LinearLayout ly_none = (LinearLayout) listCell
						.findViewById(R.id.ly_activity_shopping_car_none);
				ly_none.setVisibility(View.GONE);
				if (getEdit()) {
					ly_none.setVisibility(View.VISIBLE);
				} else {
					ly_none.setVisibility(View.GONE);
				}

				// 完成界面的商品数量
				TextView tv_number = (TextView) listCell
						.findViewById(R.id.tv_activity_shopping_car_listview_cell_number);

				// 编辑界面的商品数量
				TextView tv_number_two = (TextView) listCell
						.findViewById(R.id.tv_activity_shopping_car_listview_cell_number_two);

				TextView tv_title = (TextView) listCell
						.findViewById(R.id.tv_activity_shopping_car_listview_cell_title);
				TextView tv_context = (TextView) listCell
						.findViewById(R.id.tv_activity_shopping_car_listview_cell_type);
				TextView tv_attribute = (TextView) listCell
						.findViewById(R.id.tv_activity_shopping_car_listview_cell_attribute);
				TextView tv_money = (TextView) listCell
						.findViewById(R.id.tv_activity_shopping_car_listview_cell_money);

				CheckBox cbox = (CheckBox) listCell
						.findViewById(R.id.cb_activity_shopping_car_listview_cell);
				// 控制CheckBox的选中与否
				cbox.setChecked(getIsSelected().get(position));

				final int pot = position;
				cbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@SuppressWarnings("static-access")
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						float f = Float.parseFloat(showshoppingcar.get(pot)
								.getPrice());
						if (isChecked) {
							getIsSelected().set(pot, true);
							ifAllnot = true;
							for (int i = 0; i < showshoppingcar.size(); i++) {
								if (!adapter.getIsSelected().get(i)) {
									ifAllnot = false;
									continue;
								}
							}
							money += f;
							tv_allmoney.setText(money + "");
							if (ifAllnot) {
								checkBox.setChecked(true);
							}
						} else {
							getIsSelected().set(pot, false);
							checkBox.setChecked(false);
							money -= f;
							tv_allmoney.setText(money + "");
						}
					}
				});

				tv_title.setText(data.getName());
				tv_context.setText(data.getJians());
				tv_attribute.setText(data.getAttrList());
				tv_money.setText(data.getPrice());
				tv_number.setText(data.getGoodNo());

				// 删除按钮监听
				listCell.findViewById(R.id.tv_shopping_car_delete)
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								progressDialog
										.setMessage(getString(R.string._shopping_car_activity_detele_in));
								progressDialog.show();
								try {
									analysisJsonDeleteonemall(data.getId(), pot);

								} catch (Exception e) {
									progressDialog.cancel();
									Toast.makeText(
											getApplicationContext(),
											R.string._fix_adress_activity_tv_one,
											Toast.LENGTH_SHORT).show();
								}
							}
						});
				if (ifone) {
					tv_number_two.setText(data.getGoodNo());
					getIsNumber().set(pot, data.getGoodNo());
				} else {
					// 得到商品数量
					tv_number_two.setText(getIsNumber().get(position));
				}
				final String number = tv_number_two.getText().toString();
				// 商品数量加一
				listCell.findViewById(R.id.img_shopping_car_jiahao)
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								int a = Integer.parseInt(number);
								a = a + 1;
								getIsNumber().set(pot, "" + a);
								ifone = false;
								dataChanged();
							}
						});

				// 商品数量减一
				listCell.findViewById(R.id.img_shopping_car_jianhao)
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								int a = Integer.parseInt(number);
								if (a > 1) {
									a = a - 1;
									getIsNumber().set(pot, "" + a);
									ifone = false;
									dataChanged();
								} else {
									Toast.makeText(
											ShoppingCarActivity.this,
											R.string._shopping_car_activity_tv_three,
											Toast.LENGTH_SHORT).show();
								}
							}
						});

				String imgurl = IfHttpToStart.initUr(data.getLogo(), "100",
						"100");
				// 开始加载图片
				imageLoader.DisplayImage(imgurl, img);
			}
		};
		adapter.addUpdata(showshoppingcar);
		listView.setAdapter(adapter);
		adapter.initCheckDate();
		adapter.initNumber();

		adapter.notifyDataSetChanged();

		initCheckbox();
	}

	private float money = 0;

	private void initCheckbox() {
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@SuppressWarnings({ "static-access" })
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// 遍历list的长度，将MyAdapter中的map值全部设为true
					for (int i = 0; i < showshoppingcar.size(); i++) {
						adapter.getIsSelected().set(i, true);
					}
					// 刷新listview和TextView的显示
					dataChanged();
				} else if (!isChecked) {
					ifAll = false;
					// 遍历list的长度，将MyAdapter中的map值全部设为true
					for (int i = 0; i < showshoppingcar.size(); i++) {
						if (!adapter.getIsSelected().get(i)) {
							ifAll = true;
							continue;
						}
					}
					if (!ifAll) {
						for (int i = 0; i < showshoppingcar.size(); i++) {
							adapter.getIsSelected().set(i, false);
						}
						// 刷新listview和TextView的显示
						dataChanged();
					}
					tv_allmoney.setText("0.0");
				}
			}
		});
	}

	@Override
	protected void onResume() {
		try {
			analysisJsonShowShoppingCar();
			checkBox.setChecked(false);
		} catch (Exception e) {
			progressDialog.cancel();
		}
		super.onResume();
	}
}
