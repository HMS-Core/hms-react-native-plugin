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

package com.huawei.hms.rn.ar;

import android.opengl.GLSurfaceView;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;

import com.huawei.hiar.ARConfigBase;
import com.huawei.hiar.common.FaceHealthCheckState;
import com.huawei.hiar.listener.FaceHealthCheckStateEvent;
import com.huawei.hiar.listener.FaceHealthServiceListener;
import com.huawei.hms.plugin.ar.core.ARSetupFacade;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigAugmentedImage;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigBase;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigBasePointLine;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigBaseWorld;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigBody;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigFace;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigHand;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigSceneMesh;
import com.huawei.hms.plugin.ar.core.config.ARPluginConfigWorld;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import com.huawei.hms.plugin.ar.core.config.ARPluginConfigWorldBody;
import com.huawei.hms.rn.ar.logger.HMSLogger;
import com.huawei.hms.rn.ar.utils.Converter;

import static com.huawei.hms.rn.ar.utils.Converter.hasValidKey;
import static com.huawei.hms.rn.ar.utils.Converter.toColorRGBA;

import java.util.EventObject;

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

    private void setCommonConfig(ReadableMap params, ARPluginConfigBase configPlugin) {
        if (hasValidKey(params, "lightMode", ReadableType.Number)) {
            configPlugin.setLightMode(Converter.IntToLightEnum(params.getInt("lightMode")));
        }
        if (hasValidKey(params, "semantic", ReadableType.Map)) {
            ReadableMap paramsSemantic = params.getMap("semantic");
            if (hasValidKey(paramsSemantic, "mode", ReadableType.Number)) {
                configPlugin.setSemanticMode(paramsSemantic.getInt("mode"));
            }
            if (hasValidKey(paramsSemantic, "showSemanticModeSupportedInfo", ReadableType.Boolean)) {
                configPlugin.setShowSemanticSupportedInfo(
                    paramsSemantic.getBoolean("showSemanticModeSupportedInfo"));
            }
        }
        if (hasValidKey(params, "focusMode", ReadableType.Number)) {
            configPlugin.setFocusMode(Converter.IntToFocusModeEnum(params.getInt("focusMode")));
        }
        if (hasValidKey(params, "powerMode", ReadableType.Number)) {
            configPlugin.setPowerMode(Converter.IntToPowerModeEnum(params.getInt("powerMode")));
        }
        if (hasValidKey(params, "updateMode", ReadableType.Number)) {
            configPlugin.setUpdateMode(Converter.IntToUpdateModeEnum(params.getInt("updateMode")));
        }
    }

    private void setPointLineConfig(ReadableMap params, ARPluginConfigBasePointLine configPlugin) {
        if (hasValidKey(params, "drawLine", ReadableType.Boolean)) {
            configPlugin.setDrawLine(params.getBoolean("drawLine"));
        }
        if (hasValidKey(params, "drawPoint", ReadableType.Boolean)) {
            configPlugin.setDrawPoint(params.getBoolean("drawPoint"));
        }
        if (hasValidKey(params, "lineWidth", ReadableType.Number)) {
            configPlugin.setLineWidth((float) params.getDouble("lineWidth"));
        }
        if (hasValidKey(params, "pointSize", ReadableType.Number)) {
            configPlugin.setPointSize((float) params.getDouble("pointSize"));
        }
        if (hasValidKey(params, "lineColor", ReadableType.Map)) {
            configPlugin.setLineColor(toColorRGBA(params.getMap("lineColor")));
        }
        if (hasValidKey(params, "pointColor", ReadableType.Map)) {
            configPlugin.setPointColor(toColorRGBA(params.getMap("pointColor")));
        }
        setCommonConfig(params, configPlugin);
    }

    private void setWorldConfig(ReadableMap params, ARPluginConfigBaseWorld configPlugin) {
        if (hasValidKey(params, "objectName", ReadableType.String)) {
            configPlugin.setObjPath(params.getString("objectName"));
        }
        if (hasValidKey(params, "objectTexture", ReadableType.String)) {
            configPlugin.setTexturePath(params.getString("objectTexture"));
        }
        if (hasValidKey(params, "showPlanes", ReadableType.Boolean)) {
            configPlugin.setLabelDraw(params.getBoolean("showPlanes"));
        }
        if (hasValidKey(params, "planeOther", ReadableType.Map)) {
            ReadableMap other = params.getMap("planeOther");
            if (other != null) {
                configPlugin.setImageOther(null);
                if (hasValidKey(other, "image", ReadableType.String)) {
                    configPlugin.setImageOther(other.getString("image"));
                }
                if (hasValidKey(other, "text", ReadableType.String)) {
                    configPlugin.setTextOther(other.getString("text"));
                }
                configPlugin.setColorOther(toColorRGBA(other));
            }
        }
        if (hasValidKey(params, "planeWall", ReadableType.Map)) {
            ReadableMap wall = params.getMap("planeWall");
            if (wall != null) {
                configPlugin.setImageWall(null);
                if (hasValidKey(wall, "image", ReadableType.String)) {
                    configPlugin.setImageWall(wall.getString("image"));
                }
                if (hasValidKey(wall, "text", ReadableType.String)) {
                    configPlugin.setTextWall(wall.getString("text"));
                }
                configPlugin.setColorWall(toColorRGBA(wall));
            }
        }
        if (hasValidKey(params, "planeFloor", ReadableType.Map)) {
            ReadableMap floor = params.getMap("planeFloor");
            if (floor != null) {
                configPlugin.setImageFloor(null);
                if (hasValidKey(floor, "image", ReadableType.String)) {
                    configPlugin.setImageFloor(floor.getString("image"));
                }
                if (hasValidKey(floor, "text", ReadableType.String)) {
                    configPlugin.setTextFloor(floor.getString("text"));
                }
                configPlugin.setColorFloor(toColorRGBA(floor));
            }
        }
        if (hasValidKey(params, "planeSeat", ReadableType.Map)) {
            ReadableMap seat = params.getMap("planeSeat");
            if (seat != null) {
                configPlugin.setImageSeat(null);
                if (hasValidKey(seat, "image", ReadableType.String)) {
                    configPlugin.setImageSeat(seat.getString("image"));
                }
                if (hasValidKey(seat, "text", ReadableType.String)) {
                    configPlugin.setTextSeat(seat.getString("text"));
                }
                configPlugin.setColorSeat(toColorRGBA(seat));
            }
        }
        if (hasValidKey(params, "planeTable", ReadableType.Map)) {
            ReadableMap table = params.getMap("planeTable");
            if (table != null) {
                configPlugin.setImageTable(null);
                if (hasValidKey(table, "image", ReadableType.String)) {
                    configPlugin.setImageTable(table.getString("image"));
                }
                if (hasValidKey(table, "text", ReadableType.String)) {
                    configPlugin.setTextTable(table.getString("text"));
                }
                configPlugin.setColorTable(toColorRGBA(table));
            }
        }
        if (hasValidKey(params, "planeCeiling", ReadableType.Map)) {
            ReadableMap ceiling = params.getMap("planeCeiling");
            if (ceiling != null) {
                configPlugin.setImageCeiling(null);
                if (hasValidKey(ceiling, "image", ReadableType.String)) {
                    configPlugin.setImageCeiling(ceiling.getString("image"));
                }
                if (hasValidKey(ceiling, "text", ReadableType.String)) {
                    configPlugin.setTextCeiling(ceiling.getString("text"));
                }
                configPlugin.setColorCeiling(toColorRGBA(ceiling));
            }
        }
        if (hasValidKey(params, "maxMapSize", ReadableType.Number)) {
            configPlugin.setMaxMapSize(params.getInt("maxMapSize"));
        }
        setPointLineConfig(params, configPlugin);
    }

    private ARPluginConfigBody getBodyConfig(ReadableMap params) {
        ARPluginConfigBody mARPluginConfigBody = new ARPluginConfigBody();
        if (params == null) {
            return mARPluginConfigBody;
        }
        setPointLineConfig(params, mARPluginConfigBody);
        return mARPluginConfigBody;
    }

    private ARPluginConfigFace getFaceConfig(ReadableMap params) {
        ARPluginConfigFace mARPluginConfigFace = new ARPluginConfigFace();

        if (params == null) {
            return mARPluginConfigFace;
        }
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
        if (hasValidKey(params, "cameraLensFacing", ReadableType.Number)) {
            if (params.getInt("cameraLensFacing") == ARConfigBase.CameraLensFacing.REAR.ordinal()) {
                mARPluginConfigFace.setCameraLensFacing(ARConfigBase.CameraLensFacing.REAR);
            }
        }
        if (hasValidKey(params, "enableHealthDevice", ReadableType.Boolean)) {
            mARPluginConfigFace.setHealth(params.getBoolean("enableHealthDevice"));
        }
        if (hasValidKey(params, "multiFace", ReadableType.Boolean)) {
            mARPluginConfigFace.setMultiFace(params.getBoolean("multiFace"));
        }
        setCommonConfig(params, mARPluginConfigFace);
        return mARPluginConfigFace;
    }

    private ARPluginConfigHand getHandConfig(ReadableMap params) {
        ARPluginConfigHand mARPluginConfigHand = new ARPluginConfigHand();

        if (params == null) {
            return mARPluginConfigHand;
        }
        if (hasValidKey(params, "drawBox", ReadableType.Boolean)) {
            mARPluginConfigHand.setDrawBox(params.getBoolean("drawBox"));
        }
        if (hasValidKey(params, "boxColor", ReadableType.Map)) {
            mARPluginConfigHand.setBoxColor(toColorRGBA(params.getMap("boxColor")));
        }
        if (hasValidKey(params, "cameraLensFacing", ReadableType.Number)) {
            if (params.getInt("cameraLensFacing") == ARConfigBase.CameraLensFacing.REAR.ordinal()) {
                mARPluginConfigHand.setCameraLensFacing(ARConfigBase.CameraLensFacing.REAR);
            }
        }
        if (hasValidKey(params, "lineWidthSkeleton", ReadableType.Number)) {
            mARPluginConfigHand.setLineWidthSkeleton((float) params.getDouble("lineWidthSkeleton"));
        }
        setPointLineConfig(params, mARPluginConfigHand);

        return mARPluginConfigHand;
    }

    private ARPluginConfigWorld getWorldConfig(ReadableMap params) {
        ARPluginConfigWorld mARPluginConfigWorld = new ARPluginConfigWorld();
        if (params == null) {
            return mARPluginConfigWorld;
        }
        setWorldConfig(params, mARPluginConfigWorld);
        if (hasValidKey(params, "augmentedImages", ReadableType.Array)) {
            ReadableArray paramsAI = params.getArray("augmentedImages");
            mARPluginConfigWorld.setAugmentedImageDBModels(Converter.toAugmentedImageDBModelList(paramsAI));
        }
        if (hasValidKey(params, "planeFindingMode", ReadableType.Number)) {
            mARPluginConfigWorld.setPlaneFindingMode(
                Converter.IntToPlaneFindingModeEnum(params.getInt("planeFindingMode")));
        }
        if (hasValidKey(params, "drawLineAI", ReadableType.Boolean)) {
            mARPluginConfigWorld.setDrawLine(params.getBoolean("drawLineAI"));
        }
        if (hasValidKey(params, "drawPointAI", ReadableType.Boolean)) {
            mARPluginConfigWorld.setDrawPoint(params.getBoolean("drawPointAI"));
        }
        if (hasValidKey(params, "lineWidthAI", ReadableType.Number)) {
            mARPluginConfigWorld.setLineWidth((float) params.getDouble("lineWidthAI"));
        }
        if (hasValidKey(params, "pointSizeAI", ReadableType.Number)) {
            mARPluginConfigWorld.setPointSize((float) params.getDouble("pointSizeAI"));
        }
        if (hasValidKey(params, "lineColorAI", ReadableType.Map)) {
            mARPluginConfigWorld.setLineColor(toColorRGBA(params.getMap("lineColorAI")));
        }
        if (hasValidKey(params, "pointColorAI", ReadableType.Map)) {
            mARPluginConfigWorld.setPointColor(toColorRGBA(params.getMap("pointColorAI")));
        }
        return mARPluginConfigWorld;
    }

    private ARPluginConfigWorldBody getWorldBodyConfig(ReadableMap params) {
        ARPluginConfigWorldBody mARPluginConfigWorld = new ARPluginConfigWorldBody();
        if (params == null) {
            return mARPluginConfigWorld;
        }
        setWorldConfig(params, mARPluginConfigWorld);
        if (hasValidKey(params, "planeFindingMode", ReadableType.Number)) {
            mARPluginConfigWorld.setPlaneFindingMode(
                Converter.IntToPlaneFindingModeEnum(params.getInt("planeFindingMode")));
        }

        return mARPluginConfigWorld;
    }

    private ARPluginConfigAugmentedImage getAugmentedImageConfig(ReadableMap params) {
        ARPluginConfigAugmentedImage mARPluginConfigImageConfig = new ARPluginConfigAugmentedImage();
        if (params == null) {
            return mARPluginConfigImageConfig;
        }
        if (hasValidKey(params, "augmentedImages", ReadableType.Array)) {
            ReadableArray paramsAI = params.getArray("augmentedImages");
            mARPluginConfigImageConfig.setAugmentedImageDBModels(Converter.toAugmentedImageDBModelList(paramsAI));
        }
        setPointLineConfig(params, mARPluginConfigImageConfig);
        return mARPluginConfigImageConfig;
    }

    private ARPluginConfigSceneMesh getSceneMeshConfig(ReadableMap params) {
        ARPluginConfigSceneMesh mARPluginConfigSceneMesh = new ARPluginConfigSceneMesh();
        if (params == null) {
            return mARPluginConfigSceneMesh;
        }
        if (hasValidKey(params, "objectName", ReadableType.String)) {
            mARPluginConfigSceneMesh.setObjPath(params.getString("objectName"));
        }
        if (hasValidKey(params, "objectTexture", ReadableType.String)) {
            mARPluginConfigSceneMesh.setTexturePath(params.getString("objectTexture"));
        }
        setCommonConfig(params, mARPluginConfigSceneMesh);
        return mARPluginConfigSceneMesh;
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

            if (config.getMap("face").hasKey("enableHealthDevice")) {
                Boolean enableHealthDevice = config.getMap("face").getBoolean("enableHealthDevice");
                if (enableHealthDevice) {
                    arSetupFacade.setEnableItem(ARConfigBase.ENABLE_HEALTH_DEVICE);
                    arSetupFacade.setFaceHealthListener(new FaceHealthServiceListener() {
                        @Override
                        public void handleEvent(EventObject eventObject) {
                            if (!(eventObject instanceof FaceHealthCheckStateEvent)) {
                                return;
                            }
                            final FaceHealthCheckState faceHealthCheckState
                                = ((FaceHealthCheckStateEvent) eventObject).getFaceHealthCheckState();
                            Log.e(ARSetupFacade.class.getSimpleName(),
                                "handleEvent Object:" + faceHealthCheckState.toString());
                            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                .emit("handleEvent", faceHealthCheckState.toString());
                        }

                        @Override
                        public void handleProcessProgressEvent(final int progress) {
                            Log.e(ARSetupFacade.class.getSimpleName(),
                                "handleProcessProgressEvent Count:" + progress);
                            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                .emit("handleProcessProgressEvent", progress);
                        }
                    });
                    arSetupFacade.setFaceHealthResultListener(res -> {
                        Log.e(ARSetupFacade.class.getSimpleName(), "handleResult Object:" + res);
                        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit("handleResult", res);
                    });
                }
            }
            hmsLogger.sendSingleEvent("startFace");
        } else if (hasValidKey(config, "world", ReadableType.Map)) {
            hmsLogger.startMethodExecutionTimer("startWorld");
            arSetupFacade.startWorld(getWorldConfig(config.getMap("world")));
            hmsLogger.sendSingleEvent("startWorld");
        } else if (hasValidKey(config, "cloud3Dobject", ReadableType.Map)) {
            hmsLogger.startMethodExecutionTimer("cloud3Dobject");
            arSetupFacade.startCloud3Dobject();
            hmsLogger.sendSingleEvent("cloud3Dobject");
        } else if (hasValidKey(config, "augmentedImage", ReadableType.Map)) {
            hmsLogger.startMethodExecutionTimer("augmentedImage");
            arSetupFacade.startAugmentedImage(getAugmentedImageConfig(config.getMap("augmentedImage")));
            hmsLogger.sendSingleEvent("augmentedImage");
        } else if (hasValidKey(config, "worldBody", ReadableType.Map)) {
            hmsLogger.startMethodExecutionTimer("startWorldBody");
            arSetupFacade.startWorldBody(getWorldBodyConfig(config.getMap("worldBody")));
            hmsLogger.sendSingleEvent("startWorldBody");
        } else if (hasValidKey(config, "sceneMesh", ReadableType.Map)) {
            hmsLogger.startMethodExecutionTimer("startSceneMesh");
            arSetupFacade.startSceneMesh(getSceneMeshConfig(config.getMap("sceneMesh")));
            hmsLogger.sendSingleEvent("startSceneMesh");

            arSetupFacade.setSceneMeshListener(
                arSceneMesh -> reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("onDrawFrame", Converter.arSceneMeshToWritableMap(arSceneMesh)));
        } else {
            return;
        }
        arSetupFacade.setListener(
            arTrackables -> reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onDrawFrame", Converter.arTrackableToWritableMap(arTrackables)));
        arSetupFacade.setCameraConfigListener(
            cameraConfig -> reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("handleCameraConfig", Converter.arCameraConfigToWritableMap(cameraConfig)));
        arSetupFacade.setCameraIntrinsicsListener(
            cameraIntrinsics -> reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("handleCameraIntrinsics", Converter.arCameraIntrinsicsToWritableMap(cameraIntrinsics)));
        arSetupFacade.setMessageDataListener(
            res -> reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("messageListener", res));
    }
}