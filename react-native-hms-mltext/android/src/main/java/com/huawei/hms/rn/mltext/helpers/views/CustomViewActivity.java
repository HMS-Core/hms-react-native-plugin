/*
 * Copyright 2023. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.hms.rn.mltext.helpers.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.mlplugin.card.bcr.CustomView;
import com.huawei.hms.rn.mltext.R;
import com.huawei.hms.rn.mltext.helpers.utils.HMSLogger;
import com.huawei.hms.rn.mltext.textrelatedservices.CustomViewHandler;

import java.lang.reflect.InvocationTargetException;

public class CustomViewActivity extends ReactActivity {
    private static final String TAG = CustomViewActivity.class.getSimpleName();
    private static final int TOP_OFFSET = 150;
    private ReactApplicationContext mContext;
    private CustomView remoteView;
    private ImageView flashButton;
    private FrameLayout cameraLayout;
    private FrameLayout cardLayout;
    private Rect mScanRect;
    private TextView title;
    private ViewfinderView viewfinderView;
    private CustomView.OnBcrResultCallback callback;
    private HMSLogger mHMSLogger;
    private double scanFrameWidthFactor = 0.8;
    private double scanFrameHeightFactor = 0.63084;
    private Intent intent;
    private Bundle bundle;

    private int[] img = {R.drawable.flash_light_on, R.drawable.flash_light_off};

    public enum Event {
        ON_START("onStart"),
        ON_RESUME("onResume"),
        ON_PAUSE("onPause"),
        ON_DESTROY("onDestroy"),
        ON_STOP("onStop");

        private String eventName;

        Event(String eventName) {
            this.eventName = eventName;
        }

        public String getName() {
            return eventName;
        }
    }

    void sendEvent(Event event, @Nullable WritableMap wm) {
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event.getName(), wm);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (ReactApplicationContext) getReactNativeHost().getReactInstanceManager().getCurrentReactContext();
        mHMSLogger = HMSLogger.getInstance(mContext);

        intent = getIntent();
        try {
            bundle = intent.getExtras();
        } catch (Exception e) {
            Log.i("Customized-Exception", e.getMessage());
        }

        initView();

        createScanRectFromCamera();

        callback = idCardResult -> {
            if (idCardResult.getErrorCode() == 0 ){
                mHMSLogger.sendSingleEvent("CustomViewActivity.OnBcrResultCallback");
                CustomViewHandler.setCardResult(idCardResult);
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                mHMSLogger.sendSingleEvent("CustomViewActivity.OnBcrResultCallback");
                CustomViewHandler.setCardResult(idCardResult);
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        };

        remoteView = new CustomView.Builder()
                .setContext(this)
                .setBoundingBox(mScanRect)
                .setResultType(bundle.getInt("resultType"))
                .setRecMode(bundle.getInt("recMode"))
                .setOnBcrResultCallback(callback).build();


        CustomViewHandler.setViews(remoteView, flashButton);

        mHMSLogger.startMethodExecutionTimer("CustomViewActivity.customizedView");

        remoteView.onCreate(savedInstanceState);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        cameraLayout.addView(remoteView, params);

        addMainView(mScanRect);

    }

    private void initView() {

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom);
        cameraLayout = findViewById(R.id.rim);
        flashButton = findViewById(R.id.imageButton2);
        cardLayout = findViewById(R.id.card_view);
        title = findViewById(R.id.title);

        if (bundle.getBoolean("isTitleAvailable")) {
            title.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.GONE);
        }

        title.setText(bundle.getString("title"));

        if (bundle.getBoolean("isFlashAvailable")) {
            flashButton.setVisibility(View.VISIBLE);
            setFlashOperation();
        } else {
            flashButton.setVisibility(View.GONE);
        }
        setBackOperation();
        setRectFactors();
    }

    private void setRectFactors() {
        if (bundle.containsKey("widthFactor")) {
            scanFrameWidthFactor = bundle.getDouble("widthFactor");
        }
        if (bundle.containsKey("heightFactor")) {
            scanFrameHeightFactor = bundle.getDouble("heightFactor");
        }
    }

    private void setBackOperation() {
        ImageView backButton = findViewById(R.id.back_img);
        backButton.setOnClickListener(v -> CustomViewActivity.this.finish());
    }
    /**
     * Get real screen size information
     *
     * @return Point
     */
    private Point getRealScreenSize() {
        int heightPixels = 0;
        int widthPixels = 0;
        Point point = null;
        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

        if (manager != null) {
            Display d = manager.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            d.getMetrics(metrics);
            heightPixels = metrics.heightPixels;
            widthPixels = metrics.widthPixels;
            Log.i(TAG, "heightPixels=" + heightPixels + " widthPixels=" + widthPixels);

            if (Build.VERSION.SDK_INT < 17) {
                try {
                    heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
                    widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                    Log.i(TAG, "2 heightPixels=" + heightPixels + " widthPixels=" + widthPixels);
                } catch (IllegalArgumentException | IllegalAccessException |
                         InvocationTargetException | NoSuchMethodException e) {
                    Log.w(TAG, "getRealScreenSize exception");
                }
            } else if (Build.VERSION.SDK_INT >= 17) {
                Point realSize = new Point();
                try {
                    Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                    heightPixels = realSize.y;
                    widthPixels = realSize.x;
                    Log.i(TAG, "3 heightPixels=" + heightPixels + " widthPixels=" + widthPixels);
                } catch (IllegalArgumentException | IllegalAccessException |
                         InvocationTargetException | NoSuchMethodException e) {
                    Log.w(TAG, "getRealScreenSize exception");
                }
            }
        }
        Log.i(TAG, "getRealScreenSize widthPixels=" + widthPixels + " heightPixels=" + heightPixels);
        point = new Point(widthPixels, heightPixels);
        return point;
    }
    private void createScanRectFromCamera() {
        Point point = getRealScreenSize();
        int screenWidth = point.x;
        int screenHeight = point.y;
        final float heightFactor = (float) scanFrameHeightFactor;
        final float widthFactor = (float) scanFrameWidthFactor;
        int width = Math.round(screenWidth * heightFactor);
        int height = Math.round((float) width * widthFactor);
        int leftOffset = (screenWidth - width) / 2;
        int topOffset = TOP_OFFSET;
        Log.i(TAG, "screenWidth=" + screenWidth + " screenHeight=" + screenHeight + "  rect width=" + width
                + " leftOffset " + leftOffset + " topOffset " + topOffset);
        mScanRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
    }


    private void setFlashOperation() {
        flashButton.setOnClickListener(v -> {
            if (remoteView.getLightStatus()) {
                remoteView.switchLight();
                flashButton.setImageResource(img[1]);
            } else {
                remoteView.switchLight();
                flashButton.setImageResource(img[0]);
            }
        });
    }

    /**
     * Call the lifecycle management method of the remoteView activity.
     * RN callbacks.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(option);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        remoteView.onStart();
        sendEvent(Event.ON_START, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        remoteView.onResume();
        sendEvent(Event.ON_RESUME, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        remoteView.onPause();
        sendEvent(Event.ON_PAUSE, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remoteView.onDestroy();
        sendEvent(Event.ON_DESTROY, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        remoteView.onStop();
        sendEvent(Event.ON_STOP, null);
    }

    private void addMainView(Rect frameRect) {
        this.viewfinderView = new ViewfinderView(this, frameRect);
        this.viewfinderView.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        this.cardLayout.addView(this.viewfinderView);
    }
}