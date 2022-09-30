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

import androidx.annotation.NonNull;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.HeatMap;
import com.huawei.hms.maps.model.HeatMapOptions;
import com.huawei.hms.rn.map.logger.HMSLogger;
import com.huawei.hms.rn.map.utils.ReactUtils;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.security.SecureRandom;

public class HMSHeatMapView extends HMSMapView.MapLayerView {
    private static final SecureRandom RANDOM = new SecureRandom();

    private HeatMapOptions mHeatMapOptions = new HeatMapOptions();

    private HeatMap mHeatMap;

    HMSLogger logger;

    public HMSHeatMapView(Context context) {
        super(context);
        logger = HMSLogger.getInstance(context);
    }

    public static class Manager extends HMSMapView.MapLayerViewManager<HMSHeatMapView> {
        private HMSLogger logger;

        public Manager(Context context) {
            super();
            logger = HMSLogger.getInstance(context);
        }

        @NonNull
        @Override
        public String getName() {
            return "HMSHeatMapView";
        }

        @NonNull
        @Override
        public HMSHeatMapView createViewInstance(@NonNull ThemedReactContext context) {
            logger.startMethodExecutionTimer("HMSHeatMap");
            HMSHeatMapView view = new HMSHeatMapView(context);
            logger.sendSingleEvent("HMSHeatMap");
            return view;
        }

        @ReactProp(name = "dataSet")
        public void setDataSet(HMSHeatMapView view, String dataSet) {
            view.setDataSet(dataSet);
        }

        @ReactProp(name = "intensity")
        public void setIntensity(HMSHeatMapView view, Dynamic intensity) {
            if (intensity.getType() == ReadableType.Number) {
                view.setIntensity(intensity.asDouble());
            } else if (intensity.getType() == ReadableType.Map) {
                view.setIntensity(intensity.asMap());
            }
        }

        @ReactProp(name = "opacity")
        public void setOpacity(HMSHeatMapView view, Dynamic opacity) {
            if (opacity.getType() == ReadableType.Number) {
                view.setOpacity(opacity.asDouble());
            } else if (opacity.getType() == ReadableType.Map) {
                view.setOpacity(opacity.asMap());
            }
        }

        @ReactProp(name = "radius")
        public void setRadius(HMSHeatMapView view, Dynamic radius) {
            if (radius.getType() == ReadableType.Number) {
                view.setRadius(radius.asDouble());
            } else if (radius.getType() == ReadableType.Map) {
                view.setRadius(radius.asMap());
            }
        }

        @ReactProp(name = "radiusUnit")
        public void setRadiusUnit(HMSHeatMapView view, String radiusUnit) {
            view.setRadiusUnit(radiusUnit);
        }

        @ReactProp(name = "isVisible")
        public void setVisible(HMSHeatMapView view, boolean isVisible) {
            view.setVisible(isVisible);
        }

        @ReactProp(name = "resourceId")
        public void setResourceId(HMSHeatMapView view, int resourceId) {
            view.setResourceId(resourceId);
        }

        @ReactProp(name = "color")
        public void setColor(HMSHeatMapView view, ReadableMap colorMap) {
            view.setColor(colorMap);
        }
    }

    private void setDataSet(String dataSet) {
        mHeatMapOptions.dataSet(dataSet);
    }

    private void setColor(ReadableMap colorMap) {
        mHeatMapOptions.color(ReactUtils.toFloatIntegerMap(colorMap));
        if (mHeatMap != null) {
            mHeatMap.setColor(ReactUtils.toFloatIntegerMap(colorMap));
        }
    }

    private void setIntensity(double intensity) {
        mHeatMapOptions.intensity((float) intensity);
        if (mHeatMap != null) {
            mHeatMap.setIntensity((float) intensity);
        }
    }

    private void setIntensity(ReadableMap intensityMap) {
        mHeatMapOptions.intensity(ReactUtils.toFloatMap(intensityMap));
        if (mHeatMap != null) {
            mHeatMap.setIntensity(ReactUtils.toFloatMap(intensityMap));
        }
    }

    private void setOpacity(double opacity) {
        mHeatMapOptions.opacity((float) opacity);
        if (mHeatMap != null) {
            mHeatMap.setOpacity((float) opacity);
        }
    }

    private void setOpacity(ReadableMap opacityMap) {
        mHeatMapOptions.opacity(ReactUtils.toFloatMap(opacityMap));
        if (mHeatMap != null) {
            mHeatMap.setOpacity(ReactUtils.toFloatMap(opacityMap));
        }
    }

    private void setRadius(double radius) {
        mHeatMapOptions.radius((float) radius);
        if (mHeatMap != null) {
            mHeatMap.setRadius((float) radius);
        }
    }

    private void setRadius(ReadableMap radiusMap) {
        mHeatMapOptions.radius(ReactUtils.toFloatMap(radiusMap));
        if (mHeatMap != null) {
            mHeatMap.setRadius(ReactUtils.toFloatMap(radiusMap));
        }
    }

    private void setRadiusUnit(String radiusUnit) {
        mHeatMapOptions.radiusUnit(ReactUtils.getRadiusUnitFromString(radiusUnit));
        if (mHeatMap != null) {
            mHeatMap.setRadiusUnit(ReactUtils.getRadiusUnitFromString(radiusUnit));
        }
    }

    private void setVisible(boolean isVisible) {
        mHeatMapOptions.visible(isVisible);
        if (mHeatMap != null) {
            mHeatMap.setVisible(isVisible);
        }
    }

    private void setResourceId(int resourceId) {
        mHeatMapOptions.setResourceId(resourceId);
    }

    public static String generateID() {
        return String.valueOf(RANDOM.nextInt());
    }

    @Override
    public HeatMap addTo(HuaweiMap huaweiMap) {
        logger.startMethodExecutionTimer("addTo");
        mHeatMap = huaweiMap.addHeatMap(generateID(), mHeatMapOptions);
        logger.sendSingleEvent("addTo");
        return mHeatMap;
    }

    @Override
    public void removeFrom(HuaweiMap huaweiMap) {
        if (mHeatMap == null) {
            return;
        }
        logger.startMethodExecutionTimer("removeFrom");
        mHeatMap.remove();
        logger.sendSingleEvent("removeFrom");
        mHeatMap = null;
        mHeatMapOptions = null;
    }

    @Override
    public WritableMap getInfo() {
        if (mHeatMap == null) {
            return null;
        }
        try {
            return ReactUtils.getWritableMapFromHeatMap(mHeatMap);
        } catch (NullPointerException e) {
            return (WritableMap) null;
        }
    }

    @Override
    public WritableMap getOptionsInfo() {
        if (mHeatMapOptions == null) {
            return null;
        }
        return ReactUtils.getWritableMapFromHeatMapOptions(mHeatMapOptions);
    }
}
