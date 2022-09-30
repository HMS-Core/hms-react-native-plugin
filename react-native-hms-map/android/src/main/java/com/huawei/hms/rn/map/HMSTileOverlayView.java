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
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.TileOverlay;
import com.huawei.hms.maps.model.TileOverlayOptions;
import com.huawei.hms.maps.model.TileProvider;
import com.huawei.hms.rn.map.logger.HMSLogger;
import com.huawei.hms.rn.map.utils.ReactUtils;

import java.util.Map;

import static com.huawei.hms.rn.map.HMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.HMSMapView.MapLayerViewManager;

public class HMSTileOverlayView extends MapLayerView {
    private static final String TAG = HMSTileOverlayView.class.getSimpleName();
    private static final String REACT_CLASS = HMSTileOverlayView.class.getSimpleName();
    private TileOverlayOptions mTileOverlayOptions = new TileOverlayOptions();
    private TileOverlay mTileOverlay;

    public HMSTileOverlayView(Context context) {
        super(context);
    }

    public static class Manager extends MapLayerViewManager<HMSTileOverlayView> {
        private HMSLogger logger;

        public Manager(Context context) {
            super();
            logger = HMSLogger.getInstance(context);
        }

        @NonNull
        @Override
        public String getName() {
            return "HMSTileOverlayView";
        }

        @NonNull
        @Override
        public HMSTileOverlayView createViewInstance(@NonNull ThemedReactContext context) {
            logger.startMethodExecutionTimer("HMSTileOverlay");
            HMSTileOverlayView view = new HMSTileOverlayView(context);
            logger.sendSingleEvent("HMSTileOverlay");
            return view;
        }

        @Nullable
        @Override
        public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
            return new ArrayMap<>();
        }

        public enum Command implements ReactUtils.NamedCommand {
            CLEAR_TILE_CACHE("clearTileCache");

            private String tileOverlayCommandName;

            Command(String tileOverlayCommandName) {
                this.tileOverlayCommandName = tileOverlayCommandName;
            }

            public String getName() {
                return tileOverlayCommandName;
            }
        }

        @Nullable
        @Override
        public Map<String, Integer> getCommandsMap() {
            return ReactUtils.getCommandsMap(Command.values());
        }

        @Override
        public void receiveCommand(@NonNull HMSTileOverlayView root, int commandId, @Nullable ReadableArray args) {
            if (commandId < Command.values().length) {
                switch (Command.values()[commandId]) {
                    case CLEAR_TILE_CACHE:
                        logger.startMethodExecutionTimer("HMSTileOverlay.clearTileCache");
                        root.clearTileCache();
                        logger.sendSingleEvent("HMSTileOverlay.clearTileCache");
                        break;
                    default:
                        break;
                }
            }
        }

        @ReactProp(name = "fadeIn")
        public void setFadeIn(HMSTileOverlayView view, boolean fadeIn) {
            view.setFadeIn(fadeIn);
        }

        @ReactProp(name = "tileProvider")
        public void setTileOverlay(HMSTileOverlayView view, Dynamic tileProvider) {
            if (tileProvider.getType() == ReadableType.Map) {
                view.setTileProvider(tileProvider.asMap());
            } else if (tileProvider.getType() == ReadableType.Array) {
                view.setTileProvider(tileProvider.asArray());
            }
        }

        @ReactProp(name = "transparency")
        public void setTransparency(HMSTileOverlayView view, float transparency) {
            view.setTransparency(transparency);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(HMSTileOverlayView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "zIndex", defaultFloat = 1.0f)
        public void setZIndex(HMSTileOverlayView view, float zIndex) {
            view.setZIndex(zIndex);
        }
    }

    private void clearTileCache() {
        if (mTileOverlay != null) {
            mTileOverlay.clearTileCache();
        }
    }

    private void setFadeIn(boolean fadeIn) {
        mTileOverlayOptions.fadeIn(fadeIn);
        if (mTileOverlay != null) {
            mTileOverlay.setFadeIn(fadeIn);
        }
    }

    private void setTileProvider(ReadableMap tileProviderReadableMap) {
        TileProvider tileProvider = ReactUtils.getTileProviderFromReadableMap(tileProviderReadableMap);
        mTileOverlayOptions.tileProvider(tileProvider);
    }

    private void setTileProvider(ReadableArray tileProviderReadableArray) {
        TileProvider tileProvider = ReactUtils.getTileProviderFromReadableArray(tileProviderReadableArray,
                getContext());
        mTileOverlayOptions.tileProvider(tileProvider);
    }

    private void setTransparency(float transparency) {
        mTileOverlayOptions.transparency(transparency);
        if (mTileOverlay != null) {
            mTileOverlay.setTransparency(transparency);
        }
    }

    private void setVisible(boolean visible) {
        mTileOverlayOptions.visible(visible);
        if (mTileOverlay != null) {
            mTileOverlay.setVisible(visible);
        }
    }

    private void setZIndex(float zIndex) {
        mTileOverlayOptions.zIndex(zIndex);
        if (mTileOverlay != null) {
            mTileOverlay.setZIndex(zIndex);
        }
    }

    @Override
    public TileOverlay addTo(HuaweiMap huaweiMap) {
        mTileOverlay = huaweiMap.addTileOverlay(mTileOverlayOptions);
        return mTileOverlay;
    }

    @Override
    public void removeFrom(HuaweiMap huaweiMap) {
        if (mTileOverlay == null) {
            return;
        }
        mTileOverlay.remove();
        mTileOverlay = null;
        mTileOverlayOptions = null;
    }

    @Override
    public WritableMap getInfo() {
        if (mTileOverlay == null) {
            return null;
        }
        try {
            return ReactUtils.getWritableMapFromTileOverlay(mTileOverlay);
        } catch (NullPointerException e) {
            return (WritableMap) null;
        }
    }

    @Override
    public WritableMap getOptionsInfo() {
        if (mTileOverlayOptions == null) {
            return null;
        }
        return ReactUtils.getWritableMapFromTileOverlayOptions(mTileOverlayOptions);
    }
}
