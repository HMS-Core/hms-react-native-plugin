/*
 * Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.hms.rn.map;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.CircleOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.PatternItem;
import com.huawei.hms.maps.model.animation.Animation;
import com.huawei.hms.rn.map.logger.HMSLogger;
import com.huawei.hms.rn.map.utils.ReactUtils;

import java.util.List;
import java.util.Map;

import static com.huawei.hms.rn.map.HMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.HMSMapView.MapLayerViewManager;

public class HMSCircleView extends MapLayerView {
    private static final String TAG = HMSCircleView.class.getSimpleName();
    private static final String REACT_CLASS = HMSCircleView.class.getSimpleName();
    public CircleOptions mCircleOptions = new CircleOptions();
    public Circle mCircle;
    public Animation mAnimation = null;
    HMSLogger logger;


    public HMSCircleView(Context context) {
        super(context);
        logger = HMSLogger.getInstance(context);
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

        public enum Event implements ReactUtils.NamedEvent {

            ANIMATION_START("onAnimationStart"),
            ANIMATION_END("onAnimationEnd");

            private final String circleEventName;

            Event(String circleEventName) {
                this.circleEventName = circleEventName;
            }

            public String getName() {
                return circleEventName;
            }
        }
        @Nullable
        @Override
        public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
            return ReactUtils.getExportedCustomDirectEventTypeConstantsFromEvents(Event.values());
        }

        public enum Command implements ReactUtils.NamedCommand {
            START_ANIMATION("startAnimation"),
            SET_ANIMATION("setAnimation"),
            CLEAN_ANIMATION("cleanAnimation");

            private final String circleCommandName;

            Command(String circleCommandName) {
                this.circleCommandName = circleCommandName;
            }

            public String getName() {
                return circleCommandName;
            }
        }

        @Nullable
        @Override
        public Map<String, Integer> getCommandsMap() {
            return ReactUtils.getCommandsMap(Command.values());
        }

        @Override
        public void receiveCommand(@NonNull HMSCircleView root, int commandId, @Nullable ReadableArray args) {
            if (commandId < Command.values().length) {
                switch (Command.values()[commandId]) {
                    case START_ANIMATION:
                        logger.startMethodExecutionTimer("HMSCircle.startAnimation");
                        root.startAnimation();
                        logger.sendSingleEvent("HMSCircle.startAnimation");
                        break;
                    case SET_ANIMATION:
                        logger.startMethodExecutionTimer("HMSCircle.setAnimation");
                        root.setAnimation(args);
                        logger.sendSingleEvent("HMSCircle.setAnimation");
                        break;
                    case CLEAN_ANIMATION:
                        logger.startMethodExecutionTimer("HMSCircle.cleanAnimation");
                        root.cleanAnimation();
                        logger.sendSingleEvent("HMSCircle.cleanAnimation");
                        break;
                    default:
                        break;
                }
            }
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

    private void setAnimation(ReadableArray args) {
        if (args == null) {
            return;
        }
        ReadableMap animationMap = args.getMap(0);
        ReadableMap defaultsMap = args.getMap(1);
        if (animationMap == null) {
            return;
        }

        ReadableMapKeySetIterator it = animationMap.keySetIterator();
        if  (it.hasNextKey()) {
            String key = it.nextKey();
            if (key.equals("translate")) {
                Animation animation = ReactUtils.getAnimationFromCommandArgs(animationMap.getMap(key), defaultsMap, key);
                if (animation != null) {
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart() {
                            WritableMap event = ReactUtils.getWritableMapFromAnimation(animation);
                            event.putString("type", key);
                            logger.sendSingleEvent("HMSCircle.onAnimationStart");
                            sendEvent(Manager.Event.ANIMATION_START, event);
                        }

                        @Override
                        public void onAnimationEnd() {
                            WritableMap event = ReactUtils.getWritableMapFromAnimation(animation);
                            event.putString("type", key);
                            logger.sendSingleEvent("HMSCircle.onAnimationEnd");
                            sendEvent(Manager.Event.ANIMATION_END, event);
                        }
                    });
                    mAnimation = animation;
                }
            } else  {
                Log.w(TAG, "Only translate animation is supported");
            }
        }

        if (mCircle != null && mAnimation != null) {
            mCircle.setAnimation(mAnimation);
        }
    }

    private void startAnimation() {
        mCircle.startAnimation();
    }

    private void cleanAnimation() {
        mAnimation = null;
        mCircle.clearAnimation();
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
        if(mCircle == null) {
            return;
        }
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
