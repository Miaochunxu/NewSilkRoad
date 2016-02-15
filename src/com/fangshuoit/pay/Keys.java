/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.fangshuoit.pay;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

/*	// 合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088811146973747";
	// 收款支付宝账号
	public static final String DEFAULT_SELLER = "164015004@qq.com";
	// 商户私钥，自助生成
	public static final String PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALqURgi/Xf5io1XAAQiDl+C21/OG1yQoSV2RLAzfJWRjg8I2j3prHcQLX1aGBM47XAcyH9sYANPjWwNkwEyr3qH5ltfJyxGI3Hg7bfu/je/jB93HfQpCQAoPVXxAhk6gspQwDR9zWUupbWN2A12YUYLbcZubwstgnNNNFYuod6YpAgMBAAECgYEArWRoZW66wlMWqfUiwK/SsHIb5OSWAbMy4zKrtlaWYGIfK5ka49CyO+O5uSYZRzNhBgruEeWPt1qd1VyHXXmIDnacZZO7VGjLc3wqaLVFL/kwbn44jKEqQOgaZRWc1ypCVQzHdEPAXdfHGKuunjt0xPNEYdUt3/2n6yutZ+yrjXUCQQDm0qSdIax1aieEOfOYCEm4X9TQ+xdtKX6BKZecJdDBVGl3tQTecJy6QGFRhHSelLCKbHKdg/DT9xIzZx4EK0i3AkEAzu4yunapKgbu6VLN8/2N/wqlHrYxDOMOCBx2T3zwufToSGRoF+A5TzduOwLtOHRcFjpH1ZjR2cG9iaMdYUzoHwJAc7LOFH0Whtwo5pxtv50X5rUSZZYWjf8oi/GfLwO9ecqn+vrfb+gehzVSEpxEIGEDhL8LTG0gP/8uPlnHr+tKTQJBAJnKWDNSq0jL3FXWL7DNWfIZtDxOBchhG3WVyawr3DEoPlZHIUHVaGJifO8orqtNd38hk2/A4v2myrn3W4eMA9sCQG7P2wS2zuOJwEXGB72qn1OOnju7Mz0ioFnCWT1rR80+P/xjAZMK7bmlqPL8fmmzMV2kuEnXTRppSCRMt1ZsJJY=";
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	*/
	
	/*绿衣支付宝*/
	// 合作身份者id，以2088开头的16位纯数字
		public static final String DEFAULT_PARTNER = "2088811277687661";
		// 收款支付宝账号
		public static final String DEFAULT_SELLER = "522936200@qq.com";
		// 商户私钥，自助生成
		public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
		public static final String PRIVATE ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKUARxiCoWXOrfzVfFVmOwRXis3TFd2tlIy31wIa5GbmpNqrhAHnPLuZzzGUDpY/rybPZt9G+hxc8lZyk+1pGspcH8wOBA9Yr9bmTUa8PAKs98afXaeGTAWQiwdS30cHhxrMwfgMDQiRcbihyLzUu92uAgpdLGZXOTjqzIa2TneFAgMBAAECgYEAomFTllt4Wqi7sUanxaOGEQ/WGhjSPAbHWnTRs9CjVmFFe62vi+/1ZOpJbZE3icXCqX8Szkkbey49j7XSvqHhKhA0pDdfqDOPrHAraVfX8G72fWAA6N+uFYUy0V/3oOA4ZnUHZjbm35j0bk9dCdFiJ4JNXn7Gw3MUYyxawJnS+gECQQDTCJASChgk6LaBqDR9QgaPutUAgun6rAFJuUM44xXM23dTrcBU9LEH9AQlVbXKbGQTWPi66X7wRwxuwF98kU+9AkEAyCi+sv2WVw6SpPJzFR6o0/xvNpTYuXM5GiPrRldi3HOZIaMhDLcV7yarpLtTks4Cw2m/mNdgWG9i6GZo8B5/aQJASk2ShHd/Zsd1MxOpX4z2yHaM9sAhZ/xV+8I6M/Zov5dqXVEpDgCVsGovZ4HVHy+o0NMbyJvb8PfjHb+oHzD+tQJAA79Xiqxawzh/oFz1sKCJUqa4QlJZGGfC2u1vOzrpm+0CIfIW7VEENR0JO6VSND5tKNFzB96IbD9xn55DOrOneQJBANL1UIhKa1AKrfCj+CtjRA8WtkmySplCX3540F2h+zq+TXbBqmvCtZvoH4zMtd/eW3Ylbm6bLdhr46mHeAi4+xg=";
				
				
}
