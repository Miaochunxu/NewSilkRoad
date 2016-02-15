package com.fangshuoit.tool;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

/**
 * 异步加载图片
 * 
 * @project NewSilkRoad
 * @Company 宁夏方硕信息技术有限公司
 * @author 周庆鹏
 * @phone 15009666891
 * @QQ 872123675
 * @Blog http://blog.csdn.net/lovelvyan
 * @date 2015-4-13
 * @time 上午11:35:09
 */
public class AsyncImageLoader {
	private ImageFileCacheUtil fileCache;
	private ImageMemoryCacheUtil memoryCache;
	private Context context;

	public AsyncImageLoader(Context context) {
		this.context = context;
		fileCache = new ImageFileCacheUtil();
		memoryCache = new ImageMemoryCacheUtil(context);
	}

	@SuppressLint("HandlerLeak")
	public Bitmap loadBitmap(final String imageUrl,
			final ImageCallback imageCallback) {
		Bitmap result = memoryCache.getBitmapFromCache(imageUrl);
		if (result == null) {
			result = fileCache.getImage(imageUrl);
			if (result != null) {
				memoryCache.addBitmapToCache(imageUrl, result);
			}
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Bitmap) message.obj, imageUrl);
			}
		};
		new Thread() {
			@Override
			public void run() {
				Bitmap bitmap = loadImageFromUrl(imageUrl);
				if (bitmap == null) {
					return;
				}
				memoryCache.addBitmapToCache(imageUrl, bitmap);
				fileCache.saveBitmap(bitmap, imageUrl);
				Message message = handler.obtainMessage(0, bitmap);
				handler.sendMessage(message);
			}
		}.start();
		return result;
	}

	public Bitmap loadImageFromUrl(String imageUrl) {
		InputStream imageStream = null;

		try {
			imageStream = new URL(imageUrl).openConnection().getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		// options.inSampleSize = 2;
		Bitmap bitmap = BitmapFactory.decodeStream(imageStream, null, options);
		return bitmap;
	}

	public interface ImageCallback {
		public void imageLoaded(Bitmap imageBitmap, String imageUrl);
	}

	public boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}
}
