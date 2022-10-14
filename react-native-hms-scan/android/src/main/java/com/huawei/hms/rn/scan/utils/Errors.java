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

package com.huawei.hms.rn.scan.utils;

import com.huawei.hms.hmsscankit.ScanUtil;

public enum Errors {
    SCAN_UTIL_NO_CAMERA_PERMISSION(String.valueOf(ScanUtil.ERROR_NO_CAMERA_PERMISSION), "NO CAMERA PERMISSION"),
    SCAN_UTIL_NO_READ_PERMISSION(String.valueOf(ScanUtil.ERROR_NO_READ_PERMISSION), "NO READ PERMISSION"),

    DECODE_MULTI_ASYNC_COULD_NOT_FIND("13", "Multi Async - Couldn't find anything."),
    DECODE_MULTI_ASYNC_ON_FAILURE("14", "Multi Async - On Failure"),
    DECODE_MULTI_SYNC_COULD_NOT_FIND("15", "Multi Sync - Couldn't find anything."),

    MP_CAMERA_SCAN_MODE_ERROR("16", "Please check your scan mode."),

    DECODE_WITH_BITMAP_ERROR("17", "Please check your barcode and scan type."),

    BUILD_BITMAP("18", "Barcode generation failed."),

    HMS_SCAN_ANALYZER_ERROR("19", "Analyzer is not available."),

    REMOTE_VIEW_ERROR("20", "Remote View is not initialized.");

    private final String errorCode;

    private final String errorMessage;

    Errors(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

