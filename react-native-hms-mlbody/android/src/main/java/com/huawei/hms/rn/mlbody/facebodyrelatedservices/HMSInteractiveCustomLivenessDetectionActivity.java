/*
 * Copyright 2023-2024. Huawei Technologies Co., Ltd. All rights reserved.
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

package com.huawei.hms.rn.mlbody.facebodyrelatedservices;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.huawei.hms.mlsdk.interactiveliveness.MLInteractiveLivenessCaptureError;
import com.huawei.hms.mlsdk.interactiveliveness.MLInteractiveLivenessCaptureResult;
import com.huawei.hms.mlsdk.interactiveliveness.MLInteractiveLivenessDetectView;
import com.huawei.hms.mlsdk.interactiveliveness.OnMLInteractiveLivenessDetectCallback;
import com.huawei.hms.mlsdk.interactiveliveness.action.InteractiveLivenessStateCode;
import com.huawei.hms.mlsdk.interactiveliveness.action.MLInteractiveLivenessConfig;
import com.huawei.hms.rn.mlbody.R;

import com.facebook.react.ReactActivity;

import java.util.HashMap;

public class HMSInteractiveCustomLivenessDetectionActivity extends ReactActivity {
    public static final int DETECT_FACE_TIME_OUT = 11404;

    private static final String TAG = HMSInteractiveCustomLivenessDetectionActivity.class.getSimpleName();

    String failResult = "";

    boolean autoSizeText = false;

    int minTextSize = 3;

    int maxTextSize = 24;

    int textMargin = 900;

    int granularity = 0;

    String header = "";

    private MLInteractiveLivenessDetectView mlInteractiveLivenessDetectView;

    private FrameLayout mPreviewContainer;

    private RelativeLayout mTextContainer;

    private Intent intent;

    private Context mContext;

    private boolean defaultConfig = false;

    private Bundle bundle;

    private ImageView imgBack;

    private MLInteractiveLivenessConfig interactiveLivenessConfig;

    private float textSize;

    private TextView statusCode;
    private TextView imgbackHeader;
    private TextView tips;

    private HashMap<Integer, String> actionMap = new HashMap<>();

    private HashMap<Integer, String> statusCodeList = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint( {"WrongViewCast", "UseCompatLoadingForDrawables"})
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();
        intent = getIntent();
        statusCodeList = (HashMap<Integer, String>) getIntent().getSerializableExtra("statusCodeMessageMap");

        try {
            bundle = intent.getExtras();
        } catch (Exception e) {
            Log.i("Customized-Exception", e.getMessage());
        }

        autoSizeText = bundle.getBoolean("autoSizeText");
        minTextSize = bundle.getInt("minTextSize");
        maxTextSize = bundle.getInt("maxTextSize");
        textMargin = bundle.getInt("textMargin");
        textSize = bundle.getFloat("textSize");
        granularity = bundle.getInt("granularity");
        header = bundle.getString("header");

        setLayout();

        ImageView scanBg = new ImageView(this);
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(
            bundle.getInt("cameraRight") - bundle.getInt("cameraLeft"),
            bundle.getInt("cameraBottom") - bundle.getInt("cameraTop"));
        scanBg.setLayoutParams(layoutParams);
        scanBg.setY(bundle.getInt("faceTop") - 25);
        scanBg.setX(bundle.getInt("faceLeft"));
        scanBg.setImageDrawable(getDrawable(R.drawable.liveness_detection_frame));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgbackHeader.setText(header);

        mTextContainer.setY(textMargin);

        if (bundle.getBoolean("showStatusCodes")) {
            statusCode.setVisibility(View.VISIBLE);
        } else {
            statusCode.setVisibility(View.GONE);
        }
        tips.setTextColor(bundle.getInt("textColor"));
        statusCode.setTextColor(bundle.getInt("textColor"));

        applyStyleAutoSize(statusCode);
        applyStyleAutoSize(tips);


        actionMap = (HashMap<Integer, String>) getIntent().getSerializableExtra("actionArray");
        int[] actionArray = new int[actionMap.size()];

        if (actionArray != null && actionArray.length != 0) {

            int index = 0;
            for (Integer key : actionMap.keySet()) {
                actionArray[index++] = key;
            }

            interactiveLivenessConfig = new MLInteractiveLivenessConfig.Builder().setActionArray(actionArray,
                bundle.getInt("num"), bundle.getBoolean("isRandom")).build();
        } else {
            defaultConfig = true;
            interactiveLivenessConfig = new MLInteractiveLivenessConfig.Builder().build();
        }

        mlInteractiveLivenessDetectView = new MLInteractiveLivenessDetectView.Builder().setContext(this)
            // Set the position of the camera video stream. (The coordinates of the upper left vertex and lower right vertex are determined based on the preview view.)
            .setFrameRect(
                new Rect(bundle.getInt("cameraLeft"), bundle.getInt("cameraTop"), bundle.getInt("cameraRight"),
                    bundle.getInt("cameraBottom")))
            // Set the configurations for interactive biometric verification.
            .setActionConfig(interactiveLivenessConfig)
            // Set the position of the face frame relative to the camera preview view. The coordinates of the upper left vertex and lower right vertex are determined based on a 640 x 480 px image. You are advised to ensure the face frame dimensions comply with the ratio of a real face. The face frame is used to check whether a face is too close to or far from the camera, and whether a face deviates from the camera view.
            .setFaceRect(new Rect(bundle.getInt("faceLeft"), bundle.getInt("faceTop"), bundle.getInt("faceRight"),
                bundle.getInt("faceBottom")))
            // Set the verification timeout interval. The recommended value is about 10,000 ms.
            .setDetectionTimeOut(bundle.getInt("detectionTimeOut"))
            .setDetectCallback(new OnMLInteractiveLivenessDetectCallback() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onCompleted(MLInteractiveLivenessCaptureResult result) {
                    if (bundle.getBoolean("showStatusCodes")) {
                        statusCode.setText(statusCodeList.get(result.getStateCode()));
                    }
                    switch (result.getStateCode()) {
                        // Processing logic when the verification is passed.
                        case InteractiveLivenessStateCode.ALL_ACTION_CORRECT:
                            tips.setText("success");
                            setResult(RESULT_OK);
                            HMSInteractiveLivenessCustomDetectionHandler.setDetectResult(result);
                            setResult(Activity.RESULT_OK);
                            finish();
                            break;
                        // Processing logic during verification.
                        case InteractiveLivenessStateCode.IN_PROGRESS:

                            switch (result.getActionType()) {
                                case MLInteractiveLivenessConfig.SHAKE_DOWN_ACTION:
                                    if (defaultConfig) {
                                        tips.setText("Nod your head.");
                                    } else {
                                        tips.setText(actionMap.get(1));
                                    }
                                    break;
                                case MLInteractiveLivenessConfig.OPEN_MOUTH_ACTION:
                                    if (defaultConfig) {
                                        tips.setText("Open your mouth. ");
                                    } else {
                                        tips.setText(actionMap.get(2));
                                    }
                                    break;
                                case MLInteractiveLivenessConfig.EYE_CLOSE_ACTION:
                                    if (defaultConfig) {
                                        tips.setText("Blink. ");
                                    } else {
                                        tips.setText(actionMap.get(3));
                                    }
                                    break;
                                case MLInteractiveLivenessConfig.SHAKE_LEFT_ACTION:
                                    if (defaultConfig) {
                                        tips.setText("Turn your head to the left. ");
                                    } else {
                                        tips.setText(actionMap.get(4));
                                    }
                                    break;
                                case MLInteractiveLivenessConfig.SHAKE_RIGHT_ACTION:
                                    if (defaultConfig) {
                                        tips.setText("Turn your head to the right. ");
                                    } else {
                                        tips.setText(actionMap.get(5));
                                    }
                                    break;
                                case MLInteractiveLivenessConfig.GAZED_ACTION:
                                    if (defaultConfig) {
                                        tips.setText("Stare at the screen. ");
                                    } else {
                                        tips.setText(actionMap.get(6));
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case InteractiveLivenessStateCode.RESULT_TIME_OUT:
                            setResult(DETECT_FACE_TIME_OUT);
                            finish();
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onError(int errorCode) {
                    switch (errorCode) {
                        case MLInteractiveLivenessCaptureError.CAMERA_NO_PERMISSION:
                            failResult = "The camera permission is not obtained.";
                            break;
                        case MLInteractiveLivenessCaptureError.CAMERA_START_FAILED:
                            failResult = "The camera fails to be started.";
                            break;
                        case MLInteractiveLivenessCaptureError.DETECT_FACE_TIME_OUT:
                            failResult = "The face detection module times out.";
                            break;
                        case MLInteractiveLivenessCaptureError.USER_DEFINED_ACTIONS_INVALID:
                            failResult = "The user-defined action is invalid.";
                            break;
                        case MLInteractiveLivenessCaptureError.USER_CANCEL:
                            failResult = "The operation is canceled by the user.";
                            break;
                        case -1:
                            failResult = "Initialization failed.";
                            break;
                        default:
                            break;
                    }
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
            })
            .build();
        mPreviewContainer.addView(mlInteractiveLivenessDetectView, 0);
        mPreviewContainer.addView(scanBg, 1);

        mlInteractiveLivenessDetectView.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void applyStyleAutoSize(TextView textView) {

        if (autoSizeText) {

            textView.setAutoSizeTextTypeUniformWithConfiguration(minTextSize, maxTextSize, granularity,
                TypedValue.COMPLEX_UNIT_DIP);
        } else {

            textView.setTextSize(textSize);
        }

    }
    void setLayout(){

        setContentView(R.layout.activity_liveness_custom_detection);
        mPreviewContainer = findViewById(R.id.surface_layout);
        imgBack = findViewById(R.id.img_back);
        imgbackHeader = findViewById(R.id.img_back_title);
        statusCode = findViewById(R.id.status_code);
        mTextContainer = findViewById(R.id.bg);
        tips = findViewById(R.id.tips);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mlInteractiveLivenessDetectView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mlInteractiveLivenessDetectView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mlInteractiveLivenessDetectView.onResume();
        if (mlInteractiveLivenessDetectView != null) {
            Log.i(TAG, "GetWidth:" + mlInteractiveLivenessDetectView.getHeight() + "GetHeight:"
                + mlInteractiveLivenessDetectView.getWidth());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mlInteractiveLivenessDetectView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mlInteractiveLivenessDetectView.onStop();
    }
}