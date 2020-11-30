/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.account.views;

import android.view.View;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton;

public class HMSHuaweiIdAuthButton extends SimpleViewManager<HuaweiIdAuthButton> {

    private static final String NAME_AUTH_BUTTON = "HMSHuaweiIdAuthButton";
    private static final String ARGUMENT_COLOR_POLICY = "colorPolicy";
    private static final String ARGUMENT_ENABLED = "enabled";
    private static final String ARGUMENT_THEME = "theme";
    private static final String ARGUMENT_CORNER_RADIUS = "cornerRadius";

    public HMSHuaweiIdAuthButton(ReactApplicationContext reactContext) {
    }

    @NonNull
    @Override
    public String getName() {
        return NAME_AUTH_BUTTON;
    }

    @NonNull
    @Override
    public HuaweiIdAuthButton createViewInstance(@NonNull ThemedReactContext reactContext) {
        HuaweiIdAuthButton button = new HuaweiIdAuthButton(reactContext);
        button.setVisibility(View.VISIBLE);
        button.setColorPolicy(HuaweiIdAuthButton.COLOR_POLICY_BLACK);
        return button;
    }

    @ReactProp(name = ARGUMENT_COLOR_POLICY)
    public void setColorPolicy(HuaweiIdAuthButton huaweiIdAuthButton, int colorPolicy) {
        huaweiIdAuthButton.setColorPolicy(colorPolicy);
    }

    @ReactProp(name = ARGUMENT_ENABLED)
    public void setEnabled(HuaweiIdAuthButton huaweiIdAuthButton, boolean isEnabled) {
        huaweiIdAuthButton.setEnabled(isEnabled);
    }

    @ReactProp(name = ARGUMENT_THEME)
    public void setTheme(HuaweiIdAuthButton huaweiIdAuthButton, int theme) {
        huaweiIdAuthButton.setTheme(theme);
    }

    @ReactProp(name = ARGUMENT_CORNER_RADIUS)
    public void setCornerRadius(HuaweiIdAuthButton huaweiIdAuthButton, int cornerRadius) {
        huaweiIdAuthButton.setCornerRadius(cornerRadius);
    }
}
