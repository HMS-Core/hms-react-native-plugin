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

package com.huawei.hms.rn.map;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.ViewGroupManager;
import com.huawei.hms.rn.map.logger.HMSLogger;

import java.util.HashMap;
import java.util.Map;

public class HMSInfoWindowView extends LinearLayout {
    private static final String TAG = HMSInfoWindowView.class.getSimpleName();
    private static final String REACT_CLASS = HMSInfoWindowView.class.getSimpleName();
    public int width;
    public int height;

    public HMSInfoWindowView(Context context) {
        super(context);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    public static class Manager extends ViewGroupManager<HMSInfoWindowView> {
        private HMSLogger logger;

        public Manager(Context context) {
            super();
            logger = HMSLogger.getInstance(context);
        }

        @NonNull
        @Override
        public String getName() {
            return "HMSInfoWindowView";
        }

        @NonNull
        @Override
        public HMSInfoWindowView createViewInstance(@NonNull ThemedReactContext context) {
            logger.startMethodExecutionTimer("HMSInfoWindow");
            HMSInfoWindowView view = new HMSInfoWindowView(context);
            logger.sendSingleEvent("HMSInfoWindow");
            return view;
        }

        @Override
        public LayoutShadowNode createShadowNodeInstance() {
            return new SizeLayoutShadowNode();
        }

        @Override
        public void updateExtraData(HMSInfoWindowView root, Object extraData) {
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
