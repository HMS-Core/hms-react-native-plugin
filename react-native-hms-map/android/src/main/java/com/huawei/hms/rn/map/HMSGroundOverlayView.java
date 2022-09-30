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

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.GroundOverlay;
import com.huawei.hms.maps.model.GroundOverlayOptions;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.rn.map.logger.HMSLogger;
import com.huawei.hms.rn.map.utils.ReactUtils;
import com.huawei.hms.rn.map.utils.UriIconController;
import com.huawei.hms.rn.map.utils.UriIconView;

import static com.huawei.hms.rn.map.HMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.HMSMapView.MapLayerViewManager;

public class HMSGroundOverlayView extends MapLayerView implements UriIconView {
    private static final String TAG = HMSGroundOverlayView.class.getSimpleName();
    private static final String REACT_CLASS = HMSGroundOverlayView.class.getSimpleName();
    private GroundOverlayOptions mGroundOverlayOptions = new GroundOverlayOptions();
    private GroundOverlay mGroundOverlay;
    private boolean mOptionPositionSet = false;
    private final UriIconController uriIconController;

    public HMSGroundOverlayView(Context context) {
        super(context);
        uriIconController = new UriIconController(context, this);
    }

    @Override
    public void setUriIcon(BitmapDescriptor bitmapDescriptor, ReadableMap options) {
        mGroundOverlayOptions.image(bitmapDescriptor);
        if (mGroundOverlay != null) {
            mGroundOverlay.setImage(bitmapDescriptor);
        }
    }

    public static class Manager extends MapLayerViewManager<HMSGroundOverlayView> {
        private final HMSLogger logger;

        public Manager(Context context) {
            super();
            logger = HMSLogger.getInstance(context);
        }


        @NonNull
        @Override
        public String getName() {
            return "HMSGroundOverlayView";
        }

        @NonNull
        @Override
        public HMSGroundOverlayView createViewInstance(@NonNull ThemedReactContext context) {
            logger.startMethodExecutionTimer("HMSGroundOverlay");
            HMSGroundOverlayView view = new HMSGroundOverlayView(context);
            logger.sendSingleEvent("HMSGroundOverlay");
            return view;
        }

        @ReactProp(name = "anchor")
        public void setAnchor(HMSGroundOverlayView view, ReadableArray anchor) {
            view.setAnchor(anchor);
        }

        @ReactProp(name = "bearing")
        public void setBearing(HMSGroundOverlayView view, float bearing) {
            view.setBearing(bearing);
        }

        @ReactProp(name = "clickable", defaultBoolean = true)
        public void setClickable(HMSGroundOverlayView view, boolean clickable) {
            view.setClickable(clickable);
        }

        @ReactProp(name = "image")
        public void setImage(HMSGroundOverlayView view, ReadableMap image) {
            view.setImage(image);
        }

        @ReactProp(name = "coordinate")
        public void setPosition(HMSGroundOverlayView view, Dynamic position) {
            view.setPosition(position);
        }

        @ReactProp(name = "transparency")
        public void setTransparency(HMSGroundOverlayView view, float transparency) {
            view.setTransparency(transparency);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(HMSGroundOverlayView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "zIndex", defaultFloat = 1.0f)
        public void setZIndex(HMSGroundOverlayView view, float zIndex) {
            view.setZIndex(zIndex);
        }
    }

    public void setAnchor(ReadableArray anchor) {
        if (ReactUtils.hasValidElement(anchor, 0, ReadableType.Number)
                && ReactUtils.hasValidElement(anchor, 1, ReadableType.Number)) {
            float u = (float) anchor.getDouble(0);
            float v = (float) anchor.getDouble(1);
            mGroundOverlayOptions.anchor(u, v);
        }
    }

    public void setBearing(float bearing) {
        mGroundOverlayOptions.bearing(bearing);
        if (mGroundOverlay != null) {
            mGroundOverlay.setBearing(bearing);
        }
    }

    @Override
    public void setClickable(boolean clickable) {
        mGroundOverlayOptions.clickable(clickable);
        if (mGroundOverlay != null) {
            mGroundOverlay.setClickable(clickable);
        }
    }

    private void setImage(ReadableMap image) {
        BitmapDescriptor bitmapDescriptor = ReactUtils.getBitmapDescriptorFromReadableMap(image);
        setUriIcon(bitmapDescriptor, null);
        if(image.hasKey("uri")){
            uriIconController.setUriIcon(image);
        }
    }

    private void setPosition(Dynamic position) {
        if (position.getType() == ReadableType.Map) {
            ReadableMap positionMap = position.asMap();
            LatLng latLng = ReactUtils.getLatLngFromReadableMap(positionMap);
            if (latLng == null) {
                return;
            }
            if (ReactUtils.hasValidKey(positionMap, "width", ReadableType.Number)
                    && ReactUtils.hasValidKey(positionMap, "height", ReadableType.Number)) {
                float width = (float) positionMap.getDouble("width");
                float height = (float) positionMap.getDouble("height");
                if (!mOptionPositionSet) {
                    mOptionPositionSet = true;
                    mGroundOverlayOptions.position(latLng, width, height);
                } else {
                    if (mGroundOverlay != null) {
                        mGroundOverlay.setDimensions(width, height);
                    }
                }
            }
            if (mGroundOverlay != null) {
                mGroundOverlay.setPosition(latLng);
            }
        } else if (position.getType() == ReadableType.Array) {
            ReadableArray positionArray = position.asArray();
            LatLngBounds latLngBounds = ReactUtils.getLatLngBoundsFromReadableArray(positionArray);
            mGroundOverlayOptions.positionFromBounds(latLngBounds);
            if (mGroundOverlay != null) {
                mGroundOverlay.setPositionFromBounds(latLngBounds);
            }
        }
    }

    private void setTransparency(float transparency) {
        mGroundOverlayOptions.transparency(transparency);
        if (mGroundOverlay != null) {
            mGroundOverlay.setTransparency(transparency);
        }
    }

    private void setVisible(boolean visible) {
        mGroundOverlayOptions.visible(visible);
        if (mGroundOverlay != null) {
            mGroundOverlay.setVisible(visible);
        }
    }

    private void setZIndex(float zIndex) {
        mGroundOverlayOptions.zIndex(zIndex);
        if (mGroundOverlay != null) {
            mGroundOverlay.setZIndex(zIndex);
        }
    }

    @Override
    public GroundOverlay addTo(HuaweiMap huaweiMap) {
        mGroundOverlay = huaweiMap.addGroundOverlay(mGroundOverlayOptions);
        return mGroundOverlay;
    }

    @Override
    public void removeFrom(HuaweiMap huaweiMap) {
        if(mGroundOverlay == null) {
            return;
        }
        mGroundOverlay.remove();
        mGroundOverlay = null;
        mGroundOverlayOptions = null;
    }

    @Override
    public WritableMap getInfo() {
        if (mGroundOverlay == null){
            return null;
        }
        try {
            return ReactUtils.getWritableMapFromGroundOverlay(mGroundOverlay);
        } catch (NullPointerException e){
            return (WritableMap) null;
        }
    }

    @Override
    public WritableMap getOptionsInfo() {
        if (mGroundOverlayOptions == null){
            return null;
        }
        return ReactUtils.getWritableMapFromGroundOverlayOptions(mGroundOverlayOptions);
    }
}
