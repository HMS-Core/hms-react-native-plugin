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

package com.huawei.hms.rn.ml.helpers.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huawei.hms.rn.ml.R;
import com.huawei.hms.rn.ml.helpers.models.HMSProductModel;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class HMSBottomSheetAdapter extends BaseAdapter {
    private List<HMSProductModel> products;

    private Context appContext;

    public HMSBottomSheetAdapter(Context context, List<HMSProductModel> mlProducts) {
        appContext = context;
        products = mlProducts;
    }

    @Override
    public int getCount() {
        return products == null ? 0 : products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(appContext, R.layout.hms_product_item, null);
        }
        SimpleDraweeView draweeView = view.findViewById(R.id.hms_product_item_image_view);
        TextView textView = view.findViewById(R.id.hms_product_item_text_view);
        draweeView.setImageURI(Uri.parse(products.get(i).getImgUrl()));
        textView.setText(products.get(i).getName());
        return view;
    }
}
