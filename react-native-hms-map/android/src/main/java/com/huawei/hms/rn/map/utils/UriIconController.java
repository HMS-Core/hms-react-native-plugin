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

package com.huawei.hms.rn.map.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;

import static com.huawei.hms.rn.map.utils.ReactUtils.hasValidKey;

public class UriIconController {
    private final Context context;
    BitmapDescriptor bitmapDescriptor;
    private int iconWidth;
    private int iconHeight;
    private final UriIconView component;
    private ReadableMap options;

    private final DraweeHolder<?> draweeHolder;
    private DataSource<CloseableReference<CloseableImage>> dataSource;
    private final ControllerListener<ImageInfo> mControllerListener =
            new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(
                        String id,
                        @Nullable final ImageInfo imageInfo,
                        @Nullable Animatable animatable) {
                    CloseableReference<CloseableImage> imageReference = null;
                    try {
                        imageReference = dataSource.getResult();
                        if (imageReference != null) {
                            CloseableImage image = imageReference.get();
                            if (image instanceof CloseableStaticBitmap) {
                                CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) image;
                                Bitmap bitmap = closeableStaticBitmap.getUnderlyingBitmap();
                                if (bitmap != null) {
                                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                                    if (iconWidth != 0 && iconHeight != 0) {
                                        bitmap = Bitmap.createScaledBitmap(bitmap, iconWidth, iconHeight, false);
                                    }
                                    bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                                    setIcon();
                                }
                            }
                        }
                    } finally {
                        dataSource.close();
                        if (imageReference != null) {
                            CloseableReference.closeSafely(imageReference);
                        }
                    }

                }
            };

    public UriIconController(Context context, UriIconView component) {
        this.context = context;
        this.component = component;
        draweeHolder = DraweeHolder.create(new GenericDraweeHierarchyBuilder(context.getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setFadeDuration(0)
                .build(), context);
        draweeHolder.onAttach();
    }

    public void setUriIcon(ReadableMap rm) {
        if (rm != null && hasValidKey(rm, "uri", ReadableType.String)) {
            String uri = rm.getString("uri");
            int height = 0;
            int width = 0;
            if (rm.hasKey("height") && rm.hasKey("width")) {
                height = rm.getInt("height");
                width = rm.getInt("width");
            }
            setUriIconHelper(uri, width, height);
        }
    }

    public void setUriIconWithOptions(ReadableMap rm, ReadableMap options) {
        this.options = options;
        setUriIcon(rm);
    }


    private void setUriIconHelper(String uri, int width, int height) {
        this.iconHeight = height;
        this.iconWidth = width;

        if (uri == null) {
            return;
        }

        if (uri.startsWith("http://") || uri.startsWith("https://") ||
                uri.startsWith("file://") || uri.startsWith("asset://") || uri.startsWith("data:")) {
            ImageRequest req = ImageRequestBuilder
                    .newBuilderWithSource(Uri.parse(uri))
                    .build();

            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            dataSource = imagePipeline.fetchDecodedImage(req, context);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(req)
                    .setControllerListener(mControllerListener)
                    .setOldController(draweeHolder.getController())
                    .build();
            draweeHolder.setController(controller);
        } else {
            int drawableId = getDrawableResourceByName(uri);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
            if (iconWidth != 0 && iconHeight != 0) {
                bitmap = Bitmap.createScaledBitmap(bitmap, iconWidth, iconHeight, false);
            }
            bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
            setIcon();
        }
    }

    private int getDrawableResourceByName(String name) {
        return context.getResources().getIdentifier(
                name,
                "drawable",
                context.getPackageName());
    }

    private void setIcon() {
        component.setUriIcon(bitmapDescriptor, options);
    }
}
