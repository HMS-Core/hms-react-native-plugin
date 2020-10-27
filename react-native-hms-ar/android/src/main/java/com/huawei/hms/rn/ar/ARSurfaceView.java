/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.ar;

import android.opengl.GLSurfaceView;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.huawei.hms.plugin.ar.core.ARSetupFacade;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigBody;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigFace;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigHand;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigWorld;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.huawei.hms.rn.ar.logger.HMSLogger;
import com.huawei.hms.rn.ar.utils.Converter;

import static com.huawei.hms.rn.ar.utils.Converter.hasValidKey;
import static com.huawei.hms.rn.ar.utils.Converter.toColorRGBA;

public class ARSurfaceView extends SimpleViewManager<GLSurfaceView> {
    private ReactApplicationContext reactContext;
    private HMSLogger hmsLogger;

    public ARSurfaceView(ReactApplicationContext reactContext) {
        this.reactContext = reactContext;
        hmsLogger = HMSLogger.getInstance(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "ARSurfaceView";
    }

    @NonNull
    @Override
    protected GLSurfaceView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new GLSurfaceView(reactContext);
    }

    private ARPluginConfigBody getBodyConfig(ReadableMap params) {
        ARPluginConfigBody mARPluginConfigBody = new ARPluginConfigBody();
        if (params == null) return mARPluginConfigBody;
        if (hasValidKey(params, "drawLine", ReadableType.Boolean)) {
            mARPluginConfigBody.setDrawLine(params.getBoolean("drawLine"));
        }
        if (hasValidKey(params, "drawPoint", ReadableType.Boolean)) {
            mARPluginConfigBody.setDrawPoint(params.getBoolean("drawPoint"));
        }
        if (hasValidKey(params, "lineWidth", ReadableType.Number)) {
            mARPluginConfigBody.setLineWidth((float) params.getDouble("lineWidth"));
        }
        if (hasValidKey(params, "pointSize", ReadableType.Number)) {
            mARPluginConfigBody.setPointSize((float) params.getDouble("pointSize"));
        }
        if (hasValidKey(params, "lineColor", ReadableType.Map)) {
            mARPluginConfigBody.setLineColor(toColorRGBA(params.getMap("lineColor")));
        }
        if (hasValidKey(params, "pointColor", ReadableType.Map)) {
            mARPluginConfigBody.setPointColor(toColorRGBA(params.getMap("pointColor")));
        }
        return mARPluginConfigBody;
    }

    private ARPluginConfigFace getFaceConfig(ReadableMap params) {
        ARPluginConfigFace mARPluginConfigFace = new ARPluginConfigFace();
        if (params == null) return mARPluginConfigFace;
        if (hasValidKey(params, "drawFace", ReadableType.Boolean)) {
            mARPluginConfigFace.setDrawFace(params.getBoolean("drawFace"));
        }
        if (hasValidKey(params, "texturePath", ReadableType.String)) {
            mARPluginConfigFace.setTexturePath(params.getString("texturePath"));
        }
        if (hasValidKey(params, "pointSize", ReadableType.Number)) {
            mARPluginConfigFace.setPointSize((float) params.getDouble("pointSize"));
        }
        if (hasValidKey(params, "depthColor", ReadableType.Map)) {
            mARPluginConfigFace.setDepthColor(toColorRGBA(params.getMap("depthColor")));
        }
        return mARPluginConfigFace;
    }

    private ARPluginConfigHand getHandConfig(ReadableMap params) {
        ARPluginConfigHand mARPluginConfigHand = new ARPluginConfigHand();
        if (params == null) return mARPluginConfigHand;
        if (hasValidKey(params, "drawBox", ReadableType.Boolean)) {
            mARPluginConfigHand.setDrawBox(params.getBoolean("drawBox"));
        }
        if (hasValidKey(params, "lineWidth", ReadableType.Number)) {
            mARPluginConfigHand.setLineWidth((float)params.getDouble("lineWidth"));
        }
        if (hasValidKey(params, "boxColor", ReadableType.Map)) {
            mARPluginConfigHand.setBoxColor(toColorRGBA(params.getMap("boxColor")));
        }
        return mARPluginConfigHand;
    }

