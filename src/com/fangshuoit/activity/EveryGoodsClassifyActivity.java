package com.fangshuoit.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.model.ShoppingCarListCell;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.CheckList;
import com.fangshuoit.tool.DispalyUtil;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.fangshuoit.view.AutoLineFeedLayout;

/**
 * 
 * @ClassName: EveryGoodsClassifyActivity
 * @Description: TODO 查看商品属性，点击查看分类或者点击购买弹出
 * @author 方硕IT 缪春旭
 * @date 2015-5-29 上午9:24:44
 * 
 */
public class EveryGoodsClassifyActivity extends Activity implements
		OnClickListener {

	private LinearLayout ly_classify;

	private Button btn_ok, btn_jia, btn_jian;;

	private TextView tv_number, tv_money, tv_goodname;

	private AbHttpUtil mAbHttpUtil = null;// 解析json
	// private ProgressDialog progressDialog;

	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, String> hm = new HashMap<Integer, String>();// 最终属性
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, String> hmid = new HashMap<Integer, String>();
	// 记录最后的id，提交给后台
	private HashMap<String, String> goodId = new HashMap<String, String>();
	// 记录单个商品价格
	private HashMap<String, String> goodprice = new HashMap<String, String>();

	private AutoLineFeedLayout autoLineFeedLayout;

	private ImageView imageView;

	private LinearLayout layout, layoutOff;

	private List<String> lista = new ArrayList<String>();// 所有属性的list

	private int number = 1;

	private int typeNumber = TourConstants.numberList.size();// 属性有多少种
	private int listNumber = TourConstants.everyGoodsTypeSortList.size();// 有多少条数据
	private int sublistNumber = TourConstants.submitId.size();// 有多少种组合

	private List<String> listString = new ArrayList<String>();// 记录每种属性的名称

	private List<Integer> listitem = new ArrayList<Integer>();// 控制每个属性的数量
	// 讲取出的父类id放入list
	private List<String> listParent = new ArrayList<String>();

	private Bundle bundle;

	private String url;

	private ImageLoader imageLoader;
	// 商品最终的属性
	private String finishCell = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_every_goods_classify);

		initView();

		initEvent();

	}

	private void initEvent() {

		btn_jia.setOnClickListener(this);
		btn_jian.setOnClickListener(this);

		// 确定按钮监听
		btn_ok.setOnClickListener(this);

		ly_classify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});

	}

	private void initView() {

		imageLoader = new ImageLoader(EveryGoodsClassifyActivity.this, true);

		bundle = this.getIntent().getExtras();
		// 初始化最后要传给后台的id
		goodId.put("everyGoodsId", "");

		tv_money = (TextView) findViewById(R.id.tv_activity_every_goods_classify_money);

		tv_goodname = (TextView) findViewById(R.id.tv_activity_every_goods_classify_name);
		// 商品名称
		tv_goodname.setText(bundle.getString("goodName"));
		mAbHttpUtil = AbHttpUtil.getInstance(EveryGoodsClassifyActivity.this);// 初始化解析json
		// progressDialog = new ProgressDialog(EveryGoodsActivity.this);

		// 初始化数据
		for (int i = 1; i < listNumber; i++) {
			if (TourConstants.everyGoodsTypeSortList.get(i).getLevel()
					.equals("3")) {
				lista.add(TourConstants.everyGoodsTypeSortList.get(i).getName());
				TourConstants.finishSelfid
						.add(TourConstants.everyGoodsTypeSortList.get(i)
								.getId());
			}
		}

		// 初始化数据
		listitem.add(0);
		for (int i = 1; i < typeNumber; i++) {
			int a = TourConstants.numberList.get(i) - i;
			listitem.add(a);
		}
		int b = listNumber - typeNumber;
		listitem.add(b);

		// 初始化数据
		for (int i = 0; i < typeNumber; i++) {
			listString.add(TourConstants.everyGoodsTypeSortList.get(
					TourConstants.numberList.get(i)).getName());
		}

		ly_classify = (LinearLayout) findViewById(R.id.ly_activity_every_goods_classify);

		imageView = (ImageView) findViewById(R.id.img_activity_every_goods_classify);

		imageView.bringToFront();

		url = IfHttpToStart.initUr(bundle.getString("logo"), "150", "150");

		// 开始加载背景图片
		imageLoader.DisplayImage(url, imageView);

		// 动态添加选择后属性控件
		initLayoutOffView();

		// 动态加载类型控件
		initLayoutView();

		btn_ok = (Button) findViewById(R.id.btn_acyivity_every_goods_classify);

		btn_jia = (Button) findViewById(R.id.btn_activity_every_goods_jia);
		btn_jian = (Button) findViewById(R.id.btn_activity_every_goods_jian);

		tv_number = (TextView) findViewById(R.id.tv_activity_every_goods_number);

		tv_number.setText("1");

	}

	/**
	 * 
	 * @Title: initLayoutOffView
	 * @Description: TODO(动态显示选择后的属性值)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initLayoutOffView() {

		layoutOff = (LinearLayout) findViewById(R.id.ly_activity_every_goods_classify_type);

		autoLineFeedLayout = new AutoLineFeedLayout(this);

		autoLineFeedLayout.setChildViewWidthSpace((int) DispalyUtil
				.getPxFromDp(this, 2));
		autoLineFeedLayout.setChildHeightSpace((int) DispalyUtil.getPxFromDp(
				this, 2));
		autoLineFeedLayout.setChildHeight((int) DispalyUtil.getPxFromDp(this,
				17));
		autoLineFeedLayout.setChildWidthRatio(15);
		for (int i = 0; i < typeNumber; i++) {
			TextView tv = new TextView(this);
			tv.setTextColor(getResources().getColor(R.color.small_title));
			tv.setText(getResources().getString(R.string._plese_change)
					+ TourConstants.everyGoodsTypeSortList.get(
							TourConstants.numberList.get(i)).getName());
			tv.setTextSize(13);
			tv.setId(10000 + i);
			autoLineFeedLayout.addView(tv);
		}

		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutOff.addView(autoLineFeedLayout, lp1);

	}

	/**
	 * 
	 * @Title: initLayoutView
	 * @Description: TODO(动态加载悬着属性控件)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initLayoutView() {
		// 总布局
		layout = (LinearLayout) findViewById(R.id.ly_dontaijiazai);

		for (int n = 0; n < typeNumber; n++) {
			// 自动换行Layout
			autoLineFeedLayout = new AutoLineFeedLayout(this);

			autoLineFeedLayout.setChildViewWidthSpace((int) DispalyUtil
					.getPxFromDp(this, 10));
			autoLineFeedLayout.setChildHeightSpace((int) DispalyUtil
					.getPxFromDp(this, 10));
			autoLineFeedLayout.setChildHeight((int) DispalyUtil.getPxFromDp(
					this, 30));
			autoLineFeedLayout.setChildWidthRatio(18);

			for (int i = listitem.get(n); i < listitem.get(n + 1); i++) {
				TextView tv = new TextView(this);
				tv.setTextSize(16);
				tv.setGravity(Gravity.CENTER);
				tv.setText(" " + lista.get(i) + " ");
				tv.setId(i);
				tv.setTextColor(0xff3d4245);
				tv.setBackgroundResource(R.drawable.tv_bg_every_goods_classify);
				tv.setVisibility(View.VISIBLE);
				autoLineFeedLayout.addView(tv);

				final int b = n;
				final int a = i;
				tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						for (int i = listitem.get(b); i < listitem.get(b + 1); i++) {
							findViewById(i).setBackgroundResource(
									R.drawable.tv_bg_every_goods_classify);
						}
						findViewById(a).setBackgroundResource(
								R.drawable.tv_bg_every_goods_classify_yes);

						if (listitem.get(b) == 0) {
							hmid.put(0, TourConstants.finishSelfid.get(a));
							initList();
							// System.out.println("<<<<a=" + a);
							// System.out.println("text=" + lista.get(a));
							TextView tv = (TextView) findViewById(10000);
							tv.setText(TourConstants.everyGoodsTypeSortList
									.get(TourConstants.numberList.get(0))
									.getName()
									+ ":" + lista.get(a));
							hm.put(0, lista.get(a));
						}
						for (int i = 0; i < listitem.size(); i++) {
							if (a - listitem.get(i) < listitem.get(i)
									&& a - listitem.get(i) > -1) {
								hmid.put(listitem.get(i),
										TourConstants.finishSelfid.get(a));
								// System.out.println("<<<<a=" + a);
								// System.out.println("<<<<listitem.get(i)="
								// + listitem.get(i));
								// System.out.println("text=" + lista.get(a));

								TextView tv_miao = (TextView) findViewById((10000 + b));
								tv_miao.setText(TourConstants.everyGoodsTypeSortList
										.get(TourConstants.numberList.get(i))
										.getName()
										+ ":" + lista.get(a));
								initList();
								hm.put(listitem.get(i), lista.get(a));
							}
						}

					}

					/**
					 * 
					 * @Title: initList
					 * @Description: TODO(得到对应属性的图片)
					 * @param 设定文件
					 * @return void 返回类型
					 * @throws
					 */
					private void initList() {
						if (hmid.size() == typeNumber) {
							for (int j = 0; j < typeNumber; j++) {
								listParent.add(hmid.get(listitem.get(j)));
							}
							for (int j = 0; j < sublistNumber; j++) {
								if (CheckList.CheckListSubtract(
										TourConstants.finishIdList.get(j),
										listParent)) {
									goodprice.put("goodLogoM",
											TourConstants.submitId.get(j)
													.getMemo());
									String money = TourConstants.submitId
											.get(j).getDisPrice();
									goodprice.put("goodpriceM", money);
									tv_money.setText(money);
									goodId.put("everyGoodsId",
											TourConstants.submitId.get(j)
													.getId());

									if (!(TourConstants.submitId.get(j)
											.getMemo() == null || TourConstants.submitId
											.get(j).getMemo() == "")) {
										String a = IfHttpToStart.initUr(
												TourConstants.submitId.get(j)
														.getMemo(), "150",
												"150");
										// 开始加载背景图片
										imageLoader.DisplayImage(a, imageView);
									} else {

										imageLoader.DisplayImage(IfHttpToStart
												.initUr(bundle
														.getString("logo"),
														"150", "150"),
												imageView);
									}
								}
							}
						}
						listParent.clear();
					}
				});
			}
			LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			TextView tv = new TextView(this);
			tv.setTextColor(0xff525252);
			tv.setText(listString.get(n));
			tv.setPadding(30, 10, 0, 0);

			layout.addView(tv);
			layout.addView(autoLineFeedLayout, lp1);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		this.overridePendingTransition(0, R.anim.activity_close_translate_b);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			this.overridePendingTransition(0, R.anim.activity_close_translate_b);
		}
		return false;
	}

	/**
	 * 
	 * @Title:analysisJsonToShoppingCar
	 * @Description:TODO 加入购物车
	 * @param @param tid
	 * @param @param idList
	 * @return void
	 * @throwsTo
	 */

	private void analysisJsonToShoppingCar(String tid, String idList) {
		String url = SysConstants.SERVER + SysConstants.SHOPPINGCAR;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", tid);
			params.put("idList", idList);
			params.put("num", number + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					if (message.equals("success")) {
						Toast.makeText(getApplicationContext(),
								R.string._every_good_classify_activity_tv_one,
								Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								R.string._every_good_classify_activity_tv_two,
								Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
			}

		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_activity_every_goods_jia:
			number++;
			tv_number.setText(number + "");
			btn_jian.setClickable(true);
			break;
		case R.id.btn_activity_every_goods_jian:
			number--;
			if (number < 1) {
				Toast.makeText(EveryGoodsClassifyActivity.this,
						R.string._every_good_classify_activity_tv_three,
						Toast.LENGTH_SHORT).show();
				btn_jian.setClickable(false);
			} else {
				tv_number.setText(number + "");
			}
			break;
		case R.id.btn_acyivity_every_goods_classify:
			if (bundle.getString("activityFrom").equals("buynow")) {
				if (LoginUtil.get_is_Login()) {
					if (goodId.get("everyGoodsId").equals("")
							|| goodId.get("everyGoodsId").equals(null)) {
						Toast.makeText(getApplicationContext(),
								R.string._every_good_classify_activity_tv_six,
								Toast.LENGTH_SHORT).show();
					} else {
						TourConstants.shoppingcarList.clear();
						finishCell = "";
						for (int i = 0; i < listitem.size() - 1; i++) {
							if (i == 0) {
								finishCell = finishCell
										+ hm.get(listitem.get(i));
							} else {
								finishCell = finishCell + ";"
										+ hm.get(listitem.get(i));
							}
						}
						// 立即购买接口调用
						Intent intent = new Intent(
								EveryGoodsClassifyActivity.this,
								OrderActivity.class);

						TourConstants.shoppingcarList
								.add(new ShoppingCarListCell(goodprice
										.get("goodLogoM"), bundle
										.getString("goodName"), bundle
										.getString("goodJians"), goodprice
										.get("goodpriceM"), "" + number, "",
										"", finishCell));
						intent.putExtra("goodId", goodId.get("everyGoodsId"));
						intent.putExtra("allMoney", goodprice.get("goodpriceM"));
						intent.putExtra("number", number + "");
						intent.putExtra("activityFrom", "buynow");
						intent.putExtra("goodIdList",
								goodId.get("everyGoodsId"));
						startActivity(intent);
					}
				} else {
					Intent intent = new Intent(EveryGoodsClassifyActivity.this,
							LoginActivity.class);
					LoginUtil.rember_activityFrom("EveryGoodsClassifyActivity");
					startActivity(intent);
				}

			} else if (bundle.getString("activityFrom").equals("shopping")) {
				if (LoginUtil.get_is_Login()) {
					if (goodId.get("everyGoodsId").equals("")
							|| goodId.get("everyGoodsId").equals(null)) {
						Toast.makeText(getApplicationContext(),
								R.string._every_good_classify_activity_tv_six,
								Toast.LENGTH_SHORT).show();
					} else {
						try {
							analysisJsonToShoppingCar(LoginUtil.get_UserId(),
									goodId.get("everyGoodsId"));
						} catch (Exception e) {
						}
					}
				} else {
					Intent intent = new Intent(EveryGoodsClassifyActivity.this,
							LoginActivity.class);
					LoginUtil.rember_activityFrom("EveryGoodsClassifyActivity");
					startActivity(intent);
				}
			} else {
				for (int i = 0; i < typeNumber; i++) {
					System.out.println(hmid.get(listitem.get(i)));
				}
				Toast.makeText(EveryGoodsClassifyActivity.this,
						R.string._every_good_classify_activity_tv_seven,
						Toast.LENGTH_SHORT).show();
				finish();
			}
		default:
			break;
		}
	}
}
