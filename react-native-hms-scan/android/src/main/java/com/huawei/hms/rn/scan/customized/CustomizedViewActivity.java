/*
    Copyright 2020-2022. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.scan.customized;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.huawei.hms.hmsscankit.RemoteView;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.huawei.hms.rn.scan.R;
import com.huawei.hms.rn.scan.logger.HMSLogger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import static com.huawei.hms.rn.scan.utils.ReactUtils.toWM;

public class CustomizedViewActivity extends ReactActivity {
    private ReactApplicationContext mContext;

    private RemoteView remoteView;

    private ImageView flashButton;

    private Gson mGson = new GsonBuilder().setPrettyPrinting().create();

    private HMSLogger mHMSLogger;

    int mScreenWidth;

    int mScreenHeight;

    int SCAN_FRAME_SIZE_WIDTH;

    int SCAN_FRAME_SIZE_HEIGHT;

    boolean continuouslyScan;

    boolean enableReturnOriginalScan;

    Intent intent;

    Bundle bundle;

    private int[] img = {R.drawable.flashlight_on, R.drawable.flashlight_off};

    public static final int REQUEST_CODE_PHOTO = 0X1113;

    public enum Event {
        ON_RESPONSE("onResponse"),
        ON_START("onStart"),
        ON_RESUME("onResume"),
        ON_PAUSE("onPause"),
        ON_DESTROY("onDestroy"),
        ON_STOP("onStop"),
        ON_ORIGINAL_SCAN_LOAD("onOriginalScanLoad");

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

    void sendEvent(Event event, @Nullable Object obj) {
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event.getName(), obj);
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

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_defined);
        FrameLayout frameLayout = findViewById(R.id.rim);
        ImageView galleryButton = findViewById(R.id.img_btn);
        ImageView scanFrame = findViewById(R.id.scan_area);
        flashButton = findViewById(R.id.flush_btn);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;

        SCAN_FRAME_SIZE_HEIGHT = Objects.requireNonNull(bundle).getInt("rectHeight");
        SCAN_FRAME_SIZE_WIDTH = bundle.getInt("rectWidth");

        int scanFrameSizeHeight = (int) (SCAN_FRAME_SIZE_HEIGHT * density);
        int scanFrameSizeWidth = (int) (SCAN_FRAME_SIZE_WIDTH * density);

        Rect rect = new Rect();
        rect.left = mScreenWidth / 2 - scanFrameSizeWidth / 2;
        rect.right = mScreenWidth / 2 + scanFrameSizeWidth / 2;
        rect.top = mScreenHeight / 2 - scanFrameSizeHeight / 2;
        rect.bottom = mScreenHeight / 2 + scanFrameSizeHeight / 2;

        scanFrame.getLayoutParams().height = rect.height();
        scanFrame.getLayoutParams().width = rect.width();

        continuouslyScan = bundle.getBoolean("continuouslyScan");

        enableReturnOriginalScan = bundle.getBoolean("enableReturnOriginalScan");

        RemoteView.Builder builder = new RemoteView.Builder().setContext(this)
            .setBoundingBox(rect)
            .setFormat(bundle.getInt("scanType"), bundle.getIntArray("additionalScanTypes"))
            .setContinuouslyScan(continuouslyScan);

        if (enableReturnOriginalScan) {
            builder.enableReturnBitmap();
        }

        remoteView = builder.build();

        RNHMSScanCustomizedViewModule.setViews(remoteView, flashButton);

        mHMSLogger.startMethodExecutionTimer("CustomizedViewActivity.customizedView");
        remoteView.setOnResultCallback(result -> {
            if (enableReturnOriginalScan) {
                sendOriginalScan(result[0]);
            }
            if (result != null && result.length > 0 && result[0] != null && !TextUtils.isEmpty(
                result[0].getOriginalValue())) {
                if (!continuouslyScan) {
                    mHMSLogger.sendSingleEvent("CustomizedViewActivity.customizedView");
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(ScanUtil.RESULT, result[0]);
                    setResult(RESULT_OK, resultIntent);
                    CustomizedViewActivity.this.finish();

                } else {
                    sendEvent(Event.ON_RESPONSE, toWM(mGson.toJson(result[0])));
                    mHMSLogger.sendPeriodicEvent("CustomizedViewActivity.customizedView");
                }
            }
        });

        remoteView.onCreate(savedInstanceState);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
        frameLayout.addView(remoteView, params);

        setBackOperation();

        flashButton.setVisibility(View.INVISIBLE);

        if (bundle.getBoolean("flashOnLightChange")) {
            setFlashOperation();
            remoteView.setOnLightVisibleCallback(visible -> {
                if (visible) {
                    flashButton.setVisibility(View.VISIBLE);
                } else {
                    flashButton.setVisibility(View.INVISIBLE);
                }
            });
        }

        if (bundle.getBoolean("isFlashAvailable")) {
            flashButton.setVisibility(View.VISIBLE);
            setFlashOperation();
        }

        if (bundle.getBoolean("isGalleryAvailable")) {
            galleryButton.setVisibility(View.VISIBLE);
            setPictureScanOperation();
        }
    }

    private void sendOriginalScan(HmsScan scan) {
        Bitmap bitmap = scan.getOriginalBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        String byteArray = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        sendEvent(Event.ON_ORIGINAL_SCAN_LOAD, byteArray);
    }

    private void setPictureScanOperation() {
        ImageView galleryButton = findViewById(R.id.img_btn);
        galleryButton.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            CustomizedViewActivity.this.startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
        });
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

    private void setBackOperation() {
        ImageView backButton = findViewById(R.id.back_img);
        backButton.setOnClickListener(v -> CustomizedViewActivity.this.finish());
    }

    /**
     * Call the lifecycle management method of the remoteView activity.
     * RN callbacks.
     */
    @Override
    protected void onStart() {
        super.onStart();
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

    /**
     * Handle the return results from the gallery.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PHOTO) {
            intent = getIntent();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                mHMSLogger.startMethodExecutionTimer("CustomizedViewActivity.decodeWithBitmap");
                HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(CustomizedViewActivity.this, bitmap,
                    new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(
                        Objects.requireNonNull(intent.getExtras()).getInt("scanType"),
                        intent.getExtras().getIntArray("additionalScanTypes")).setPhotoMode(true).create());
                mHMSLogger.sendSingleEvent("CustomizedViewActivity.decodeWithBitmap");
                if (hmsScans != null && hmsScans.length > 0 && hmsScans[0] != null && !TextUtils.isEmpty(
                    hmsScans[0].getOriginalValue())) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(ScanUtil.RESULT, hmsScans[0]);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            } catch (IOException e) {
                Log.i("Customized-IOException", e.getMessage());
            }
        }
    }
}
