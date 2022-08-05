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

package com.huawei.hms.rn.ml.helpers.views;

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.LENS_SURFACE_ON_CHANGED;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.LENS_SURFACE_ON_CREATED;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.LENS_SURFACE_ON_DESTROY;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;

public class HMSCustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private ThemedReactContext applicationContext;

    public HMSCustomSurfaceView(Context context) {
        super(context);
        applicationContext = (ThemedReactContext) context;
        HMSUtils.getInstance().setSurfaceViewHolder(getHolder());
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        sendEvent(LENS_SURFACE_ON_CREATED, null);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        WritableMap wm = Arguments.createMap();
        wm.putInt("format", i);
        wm.putInt("width", i1);
        wm.putInt("height", i2);
        sendEvent(LENS_SURFACE_ON_CHANGED, wm);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        sendEvent(LENS_SURFACE_ON_DESTROY, null);
    }

    private void sendEvent(String eventName, WritableMap params) {
        applicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
