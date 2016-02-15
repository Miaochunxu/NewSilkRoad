package com.fangshuoit.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.widget.ImageView;

import com.ab.image.AbImageLoader;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.AsyncImageLoader.ImageCallback;

/**
 * 图片加载工具类
 * 
 * @project NewSilkRoad
 * @Company 宁夏方硕信息技术有限公司
 * @author 周庆鹏
 * @phone 15009666891
 * @QQ 872123675
 * @Blog http://blog.csdn.net/lovelvyan
 * @date 2015-4-13
 * @time 上午11:43:38
 */
public class ImageUtil {

	/**
	 * 有缓存图片加载
	 * 
	 * @Description: TODO
	 * @date 2015-4-13
	 * @time 上午11:43:15
	 * @author lvyan
	 * @return
	 */

	public void loadImgUseCache(Context context, String uri, final ImageView img) {
		AsyncImageLoader asyncImageLoader = new AsyncImageLoader(context);
		asyncImageLoader.loadBitmap(uri, new ImageCallback() {
			public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
				img.setImageBitmap(imageDrawable);
			}
		});
	}

	/**
	 * 有缓存加载图片
	 * 
	 * @Description: TODO
	 * @date 2015-4-13
	 * @time 上午11:28:27
	 * @author lvyan
	 * @return
	 */
	public void loadImgUseAb(Context context, String url, final ImageView img) {
		// 图片的下载
		AbImageLoader mAbImageLoader = AbImageLoader.newInstance(context);
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
		// 原图片的下载
		mAbImageLoader.setMaxWidth(0);
		mAbImageLoader.setMaxHeight(0);
		mAbImageLoader.display(img, url);
	}

	public static Bitmap toRoundCorner(Bitmap bitmap, float pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		// 保证是方形，并且从中心画
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int w;
		int deltaX = 0;
		int deltaY = 0;
		if (width <= height) {
			w = width;
			deltaY = height - w;
		} else {
			w = height;
			deltaX = width - w;
		}
		final Rect rect = new Rect(deltaX, deltaY, w, w);
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		// 圆形，所有只用一个
		int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
		canvas.drawRoundRect(rectF, radius, radius, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * bitmap进行base64编码
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 读取sd卡的图片文件
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap buildBitma4File(String path) {

		File baseFile = new File(path);

		if (baseFile != null && baseFile.exists()) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			return BitmapFactory.decodeFile(path, options);
		} else {
			return null;
		}

	}

	/**
	 * 将base64编码的图片数据解码成图片
	 * 
	 * @param base64Data
	 * @return
	 */
	public static Bitmap buildBitma4Base64(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	public static Bitmap optimizeBitmap(Bitmap bitmap, int width, int height) {

		int nWidth = bitmap.getWidth();
		int nHeight = bitmap.getHeight();
		if (nWidth <= width || nHeight <= height) {
			return bitmap;
		}
		Matrix matrix = new Matrix();
		// float scaleWidth = ((float) width / nWidth);
		// float scaleHeight = ((float) height / nHeight);
		// matrix.postScale(scaleWidth, scaleHeight);
		float scaleWidth = ((float) width / nWidth);
		float scaleHeight = ((float) height / nHeight);
		float scale = scaleWidth >= scaleHeight ? scaleWidth : scaleHeight;
		matrix.postScale(scale, scale);
		return Bitmap.createBitmap(bitmap, 0, 0, nWidth, nHeight, matrix, true);
	}

	/**
	 * 压缩图片的大小（高宽）
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap compressBitmap(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图过大
			baos.reset();
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();

		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int width = newOpts.outWidth;
		int height = newOpts.outHeight;

		float STANDARD_HEIGHT = 960f;
		float STANDARD_WIDTH = 640f;
		int be = 1;
		be = (int) ((width / STANDARD_WIDTH + height / STANDARD_HEIGHT) / 2);
		newOpts.inSampleSize = be;
		isBm = new ByteArrayInputStream(baos.toByteArray());
		image = BitmapFactory.decodeStream(isBm, null, newOpts);
		return image;
	}

	public static Bitmap compressBitmap(String imagePath) {

		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置inJustDecodeBounds为true
		opts.inJustDecodeBounds = true;
		// 使用decodeFile方法得到图片的宽和高
		BitmapFactory.decodeFile(imagePath, opts);

		int width = opts.outWidth;
		int height = opts.outHeight;
		float STANDARD_HEIGHT = 1280f;
		float STANDARD_WIDTH = 720f;

		int be = 1;
		be = (int) ((width / STANDARD_WIDTH + height / STANDARD_HEIGHT) / 2);
		opts.inSampleSize = be;
		opts.inJustDecodeBounds = false;
		Bitmap image = BitmapFactory.decodeFile(imagePath, opts);
		return image;
	}

	public static Bitmap optimizeBitmap(Bitmap bitmap, int width) {

		int nWidth = bitmap.getWidth();
		int nHeight = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / nWidth);
		if (scaleWidth > 1) {
			return bitmap;
		} else {
			matrix.postScale(scaleWidth, scaleWidth);
			return Bitmap.createBitmap(bitmap, 0, 0, nWidth, nHeight, matrix,
					true);
		}
	}

	public static File saveMyBitmap(Bitmap bitmap) {

		String picPath = FileStorage.PICTURE_CACHE_DIR;
		File pahtFile = new File(picPath);
		if (!pahtFile.exists()) {
			pahtFile.mkdirs();
		}
		String picName = UUID.randomUUID().toString() + ".png";
		File f = new File(picPath + picName);
		FileOutputStream fOut = null;
		try {
			if (!f.exists() && f.isFile()) {
				f.createNewFile();
			}
			fOut = new FileOutputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

	/**
	 * 旋转图片
	 * 
	 * @Description: TODO
	 * @date 2015-4-13
	 * @time 上午11:52:10
	 * @author lvyan
	 * @return
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap) {
		if (bitmap != null && bitmap.getWidth() > bitmap.getHeight()) {
			final Matrix matrix = new Matrix();
			matrix.setRotate(90);
			try {
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, true);
			} catch (OutOfMemoryError ex) {
				System.gc();
				ex.printStackTrace();
				DebugLog.loge("error =" + ex.toString());
			}
		}
		return bitmap;
	}
}
