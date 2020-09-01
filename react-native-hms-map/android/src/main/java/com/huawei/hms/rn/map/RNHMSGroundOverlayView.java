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

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.GroundOverlay;
import com.huawei.hms.maps.model.GroundOverlayOptions;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.rn.map.utils.ReactUtils;

import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerViewManager;

public class RNHMSGroundOverlayView extends MapLayerView {
    private static final String TAG = RNHMSGroundOverlayView.class.getSimpleName();
    private static final String REACT_CLASS = RNHMSGroundOverlayView.class.getSimpleName();
    private GroundOverlayOptions mGroundOverlayOptions = new GroundOverlayOptions();
    private GroundOverlay mGroundOverlay;
    private boolean mOptionPositionSet = false;

    public RNHMSGroundOverlayView(Context context) {
        super(context);
    }

    public static class Manager extends MapLayerViewManager<RNHMSGroundOverlayView> {
        @NonNull
        @Override
        public String getName() {
            return REACT_CLASS;
        }

        @NonNull
        @Override
        public RNHMSGroundOverlayView createViewInstance(@NonNull ThemedReactContext context) {
            return new RNHMSGroundOverlayView(context);
        }

        @ReactProp(name = "anchor")
        public void setAnchor(RNHMSGroundOverlayView view, ReadableArray anchor) {
            view.setAnchor(anchor);
        }

        @ReactProp(name = "bearing")
        public void setBearing(RNHMSGroundOverlayView view, float bearing) {
            view.setBearing(bearing);
        }

        @ReactProp(name = "clickable", defaultBoolean = true)
        public void setClickable(RNHMSGroundOverlayView view, boolean clickable) {
            view.setClickable(clickable);
        }

        @ReactProp(name = "image")
        public void setImage(RNHMSGroundOverlayView view, ReadableMap image) {
            view.setImage(image);
        }

        @ReactProp(name = "coordinate")
        public void setPosition(RNHMSGroundOverlayView view, Dynamic position) {
            view.setPosition(position);
        }

        @ReactProp(name = "transparency")
        public void setTransparency(RNHMSGroundOverlayView view, float transparency) {
            view.setTransparency(transparency);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(RNHMSGroundOverlayView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "zIndex", defaultFloat = 1.0f)
        public void setZIndex(RNHMSGroundOverlayView view, float zIndex) {
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
        mGroundOverlayOptions.image(bitmapDescriptor);
        if (mGroundOverlay != null) {
            mGroundOverlay.setImage(bitmapDescriptor);
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
        mGroundOverlay.remove();
    }
}
