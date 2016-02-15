//package com.fangshuoit.pay;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//import java.util.Random;
//import android.annotation.SuppressLint;
//import android.app.ProgressDialog;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.ab.http.AbHttpUtil;
//import com.ab.http.AbRequestParams;
//import com.ab.http.AbStringHttpResponseListener;
//import com.ab.util.AbToastUtil;
//import com.alipay.sdk.app.PayTask;
//import com.fangshuoit.application.SysConstants;
//import com.fangshuoit.newsilkroad.R;
//import com.fangshuoit.tool.JSONUtils;
//import com.fangshuoit.tool.LoginUtil;
//
//public class PayActivity extends FragmentActivity {
//
//	public static final String PARTNER = Keys.DEFAULT_PARTNER;
//	public static final String SELLER = Keys.DEFAULT_SELLER;
//	public static final String RSA_PRIVATE = Keys.PRIVATE;
//	public static final String RSA_PUBLIC = Keys.PUBLIC;
//	private static final int SDK_PAY_FLAG = 1;
//	private static final int SDK_CHECK_FLAG = 2;
//
//	// private static EGood good;
//	private String orderNo;
//
//	private TextView good_order_no, good_order_name, good_order_address,
//			good_order_price;
//
//	private EditText good_order_number;
//	private String orderId;
//	private String orderState;
//	private String payType;
//
//	private ProgressDialog progressDialog;
//	private AbHttpUtil mAbHttpUtil = null;
//
//	@SuppressLint("HandlerLeak")
//	private Handler mHandler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case SDK_PAY_FLAG: {
//				Result resultObj = new Result((String) msg.obj);
//				String resultStatus = resultObj.resultStatus;
//				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//				if (TextUtils.equals(resultStatus, "9000")) {
//					orderState = "3";
//					updateOrderData();
//				} else {
//					// 判断resultStatus 为非“9000”则代表可能支付失败
//					// “8000”
//					// 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//					if (TextUtils.equals(resultStatus, "8000")) {
//						orderState = "1";
//						Toast.makeText(PayActivity.this, "支付结果确认中",
//								Toast.LENGTH_SHORT).show();
//					} else {
//						orderState = "1";
//						Toast.makeText(PayActivity.this, "支付失败",
//								Toast.LENGTH_SHORT).show();
//					}
//				}
//				break;
//			}
//			case SDK_CHECK_FLAG: {
//				Toast.makeText(PayActivity.this, "检查结果为：" + msg.obj,
//						Toast.LENGTH_SHORT).show();
//				break;
//			}
//			default:
//				break;
//			}
//		};
//	};
//
//	@SuppressLint("NewApi")
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_pay_test);
//
//		getActionBar().setBackgroundDrawable(
//				this.getBaseContext().getResources()
//						.getDrawable(R.drawable.actionbar_bg));
//
//		getActionBar().setDisplayHomeAsUpEnabled(false);
//		getActionBar().setDisplayShowHomeEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);
//		getActionBar().setLogo(
//				getResources().getDrawable(R.drawable.ic_action_back));
//		getActionBar().setTitle("在线购买");
//		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
//				"id", "android");
//		TextView title = (TextView) findViewById(titleId);
//		title.setTextColor(getResources().getColor(R.color.white));
//		title.setTextSize(20);
//
//		Bundle extras = getIntent().getExtras();
//		// good = (EGood) extras.getSerializable("good");
//		orderNo = getOutTradeNo();
//
//		mAbHttpUtil = AbHttpUtil.getInstance(this);
//		progressDialog = new ProgressDialog(this);
//		progressDialog.setCanceledOnTouchOutside(false);
//
//		good_order_no = (TextView) findViewById(R.id.good_order_no);
//		good_order_name = (TextView) findViewById(R.id.good_order_name);
//		good_order_address = (TextView) findViewById(R.id.good_order_address);
//		good_order_price = (TextView) findViewById(R.id.good_order_price);
//
//		good_order_number = (EditText) findViewById(R.id.good_order_number);
//
//		good_order_no.setText(orderNo);
//		good_order_name.setText(good.getName());
//		good_order_address.setText(good.getAddress());
//		good_order_price.setText(good.getPrice() + "元");
//		// submitOrderData();
//	}
//
//	/**
//	 * 提交订单信息
//	 * 
//	 * @Description: TODO
//	 * @date 2015-1-9
//	 * @time 上午9:08:47
//	 * @author lvyan
//	 * @return
//	 */
//	public void submitOrderData() {
//		String url = SysConstants.SERVER;
//		AbRequestParams params = new AbRequestParams();
//		try {
//			params.put("tempVo.orderNo", orderNo);
//			params.put("tempVo.checkCode", "1");
//			params.put("tempVo.EGood.id", good.getId());
//			params.put("tempVo.creater", LoginUtil.get_UserId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
//			@Override
//			public void onSuccess(int statusCode, String content) {
//				String body = JSONUtils.getString(content, "body", "");
//				orderId = JSONUtils.getString(body, "id", "");
//				String code = JSONUtils.getString(content, "code", "");
//				String message = JSONUtils.getString(content, "message", "");
//				System.out.println(body);
//				if (code.equals("-1")) {
//					AbToastUtil.showToast(PayActivity.this, "商品已买完请选择其它商品！");
//					finish();
//				} else {
//					if (payType.equals("1")) {
//						String orderInfo = getOrderInfo(good.getName(),
//								good.getContent(), good.getPrice());
//						String sign = sign(orderInfo);
//						try {
//							// 仅需对sign 做URL编码
//							sign = URLEncoder.encode(sign, "UTF-8");
//						} catch (UnsupportedEncodingException e) {
//							e.printStackTrace();
//						}
//						final String payInfo = orderInfo + "&sign=\"" + sign
//								+ "\"&" + getSignType();
//						Runnable payRunnable = new Runnable() {
//							@Override
//							public void run() {
//								// 构造PayTask 对象
//								PayTask alipay = new PayTask(PayActivity.this);
//								// 调用支付接口
//								String result = alipay.pay(payInfo);
//								Message msg = new Message();
//								msg.what = SDK_PAY_FLAG;
//								msg.obj = result;
//								mHandler.sendMessage(msg);
//							}
//						};
//						Thread payThread = new Thread(payRunnable);
//						payThread.start();
//					} else {
//						updateOrderData();
//					}
//				}
//			}
//
//			// 开始执行前
//			@Override
//			public void onStart() {
//				progressDialog.setMessage("正在生成订单信息，请稍后...");
//				progressDialog.show();
//			}
//
//			@Override
//			public void onFailure(int statusCode, String content,
//					Throwable error) {
//				AbToastUtil.showToast(PayActivity.this, "生成订单失败，请重试！");
//				progressDialog.cancel();
//			}
//
//			public void onFinish() {
//				progressDialog.cancel();
//			};
//		});
//	}
//
//	/**
//	 * 更新订单信息
//	 * 
//	 * @Description: TODO
//	 * @date 2015-1-9
//	 * @time 上午9:13:41
//	 * @author lvyan
//	 * @return
//	 */
//	public void updateOrderData() {
//		String url = SysConstants.SERVER;
//		AbRequestParams params = new AbRequestParams();
//		try {
//			params.put("tempVo.state", orderState);
//			params.put("tempVo.payType", payType);
//			params.put("tempVo.id", orderId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("提交的参数：" + params.getParamString().toString());
//		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
//			@Override
//			public void onSuccess(int statusCode, String content) {
//				String body = JSONUtils.getString(content, "body", "");
//				String code = JSONUtils.getString(content, "code", "");
//				String message = JSONUtils.getString(content, "message", "");
//				if (code.equals("0")) {
//					/*
//					 * Intent intent = new Intent();
//					 * intent.setClass(PayActivity.this,
//					 * OrderViewActivity.class); Bundle bundle = new Bundle();
//					 * bundle.putString("orderno", orderNo);
//					 * bundle.putString("type", "pay");
//					 * bundle.putSerializable("good", good);
//					 * intent.putExtras(bundle); startActivity(intent);
//					 * finish();
//					 */
//				} else if (code.equals("-1")) {
//					AbToastUtil.showToast(PayActivity.this, "订单信息提交失败，请联系客服！");
//				} else {
//					AbToastUtil.showToast(PayActivity.this, "订单信息提交失败，请联系客服！");
//				}
//			}
//
//			// 开始执行前
//			@Override
//			public void onStart() {
//				progressDialog.setMessage("正在更新订单信息，请稍后...");
//				progressDialog.show();
//			}
//
//			@Override
//			public void onFailure(int statusCode, String content,
//					Throwable error) {
//				AbToastUtil.showToast(PayActivity.this, error.getMessage());
//				progressDialog.cancel();
//			}
//
//			public void onFinish() {
//				progressDialog.cancel();
//			};
//		});
//	}
//
//	/**
//	 * 调用SDK支付
//	 * 
//	 * @Description: TODO
//	 * @date 2015-1-7
//	 * @time 下午10:09:41
//	 * @author lvyan
//	 * @return
//	 */
//	public void pay(View v) {
//		payType = "1";
//		submitOrderData();
//	}
//
//	/**
//	 * 线下交易
//	 * 
//	 * @Description: TODO
//	 * @date 2015-1-7
//	 * @time 下午10:09:41
//	 * @author lvyan
//	 * @return
//	 */
//	public void payLine(View v) {
//		payType = "2";
//		orderState = "2";
//		submitOrderData();
//	}
//
//	/**
//	 * 查询终端设备是否存在支付宝认证账户
//	 * 
//	 * @Description: TODO
//	 * @date 2015-1-7
//	 * @time 下午10:09:22
//	 * @author lvyan
//	 * @return
//	 */
//	public void check(View v) {
//		Runnable checkRunnable = new Runnable() {
//			@Override
//			public void run() {
//				PayTask payTask = new PayTask(PayActivity.this);
//				boolean isExist = payTask.checkAccountIfExist();
//				Message msg = new Message();
//				msg.what = SDK_CHECK_FLAG;
//				msg.obj = isExist;
//				mHandler.sendMessage(msg);
//			}
//		};
//		Thread checkThread = new Thread(checkRunnable);
//		checkThread.start();
//	}
//
//	/**
//	 * 获取SDK版本号
//	 * 
//	 * @Description: TODO
//	 * @date 2015-1-7
//	 * @time 下午10:09:00
//	 * @author lvyan
//	 * @return
//	 */
//	public void getSDKVersion() {
//		PayTask payTask = new PayTask(this);
//		String version = payTask.getVersion();
//		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
//	}
//
//	/**
//	 * 创建订单信息
//	 * 
//	 * @Description: TODO
//	 * @date 2015-1-7
//	 * @time 下午10:08:34
//	 * @author lvyan
//	 * @return
//	 */
//	public String getOrderInfo(String subject, String body, String price) {
//		// 合作者身份ID
//		String orderInfo = "partner=" + "\"" + PARTNER + "\"";
//		// 卖家支付宝账号
//		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
//		// 商户网站唯一订单号
//		orderInfo += "&out_trade_no=" + "\"" + orderNo + "\"";
//		// 商品名称
//		orderInfo += "&subject=" + "\"" + subject + "\"";
//		// 商品详情
//		orderInfo += "&body=" + "\"" + body + "\"";
//		// 商品金额
//		orderInfo += "&total_fee=" + "\"" + price + "\"";
//		// 服务器异步通知页面路径
//		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
//				+ "\"";
//		// 接口名称， 固定值
//		orderInfo += "&service=\"mobile.securitypay.pay\"";
//		// 支付类型， 固定值
//		orderInfo += "&payment_type=\"1\"";
//		// 参数编码， 固定值
//		orderInfo += "&_input_charset=\"utf-8\"";
//		// 设置未付款交易的超时时间
//		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
//		// 取值范围：1m～15d。
//		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
//		// 该参数数值不接受小数点，如1.5h，可转换为90m。
//		orderInfo += "&it_b_pay=\"30m\"";
//		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//		orderInfo += "&return_url=\"m.alipay.com\"";
//		// 调用银行卡支付，需配置此参数，参与签名， 固定值
//		// orderInfo += "&paymethod=\"expressGateway\"";
//		return orderInfo;
//	}
//
//	/**
//	 * 生成订单编号
//	 * 
//	 * @Description: TODO
//	 * @date 2015-1-7
//	 * @time 下午10:07:48
//	 * @author lvyan
//	 * @return
//	 */
//	public String getOutTradeNo() {
//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//				Locale.getDefault());
//		Date date = new Date();
//		String key = format.format(date);
//		Random r = new Random();
//		key = key + r.nextInt();
//		key = key.substring(0, 15);
//		return key;
//	}
//
//	/**
//	 * 对订单信息进行签名 ,待签名订单信息。
//	 */
//	public String sign(String content) {
//		return SignUtils.sign(content, RSA_PRIVATE);
//	}
//
//	/**
//	 * 获取签名方式
//	 */
//	public String getSignType() {
//		return "sign_type=\"RSA\"";
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.empty, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			PayActivity.this.finish();
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//}
