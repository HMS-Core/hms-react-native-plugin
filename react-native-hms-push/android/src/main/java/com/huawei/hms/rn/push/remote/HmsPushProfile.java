/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.push.remote;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.hms.push.HmsProfile;
import com.huawei.hms.rn.push.logger.HMSLogger;
import com.huawei.hms.rn.push.utils.ResultUtils;


public class HmsPushProfile extends ReactContextBaseJavaModule {
    private final String TAG = HmsPushProfile.class.getSimpleName();

    private final HmsProfile hmsProfile;
    private final HMSLogger hmsLogger;

    private static volatile ReactApplicationContext context;

    public HmsPushProfile(ReactApplicationContext reactContext) {
        super(reactContext);
        setContext(reactContext);
        hmsProfile = HmsProfile.getInstance(reactContext);
        hmsLogger = HMSLogger.getInstance(getContext());
    }

    @NonNull
    @Override
    public String getName() {
        return TAG;
    }

    public static ReactApplicationContext getContext() {
        return context;
    }

    public static void setContext(ReactApplicationContext reactContext) {
        HmsPushProfile.context = reactContext;
    }

    @ReactMethod
    public void isSupportProfile(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("isSupportProfile");
        try {
            boolean result = hmsProfile.isSupportProfile();
            hmsLogger.sendSingleEvent("isSupportProfile");
            ResultUtils.handleResult(true, result, promise);
        } catch (Exception e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("isSupportProfile", e.getMessage());
            ResultUtils.handleResult(false, e.getLocalizedMessage(), promise);
        }
    }

    @NonNull
    @ReactMethod
    public void addProfile(int type, String profileId, final Promise promise) {
        hmsLogger.startMethodExecutionTimer("addProfile");
        hmsProfile.addProfile(type, profileId)
            .addOnSuccessListener(aVoid -> {
                ResultUtils.handleResult(true, true, promise);
                hmsLogger.sendSingleEvent("addProfile");
            })
            .addOnFailureListener(error -> {
                ResultUtils.handleResult(false, error.getLocalizedMessage(), promise);
                hmsLogger.sendSingleEvent("addProfile", error.getMessage());
            });
    }

    @ReactMethod
    public void addProfileWithSubjectId(String subjectId, int type, String profileId, final Promise promise) {
        hmsLogger.startMethodExecutionTimer("addProfileWithSubjectId");
        hmsProfile.addProfile(subjectId, type, profileId)
            .addOnSuccessListener(aVoid -> {
                ResultUtils.handleResult(true, true, promise);
                hmsLogger.sendSingleEvent("addProfileWithSubjectId");
            })
            .addOnFailureListener(error -> {
                ResultUtils.handleResult(false, error.getLocalizedMessage(), promise);
                hmsLogger.sendSingleEvent("addProfileWithSubjectId", error.getMessage());
            });
    }

    @ReactMethod
    public void deleteProfile(String profileId, final Promise promise) {
        hmsLogger.startMethodExecutionTimer("deleteProfile");
        hmsProfile.deleteProfile(profileId)
            .addOnSuccessListener(aVoid -> {
                ResultUtils.handleResult(true, true, promise);
                hmsLogger.sendSingleEvent("deleteProfile");
            })
            .addOnFailureListener(error -> {
                ResultUtils.handleResult(false, error.getLocalizedMessage(), promise);
                hmsLogger.sendSingleEvent("deleteProfile", error.getMessage());
            });
    }

    @ReactMethod
    public void deleteProfileWithSubjectId(String subjectId, String profileId, final Promise promise) {
        hmsLogger.startMethodExecutionTimer("deleteProfileWithSubjectId");
        hmsProfile.deleteProfile(subjectId, profileId)
            .addOnSuccessListener(aVoid -> {
                ResultUtils.handleResult(true, true, promise);
                hmsLogger.sendSingleEvent("deleteProfileWithSubjectId");
            })
            .addOnFailureListener(error -> {
                ResultUtils.handleResult(false, error.getLocalizedMessage(), promise);
                hmsLogger.sendSingleEvent("deleteProfileWithSubjectId", error.getMessage());
            });
    }
}
