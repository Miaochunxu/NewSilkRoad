package com.fangshuoit.application;

public class SysConstants {

	// public static final String SERVER_NAME = "192.168.0.119:80/silkroad";
	// public static final String SERVER_NAME = "10.130.44.199:8080/silkroad";
	public static final String SERVER_NAME = "192.168.1.104:8080/silkroad";
	// public static final String SERVER_NAME = "180.76.143.77";
	public static final String SERVER = "http://" + SERVER_NAME + "/";
	public static final String VERSION = "1.0";// 当前版本号
	// 更新应用地址
	public static final String UPDATEADDRESS = SERVER + "upload/ete.apk";
	public static final String UTF_8 = "UTF-8";
	public static final int CONNECT_TIMEOUT = 60 * 1000;
	public static final int READ_TIMEOUT = 60 * 1000;
	public static final String USER_AGENT_KEY = "User-Agent";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String COOKIE = "Cookie";
	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_VALUE = "*/*;version=";
	public static final String GZIP_DEFLATE = "gzip,deflate";
	public static final String CONTENT_ENCODING = "Content-Encoding";
	public static final String GZIP = "gzip";
	public static final String CONNECT_METHOD_GET = "GET";
	public static final String CONNECT_METHOD_POST = "POST";
	public static final String FORMAT_JSON = "JSON";
	public static int ZIP_BUFFER_SIZE = 1024;
	public static final String ZERO = "0";
	public static final String API_VERSION = "1.0.0";
	public static final int SUCCESS_CODE = 200;
	public static final String APP_KEY = "secret_appkey";
	public static final String TICKET_KEY = "secret_ticket";
	public static final String CARCUBE_USERID = "secret_userid";
	public static final String VIRTUAL_TOKEN = "secret_virtoken";
	public static final String RESP_SUCCESS = "0";
	public static final String RESP_SYSTEM_ERROR = "-1";

	public static final int TOAST = 1;
	public static final int NET_ERROR = TOAST + 1;
	public static final int RESPONSE_SUCCESS = NET_ERROR + 1;
	public static final int LOAD = RESPONSE_SUCCESS + 1;
	public static final int SYNCLOAD = LOAD + 1;
	public static final int DISSMISS = SYNCLOAD + 1;

	public static final String GET_CHECK_VERSION = "go_checkVersion.action";

	// 获取地区（没有参数）
	public static final String CHANGE_PLACE = "VAreaMager/go_appArea";
	// 注册（DUserSimpleMager/go_appbitch?user.loginName=?&user.loginPassword=?&tempVo.reservation=?&tempVo.nickName=?）
	public static final String REGISTER = "DUserSimpleMager/go_appbitch";
	// 登录（loginMager/go_appfuck?tempUser.loginName=?&tempUser.loginPassword=?）
	public static final String LOGIN = "loginMager/go_appfuck";
	// 旅游主页信息（idList=?&&typeId=?&&num=?）
	public static final String TOURHOMEDATA = "MarketAppMager/go_appHomeListView";
	// 旅游主页广告（没有参数）
	public static final String TOURHOMEADS = "MarketAppMager/go_appAdvertisement";
	// 商城首页信息（没有参数）
	public static final String MALLHOMEDATA = "MarketAppMager/go_appMarketPush";
	// 商城特产接口（MarketAppMager/go_appMarketListView?idList=?&&typeId=?&&num=?）
	public static final String MALLHOMETECHAN = "MarketAppMager/go_appMarketListView";
	// 商品列表( MarketAppMager/go_appMarketPushOneIn?typeId=?&&num=?&&location=?)
	public static final String MALLSORTGOODS = "MarketAppMager/go_appMarketPushOneIn";
	// 商品加入购物车（ MarketAppMager/go_appShoppingCar？tid=？&idList=？&num=?
	public static final String SHOPPINGCAR = "MarketAppMager/go_appShoppingCar";
	// 查看购物车内商品（MarketAppMager/go_appShoppingCarView?tid=?)
	public static final String SHOWSHOPPINGCAR = "MarketAppMager/go_appShoppingCarView";
	// 选择收获地址（MarketAppMager/go_appAddressView?tid=?）
	public static final String SHOWADRESS = "MarketAppMager/go_appAddressView";
	// 添加收货地址（MarketAppMager/go_appAddressAdd?address.userId=?&&address.name=?&&address.phone=?&&address.address=?）
	public static final String ADDADRESS = "MarketAppMager/go_appAddressAdd";
	// 修改收货地址（MarketAppMager/go_appAddressEdit?address.id=?&&address.userId=?&&address.name=?&&address.phone=?&&address.address=?）
	public static final String FIXADRESS = "MarketAppMager/go_appAddressEdit";
	// 删除收货地址（MarketAppMager/go_appAddressDel?address.id=?）
	public static final String DETELEADRESS = "MarketAppMager/go_appAddressDel";
	// 购物车删除商品（MarketAppMager/go_appShoppingCarDel?tid=?&&idList=?）
	public static final String DELETESHOPPINGCAR = "MarketAppMager/go_appShoppingCarDel";
	// 修改购物车数量(MarketAppMager/go_appShoppingCarEdit?tid=?&&idList=?&&num=?)
	public static final String FIXSHOPPINGCARNUMBER = "MarketAppMager/go_appShoppingCarEdit";
	// 每一件商品JSON解析（ MarketAppMager/go_appDetail?tid=?）
	public static final String EVERYGOODS = "MarketAppMager/go_appDetail";
	// 商品评论（MarketAppMager/go_appCommentView?tid=?&&option=?）
	public static final String EVERYGOODSCOMMENTS = "MarketAppMager/go_appCommentView";
	// 添加入收藏(MarketAppMager/go_appCollectAdd?tid=?&&idList=?)
	public static final String ADDCOLLECTION = "MarketAppMager/go_appCollectAdd";
	// 删除收藏(MarketAppMager/go_appCollectDel?tid=?)
	public static final String DETELECOLLECTION = "MarketAppMager/go_appCollectDel";
	// 显示所有收藏(MarketAppMager/go_appCollectView?tid=?)
	public static final String SHOWCOLLECTION = "MarketAppMager/go_appCollectView";
	// 文化主页ListView内容(没有参数)
	public static final String SHOWCULTURE = "MarketAppMager/go_appCultureView";
	// 搜索（MarketAppMager/go_appSearch？option=？）
	public static final String SEARCH = "MarketAppMager/go_appSearch";
	// 个人中心信息展示(DUserSimpleMager/go_appInfo?tempVo.id)
	public static final String PERSONALSHOW = "DUserSimpleMager/go_appInfo";
	// 修改个人密码(DUserSimpleMager/go_appxixi?user.Id=?&user.loginPassword=?&idList=?)
	public static final String FIXPASSWORD = "DUserSimpleMager/go_appxixi";
	// 我得订单展示（MarketAppMager/go_appOrderView?tid=?&&option=?）
	public static final String MORDERSHOW = "MarketAppMager/go_appOrderView";
	// 修改个人资料(DUserSimpleMager/go_appInUp?tempVo.**=?tempVo.id)
	public static final String PERSONALEDIT = "DUserSimpleMager/go_appInUp";
	// 商品分类（MarketAppMager/go_appGetTypeIdList）
	public static final String MALLSORT = "MarketAppMager/go_appGetTypeIdList";
	// 七牛token
	public static final String TOKENQINIU = "QiniuMager/go_appGetUpToken";

