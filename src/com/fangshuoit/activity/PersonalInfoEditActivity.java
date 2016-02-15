package com.fangshuoit.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.fangshuoit.application.SysConstants;
import com.fangshuoit.newsilkroad.R;
import com.fangshuoit.tool.JSONUtils;
import com.fangshuoit.tool.LoginUtil;
import com.fangshuoit.tool.NoUtil;
import com.fangshuoit.view.CustomProgressDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

public class PersonalInfoEditActivity extends AbActivity {

	private ImageView img;

	private View mAvatarView = null;

	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果

	public static final String IMAGE_UNSPECIFIED = "image/*";
	// 相机拍照得到的文件
	private File picture;
	// 图片文件地址
	private String photoadress = null;

	private Button button;

	private AbHttpUtil mAbHttpUtil = null;// 解析json

	private CustomProgressDialog progressDialog;

	private Bitmap photo;

	private EditText et_user_nickname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_edit_personal_info);
		initView();

		initEvent();
	}

	private void initView() {

		mAbHttpUtil = AbHttpUtil.getInstance(PersonalInfoEditActivity.this);// 初始化解析json
		progressDialog = new CustomProgressDialog(PersonalInfoEditActivity.this);

		button = (Button) findViewById(R.id.save);
		img = (ImageView) findViewById(R.id.user_pho);
		et_user_nickname = (EditText) findViewById(R.id.user_nick);
	}

	private void initEvent() {
		// 昵称
		et_user_nickname.setText(LoginUtil.get_nickname());
		et_user_nickname.addTextChangedListener(new EditChangedListener());
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				button.setEnabled(false);
				progressDialog.show();
				if (photoadress == null || photoadress == "") {
					analysisJsonReturn("", et_user_nickname.getText()
							.toString().trim()
							+ "");
				} else {
					try {
						analysisJsonQiniu();
					} catch (Exception e) {
						progressDialog.cancel();
					}
				}
			}
		});

		findViewById(R.id.address).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						AdressManageActivity.class);
				startActivity(intent);
			}
		});

		findViewById(R.id.fix_passworld).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getApplicationContext(),
								FixPasswActivity.class);
						startActivity(intent);
					}
				});
		findViewById(R.id.img_activity_fix_adress_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

		// 点击头像选择图片
		img.setOnClickListener(new OnClickListener() {
			@SuppressLint("InflateParams")
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = LayoutInflater
						.from(PersonalInfoEditActivity.this);// 创建一个View对象,将定义好的布局放入其中
				mAvatarView = inflater.inflate(R.layout.choose_avatar, null);
				Button albumButton = (Button) mAvatarView
						.findViewById(R.id.choose_album);
				Button camButton = (Button) mAvatarView
						.findViewById(R.id.choose_cam);
				Button cancelButton = (Button) mAvatarView
						.findViewById(R.id.choose_cancel);
				albumButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						AbDialogUtil
								.removeDialog(PersonalInfoEditActivity.this);
						// 从本地相册中去获取
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
								null);
						intent.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								IMAGE_UNSPECIFIED);
						startActivityForResult(intent, PHOTOZOOM);
					}
				});
				camButton.setOnClickListener(new OnClickListener() {
					@Override
					// 相机拍照
					public void onClick(View v) {
						AbDialogUtil
								.removeDialog(PersonalInfoEditActivity.this);
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
								.fromFile(new File(Environment
										.getExternalStorageDirectory(),
										"temp.jpg")));
						startActivityForResult(intent, PHOTOHRAPH);
					}
				});
				cancelButton.setOnClickListener(new OnClickListener() {
					@Override
					// 取消
					public void onClick(View v) {
						AbDialogUtil
								.removeDialog(PersonalInfoEditActivity.this);
					}
				});
				AbDialogUtil.showDialog(mAvatarView, Gravity.BOTTOM);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
			picture = new File(Environment.getExternalStorageDirectory()
					+ "/temp.jpg");
			photoadress = picture.getPath();
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			Uri uri = data.getData();
			photoadress = getPath(uri);
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
																		// 100)压缩文件
				img.setImageBitmap(photo);

				button.setEnabled(true);
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}

	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 从相册得到的url转换为SD卡中图片路径
	 */
	@SuppressWarnings("deprecation")
	public String getPath(Uri uri) {
		if (AbStrUtil.isEmpty(uri.getAuthority())) {
			return null;
		}
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(column_index);
		return path;
	}

	private String token;

	/**
	 * 
	 * @Title: uploadImg
	 * @Description: TODO(上传头像)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void uploadImg() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (token != null) {
					// 图片名称为当前日期+随机数生成
					final String key = LoginUtil.get_UserId()
							+ NoUtil.getNoByYY0000("");
					UploadManager uploadManager = new UploadManager();
					uploadManager.put(Bitmap2Bytes(photo), key, token,
							new UpCompletionHandler() {

								@Override
								public void complete(String arg0,
										ResponseInfo info, JSONObject arg2) {
									Log.i("qiniu", info.toString());
									if (info.isOK()) {
										analysisJsonReturn(key,
												et_user_nickname.getText()
														.toString().trim()
														+ "");
									} else {
										Toast.makeText(
												abApplication,
												R.string._personal_info_edit_activaty_head_on,
												Toast.LENGTH_SHORT).show();
									}
								}
							}, null);
				} else {
					Log.i("fail",
							getString(R.string._personal_info_edit_activaty_on));
				}

			}
		}).start();
	}

	/**
	 * 
	 * @Title: analysisJsonQiniu
	 * @Description: TODO(获取Token)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonQiniu() {
		String url = SysConstants.SERVER + SysConstants.TOKENQINIU;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String uptoken = JSONUtils.getString(content, "uptoken", "");
				if (uptoken != null && uptoken != "") {
					// 获得七牛上传凭证uploadToken
					token = uptoken;
					uploadImg();
				} else {
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

	/**
	 * 
	 * @Title: analysisJsonReturn
	 * @Description: TODO(成功后返回给后台修改信息)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void analysisJsonReturn(String photoUri, String nickname) {
		String url = SysConstants.SERVER + SysConstants.PERSONALEDIT;
		AbRequestParams params = new AbRequestParams();// 定义发送请求
		try {
			if (!photoUri.equals("")) {
				params.put("tempVo.photo", photoUri);
			}
			params.put("tempVo.id", LoginUtil.get_UserId());
			params.put("tempVo.nickName", nickname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int arg0, String content) {
				String code = JSONUtils.getString(content, "code", null);
				if (code.equals("0")) {
					Toast.makeText(abApplication,
							R.string._personal_info_edit_activaty_save_off,
							Toast.LENGTH_SHORT).show();
					finish();
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

	public class EditChangedListener implements TextWatcher {

		private CharSequence temp;// 监听前的文本

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			temp = LoginUtil.get_nickname();
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (s.length() == 10) {
				AbToastUtil.showToast(PersonalInfoEditActivity.this,
						R.string._personal_info_edit_activaty_nickname_ten);
			} else if (s.length() == 1) {
				AbToastUtil.showToast(PersonalInfoEditActivity.this,
						R.string._personal_info_edit_activaty_nickname_one);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (temp != s) {
				button.setEnabled(true);
			}
		}

	}
}
