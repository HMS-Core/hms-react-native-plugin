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
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.model.animation.Animation;
import com.huawei.hms.maps.model.animation.AnimationSet;
import com.huawei.hms.rn.map.logger.HMSLogger;
import com.huawei.hms.rn.map.utils.ReactUtils;
import com.huawei.hms.rn.map.utils.UriIconController;
import com.huawei.hms.rn.map.utils.UriIconView;

import java.util.Map;

import static com.huawei.hms.rn.map.HMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.HMSMapView.MapLayerViewManager;
import static com.huawei.hms.rn.map.HMSInfoWindowView.SizeLayoutShadowNode;

public class HMSMarkerView extends MapLayerView implements UriIconView {
    private static final String TAG = HMSMarkerView.class.getSimpleName();
    private static final String REACT_CLASS = HMSMarkerView.class.getSimpleName();
    private MarkerOptions mMarkerOptions = new MarkerOptions();
    private Marker mMarker;
    private HMSInfoWindowView mInfoWindowView;
    private LinearLayout almostWrappedInfoWindowView;
    private LinearLayout wrappedInfoWindowView;
    private AnimationSet animationSet;
    public boolean defaultActionOnClick = true;
    private final UriIconController uriIconController;

    HMSLogger logger;

    public HMSMarkerView(Context context) {
        super(context);
        logger = HMSLogger.getInstance(context);
        uriIconController = new UriIconController(context, this);
    }

    public static class Manager extends MapLayerViewManager<HMSMarkerView> {
        private final HMSLogger logger;

        public Manager(Context context) {
            super();
            logger = HMSLogger.getInstance(context);
        }

        @NonNull
        @Override
        public String getName() {
            return "HMSMarkerView";
        }

        @NonNull
        @Override
        public HMSMarkerView createViewInstance(@NonNull ThemedReactContext context) {
            logger.startMethodExecutionTimer("HMSMarker");
            HMSMarkerView view = new HMSMarkerView(context);
            logger.sendSingleEvent("HMSMarker");
            return view;
        }

        @Override
        public void addView(HMSMarkerView parent, View child, int index) {
            if (child instanceof HMSInfoWindowView) {
                parent.setInfoWindowView((HMSInfoWindowView) child);
            }
        }

        public enum Event implements ReactUtils.NamedEvent {
            CLICK("onClick"),
            DRAG_START("onDragStart"),
            DRAG("onDrag"),
            DRAG_END("onDragEnd"),
            INFO_WINDOW_CLICK("onInfoWindowClick"),
            INFO_WINDOW_CLOSE("onInfoWindowClose"),
            INFO_WINDOW_LONG_CLICK("onInfoWindowLongClick"),
            ANIMATION_START("onAnimationStart"),
            ANIMATION_END("onAnimationEnd");

            private final String markerEventName;

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
            HIDE_INFO_WINDOW("hideInfoWindow"),
            START_ANIMATION("startAnimation"),
            SET_ANIMATION("setAnimation"),
            CLEAN_ANIMATION("cleanAnimation");

            private final String markerCommandName;

            Command(String markerCommandName) {
                this.markerCommandName = markerCommandName;
            }

            public String getName() {
                return markerCommandName;
            }
        }

        @Nullable
        @Override
        public Map<String, Integer> getCommandsMap() {
            return ReactUtils.getCommandsMap(Command.values());
        }

        @Override
        public void receiveCommand(
                @NonNull HMSMarkerView root, int commandId, @androidx.annotation.Nullable ReadableArray args) {
            if (commandId < Command.values().length) {
                switch (Command.values()[commandId]) {
                    case SHOW_INFO_WINDOW:
                        logger.startMethodExecutionTimer("HMSMarker.showInfoWindow");
                        root.showInfoWindow();
                        logger.sendSingleEvent("HMSMarker.showInfoWindow");
                        break;
                    case HIDE_INFO_WINDOW:
                        logger.startMethodExecutionTimer("HMSMarker.hideInfoWindow");
                        root.hideInfoWindow();
                        logger.sendSingleEvent("HMSMarker.hideInfoWindow");
                        break;
                    case START_ANIMATION:
                        logger.startMethodExecutionTimer("HMSMarker.startAnimation");
                        root.startAnimation();
                        logger.sendSingleEvent("HMSMarker.startAnimation");
                        break;
                    case SET_ANIMATION:
                        logger.startMethodExecutionTimer("HMSMarker.setAnimation");
                        root.setAnimation(args);
                        logger.sendSingleEvent("HMSMarker.setAnimation");
                        break;
                    case CLEAN_ANIMATION:
                        logger.startMethodExecutionTimer("HMSMarker.cleanAnimation");
                        root.cleanAnimation();
                        logger.sendSingleEvent("HMSMarker.cleanAnimation");
                        break;
                    default:
                        break;
                }
            }
        }

        @ReactProp(name = "defaultActionOnClick", defaultBoolean = true)
        public void setDefaultActionOnClick(HMSMarkerView view, boolean isDefault) {
            view.setDefaultActionOnClick(isDefault);
        }

        @ReactProp(name = "alpha", defaultFloat = 1.0f)
        public void setAlpha(HMSMarkerView view, float alpha) {
            view.setAlpha(alpha);
        }

        @ReactProp(name = "markerAnchor")
        public void setMarkerAnchor(HMSMarkerView view, ReadableArray markerAnchor) {
            view.setMarkerAnchor(markerAnchor);
        }