	// #####订单处理
	// 订单添加(MarketAppMager/go_appOrderAdd?tid=?&&idList=?&&addessId=?)
	public static final String ADDORDER = "MarketAppMager/go_appOrderAdd";
	// 订单查看(MarketAppMager/go_appOrderView?tid=?)
	public static final String SHOWORDER = "MarketAppMager/go_appOrderView";
	// 订单详情（MarketAppMager/go_appOrderViewOne?tid=?）
	public static final String SHOWORDERINFO = "MarketAppMager/go_appOrderViewOne";
	public static final String LOGISTICS = "MarketAppMager/go_appExpressView";
	// 订单删除(MarketAppMager/go_appOrderDel?order=?)
	public static final String DETELEORDER = "MarketAppMager/go_appOrderDel";
	// 确认收货 MarketAppMager/go_appConfirmReceiving?tid=?
	public static final String ORDEROK = "MarketAppMager/go_appConfirmReceiving";
	// 立即评价（MarketAppMager/go_appCommentAdd?comment.**=?&&tid=?）
	public static final String ORDERCOMMENTNOW = "MarketAppMager/go_appCommentAdd";
	// 订单支付（MarketAppMager/go_appOrderPay?tid= //支付订单号&num= //支付标示 al-支付宝
	// unio-银联
	public static final String PAYORDER = "MarketAppMager/go_appOrderPay";
	// unio返回
	public static final String PAYORDERRESULT = "web/go_app_order_pay";

	public static final String GET_DOC_DETAIL = "go_appDocView.action";

	public static final String FIND_USER_PASSWORD = "go_appRePassword.action";
	public static final String CHECEVERSION = "admin/checkVersion?version=";// 检查更新方法
	public static final String TODAY_AWARDS_URL = "award/getNearAward";
	public static final String SHAKE_AWARD_URL = "award/getMotionAward";
	public static final String SHAKE_FREE_AWARD_URL = "user/getFreeUserAward";
	public static final String SHAKE_NOMAL_AWARD_URL = "award/getNomalMotionAward";
	public static final String DELETE_AWARD_URL = "user/deleteAward";
	public static final String MODIFY_PWD_URL = "user/userModifyPassword";
	public static final String MODIFY_INFO_URL = "user/userModify";
	public static final String LOGIN_URL = "user/login";// 用户登录方法
	public static final String REGISTER_URL = "user/userAdd";
	public static final String ORDER_INFO_URL = "order/getAndroidOrderInfo";
	public static final String UPDATE_ORDER_URL = "order/updateOrderStatus";
	public static final String GET_USER_AWARD_URL = "award/getUserAward";
	public static final String GET_USER_AMOUNT_URL = "user/getUserAmount";
	public static final String APP_URL = "award/queryAppAddressById";

	// 系统请求参数
	public static final String USER_ID = "userId";
	public static final String USER_ACCOUNT = "userAccount";
	public static final String USER_PASSWORD = "userPassword";
	public static final String NEW_PASSWORD = "newUserPassword";
	public static final String USER_NAME = "userName";
	public static final String USER_PHONE = "userPhone";
	public static final String USER_EMAIL = "userEmail";
	public static final String LANTITUDE = "lat";
	public static final String LONGITUDE = "lng";
	public static final String ORDER_AMOUNT = "orderAmount";

}
