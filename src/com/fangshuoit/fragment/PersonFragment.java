package com.fangshuoit.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.fangshuoit.activity.LoginActivity;
import com.fangshuoit.activity.MyCollectionActivity;
import com.fangshuoit.activity.MyorderActivity;
import com.fangshuoit.activity.PersonalInfoEditActivity;
import com.fangshuoit.activity.ShoppingCarActivity;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.imageUtil.ImageLoader;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.IfHttpToStart;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.fangshuoit.view.CircleImageView;

public class PersonFragment extends Fragment implements OnClickListener {
	protected boolean isVisible;

	private View view;

	private TextView tv_nickname, tv_login, tv_level, tv_no_collection,
			tv_no_shoppingcar, tv_no_order;

	private CircleImageView img_user;

	private Intent intent;

	private LinearLayout ly_collection, ly_collection_list, ly_travels,
			ly_order, ly_order_list;

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private ProgressDialog progressDialog;

	private ImageLoader imageLoader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_person, container, false);

		initView();

		initEvent();

		return view;
	}

	private void initEvent() {
		ly_collection.setOnClickListener(this);
		ly_travels.setOnClickListener(this);
		ly_order.setOnClickListener(this);

		tv_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (tv_login.getText().equals(
						getActivity().getResources().getString(
								R.string._personal_fragment_cancel))) {
					LoginUtil.remberIsLogin(false);
					LoginUtil.rember_passWorld("");
					LoginUtil.rember_nickname("");
					LoginUtil.rember_Adress_default("", "", "", "");
				}
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});

		// 编辑 个信息
		view.findViewById(R.id.img_fragment_personal_edit).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (LoginUtil.get_is_Login()) {
							intent = new Intent(getActivity(),
									PersonalInfoEditActivity.class);
							startActivity(intent);
						} else {
							intent = new Intent(getActivity(),
									LoginActivity.class);
							startActivity(intent);
						}
					}
				});

	}

	private void initView() {

		mAbHttpUtil = AbHttpUtil.getInstance(getActivity());// 初始化解析json
		progressDialog = new ProgressDialog(getActivity());

		imageLoader = new ImageLoader(getActivity(), true);

		tv_nickname = (TextView) view
				.findViewById(R.id.tv_fragment_personal_username);
		tv_login = (TextView) view
				.findViewById(R.id.tv_fragment_personal_login);
		tv_level = (TextView) view
				.findViewById(R.id.tv_fragment_personal_level);
		tv_no_collection = (TextView) view
				.findViewById(R.id.tv_fragment_personal_collection_number);
		tv_no_shoppingcar = (TextView) view
				.findViewById(R.id.tv_fragment_personal_travels_number);
		tv_no_order = (TextView) view
				.findViewById(R.id.tv_fragment_personal_camera_order);

		img_user = (CircleImageView) view
				.findViewById(R.id.img_personal_head_img);

		ly_collection = (LinearLayout) view
				.findViewById(R.id.ly_fragment_personal_collection);
		ly_travels = (LinearLayout) view
				.findViewById(R.id.ly_fragment_personal_travels);
		ly_order = (LinearLayout) view
				.findViewById(R.id.ly_fragment_personal_order);
		ly_collection_list = (LinearLayout) view
				.findViewById(R.id.ly_fragment_personal_collection_list);
		ly_order_list = (LinearLayout) view
				.findViewById(R.id.ly_fragment_personal_order_list);

		ly_collection_list.setVisibility(View.GONE);
		ly_order_list.setVisibility(View.GONE);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
		} else {
			isVisible = false;
		}
	}

	/**
	 * 
	 * @Title: analysisJsonPersonalHome
	 * @Description: TODO(个人信息)
	 * @param @param mallid
	 * @param @param pot 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonPersonalHome() {
		String url = SysConstants.SERVER + SysConstants.PERSONALSHOW;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			params.put("tempVo.id", LoginUtil.get_UserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", "");
				String body = JSONUtils.getString(content, "body", "");
				// String message = JSONUtils.getString(content, "message", "");
				String level = JSONUtils.getString(body, "level", "");
				String shopNum = JSONUtils.getString(body, "shopNum", "");
				String orderNum = JSONUtils.getString(body, "orderNum", "");
				String photo = JSONUtils.getString(body, "photo", "");
				String collNum = JSONUtils.getString(body, "collNum", "");
				String nickName = JSONUtils.getString(body, "nickName", "");
				if (code.equals("0")) {
					if (!(body == null || body == "")) {
						// 显示信息
						tv_level.setText(level);
						tv_no_collection.setText(collNum);
						tv_no_shoppingcar.setText(shopNum);
						tv_no_order.setText(orderNum);
						tv_nickname.setText(nickName);
						LoginUtil.rember_nickname(nickName);
						String imgurl = IfHttpToStart.initUr(photo, "200",
								"200");
						// 开始加载图片
						imageLoader.DisplayImage(imgurl, img_user);
					}
					progressDialog.cancel();
				} else {
					progressDialog.cancel();
					Toast.makeText(getActivity(),
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
			}

		});
	}

	boolean collection = false, order = false;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ly_fragment_personal_collection:

			if (LoginUtil.get_is_Login()) {
				intent = new Intent(getActivity(), MyCollectionActivity.class);

			} else {
				intent = new Intent(getActivity(), LoginActivity.class);
			}
			startActivity(intent);

			break;
		case R.id.ly_fragment_personal_travels:
			if (LoginUtil.get_is_Login()) {
				intent = new Intent(getActivity(), ShoppingCarActivity.class);
				startActivity(intent);
			} else {
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.ly_fragment_personal_order:
			if (LoginUtil.get_is_Login()) {
				intent = new Intent(getActivity(), MyorderActivity.class);
				startActivity(intent);
			} else {
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onResume() {
		if (LoginUtil.get_is_Login()) {
			tv_login.setText(getResources().getString(
					R.string._personal_fragment_cancel));
		} else {
			tv_login.setText(getResources().getString(R.string._login_denglu));
			tv_nickname.setText("");
			img_user.setImageResource(R.drawable.person_moren);
			tv_no_collection.setText("0");
			tv_no_shoppingcar.setText("0");
			tv_no_order.setText("0");
		}
		if (LoginUtil.get_is_Login()) {
			analysisJsonPersonalHome();
		}
		super.onResume();
	}

}
