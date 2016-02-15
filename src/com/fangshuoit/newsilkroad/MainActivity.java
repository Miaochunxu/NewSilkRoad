package com.fangshuoit.newsilkroad;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fangshuoit.fragment.CultureFragment;
import com.fangshuoit.fragment.MallFragment;
import com.fangshuoit.fragment.PersonFragment;
import com.fangshuoit.tool.LoginUtil;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private Fragment fg_mall, fg_culture, fg_person;

	// private Fragment fg_tour;

	private LinearLayout ly_mall, ly_culture, ly_person;

	// private LinearLayout ly_tour;
	// private ImageView img_tour;
	private ImageView img_mall, img_culture, img_person;
	// private TextView tv_tour
	private TextView tv_mall, tv_culture, tv_person;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initView();

		initEvent();

		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		if (fg_mall == null) {
			fg_mall = new MallFragment();
			t.add(R.id.fg_main_content, fg_mall);
		} else {
			t.show(fg_mall);
		}
		t.commit();
	}

	private void initEvent() {
		// ly_tour.setOnClickListener(this);
		ly_mall.setOnClickListener(this);
		ly_culture.setOnClickListener(this);
		ly_person.setOnClickListener(this);
	}

	private void initView() {

		// ly_tour = (LinearLayout) findViewById(R.id.ly_tour);
		ly_mall = (LinearLayout) findViewById(R.id.ly_mall);
		ly_culture = (LinearLayout) findViewById(R.id.ly_culture);
		ly_person = (LinearLayout) findViewById(R.id.ly_person);

		// img_tour = (ImageView) findViewById(R.id.iv_tour);
		img_mall = (ImageView) findViewById(R.id.iv_mall);
		img_culture = (ImageView) findViewById(R.id.iv_culture);
		img_person = (ImageView) findViewById(R.id.iv_person);

		// tv_tour = (TextView) findViewById(R.id.tv_tour);
		tv_mall = (TextView) findViewById(R.id.tv_mall);
		tv_culture = (TextView) findViewById(R.id.tv_culture);
		tv_person = (TextView) findViewById(R.id.tv_person);

	}

	private void getWindowsXY() {
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);

		@SuppressWarnings("deprecation")
		int width = wm.getDefaultDisplay().getWidth();

		LoginUtil.rember_ImageSize(width);

	}

	@Override
	public void onClick(View v) {

		outColorAndImg();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// HideFragment(transaction, fg_tour, fg_mall, fg_culture, fg_person);
		HideFragment(transaction, fg_mall, fg_culture, fg_person);

		switch (v.getId()) {
		// case R.id.ly_tour:
		// transaction.show(fg_tour);
		// tv_tour.setTextColor(0xff368ff7);
		// img_tour.setBackgroundResource(R.drawable.btn_selvyou);
		// break;
		case R.id.ly_mall:
			// if (fg_mall == null) {
			// fg_mall = new MallFragment();
			// transaction.add(R.id.fg_main_content, fg_mall);
			// } else {
			// transaction.show(fg_mall);
			// }
			// tv_mall.setTextColor(0xff368ff7);
			// img_mall.setBackgroundResource(R.drawable.btn_seshangcheng);
			transaction.show(fg_mall);
			tv_mall.setTextColor(0xff368ff7);
			img_mall.setBackgroundResource(R.drawable.btn_seshangcheng);
			break;
		case R.id.ly_culture:
			if (fg_culture == null) {
				fg_culture = new CultureFragment();
				transaction.add(R.id.fg_main_content, fg_culture);
			} else {
				transaction.show(fg_culture);
			}
			tv_culture.setTextColor(0xff368ff7);
			img_culture.setBackgroundResource(R.drawable.btn_sewenhua);
			break;
		case R.id.ly_person:
			if (fg_person == null) {
				fg_person = new PersonFragment();
				transaction.add(R.id.fg_main_content, fg_person);
			} else {
				transaction.show(fg_person);
			}
			tv_person.setTextColor(0xff368ff7);
			img_person.setBackgroundResource(R.drawable.btn_segeren);
			break;
		// case R.id.img_language:
		//
		// dialog.show();
		//
		// break;
		// case R.id.language_chinese:
		// Toast.makeText(MainActivity.this, "Chinese", Toast.LENGTH_SHORT)
		// .show();
		// break;
		// case R.id.language_english:
		// Toast.makeText(MainActivity.this, "English", Toast.LENGTH_SHORT)
		// .show();
		// break;
		// case R.id.language_arabic:
		// Toast.makeText(MainActivity.this, "Arabic", Toast.LENGTH_SHORT)
		// .show();
		// break;

		default:
			break;
		}
		transaction.commit();
	}

	public void HideFragment(FragmentTransaction transaction, Fragment f01,
			Fragment f02, Fragment f03) {

		if (f01 != null) {
			transaction.hide(f01);
		}
		if (f02 != null) {
			transaction.hide(f02);
		}
		if (f03 != null) {
			transaction.hide(f03);
		}
		// if (f04 != null) {
		// transaction.hide(f04);
		// }
	}

	/**
	 * 重置颜色和图片
	 * 
	 * @author 缪春旭
	 * 
	 */

	private void outColorAndImg() {
		// tv_tour.setTextColor(0xffd3cec7);
		tv_mall.setTextColor(0xffd3cec7);
		tv_culture.setTextColor(0xffd3cec7);
		tv_person.setTextColor(0xffd3cec7);

		// img_tour.setBackgroundResource(R.drawable.btn_unlvyou);
		img_mall.setBackgroundResource(R.drawable.btn_unshangcheng);
		img_culture.setBackgroundResource(R.drawable.btn_unwenhua);
		img_person.setBackgroundResource(R.drawable.btn_ungeren);

	}

	@Override
	protected void onResume() {
		try {
			if (LoginUtil.get_ImageSizeX() == 0) {
				getWindowsXY();
			}
		} catch (Exception e) {
		}

		super.onResume();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) // keyCode
												// 调用手机键盘，KeyEvent.KEYCODE_BACK调用返回键
		{
			exitByTwoClick(); // 调用双击退出函数
		}
		return false;
	}

	private static Boolean isExit = false;

	private void exitByTwoClick() {
		Timer tExit = new Timer(); // Timer：定时器，功能是在指定的时间间隔内反复触发指定窗口的定时器事件。
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, R.string._pull_two_finish, Toast.LENGTH_SHORT)
					.show();
			tExit.schedule(new TimerTask() {
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
		}
	}
}
