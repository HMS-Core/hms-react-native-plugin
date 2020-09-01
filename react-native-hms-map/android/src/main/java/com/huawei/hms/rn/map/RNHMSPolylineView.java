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

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.Cap;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.PatternItem;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;
import com.huawei.hms.rn.map.utils.ReactUtils;

import java.util.List;

import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerViewManager;

public class RNHMSPolylineView extends MapLayerView {
    private static final String TAG = RNHMSPolylineView.class.getSimpleName();
    private static final String REACT_CLASS = RNHMSPolylineView.class.getSimpleName();
    public PolylineOptions mPolylineOptions = new PolylineOptions();
    public Polyline mPolyline;

    public RNHMSPolylineView(Context context) {
        super(context);
    }

    public static class Manager extends MapLayerViewManager<RNHMSPolylineView> {
        @NonNull
        @Override
        public String getName() {
            return REACT_CLASS;
        }

        @NonNull
        @Override
        public RNHMSPolylineView createViewInstance(@NonNull ThemedReactContext context) {
            return new RNHMSPolylineView(context);
        }

        @ReactProp(name = "clickable")
        public void setClickable(RNHMSPolylineView view, boolean clickable) {
            view.setClickable(clickable);
        }

        @ReactProp(name = "color", defaultInt = Color.BLACK)
        public void setColor(RNHMSPolylineView view, int color) {
            view.setColor(color);
        }

        @ReactProp(name = "endCap")
        public void setEndCap(RNHMSPolylineView view, ReadableMap endCap) {
            view.setEndCap(endCap);
        }

        @ReactProp(name = "geodesic")
        public void setGeodesic(RNHMSPolylineView view, boolean geodesic) {
            view.setGeodesic(geodesic);
        }

        @ReactProp(name = "jointType")
        public void setJointType(RNHMSPolylineView view, int jointType) {
            view.setJointType(jointType);
        }

        @ReactProp(name = "pattern") // [{type: 0, length:20}]
        public void setPattern(RNHMSPolylineView view, ReadableArray pattern) {
            view.setPattern(pattern);
        }

        @ReactProp(name = "points")
        public void setPoints(RNHMSPolylineView view, ReadableArray points) {
            view.setPoints(points);
        }

        @ReactProp(name = "startCap")
        public void setStartCap(RNHMSPolylineView view, ReadableMap startCap) {
            view.setStartCap(startCap);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(RNHMSPolylineView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "width", defaultFloat = 10f)
        public void setWidth(RNHMSPolylineView view, float width) {
            view.setWidth(width);
        }

        @ReactProp(name = "zIndex")
        public void setZIndex(RNHMSPolylineView view, float zIndex) {
            view.setZIndex(zIndex);
        }
    }

    @Override
    public void setClickable(boolean clickable) {
        mPolylineOptions.clickable(clickable);
        if (mPolyline != null) {
            mPolyline.setClickable(clickable);
        }
    }

    private void setColor(int color) {
        mPolylineOptions.color(color);
        if (mPolyline != null) {
            mPolyline.setColor(color);
        }
    }

    private void setEndCap(ReadableMap endCapReadableMap) {
        Cap endCap = ReactUtils.getCapFromReadableMap(endCapReadableMap);
        mPolylineOptions.endCap(endCap);
        if (mPolyline != null) {
            mPolyline.setEndCap(endCap);
        }
    }

    private void setGeodesic(boolean geodesic) {
        mPolylineOptions.geodesic(geodesic);
        if (mPolyline != null) {
            mPolyline.setGeodesic(geodesic);
        }
    }

    private void setJointType(int jointType) {
        mPolylineOptions.jointType(jointType);
        if (mPolyline != null) {
            mPolyline.setJointType(jointType);
        }
    }

    private void setPattern(ReadableArray patternReadableArray) {
        List<PatternItem> pattern = ReactUtils.getPatternItemListFromReadableArray(patternReadableArray);
        mPolylineOptions.pattern(pattern);
        if (mPolyline != null) {
            mPolyline.setPattern(pattern);
        }
    }

    private void setPoints(ReadableArray pointsReadableArray) {
        List<LatLng> points = ReactUtils.getLatLngListFromReadableArray(pointsReadableArray);
        for (LatLng latLng : points) {
            mPolylineOptions.add(latLng);
        }
        if (mPolyline != null) {
            mPolyline.setPoints(points);
        }
    }

    private void setStartCap(ReadableMap startCapReadableMap) {
        Cap startCap = ReactUtils.getCapFromReadableMap(startCapReadableMap);
        mPolylineOptions.startCap(startCap);
        if (mPolyline != null) {
            mPolyline.setStartCap(startCap);
        }
    }

    private void setVisible(boolean visible) {
        mPolylineOptions.visible(visible);
        if (mPolyline != null) {
            mPolyline.setVisible(visible);
        }
    }

    private void setWidth(float width) {
        mPolylineOptions.width(width);
        if (mPolyline != null) {
            mPolyline.setWidth(width);
        }
    }

    private void setZIndex(float zIndex) {
        mPolylineOptions.zIndex(zIndex);
        if (mPolyline != null) {
            mPolyline.setZIndex(zIndex);
        }
    }

    @Override
    public Polyline addTo(HuaweiMap huaweiMap) {
        mPolyline = huaweiMap.addPolyline(mPolylineOptions);
        return mPolyline;
    }

    @Override
    public void removeFrom(HuaweiMap huaweiMap) {
        mPolyline.remove();
    }

}