        @ReactProp(name = "draggable")
        public void setDraggable(HMSMarkerView view, boolean draggable) {
            view.setDraggable(draggable);
        }

        @ReactProp(name = "flat")
        public void setFlat(HMSMarkerView view, boolean flat) {
            view.setFlat(flat);
        }

        @ReactProp(name = "icon")
        public void setIcon(HMSMarkerView view, ReadableMap icon) {
            view.setIcon(icon);
        }

        @ReactProp(name = "infoWindowAnchor")
        public void setInfoWindowAnchor(HMSMarkerView view, ReadableArray infoWindowAnchor) {
            view.setInfoWindowAnchor(infoWindowAnchor);
        }

        @ReactProp(name = "coordinate")
        public void setPosition(HMSMarkerView view, ReadableMap position) {
            view.setPosition(position);
        }

        @ReactProp(name = "rotation")
        public void setMarkerRotation(HMSMarkerView view, float rotation) {
            view.setMarkerRotation(rotation);
        }

        @ReactProp(name = "snippet")
        public void setSnippet(HMSMarkerView view, String snippet) {
            view.setSnippet(snippet);
        }

        @ReactProp(name = "title")
        public void setTitle(HMSMarkerView view, String title) {
            view.setTitle(title);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(HMSMarkerView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "clusterable")
        public void setClusterable(HMSMarkerView view, boolean clusterable) {
            view.setClusterable(clusterable);
        }

        @ReactProp(name = "zIndex")
        public void setZIndex(HMSMarkerView view, float zIndex) {
            view.setZIndex(zIndex);
        }

        @Override
        public LayoutShadowNode createShadowNodeInstance() {
            return new SizeLayoutShadowNode();
        }

        @Override
        public void updateExtraData(HMSMarkerView view, Object extraData) {
            HMSInfoWindowView infoWindow = view.getInfoWindowView();
            if (infoWindow != null) {
                view.wrapInfoWindowView();
            }
        }
    }

    private void setAnimation(ReadableArray args) {
        if (args == null) {
            return;
        }
        animationSet = new AnimationSet(false);
        ReadableMap animationMap = args.getMap(0);
        ReadableMap defaultsMap = args.getMap(1);
        if (animationMap == null) {
            return;
        }

        ReadableMapKeySetIterator it = animationMap.keySetIterator();
        while (it.hasNextKey()) {
            String key = it.nextKey();
            Animation animation = ReactUtils.getAnimationFromCommandArgs(animationMap.getMap(key), defaultsMap, key);
            if (animation != null) {
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart() {
                        WritableMap event = ReactUtils.getWritableMapFromAnimation(animation);
                        event.putString("type", key);
                        logger.sendSingleEvent("HMSMarker.onAnimationStart");
                        sendEvent(Manager.Event.ANIMATION_START, event);
                    }

                    @Override
                    public void onAnimationEnd() {
                        WritableMap event = ReactUtils.getWritableMapFromAnimation(animation);
                        event.putString("type", key);
                        logger.sendSingleEvent("HMSMarker.onAnimationEnd");
                        sendEvent(Manager.Event.ANIMATION_END, event);
                    }
                });
                animationSet.addAnimation(animation);
            }
        }


        if (mMarker != null) {
            mMarker.setAnimation(animationSet);
        }

    }

    private void startAnimation() {
        mMarker.startAnimation();
    }

    private void cleanAnimation() {
        animationSet.cleanAnimation();
        mMarker.setAnimation(animationSet);
    }

    public HMSInfoWindowView getInfoWindowView() {
        return mInfoWindowView;
    }

    public void setInfoWindowView(HMSInfoWindowView infoWindowView) {
        mInfoWindowView = infoWindowView;
    }

    public void wrapInfoWindowView() {
        HMSInfoWindowView infoWindowView = getInfoWindowView();
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
        if(icon.hasKey("uri")){
            uriIconController.setUriIcon(icon);
            return;
        }
        BitmapDescriptor bitmapDescriptor = ReactUtils.getBitmapDescriptorFromReadableMap(icon);
        setUriIcon(bitmapDescriptor, null);
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

    public void setMarkerRotation(float rotation) {
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

    private void setDefaultActionOnClick(boolean isDefault) {
        defaultActionOnClick = isDefault;
    }

    @Override
    public void setUriIcon(BitmapDescriptor bitmapDescriptor, ReadableMap options){
        mMarkerOptions.icon(bitmapDescriptor);
        if (mMarker != null) {
            mMarker.setIcon(bitmapDescriptor);
        }
    }

    @Override
    public Marker addTo(HuaweiMap huaweiMap) {
        mMarker = huaweiMap.addMarker(mMarkerOptions);
        return mMarker;
    }

    @Override
    public void removeFrom(HuaweiMap huaweiMap) {
        if (mMarker == null) {
            return;
        }
        try {
            mMarker.getPosition();
            mMarker.remove();
        } catch (NullPointerException e) {
            mMarker = null;
            mMarkerOptions = null;
        }

    }

    @Override
    public WritableMap getInfo() {
        if (mMarker == null) {
            return null;
        }
        try {
            return ReactUtils.getWritableMapFromMarker(mMarker);
        } catch (NullPointerException e) {
            return (WritableMap) null;
        }

    }

    @Override
    public WritableMap getOptionsInfo() {
        if (mMarkerOptions == null) {
            return null;
        }
        return ReactUtils.getWritableMapFromMarkerOptions(mMarkerOptions);
    }
}
