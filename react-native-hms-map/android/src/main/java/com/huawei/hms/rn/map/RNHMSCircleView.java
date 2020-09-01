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
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.CircleOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.PatternItem;
import com.huawei.hms.rn.map.utils.ReactUtils;

import java.util.List;

import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerViewManager;

public class RNHMSCircleView extends MapLayerView {
    private static final String TAG = RNHMSCircleView.class.getSimpleName();
    private static final String REACT_CLASS = RNHMSCircleView.class.getSimpleName();
    public CircleOptions mCircleOptions = new CircleOptions();
    public Circle mCircle;

    public RNHMSCircleView(Context context) {
        super(context);
    }

    public static class Manager extends MapLayerViewManager<RNHMSCircleView> {
        @NonNull
        @Override
        public String getName() {
            return REACT_CLASS;
        }

        @NonNull
        @Override
        public RNHMSCircleView createViewInstance(@NonNull ThemedReactContext context) {
            return new RNHMSCircleView(context);
        }

        @ReactProp(name = "center") // {latitude: 0.0, longitude: 0.0}
        public void setCenter(RNHMSCircleView view, ReadableMap center) {
            view.setCenter(center);
        }

        @ReactProp(name = "clickable")
        public void setClickable(RNHMSCircleView view, boolean clickable) {
            view.setClickable(clickable);
        }

        @ReactProp(name = "fillColor", defaultInt = Color.BLACK)
        public void setFillColor(RNHMSCircleView view, int fillColor) {
            view.setFillColor(fillColor);
        }

        @ReactProp(name = "radius", defaultDouble = 0)
        public void setRadius(RNHMSCircleView view, double radius) {
            view.setRadius(radius);
        }

        @ReactProp(name = "strokeColor")
        public void setStrokeColor(RNHMSCircleView view, int strokeColor) {
            view.setStrokeColor(strokeColor);
        }

        @ReactProp(name = "strokeWidth", defaultFloat = 1f)
        public void setStrokeWidth(RNHMSCircleView view, float strokeWidth) {
            view.setStrokeWidth(strokeWidth);
        }

        @ReactProp(name = "strokePattern") // [{type: 0, length:20}]
        public void setStrokePattern(RNHMSCircleView view, ReadableArray strokePattern) {
            view.setStrokePattern(strokePattern);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(RNHMSCircleView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "zIndex", defaultFloat = 1.0f)
        public void setZIndex(RNHMSCircleView view, float zIndex) {
            view.setZIndex(zIndex);
        }
    }

    private void setCenter(ReadableMap center) {
        LatLng latLng = ReactUtils.getLatLngFromReadableMap(center);
        if (latLng == null) {
            return;
        }
        mCircleOptions.center(latLng);
        if (mCircle != null) {
            mCircle.setCenter(latLng);
        }
    }

    @Override
    public void setClickable(boolean clickable) {
        mCircleOptions.clickable(clickable);
        if (mCircle != null) {
            mCircle.setClickable(clickable);
        }
    }

    private void setFillColor(int fillColor) {
        mCircleOptions.fillColor(fillColor);
        if (mCircle != null) {
            mCircle.setFillColor(fillColor);
        }
    }

    private void setRadius(double radius) {
        mCircleOptions.radius(radius);
        if (mCircle != null) {
            mCircle.setRadius(radius);
        }
    }

    private void setStrokeColor(int strokeColor) {
        mCircleOptions.strokeColor(strokeColor);
        if (mCircle != null) {
            mCircle.setStrokeColor(strokeColor);
        }
    }

    private void setStrokePattern(ReadableArray strokePatternReadableArray) {
        List<PatternItem> strokePattern = ReactUtils.getPatternItemListFromReadableArray(strokePatternReadableArray);
        mCircleOptions.strokePattern(strokePattern);
        if (mCircle != null) {
            mCircle.setStrokePattern(strokePattern);
        }
    }

    private void setStrokeWidth(float strokeWidth) {
        mCircleOptions.strokeWidth(strokeWidth);
        if (mCircle != null) {
            mCircle.setStrokeWidth(strokeWidth);
        }
    }

    private void setVisible(boolean visible) {
        mCircleOptions.visible(visible);
        if (mCircle != null) {
            mCircle.setVisible(visible);
        }
    }

    private void setZIndex(float zIndex) {
        mCircleOptions.zIndex(zIndex);
        if (mCircle != null) {
            mCircle.setZIndex(zIndex);
        }
    }

    @Override
    public Circle addTo(HuaweiMap huaweiMap) {
        mCircle = huaweiMap.addCircle(mCircleOptions);
        return mCircle;
    }

    @Override
    public void removeFrom(HuaweiMap huaweiMap) {
        mCircle.remove();
    }
}
