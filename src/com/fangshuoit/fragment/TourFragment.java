package com.fangshuoit.fragment;

import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.sliding.AbSlidingPlayView;
import com.fangshuoit.activity.ChangePlaceActivity;
import com.fangshuoit.activity.ChineseTourActivity;
import com.fangshuoit.activity.EntranceTicketActivity;
import com.fangshuoit.activity.EveryGoodsActivity;
import com.fangshuoit.activity.FoodActivity;
import com.fangshuoit.activity.GroupTourActivity;
import com.fangshuoit.activity.GuideListActivity;
import com.fangshuoit.activity.TourRecordActivity;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.model.TourAvds;
import com.fangshuoit.model.TourHomeData;
import com.fangshuoit.newsilkroad.MainActivity;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.AsyncImageLoader;
import com.fangshuoit.tool.AsyncImageLoader.ImageCallback;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.fangshuoit.view.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: TourFragment
 * @Description: TODO 主页Fragment（旅游页）
 * @author 方硕IT 缪春旭
 * @date 2015-5-20 下午5:02:30
 * 
 */
@SuppressLint("InflateParams")
public class TourFragment extends Fragment implements OnClickListener {

	private View view;
	private AbSlidingPlayView mSlidingPlayView = null;// 初始化滑动
	AsyncImageLoader asyncImageLoader;

	private MyGridView lv_bigtour;

	private LinearLayout ly_change_place;

	private TextView tv_left, tv_dj_title_one, tv_dj_title_two,
			tv_dj_title_three, tv_dj_title_four, tv_dj_context_one;
	private TextView tv_ly_title_one, tv_ly_title_two, tv_ly_title_three,
			tv_ly_title_four, tv_ly_title_five, tv_ly_context_one;

	// 记录图片地址
	private String DjUrlone = "", DjUrltwo = "", DjUrlthree = "",
			DjUrlfour = "";
	private String LyUrlone = "", LyUrltwo = "", LyUrlthree = "",
			LyUrlfour = "", LyUrlfive = "";

	private ImageView img_dj_one, img_dj_two, img_dj_three, img_dj_four;
	// 旅游向导控件
	private ImageView img_ly_one, img_ly_two, img_ly_three, img_ly_four,
			img_ly_five;

	private List<TourHomeData> tourHomeData, tourHomeDataTwo,
			tourHomeDataThree, tourHomeDataFour;

	private AbHttpUtil mAbHttpUtil = null;// 解析JSON
	private ProgressDialog progressDialog;

	private List<TourAvds> tourAvds;

	private McxBaseAdapter<TourHomeData> Bigadapter;
	private LinearLayout img_ticket, img_grouptour, img_tour_record, img_food,
			img_guoneiyou, img_daoyou, img_lvxingshe, img_chujingyou;
	private Intent intent;

	private ImageLoader imageLoader;

	// 语言选择框
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private static final String[] m = { "中文", "English", "العَرَبِيَّة‎‎‎" };

