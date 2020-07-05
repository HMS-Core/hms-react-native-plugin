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

package com.huawei.hms.push.react;

import android.app.Activity;
import android.content.Context;

import com.facebook.react.bridge.ReactApplicationContext;

public class Constants {

    public final static String RESULT_SUCESS = "0";

    // Unknown error. Contact Huawei technical support.
    public final static String RESULT_FAIL = "907122045";

    // The message contains invalid parameters.
    public final static String PARAMETR_IS_EMPTY = "907122042";

    public final static String PushMsgReceiverEvent = "PushMsgReceiverEvent";

    public final static String PushXmppMsgReceiverEvent = "PushXmppMsgReceiverEvent";

    public final static String PushTokenMsgReceiverEvent = "PushTokenMsgReceiverEvent";

}
