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

package com.huawei.hms.rn.map;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.ViewGroupManager;

import java.util.HashMap;
import java.util.Map;

public class RNHMSInfoWindowView extends LinearLayout {
    private static final String TAG = RNHMSInfoWindowView.class.getSimpleName();
    private static final String REACT_CLASS = RNHMSInfoWindowView.class.getSimpleName();
    public int width;
    public int height;

    public RNHMSInfoWindowView(Context context) {
        super(context);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    public static class Manager extends ViewGroupManager<RNHMSInfoWindowView> {
        @NonNull
        @Override
        public String getName() {
            return REACT_CLASS;
        }

        @NonNull
        @Override
        public RNHMSInfoWindowView createViewInstance(@NonNull ThemedReactContext context) {
            return new RNHMSInfoWindowView(context);
        }

        @Override
        public LayoutShadowNode createShadowNodeInstance() {
            return new SizeLayoutShadowNode();
        }

        @Override
        public void updateExtraData(RNHMSInfoWindowView root, Object extraData) {
            Map<String, Integer> sizeData = (Map<String, Integer>) extraData;
            root.width = sizeData.get("width");
            root.height = sizeData.get("height");
        }
    }

    public static class SizeLayoutShadowNode extends LayoutShadowNode {
        @Override
        public void onCollectExtraUpdates(UIViewOperationQueue uiViewOperationQueue) {
            super.onCollectExtraUpdates(uiViewOperationQueue);
            Map<String, Integer> sizeData = new HashMap<>();
            sizeData.put("width", (int)getLayoutWidth());
            sizeData.put("height", (int)getLayoutHeight());
            uiViewOperationQueue.enqueueUpdateExtraData(getReactTag(), sizeData);
        }
    }
}
