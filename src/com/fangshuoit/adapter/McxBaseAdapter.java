package com.fangshuoit.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class McxBaseAdapter<T> extends BaseAdapter {

	@SuppressLint("UseSparseArrays")
	public McxBaseAdapter(Context context, int resid) {
		this.context = context;
		listCellId = resid;
	}

	// 控制内部控件的隐藏与否
	private static Boolean edit = false;

	public static Boolean getEdit() {
		return edit;
	}

	public static void setEdit(Boolean edit) {
		McxBaseAdapter.edit = edit;
	}

	// 用来控制商品数量
	private static List<String> isNumber;

	// 初始化isNumber
	public void initNumber() {
		isNumber = new ArrayList<String>();
		for (int i = 0; i < myList.size(); i++) {
			isNumber.add("1");
		}
	}

	public static List<String> getIsNumber() {
		return isNumber;
	}

	public static void setIsNumber(int l, String o) {
		isNumber.set(l, o);
	}

	// 用来控制CheckBox的选中状况
	private static List<Boolean> isSelected;

	public static List<Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(List<Boolean> isSelected) {
		McxBaseAdapter.isSelected = isSelected;
	}

	// 初始化isSelected的数据
	public void initCheckDate() {
		isSelected = new ArrayList<Boolean>();
		for (int i = 0; i < myList.size(); i++) {
			getIsSelected().add(false);
		}
	}

	private Context context;
	private int listCellId = 0;

	public Context getContext() {
		return context;
	}

	// 数据源
	private List<T> myList = new ArrayList<T>();

	public void add(T item) {
		myList.add(item);
	}

	// 添加一整个List数据,初始化
	public void addUpdata(List<T> list) {
		myList = list;
	}

	public void addOffdata(List<T> listoff) {
		myList.addAll(listoff);
	}

	// 删除当前条
	public void remove(int position) {
		myList.remove(position);
		isNumber.remove(position);
		isSelected.remove(position);
	}

	// 添加整个List数据
	public void addList(List<T> list) {
		myList.addAll(list);
	}

	// 删除最后一条数据
	private void removeLast() {
		remove(myList.size() - 1);
	}

	@Override
	public int getCount() {
		return myList.size();
	}

	@Override
	public T getItem(int position) {
		return myList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(listCellId,
					null);
		}
		initListCell(position, convertView, parent);
		return convertView;
	}

	protected abstract void initListCell(int position, View listCell,
			ViewGroup parent);
}
