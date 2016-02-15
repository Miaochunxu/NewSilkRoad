package com.fangshuoit.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.ab.view.sliding.AbSlidingPlayView;
import com.fangshuoit.activity.CultureInfo;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.model.CultureCell;
import com.fangshuoit.model.TourAvds;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.pulltorefresh.library.PullToRefreshBase;
import com.fangshuoit.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.fangshuoit.pulltorefresh.library.PullToRefreshScrollView;
import com.fangshuoit.tool.AsyncImageLoader;
import com.fangshuoit.tool.DateUtil;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.fangshuoit.view.CustomProgressDialog;
import com.fangshuoit.view.MultiListView;
import com.fangshuoit.view.MyAbSlidingPlayView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: CultureFragment
 * @Description: TODO 文化，新闻
 * @author 方硕IT 缪春旭
 * @date 2015-5-29 下午12:58:15
 * 
 */
public class CultureFragment extends Fragment {

	private View view;

	private MultiListView listView;

	private McxBaseAdapter<CultureCell> adapter;

	private List<CultureCell> cells;

	private List<CultureCell> cellLists = new ArrayList<CultureCell>();

	private List<TourAvds> avdlist;

	private MyAbSlidingPlayView mSlidingPlayView = null;// 初始化滑动
	AsyncImageLoader asyncImageLoader;// 加载图片

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private CustomProgressDialog progressDialog = null;

	private PullToRefreshScrollView scrollView;

	private ImageLoader a;