	@SuppressWarnings("unused")
	private TableLayout tableLayout;
	int[] location = new int[2];
	int[] location2 = new int[2];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_tour, container, false);

		initView();

		initEvent();

		return view;
	}

	private void updataAdvert() {
		mSlidingPlayView = (AbSlidingPlayView) view
				.findViewById(R.id.mAbSlidingPlayView);
		asyncImageLoader = new AsyncImageLoader(getActivity());
		LayoutInflater mInflater = LayoutInflater.from(getActivity());
		for (int i = 0; i < tourAvds.size(); i++) {

			View mPlayView = mInflater.inflate(R.layout.play_view_item, null);
			ImageView mPlayImage = (ImageView) mPlayView
					.findViewById(R.id.mPlayImage);
			imageLoader.DisplayImage(IfHttpToStart.initUr(tourAvds.get(i)
					.getUrls(), "320", "120"), mPlayImage);
			mSlidingPlayView.addView(mPlayView);
		}
		mSlidingPlayView.startPlay();
		mSlidingPlayView
				.setOnItemClickListener(new AbSlidingPlayView.AbOnItemClickListener() {
					@Override
					public void onClick(int position) {
						// AbToastUtil.showToast(getActivity(), "点击" +
						// position);
						// Intent intent = new Intent();
						// intent.setClass(getActivity(),
						// ContentViewActivity.class);
						// startActivity(intent);
					}
				});
	}

	private void initEvent() {

		img_ticket.setOnClickListener(this);
		img_grouptour.setOnClickListener(this);
		img_tour_record.setOnClickListener(this);
		img_food.setOnClickListener(this);
		img_daoyou.setOnClickListener(this);
		img_lvxingshe.setOnClickListener(this);
		img_guoneiyou.setOnClickListener(this);
		img_chujingyou.setOnClickListener(this);

		img_dj_one.setOnClickListener(this);
		img_dj_two.setOnClickListener(this);
		img_dj_three.setOnClickListener(this);
		img_dj_four.setOnClickListener(this);

		img_ly_one.setOnClickListener(this);
		img_ly_two.setOnClickListener(this);
		img_ly_three.setOnClickListener(this);
		img_ly_four.setOnClickListener(this);
		img_ly_five.setOnClickListener(this);

		ly_change_place.setOnClickListener(this);

		progressDialog.setMessage("正在玩命加载~");
		progressDialog.show();

		analysisJsonHomeAds();

		try {
			// JSON解析独家推荐
			analysisJsonTourHome(TourConstants.DJTJ);
			// JSON解析旅游向导
			analysisJsonTourHomeLYXD(TourConstants.LYXD);
			// JSON解析GridView数据
			analysisJsonGridViewData(TourConstants.GDTJ);
		} catch (Exception e) {
			progressDialog.cancel();
		}
	}

	private void initView() {
		imageLoader = new ImageLoader(getActivity(), true);

		mAbHttpUtil = AbHttpUtil.getInstance(getActivity());// 初始化解析json
		progressDialog = new ProgressDialog(getActivity());
		asyncImageLoader = new AsyncImageLoader(getActivity());

		// 初始化独家推荐控件以及旅游向导控件
		initDjView();

		tv_left = (TextView) view.findViewById(R.id.tv_left);

		lv_bigtour = (MyGridView) view.findViewById(R.id.lv_fragment_bigTour);
		lv_bigtour.setSelector(new ColorDrawable(Color.TRANSPARENT));
		ly_change_place = (LinearLayout) view
				.findViewById(R.id.ly_change_place);

		tableLayout = (TableLayout) view
				.findViewById(R.id.tablela_fragment_tour);
		spinnerLanguage();

		// scrollView.smoothScrollTo(0, 20);

		// img_language = (ImageView) view.findViewById(R.id.img_language);

		img_ticket = (LinearLayout) view.findViewById(R.id.ly_fragment_ticket);
		img_grouptour = (LinearLayout) view
				.findViewById(R.id.ly_fragment_zutuanyou);
		img_tour_record = (LinearLayout) view
				.findViewById(R.id.ly_fragment_youji);
		img_food = (LinearLayout) view.findViewById(R.id.ly_food);
		img_daoyou = (LinearLayout) view.findViewById(R.id.ly_fragment_daoyou);
		img_lvxingshe = (LinearLayout) view
				.findViewById(R.id.ly_fragment_lvxingshe);
		img_guoneiyou = (LinearLayout) view
				.findViewById(R.id.ly_fragment_guoneiyou);
		img_chujingyou = (LinearLayout) view
				.findViewById(R.id.ly_fragment_chujingyou);

	}

	// 初始化独家推荐控件、旅游控件
	private void initDjView() {
		// 独家推荐
		tv_dj_title_one = (TextView) view
				.findViewById(R.id.tv_fragment_tour_typeone_one_title);
		tv_dj_title_two = (TextView) view
				.findViewById(R.id.tv_fragment_tour_typeone_two_title);
		tv_dj_title_three = (TextView) view
				.findViewById(R.id.tv_fragment_tour_typeone_three_title);
		tv_dj_title_four = (TextView) view
				.findViewById(R.id.tv_fragment_tour_typeone_four_title);
		tv_dj_context_one = (TextView) view
				.findViewById(R.id.tv_fragment_tour_typeone_one_context);

		img_dj_one = (ImageView) view
				.findViewById(R.id.img_fragment_tour_typeone_one_img);
		img_dj_two = (ImageView) view
				.findViewById(R.id.img_fragment_tour_typeone_two_img);
		img_dj_three = (ImageView) view
				.findViewById(R.id.img_fragment_tour_typeone_three_img);
		img_dj_four = (ImageView) view
				.findViewById(R.id.img_fragment_tour_typeone_four_img);

		// 旅游向导
		tv_ly_context_one = (TextView) view
				.findViewById(R.id.tv_fragment_tour_typetwo_one_context);
		tv_ly_title_one = (TextView) view
				.findViewById(R.id.tv_fragment_tour_typetwo_one_title);
		tv_ly_title_two = (TextView) view
				.findViewById(R.id.tv_fragment_tour_typetwo_two_title);
		tv_ly_title_three = (TextView) view
				.findViewById(R.id.tv_fragment_tour_typetwo_three_title);
		tv_ly_title_four = (TextView) view
				.findViewById(R.id.tv_fragment_tour_typetwo_four_title);
		tv_ly_title_five = (TextView) view
				.findViewById(R.id.tv_fragment_tour_typetwo_five_title);

		img_ly_one = (ImageView) view
				.findViewById(R.id.img_fragment_tour_typetwo_one_img);
		img_ly_two = (ImageView) view
				.findViewById(R.id.img_fragment_tour_typetwo_two_img);
		img_ly_three = (ImageView) view
				.findViewById(R.id.img_fragment_tour_typetwo_three_img);
		img_ly_four = (ImageView) view
				.findViewById(R.id.img_fragment_tour_typetwo_four_img);
		img_ly_five = (ImageView) view
				.findViewById(R.id.img_fragment_tour_typetwo_five_img);

	}

	/**
	 * @author FangshuoIT 缪春旭
	 * @Title: analysisJsonTourHome
	 * @Description: TODO(解析首页独家推荐内容)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonTourHome(String id) {
		String url = SysConstants.SERVER + SysConstants.TOURHOMEDATA;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("idList", "1");
			params.put("typeId", id);
			params.put("num", "4");

		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				// String message = JSONUtils.getString(content, "message", "");

				if (code.equals("0")) {
					if (!(null == body || body.equals("") || body
							.equals("null"))) {
						// 新建Gson
						Gson gson = new Gson();
						// 获取旅游首页信息
						tourHomeData = gson.fromJson(body,
								new TypeToken<List<TourHomeData>>() {
								}.getType());
						tv_dj_title_one.setText(tourHomeData.get(0).getName());
						tv_dj_title_two.setText(tourHomeData.get(1).getName());
						tv_dj_title_three
								.setText(tourHomeData.get(2).getName());
						tv_dj_title_four.setText(tourHomeData.get(3).getName());
						tv_dj_context_one.setText(tourHomeData.get(0)
								.getJians());
						// 得到图片地址
						DjUrlone = IfHttpToStart.initUr(tourHomeData.get(0)
								.getLogo(), "180", "165");
						DjUrltwo = IfHttpToStart.initUr(tourHomeData.get(1)
								.getLogo(), "180", "165");
						DjUrlthree = IfHttpToStart.initUr(tourHomeData.get(2)
								.getLogo(), "180", "165");
						DjUrlfour = IfHttpToStart.initUr(tourHomeData.get(3)
								.getLogo(), "180", "165");

						// 加载首页独家推荐图片
						loadingMainImage(TourConstants.DJTJ);
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
	 * @Title: analysisJsonTourHomeLYXD
	 * @Description: TODO(解析JSON，旅游向导)
	 * @param @param id 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonTourHomeLYXD(String id) {
		String url = SysConstants.SERVER + SysConstants.TOURHOMEDATA;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("idList", "1");
			params.put("typeId", id);
			params.put("num", "5");

		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				// String message = JSONUtils.getString(content, "message", "");

				if (code.equals("0")) {
					if (!(null == body || body.equals("") || body
							.equals("null"))) {
						// 新建Gson
						Gson gson = new Gson();
						// 获取旅游首页信息
						tourHomeDataTwo = gson.fromJson(body,
								new TypeToken<List<TourHomeData>>() {
								}.getType());
						tv_ly_title_one.setText(tourHomeDataTwo.get(0)
								.getName());
						tv_ly_title_two.setText(tourHomeDataTwo.get(1)
								.getName());
						tv_ly_title_three.setText(tourHomeDataTwo.get(2)
								.getName());
						tv_ly_title_four.setText(tourHomeDataTwo.get(3)
								.getName());
						tv_ly_title_five.setText(tourHomeDataTwo.get(4)
								.getName());
						tv_ly_context_one.setText(tourHomeDataTwo.get(0)
								.getJians());
						// 得到图片地址
						LyUrlone = IfHttpToStart.initUr(tourHomeDataTwo.get(0)
								.getLogo(), "180", "165");
						LyUrltwo = IfHttpToStart.initUr(tourHomeDataTwo.get(1)
								.getLogo(), "180", "165");
						LyUrlthree = IfHttpToStart.initUr(tourHomeDataTwo
								.get(2).getLogo(), "180", "165");
						LyUrlfour = IfHttpToStart.initUr(tourHomeDataTwo.get(3)
								.getLogo(), "180", "165");
						LyUrlfive = IfHttpToStart.initUr(tourHomeDataTwo.get(4)
								.getLogo(), "180", "165");

						// 加载首页独家推荐图片
						loadingMainImage(TourConstants.LYXD);
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

	/**
	 * 
	 * @Title: analysisJsonGridViewData
	 * @Description: TODO( 解析GridView内容 )
	 * @param @param id 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonGridViewData(String id) {
		String url = SysConstants.SERVER + SysConstants.TOURHOMEDATA;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("idList", "1");
			params.put("typeId", id);
			params.put("num", "6");

		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				// String message = JSONUtils.getString(content, "message", "");

				if (code.equals("0")) {
					if (!(null == body || body.equals("") || body
							.equals("null"))) {
						// 新建Gson
						Gson gson = new Gson();
						// 获取旅游首页信息
						tourHomeDataThree = gson.fromJson(body,
								new TypeToken<List<TourHomeData>>() {
								}.getType());

						Bigadapter = new McxBaseAdapter<TourHomeData>(
								getActivity(), R.layout.fragment_tour_bigitem) {

							@Override
							protected void initListCell(int position,
									View listCell, ViewGroup parent) {
								TourHomeData da = getItem(position);

								ImageView img = (ImageView) listCell
										.findViewById(R.id.img_fragment_img);

								TextView tv_big = (TextView) listCell
										.findViewById(R.id.tv_fragment_tour_bigtv);
								tv_big.setText(da.getName());
								TextView tv = (TextView) listCell
										.findViewById(R.id.tv_fragment_tour_smalltv);
								tv.setText(da.getJians());

								String img_url = IfHttpToStart.initUr(
										da.getLogo(), "160", "150");

								img.setTag(img_url);
								Bitmap cachedImageone = asyncImageLoader
										.loadBitmap(img_url,
												new ImageCallback() {
													public void imageLoaded(
															Bitmap imageDrawable,
															String imageUrl) {
														ImageView imageViewByTag = (ImageView) view
																.findViewWithTag(imageUrl);
														if (imageViewByTag != null) {
															imageViewByTag
																	.setImageBitmap(imageDrawable);
														}
													}
												});

								// 如果找不到图片,就显示默认的空的图片
								if (cachedImageone == null) {
									img.setImageResource(R.drawable.image_loading);
								} else {
									img.setImageBitmap(cachedImageone);
								}
							}
						};

						lv_bigtour.setAdapter(Bigadapter);
						Bigadapter.addUpdata(tourHomeDataThree);
						lv_bigtour
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										TourHomeData da = tourHomeDataThree
												.get(position);
										intent = new Intent(getActivity(),
												EveryGoodsActivity.class);
										intent.putExtra("GoodId", da.getId());
										startActivity(intent);
									}
								});
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

	/**
	 * 
	 * @Title: loadingMainImage
	 * @Description: TODO(加载图片)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void loadingMainImage(String type) {

		if (type.equals(TourConstants.DJTJ)) {

			// 开始加载背景图片第一个
			img_dj_one.setTag(DjUrlone);
			Bitmap cachedImageone = asyncImageLoader.loadBitmap(DjUrlone,
					new ImageCallback() {
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) view
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}
					});

			// 如果找不到图片,就显示默认的空的图片
			if (cachedImageone == null) {
				img_dj_one.setImageResource(R.drawable.image_loading);
			} else {
				img_dj_one.setImageBitmap(cachedImageone);
			}
			// 开始加载背景图片第二个
			img_dj_two.setTag(DjUrltwo);
			Bitmap cachedImagetwo = asyncImageLoader.loadBitmap(DjUrltwo,
					new ImageCallback() {
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) view
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}
					});

			if (cachedImagetwo == null) {
				img_dj_two.setImageResource(R.drawable.image_loading);
			} else {
				img_dj_two.setImageBitmap(cachedImagetwo);
			}

			// 开始加载背景图片第三个
			img_dj_three.setTag(DjUrlthree);
			Bitmap cachedImagethree = asyncImageLoader.loadBitmap(DjUrlthree,
					new ImageCallback() {
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) view
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}
					});
			if (cachedImagethree == null) {
				img_dj_three.setImageResource(R.drawable.image_loading);
			} else {
				img_dj_three.setImageBitmap(cachedImagethree);
			}

			// 开始加载背景图片第四个
			img_dj_four.setTag(DjUrlfour);
			Bitmap cachedImagefour = asyncImageLoader.loadBitmap(DjUrlfour,
					new ImageCallback() {
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) view
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}
					});

			// 如果找不到图片,就显示默认的空的图片
			if (cachedImagefour == null) {
				img_dj_four.setImageResource(R.drawable.image_loading);
			} else {
				img_dj_four.setImageBitmap(cachedImagefour);
			}
		} else if (type.equals(TourConstants.LYXD)) {

			// 开始加载背景图片第一个
			img_ly_one.setTag(LyUrlone);
			Bitmap cachedImageone = asyncImageLoader.loadBitmap(LyUrlone,
					new ImageCallback() {
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) view
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}
					});

			// 如果找不到图片,就显示默认的空的图片
			if (cachedImageone == null) {
				img_ly_one.setImageResource(R.drawable.image_loading);
			} else {
				img_ly_one.setImageBitmap(cachedImageone);
			}
			// 开始加载背景图片第二个
			img_ly_two.setTag(LyUrltwo);
			Bitmap cachedImagetwo = asyncImageLoader.loadBitmap(LyUrltwo,
					new ImageCallback() {
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) view
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}
					});

			if (cachedImagetwo == null) {
				img_ly_two.setImageResource(R.drawable.image_loading);
			} else {
				img_ly_two.setImageBitmap(cachedImagetwo);
			}

			// 开始加载背景图片第三个
			img_ly_three.setTag(LyUrlthree);
			Bitmap cachedImagethree = asyncImageLoader.loadBitmap(LyUrlthree,
					new ImageCallback() {
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) view
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}
					});
			if (cachedImagethree == null) {
				img_ly_three.setImageResource(R.drawable.image_loading);
			} else {
				img_ly_three.setImageBitmap(cachedImagethree);
			}

			// 开始加载背景图片第四个
			img_ly_four.setTag(LyUrlfour);
			Bitmap cachedImagefour = asyncImageLoader.loadBitmap(LyUrlfour,
					new ImageCallback() {
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) view
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}
					});

			// 如果找不到图片,就显示默认的空的图片
			if (cachedImagefour == null) {
				img_ly_four.setImageResource(R.drawable.image_loading);
			} else {
				img_ly_four.setImageBitmap(cachedImagefour);
			}
			// 开始加载背景图片第五个
			img_ly_five.setTag(LyUrlfive);
			Bitmap cachedImagefive = asyncImageLoader.loadBitmap(LyUrlfive,
					new ImageCallback() {
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) view
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageBitmap(imageDrawable);
							}
						}
					});

			// 如果找不到图片,就显示默认的空的图片
			if (cachedImagefive == null) {
				img_ly_five.setImageResource(R.drawable.image_loading);
			} else {
				img_ly_five.setImageBitmap(cachedImagefour);
			}

		}

		progressDialog.cancel();
	}

	/**
	 * @Title: spinnerLanguage
	 * @author FangshuoIT 缪春旭
	 * @Description: TODO(选择语言)
	 * @return void
	 * @throws
	 */
	private void spinnerLanguage() {
		spinner = (Spinner) view.findViewById(R.id.spinner_language);

		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, m);

		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner.setAdapter(adapter);

		spinner.setSelection(LoginUtil.get_language(), true);
		// 添加事件Spinner事件监听
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Resources resources = getResources();// 获得res资源对象
				Configuration config = resources.getConfiguration();// 获得设置对象
				DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
				if (position == 0) {
					if (config.locale != Locale.SIMPLIFIED_CHINESE) {
						config.locale = Locale.SIMPLIFIED_CHINESE;// 简体中文
						resources.updateConfiguration(config, dm);
						LoginUtil.remberlanguage(0);
						intent = new Intent(getActivity(), MainActivity.class);
						startActivity(intent);
						getActivity().finish();
						getActivity().overridePendingTransition(
								R.anim.activity_open_scale,
								R.anim.activity_close_scale);
					}
				}
				if (position == 1) {
					config.locale = Locale.ENGLISH; // 英语
					resources.updateConfiguration(config, dm);
					LoginUtil.remberlanguage(1);
					intent = new Intent(getActivity(), MainActivity.class);
					startActivity(intent);
					getActivity().finish();
					getActivity().overridePendingTransition(
							R.anim.activity_open_scale,
							R.anim.activity_close_scale);
				} else if (position == 2) {
					config.locale = new Locale("ar");// 阿拉伯语
					resources.updateConfiguration(config, dm);
					LoginUtil.remberlanguage(2);
					intent = new Intent(getActivity(), MainActivity.class);
					startActivity(intent);
					getActivity().finish();
					getActivity().overridePendingTransition(
							R.anim.activity_open_scale,
							R.anim.activity_close_scale);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// 设置默认值
		spinner.setVisibility(View.VISIBLE);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ly_change_place:
			intent = new Intent(getActivity(), ChangePlaceActivity.class);
			startActivity(intent);
			break;
		case R.id.ly_fragment_ticket:
			intent = new Intent(getActivity(), EntranceTicketActivity.class);
			startActivity(intent);
			break;
		case R.id.ly_fragment_zutuanyou:
			intent = new Intent(getActivity(), GroupTourActivity.class);
			startActivity(intent);
			break;
		case R.id.ly_fragment_youji:
			intent = new Intent(getActivity(), TourRecordActivity.class);
			startActivity(intent);
			break;
		case R.id.ly_food:
			intent = new Intent(getActivity(), FoodActivity.class);
			startActivity(intent);
			break;
		case R.id.ly_fragment_daoyou:
			intent = new Intent(getActivity(), GuideListActivity.class);
			intent.putExtra("topTitle", "导游");
			startActivity(intent);
			break;
		case R.id.ly_fragment_lvxingshe:
			intent = new Intent(getActivity(), GuideListActivity.class);
			intent.putExtra("topTitle", "旅行社");
			startActivity(intent);
			break;
		case R.id.ly_fragment_guoneiyou:
			intent = new Intent(getActivity(), ChineseTourActivity.class);
			intent.putExtra("topTitle", "国内游");
			startActivity(intent);
			break;
		case R.id.ly_fragment_chujingyou:
			intent = new Intent(getActivity(), ChineseTourActivity.class);
			intent.putExtra("topTitle", "出境游");
			startActivity(intent);
			break;
		case R.id.img_fragment_tour_typeone_one_img:
			intent = new Intent(getActivity(), EveryGoodsActivity.class);
			intent.putExtra("GoodId", tourHomeData.get(0).getId());
			startActivity(intent);
			break;
		case R.id.img_fragment_tour_typeone_two_img:
			intent = new Intent(getActivity(), EveryGoodsActivity.class);
			intent.putExtra("GoodId", tourHomeData.get(1).getId());
			startActivity(intent);
			break;
		case R.id.img_fragment_tour_typeone_three_img:
			intent = new Intent(getActivity(), EveryGoodsActivity.class);
			intent.putExtra("GoodId", tourHomeData.get(2).getId());
			startActivity(intent);
			break;
		case R.id.img_fragment_tour_typeone_four_img:
			intent = new Intent(getActivity(), EveryGoodsActivity.class);
			intent.putExtra("GoodId", tourHomeData.get(3).getId());
			startActivity(intent);
			break;
		case R.id.img_fragment_tour_typetwo_one_img:
			intent = new Intent(getActivity(), EveryGoodsActivity.class);
			intent.putExtra("GoodId", tourHomeDataTwo.get(0).getId());
			startActivity(intent);
			break;
		case R.id.img_fragment_tour_typetwo_two_img:
			intent = new Intent(getActivity(), EveryGoodsActivity.class);
			intent.putExtra("GoodId", tourHomeDataTwo.get(1).getId());
			startActivity(intent);
			break;
		case R.id.img_fragment_tour_typetwo_three_img:
			intent = new Intent(getActivity(), EveryGoodsActivity.class);
			intent.putExtra("GoodId", tourHomeDataTwo.get(2).getId());
			startActivity(intent);
			break;
		case R.id.img_fragment_tour_typetwo_four_img:
			intent = new Intent(getActivity(), EveryGoodsActivity.class);
			intent.putExtra("GoodId", tourHomeDataTwo.get(3).getId());
			startActivity(intent);
			break;
		case R.id.img_fragment_tour_typetwo_five_img:
			intent = new Intent(getActivity(), EveryGoodsActivity.class);
			intent.putExtra("GoodId", tourHomeDataTwo.get(4).getId());
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onResume() {
		try {
			tv_left.setText(LoginUtil.get_Place());
		} catch (Exception e) {
		}

		super.onResume();
	}

	/**
	 * 
	 * @Title: analysisJsonGridViewLoadData
	 * @Description: TODO(GridView加载更多)
	 * @param @param id 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonGridViewLoadData(String id) {
		String url = SysConstants.SERVER + SysConstants.TOURHOMEDATA;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("idList", "1");
			params.put("typeId", id);
			params.put("num", "6");

		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				// String message = JSONUtils.getString(content, "message", "");

				if (code.equals("0")) {
					if (!(null == body || body.equals("") || body
							.equals("null"))) {
						// 新建Gson
						Gson gson = new Gson();
						// 获取旅游首页信息
						tourHomeDataFour = gson.fromJson(body,
								new TypeToken<List<TourHomeData>>() {
								}.getType());

						Bigadapter.addOffdata(tourHomeDataFour);
						Bigadapter.notifyDataSetChanged();
					}
				} else {
					Toast.makeText(getActivity(), "更新失败~再来一次吧",
							Toast.LENGTH_SHORT).show();
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

	/**
	 * 
	 * @Title:
	 * @Description:TODO 加载广告
	 * @param
	 * @return void
	 * @throwsTo
	 */
	private void analysisJsonHomeAds() {
		String url = SysConstants.SERVER + SysConstants.TOURHOMEADS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				// String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					if (!(null == body || body.equals("") || body
							.equals("null"))) {
						Gson gson = new Gson();
						tourAvds = gson.fromJson(body,
								new TypeToken<List<TourAvds>>() {
								}.getType());
						updataAdvert();
					}
				} else {

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

	// /**
	// * 上拉事件监听
	// */
	// @Override
	// public void onFooterRefresh(PullToRefreshView view) {
	// mPullToRefreshView.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// try {
	// analysisJsonGridViewLoadData(TourConstants.GDTJ);
	// } catch (Exception e) {
	// Toast.makeText(getActivity(), "加载失败~再来一次吧",
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	// }, 1000);
	// }
	//
	// /**
	// * 下拉事件监听
	// */
	// @Override
	// public void onHeaderRefresh(PullToRefreshView view) {
	// mPullToRefreshView.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// try {
	// // JSON解析独家推荐
	// analysisJsonTourHome(TourConstants.DJTJ);
	// // JSON解析旅游向导
	// analysisJsonTourHomeLYXD(TourConstants.LYXD);
	// } catch (Exception e) {
	// Toast.makeText(getActivity(), "刷新失败啦", Toast.LENGTH_SHORT)
	// .show();
	// }
	//
	// mPullToRefreshView.onHeaderRefreshComplete();
	//
	// }
	// }, 1000);
	// }

}
