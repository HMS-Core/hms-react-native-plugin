/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.ads;

import androidx.annotation.Nullable;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import static com.huawei.hms.rn.ads.RNHMSAdsBannerViewManager.Event;

public class RNHMSAdsBannerView extends LinearLayout {
    private static final String TAG = RNHMSAdsBannerView.class.getSimpleName();
    private ReactContext mReactContext;
    private ReadableMap mAdParamReadableMap;
    private BannerView mBannerView;
    private BannerAdSize mBannerAdSize = ReactUtils.getBannerAdSizeFromReadableMap(null, null);
    private String mAdId = "testw6vs28auh3";

    public RNHMSAdsBannerView(final Context context) {
        super(context);
        if (context instanceof ReactContext) {
            mReactContext = (ReactContext) context;
        }
        setupBannerView();
    }

    private final Runnable measureAndLayout = () -> {
        measure(
                MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
        layout(getLeft(), getTop(), getRight(), getBottom());
    };

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    void setupBannerView() {
        mBannerView = new BannerView(mReactContext);
        Log.i(TAG, "BannerView object is created");
        mBannerView.setAdId(mAdId);
        mBannerView.setBannerAdSize(mBannerAdSize);
        mBannerView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                int top = mBannerView.getTop();
                int left = mBannerView.getLeft();
                int height = mBannerAdSize.getHeightPx(mReactContext);
                int width = mBannerAdSize.getWidthPx(mReactContext);
                mBannerView.measure(width, height);
                mBannerView.layout(left, top, left + width, top + height);
                WritableMap wm = new WritableNativeMap();
                wm.putString("adId", mBannerView.getAdId());
                wm.putBoolean("isLoading", mBannerView.isLoading());
                wm.putMap("bannerAdSize",
                        ReactUtils.getWritableMapFromBannerAdSizeWithContext(mBannerView.getBannerAdSize(),
                                mReactContext));
                sendEvent(Event.AD_LOADED, wm);
            }

            @Override
            public void onAdFailed(int errorCode) {
                WritableMap wm = new WritableNativeMap();
                wm.putInt("errorCode", errorCode);
                wm.putString("errorMessage", RNHMSAdsModule.getErrorMessage(errorCode));
                sendEvent(Event.AD_FAILED, wm);
            }

            @Override
            public void onAdOpened() {
                sendEvent(Event.AD_OPENED, null);
            }

            @Override
            public void onAdClicked() {
                sendEvent(Event.AD_CLICKED, null);
            }

            @Override
            public void onAdClosed() {
                sendEvent(Event.AD_CLOSED, null);
            }

            @Override
            public void onAdImpression() {
                sendEvent(Event.AD_IMPRESSION, null);
            }

            @Override
            public void onAdLeave() {
                sendEvent(Event.AD_LEAVE, null);
            }
        });
        Log.i(TAG, "AdListener object is created");
        Log.i(TAG, "setAdListener() is called");
        this.addView(mBannerView);
        this.requestLayout();
    }

    private void sendEvent(Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), event.getName(), wm);
    }

    public void setAdId(String adId) {
        mAdId = adId;
        Log.i(TAG, "adId is set");
    }

    public void setBannerAdSize(ReadableMap bannerAdSizeReadableMap) {
        mBannerAdSize = ReactUtils.getBannerAdSizeFromReadableMap(mReactContext, bannerAdSizeReadableMap);
        Log.i(TAG, "bannerAdSize is set");
    }

    void loadAd() {
        mBannerView.setAdId(mAdId);
        Log.i(TAG, "setAdId() is called ");

        mBannerView.setBannerAdSize(mBannerAdSize);
        Log.i(TAG, "setBannerAdSize() is called ");

        mBannerView.loadAd(ReactUtils.getAdParamFromReadableMap(mAdParamReadableMap));
        Log.i(TAG, "loadAd() is called ");
    }

    void setBannerRefresh(int refreshTime) {
        mBannerView.setBannerRefresh(refreshTime);
        Log.i(TAG, "setBannerRefresh() is called ");
    }

    void pause() {
        mBannerView.pause();
        Log.i(TAG, "pause() is called ");
    }

    void resume() {
        mBannerView.resume();
        Log.i(TAG, "resume() is called ");
    }

    void destroy() {
        mBannerView.destroy();
        Log.i(TAG, "destroy() is called ");
    }

    public void setAdParam(ReadableMap adParamReadableMap) {
        mAdParamReadableMap = adParamReadableMap;
        Log.i(TAG, "adParam is set");
    }
}
