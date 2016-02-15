package com.fangshuoit.myOrderFragment;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.image.AbImageLoader;
import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.model.MyOrderCell;
import com.fangshuoit.model.MyOrderCellOne;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.AsyncImageLoader;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: PayYesFragment
 * @Description: TODO 已支付订单
 * @author 方硕IT 缪春旭
 * @date 2015-7-9 下午4:45:03
 * 
 */
public class PayYesFragment extends Fragment {

	private View view;

	private ListView listView;

	private List<MyOrderCellOne> listone;
	// 存储订单数据
	private McxBaseAdapter<MyOrderCell> adapter2;
	// 存储body数据
	private McxBaseAdapter<MyOrderCellOne> adapter;

	AsyncImageLoader asyncImageLoader;// 加载图片

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private ProgressDialog progressDialog;

	private TextView tv_noorder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_myorder_allorder, container,
				false);
		initView();
		initEvent();
		return view;
	}

	private void initEvent() {
		analysisJsonShowOrder();
	}

	private void initView() {
		mAbHttpUtil = AbHttpUtil.getInstance(getActivity());// 初始化解析json
		progressDialog = new ProgressDialog(getActivity());

		asyncImageLoader = new AsyncImageLoader(getActivity());// 初始化

		listView = (ListView) view.findViewById(R.id.lv_fragment_myorder);

		tv_noorder = (TextView) view.findViewById(R.id.tv_noorder);
		tv_noorder.setVisibility(View.GONE);

		// initAdapter();

	}

	private void initAdapter() {
		adapter = new McxBaseAdapter<MyOrderCellOne>(getActivity(),
				R.layout.fragment_myorder_list_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				MyOrderCellOne dataone = getItem(position);

				TextView tv_orderid = (TextView) listCell
						.findViewById(R.id.tv_fragment_myorder_list_no);
				TextView tv_state = (TextView) listCell
						.findViewById(R.id.tv_fragment_myorder_list_state);
				if (dataone.getPayState().equals("0")) {
					tv_state.setText("未付款");
					tv_state.setTextColor(0xfffe0663);
				} else if (dataone.getPayState().equals("1")) {
					tv_state.setText("已付款");
					tv_state.setTextColor(0xff07d515);
				}
				tv_orderid.setText(dataone.getOrderId());

				// MultiListView listView = (MultiListView) listCell
				// .findViewById(R.id.mlv_fragment_myorder_list_cell);
				//
				// adapter2 = new McxBaseAdapter<MyOrderCell>(
				// listCell.getContext(),
				// R.layout.activity_shopping_car_listview_cell) {
				//
				// @Override
				// protected void initListCell(int position, View Cell,
				// ViewGroup parent) {
				// MyOrderCell data = getItem(position);
				//
				// CheckBox box = (CheckBox) Cell
				// .findViewById(R.id.cb_activity_shopping_car_listview_cell);
				// box.setVisibility(View.GONE);
				//
				// TextView tv_name = (TextView) Cell
				// .findViewById(R.id.tv_activity_shopping_car_listview_cell_title);
				// TextView tv_jians = (TextView) Cell
				// .findViewById(R.id.tv_activity_shopping_car_listview_cell_type);
				// TextView tv_attribute = (TextView) Cell
				// .findViewById(R.id.tv_activity_shopping_car_listview_cell_attribute);
				// TextView tv_money = (TextView) Cell
				// .findViewById(R.id.tv_activity_shopping_car_listview_cell_money);
				// TextView tv_no = (TextView) Cell
				// .findViewById(R.id.tv_activity_shopping_car_listview_cell_number);
				//
				// ImageView img = (ImageView) Cell
				// .findViewById(R.id.img_activity_shopping_car_listview_cell);
				//
				// tv_name.setText(data.getGoodName());
				// tv_jians.setText(data.getGoodJians());
				// tv_money.setText(data.getGoodPrice());
				//
				// String url = IfHttpToStart.initUr(data.getGoodPhoto());
				// System.out.println("lalalal=" + url);
				// loadImg(url, img);
				// }
				// };
				//
				// Gson gson = new Gson();
				// List<MyOrderCell> listtwo =
				// gson.fromJson(listone.get(position)
				// .getOrder(), new TypeToken<List<MyOrderCell>>() {
				// }.getType());
				// adapter2.addUpdata(listtwo);
				// listView.setAdapter(adapter2);
			}

		};

		adapter.addUpdata(listone);
		listView.setAdapter(adapter);
	}

	/**
	 * 缓存加载图片
	 * 
	 * @Description: TODO
	 * @date 2015-1-10
	 * @time 上午11:55:03
	 * @author lvyan
	 * @return
	 */
	public void loadImg(String url, final ImageView img) {
		// 图片的下载
		AbImageLoader mAbImageLoader = AbImageLoader.newInstance(getActivity());
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
		// 原图片的下载
		mAbImageLoader.setMaxWidth(0);
		mAbImageLoader.setMaxHeight(0);
		mAbImageLoader.display(img, url);
	}

	/**
	 * 
	 * @Title: analysisJsonShowOrder
	 * @Description: TODO(拿到body里面的值)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonShowOrder() {
		String url = SysConstants.SERVER + SysConstants.MORDERSHOW;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tid", LoginUtil.get_UserId());
			params.put("option", "paid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String body = JSONUtils.getString(content, "body", "");
				String message = JSONUtils.getString(content, "message", "");
				if (code.equals("0")) {
					if (!(body == null || body == "")) {
						Gson gson = new Gson();
						listone = gson.fromJson(body,
								new TypeToken<List<MyOrderCellOne>>() {
								}.getType());
						initAdapter();
					}

					progressDialog.cancel();

				} else if (message.equals("null.error!")) {
					tv_noorder.setVisibility(View.VISIBLE);
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
}
