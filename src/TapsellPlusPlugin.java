package miladesign.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import ir.tapsell.plus.AdRequestCallback;
import ir.tapsell.plus.AdShowListener;
import ir.tapsell.plus.TapsellPlus;
import ir.tapsell.plus.TapsellPlusBannerType;
import ir.tapsell.plus.TapsellPlusInitListener;
import ir.tapsell.plus.model.AdNetworkError;
import ir.tapsell.plus.model.AdNetworks;
import ir.tapsell.plus.model.TapsellPlusAdModel;
import ir.tapsell.plus.model.TapsellPlusErrorModel;

public class TapsellPlusPlugin extends CordovaPlugin {
	private static final String LOG_TAG = "TapsellPlusPlugin";
	private static Activity mActivity = null;
	public CordovaInterface cordova = null;
	private FrameLayout bannerLayout;
	private String standardBannerResponseId = null;
	
	public static final int TOP_LEFT = 0;
	public static final int TOP_CENTER = 1;
	public static final int TOP_RIGHT = 2;
	public static final int LEFT = 3;
	public static final int CENTER = 4;
	public static final int RIGHT = 5;
	public static final int BOTTOM_LEFT = 6;
	public static final int BOTTOM_CENTER = 7;
	public static final int BOTTOM_RIGHT = 8;
	
