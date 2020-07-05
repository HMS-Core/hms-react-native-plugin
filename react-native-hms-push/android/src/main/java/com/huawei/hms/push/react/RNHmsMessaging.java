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

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.push.HmsMessaging;

import java.util.HashMap;
import java.util.Map;

public class RNHmsMessaging extends ReactContextBaseJavaModule {

    private static ReactApplicationContext mContext;

    public RNHmsMessaging(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNHmsMessaging";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        return constants;
    }

    @ReactMethod
    public void isAutoInitEnabled(Callback callBack) {
        try {
            String autoInit = HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).isAutoInitEnabled() + "";
            callBack.invoke(Constants.RESULT_SUCESS, autoInit);
        } catch (Exception e) {
            callBack.invoke(Constants.RESULT_FAIL, e.getMessage());
        }
    }

    @ReactMethod
    public void setAutoInitEnabled(boolean enabled, Callback callBack) {
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).setAutoInitEnabled(enabled);
            callBack.invoke(Constants.RESULT_SUCESS, enabled);
        } catch (Exception e) {
            callBack.invoke(Constants.RESULT_FAIL, e.getMessage());
        }
    }

    @ReactMethod
    public void turnOnPush(final Callback callBack) {
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).turnOnPush().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        callBack.invoke(Constants.RESULT_SUCESS);
                    } else {
                        callBack.invoke(Constants.RESULT_FAIL, task.getException().getMessage());
                    }
                }
            });
        } catch (Exception e) {
            callBack.invoke(Constants.RESULT_FAIL, e.getMessage());
        }
    }


    @ReactMethod
    public void turnOffPush(final Callback callBack) {
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).turnOffPush().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        callBack.invoke(Constants.RESULT_SUCESS);
                    } else {
                        callBack.invoke(Constants.RESULT_FAIL, task.getException().getMessage());
                    }
                }
            });
        } catch (Exception e) {
            callBack.invoke(Constants.RESULT_FAIL, e.getMessage());
        }
    }


    @ReactMethod
    public void subscribe(String topic, final Callback callBack) {

        if (topic == null || topic.toString().equals("")) {
            callBack.invoke(Constants.PARAMETR_IS_EMPTY, "topic is empty!");
            return;
        }
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).subscribe(topic).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                callBack.invoke(Constants.RESULT_SUCESS);
                            } else {
                                callBack.invoke(Constants.RESULT_FAIL, task.getException().getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            callBack.invoke(Constants.RESULT_FAIL, e.getMessage());
        }
    }

    @ReactMethod
    public void unsubscribe(String topic, final Callback callBack) {
        if (topic == null || topic.toString().equals("")) {
            callBack.invoke(Constants.PARAMETR_IS_EMPTY, "topic is empty!");
            return;
        }
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), mContext)).unsubscribe(topic).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                callBack.invoke(Constants.RESULT_SUCESS);
                            } else {
                                callBack.invoke(Constants.RESULT_FAIL, task.getException().getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            callBack.invoke(Constants.RESULT_FAIL, e.getMessage());
        }
    }

}
