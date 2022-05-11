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

package com.huawei.hms.rn.nearby.modules;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.rn.nearby.utils.HMSLogger;
import com.huawei.hms.rn.nearby.utils.HMSResult;

import java.util.Map;

import static com.huawei.hms.rn.nearby.utils.HMSResult.FAILURE;
import static com.huawei.hms.rn.nearby.utils.HMSResult.SUCCESS;

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
    HMSBase(ReactApplicationContext mContext, String moduleName, Map<String, Object> constant) {
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
    @Override
    public Map<String, Object> getConstants() {
        return this.constant;
    }

    /**
     * Context
     * 
     * @return App context
     */
    ReactApplicationContext getContext() {
        return mContext;
    }

    /**
     * Start method execution timer in logger
     * 
     * @param methodName Name of method
     */
    void startMethodExecTimer(String methodName) {
        logger.startMethodExecutionTimer(methodName);
    }

    /**
     * Gives logger instance
     * 
     * @return Logger instance for enable disable methods
     */
    HMSLogger getLogger() {
        return logger;
    }

    /**
     * Handles HMSResult based results
     *
     * @param methodName method name
     * @param result result type
     * @param promise promise
     */
    void handleResult(String methodName, HMSResult result, Promise promise) {
        logger.sendSingleEvent(methodName, result.equals(SUCCESS) ? "0" : result.getResultMessage());
        promise.resolve(result.getStatusAndMessage());
    }

    /**
     * Handles HMSResult based results
     *
     * @param methodName method name
     * @param voidTask task
     * @param promise promise
     */
    void handleResult(String methodName, Task<Void> voidTask, Promise promise) {
        voidTask.addOnSuccessListener(aVoid -> handleResult(methodName, SUCCESS, promise))
            .addOnFailureListener(e -> handleResult(methodName, e, promise));
    }

    /**
     * Handles failure results
     *
     * @param methodName method name
     * @param e exception
     * @param promise promise
     */
    void handleResult(String methodName, Exception e, Promise promise) {
        logger.sendSingleEvent(methodName, e.getMessage());
        promise.resolve(FAILURE.getStatusAndMessage(null, e.getMessage()));
    }

    /**
     * Helper method that sends an event to RN side.
     *
     * @param eventName event name
     * @param methodName method name which calls this method
     * @param params WritableMap object that contains related keys and values
     */
    void sendEvent(String eventName, String methodName, WritableMap params) {
        logger.sendSingleEvent(methodName);
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

}
