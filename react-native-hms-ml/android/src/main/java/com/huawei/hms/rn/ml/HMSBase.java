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

package com.huawei.hms.rn.ml;

import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.UNKNOWN;

import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.rn.ml.helpers.constants.HMSResults;
import com.huawei.hms.rn.ml.helpers.utils.HMSLogger;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Map;

import javax.annotation.Nullable;

public class HMSBase extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;

    private String moduleName;

    private Map<String, Object> constant;

    private HMSLogger logger;

    /**
     * Initializes base fields for each module
     *
     * @param mContext context
     * @param moduleName module name
     * @param constant module constant
     */
    public HMSBase(ReactApplicationContext mContext, String moduleName, Map<String, Object> constant) {
        super(mContext);
        this.mContext = mContext;
        this.moduleName = moduleName;
        this.constant = constant;
        this.logger = HMSLogger.getInstance(mContext);
    }

    /**
     * Module name used by RN side
     *
     * @return name of the child module
     */
    @Override
    public String getName() {
        return this.moduleName;
    }

    /**
     * Exposes constants to RN side
     *
     * @return Constant of related child module
     */
    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return this.constant;
    }

    /**
     * To obtain context
     * @return app context
     */
    public ReactApplicationContext getContext() {
        return mContext;
    }

    /**
     * Start method execution timer in logger
     * @param methodName Name of the method
     */
    protected void startMethodExecTimer(String methodName) {
        logger.startMethodExecutionTimer(methodName);
    }

    /**
     * Handles exceptions
     *
     * @param methodName method name
     * @param e Exception
     * @param promise promise
     */
    protected void handleResult(String methodName, Exception e, Promise promise) {
        logger.sendSingleEvent(methodName, e.getMessage());
        if (e instanceof MLException) {
            MLException e1 = (MLException) e;
            promise.resolve(UNKNOWN.getStatusAndMessage(e1.getErrCode(), e1.getMessage()));
        } else {
            promise.resolve(UNKNOWN.getStatusAndMessage(null, e.getMessage()));
        }
    }

    /**
     * Handles exceptions
     *
     * @param methodName method name
     * @param e Exception
     * @param promise promise
     */
    protected void handleResult(String methodName, HMSResults e, Promise promise) {
        logger.sendSingleEvent(methodName);
        promise.resolve(e.getStatusAndMessage());
    }

    /**
     * Handles success
     *
     * @param methodName method name
     * @param writableMap result
     * @param promise promise
     */
    protected void handleResult(String methodName, WritableMap writableMap, Promise promise) {
        logger.sendSingleEvent(methodName);
        promise.resolve(writableMap);
    }

    /**
     * Helper method that sends an event to RN side.
     *
     * @param eventName event name
     * @param methodName method name which calls this method
     * @param params WritableMap object that contains related keys and values
     */
    protected void sendEvent(String eventName, String methodName, WritableMap params) {
        logger.sendSingleEvent(methodName);
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
