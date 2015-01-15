package cn.chinat2t.cloud.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.chinat2t.cloud.bean.BrandPicsDetailBean;
import cn.chinat2t.cloud.bean.BrandPicsListBean;
import cn.chinat2t.cloud.bean.BusinessExhibitionBean;
import cn.chinat2t.cloud.bean.BusinessExhibitionListBean;
import cn.chinat2t.cloud.bean.BusinessListBean;
import cn.chinat2t.cloud.bean.BusinessOrderBean;
import cn.chinat2t.cloud.bean.BusinessOrderListBean;
import cn.chinat2t.cloud.bean.BusinessPriceBean;
import cn.chinat2t.cloud.bean.BusinessPriceListBean;
import cn.chinat2t.cloud.bean.BusinessSupplyBean;
import cn.chinat2t.cloud.bean.BusinessSupplyListBean;
import cn.chinat2t.cloud.bean.CommentBean;
import cn.chinat2t.cloud.bean.GalleryBean;
import cn.chinat2t.cloud.bean.HotBusinessBean;
import cn.chinat2t.cloud.bean.HotNewsBean;
import cn.chinat2t.cloud.bean.LeastProduct;
import cn.chinat2t.cloud.bean.NewsBean;
import cn.chinat2t.cloud.bean.NewsDetail;
import cn.chinat2t.cloud.bean.ProductBean;
import cn.chinat2t.cloud.bean.ShopLogo;
import cn.chinat2t.cloud.bean.SortBean;
import cn.chinat2t.cloud.bean.SubSortBean;
import cn.chinat2t.cloud.bean.TopNewsBean;
import cn.chinat2t.cloud.utils.CtLog;

public class JsonParserManager {

	private static JsonParserManager mParser;

	private JsonParserManager() {
	}

	public static JsonParserManager getInstance() {
		if (mParser == null) {
			mParser = new JsonParserManager();
		}
		return mParser;
	}

