package com.fangshuoit.personalFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fangshuoit.newsilkroad.R;

/**
 * 
 * @ClassName: MycollectionFragment
 * @Description: TODO 个人中心“我的收藏Fragment”
 * @author 方硕IT 缪春旭
 * @date 2015-6-12 下午2:28:02
 * 
 */
public class MycollectionFragment extends Fragment {

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_personal_my_collection,
				container, false);

		initView();
		
		initEvent();
		
		return view;
	}

	private void initEvent() {
		
	}

	private void initView() {
		
	}

}
