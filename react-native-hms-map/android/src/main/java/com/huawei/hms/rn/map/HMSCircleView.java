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
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.CircleOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.PatternItem;
import com.huawei.hms.rn.map.logger.HMSLogger;
import com.huawei.hms.rn.map.utils.ReactUtils;

import java.util.List;

import static com.huawei.hms.rn.map.HMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.HMSMapView.MapLayerViewManager;

public class HMSCircleView extends MapLayerView {
    private static final String TAG = HMSCircleView.class.getSimpleName();
    private static final String REACT_CLASS = HMSCircleView.class.getSimpleName();
    public CircleOptions mCircleOptions = new CircleOptions();
    public Circle mCircle;


    public HMSCircleView(Context context) {
        super(context);

    }

    public static class Manager extends MapLayerViewManager<HMSCircleView> {
        private HMSLogger logger;

        public Manager(Context context) {
            super();
            logger = HMSLogger.getInstance(context);
        }

        @NonNull
        @Override
        public String getName() {
            return "HMSCircleView";
        }


        @NonNull
        @Override
        public HMSCircleView createViewInstance(@NonNull ThemedReactContext context) {
            logger.startMethodExecutionTimer("HMSCircle");
            HMSCircleView view = new HMSCircleView(context);
            logger.sendSingleEvent("HMSCircle");
            return view;
        }

        @ReactProp(name = "center")
        public void setCenter(HMSCircleView view, ReadableMap center) {
            view.setCenter(center);
        }

        @ReactProp(name = "clickable")
        public void setClickable(HMSCircleView view, boolean clickable) {
            view.setClickable(clickable);
        }

        @ReactProp(name = "fillColor", defaultInt = Color.BLACK)
        public void setFillColor(HMSCircleView view, Dynamic color) {
            if (color.getType() == ReadableType.Array) {
                view.setFillColor(ReactUtils.getColorFromRgbaArray(color.asArray()));
            } else if (color.getType() == ReadableType.Number) {
                view.setFillColor(color.asInt());
            }
        }

        @ReactProp(name = "radius", defaultDouble = 0)
        public void setRadius(HMSCircleView view, double radius) {
            view.setRadius(radius);
        }

        @ReactProp(name = "strokeColor")
        public void setStrokeColor(HMSCircleView view, Dynamic color) {
            if (color.getType() == ReadableType.Array) {
                view.setStrokeColor(ReactUtils.getColorFromRgbaArray(color.asArray()));
            } else if (color.getType() == ReadableType.Number) {
                view.setStrokeColor(color.asInt());
            }
        }

        @ReactProp(name = "strokeWidth", defaultFloat = 1f)
        public void setStrokeWidth(HMSCircleView view, float strokeWidth) {
            view.setStrokeWidth(strokeWidth);
        }

        @ReactProp(name = "strokePattern")
        public void setStrokePattern(HMSCircleView view, ReadableArray strokePattern) {
            view.setStrokePattern(strokePattern);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(HMSCircleView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "zIndex", defaultFloat = 1.0f)
        public void setZIndex(HMSCircleView view, float zIndex) {
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
        if(mCircle == null) return;
        mCircle.remove();
        mCircle = null;
        mCircleOptions = null;
    }

    @Override
    public WritableMap getInfo() {
        if (mCircle == null){
            return null;
        }
        try {
            return ReactUtils.getWritableMapFromCircle(mCircle);
        } catch (NullPointerException e){
            return (WritableMap) null;
        }
    }

    @Override
    public WritableMap getOptionsInfo() {
        if (mCircleOptions == null){
            return null;
        }
        return ReactUtils.getWritableMapFromCircleOptions(mCircleOptions);
    }
}
