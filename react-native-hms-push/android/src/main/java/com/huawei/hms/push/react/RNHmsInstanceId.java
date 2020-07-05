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

package com.huawei.hms.push.react;

import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.aaid.entity.AAIDResult;
import com.huawei.hms.common.ApiException;

import java.util.HashMap;
import java.util.Map;

import com.huawei.agconnect.config.AGConnectServicesConfig;

public class RNHmsInstanceId extends ReactContextBaseJavaModule {

    private static ReactApplicationContext mContext;

    public RNHmsInstanceId(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNHmsInstanceId";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        return constants;
    }


    @ReactMethod
    public void getToken(Callback callBack) {
        try {
            String appId = AGConnectServicesConfig.fromContext(mContext).getString("client/app_id");
            String token = HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).getToken(appId, "HCM");
            callBack.invoke(Constants.RESULT_SUCESS, token);
            Log.d("token", token);
        } catch (ApiException e) {
            callBack.invoke(e.getStatusCode(), e.getStatusCode());
        }

    }

    @ReactMethod
    public void getId(Callback callBack) {
        try {
            String instanceId = HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).getId();
            callBack.invoke(Constants.RESULT_SUCESS, instanceId);
        } catch (Exception e) {
            callBack.invoke(Constants.RESULT_FAIL, e.getMessage());
        }
    }

    @ReactMethod
    public void getAAID(final Callback callBack) {
        Task<AAIDResult> idResult = HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).getAAID();
        idResult.addOnSuccessListener(new OnSuccessListener<AAIDResult>() {

            @Override
            public void onSuccess(AAIDResult aaidResult) {
                callBack.invoke(Constants.RESULT_SUCESS, aaidResult.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                callBack.invoke(Constants.RESULT_FAIL, e.getMessage());
            }
        });

    }

    @ReactMethod
    public void getCreationTime(Callback callBack) {
        try {
            String createTime = HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).getCreationTime() + "";
            callBack.invoke(Constants.RESULT_SUCESS, createTime);
        } catch (Exception e) {
            callBack.invoke(Constants.RESULT_FAIL, e.getMessage());
        }
    }

    @ReactMethod
    public void deleteAAID(Callback callBack) {
        try {
            HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).deleteAAID();
            callBack.invoke(Constants.RESULT_SUCESS);
        } catch (ApiException e) {
            callBack.invoke(e.getStatusCode(), e.getMessage());
        }
    }

    @ReactMethod
    public void deleteToken(Callback callBack) {
        try {
            String appId = AGConnectServicesConfig.fromContext(mContext).getString("client/app_id");
            HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).deleteToken(appId, "HCM");
            callBack.invoke(Constants.RESULT_SUCESS);
        } catch (ApiException e) {
            callBack.invoke(e.getStatusCode(), e.getMessage());
        }

    }

}
