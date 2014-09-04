package com.weibo.tencent.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.weibo.joechan.util.TextUtil;
import com.weibo.tencent.model.Parameter;
import com.weibo.tencent.util.Constants;

/**
 *@author coolszy
 *@date 2011-5-29
 *@blog http://blog.csdn.net/coolszy
 */

public class OAuth
{
	public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
	public static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
	public static final String OAUTH_TIMESTAMP = "oauth_timestamp";
	public static final String OAUTH_NONCE = "oauth_nonce";
	public static final String OAUTH_VERSION = "oauth_version";
	public static final String OAUTH_CALLBACK = "oauth_callback";
	public static final String OAUTH_VERIFIER = "oauth_verifier";
	public static final String OAUTH_TOKEN = "oauth_token";
	public static final String OAUTH_SIGNATURE = "oauth_signature";

	public static final String FORMAT = "format";
	public static final String CONTENT = "content";
	public static final String CLIENTIP = "clientip";
	public static final String JING = "jing";
	public static final String WEI = "wei";

	public static final String CONSUMER_KEY = "801393143";
	public static final String CONSUMER_SECRET = "b23134b39af011264a19b0ed87d9eff8";
	public static final String SIGNATURE_METHOD = "HMAC-SHA1";
	public static final String VERSION = "1.0";

	public static final String ENCODING = "UTF-8";

	/**
	 * 获取请求参数
	 * 
	 * @param urlStr
	 *            请求URL
	 * @param httpMethod
	 *            请求方式
	 * @param callBack
	 *            认证成功后浏览器会被重定向到这个url中，没有回调url，此时设置为字符串“null”即可
	 * @return
	 * @throws Exception
	 */
	public static String getPostParams(String urlStr, String httpMethod, String callBack) throws Exception
	{
		return getPostParams(urlStr, httpMethod, callBack, null, null, null);
	}

	/**
	 * 获取请求参数
	 * 
	 * @param urlStr
	 *            请求URL
	 * @param httpMethod
	 *            请求方式
	 * @param oauthToken
	 *            oauthToken
	 * @param tokenSecret
	 *            oauthTokenSecret
	 * @param content
	 *            微博内容
	 * @param format
	 *            返回格式xml或json
	 * @param clientIP
	 *            客户端IP
	 * @param jing
	 *            精度
	 * @param wei
	 *            纬度
	 * @return
	 * @throws Exception
	 */
	public static String getPostParams(String urlStr, String httpMethod, String oauthToken, String tokenSecret, String content, String format, String clientIP, String jing, String wei) throws Exception
	{
		return getPostParams(urlStr, httpMethod, null, oauthToken, tokenSecret, null, content, format, clientIP, jing, wei);
	}

	/**
	 * 获取请求参数
	 * 
	 * @param urlStr
	 *            请求URL
	 * @param httpMethod
	 *            请求方式
	 * @param callBack
	 *            认证成功后浏览器会被重定向到这个url中，没有回调url，此时设置为字符串“null”即可
	 * @param oauthToken
	 *            oauthToken
	 * @param tokenSecret
	 *            oauthTokenSecret
	 * @param verifier
	 *            验证码
	 * @return
	 * @throws Exception
	 */
	public static String getPostParams(String urlStr, String httpMethod, String callBack, String oauthToken, String tokenSecret, String verifier) throws Exception
	{
		return getPostParams(urlStr, httpMethod, callBack, oauthToken, tokenSecret, verifier, null, null, null, null, null);
	}

	/**
	 * 获取请求参数
	 * 
	 * @param urlStr
	 *            请求URL
	 * @param httpMethod
	 *            请求方式
	 * @param callBack
	 *            认证成功后浏览器会被重定向到这个url中，没有回调url，此时设置为字符串“null”即可
	 * @param oauthToken
	 *            oauthToken
	 * @param tokenSecret
	 *            oauthTokenSecret
	 * @param verifier
	 *            验证码
	 * @param content
	 *            微博内容
	 * @param format
	 *            返回格式xml或json
	 * @param clientIP
	 *            客户端IP
	 * @param jing
	 *            精度
	 * @param wei
	 *            纬度
	 * @return
	 * @throws Exception
	 */
	public static String getPostParams(String urlStr, String httpMethod, String callBack, String oauthToken, String tokenSecret, String verifier, String content, String format, String clientIP, String jing, String wei) throws Exception
	{
		// 保存参数集合
		List<Parameter> params = new ArrayList<Parameter>();
		// 获取时间戳
		String timestamp = generateTimeStamp();
		// 获取单次值
		String nonce = generateNonce();
		// 添加参数
		params.add(new Parameter(OAUTH_CONSUMER_KEY, encode(CONSUMER_KEY)));
		params.add(new Parameter(OAUTH_SIGNATURE_METHOD, encode(SIGNATURE_METHOD)));
		params.add(new Parameter(OAUTH_TIMESTAMP, encode(timestamp)));
		params.add(new Parameter(OAUTH_NONCE, encode(nonce)));
		params.add(new Parameter(OAUTH_VERSION, encode(VERSION)));

		if (!TextUtil.isEmpty(callBack))
		{
			params.add(new Parameter(OAUTH_CALLBACK, encode(callBack)));
		}
		// 验证码
		if (!TextUtil.isEmpty(verifier))
		{
			params.add(new Parameter(OAUTH_VERIFIER, encode(verifier)));
		}
		// oauthToken
		if (!TextUtil.isEmpty(oauthToken))
		{
			params.add(new Parameter(OAUTH_TOKEN, encode(oauthToken)));
		}
		// 微博内容
		if (!TextUtil.isEmpty(content))
		{
			params.add(new Parameter(CONTENT, encode(content)));
		}
		// 返回格式
		if (!TextUtil.isEmpty(format))
		{
			params.add(new Parameter(FORMAT, encode(format)));
		}
		// 客户端IP
		if (!TextUtil.isEmpty(clientIP))
		{
			params.add(new Parameter(CLIENTIP, encode(clientIP)));
		}
		// 经度
		if (!TextUtil.isEmpty(jing))
		{
			params.add(new Parameter(JING, encode(oauthToken)));
		}
		// 纬度
		if (!TextUtil.isEmpty(jing))
		{
			params.add(new Parameter(WEI, encode(wei)));
		}

		// 获取签名值
		String signature = generateSignature(httpMethod, urlStr, params, CONSUMER_SECRET, tokenSecret);
		params.add(new Parameter(OAUTH_SIGNATURE, encode(signature)));

		// 构造请求参数字符串
		StringBuilder urlBuilder = new StringBuilder();
		for (Parameter param : params)
		{
			urlBuilder.append(param.getName());
			urlBuilder.append("=");
			urlBuilder.append(param.getValue());
			urlBuilder.append("&");
		}
		// 删除最后“&”字符
		urlBuilder.deleteCharAt(urlBuilder.length() - 1);
		Log.i(Constants.TAG, "paramsStr=" + urlBuilder.toString());
		return urlBuilder.toString();
	}