    private ARPluginConfigWorld getWorldConfig(ReadableMap params) {
        ARPluginConfigWorld mARPluginConfigWorld = new ARPluginConfigWorld();
        if (params == null) return mARPluginConfigWorld;
        if (hasValidKey(params, "objectName", ReadableType.String)) {
            mARPluginConfigWorld.setObjPath(params.getString("objectName"));
        }
        if (hasValidKey(params, "objectTexture", ReadableType.String)) {
            mARPluginConfigWorld.setTexturePath(params.getString("objectTexture"));
        }
        if (hasValidKey(params, "showPlanes", ReadableType.Boolean)) {
            mARPluginConfigWorld.setLabelDraw(params.getBoolean("showPlanes"));
        }
        if (hasValidKey(params, "planeOther", ReadableType.Map)) {
            ReadableMap other = params.getMap("planeOther");
            if (other != null) {
                mARPluginConfigWorld.setImageOther(null);
                if (hasValidKey(other, "image", ReadableType.String)) {
                    mARPluginConfigWorld.setImageOther(other.getString("image"));
                }
                if (hasValidKey(other, "text", ReadableType.String)) {
                    mARPluginConfigWorld.setTextOther(other.getString("text"));
                }
                mARPluginConfigWorld.setColorOther(toColorRGBA(other));
            }
        }
        if (hasValidKey(params, "planeWall", ReadableType.Map)) {
            ReadableMap wall = params.getMap("planeWall");
            if (wall != null) {
                mARPluginConfigWorld.setImageWall(null);
                if (hasValidKey(wall, "image", ReadableType.String)) {
                    mARPluginConfigWorld.setImageWall(wall.getString("image"));
                }
                if (hasValidKey(wall, "text", ReadableType.String)) {
                    mARPluginConfigWorld.setTextWall(wall.getString("text"));
                }
                mARPluginConfigWorld.setColorWall(toColorRGBA(wall));
            }
        }
        if (hasValidKey(params, "planeFloor", ReadableType.Map)) {
            ReadableMap floor = params.getMap("planeFloor");
            if (floor != null) {
                mARPluginConfigWorld.setImageFloor(null);
                if (hasValidKey(floor, "image", ReadableType.String)) {
                    mARPluginConfigWorld.setImageFloor(floor.getString("image"));
                }
                if (hasValidKey(floor, "text", ReadableType.String)) {
                    mARPluginConfigWorld.setTextFloor(floor.getString("text"));
                }
                mARPluginConfigWorld.setColorFloor(toColorRGBA(floor));
            }
        }
        if (hasValidKey(params, "planeSeat", ReadableType.Map)) {
            ReadableMap seat = params.getMap("planeSeat");
            if (seat != null) {
                mARPluginConfigWorld.setImageSeat(null);
                if (hasValidKey(seat, "image", ReadableType.String)) {
                    mARPluginConfigWorld.setImageSeat(seat.getString("image"));
                }
                if (hasValidKey(seat, "text", ReadableType.String)) {
                    mARPluginConfigWorld.setTextSeat(seat.getString("text"));
                }
                mARPluginConfigWorld.setColorSeat(toColorRGBA(seat));
            }
        }
        if (hasValidKey(params, "planeTable", ReadableType.Map)) {
            ReadableMap table = params.getMap("planeTable");
            if (table != null) {
                mARPluginConfigWorld.setImageTable(null);
                if (hasValidKey(table, "image", ReadableType.String)) {
                    mARPluginConfigWorld.setImageTable(table.getString("image"));
                }
                if (hasValidKey(table, "text", ReadableType.String)) {
                    mARPluginConfigWorld.setTextTable(table.getString("text"));
                }
                mARPluginConfigWorld.setColorTable(toColorRGBA(table));
            }
        }
        if (hasValidKey(params, "planeCeiling", ReadableType.Map)) {
            ReadableMap ceiling = params.getMap("planeCeiling");
            if (ceiling != null) {
                mARPluginConfigWorld.setImageCeiling(null);
                if (hasValidKey(ceiling, "image", ReadableType.String)) {
                    mARPluginConfigWorld.setImageCeiling(ceiling.getString("image"));
                }
                if (hasValidKey(ceiling, "text", ReadableType.String)) {
                    mARPluginConfigWorld.setTextCeiling(ceiling.getString("text"));
                }
                mARPluginConfigWorld.setColorCeiling(toColorRGBA(ceiling));
            }
        }
        return mARPluginConfigWorld;
    }

    @ReactProp(name = "config")
    public void setConfig(GLSurfaceView view, ReadableMap config) {
        ARSetupFacade arSetupFacade = new ARSetupFacade(reactContext, view);
        if (hasValidKey(config, "hand", ReadableType.Map)) {
            hmsLogger.startMethodExecutionTimer("startHand");
            arSetupFacade.startHand(getHandConfig(config.getMap("hand")));
            hmsLogger.sendSingleEvent("startHand");
        } else if (hasValidKey(config, "body", ReadableType.Map)) {
            hmsLogger.startMethodExecutionTimer("startBody");
            arSetupFacade.startBody(getBodyConfig(config.getMap("body")));
            hmsLogger.sendSingleEvent("startBody");
        } else if (hasValidKey(config, "face", ReadableType.Map)) {
            hmsLogger.startMethodExecutionTimer("startFace");
            arSetupFacade.startFace(getFaceConfig(config.getMap("face")));
            hmsLogger.sendSingleEvent("startFace");
        } else if (hasValidKey(config, "world", ReadableType.Map)) {
            hmsLogger.startMethodExecutionTimer("startWorld");
            arSetupFacade.startWorld(getWorldConfig(config.getMap("world")));
            hmsLogger.sendSingleEvent("startWorld");
        } else return;
        arSetupFacade.setListener(arTrackables -> reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(
            "onDrawFrame", Converter.arTrackableToWritableMap(arTrackables)));
    }
}