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

package com.huawei.hms.rn.health.foundation.view;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;

import java.lang.ref.WeakReference;

/**
 * BaseProtocol of all the {@link com.huawei.hms.rn.health} Module implementations.
 *
 * @since v.5.0.1
 */
public interface BaseProtocol {
    /**
     * View protocol that represents the current Activity.
     */
    enum View {
        INSTANCE;

        /**
         * Represents the current activity.
         *
         * @return Activity instance.
         */
        public static synchronized Activity getActivity(Activity activity) {
            return new WeakReference<>(activity).get();
        }
    }

    /**
     * Event protocol that sends event to JS side.
     */
    interface Event {
        /**
         * Sends event to RN Side.
         *
         * @param reactContext ReactContext instance.
         * @param eventName Event name that will be available via modules.
         * @param params Event params.
         */
        void sendEvent(ReactContext reactContext, @Nullable String eventName, @Nullable WritableMap params);
    }

}
