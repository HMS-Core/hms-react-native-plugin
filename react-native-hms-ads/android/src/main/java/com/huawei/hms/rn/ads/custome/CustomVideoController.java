/*
 * Copyright 2020-2024. Huawei Technologies Co., Ltd. All rights reserved.
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

package com.huawei.hms.rn.ads.custome;

import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.facebook.react.bridge.ReactContext;
import com.huawei.hms.ads.vast.player.base.BaseVideoController;
import com.huawei.hms.ads.vast.player.misc.utils.AudioUtil;
import com.huawei.hms.rn.ads.R;

public class CustomVideoController extends BaseVideoController{
    private CheckBox btnMute;

    private ViewGroup clContent;

    private Button btnDetailView;

    private Button btnScreen;

    private Button btnPlay;

    public CustomVideoController(ReactContext context) {
        this(context, null);
    }

    public CustomVideoController(ReactContext context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVideoController(ReactContext context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initMuteState();
    }

    private void initView(ReactContext context) {
        clContent = findViewById(R.id.cl_content);
        btnDetailView = findViewById(R.id.demo_bt_detail);
        btnScreen = findViewById(R.id.demo_bt_full_screen);
        btnPlay = findViewById(R.id.demo_bt_play);
        btnMute = findViewById(R.id.demo_bt_voice);

        btnScreen.setOnClickListener(v -> toggleScreen(context.getCurrentActivity()));
        btnDetailView.setOnClickListener(v -> launchAdDetailView(context.getCurrentActivity()));
        btnPlay.setOnClickListener(v -> startOrPause());
        clContent.setOnClickListener(v -> launchAdDetailView(context.getCurrentActivity()));
    }

    private void initMuteState() {
        btnMute.setChecked(isMute());
        btnMute.setOnCheckedChangeListener((button, checked) -> toggleMuteState(checked));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_player;
    }

    @Override
    public boolean isMute() {
        if (mIsForceMute) {
            return btnMute.isChecked();
        } else {
            return AudioUtil.isSystemVolumeZero(mActivity);
        }
    }
}
