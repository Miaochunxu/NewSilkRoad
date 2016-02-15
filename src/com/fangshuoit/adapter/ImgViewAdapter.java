package com.fangshuoit.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fangshuoit.tool.AsyncImageLoader;
import com.fangshuoit.tool.AsyncImageLoader.ImageCallback;

public class ImgViewAdapter extends PagerAdapter {
	private List<String> mPaths;
	private Context mContext;
	AsyncImageLoader asyncImageLoader;

	public ImgViewAdapter(Context context) {
		mContext = context.getApplicationContext();
		asyncImageLoader = new AsyncImageLoader(context);
	}

	public void refreshData(List<String> listItems) {
		this.mPaths = listItems;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mPaths.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == (View) obj;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView img = new ImageView(mContext);
		img.setScaleType(ImageView.ScaleType.CENTER_CROP);
		System.out.println(mPaths.get(position));
		try {
			Bitmap cachedImage = asyncImageLoader.loadBitmap(
					mPaths.get(position), new ImageCallback() {
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
						}
					});
			img.setImageBitmap(cachedImage);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		((ViewPager) container).addView(img, 0);
		return img;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
