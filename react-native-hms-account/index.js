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

import React from 'react';
import {
    NativeModules,
    requireNativeComponent,
    TouchableOpacity,
} from 'react-native';

const {
    HmsAccount,
    SMSManager,
    AuthHuaweiId,
    HuaweiIdAuthManager,
    HuaweiIdAuthTool,
    NetworkTool
} = NativeModules;

const RNAccountButton = requireNativeComponent('HuaweiIdAuthButton');

const HuaweiIdAuthButton = (props) => {
  const { onPress, ...restProps } = props;
  return (
    <TouchableOpacity onPress={onPress}>
      <RNAccountButton
        {...restProps}
      />
    </TouchableOpacity>
  );
};

export default {
    HmsAccount,
    SMSManager,
    AuthHuaweiId,
    HuaweiIdAuthManager,
    HuaweiIdAuthTool,
    NetworkTool,
    HuaweiIdAuthButton
}
