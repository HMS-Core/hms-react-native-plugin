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
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.rn.map.utils.ReactUtils;

import java.util.HashMap;
import java.util.Map;

import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerViewManager;
import static com.huawei.hms.rn.map.RNHMSInfoWindowView.SizeLayoutShadowNode;

public class RNHMSMarkerView extends MapLayerView {
    private static final String TAG = RNHMSMarkerView.class.getSimpleName();
    private static final String REACT_CLASS = RNHMSMarkerView.class.getSimpleName();
    private MarkerOptions mMarkerOptions = new MarkerOptions();
    private Marker mMarker;
    private RNHMSInfoWindowView mInfoWindowView;
    private LinearLayout almostWrappedInfoWindowView;
    private LinearLayout wrappedInfoWindowView;

    public RNHMSMarkerView(Context context) {
        super(context);
    }

    public static class Manager extends MapLayerViewManager<RNHMSMarkerView> {
        @NonNull
        @Override
        public String getName() {
            return REACT_CLASS;
        }

        @NonNull
        @Override
        public RNHMSMarkerView createViewInstance(@NonNull ThemedReactContext context) {
            return new RNHMSMarkerView(context);
        }

        @Override
        public void addView(RNHMSMarkerView parent, View child, int index) {
            if (child instanceof RNHMSInfoWindowView) {
                parent.setInfoWindowView((RNHMSInfoWindowView) child);
            }
        }

        public enum Event implements ReactUtils.NamedEvent {
            CLICK("onClick"),
            DRAG_START("onDragStart"),
            DRAG("onDrag"),
            DRAG_END("onDragEnd"),
            INFO_WINDOW_CLICK("onInfoWindowClick"),
            INFO_WINDOW_CLOSE("onInfoWindowClose"),
            INFO_WINDOW_LONG_CLICK("onInfoWindowLongClick");

            private String markerEventName;

            Event(String markerEventName) {
                this.markerEventName = markerEventName;
            }

            public String getName() {
                return markerEventName;
            }
        }

        @Nullable
        @Override
        public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
            return ReactUtils.getExportedCustomDirectEventTypeConstantsFromEvents(Event.values());
        }

        public enum Command implements ReactUtils.NamedCommand {
            SHOW_INFO_WINDOW("showInfoWindow"),
            HIDE_INFO_WINDOW("hideInfoWindow");

            private String markerCommandName;

            Command(String markerCommandName) {
                this.markerCommandName = markerCommandName;
            }

            public String getName() {
                return markerCommandName;
            }
        }

        @Override
        public void receiveCommand(
                @NonNull RNHMSMarkerView root, String commandId, @androidx.annotation.Nullable ReadableArray args) {
            ReactUtils.NamedCommand command = ReactUtils.getCommand(commandId, Command.values());
            assert command != null;
            if (command instanceof Command) {
                switch ((Command) command) {
                    case SHOW_INFO_WINDOW:
                        root.showInfoWindow();
                        break;
                    case HIDE_INFO_WINDOW:
                        root.hideInfoWindow();
                        break;
                    default:
                        break;
                }
            }
        }

        @ReactProp(name = "alpha", defaultFloat = 1.0f)
        public void setAlpha(RNHMSMarkerView view, float alpha) {
            view.setAlpha(alpha);
        }

        @ReactProp(name = "markerAnchor")
        public void setMarkerAnchor(RNHMSMarkerView view, ReadableArray markerAnchor) {
            view.setMarkerAnchor(markerAnchor);
        }

        @ReactProp(name = "draggable")
        public void setDraggable(RNHMSMarkerView view, boolean draggable) {
            view.setDraggable(draggable);
        }

        @ReactProp(name = "flat")
        public void setFlat(RNHMSMarkerView view, boolean flat) {
            view.setFlat(flat);
        }

        @ReactProp(name = "icon")
        public void setIcon(RNHMSMarkerView view, ReadableMap icon) {
            view.setIcon(icon);
        }

        @ReactProp(name = "infoWindowAnchor")
        public void setInfoWindowAnchor(RNHMSMarkerView view, ReadableArray infoWindowAnchor) {
            view.setInfoWindowAnchor(infoWindowAnchor);
        }

        @ReactProp(name = "coordinate")
        public void setPosition(RNHMSMarkerView view, ReadableMap position) {
            view.setPosition(position);
        }

        @ReactProp(name = "rotation")
        public void setRotation(RNHMSMarkerView view, float rotation) {
            view.setRotation(rotation);
        }

        @ReactProp(name = "snippet")
        public void setSnippet(RNHMSMarkerView view, String snippet) {
            view.setSnippet(snippet);
        }

        @ReactProp(name = "title")
        public void setTitle(RNHMSMarkerView view, String title) {
            view.setTitle(title);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(RNHMSMarkerView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "clusterable")
        public void setClusterable(RNHMSMarkerView view, boolean clusterable) {
            view.setClusterable(clusterable);
        }

        @ReactProp(name = "zIndex")
        public void setZIndex(RNHMSMarkerView view, float zIndex) {
            view.setZIndex(zIndex);
        }

        @Override
        public LayoutShadowNode createShadowNodeInstance() {
            return new SizeLayoutShadowNode();
        }

        @Override
        public void updateExtraData(RNHMSMarkerView view, Object extraData) {
            RNHMSInfoWindowView infoWindow = view.getInfoWindowView();
            if (infoWindow != null) {
                view.wrapInfoWindowView();
            }
        }
    }

