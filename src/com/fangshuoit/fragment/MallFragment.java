package com.fangshuoit.fragment;

import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.ab.view.sliding.AbSlidingPlayView;
import com.fangshuoit.activity.EveryGoodsActivity;
import com.fangshuoit.activity.MallSortActivity;
import com.fangshuoit.activity.SearchActivity;
import com.fangshuoit.activity.SortGoodsActivity;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.application.TourConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.model.MallHome;
import com.fangshuoit.model.MallHomeDataGD;
import com.fangshuoit.model.SortGoodsCell;
import com.fangshuoit.model.TourAvds;
import com.fangshuoit.model.TourHomeData;
import com.fangshuoit.newsilkroad.MainActivity;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.pulltorefresh.library.PullToRefreshBase;
import com.fangshuoit.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.fangshuoit.pulltorefresh.library.PullToRefreshScrollView;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.fangshuoit.view.CustomProgressDialog;
import com.fangshuoit.view.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName: MallFragment
 * @Description: TODO 商城Fragment
 * @author 方硕IT 缪春旭
 * @date 2015-5-20 下午3:46:42
 * 
 */
public class MallFragment extends Fragment implements OnClickListener {

	private View view;

	private AbSlidingPlayView mSlidingPlayView = null;// 初始化滑动

	private GridView ge_mall;

	private McxBaseAdapter<MallHomeDataGD> adapter;

	private ArrayAdapter<String> adapterl;

	private MyGridView lv_bigtour;

	private LinearLayout ly_sort, ly_recommend;

	private Intent intent;

	private List<MallHomeDataGD> dataGD;

	private PullToRefreshScrollView scrollView;

	private List<TourAvds> tourAvds;

	private AbHttpUtil mAbHttpUtil = null;// 解析json
	private CustomProgressDialog mprogressDialog = null;

	// 语言选择框
	private Spinner spinner;

	private static final String[] m = { "中文", "English", "العَرَبِيَّة‎‎‎" };

	private List<MallHome> mallHomeData;

	private ImageView mall_one, mall_two, mall_three, mall_four;

	private ImageView img_search;
	// 搜索
	private TextView tv_one, tv_two, tv_three, tv_four;

	private TextView tv_bottom;

	private TextView tv_dj_title_one, tv_dj_title_two, tv_dj_title_three,
			tv_dj_title_four, tv_dj_context_one;
	private List<TourHomeData> tourHomeData;

	private List<SortGoodsCell> tourHomeDataThree, tourHomeDataFour;

	private ImageView img_dj_one, img_dj_two, img_dj_three, img_dj_four;

	// 记录图片地址
	private String DjUrlone = "", DjUrltwo = "", DjUrlthree = "",
			DjUrlfour = "";

	private McxBaseAdapter<SortGoodsCell> Bigadapter;

	private String TextUrlone = "", TextUrltwo = "", TextUrlthree = "",
			TextUrlfour = "";

	private String idone = "", idtwo = "", idthree = "", idfour = "";

	private ImageLoader imageLoader;

