package com.fangshuoit.myOrderFragment;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.image.AbImageLoader;
import com.fangshuoit.adapter.McxBaseAdapterNoIf;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.model.MyOrderCell;
import com.fangshuoit.model.MyOrderCellOne;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.AsyncImageLoader;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: AllFragment
 * @Description: TODO 全部订单
 * @author 方硕IT 缪春旭
 * @date 2015-7-9 下午4:44:46
 * 
 */
public class AllFragment extends Fragment {

	private View view;

	private ListView listView;

	private List<MyOrderCellOne> listone;
	// 存储body数据
	private McxBaseAdapterNoIf<MyOrderCellOne> adapter;

	AsyncImageLoader asyncImageLoader;// 加载图片

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private ProgressDialog progressDialog;

	private TextView tv_noorder;

	private ImageLoader imageLoader;

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

		imageLoader = new ImageLoader(getActivity(), true);

		mAbHttpUtil = AbHttpUtil.getInstance(getActivity());// 初始化解析json
		progressDialog = new ProgressDialog(getActivity());

		asyncImageLoader = new AsyncImageLoader(getActivity());// 初始化

		listView = (ListView) view.findViewById(R.id.lv_fragment_myorder);

		tv_noorder = (TextView) view.findViewById(R.id.tv_noorder);
		tv_noorder.setVisibility(View.GONE);

		// initAdapter();

	}

	private void initAdapter() {
		adapter = new McxBaseAdapterNoIf<MyOrderCellOne>(getActivity(),
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
					tv_state.setText(R.string._my_order_activity_pay_on);
					tv_state.setTextColor(0xfffe0663);
				} else if (dataone.getPayState().equals("1")) {
					tv_state.setText(R.string._my_order_activity_pay_off);
					tv_state.setTextColor(0xff07d515);
				}
				tv_orderid.setText(dataone.getOrderId());

				Gson gson = new Gson();
				List<MyOrderCell> listtwo = gson.fromJson(listone.get(position)
						.getOrder(), new TypeToken<List<MyOrderCell>>() {
				}.getType());

				LinearLayout layout = (LinearLayout) listCell
						.findViewById(R.id.ly_fragment_myorder_listcell);

				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				for (int i = 0; i < listtwo.size(); i++) {
					View viewp = LayoutInflater
							.from(listCell.getContext())
							.inflate(
									R.layout.activity_shopping_car_listview_cell,
									null);

					// #########

					CheckBox box = (CheckBox) viewp
							.findViewById(R.id.cb_activity_shopping_car_listview_cell);
					box.setVisibility(View.GONE);

					TextView tv_name = (TextView) viewp
							.findViewById(R.id.tv_activity_shopping_car_listview_cell_title);
					TextView tv_jians = (TextView) viewp
							.findViewById(R.id.tv_activity_shopping_car_listview_cell_type);
					TextView tv_attribute = (TextView) viewp
							.findViewById(R.id.tv_activity_shopping_car_listview_cell_attribute);
					TextView tv_money = (TextView) viewp
							.findViewById(R.id.tv_activity_shopping_car_listview_cell_money);
					TextView tv_no = (TextView) viewp
							.findViewById(R.id.tv_activity_shopping_car_listview_cell_number);

					ImageView img = (ImageView) viewp
							.findViewById(R.id.img_activity_shopping_car_listview_cell);

					tv_name.setText(listtwo.get(i).getGoodName());
					tv_jians.setText(listtwo.get(i).getGoodJians());
					tv_money.setText(listtwo.get(i).getGoodPrice());

					String url = IfHttpToStart.initUr(listtwo.get(i)
							.getGoodPhoto(), "80", "80");
					imageLoader.DisplayImage(url, img);
					// ##########
					viewp.setLayoutParams(lp);
					layout.addView(viewp);
				}

			}

		};

		adapter.addUpdata(listone);
		listView.setAdapter(adapter);
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
			params.put("option", "all");
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

					// progressDialog.cancel();

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