    public RNHMSInfoWindowView getInfoWindowView() {
        return mInfoWindowView;
    }

    public void setInfoWindowView(RNHMSInfoWindowView infoWindowView) {
        mInfoWindowView = infoWindowView;
    }

    public void wrapInfoWindowView() {
        RNHMSInfoWindowView infoWindowView = getInfoWindowView();
        if (infoWindowView != null) {
            if (wrappedInfoWindowView != null) {
                wrappedInfoWindowView.removeAllViews();
                almostWrappedInfoWindowView.removeAllViews();
            } else {
                wrappedInfoWindowView = new LinearLayout(infoWindowView.getContext());
                almostWrappedInfoWindowView = new LinearLayout(infoWindowView.getContext());
                wrappedInfoWindowView.setOrientation(LinearLayout.VERTICAL);
                wrappedInfoWindowView.setLayoutParams(new LinearLayout.LayoutParams(
                        infoWindowView.width,
                        infoWindowView.height,
                        0f
                ));

                almostWrappedInfoWindowView.setOrientation(LinearLayout.HORIZONTAL);
                almostWrappedInfoWindowView.setLayoutParams(new LinearLayout.LayoutParams(
                        infoWindowView.width,
                        infoWindowView.height,
                        0f
                ));
            }

            wrappedInfoWindowView.addView(almostWrappedInfoWindowView);
            almostWrappedInfoWindowView.addView(infoWindowView);
        }
    }

    public LinearLayout getWrappedInfoWindowView() {
        return wrappedInfoWindowView;
    }

    private void showInfoWindow() {
        if (mMarker != null) {
            mMarker.showInfoWindow();
        }
    }

    private void hideInfoWindow() {
        if (mMarker != null) {
            mMarker.hideInfoWindow();
        }
    }

    @Override
    public void setAlpha(float alpha) {
        mMarkerOptions.alpha(alpha);
        if (mMarker != null) {
            mMarker.setAlpha(alpha);
        }
    }

    private void setMarkerAnchor(ReadableArray markerAnchor) {
        if (ReactUtils.hasValidElement(markerAnchor, 0, ReadableType.Number)
                && ReactUtils.hasValidElement(markerAnchor, 1, ReadableType.Number)) {
            float u = (float) markerAnchor.getDouble(0);
            float v = (float) markerAnchor.getDouble(1);
            mMarkerOptions.anchorMarker(u, v);
            if (mMarker != null) {
                mMarker.setMarkerAnchor(u, v);
            }
        }
    }

    private void setDraggable(boolean draggable) {
        mMarkerOptions.draggable(draggable);
        if (mMarker != null) {
            mMarker.setDraggable(draggable);
        }
    }

    private void setFlat(boolean flat) {
        mMarkerOptions.flat(flat);
        if (mMarker != null) {
            mMarker.setFlat(flat);
        }
    }

    private void setIcon(ReadableMap icon) {
        BitmapDescriptor bitmapDescriptor = ReactUtils.getBitmapDescriptorFromReadableMap(icon);
        mMarkerOptions.icon(bitmapDescriptor);
        if (mMarker != null) {
            mMarker.setIcon(bitmapDescriptor);
        }
    }

    private void setInfoWindowAnchor(ReadableArray infoWindowAnchor) {
        if (ReactUtils.hasValidElement(infoWindowAnchor, 0, ReadableType.Number)
                && ReactUtils.hasValidElement(infoWindowAnchor, 1, ReadableType.Number)) {
            float u = (float) infoWindowAnchor.getDouble(0);
            float v = (float) infoWindowAnchor.getDouble(1);
            mMarkerOptions.infoWindowAnchor(u, v);
            if (mMarker != null) {
                mMarker.setInfoWindowAnchor(u, v);
            }
        }
    }

    private void setPosition(ReadableMap position) {
        LatLng mPosition = ReactUtils.getLatLngFromReadableMap(position);
        if (mPosition == null) {
            return;
        }
        mMarkerOptions.position(mPosition);
        if (mMarker != null) {
            mMarker.setPosition(mPosition);
        }
    }

    @Override
    public void setRotation(float rotation) {
        mMarkerOptions.rotation(rotation);
        if (mMarker != null) {
            mMarker.setRotation(rotation);
        }
    }

    private void setSnippet(String snippet) {
        mMarkerOptions.snippet(snippet);
        if (mMarker != null) {
            mMarker.setSnippet(snippet);
        }
    }

    private void setTitle(String title) {
        mMarkerOptions.title(title);
        if (mMarker != null) {
            mMarker.setTitle(title);
        }
    }

    private void setVisible(boolean visible) {
        mMarkerOptions.visible(visible);
        if (mMarker != null) {
            mMarker.setVisible(visible);
        }
    }

    private void setZIndex(float zIndex) {
        mMarkerOptions.zIndex(zIndex);
        if (mMarker != null) {
            mMarker.setZIndex(zIndex);
        }
    }

    private void setClusterable(boolean clusterable) {
        mMarkerOptions.clusterable(clusterable);
    }

    @Override
    public Marker addTo(HuaweiMap huaweiMap) {
        mMarker = huaweiMap.addMarker(mMarkerOptions);
        return mMarker;
    }

    @Override
    public void removeFrom(HuaweiMap huaweiMap) {
        try { // Simple trick to avoid errors
            mMarker.getPosition();
            mMarker.remove();
        } catch (NullPointerException e) {
            mMarker = null;
        }
    }
}