	private int numberLoadMore = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mall, container, false);

		initView();

		initEvent();

		return view;
	}

	private void initEvent() {
		ly_sort.setOnClickListener(this);
		ly_recommend.setOnClickListener(this);
		img_search.setOnClickListener(this);
		mall_one.setOnClickListener(this);
		mall_two.setOnClickListener(this);
		mall_three.setOnClickListener(this);
		mall_four.setOnClickListener(this);
		img_dj_one.setOnClickListener(this);
		img_dj_two.setOnClickListener(this);
		img_dj_three.setOnClickListener(this);
		img_dj_four.setOnClickListener(this);

		mprogressDialog.show();
		mprogressDialog.setCanceledOnTouchOutside(false);
		try {
			analysisJsonHomeAds();
		} catch (Exception e) {
			mprogressDialog.cancel();
			AbToastUtil.showToast(getActivity(), R.string._data_error_load);
		}

		scrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				// String label = DateUtils.formatDateTime(getActivity(),
				// System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
				// | DateUtils.FORMAT_SHOW_DATE
				// | DateUtils.FORMAT_ABBREV_ALL);
				//
				// refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// 得到Scrollview的Y轴的坐标,小于0则说明在头部，大于0则在底部
				float Sy = scrollView.getScrollY();

				if (Sy <= 0f) {
					// 刷新方法
					scrollView.postDelayed(new Runnable() {

						@Override
						public void run() {
							try {
								analysisJsonHomeAds();
							} catch (Exception e) {
								Toast.makeText(getActivity(),
										R.string._refurbish_error,
										Toast.LENGTH_SHORT).show();
							}
						}
					}, 1000);
				} else {
					// 加载方法
					scrollView.postDelayed(new Runnable() {

						@Override
						public void run() {
							try {
								numberLoadMore += 10;
								analysisJsonGridViewLoadData(
										TourConstants.GDTJ, numberLoadMore);
							} catch (Exception e) {
								Toast.makeText(getActivity(),
										R.string._refurbish_null,
										Toast.LENGTH_SHORT).show();
							}
						}
					}, 500);
				}

			}
		});
		scrollView.setPullToRefreshOverScrollEnabled(true);
	}

	private void initView() {

		LoginUtil.rember_activityFrom("MallFragment");

		imageLoader = new ImageLoader(getActivity(), true);

		scrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.sv_fragment_mall);
		scrollView.scrollTo(0, 20);

		tv_bottom = (TextView) view.findViewById(R.id.tv_fragment_mall_bottom);

		mAbHttpUtil = AbHttpUtil.getInstance(getActivity());// 初始化解析json
		mprogressDialog = CustomProgressDialog.createDialog(getActivity());
		mprogressDialog.setCanceledOnTouchOutside(false);
		spinnerLanguage();

		ly_sort = (LinearLayout) view.findViewById(R.id.ly_fragment_mall_sort);
		ly_recommend = (LinearLayout) view
				.findViewById(R.id.ly_fragment_mall_recommend);

		mall_one = (ImageView) view.findViewById(R.id.img_fragment_mall_one);

		mall_two = (ImageView) view.findViewById(R.id.img_fragment_mall_two);

		mall_three = (ImageView) view
				.findViewById(R.id.img_fragment_mall_three);

		mall_four = (ImageView) view.findViewById(R.id.img_fragment_mall_four);

		// #########
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

		lv_bigtour = (MyGridView) view
				.findViewById(R.id.lv_fragment_mall_bottom);
		lv_bigtour.setSelector(new ColorDrawable(Color.TRANSPARENT));
		// #########

		img_search = (ImageView) view
				.findViewById(R.id.img_fragment_mall_search);

		tv_one = (TextView) view.findViewById(R.id.tv_fragment_mall_textone);
		tv_two = (TextView) view.findViewById(R.id.tv_fragment_mall_texttwo);
		tv_three = (TextView) view
				.findViewById(R.id.tv_fragment_mall_textthree);
		tv_four = (TextView) view.findViewById(R.id.tv_fragment_mall_textfour);

		mallImage();

		ge_mall = (GridView) view.findViewById(R.id.gv_fragment_mall);
		ge_mall.setFocusable(false);
	}

	/**
	 * @Title: spinnerLanguage
	 * @author FangshuoIT 缪春旭
	 * @Description: TODO(选择语言)
	 * @return void
	 * @throws
	 */
	private void spinnerLanguage() {
		spinner = (Spinner) view.findViewById(R.id.spinner_language_mall);

		// 将可选内容与ArrayAdapter连接起来
		adapterl = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, m);

		// 设置下拉列表的风格
		adapterl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner.setAdapter(adapterl);

		if (LoginUtil.get_language() == 1) {
			spinner.setSelection(1, false);
			// if (Locale.getDefault().getLanguage().equals("ar")) {
			//
			// } else if (Locale.getDefault().getLanguage().equals("en")) {
			//
			// } else {
			//
			// }
		} else if (LoginUtil.get_language() == 2) {
			spinner.setSelection(2, false);
			// spinner.setSelection(LoginUtil.get_language(), true);
		} else {
			spinner.setSelection(0, false);
		}
		// 添加事件Spinner事件监听
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Resources resources = getResources();// 获得res资源对象
				Configuration config = resources.getConfiguration();// 获得设置对象
				DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
				if (position == 0) {
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

	/**
	 * 
	 * @Title: updataAdvert
	 * @Description: TODO(加载广告)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressLint("InflateParams")
	private void updataAdvert() {
		mSlidingPlayView = (AbSlidingPlayView) view
				.findViewById(R.id.mAbSlidingPlayView_mall);
		mSlidingPlayView.removeAllViews();
		LayoutInflater mInflater = LayoutInflater.from(getActivity());
		for (int i = 0; i < tourAvds.size(); i++) {

			View mPlayView = mInflater.inflate(R.layout.play_view_item, null);
			ImageView mPlayImage = (ImageView) mPlayView
					.findViewById(R.id.mPlayImage);
			imageLoader.DisplayImage(IfHttpToStart.initUr(tourAvds.get(i)
					.getUrls(), "320", "120"), mPlayImage);
			mSlidingPlayView.addView(mPlayView);
		}
		mSlidingPlayView.setPageLineImage(BitmapFactory.decodeResource(
				getActivity().getResources(), R.drawable.white_dian_two),
				BitmapFactory.decodeResource(getActivity().getResources(),
						R.drawable.white_dian));
		mSlidingPlayView.startPlay();
		mSlidingPlayView
				.setOnItemClickListener(new AbSlidingPlayView.AbOnItemClickListener() {
					@Override
					public void onClick(int position) {
					}
				});
	}

	private void mallImage() {
		mall_one.setImageResource(R.drawable.image_loading);
		mall_two.setImageResource(R.drawable.image_loading);
		mall_three.setImageResource(R.drawable.image_loading);
		mall_four.setImageResource(R.drawable.image_loading);
	}

	/**
	 * @Title:loadingMainImage
	 * @Description:TODO 加载商城主界面4个大分类的图片
	 * @param
	 * @return void
	 * @throwsTo
	 */
	private void loadingMainImage() {
		// 开始加载背景图片第一个
		imageLoader.DisplayImage(TextUrlone, mall_one);

		// 开始加载背景图片第二个
		imageLoader.DisplayImage(TextUrltwo, mall_two);

		// 开始加载背景图片第三个
		imageLoader.DisplayImage(TextUrlthree, mall_three);

		// 开始加载背景图片第四个
		imageLoader.DisplayImage(TextUrlfour, mall_four);
		mprogressDialog.cancel();
	}

	/**
	 * @Title:analysisJsonHomeData
	 * @author 缪春旭
	 * @Description:TODO 解析商城主页信息
	 * @return void
	 */
	private void analysisJsonHomeData() {
		String url = SysConstants.SERVER + SysConstants.MALLHOMEDATA;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", LoginUtil.get_language() + "");
		} catch (Exception e) {
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String body = JSONUtils.getString(content, "body", "");
				String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					if (!(null == body || body.equals("") || body
							.equals("null"))) {
						// 新建Gson
						Gson gson = new Gson();
						// 获取商城首页信息
						mallHomeData = gson.fromJson(body,
								new TypeToken<List<MallHome>>() {
								}.getType());

						TextUrlone = IfHttpToStart.initUr(mallHomeData.get(0)
								.getLogo(), "275", "175");
						TextUrltwo = IfHttpToStart.initUr(mallHomeData.get(1)
								.getLogo(), "275", "175");
						TextUrlthree = IfHttpToStart.initUr(mallHomeData.get(2)
								.getLogo(), "275", "175");
						TextUrlfour = IfHttpToStart.initUr(mallHomeData.get(3)
								.getLogo(), "275", "175");

						idone = mallHomeData.get(0).getTypeId();
						idtwo = mallHomeData.get(1).getTypeId();
						idthree = mallHomeData.get(2).getTypeId();
						idfour = mallHomeData.get(3).getTypeId();

						tv_one.setText(mallHomeData.get(0).getTypeName());
						tv_two.setText(mallHomeData.get(1).getTypeName());
						tv_three.setText(mallHomeData.get(2).getTypeName());
						tv_four.setText(mallHomeData.get(3).getTypeName());

						// 加载商城主页图片
						loadingMainImage();

						analysisJsonTourHomeLYXD(TourConstants.MALLTCTJ);

					} else {
						mprogressDialog.cancel();
						scrollView.onRefreshComplete();
						AbToastUtil.showToast(getActivity(),
								R.string._data_error_load);
					}
				} else if (code.equals("-1")) {
					mprogressDialog.cancel();
					scrollView.onRefreshComplete();
					AbToastUtil.showToast(getActivity(),
							R.string._no_on_data_of_language);
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
				mprogressDialog.cancel();
				AbToastUtil.showToast(getActivity(), R.string._data_error_load);
			}

		});

	}

	/**
	 * 
	 * @Title: analysisJsonTourHomeLYXD
	 * @Description: TODO(横向展示栏)
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
			params.put("tid", LoginUtil.get_language() + "");
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
						dataGD = gson.fromJson(body,
								new TypeToken<List<MallHomeDataGD>>() {
								}.getType());

						adapter = new McxBaseAdapter<MallHomeDataGD>(
								getActivity(), R.layout.fragment_mall_gv_cell) {
							@Override
							protected void initListCell(int position,
									View listCell, ViewGroup parent) {
								MallHomeDataGD data = getItem(position);

								ImageView img = (ImageView) listCell
										.findViewById(R.id.img_fragment_mall_gv_cell_img);

								TextView context = (TextView) listCell
										.findViewById(R.id.tv_fragment_mall_gv_cell_context);
								context.setText(data.getJians());

								TextView money = (TextView) listCell
										.findViewById(R.id.tv_fragment_mall_gv_cell_money);
								money.setText(data.getPrize());

								String img_url = IfHttpToStart.initUr(
										data.getLogo(), "100", "100");

								imageLoader.DisplayImage(img_url, img);
							}
						};

						adapter.addUpdata(dataGD);

						int size = dataGD.size();
						int length = 100;

						DisplayMetrics dm = new DisplayMetrics();
						getActivity().getWindowManager().getDefaultDisplay()
								.getMetrics(dm);

						float density = dm.density;
						int gridviewWidth = (int) (size * (length + 4) * density);

						int itemWidth = (int) (length * density);

						@SuppressWarnings("deprecation")
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								gridviewWidth,
								LinearLayout.LayoutParams.FILL_PARENT);

						ge_mall.setLayoutParams(params);
						ge_mall.setColumnWidth(itemWidth);
						ge_mall.setHorizontalSpacing(5);
						ge_mall.setStretchMode(GridView.NO_STRETCH);
						ge_mall.setNumColumns(size);

						ge_mall.setAdapter(adapter);

						ge_mall.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								MallHomeDataGD da = dataGD.get(position);
								intent = new Intent(getActivity(),
										EveryGoodsActivity.class);
								intent.putExtra("GoodId", da.getId());
								startActivity(intent);
							}
						});
						// JSON解析独家推荐
						analysisJsonMallDj(TourConstants.DJTJ);
					} else {
						mprogressDialog.cancel();
						scrollView.onRefreshComplete();
						AbToastUtil.showToast(getActivity(),
								R.string._data_error_load);
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
				mprogressDialog.cancel();
				scrollView.onRefreshComplete();
				AbToastUtil.showToast(getActivity(), R.string._data_error_load);
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
			params.put("option", "travel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				if (code.equals("0")) {
					if (!(null == body || body.equals("") || body
							.equals("null"))) {
						Gson gson = new Gson();
						tourAvds = gson.fromJson(body,
								new TypeToken<List<TourAvds>>() {
								}.getType());
						updataAdvert();
						analysisJsonHomeData();
					} else {
						mprogressDialog.cancel();
						scrollView.onRefreshComplete();
						AbToastUtil.showToast(getActivity(),
								R.string._data_error_load);
					}
				} else {
					mprogressDialog.cancel();
					scrollView.onRefreshComplete();
					AbToastUtil.showToast(getActivity(),
							R.string._data_error_load);
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
				mprogressDialog.cancel();
				scrollView.onRefreshComplete();
				AbToastUtil.showToast(getActivity(), R.string._data_error_load);
			}

		});
	}

	/**
	 * @author FangshuoIT 缪春旭
	 * @Title: analysisJsonTourHome
	 * @Description: TODO(解析独家推荐内容)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonMallDj(String id) {
		String url = SysConstants.SERVER + SysConstants.TOURHOMEDATA;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("idList", "1");
			params.put("typeId", id);
			params.put("num", "4");
			params.put("tid", LoginUtil.get_language() + "");

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
						loadingMainImageDj();

						// JSON解析GridView数据
						analysisJsonGridViewData(TourConstants.GDTJ);

					}
				} else {
					mprogressDialog.cancel();
					scrollView.onRefreshComplete();
					AbToastUtil.showToast(getActivity(),
							R.string._data_error_load);
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
				mprogressDialog.cancel();
				scrollView.onRefreshComplete();
				AbToastUtil.showToast(getActivity(), R.string._data_error_load);
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
	private void loadingMainImageDj() {

		// 开始加载背景图片第一个
		imageLoader.DisplayImage(DjUrlone, img_dj_one);

		// 开始加载背景图片第二个
		imageLoader.DisplayImage(DjUrltwo, img_dj_two);

		// 开始加载背景图片第三个
		imageLoader.DisplayImage(DjUrlthree, img_dj_three);

		// 开始加载背景图片第四个
		imageLoader.DisplayImage(DjUrlfour, img_dj_four);
		mprogressDialog.cancel();
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
		String url = SysConstants.SERVER + SysConstants.MALLSORTGOODS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("typeId", id);
			params.put("num", "10");
			params.put("location", "0");
			params.put("tid", LoginUtil.get_language() + "");

		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				if (code.equals("0")) {
					if (!(null == body || body.equals("") || body
							.equals("null"))) {

						tv_bottom.setText(R.string._jixuhuadongjiazai);

						// 新建Gson
						Gson gson = new Gson();
						// 获取旅游首页信息
						tourHomeDataThree = gson.fromJson(body,
								new TypeToken<List<SortGoodsCell>>() {
								}.getType());

						Bigadapter = new McxBaseAdapter<SortGoodsCell>(
								getActivity(), R.layout.fragment_tour_bigitem) {

							@Override
							protected void initListCell(int position,
									View listCell, ViewGroup parent) {
								SortGoodsCell da = getItem(position);

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
								imageLoader.DisplayImage(img_url, img);
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
										SortGoodsCell da = tourHomeDataThree
												.get(position);
										intent = new Intent(getActivity(),
												EveryGoodsActivity.class);
										intent.putExtra("GoodId", da.getId());
										startActivity(intent);
									}
								});
					}
				} else {
					mprogressDialog.cancel();
					scrollView.onRefreshComplete();
					AbToastUtil.showToast(getActivity(),
							R.string._data_error_load);
				}
			}

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				mprogressDialog.cancel();
				scrollView.onRefreshComplete();
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				mprogressDialog.cancel();
				AbToastUtil.showToast(getActivity(), R.string._data_error_load);
			}

		});
	}

	/**
	 * 
	 * @Title: analysisJsonGridViewLoadData
	 * @Description: TODO(GridView加载更多)
	 * @param @param id 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonGridViewLoadData(String id, int number) {
		String url = SysConstants.SERVER + SysConstants.MALLSORTGOODS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("typeId", id);
			params.put("num", number + 10 + "");
			params.put("location", number + "");
			params.put("tid", LoginUtil.get_language() + "");
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
					if (!(body == null || body.equals("") || body.equals("[]"))) {
						// 新建Gson
						Gson gson = new Gson();

						tourHomeDataFour = gson.fromJson(body,
								new TypeToken<List<SortGoodsCell>>() {
								}.getType());

						Bigadapter.addOffdata(tourHomeDataFour);
						Bigadapter.notifyDataSetChanged();
						System.out.println("body=" + body);
					} else {
						Toast.makeText(getActivity(),
								R.string._refurbish_null_bottom,
								Toast.LENGTH_SHORT).show();
						tv_bottom.setText(R.string._refurbish_null_bottom);
						numberLoadMore -= 10;
					}
				} else {
					Toast.makeText(getActivity(),
							R.string._sort_goods_load_error, Toast.LENGTH_SHORT)
							.show();
					numberLoadMore -= 10;
				}
			}

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				scrollView.onRefreshComplete();
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				mprogressDialog.cancel();
				AbToastUtil.showToast(getActivity(), "数据加载错误");
			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ly_fragment_mall_sort:
			intent = new Intent(getActivity(), MallSortActivity.class);
			startActivity(intent);
			break;
		case R.id.img_fragment_mall_search:
			intent = new Intent(getActivity(), SearchActivity.class);
			startActivity(intent);
			break;
		case R.id.img_fragment_mall_one:
			intent = new Intent(getActivity(), SortGoodsActivity.class);
			intent.putExtra("Title", tv_one.getText());
			intent.putExtra("id", idone);
			startActivity(intent);
			break;
		case R.id.img_fragment_mall_two:
			intent = new Intent(getActivity(), SortGoodsActivity.class);
			intent.putExtra("Title", tv_two.getText());
			intent.putExtra("id", idtwo);
			startActivity(intent);
			break;
		case R.id.img_fragment_mall_three:
			intent = new Intent(getActivity(), SortGoodsActivity.class);
			intent.putExtra("Title", tv_three.getText());
			intent.putExtra("id", idthree);
			startActivity(intent);
			break;
		case R.id.img_fragment_mall_four:
			intent = new Intent(getActivity(), SortGoodsActivity.class);
			intent.putExtra("Title", tv_four.getText());
			intent.putExtra("id", idfour);
			startActivity(intent);
			break;
		// 独家推荐点击事件
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
		default:
			break;
		}
	}

	/**
	 * @Title: activityTo
	 * @Description: TODO(自定义跳转)
	 * @param 设定文件
	 * @return void
	 */
	// private void activityTo() {
	// getActivity().overridePendingTransition(
	// R.anim.activity_open_translate_right,
	// R.anim.activity_close_translate_lift);
	// }

	@Override
	public void onResume() {
		super.onResume();
	}

}