	private int PageNumber;
	private int nowPageNumber = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_culture, container, false);

		initView();

		initEvent();

		return view;
	}

	private void initEvent() {
		progressDialog.show();
		try {
			analysisJsonHomeAds();
		} catch (Exception e) {
			progressDialog.cancel();
			Toast.makeText(getActivity(), R.string._culture_fragment_r_error,
					Toast.LENGTH_SHORT).show();
		}

		scrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(),
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// 得到Scrollview的Y轴的坐标,小于0则说明在头部，大于0则在底部
				float Sy = scrollView.getScrollY();

				if (Sy <= 0f) {
					// 刷新方法
					scrollView.postDelayed(new Runnable() {

						@Override
						public void run() {
							try {
								nowPageNumber = 1;
								cellLists.clear();
								analysisJsonHomeAds();
							} catch (Exception e) {
								Toast.makeText(getActivity(),
										R.string._refurbish_error,
										Toast.LENGTH_SHORT).show();
							}
						}
					}, 1000);
				} else {
					nowPageNumber++;
					if (nowPageNumber <= PageNumber) {
						try {
							analysisJsonHome(nowPageNumber);
						} catch (Exception e) {
							nowPageNumber--;
						}
					} else {
						Toast.makeText(getActivity(), R.string._refurbish_null,
								Toast.LENGTH_SHORT).show();
						scrollView.onRefreshComplete();
						nowPageNumber--;
					}
				}
			}
		});
		scrollView.setPullToRefreshOverScrollEnabled(true);
	}

	private void initView() {

		a = new ImageLoader(getActivity(), true);

		scrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.newcustomscrollview_fragment_culture);
		scrollView.scrollTo(0, 20);
		// scrollView.findViewById(R.id.fl_inner).setVisibility(View.INVISIBLE);

		// scrollView.setRefreshingLabel(null);

		mAbHttpUtil = AbHttpUtil.getInstance(getActivity());// 初始化解析json
		progressDialog = CustomProgressDialog.createDialog(getActivity());
		progressDialog.setCanceledOnTouchOutside(false);
		asyncImageLoader = new AsyncImageLoader(getActivity());
		listView = (MultiListView) view.findViewById(R.id.lv_fragment_culture);
		listView.setFocusable(false);
	}

	// 加载广告
	@SuppressLint("InflateParams")
	private void updataAdvert() {
		mSlidingPlayView = (MyAbSlidingPlayView) view
				.findViewById(R.id.ab_fragment_culture);
		mSlidingPlayView.removeAllViews();
		asyncImageLoader = new AsyncImageLoader(getActivity());
		LayoutInflater mInflater = LayoutInflater.from(getActivity());
		for (int i = 0; i < avdlist.size(); i++) {
			View mPlayView = mInflater.inflate(R.layout.play_view_item, null);
			ImageView mPlayImage = (ImageView) mPlayView
					.findViewById(R.id.mPlayImage);
			a.DisplayImage(IfHttpToStart.initUr(avdlist.get(i).getUrls(),
					"320", "120"), mPlayImage);

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
						// AbToastUtil.showToast(getActivity(), "点击" +
						// position);
						// Intent intent = new Intent();
						// intent.setClass(getActivity(),
						// ContentViewActivity.class);
						// startActivity(intent);
					}
				});
	}

	private void initAdapter() {
		adapter = new McxBaseAdapter<CultureCell>(getActivity(),
				R.layout.fragment_culture_listview_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				CultureCell data = getItem(position);

				TextView tv_title = (TextView) listCell
						.findViewById(R.id.tv_fragment_culture_cell_title);
				TextView tv_auther = (TextView) listCell
						.findViewById(R.id.tv_fragment_culture_cell_createname);
				TextView tv_time = (TextView) listCell
						.findViewById(R.id.tv_fragment_culture_cell_time);
				ImageView img = (ImageView) listCell
						.findViewById(R.id.img_fragment_culture_cell);

				tv_title.setText(data.getName());
				tv_time.setText(DateUtil.USATimeStringToString(
						data.getCreatTime(), "yyyy-MM-dd"));
				tv_auther.setText(data.getCreater());
				// tv_context.setText(data.getContext());
				String Url = IfHttpToStart.initUr(data.getPhotoUrl());
				// 开始加载
				a.DisplayImage(Url, img);
			}
		};
		adapter.addUpdata(cellLists);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), CultureInfo.class);
				intent.putExtra("content", cells.get(position).getContent());
				intent.putExtra("title", cells.get(position).getName());
				startActivity(intent);
			}
		});
	}

	/**
	 * @Title:analysisJsonHomeAds
	 * @Description:TODO 加载广告
	 * @param
	 * @return void
	 */
	private void analysisJsonHomeAds() {
		String url = SysConstants.SERVER + SysConstants.TOURHOMEADS;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("option", "culture");
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
						avdlist = gson.fromJson(body,
								new TypeToken<List<TourAvds>>() {
								}.getType());
						updataAdvert();

						analysisJsonHome(nowPageNumber);
					}
				} else {
					progressDialog.cancel();
					Toast.makeText(getActivity(),
							R.string._culture_fragment_r_error,
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
				progressDialog.cancel();
				AbToastUtil.showToast(getActivity(), R.string._data_error_load);
			}

		});
	}

	/**
	 * 
	 * @Title: analysisJsonHome
	 * @Description: TODO(主要内容ListView)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonHome(int i) {
		String url = SysConstants.SERVER + SysConstants.SHOWCULTURE;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("option", i + "");
			params.put("tid", LoginUtil.get_language() + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String body = JSONUtils.getString(content, "body", "");
				String code = JSONUtils.getString(content, "code", "");
				String voList = JSONUtils.getString(body, "voList", "");
				String pageSize = JSONUtils.getString(body, "pageSize", "");
				String nowPage = JSONUtils.getString(body, "nowPage", "");
				String totalRecord = JSONUtils.getString(body, "totalRecord",
						"");
				String totalPage = JSONUtils.getString(body, "totalPage", "");
				if (code.equals("0")) {
					if (!(null == voList || voList.equals("") || voList
							.equals("null"))) {
						Gson gson = new Gson();
						cells = gson.fromJson(voList,
								new TypeToken<List<CultureCell>>() {
								}.getType());
						PageNumber = Integer.valueOf(totalPage).intValue();
						cellLists.addAll(cells);
						initAdapter();
					}
				} else {
					Toast.makeText(getActivity(),
							R.string._every_good_activity_tv_one,
							Toast.LENGTH_SHORT).show();
					nowPageNumber--;
				}
			}

			@Override
			public void onStart() {

			}

			@Override
			public void onFinish() {
				progressDialog.cancel();
				scrollView.onRefreshComplete();
			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				progressDialog.cancel();
				AbToastUtil.showToast(getActivity(), R.string._data_error_load);
				nowPageNumber--;
			}

		});
	}
}