	/**
	 * 产生签名值
	 * 
	 * @param method
	 *            请求方法
	 * @param url
	 *            请求路径
	 * @param params
	 *            请求参数集合
	 * @param consumerSecret
	 *            AppSecret
	 * @param tokenSecret
	 *            tokenSecret
	 * @return
	 * @throws Exception
	 */
	private static String generateSignature(String method, String url, List<Parameter> params, String consumerSecret, String tokenSecret) throws Exception
	{
		// 获取源串
		String signatureBase = generateSignatureBase(method, url, params);
		// 构造密钥
		String oauthKey = "";
		if (null == tokenSecret || tokenSecret.equals(""))
		{
			oauthKey = encode(consumerSecret) + "&";
		} else
		{
			oauthKey = encode(consumerSecret) + "&" + encode(tokenSecret);
		}
		// 生成签名值
		byte[] encryptBytes = HMAC_SHA1.HmacSHA1Encrypt(signatureBase, oauthKey);
		return Base64.encode(encryptBytes);
	}

	/**
	 * 构造源串：HTTP请求方式 & urlencode(url) & urlencode(a=x&b=y&...)
	 * 
	 * @param method
	 *            请求方法
	 * @param url
	 *            请求路径
	 * @param params
	 *            请求参数
	 * @return
	 */
	private static String generateSignatureBase(String method, String url, List<Parameter> params)
	{
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(method.toUpperCase() + "&");
		url = encode(url.toLowerCase());
		urlBuilder.append(url + "&");
		// 所有参数按key进行字典升序排列
		Collections.sort(params);
		for (Parameter param : params)
		{
			String name = encode(param.getName());
			String value = encode(param.getValue());
			urlBuilder.append(name);
			urlBuilder.append("%3D"); // 字符 =
			urlBuilder.append(value);
			urlBuilder.append("%26"); // 字符 &
		}
		// 删除末尾的%26
		urlBuilder.delete(urlBuilder.length() - 3, urlBuilder.length());
		return urlBuilder.toString();
	}

	/**
	 * 对字符串进行编码
	 * 
	 * @param s
	 * @return
	 * @see <a
	 *      href="http://tools.ietf.org/html/draft-hammer-oauth-10#section-3.6">为什么编码后需要再次处理</a>
	 * @see <a
	 *      href="http://oauth.googlecode.com/svn/code/java/core/commons/src/main/java/net/oauth/OAuth.java">参考代码，进去后搜索RLEncoder.encode</a>
	 */
	public static String encode(String s)
	{
		if (s == null)
		{
			return "";
		}
		String encoded = "";
		try
		{
			encoded = URLEncoder.encode(s, ENCODING);
		} catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < encoded.length(); i++)
		{
			char c = encoded.charAt(i);
			if (c == '+')
			{
				sBuilder.append("%20");
			} else if (c == '*')
			{
				sBuilder.append("%2A");
			}
			// URLEncoder.encode()会把“~”使用“%7E”表示，因此在这里我们需要变成“~”
			else if ((c == '%') && ((i + 1) < encoded.length()) && ((i + 2) < encoded.length()) & (encoded.charAt(i + 1) == '7') && (encoded.charAt(i + 2) == 'E'))
			{
				sBuilder.append("~");
				i += 2;
			} else
			{
				sBuilder.append(c);
			}
		}
		return sBuilder.toString();
	}

	/**
	 * 产生时间戳
	 * 
	 * @return
	 */
	private static String generateTimeStamp()
	{
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	/**
	 * 产生单次值
	 * 
	 * @param is32
	 *            产生字符串长度是否为32位
	 * @return
	 */
	private static String generateNonce()
	{
		Random random = new Random();
		// 产生123400至9999999随机数
		String result = String.valueOf(random.nextInt(9876599) + 123400);
		// 进行MD5加密
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(result.getBytes());
			byte b[] = md.digest();
			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++)
			{
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return result;
	}
}