	@Override
	public void initialize(CordovaInterface initCordova, CordovaWebView webView) {
		 Log.e(LOG_TAG, "initialize");
		 cordova = initCordova;
		 mActivity = cordova.getActivity();
		 super.initialize(cordova, webView);
	}
	
	
	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext CallbackContext) throws JSONException {
		if (action.equals("initialize")) {
			String appKey = args.getString(0);
			init(appKey);
			return true;
		}
		if (action.equals("createBanner")) {
			String zoneId = args.getString(0);
			int position = args.getInt(1);
			int size = args.getInt(2);
			createBanner(zoneId, position, size);
			return true;
		}
		if (action.equals("createBannerAtXY")) {
			String zoneId = args.getString(0);
			int x = args.getInt(1);
			int y = args.getInt(2);
			int size = args.getInt(3);
			createBannerAtXY(zoneId, x, y, size);
			return true;
		}
		if (action.equals("removeBanner")) {
			removeBanner();
			return true;
		}
		if (action.equals("showBanner")) {
			showBanner();
			return true;
		}
		if (action.equals("hideBanner")) {
			hideBanner();
			return true;
		}
		if (action.equals("requestRewardedVideo")) {
			String zoneId = args.getString(0);
			requestRewardedVideo(zoneId);
		    return true;
		}
		if (action.equals("requestInterstitial")) {
			String zoneId = args.getString(0);
			requestInterstitial(zoneId);
		    return true;
		}
		if (action.equals("showInterstitial")) {
			String responseId = args.getString(0);
			showInterstitial(responseId);
		    return true;
		}
		if (action.equals("showRewardedVideo")) {
			String responseId = args.getString(0);
			showRewardedVideo(responseId);
		    return true;
		}
	    return false;
	}
	
	private void init(String appKey) {
		TapsellPlus.initialize(mActivity, appKey, new TapsellPlusInitListener(){

			@Override
			public void onInitializeSuccess(AdNetworks adNetworks) {
			    fireEvent("tapsellplus", "onInitializeSuccess", null);
			    TapsellPlus.setGDPRConsent(mActivity, true);
			}

			@Override
			public void onInitializeFailed(AdNetworks adNetworks, AdNetworkError adNetworkError) {
				fireEvent("tapsellplus", "onInitializeFailed", null);
			}
		});
	}
	

	private TapsellPlusBannerType getBannerSize(int size) {
        switch (size) {
        	case 1: return TapsellPlusBannerType.BANNER_320x50;
        	case 2: return TapsellPlusBannerType.BANNER_320x100;
        	case 3: return TapsellPlusBannerType.BANNER_250x250;
        	case 4: return TapsellPlusBannerType.BANNER_300x250;
        	case 5: return TapsellPlusBannerType.BANNER_468x60;
        	case 6: return TapsellPlusBannerType.BANNER_728x90;
        	case 7: return TapsellPlusBannerType.BANNER_160x600;
        	default: return TapsellPlusBannerType.BANNER_320x50;
        }
    }
	
	private void createBanner(final String zoneId, final int position, final int size) {
		final TapsellPlusBannerType adSize = getBannerSize(size);
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (bannerLayout != null) {
					_removeBanner();
				}
				bannerLayout = new FrameLayout(mActivity);
				FrameLayout.LayoutParams fLayoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				if (position == TOP_LEFT) {
					fLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
					bannerLayout.setLayoutParams(fLayoutParams);
				    ((ViewGroup) getParentGroup().getParent()).addView(bannerLayout, 1);
					 Log.e(LOG_TAG, "createBanner 2");
				} else if (position == TOP_CENTER) {
					fLayoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
					bannerLayout.setLayoutParams(fLayoutParams);
				    ((ViewGroup) getParentGroup().getParent()).addView(bannerLayout, 1);
				} else if (position == TOP_RIGHT) {
					fLayoutParams.gravity = Gravity.TOP | Gravity.RIGHT;
					bannerLayout.setLayoutParams(fLayoutParams);
				    ((ViewGroup) getParentGroup().getParent()).addView(bannerLayout, 1);
				} else if (position == LEFT) {
					fLayoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
					bannerLayout.setLayoutParams(fLayoutParams);
				    ((ViewGroup) getParentGroup().getParent()).addView(bannerLayout, 1);
				} else if (position == CENTER) {
					fLayoutParams.gravity = Gravity.CENTER;
			    	bannerLayout.setLayoutParams(fLayoutParams);
				    ((ViewGroup) getParentGroup().getParent()).addView(bannerLayout, 1);
				} else if (position == RIGHT) {
					fLayoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
					bannerLayout.setLayoutParams(fLayoutParams);
				    ((ViewGroup) getParentGroup().getParent()).addView(bannerLayout, 1);
				} else if (position == BOTTOM_LEFT) {
					fLayoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
					bannerLayout.setLayoutParams(fLayoutParams);
				    ((ViewGroup) getParentGroup().getParent()).addView(bannerLayout, 1);
				} else if (position == BOTTOM_CENTER) {
					fLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
					bannerLayout.setLayoutParams(fLayoutParams);
				    ((ViewGroup) getParentGroup().getParent()).addView(bannerLayout, 1);
				} else if (position == BOTTOM_RIGHT) {
					fLayoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
					bannerLayout.setLayoutParams(fLayoutParams);
				    ((ViewGroup) getParentGroup().getParent()).addView(bannerLayout, 1);
				}
				
				TapsellPlus.requestStandardBannerAd(
						mActivity, zoneId,
						adSize,
		                new AdRequestCallback() {
		                    @Override
		                    public void response(TapsellPlusAdModel tapsellPlusAdModel) {
		                        super.response(tapsellPlusAdModel);
		                        standardBannerResponseId = tapsellPlusAdModel.getResponseId();
		                        TapsellPlus.showStandardBannerAd(mActivity, standardBannerResponseId,
		                        		bannerLayout,
		                        		BannerListener);
		                    }

		                    @Override
		                    public void error(String message) {
		                    	
		                    }
		                });
			}
		});
	}
	
	private void createBannerAtXY(final String zoneId, final int x, final int y, final int size) {
		final TapsellPlusBannerType adSize = getBannerSize(size);
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (bannerLayout != null) {
					_removeBanner();
				}
				bannerLayout = new FrameLayout(mActivity);
			    FrameLayout.LayoutParams fLayoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			    fLayoutParams.leftMargin = x;
		    	fLayoutParams.topMargin = y;
			    bannerLayout.setLayoutParams(fLayoutParams);
			    ((ViewGroup) getParentGroup().getParent()).addView(bannerLayout, 1);

			    TapsellPlus.requestStandardBannerAd(
						mActivity, zoneId,
						adSize,
		                new AdRequestCallback() {
		                    @Override
		                    public void response(TapsellPlusAdModel tapsellPlusAdModel) {
		                        super.response(tapsellPlusAdModel);
		                        standardBannerResponseId = tapsellPlusAdModel.getResponseId();
		                        TapsellPlus.showStandardBannerAd(mActivity, standardBannerResponseId,
		                        		bannerLayout,
		                        		BannerListener);
		                    }

		                    @Override
		                    public void error(String message) {
		                    	
		                    }
		                });
			}
		});
	}

	private void removeBanner() {
		if (bannerLayout == null)
		      return;
	    if (mActivity != null) {
	    	mActivity.runOnUiThread(new Runnable() {
	    		public void run() {
	    			if (standardBannerResponseId != null) {
	    				TapsellPlus.destroyStandardBanner(mActivity, standardBannerResponseId, bannerLayout);
	    				standardBannerResponseId = null;
	    			}
		        	ViewGroup viewGroup;
		      		if (((viewGroup = getParentGroup()) != null) && ((viewGroup instanceof ViewGroup)) && (((ViewGroup)viewGroup.getParent()).getChildAt(1) != null))
		      			((ViewGroup)viewGroup.getParent()).removeViewAt(1);
		        }
	    	});
	    }
	}
	
	private void _removeBanner() {
		if (bannerLayout == null)
		      return;
	    if (mActivity != null) {
	    	mActivity.runOnUiThread(new Runnable() {
		        public void run() {
		        	if (standardBannerResponseId != null) {
	    				TapsellPlus.destroyStandardBanner(mActivity, standardBannerResponseId, bannerLayout);
	    				standardBannerResponseId = null;
	    			}
		        	ViewGroup viewGroup;
	        		if (((viewGroup = getParentGroup()) != null) && ((viewGroup instanceof ViewGroup)) && (((ViewGroup)viewGroup.getParent()).getChildAt(1) != null))
	        			((ViewGroup)viewGroup.getParent()).removeViewAt(1);
		        }
	    	});
	    }
	}

	private void showBanner() {
		try {
			if (mActivity != null) {
		    	mActivity.runOnUiThread(new Runnable() {
			        public void run() {
						bannerLayout.setVisibility(View.VISIBLE);
			        }
		    	});
		    }
		} catch(Exception e) {
			Log.e(LOG_TAG, e.getMessage());
		}
	}

	private void hideBanner() {
		try {
			if (mActivity != null) {
		    	mActivity.runOnUiThread(new Runnable() {
			        public void run() {
						bannerLayout.setVisibility(View.INVISIBLE);
			        }
		    	});
		    }
		} catch(Exception e) {
			Log.e(LOG_TAG, e.getMessage());
		}
	}
	
	private ViewGroup getParentGroup() {
	    try {
	      return (ViewGroup)this.webView.getClass().getMethod("getView", new Class[0]).invoke(this.webView, new Object[0]);
	    } catch (Exception ex) {
	    	try {
	    		return (ViewGroup)this.webView.getClass().getMethod("getParent", new Class[0]).invoke(this.webView, new Object[0]);
	    	} catch (Exception e) {
	    		e.printStackTrace(); 
	        }
	    }
	    return null;
	}
	
	private void requestRewardedVideo(String zoneId) throws JSONException {
		if(zoneId!=null && (zoneId.equalsIgnoreCase("null") || zoneId.equalsIgnoreCase(""))) {
			zoneId = null;
		}
		
		TapsellPlus.requestRewardedVideoAd(mActivity, zoneId, new AdRequestCallback() {
	        @Override
	        public void response(TapsellPlusAdModel tapsellPlusAdModel) {
	        	super.response(tapsellPlusAdModel);
	        	String json = String.format("{'adType':'%s', 'responseId':'%s'}", new Object[] { "rewardedVideo", tapsellPlusAdModel.getResponseId() });
			    fireEvent("tapsellplus", "response", json);
	        }

	        @Override
	        public void error(String message) {
	        	String json = String.format("{'adType':'%s', 'message':'%s'}", new Object[] { "rewardedVideo", message });
			    fireEvent("tapsellplus", "error", json);
	        }

	    });
	}
	
	private void requestInterstitial(String zoneId) throws JSONException {
		if(zoneId!=null && (zoneId.equalsIgnoreCase("null") || zoneId.equalsIgnoreCase(""))) {
			zoneId = null;
		}
		
		TapsellPlus.requestInterstitialAd(mActivity, zoneId, new AdRequestCallback() {
	        @Override
	        public void response(TapsellPlusAdModel tapsellPlusAdModel) {
	        	String json = String.format("{'adType':'%s', 'responseId':'%s'}", new Object[] { "interstitial", tapsellPlusAdModel.getResponseId() });
			    fireEvent("tapsellplus", "response", json);
	        }

	        @Override
	        public void error(String message) {
	        	String json = String.format("{'adType':'%s', 'message':'%s'}", new Object[] { "interstitial", message });
			    fireEvent("tapsellplus", "error", json);
	        }

	    });
	}
	
	private void showInterstitial(String responseId) {
		TapsellPlus.showInterstitialAd(mActivity, responseId, new AdShowListener() {
	        @Override
	        public void onOpened(TapsellPlusAdModel tapsellPlusAdModel) {
	        	super.onOpened(tapsellPlusAdModel);
	        	String json = String.format("{'adType':'%s'}", new Object[] { "interstitial" });
			    fireEvent("tapsellplus", "onOpened", json);
	        }

	        @Override
	        public void onClosed(TapsellPlusAdModel tapsellPlusAdModel) {
	        	super.onClosed(tapsellPlusAdModel);
	        	String json = String.format("{'adType':'%s'}", new Object[] { "interstitial" });
			    fireEvent("tapsellplus", "onClosed", json);
	        }

	        @Override
	        public void onRewarded(TapsellPlusAdModel tapsellPlusAdModel) {
	        	super.onRewarded(tapsellPlusAdModel);
	        	String json = String.format("{'adType':'%s'}", new Object[] { "interstitial" });
			    fireEvent("tapsellplus", "onRewarded", json);
	        }

	        @Override
	        public void onError(TapsellPlusErrorModel tapsellPlusErrorModel) {
	        	super.onError(tapsellPlusErrorModel);
	        	String json = String.format("{'adType':'%s', 'message':'%s'}", new Object[] { "interstitial", tapsellPlusErrorModel.getErrorMessage() });
			    fireEvent("tapsellplus", "onError", json);
	        }
	    });
	}
	
	private void showRewardedVideo(String responseId) {
		TapsellPlus.showRewardedVideoAd(mActivity, responseId, new AdShowListener() {
	        @Override
	        public void onOpened(TapsellPlusAdModel tapsellPlusAdModel) {
	        	super.onOpened(tapsellPlusAdModel);
	        	String json = String.format("{'adType':'%s'}", new Object[] { "rewardedVideo" });
			    fireEvent("tapsellplus", "onOpened", json);
	        }

	        @Override
	        public void onClosed(TapsellPlusAdModel tapsellPlusAdModel) {
	        	super.onClosed(tapsellPlusAdModel);
	        	String json = String.format("{'adType':'%s'}", new Object[] { "rewardedVideo" });
			    fireEvent("tapsellplus", "onClosed", json);
	        }

	        @Override
	        public void onRewarded(TapsellPlusAdModel tapsellPlusAdModel) {
	        	super.onRewarded(tapsellPlusAdModel);
	        	String json = String.format("{'adType':'%s'}", new Object[] { "rewardedVideo" });
			    fireEvent("tapsellplus", "onRewarded", json);
	        }

	        @Override
	        public void onError(TapsellPlusErrorModel tapsellPlusErrorModel) {
	        	super.onError(tapsellPlusErrorModel);
	        	String json = String.format("{'adType':'%s', 'message':'%s'}", new Object[] { "rewardedVideo", tapsellPlusErrorModel.getErrorMessage() });
			    fireEvent("tapsellplus", "onError", json);
	        }
	    });
	}
	
	private AdShowListener BannerListener = new AdShowListener(){
		@Override
        public void onOpened(TapsellPlusAdModel tapsellPlusAdModel) {
            super.onOpened(tapsellPlusAdModel);
            String json = String.format("{'adType':'%s'}", new Object[] { "banner" });
		    fireEvent("tapsellplus", "onOpened", json);
        }

        @Override
        public void onError(TapsellPlusErrorModel tapsellPlusErrorModel) {
            super.onError(tapsellPlusErrorModel);
            String json = String.format("{'adType':'%s', 'message':'%s'}", new Object[] { "banner", tapsellPlusErrorModel.getErrorMessage() });
		    fireEvent("tapsellplus", "onError", json);
        }
	};
	
	public void fireEvent(String obj, String eventName, String jsonData) {
			String js;
			if("window".equals(obj)) {
				js = "var evt=document.createEvent('UIEvents');evt.initUIEvent('" + eventName + "',true,false,window,0);window.dispatchEvent(evt);";
			} else {
				js = "javascript:cordova.fireDocumentEvent('" + eventName + "'";
				if(jsonData != null) {
					js += "," + jsonData;
				}
				js += ");";
			}
			webView.loadUrl(js);
	}
}