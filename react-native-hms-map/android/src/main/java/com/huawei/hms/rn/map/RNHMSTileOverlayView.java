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
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.TileOverlay;
import com.huawei.hms.maps.model.TileOverlayOptions;
import com.huawei.hms.maps.model.TileProvider;
import com.huawei.hms.rn.map.utils.ReactUtils;

import java.util.Map;

import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerView;
import static com.huawei.hms.rn.map.RNHMSMapView.MapLayerViewManager;

public class RNHMSTileOverlayView extends MapLayerView {
    private static final String TAG = RNHMSTileOverlayView.class.getSimpleName();
    private static final String REACT_CLASS = RNHMSTileOverlayView.class.getSimpleName();
    private TileOverlayOptions mTileOverlayOptions = new TileOverlayOptions();
    private TileOverlay mTileOverlay;

    public RNHMSTileOverlayView(Context context) {
        super(context);
    }

    public static class Manager extends MapLayerViewManager<RNHMSTileOverlayView> {
        @NonNull
        @Override
        public String getName() {
            return REACT_CLASS;
        }

        @NonNull
        @Override
        public RNHMSTileOverlayView createViewInstance(@NonNull ThemedReactContext context) {
            return new RNHMSTileOverlayView(context);
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

        @Override
        public void receiveCommand(@NonNull RNHMSTileOverlayView root, String commandId, @Nullable ReadableArray args) {
            ReactUtils.NamedCommand command = ReactUtils.getCommand(commandId, Command.values());
            assert command != null;
            if (command instanceof Command) {
                switch ((Command) command) {
                    case CLEAR_TILE_CACHE:
                        root.clearTileCache();
                        break;
                    default:
                        break;
                }
            }
        }

        @ReactProp(name = "fadeIn")
        public void setFadeIn(RNHMSTileOverlayView view, boolean fadeIn) {
            view.setFadeIn(fadeIn);
        }

        @ReactProp(name = "tileProvider")
        public void setTileOverlay(RNHMSTileOverlayView view, ReadableMap tileProvider) {
            view.setTileProvider(tileProvider);
        }

        @ReactProp(name = "transparency")
        public void setTransparency(RNHMSTileOverlayView view, float transparency) {
            view.setTransparency(transparency);
        }

        @ReactProp(name = "visible", defaultBoolean = true)
        public void setVisible(RNHMSTileOverlayView view, boolean visible) {
            view.setVisible(visible);
        }

        @ReactProp(name = "zIndex", defaultFloat = 1.0f)
        public void setZIndex(RNHMSTileOverlayView view, float zIndex) {
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
        mTileOverlay.remove();
    }
}
