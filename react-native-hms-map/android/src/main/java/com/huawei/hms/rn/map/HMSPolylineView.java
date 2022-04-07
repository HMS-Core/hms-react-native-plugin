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
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.Cap;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.PatternItem;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;
import com.huawei.hms.rn.map.logger.HMSLogger;
import com.huawei.hms.rn.map.utils.ReactUtils;
import com.huawei.hms.rn.map.utils.UriIconController;
import com.huawei.hms.rn.map.utils.UriIconView;

import java.util.List;

import static com.huawei.hms.rn.map.HMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.HMSMapView.MapLayerViewManager;

public class HMSPolylineView extends MapLayerView implements UriIconView {
    private static final String TAG = HMSPolylineView.class.getSimpleName();
    private static final String REACT_CLASS = HMSPolylineView.class.getSimpleName();
    public PolylineOptions mPolylineOptions = new PolylineOptions();
    public Polyline mPolyline;
    private final UriIconController startCapUriIconController;
    private final UriIconController endCapUriIconController;

    public HMSPolylineView(Context context) {
        super(context);
        startCapUriIconController = new UriIconController(context, this);
        endCapUriIconController = new UriIconController(context, this);
    }


    public static class Manager extends MapLayerViewManager<HMSPolylineView> {
        private final HMSLogger logger;

        public Manager(Context context) {
            super();
            logger = HMSLogger.getInstance(context);
        }


        @NonNull
        @Override
        public String getName() {
            return "HMSPolylineView";
        }

        @NonNull
        @Override
        public HMSPolylineView createViewInstance(@NonNull ThemedReactContext context) {
            logger.startMethodExecutionTimer("HMSPolyline");
            HMSPolylineView view = new HMSPolylineView(context);
            logger.sendSingleEvent("HMSPolyline");
            return view;
        }

        @ReactProp(name = "clickable")
        public void setClickable(HMSPolylineView view, boolean clickable) {
            view.setClickable(clickable);
        }

        @ReactProp(name = "color", defaultInt = Color.BLACK)
        public void setColor(HMSPolylineView view, Dynamic color) {
            if (color.getType() == ReadableType.Array) {
                view.setColor(ReactUtils.getColorFromRgbaArray(color.asArray()));
            } else if (color.getType() == ReadableType.Number) {
                view.setColor(color.asInt());
            }

        }

        @ReactProp(name = "endCap")
        public void setEndCap(HMSPolylineView view, ReadableMap endCap) {
            view.setEndCap(endCap);
        }

        @ReactProp(name = "geodesic")
        public void setGeodesic(HMSPolylineView view, boolean geodesic) {
            view.setGeodesic(geodesic);
        }

        @ReactProp(name = "jointType")
        public void setJointType(HMSPolylineView view, int jointType) {
            view.setJointType(jointType);
        }

        @ReactProp(name = "pattern")
        public void setPattern(HMSPolylineView view, ReadableArray pattern) {
            view.setPattern(pattern);
        }

        @ReactProp(name = "points")
        public void setPoints(HMSPolylineView view, ReadableArray points) {
            view.setPoints(points);
        }

        @ReactProp(name = "startCap")
        public void setStartCap(HMSPolylineView view, ReadableMap startCap) {
            view.setStartCap(startCap);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(HMSPolylineView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "width", defaultFloat = 10f)
        public void setWidth(HMSPolylineView view, float width) {
            view.setWidth(width);
        }

        @ReactProp(name = "zIndex")
        public void setZIndex(HMSPolylineView view, float zIndex) {
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

    @Override
    synchronized public void setUriIcon(BitmapDescriptor bitmapDescriptor, ReadableMap options) {
        boolean isStartCap = options.getBoolean("isStartCap");
        Float refWidth = options.hasKey("refWidth") ? (float) options.getDouble("refWidth") : null;
        Cap customCap = ReactUtils.getCustomCapFromBitmapDescriptor(bitmapDescriptor, refWidth);

        if (isStartCap) {
            mPolylineOptions.startCap(customCap);
            if (mPolyline != null) {
                mPolyline.setStartCap(customCap);
            }
        } else {
            mPolylineOptions.endCap(customCap);
            if (mPolyline != null) {
                mPolyline.setEndCap(customCap);
            }
        }
    }

    private void setStartCap(ReadableMap startCapReadableMap) {
        Cap startCap = ReactUtils.getCapFromReadableMap(startCapReadableMap);
        mPolylineOptions.startCap(startCap);
        if (mPolyline != null) {
            mPolyline.setStartCap(startCap);
        }
        if (ReactUtils.hasValidKey(startCapReadableMap, "uri", ReadableType.String)) {
            WritableMap options = new WritableNativeMap();
            options.putBoolean("isStartCap", true);
            if(startCapReadableMap.hasKey("refWidth")){
                options.putDouble("refWidth", startCapReadableMap.getDouble("refWidth"));
            }
            startCapUriIconController.setUriIconWithOptions(startCapReadableMap, options);
        }
    }

    private void setEndCap(ReadableMap endCapReadableMap) {
        Cap endCap = ReactUtils.getCapFromReadableMap(endCapReadableMap);
        mPolylineOptions.endCap(endCap);
        if (mPolyline != null) {
            mPolyline.setEndCap(endCap);
        }

        if (ReactUtils.hasValidKey(endCapReadableMap, "uri", ReadableType.String)) {
            WritableMap options = new WritableNativeMap();
            options.putBoolean("isStartCap", false);
            if(endCapReadableMap.hasKey("refWidth")){
                options.putDouble("refWidth", endCapReadableMap.getDouble("refWidth"));
            }
            endCapUriIconController.setUriIconWithOptions(endCapReadableMap, options);
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
        if (mPolyline == null) return;
        mPolyline.remove();
        mPolyline = null;
        mPolylineOptions = null;
    }

    @Override
    public WritableMap getInfo() {
        if (mPolyline == null) {
            return null;
        }
        try {
            return ReactUtils.getWritableMapFromPolyline(mPolyline);
        } catch (NullPointerException e) {
            return (WritableMap) null;
        }
    }

    @Override
    public WritableMap getOptionsInfo() {
        if (mPolylineOptions == null) {
            return null;
        }
        return ReactUtils.getWritableMapFromPolylineOptions(mPolylineOptions);
    }
}
