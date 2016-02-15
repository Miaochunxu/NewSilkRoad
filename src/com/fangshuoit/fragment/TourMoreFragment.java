package com.fangshuoit.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fangshuoit.adapter.McxBaseAdapter;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.view.MyGridView;

public class TourMoreFragment extends Fragment {

	private View view;

	private LinearLayout ly_tour_more;
	//
	private MyGridView gv_tour_more;

	private McxBaseAdapter<String> adapter;

	// private float mLastY;
	//
	// private int index;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_tour_more, container, false);

		initView();

		initEvent();

		return view;
	}

	private void initEvent() {

	}

	// private void initTopUp() {
	//
	// gv_tour_more.setOnTouchListener(new OnTouchListener() {
	//
	// @SuppressLint("ClickableViewAccessibility")
	// @Override
	// public boolean onTouch(View v, MotionEvent event) {
	//
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	//
	// break;
	// case MotionEvent.ACTION_MOVE:
	// index++;
	// break;
	// case MotionEvent.ACTION_UP:
	// if (mLastY == 0.0 && index > 10) {
	// Toast.makeText(getActivity(), "最上面!",
	// Toast.LENGTH_SHORT).show();
	// }
	// index = 0;
	// break;
	// default:
	// break;
	// }
	// return false;
	// }
	// });
	// }

	private void initView() {

		gv_tour_more = (MyGridView) view
				.findViewById(R.id.gv_fragment_tour_more);

		adapter = new McxBaseAdapter<String>(getActivity(),
				R.layout.fragment_tour_more_gridview_cell) {

			@Override
			protected void initListCell(int position, View listCell,
					ViewGroup parent) {
				TextView tv = (TextView) listCell
						.findViewById(R.id.tv_fragment_tour_more_cell_title);

				tv.setText(getItem(position));
			}
		};
		gv_tour_more.setAdapter(adapter);
		adapter.add("aaa");
		adapter.add("aaa");
		adapter.add("aaa");
		adapter.add("aaa");
		adapter.add("aaa");
		adapter.add("aaa");
		adapter.add("aaa");
		adapter.add("aaa");
		adapter.add("aaa");
		adapter.add("aaa");
		adapter.add("aaa");
		adapter.add("aaa");

	}

	// @Override
	// public void onFooterRefresh(PullToRefreshView view) {
	// mPullToRefreshView.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// mPullToRefreshView.onFooterRefreshComplete();
	// Toast.makeText(getActivity(), "onFooterRefresh",
	// Toast.LENGTH_SHORT).show();
	// }
	// }, 1000);
	// }
	//
	// @Override
	// public void onHeaderRefresh(PullToRefreshView view) {
	// mPullToRefreshView.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// mPullToRefreshView.onHeaderRefreshComplete();
	// Toast.makeText(getActivity(), "onHeaderRefresh",
	// Toast.LENGTH_SHORT).show();
	// }
	// }, 1000);
	// }

}
