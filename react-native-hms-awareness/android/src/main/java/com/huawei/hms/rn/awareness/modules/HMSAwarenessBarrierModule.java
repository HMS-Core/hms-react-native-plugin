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

package com.huawei.hms.rn.awareness.modules;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.rn.awareness.constants.Constants;
import com.huawei.hms.rn.awareness.constants.LocaleConstants;
import com.huawei.hms.rn.awareness.utils.BarrierReceiver;
import com.huawei.hms.rn.awareness.utils.DataUtils;
import com.huawei.hms.rn.awareness.wrapper.AwarenessBarrierWrapper;
import com.huawei.hms.rn.awareness.wrapper.AwarenessCombinationBarrierWrapper;

import java.util.Map;

import javax.annotation.Nullable;

import static com.huawei.hms.rn.awareness.utils.DataUtils.errorMessage;

public class HMSAwarenessBarrierModule extends ReactContextBaseJavaModule {

    AwarenessBarrierWrapper awarenessBarrierWrapper;
    AwarenessCombinationBarrierWrapper combinationBarrierWrapper;
    PendingIntent pendingIntent;

    public HMSAwarenessBarrierModule(ReactApplicationContext reactContext) {
        super(reactContext);
        final String barrierReceiverAction = "com.huawei.hms.rn.awareness.modules.ReceiverAction";

        Intent intent = new Intent();
        intent.setPackage(reactContext.getPackageName());
        intent.setAction(barrierReceiverAction);

        pendingIntent = PendingIntent.getBroadcast(reactContext, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        BarrierReceiver barrierReceiver = new BarrierReceiver(reactContext);
        reactContext.registerReceiver(barrierReceiver, new IntentFilter(barrierReceiverAction));

        awarenessBarrierWrapper = new AwarenessBarrierWrapper(reactContext, pendingIntent);
        combinationBarrierWrapper = new AwarenessCombinationBarrierWrapper(reactContext, pendingIntent);
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSAwarenessBarrierModule";
    }

    /**
     * Allows you to customize notifications.
     *
     * @param map     : WritableMap
     * @param promise : WritableMap
     */
    @ReactMethod
    public void setBackgroundNotification(final ReadableMap map, final Promise promise) {
        try {
            SharedPreferences.Editor editor = getReactApplicationContext().getSharedPreferences(getReactApplicationContext().getPackageName(),
                    Context.MODE_PRIVATE).edit();
            String title = LocaleConstants.KEY_CONTENT_TITLE;
            String defTitle = LocaleConstants.DEFAULT_CONTENT_TITLE;
            String text = LocaleConstants.KEY_CONTENT_TEXT;
            String defText = LocaleConstants.DEFAULT_CONTENT_TEXT;
            String type = LocaleConstants.KEY_DEF_TYPE;
            String defType = LocaleConstants.DEFAULT_DEF_TYPE;
            String resource = LocaleConstants.KEY_RESOURCE_NAME;
            String defResource = LocaleConstants.DEFAULT_RESOURCE_NAME;

            editor.putString(title, map.hasKey(title) ? map.getString(title) : defTitle);
            editor.putString(text, map.hasKey(text) ? map.getString(text) : defText);
            editor.putString(type, map.hasKey(type) ? map.getString(type) : defType);
            editor.putString(resource, map.hasKey(resource) ? map.getString(resource) : defResource);
            editor.apply();
            promise.resolve(DataUtils.valueConvertToMap("Response", "success"));
        }catch (IllegalArgumentException e){
            errorMessage(null, "barrierModule","setNotification" , e, promise);
        }
    }

    /**
     * Returns the registered barriers and their properties in the array.
     *
     * @param queryBarrierReq : WritableMap
     * @param promise         : WritableMap
     */
    @ReactMethod
    public void queryBarrier(ReadableArray queryBarrierReq, Promise promise) {
        awarenessBarrierWrapper.queryBarrier(queryBarrierReq, promise);
    }

    /**
     * Returns all added barriers and their attributes.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void queryAllBarrier(Promise promise) {
        awarenessBarrierWrapper.queryAllBarrier(promise);
    }

    /**
     * This method removes barrier labels in the array.
     *
     * @param promise          : return WritableMap
     * @param updateBarrierReq : Barrier object to be added.
     * @param barrierEventType : Must match one of the values ​​starting with "EVENT_ ..."
     *                         in the {@link Constants} class. Example: EVENT_HEADSET
     */
    @ReactMethod
    public void updateBarrier(String barrierEventType, ReadableMap updateBarrierReq, Promise
            promise) {
        awarenessBarrierWrapper.updateBarrier(barrierEventType, updateBarrierReq, promise);
    }

    /**
     * You can create a combination of barriers using "and", "or" and "not".
     * You can listen to different awareness features with a single barrier.
     *
     * @param barrierLabel          : It is a unique value for barrier. This value makes it stand out from other barriers.
     * @param combinationBarrierReq : It includes barrier arrays that need to be added.
     * @param promise               : return WritableMap
     */
    @ReactMethod
    public void combinationBarrier(String barrierLabel, ReadableArray
            combinationBarrierReq, Promise promise) {
        combinationBarrierWrapper.addCombinationBarrier(barrierLabel, combinationBarrierReq, promise);
    }

    /**
     * This method removes barrier labels in the array.
     *
     * @param promise           : WritableMap
     * @param deleteBarrierReq: barrierLabel array
     */
    @ReactMethod
    public void deleteBarrier(ReadableArray deleteBarrierReq, Promise promise) {
        awarenessBarrierWrapper.deleteBarrier(deleteBarrierReq, promise);
    }

    /**
     * This method deletes all registered barriers.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void deleteAllBarrier(Promise promise) {
        awarenessBarrierWrapper.deleteAllBarrier(promise);
    }

    /**
     * You can use constants values for request objects.
     *
     * @return {@link Constants} class.
     */
    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return Constants.getAllConstants();
    }
}

