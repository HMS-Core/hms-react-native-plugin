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

package com.huawei.hms.plugin.ar.core.config;

import com.huawei.hiar.ARConfigBase;

public class ARPluginConfigHand extends ARPluginConfigBasePointLine {
    private boolean drawBox;

    private ColorRGBA boxColor;

    private ARConfigBase.CameraLensFacing cameraLensFacing = ARConfigBase.CameraLensFacing.FRONT;

    private float lineWidthSkeleton;

    public ARPluginConfigHand() {
        this.boxColor = new ColorRGBA(0, 255, 0, 255);
        this.drawBox = true;

        this.lineWidthSkeleton = 19.9f;
    }

    public float getLineWidthSkeleton() {
        return lineWidthSkeleton;
    }

    public void setLineWidthSkeleton(float lineWidthSkeleton) {
        this.lineWidthSkeleton = lineWidthSkeleton;
    }

    public ColorRGBA getBoxColor() {
        return boxColor;
    }

    public boolean isDrawBox() {
        return drawBox;
    }

    public ARConfigBase.CameraLensFacing getCameraLensFacing() {
        return this.cameraLensFacing;
    }

    public void setBoxColor(ColorRGBA boxColor) {
        this.boxColor = boxColor;
    }

    public void setDrawBox(boolean drawBox) {
        this.drawBox = drawBox;
    }

    public void setCameraLensFacing(ARConfigBase.CameraLensFacing cameraLensFacing) {
        this.cameraLensFacing = cameraLensFacing;
    }
}
