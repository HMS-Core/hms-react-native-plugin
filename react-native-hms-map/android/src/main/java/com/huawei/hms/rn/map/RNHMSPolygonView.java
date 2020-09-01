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
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.Polygon;
import com.huawei.hms.maps.model.PolygonOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.PatternItem;
import com.huawei.hms.rn.map.utils.ReactUtils;

import java.util.List;

import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerViewManager;

public class RNHMSPolygonView extends MapLayerView {
    private static final String TAG = RNHMSPolygonView.class.getSimpleName();
    private static final String REACT_CLASS = RNHMSPolygonView.class.getSimpleName();
    public PolygonOptions mOptions = new PolygonOptions();
    public Polygon mLayer;

    public RNHMSPolygonView(Context context) {
        super(context);
    }

    public static class Manager extends MapLayerViewManager<RNHMSPolygonView> {
        @NonNull
        @Override
        public String getName() {
            return REACT_CLASS;
        }

        @NonNull
        @Override
        public RNHMSPolygonView createViewInstance(@NonNull ThemedReactContext context) {
            return new RNHMSPolygonView(context);
        }

        @ReactProp(name = "clickable")
        public void setClickable(RNHMSPolygonView view, boolean clickable) {
            view.setClickable(clickable);
        }

        @ReactProp(name = "fillColor")
        public void setFillColor(RNHMSPolygonView view, int fillColor) {
            view.setFillColor(fillColor);
        }

        @ReactProp(name = "geodesic")
        public void setGeodesic(RNHMSPolygonView view, boolean geodesic) {
            view.setGeodesic(geodesic);
        }

        @ReactProp(name = "holes") // [{type: 0, length:20}]
        public void setHoles(RNHMSPolygonView view, ReadableArray holes) {
            view.setHoles(holes);
        }

        @ReactProp(name = "points") // [{type: 0, length:20}]
        public void setPoints(RNHMSPolygonView view, ReadableArray points) {
            view.setPoints(points);
        }

        @ReactProp(name = "strokeColor", defaultInt = Color.BLACK)
        public void setStrokeColor(RNHMSPolygonView view, int strokeColor) {
            view.setStrokeColor(strokeColor);
        }

        @ReactProp(name = "strokeJointType")
        public void setStrokeJointType(RNHMSPolygonView view, int strokeJointType) {
            view.setStrokeJointType(strokeJointType);
        }

        @ReactProp(name = "strokeWidth", defaultFloat = 10.0f)
        public void setStrokeWidth(RNHMSPolygonView view, float strokeWidth) {
            view.setStrokeWidth(strokeWidth);
        }

        @ReactProp(name = "strokePattern") // [{type: 0, length:20}]
        public void setStrokePattern(RNHMSPolygonView view, ReadableArray strokePattern) {
            view.setStrokePattern(strokePattern);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(RNHMSPolygonView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "zIndex")
        public void setZIndex(RNHMSPolygonView view, float zIndex) {
            view.setZIndex(zIndex);
        }
    }

    @Override
    public void setClickable(boolean clickable) {
        mOptions.clickable(clickable);
        if (mLayer != null) {
            mLayer.setClickable(clickable);
        }
    }

    private void setFillColor(int fillColor) {
        mOptions.fillColor(fillColor);
        if (mLayer != null) {
            mLayer.setFillColor(fillColor);
        }
    }

    private void setGeodesic(boolean geodesic) {
        mOptions.geodesic(geodesic);
        if (mLayer != null) {
            mLayer.setGeodesic(geodesic);
        }
    }

    private void setPoints(ReadableArray pointsReadableArray) {
        List<LatLng> points = ReactUtils.getLatLngListFromReadableArray(pointsReadableArray);
        for (LatLng latLng : points) {
            mOptions.add(latLng);
        }
        if (mLayer != null) {
            mLayer.setPoints(points);
        }
    }

    private void setHoles(ReadableArray holesReadableArray) {
        List<List<LatLng>> holes = ReactUtils.getListOfLatLngListFromReadableArray(holesReadableArray);
        for (List<LatLng> latLngList : holes) {
            mOptions.addHole(latLngList);
        }
        if (mLayer != null) {
            mLayer.setHoles(holes);
        }
    }

    private void setStrokeColor(int strokeColor) {
        mOptions.strokeColor(strokeColor);
        if (mLayer != null) {
            mLayer.setStrokeColor(strokeColor);
        }
    }

    private void setStrokeJointType(int strokeJointType) {
        mOptions.strokeJointType(strokeJointType);
        if (mLayer != null) {
            mLayer.setStrokeJointType(strokeJointType);
        }
    }

    private void setStrokePattern(ReadableArray strokePatternReadableArray) {
        List<PatternItem> strokePattern = ReactUtils.getPatternItemListFromReadableArray(strokePatternReadableArray);
        mOptions.strokePattern(strokePattern);
        if (mLayer != null) {
            mLayer.setStrokePattern(strokePattern);
        }
    }

    private void setStrokeWidth(float strokeWidth) {
        mOptions.strokeWidth(strokeWidth);
        if (mLayer != null) {
            mLayer.setStrokeWidth(strokeWidth);
        }
    }

    private void setVisible(boolean visible) {
        mOptions.visible(visible);
        if (mLayer != null) {
            mLayer.setVisible(visible);
        }
    }

    private void setZIndex(float zIndex) {
        mOptions.zIndex(zIndex);
        if (mLayer != null) {
            mLayer.setZIndex(zIndex);
        }
    }

    @Override
    public Polygon addTo(HuaweiMap huaweiMap) {
        mLayer = huaweiMap.addPolygon(mOptions);
        return mLayer;
    }

    @Override
    public void removeFrom(HuaweiMap huaweiMap) {
        mLayer.remove();
    }
}
