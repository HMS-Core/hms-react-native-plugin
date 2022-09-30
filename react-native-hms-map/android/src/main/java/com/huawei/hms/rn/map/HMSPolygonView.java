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
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.Polygon;
import com.huawei.hms.maps.model.PolygonOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.PatternItem;
import com.huawei.hms.rn.map.logger.HMSLogger;
import com.huawei.hms.rn.map.utils.ReactUtils;

import java.util.List;

import static com.huawei.hms.rn.map.HMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.HMSMapView.MapLayerViewManager;

public class HMSPolygonView extends MapLayerView {
    private static final String TAG = HMSPolygonView.class.getSimpleName();
    private static final String REACT_CLASS = HMSPolygonView.class.getSimpleName();
    public PolygonOptions mPolygonOptions = new PolygonOptions();
    public Polygon mPolygon;

    public HMSPolygonView(Context context) {
        super(context);
    }

    public static class Manager extends MapLayerViewManager<HMSPolygonView> {
        private HMSLogger logger;

        public Manager(Context context) {
            super();
            logger = HMSLogger.getInstance(context);
        }


        @NonNull
        @Override
        public String getName() {
            return "HMSPolygonView";
        }

        @NonNull
        @Override
        public HMSPolygonView createViewInstance(@NonNull ThemedReactContext context) {
            logger.startMethodExecutionTimer("HMSPolygon");
            HMSPolygonView view = new HMSPolygonView(context);
            logger.sendSingleEvent("HMSPolygon");
            return view;
        }

        @ReactProp(name = "clickable")
        public void setClickable(HMSPolygonView view, boolean clickable) {
            view.setClickable(clickable);
        }

        @ReactProp(name = "fillColor")
        public void setFillColor(HMSPolygonView view, Dynamic color) {
            if (color.getType() == ReadableType.Array) {
                view.setFillColor(ReactUtils.getColorFromRgbaArray(color.asArray()));
            } else if (color.getType() == ReadableType.Number) {
                view.setFillColor(color.asInt());
            }
        }

        @ReactProp(name = "geodesic")
        public void setGeodesic(HMSPolygonView view, boolean geodesic) {
            view.setGeodesic(geodesic);
        }

        @ReactProp(name = "holes")
        public void setHoles(HMSPolygonView view, ReadableArray holes) {
            view.setHoles(holes);
        }

        @ReactProp(name = "points")
        public void setPoints(HMSPolygonView view, ReadableArray points) {
            view.setPoints(points);
        }

        @ReactProp(name = "strokeColor", defaultInt = Color.BLACK)
        public void setStrokeColor(HMSPolygonView view, Dynamic color) {
            if (color.getType() == ReadableType.Array) {
                view.setStrokeColor(ReactUtils.getColorFromRgbaArray(color.asArray()));
            } else if (color.getType() == ReadableType.Number) {
                view.setStrokeColor(color.asInt());
            }
        }

        @ReactProp(name = "strokeJointType")
        public void setStrokeJointType(HMSPolygonView view, int strokeJointType) {
            view.setStrokeJointType(strokeJointType);
        }

        @ReactProp(name = "strokeWidth", defaultFloat = 10.0f)
        public void setStrokeWidth(HMSPolygonView view, float strokeWidth) {
            view.setStrokeWidth(strokeWidth);
        }

        @ReactProp(name = "strokePattern")
        public void setStrokePattern(HMSPolygonView view, ReadableArray strokePattern) {
            view.setStrokePattern(strokePattern);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(HMSPolygonView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "zIndex")
        public void setZIndex(HMSPolygonView view, float zIndex) {
            view.setZIndex(zIndex);
        }
    }

    @Override
    public void setClickable(boolean clickable) {
        mPolygonOptions.clickable(clickable);
        if (mPolygon != null) {
            mPolygon.setClickable(clickable);
        }
    }

    private void setFillColor(int fillColor) {
        mPolygonOptions.fillColor(fillColor);
        if (mPolygon != null) {
            mPolygon.setFillColor(fillColor);
        }
    }

    private void setGeodesic(boolean geodesic) {
        mPolygonOptions.geodesic(geodesic);
        if (mPolygon != null) {
            mPolygon.setGeodesic(geodesic);
        }
    }

    private void setPoints(ReadableArray pointsReadableArray) {
        List<LatLng> points = ReactUtils.getLatLngListFromReadableArray(pointsReadableArray);
        for (LatLng latLng : points) {
            mPolygonOptions.add(latLng);
        }
        if (mPolygon != null) {
            mPolygon.setPoints(points);
        }
    }

    private void setHoles(ReadableArray holesReadableArray) {
        List<List<LatLng>> holes = ReactUtils.getListOfLatLngListFromReadableArray(holesReadableArray);
        for (List<LatLng> latLngList : holes) {
            mPolygonOptions.addHole(latLngList);
        }
        if (mPolygon != null) {
            mPolygon.setHoles(holes);
        }
    }

    private void setStrokeColor(int strokeColor) {
        mPolygonOptions.strokeColor(strokeColor);
        if (mPolygon != null) {
            mPolygon.setStrokeColor(strokeColor);
        }
    }

    private void setStrokeJointType(int strokeJointType) {
        mPolygonOptions.strokeJointType(strokeJointType);
        if (mPolygon != null) {
            mPolygon.setStrokeJointType(strokeJointType);
        }
    }

    private void setStrokePattern(ReadableArray strokePatternReadableArray) {
        List<PatternItem> strokePattern = ReactUtils.getPatternItemListFromReadableArray(strokePatternReadableArray);
        mPolygonOptions.strokePattern(strokePattern);
        if (mPolygon != null) {
            mPolygon.setStrokePattern(strokePattern);
        }
    }

    private void setStrokeWidth(float strokeWidth) {
        mPolygonOptions.strokeWidth(strokeWidth);
        if (mPolygon != null) {
            mPolygon.setStrokeWidth(strokeWidth);
        }
    }

    private void setVisible(boolean visible) {
        mPolygonOptions.visible(visible);
        if (mPolygon != null) {
            mPolygon.setVisible(visible);
        }
    }

    private void setZIndex(float zIndex) {
        mPolygonOptions.zIndex(zIndex);
        if (mPolygon != null) {
            mPolygon.setZIndex(zIndex);
        }
    }

    @Override
    public Polygon addTo(HuaweiMap huaweiMap) {
        mPolygon = huaweiMap.addPolygon(mPolygonOptions);
        return mPolygon;
    }

    @Override
    public void removeFrom(HuaweiMap huaweiMap) {
        if(mPolygon == null) {
            return;
        }
        mPolygon.remove();
        mPolygon = null;
        mPolygonOptions = null;
    }

    @Override
    public WritableMap getInfo() {
        if (mPolygon == null){
            return null;
        }
        try {
            return ReactUtils.getWritableMapFromPolygon(mPolygon);
        } catch (NullPointerException e){
            return (WritableMap) null;
        }
    }

    @Override
    public WritableMap getOptionsInfo() {
        if (mPolygonOptions == null){
            return null;
        }
        return ReactUtils.getWritableMapFromPolygonOptions(mPolygonOptions);
    }
}
