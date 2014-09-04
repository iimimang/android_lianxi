package com.weibo.tencent.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.weibo.joechan.util.TextUtil;
import com.weibo.tencent.model.Parameter;
import com.weibo.tencent.oauth.OAuth;
import com.weibo.tencent.util.Constants;


/**
 * QQ
 *@author coolszy
 *@date 2011-6-11
 *@blog http://blog.csdn.net/coolszy
 */

public class Weibo
{
	private String urlStr = ""; //请求url
	private String httpMethod = "";//请求方式
	private String paramsStr = "";//请求参数
	private String response = null;//接受返回信息
	
	/**
	 * 获取获取未授权的Request Token
	 * @return oauth_token和oauth_token_secret键值对
	 */
	public Map<String, String> getRequestToken()
	{
		urlStr = "http://open.t.qq.com/cgi-bin/request_token";
		httpMethod = "GET";
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			//获取请求参数
			paramsStr = OAuth.getPostParams(urlStr,httpMethod,"null");
			SyncHttp http = new SyncHttp();
			//发送请求
			response = http.httpGet(urlStr, paramsStr);
			Log.i(Constants.TAG, "RequestToken："+response);
			//分割返回信息
			map = TextUtil.splitResponse(response);
		} catch (Exception e)
		{
//			Log.i(Constants.TAG, e.getMessage());
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取AccessToken
	 * @param oauthToken Request Token
	 * @param tokenSecret  Request Token Secret
	 * @param verifier    验证码
	 * @return
	 */
	public Map<String, String> getAccessToken(String oauthToken,String tokenSecret,String verifier)
	{
		urlStr="http://open.t.qq.com/cgi-bin/access_token";
		httpMethod = "GET";
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			//获取请求参数
			paramsStr = OAuth.getPostParams(urlStr, httpMethod,null, oauthToken, tokenSecret, verifier);
			//发送请求
			SyncHttp http = new SyncHttp();
			response = http.httpGet(urlStr, paramsStr);
			Log.i(Constants.TAG, "AccessToken："+response);
			//分割返回信息
			map = TextUtil.splitResponse(response);
		} catch (Exception e)
		{
			Log.i(Constants.TAG, e.getMessage());
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 发送普通微博
	 * @param oauthToken	Access Token
	 * @param tokenSecret	Access Token Secret
	 * @param format		返回格式 xml或json
	 * @param content		微博内容
	 * @param clientIP		客户端IP，没有请设置为127.0.0.1
	 * @return
	 */
	public String add(String oauthToken,String tokenSecret,String format,String content,String clientIP)
	{
		return add(oauthToken,tokenSecret,format, content, clientIP,"","");
	}
	
	/**
	 * 发送普通微博
	 * @param oauthToken	Access Token
	 * @param tokenSecret	Access Token Secret
	 * @param format		返回格式 xml或json
	 * @param content		微博内容
	 * @param clientIP		客户端IP，没有请设置为127.0.0.1
	 * @param jing			经度
	 * @param wei			纬度
	 * @return
	 */
	public String add(String oauthToken,String tokenSecret,String format,String content,String clientIP,String jing,String wei)
	{
		urlStr = "http://open.t.qq.com/api/t/add";
		httpMethod = "POST";
		
		try
		{
			//获取请求参数
			paramsStr = OAuth.getPostParams(urlStr, httpMethod,oauthToken,tokenSecret, content, format, clientIP, jing,wei);
			SyncHttp http = new SyncHttp();
			response = http.httpPost(urlStr, paramsStr);
		} catch (Exception e)
		{
			Log.i(Constants.TAG, e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 发送带图片的微博
	 * @param oauthToken	Access Token
	 * @param tokenSecret	Access Token Secret
	 * @param format		返回格式 xml或json
	 * @param content		微博内容
	 * @param clientIP		客户端IP，没有请设置为127.0.0.1
	 * @param picsPath		图片路径
	 * @return
	 */
	public String addWithPic(String oauthToken,String tokenSecret,String format,String content,String clientIP,String[] picsPath)
	{
		return addWithPic(oauthToken, tokenSecret, format, content, clientIP, "", "", picsPath);
	}
	
	/**
	 * 发送带图片的微博
	 * @param oauthToken	Access Token
	 * @param tokenSecret	Access Token Secret
	 * @param format		返回格式 xml或json
	 * @param content		微博内容
	 * @param clientIP		客户端IP，没有请设置为127.0.0.1
	 * @param jing			经度
	 * @param wei			纬度
	 * @param picsPath		图片路径
	 * @return
	 */
	public String addWithPic(String oauthToken,String tokenSecret,String format,String content,String clientIP,String jing,String wei,String[] picsPath)
	{
		String response = null;
		//判断图片集合是否存在值
		if (null!=picsPath&&picsPath.length>0)
		{
			List<Parameter> pics = new ArrayList<Parameter>();
			//判断图片是否存在
			for (String picPath : picsPath)
			{
				if (new File(picPath).exists())
				{
					//图片存在放到集合中
					pics.add(new Parameter("pic", picPath));
				}
			}
			//发送带图片的微博
			if (pics.size()>0)
			{
				urlStr = "http://open.t.qq.com/api/t/add_pic";
				httpMethod = "POST";
				try
				{
					//获取请求参数
					paramsStr = OAuth.getPostParams(urlStr, httpMethod,oauthToken,tokenSecret, content, format, clientIP, jing,wei);
					SyncHttp http = new SyncHttp();
					response=http.postWithFile(urlStr, paramsStr, pics);
				} catch (Exception e)
				{
					Log.i(Constants.TAG, e.getMessage());
					e.printStackTrace();
				}
			}
			else
			{
				response = add(oauthToken, tokenSecret, format, content, clientIP, jing, wei);
			}
		}
		else
		{
			response = add(oauthToken, tokenSecret, format, content, clientIP, jing, wei);
		}
		return response;
	}
}
