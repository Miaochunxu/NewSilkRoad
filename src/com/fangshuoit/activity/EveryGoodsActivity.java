package com.fangshuoit.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.sliding.AbSlidingPlayView;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.model.EveryGoodsCell;
import com.fangshuoit.model.EveryGoodsType;
import com.fangshuoit.model.GoodToAttribute;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: EveryGoodsActivity
 * @Description: TODO 商品详情
 * @author 方硕IT 缪春旭
 * @date 2015-6-9 上午10:17:17
 * 
 */
public class EveryGoodsActivity extends FragmentActivity implements
		OnClickListener {

	private AbSlidingPlayView mySlidingPlayView = null;// 初始化滑动

	private ImageView img_back, img_shopping, img_collection;

	private TextView tv_buy_now, tv_runto_car;
	// 商品名称、简述、总计销量、累计评价
	private TextView tv_goodsname, tv_goodsjians, tv_allsales, tv_price,
			tv_price_yuan;

	private Intent intent, intentform;

	private AbHttpUtil mAbHttpUtil = null;// 解析json
	private ProgressDialog progressDialog;
	// 记录商品id
	private String goodId = "";
	// 记录收藏id
	private String collectionId = "";

	private int priceMax, pricemin;

	private int disPriceMax, disPricemin;
	// 解析详细单个商品的属性list
	private List<GoodToAttribute> goodToAttributeList;
	// 商品显示信息
	private EveryGoodsCell everyGoodsCellList;
	// 商品属性类型
	private List<EveryGoodsType> everyGoodsTypeList;
	// 记录商品图片地址
	private String[] photoUrlFromHttp;
	// 分类，详情，评论
	private LinearLayout ly_sort, ly_details, ly_comment;

	private Bundle bundle;

	private String ifcollection = "0";

	private ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_every_goods);

		initView();

		initEvent();

	}

	private void initEvent() {
		ly_sort.setOnClickListener(this);
		ly_details.setOnClickListener(this);
		ly_comment.setOnClickListener(this);
		img_back.setOnClickListener(this);
		img_shopping.setOnClickListener(this);
		tv_buy_now.setOnClickListener(this);
		tv_runto_car.setOnClickListener(this);
		img_collection.setOnClickListener(this);
		progressDialog.show();
		try {
			analysisJsonEveryGoods(goodId);
		} catch (Exception e) {
			progressDialog.cancel();
			Toast.makeText(getApplicationContext(),
					R.string._every_good_activity_tv_one, Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void initView() {

		imageLoader = new ImageLoader(EveryGoodsActivity.this, true);

		intentform = getIntent();

		bundle = new Bundle();

		goodId = intentform.getStringExtra("GoodId");

		mAbHttpUtil = AbHttpUtil.getInstance(EveryGoodsActivity.this);// 初始化解析json
		progressDialog = new ProgressDialog(EveryGoodsActivity.this);

		// 初始化显示数据的控件
		initGoodView();

		ly_sort = (LinearLayout) findViewById(R.id.ly_activity_every_goods_look_sort);
		ly_details = (LinearLayout) findViewById(R.id.ly_activity_every_goods_look_details);
		ly_comment = (LinearLayout) findViewById(R.id.ly_activity_every_goods_comment);

		tv_buy_now = (TextView) findViewById(R.id.tv_activity_everygoods_buynow);
		tv_runto_car = (TextView) findViewById(R.id.tv_activity_everygoods_runto_car);

		img_back = (ImageView) findViewById(R.id.img_activity_every_goods_back);
		img_shopping = (ImageView) findViewById(R.id.img_activity_every_goods_shopping_car);
		img_collection = (ImageView) findViewById(R.id.img_collection);

	}

	/**
	 * @Title: initGoodView
	 * @Description: TODO(初始化显示内容的控件)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initGoodView() {
		tv_goodsname = (TextView) findViewById(R.id.activity_every_goods_goodsname);
		tv_goodsjians = (TextView) findViewById(R.id.activity_every_goods_goodjians);
		tv_allsales = (TextView) findViewById(R.id.tv_activity_every_goods_allsales);
		tv_price = (TextView) findViewById(R.id.tv_activity_every_goods_price);
		tv_price_yuan = (TextView) findViewById(R.id.tv_activity_every_goods_price_yuan);
	}

	@SuppressLint("InflateParams")
	private void updataContent(int photoNumber) {
		mySlidingPlayView = (AbSlidingPlayView) findViewById(R.id.mySlidingPlayView);
		LayoutInflater mInflater = LayoutInflater.from(EveryGoodsActivity.this);

		for (int i = 0; i < photoNumber; i++) {
			View mPlayView = mInflater.inflate(R.layout.play_view_item, null);
			ImageView mPlayImage = (ImageView) mPlayView
					.findViewById(R.id.mPlayImage);
			imageLoader.DisplayImage(
					IfHttpToStart.initUr(photoUrlFromHttp[i], "400", "300"),
					mPlayImage);
			mySlidingPlayView.addView(mPlayView);
		}
		mySlidingPlayView.startPlay();
		mySlidingPlayView
				.setOnItemClickListener(new AbSlidingPlayView.AbOnItemClickListener() {
					@Override
					public void onClick(int position) {

					}
				});
	}

	/**
	 * 
	 * @Title: analysisJsonEveryGoods
	 * @Description: TODO(具体商品解析)
	 * @param @param id 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonEveryGoods(String id) {
		// 把所有的全局的list清除
		TourConstants.everyGoodsTypeSortList.clear();
		TourConstants.numberList.clear();
		TourConstants.finishSelfid.clear();
		TourConstants.submitId.clear();
		TourConstants.finishIdList.clear();

		String url = SysConstants.SERVER + SysConstants.EVERYGOODS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				// 第一个列表
				String goodToAttribute = JSONUtils.getString(body,
						"goodToAttribute", "");
				String good = JSONUtils.getString(body, "good", "");
				// 商品显示信息
				String bodyTwo = JSONUtils.getString(good, "body", "");

				String attributeIdToList = JSONUtils.getString(body,
						"attributeIdToList", "");
				// String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					if (!(null == goodToAttribute || goodToAttribute.equals("") || goodToAttribute
							.equals("null"))) {
						// 解析商品最终的属性列表
						// 新建Gson
						Gson gson = new Gson();
						goodToAttributeList = gson.fromJson(goodToAttribute,
								new TypeToken<List<GoodToAttribute>>() {
								}.getType());
						priceMax = (int) Float.parseFloat(goodToAttributeList
								.get(0).getPrice());
						disPriceMax = (int) Float
								.parseFloat(goodToAttributeList.get(0)
										.getDisPrice());
						disPricemin = (int) Float
								.parseFloat(goodToAttributeList.get(0)
										.getDisPrice());
						pricemin = (int) Float.parseFloat(goodToAttributeList
								.get(0).getPrice());
						// 得到价格的最大值于最小值
						for (int i = 0; i < goodToAttributeList.size(); i++) {
							int a = (int) Float.parseFloat(goodToAttributeList
									.get(i).getPrice());
							int b = (int) Float.parseFloat(goodToAttributeList
									.get(i).getDisPrice());
							if (a > priceMax) {
								priceMax = a;
							} else if (a < pricemin) {
								pricemin = a;
							}

							if (b > disPriceMax) {
								disPriceMax = b;
							} else if (b < disPricemin) {
								disPricemin = b;
							}
						}

						for (int i = 0; i < goodToAttributeList.size(); i++) {
							String s3 = goodToAttributeList.get(i)
									.getAttributeId();
							String[] temp = null;
							temp = s3.split(";");
							// 记录attributeId根据“;”分割之后的数据
							List<String> afterId = new ArrayList<String>();
							for (int j = 0; j < temp.length; j++) {
								afterId.add(temp[j]);
							}
							TourConstants.finishIdList.add(afterId);
							TourConstants.submitId.add(goodToAttributeList
									.get(i));
						}

						// 添加商品价格
						tv_price.setText(disPricemin + "-" + disPriceMax);
						tv_price_yuan.setText(" ￥" + pricemin + "-" + priceMax);
					}
					if (!(null == bodyTwo || bodyTwo.equals("") || bodyTwo
							.equals("null"))) {

						Gson gson = new Gson();
						everyGoodsCellList = gson.fromJson(bodyTwo,
								EveryGoodsCell.class);

						tv_goodsname.setText(everyGoodsCellList.getName());
						tv_goodsjians.setText(everyGoodsCellList.getJians());
						tv_allsales.setText(getResources().getText(
								R.string._allsales)
								+ everyGoodsCellList.getSelNo());

						photoUrlFromHttp = everyGoodsCellList.getPhotoUrl()
								.split(";");

						// 加载商品图片
						updataContent(photoUrlFromHttp.length);

					}
					if (!(null == attributeIdToList
							|| attributeIdToList.equals("") || attributeIdToList
							.equals("null"))) {
						Gson gson = new Gson();
						everyGoodsTypeList = gson.fromJson(attributeIdToList,
								new TypeToken<List<EveryGoodsType>>() {
								}.getType());
						SortList();
					}
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

	// 添加收藏
	private void analysisJsonConnection() {
		String url = SysConstants.SERVER + SysConstants.ADDCOLLECTION;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", LoginUtil.get_UserId());
			params.put("idList", goodId);
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
					if (message.toString() != null) {
						img_collection.setImageResource(R.drawable.collection);
						ifcollection = "1";
						collectionId = message.toString();
						Toast.makeText(getApplicationContext(),
								R.string._every_good_activity_tv_two,
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(),
								R.string._every_good_activity_tv_three,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					if (message.toString().equals("collection~Exist")) {
						img_collection.setImageResource(R.drawable.collection);
						ifcollection = "1";
						Toast.makeText(getApplicationContext(),
								R.string._every_good_activity_tv_four,
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(),
								R.string._every_good_activity_tv_five,
								Toast.LENGTH_SHORT).show();
					}
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
	private void analysisJsonConnectionDetele() {
		String url = SysConstants.SERVER + SysConstants.ADDCOLLECTION;
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
						img_collection
								.setImageResource(R.drawable.collection02);
						ifcollection = "0";
						Toast.makeText(getApplicationContext(),
								R.string._every_good_activity_tv_six,
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(),
								R.string._every_good_activity_tv_five,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							R.string._every_good_activity_tv_four,
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

	// 列表排序
	private void SortList() {
		for (int i = 0; i < everyGoodsTypeList.size(); i++) {
			if (everyGoodsTypeList.get(i).getLevel().equals("2")) {
				TourConstants.everyGoodsTypeSortList.add(everyGoodsTypeList
						.get(i));
				for (int j = 0; j < everyGoodsTypeList.size(); j++) {
					if (everyGoodsTypeList.get(j).getLevel().equals("3")
							&& everyGoodsTypeList.get(j).getSelfId()
									.equals(everyGoodsTypeList.get(i).getId())) {
						TourConstants.everyGoodsTypeSortList
								.add(everyGoodsTypeList.get(j));
					}
				}
			}
		}
		for (int i = 0; i < TourConstants.everyGoodsTypeSortList.size(); i++) {
			if (TourConstants.everyGoodsTypeSortList.get(i).getLevel()
					.equals("2")) {
				TourConstants.numberList.add(i);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ly_activity_every_goods_comment:// 查看评论
			intent = new Intent(EveryGoodsActivity.this,
					EveryGoodsCommentActivity.class);
			intent.putExtra("goodId", goodId);
			startActivity(intent);
			break;
		case R.id.ly_activity_every_goods_look_details:// 查看详情
			intent = new Intent(EveryGoodsActivity.this,
					EveryGoodsWebInfoActivity.class);
			intent.putExtra("content", everyGoodsCellList.getContent());

			startActivity(intent);
			break;

		case R.id.img_activity_every_goods_back:
			TourConstants.everyGoodsTypeSortList.clear();
			TourConstants.numberList.clear();
			finish();
			break;

		case R.id.img_activity_every_goods_shopping_car:

			intent = new Intent(EveryGoodsActivity.this,
					ShoppingCarActivity.class);
			startActivity(intent);
			break;

		case R.id.ly_activity_every_goods_look_sort:// 查看、选择分类

			intent = new Intent(EveryGoodsActivity.this,
					EveryGoodsClassifyActivity.class);
			bundle.putString("logo", everyGoodsCellList.getLogo());
			bundle.putString("goodName", everyGoodsCellList.getName());
			bundle.putString("goodJians", everyGoodsCellList.getJians());
			bundle.putString("activityFrom", "buynow");
			intent.putExtras(bundle);
			startActivity(intent);
			this.overridePendingTransition(R.anim.activity_open_translate_b, 0);

			break;

		case R.id.tv_activity_everygoods_buynow:
			intent = new Intent(EveryGoodsActivity.this,
					EveryGoodsClassifyActivity.class);
			bundle.putString("logo", everyGoodsCellList.getLogo());
			bundle.putString("goodName", everyGoodsCellList.getName());
			bundle.putString("goodJians", everyGoodsCellList.getJians());
			bundle.putString("activityFrom", "buynow");
			intent.putExtras(bundle);
			startActivity(intent);
			this.overridePendingTransition(R.anim.activity_open_translate_b, 0);
			break;
		case R.id.tv_activity_everygoods_runto_car:
			intent = new Intent(EveryGoodsActivity.this,
					EveryGoodsClassifyActivity.class);
			bundle.putString("logo", everyGoodsCellList.getLogo());
			bundle.putString("goodName", everyGoodsCellList.getName());
			bundle.putString("activityFrom", "shopping");
			intent.putExtras(bundle);
			startActivity(intent);
			this.overridePendingTransition(R.anim.activity_open_translate_b, 0);
			break;
		case R.id.img_collection:
			// 添加收藏
			if (LoginUtil.get_is_Login()) {
				if (ifcollection.equals("0")) {
					progressDialog.show();
					try {
						analysisJsonConnection();
					} catch (Exception e) {
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(),
								R.string._every_good_activity_tv_seven,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					analysisJsonConnectionDetele();
				}
			} else {
				intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @Title: ToEveryGoodsClassifyActivity
	 * @Description: TODO(分类框出现)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void ToEveryGoodsClassifyActivity(String t) {
		intent = new Intent(EveryGoodsActivity.this,
				EveryGoodsClassifyActivity.class);
		LoginUtil.rember_activityFrom(t);
		bundle.putString("logo", everyGoodsCellList.getLogo());
		intent.putExtras(bundle);
		startActivity(intent);
		this.overridePendingTransition(R.anim.activity_open_translate_b, 0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			TourConstants.everyGoodsTypeSortList.clear();
			TourConstants.numberList.clear();
			finish();
		}
		return false;
	}

	/**
	 * @Title: activityTo
	 * @Description: TODO(自定义跳转)
	 * @param 设定文件
	 * @return void
	 */
	private void activityTo() {
		EveryGoodsActivity.this.overridePendingTransition(
				R.anim.activity_open_translate_right,
				R.anim.activity_close_translate_lift);
	}
}
