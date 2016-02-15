package com.fangshuoit.image.upload;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ab.image.AbImageCache;
import com.ab.image.AbImageLoader;
import com.ab.util.AbFileUtil;
import com.ab.util.AbImageUtil;
import com.ab.util.AbStrUtil;
import com.fangshuoit.newsilkroad.R;

/**
 * 适配器 网络URL的图片.
 * 
 * @project 易2易客户端
 * @Company 宁夏方硕信息技术有限公司
 * @author 周庆鹏
 * @phone 15009666891
 * @QQ 872123675
 * @Blog http://blog.csdn.net/lovelvyan
 * @date 2015-1-3
 * @time 下午4:40:06
 */
public class ImageShowAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> mImagePaths = null;
	private int mWidth;
	private int mHeight;

	// 图片下载器
	private AbImageLoader mAbImageLoader = null;

	public ImageShowAdapter(Context context, List<String> imagePaths,
			int width, int height) {
		mContext = context;
		this.mImagePaths = imagePaths;
		this.mWidth = width;
		this.mHeight = height;
		// 图片下载器
		mAbImageLoader = new AbImageLoader(mContext);
		mAbImageLoader.setMaxWidth(this.mWidth);
		mAbImageLoader.setMaxHeight(this.mHeight);
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
	}

	/**
	 * 描述：获取数量.
	 * 
	 * @return the count
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		return mImagePaths.size();
	}

	/**
	 * 描述：获取索引位置的路径.
	 * 
	 * @param position
	 * @return the item
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position) {
		return mImagePaths.get(position);
	}

	/**
	 * 描述：获取位置.
	 * 
	 * @param position
	 * @return the item id
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 描述：显示View.
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return the view
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	@SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LinearLayout mLinearLayout = new LinearLayout(mContext);
			RelativeLayout mRelativeLayout = new RelativeLayout(mContext);
			ImageView mImageView1 = new ImageView(mContext);
			mImageView1.setScaleType(ScaleType.FIT_CENTER);
			ImageView mImageView2 = new ImageView(mContext);
			mImageView2.setScaleType(ScaleType.FIT_CENTER);
			holder.mImageView1 = mImageView1;
			holder.mImageView2 = mImageView2;
			LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT);
			lp1.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
			RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
					mWidth, mHeight);
			lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
			lp2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
			mRelativeLayout.addView(mImageView1, lp2);
			mRelativeLayout.addView(mImageView2, lp2);
			mLinearLayout.addView(mRelativeLayout, lp1);

			convertView = mLinearLayout;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mImageView1.setImageBitmap(null);
		holder.mImageView2.setBackgroundDrawable(null);

		String imagePath = mImagePaths.get(position);

		if (!AbStrUtil.isEmpty(imagePath)) {
			// 从缓存中获取图片，很重要否则会导致页面闪动
			Bitmap bitmap = AbImageCache.getInstance().getBitmap(imagePath);
			// 缓存中没有则从网络和SD卡获取
			if (bitmap == null) {
				holder.mImageView1.setImageResource(R.drawable.image_loading);
				if (imagePath.indexOf("http://") != -1) {
					// 图片的下载
					mAbImageLoader.display(holder.mImageView1, imagePath);

				} else if (imagePath.indexOf("/") == -1) {
					// 索引图片
					try {
						int res = Integer.parseInt(imagePath);
						holder.mImageView1.setImageDrawable(mContext
								.getResources().getDrawable(res));
					} catch (Exception e) {
						holder.mImageView1
								.setImageResource(R.drawable.image_error);
					}
				} else {
					Bitmap mBitmap = AbFileUtil.getBitmapFromSD(new File(
							imagePath), AbImageUtil.SCALEIMG, mWidth, mHeight);
					if (mBitmap != null) {
						holder.mImageView1.setImageBitmap(mBitmap);
					} else {
						// 无图片时显示
						holder.mImageView1
								.setImageResource(R.drawable.image_empty);
					}
				}
			} else {
				// 直接显示
				holder.mImageView1.setImageBitmap(bitmap);
			}
		} else {
			// 无图片时显示
			holder.mImageView1.setImageResource(R.drawable.image_empty);
		}
		holder.mImageView1.setAdjustViewBounds(true);
		return convertView;
	}

	/**
	 * 增加并改变视图.
	 * 
	 * @param position
	 * @param imagePaths
	 */
	public void addItem(int position, String imagePaths) {
		mImagePaths.add(position, imagePaths);
		notifyDataSetChanged();
	}

	/**
	 * 增加多条并改变视图.
	 * 
	 * @param imagePaths
	 */
	public void addItems(List<String> imagePaths) {
		mImagePaths.addAll(imagePaths);
		notifyDataSetChanged();
	}

	/**
	 * 增加多条并改变视图.
	 */
	public void clearItems() {
		mImagePaths.clear();
		notifyDataSetChanged();
	}

	/**
	 * View元素.
	 */
	public static class ViewHolder {
		public ImageView mImageView1;
		public ImageView mImageView2;
	}

}
