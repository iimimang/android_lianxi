package cn.chinat2t.cloud.http;


public class CommunicationManager {
	private final String TAG = "CommunicationManager";
	private final int PAGESIZE = 10;
	public static final byte FAIL = 0;
	public static final byte SUCCEED = 1;
    
	private static CommunicationManager instance;
	private NetworkManager networkManager;
	private JsonParserManager jsonParserManager;

	public CommunicationManager() {
		if (networkManager == null)
			networkManager = NetworkManager.getInstance();

		if (jsonParserManager == null)
			jsonParserManager = JsonParserManager.getInstance();
	}

	public static CommunicationManager getInstance() {
		if (instance == null)
			instance = new CommunicationManager();

		return instance;
	}

	/**
	 * ���������ϸ����
	 * 
	 */
	/**
	 * ��ȡ������Ѷ
	 * @param listener
	 */
	public void getNewsDetail(String id, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_News_Detail+id;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserNewsDetailResult(str, listener);
			}
		});
	}
	/**
	 * ��ȡ������Ѷ
	 * @param listener
	 */
	public void getNews( final CommunicationResultListener listener) {

		if (listener == null) {
			return;
		}

		String url = ServerAddress.GET_NEWS;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
					@Override
					public void resultString(String str) {
						if (str == null || str.equals("")) {
							listener.resultListener(FAIL, "����ʧ�ܣ�������!");
							return;
						}
						jsonParserManager.parserNews(str, listener);
					}
				});
	}
	
	/**
	 * ��ȡ�õ�Ƭ
	 * @param listener
	 */
	public void getGalleryImage( final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_GALLERY;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserGalleryResult(str, listener);
			}
		});
	}
	
	/**
	 * ��ȡ��ҳ�����
	 * @param listener
	 */
	public void getShopSort( final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_SORT;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserSortResult(str, listener);
			}
		});
	}
	
	
	public void getLeastProduct( final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_LEAST_PRODUCT;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserProductResult1(str, listener);
			}
		});
	}
	/**
	 * @author wyq
	 * 2013.5.23 13:15
	 * ���������ҵ
	 */
public void getHotBusiness( final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_HotBusinessList;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserHotBusinessResult(str, listener);
			}
		});
	}
	
/**
 * @author wyq
 * 2013.5.23 13:15
 * ���������ҵ�±ߵ�����ͼƬ
 */
public void getShopLogo( final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_ShopLogo;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserShopLogoResult(str, listener);
		}
	});
}

	public void getTopNews( final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_TOP_NEWS;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserTopNewsResult(str, listener);
			}
		});
	}
	
	
	public void getHotNews(int page,String pagesize, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_HOT_NEWS+pagesize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserHotNewsResult(str, listener);
			}
		});
	}
	
	
	public void getSearchBusiness(int page,String pagesize, String input,final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_BUSINESS_LIST+input.trim()+"&pagesize="+pagesize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserBusinessResult(str, listener);
			}
		});
	}
	
	
	public void getSearchNews(int currentPage,String pageSize, String input,final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = (ServerAddress.GET_NEWS_SEARCH+input).trim()+"&pagesize="+pageSize+"&page="+currentPage;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserNewsSearchResult(str, listener);
			}
		});
	}
	
	
	public void getPartCat(final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_PART_CAT;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserSortResult(str, listener);
			}
		});
	}
	
	
	public void getAllCat(final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_ALL_CAT;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserSortResult(str, listener);
			}
		});
	}
	
	public void getSearchProduct(int page,String pageSize, String input,final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_Product_SearchLIST+input.trim()+"&pagesize="+pageSize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserProductResult(str, listener);
			}
		});
	}
	
	public void getProductSortList(int page,String pageSize,String pid, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_HotSortList+pid+"&pagesize="+pageSize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserProductResult(str, listener);
			}
		});
	}
	
	public void getProductList(int page,String pageSize, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_PRODUCT_LIST+pageSize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserProductResult(str, listener);
			}
		});
	}
	
	public void getProductDetail(String id, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_PRODUCT_DETAIL+id;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserProductDetailResult(str, listener);
			}
		});
	}
	
	
	public void getProductComments(String id, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_COMMENTS+id;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserCommentResult(str, listener);
			}
		});
	}
	
	/**
	 * @author ����ǿ
	 * @2013.5.20 14��41
	 * ��ҵ����ҳ���ȡ��ҵ�б���Ϣ
	 */
	public void getBusinessList(int page,String pagesize, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_BUSINESS_LIST+"&pagesize="+pagesize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserBusinessResult(str, listener);
			}
		});
	}
	
