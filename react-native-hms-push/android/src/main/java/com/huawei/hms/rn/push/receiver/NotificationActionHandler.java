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

package com.huawei.hms.rn.push.receiver;

import android.content.Context;
import android.os.Bundle;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.huawei.hms.rn.push.local.HmsLocalNotificationActionPublisher;

public class NotificationActionHandler implements Runnable {
    Context context;
    Bundle bundle;

    public NotificationActionHandler(Context context, Bundle bundle) {
        this.context = context;
        this.bundle = bundle;
    }

    @Override
    public void run() {
        final ReactInstanceManager reactInstanceManager = ((ReactApplication) context.getApplicationContext()).getReactNativeHost().getReactInstanceManager();
        ReactContext reactContext = reactInstanceManager.getCurrentReactContext();

        if (reactContext != null) {
            HmsLocalNotificationActionPublisher hmsLocalNotificationActionPublisher = new HmsLocalNotificationActionPublisher(reactContext);

            hmsLocalNotificationActionPublisher.notifyNotificationAction(bundle);
        } else {
            reactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                public void onReactContextInitialized(ReactContext context) {

                    HmsLocalNotificationActionPublisher hmsLocalNotificationActionPublisher = new HmsLocalNotificationActionPublisher(context);

                    hmsLocalNotificationActionPublisher.notifyNotificationAction(bundle);

                    reactInstanceManager.removeReactInstanceEventListener(this);
                }
            });

            if (!reactInstanceManager.hasStartedCreatingInitialContext()) {

                reactInstanceManager.createReactContextInBackground();
            }
        }
    }
}