	/**
	 * 首页新闻资讯
	 * 
	 * @param result
	 * @param listener
	 */
	public void parserNews(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			JSONArray ja = new JSONArray(result);

			List<NewsBean> newsList = new ArrayList<NewsBean>();
			NewsBean news = null;
			for(int i = 0 ; i < ja.length() ; i++){
				news = new NewsBean();
				JSONObject jo = ja.getJSONObject(i);
				news.id = jo.getString("id");
				news.title = jo.getString("title");
				newsList.add(news);
			}
			

			if (listener != null) {
					listener.resultListener(CommunicationManager.SUCCEED,
							newsList);
			}

		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	
	public void parserGalleryResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<GalleryBean> resultList = new ArrayList<GalleryBean>();
			GalleryBean info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new GalleryBean();
				JSONObject jo = ja.getJSONObject(i);
				info.link = jo.getString("link");
				info.picurl = jo.getString("picurl");
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	
	public void parserSortResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<SortBean> resultList = new ArrayList<SortBean>();
			SortBean info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new SortBean();
				JSONObject jo = ja.getJSONObject(i);
				info.linkageid = jo.getString("linkageid");
				info.name = jo.getString("name");
				JSONArray jaa = jo.getJSONArray("sub");
				List<SubSortBean> sList = new ArrayList<SubSortBean>();
				SubSortBean sBean = null;
				for(int j = 0 ; j < jaa.length() ; j++){
					JSONObject jao = jaa.getJSONObject(j);
					sBean = new SubSortBean();
					sBean.linkageid = jao.getString("linkageid");
					sBean.name = jao.getString("name");
					sList.add(sBean);
				}
				info.subList = sList;
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @author 汪亚强
	 * 2013.5.20 14:44
	 * 解析企业内容信息
	 */
	
	public void parserBusinessDetailResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			
			BusinessListBean busiBean = null;
			busiBean = new BusinessListBean();
			JSONObject jo = new JSONObject(result);
			busiBean.setUserid(jo.getString("userid"));
			busiBean.setC_name(jo.getString("c_name"));
			busiBean.setCz(jo.getString("cz"));
			busiBean.setGsdz(jo.getString("gsdz"));
			busiBean.setGsjj(jo.getString("gsjj"));
			busiBean.setLogo(jo.getString("logo"));
			busiBean.setLxdh(jo.getString("lxdh"));
			busiBean.setLxr(jo.getString("lxr"));
			busiBean.setZyyw(jo.getString("zyyw"));
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,busiBean);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @author 汪亚强
	 * 2013.5.20 14:44
	 * 解析企业列表信息
	 */
	public void parserBusinessResult(String result,CommunicationResultListener listener) {
		try {
			//CtLog.d(result);
			android.util.Log.d("wyq", result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			if("0".equals(result))
			{
				listener.resultListener(CommunicationManager.SUCCEED,"0");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<BusinessListBean> resultList = new ArrayList<BusinessListBean>();
			BusinessListBean info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new BusinessListBean();
				JSONObject jo = ja.getJSONObject(i);
				info.setUserid(jo.getString("userid"));
				info.setC_name(jo.getString("c_name"));
				info.setGsdz(jo.getString("gsdz"));
				info.setLogo(jo.getString("logo"));
				info.setLxdh(jo.getString("lxdh"));
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	/**
	 * @author wyq
	 * 
	 * 解析最热企业
	 */
	public void parserHotBusinessResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			if(result.equals("0")){
				if (listener != null)
					listener.resultListener(CommunicationManager.FAIL, "无搜索数据！");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<HotBusinessBean> resultList = new ArrayList<HotBusinessBean>();
			HotBusinessBean info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new HotBusinessBean();
				JSONObject jo = ja.getJSONObject(i);
				info.setC_name(jo.getString("c_name"));
				info.setGsdz(jo.getString("gsdz"));
				info.setLogo(jo.getString("logo"));
				info.setLxdh(jo.getString("lxdh"));
				info.setUserid(jo.getString("userid"));
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	/**
	 * @author wyq
	 * 
	 * 解析最热企业下三张图片
	 */
	public void parserShopLogoResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			if(result.equals("0")){
				if (listener != null)
					listener.resultListener(CommunicationManager.FAIL, "无搜索数据！");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<ShopLogo> resultList = new ArrayList<ShopLogo>();
			ShopLogo info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new ShopLogo();
				JSONObject jo = ja.getJSONObject(i);
				info.setLinkageid(jo.getString("linkageid"));
				info.setLogo(jo.getString("logo"));
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	public void parserProductResult1(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			if(result.equals("0")){
				if (listener != null)
					listener.resultListener(CommunicationManager.FAIL, "无搜索数据！");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<LeastProduct> resultList = new ArrayList<LeastProduct>();
			LeastProduct info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new LeastProduct();
				JSONObject jo = ja.getJSONObject(i);
				info.id = jo.getString("id");
				info.title = jo.getString("title");
				info.description = jo.getString("description");
				info.thumb = jo.getString("thumb");
				//info.time=jo.getString("inputtime");
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	
	
	public void parserProductResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			if(result.equals("0")){
				if (listener != null)
					listener.resultListener(CommunicationManager.SUCCEED, "0");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<LeastProduct> resultList = new ArrayList<LeastProduct>();
			LeastProduct info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new LeastProduct();
				JSONObject jo = ja.getJSONObject(i);
				info.id = jo.getString("id");
				info.title = jo.getString("title");
				info.description = jo.getString("description");
				info.thumb = jo.getString("thumb");
				info.time=jo.getString("inputtime");
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 获取新闻详细页面
	 * @author wyq
	 * @param result
	 * @param listener
	 */
	public void parserNewsDetailResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			
			NewsDetail news = null;
			news = new NewsDetail();
			JSONObject jo = new JSONObject(result);
			news.setId(jo.getString("id"));
			news.setTitle( jo.getString("title"));
			news.setInputtime(jo.getString("inputtime"));
			news.setContent(jo.getString("content"));
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,news);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	public void parserTopNewsResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			TopNewsBean info = null;
			if(ja.length() > 0){
				info = new TopNewsBean();
				JSONObject jo = ja.getJSONObject(0);
				info.id = jo.getString("id");
				info.title = jo.getString("title");
				info.description = jo.getString("description");
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,info);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	
	public void parserHotNewsResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<HotNewsBean> newsList = new ArrayList<HotNewsBean>();
			HotNewsBean news = null;
			for(int i = 0 ; i < ja.length() ; i++){
				news = new HotNewsBean();
				JSONObject jo = ja.getJSONObject(i);
				news.id = jo.getString("id");
				news.title = jo.getString("title");
				news.description=jo.getString("description");
				news.inputtime=jo.getString("inputtime");
				newsList.add(news);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,newsList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	
	public void parserNewsSearchResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			
			if(result.equals("0")){
				if (listener != null)
					listener.resultListener(CommunicationManager.FAIL, "无搜索数据！");
				return;
			}
			
			JSONArray ja = new JSONArray(result);
			
			List<HotNewsBean> newsList = new ArrayList<HotNewsBean>();
			HotNewsBean news = null;
			for(int i = 0 ; i < ja.length() ; i++){
				news = new HotNewsBean();
				JSONObject jo = ja.getJSONObject(i);
				news.id = jo.getString("id");
				news.title = jo.getString("title");
				news.description = jo.getString("description");
				news.inputtime = jo.getString("inputtime");
				newsList.add(news);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,newsList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	public void parserProductDetailResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			
			ProductBean news = null;
			news = new ProductBean();
			JSONObject jo = new JSONObject(result);
			news.id = jo.getString("id");
			news.title = jo.getString("title");
			news.description = jo.getString("description");
			news.thumb = jo.getString("thumb");
			news.jg = jo.getString("jg");
			news.cd = jo.getString("cd");
			news.sj = jo.getString("sj");
			news.cpms = jo.getString("cpms");
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,news);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	
	public void parserCommentResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			
			JSONArray ja = new JSONArray(result);
			List<CommentBean> commentList = new ArrayList<CommentBean>();
			CommentBean comment = null;
			for(int i = 0 ; i < ja.length() ; i++){
				comment = new CommentBean();
				JSONObject jo = ja.getJSONObject(i);
				comment.username = jo.getString("username");
				comment.creat_at = jo.getString("creat_at");
				comment.content = jo.getString("content");
				commentList.add(comment);
			}
			
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,commentList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @author 汪亚强
	 * 2013.5.21 14:50
	 * 解析招商搜索列表信息
	 */
	public void parserBusinessOrderResult(String result,CommunicationResultListener listener) {
		try {
			//CtLog.d(result);
			//android.util.Log.d("wyq", result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			if(result.equals("0")){
				if (listener != null)
					listener.resultListener(CommunicationManager.FAIL, "无搜索数据！");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<BusinessOrderListBean> resultList = new ArrayList<BusinessOrderListBean>();
			BusinessOrderListBean info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new BusinessOrderListBean();
				JSONObject jo = ja.getJSONObject(i);
				info.setId(jo.getString("id"));
				info.setDescription(jo.getString("description"));
				info.setInputtime(jo.getString("inputtime"));
				info.setTitle(jo.getString("title"));
				info.setThumb(jo.getString("thumb"));
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @author 汪亚强
	 * 2013.5.20 16:23
	 * 解析招商详细页
	 */
	
	public void parserBusinessOrderDetailResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			
			BusinessOrderBean busiBean = null;
			busiBean = new BusinessOrderBean();
			JSONObject jo = new JSONObject(result);
			busiBean.setId(jo.getString("id"));
			busiBean.setBzj(jo.getString("bzj"));
			busiBean.setCpdw(jo.getString("cpdw"));
			busiBean.setDd(jo.getString("dd"));
			busiBean.setDmyq(jo.getString("dmyq"));
			busiBean.setFbsj(jo.getString("fbsj"));
			busiBean.setFzms(jo.getString("fzms"));
			busiBean.setHtqx(jo.getString("htqx"));
			busiBean.setJbtz(jo.getString("jbtz"));
			busiBean.setJmf(jo.getString("jmf"));
			busiBean.setJmszs(jo.getString("jmszs"));
			busiBean.setJyms(jo.getString("jyms"));
			busiBean.setPbfyd(jo.getString("pbfyd"));
			busiBean.setPb(jo.getString("pb"));
			busiBean.setQynf(jo.getString("qynf"));
			busiBean.setSjlxfs(jo.getString("sjlxfs"));
			busiBean.setThumb(jo.getString("thumb"));
			busiBean.setTitle(jo.getString("title"));
			busiBean.setTxjysj(jo.getString("txjysj"));
			busiBean.setTxqsyf(jo.getString("txqsyf"));
			busiBean.setXmqyyq(jo.getString("xmqyyq"));
			busiBean.setYjhbl(jo.getString("yjhbl"));
			busiBean.setYjhbzq(jo.getString("yjhbzq"));
			busiBean.setZsfw(jo.getString("zsfw"));
			busiBean.setZycp(jo.getString("zycp"));
			busiBean.setSjlxfs(jo.getString("sjlxfs"));
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,busiBean);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @author 汪亚强
	 * 2013.5.22 11:15
	 * 解析供求商机搜索搜索列表信息
	 */
	public void parserBusinessSupplyResult(String result,CommunicationResultListener listener) {
		try {
			//CtLog.d(result);
			//android.util.Log.d("wyq", result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			if(result.equals("0")){
				if (listener != null)
					listener.resultListener(CommunicationManager.FAIL, "无搜索数据！");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<BusinessSupplyListBean> resultList = new ArrayList<BusinessSupplyListBean>();
			BusinessSupplyListBean info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new BusinessSupplyListBean();
				JSONObject jo = ja.getJSONObject(i);
				info.setId(jo.getString("id"));
				info.setDescription(jo.getString("description"));
				info.setInputtime(jo.getString("inputtime"));
				info.setTitle(jo.getString("title"));
				info.setThumb(jo.getString("thumb"));
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @author 汪亚强
	 * 2013.5.22 11:15
	 * 解析行情价格搜索列表信息
	 */
	public void parserBusinessPriceResult(String result,CommunicationResultListener listener) {
		try {
			//CtLog.d(result);
			//android.util.Log.d("wyq", result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			if(result.equals("0")){
				if (listener != null)
					listener.resultListener(CommunicationManager.FAIL, "无搜索数据！");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<BusinessPriceListBean> resultList = new ArrayList<BusinessPriceListBean>();
			BusinessPriceListBean info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new BusinessPriceListBean();
				JSONObject jo = ja.getJSONObject(i);
				info.setId(jo.getString("id"));
				info.setDescription(jo.getString("description"));
				info.setInputtime(jo.getString("inputtime"));
				info.setTitle(jo.getString("title"));
				info.setThumb(jo.getString("thumb"));
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @author 汪亚强
	 * 2013.5.22 11:15
	 * 解析行情价格搜索列表信息
	 */
	public void parserBusinessExhibitionResult(String result,CommunicationResultListener listener) {
		try {
			//CtLog.d(result);
			//android.util.Log.d("wyq", result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			if(result.equals("0")){
				if (listener != null)
					listener.resultListener(CommunicationManager.FAIL, "无搜索数据！");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<BusinessExhibitionListBean> resultList = new ArrayList<BusinessExhibitionListBean>();
			BusinessExhibitionListBean info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new BusinessExhibitionListBean();
				JSONObject jo = ja.getJSONObject(i);
				info.setId(jo.getString("id"));
				info.setDescription(jo.getString("description"));
				info.setInputtime(jo.getString("inputtime"));
				info.setTitle(jo.getString("title"));
				info.setThumb(jo.getString("thumb"));
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @author 汪亚强
	 * 2013.5.20 14:44
	 * 解析企业列表信息
	 */
	public void parserBrandPicsResult(String result,CommunicationResultListener listener) {
		try {
			//CtLog.d(result);
			//android.util.Log.d("wyq", result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			if("0".equals(result))
			{
				listener.resultListener(CommunicationManager.SUCCEED,"0");
				return;
			}
			JSONArray ja = new JSONArray(result);
			
			List<BrandPicsListBean> resultList = new ArrayList<BrandPicsListBean>();
			BrandPicsListBean info = null;
			for(int i = 0 ; i < ja.length() ; i++){
				info = new BrandPicsListBean();
				JSONObject jo = ja.getJSONObject(i);
				info.setLinkageid(jo.getString("linkageid"));
				info.setLogo(jo.getString("logo"));
				info.setName(jo.getString("name"));
				resultList.add(info);
			}
			
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,resultList);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 解析行情价格详细页面
	 * @author wyq
	 * @param result
	 * @param listener
	 */
	public void parserPriceDetailResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			
			BusinessPriceBean news = null;
			news = new BusinessPriceBean();
			JSONObject jo = new JSONObject(result);
			news.setId(jo.getString("id"));
			news.setTitle( jo.getString("title"));
			news.setInputtime(jo.getString("inputtime"));
			news.setContent(jo.getString("content"));
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,news);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 解析展览展会详细页面
	 * @author wyq
	 * @param result
	 * @param listener
	 */
	public void parserExhibitionDetailResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			
			BusinessExhibitionBean bean = null;
			bean = new BusinessExhibitionBean();
			JSONObject jo = new JSONObject(result);
			bean.setCbdw(jo.getString("cbdw"));
			bean.setHylb(jo.getString("hylb"));
			bean.setId(jo.getString("id"));
			bean.setInputtime(jo.getString("inputtime"));
			bean.setJsrq(jo.getString("jsrq"));
			bean.setKsrq(jo.getString("ksrq"));
			bean.setLxcz(jo.getString("lxcz"));
			bean.setLxdh(jo.getString("lxdh"));
			bean.setLxdz(jo.getString("lxdz"));
			bean.setLxr(jo.getString("lxr"));
			bean.setSfcs(jo.getString("sfcs"));
			bean.setThumb(jo.getString("thumb"));
			bean.setTitle(jo.getString("title"));
			bean.setYx(jo.getString("yx"));
			bean.setZbdw(jo.getString("zbdw"));
			bean.setZgmc(jo.getString("zgmc"));
			bean.setZhdz(jo.getString("zhdz"));
			bean.setZhfw(jo.getString("zhfw"));
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,bean);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 解析供求商机详细页面
	 * @author wyq
	 * @param result
	 * @param listener
	 */
	public void parserSupplyDetailResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			
			BusinessSupplyBean bean = null;
			bean = new BusinessSupplyBean();
			JSONObject jo = new JSONObject(result);
			bean.setId(jo.getString("id"));
			bean.setTitle( jo.getString("title"));
			bean.setCpsl(jo.getString("cpsl"));
			bean.setDq(jo.getString("dq"));
			bean.setEmail(jo.getString("email"));
			bean.setGgxh(jo.getString("ggxh"));
			bean.setLb(jo.getString("lb"));
			bean.setLxdh(jo.getString("lxdh"));
			bean.setLxr(jo.getString("lxr"));
			bean.setYxq(jo.getString("yxq"));
			bean.setInputtime(jo.getString("inputtime"));
			
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,bean);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 解析品牌详细页面
	 * @author wyq
	 * @param result
	 * @param listener
	 */
	public void parserBrandPicDetailResult(String result,CommunicationResultListener listener) {
		try {
			CtLog.d(result);
			if(result == null ) {
				listener.resultListener(CommunicationManager.FAIL, "获取数据失败！");
				return;
			}
			
			BrandPicsDetailBean bean = null;
			bean = new BrandPicsDetailBean();
			JSONObject jo = new JSONObject(result);
			bean.setLinkageid(jo.getString("linkageid"));
			bean.setLogo(jo.getString("logo"));
			bean.setName(jo.getString("name"));
			bean.setPpjs(jo.getString("ppjs"));
			bean.setPpxx(jo.getString("ppxx"));
			if (listener != null) {
				listener.resultListener(CommunicationManager.SUCCEED,bean);
			}
			
		} catch (JSONException e) {
			if (listener != null)
				listener.resultListener(CommunicationManager.FAIL, "数据解析错误！");
			e.printStackTrace();
		}
	}
}