public void getBusinessDetail(String id, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_BUSINESS_DETAIL+id;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "����ʧ�ܣ�������!");
					return;
				}
				jsonParserManager.parserBusinessDetailResult(str, listener);
			}
		});
	}
	
/**
 * @author ����ǿ
 * @2013.5.21 14��44
 * �����б���Ϣ
 */
public void getBusinessOrderList(int page,String pageSize, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessOrder_List+"&pagesize="+pageSize+"&page="+page;;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBusinessOrderResult(str, listener);
		}
	});
}
/**
 * @author ����ǿ
 * @2013.5.21 15��24
 * ��������
 */
public void getSearchBusinessOrder(int page,String pageSize, String input,final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessOrder_List+input.trim()+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBusinessOrderResult(str, listener);
		}
	});
}
/**
 * 
 * @author ����ǿ
 * 2013.5.21 16:20
 * @param id
 * @param listener
 */
public void getBusinessOrderDetail(String id, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessOrderDetail+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBusinessOrderDetailResult(str, listener);
		}
	});
}

/**
 * @author ����ǿ
 * @2013.5.21 14��44
 * �����̻��б���Ϣ
 */
public void getBusinessSupplyList(int page,String pageSize, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessSupplyList+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBusinessSupplyResult(str, listener);
		}
	});
}
/**
 * @author ����ǿ
 * @2013.5.21 15��24
 * �����̻�����
 */
public void getSearchBusinessSupply(int page,String pageSize, String input,final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessSupplyList+input.trim()+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBusinessSupplyResult(str, listener);
		}
	});
}


/**
 * @author ����ǿ
 * @2013.5.21 14��44
 * ����۸��б���Ϣ
 */
public void getBusinessPriceList(int page,String pageSize, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessPriceList+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBusinessPriceResult(str, listener);
		}
	});
}
/**
 * @author ����ǿ
 * @2013.5.21 15��24
 * ����۸�����
 */
public void getSearchBusinessPrice(int page,String pageSize, String input,final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessPriceList+input.trim()+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBusinessPriceResult(str, listener);
		}
	});
}

/**
 * @author ����ǿ
 * @2013.5.21 14��44
 * ����۸��б���Ϣ
 */
public void getBusinessExhibitionList(int page,String pageSize, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessExhibitionList+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBusinessExhibitionResult(str, listener);
		}
	});
}
/**
 * @author ����ǿ
 * @2013.5.21 15��24
 * չ��۸�����
 */
public void getSearchBusinessExhibition(int page,String pageSize, String input,final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessExhibitionList+input.trim()+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBusinessExhibitionResult(str, listener);
		}
	});
}


/**
 * @author ����ǿ
 * @2013.5.20 14��41
 * Ʒ�ư�
 */
public void getBrandPicsList( final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BrandPicsList;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBrandPicsResult(str, listener);
		}
	});
}



/**
 * @author ����ǿ
 * @2013.5.20 14��41
 * Ʒ�ư�
 */
public void getBrandPicsList1(int page,String pageSize, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BrandPicsList+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBrandPicsResult(str, listener);
		}
	});
}

/**
 * ��ȡ������Ѷ
 * @param listener
 */
public void getPriceDetail(String id, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessPriceDetail+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserPriceDetailResult(str, listener);
		}
	});
}
/**
 * ��ȡչ��չ����ϸҳ
 * @param listener
 */
public void getExhibitionDetail(String id, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessExhibitionDetail+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserExhibitionDetailResult(str, listener);
		}
	});
}
/**
 * ��ȡ�����̻���ϸ����
 * @param listener
 */
public void getSupplyDetail(String id, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessSupplyDetail+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserSupplyDetailResult(str, listener);
		}
	});
}

/**
 * ��ȡƷ����ϸ����
 * @param listener
 */
public void getBrandPicDetail(String id, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BrandDetail+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserBrandPicDetailResult(str, listener);
		}
	});
}
/**
 * ��Ʒҳ��������
 * @param listener
 */
public void getProductSort(String id,final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_HotSortList+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "����ʧ�ܣ�������!");
				return;
			}
			jsonParserManager.parserProductResult(str, listener);
		}
	});
}
}
