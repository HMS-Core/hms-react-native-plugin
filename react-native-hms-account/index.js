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

import React from "react";
import {
  findNodeHandle,
  NativeModules,
  requireNativeComponent,
  TouchableOpacity,
} from "react-native";

const { HMSAccount } = NativeModules;

export const HMSAuthParamConstants = {
  DEFAULT_AUTH_REQUEST_PARAM: "DEFAULT_AUTH_REQUEST_PARAM",
  DEFAULT_AUTH_REQUEST_PARAM_GAME: "DEFAULT_AUTH_REQUEST_PARAM_GAME",
}

export const HMSAuthRequestOptionConstants = {
  ID_TOKEN: "idToken",
  ID: "id",
  ACCESS_TOKEN: "accessToken",
  AUTHORIZATION_CODE: "authorizationCode",
  EMAIL: "email",
  PROFILE: "profile",
}

export const HMSAuthScopeListConstants = {
  OPENID: "openid",
  EMAIL: "email",
  PROFILE: "profile",
  GAME: "https://www.huawei.com/auth/games",
}

export const {
  HMSHuaweiIdAuthManager,
  HMSHuaweiIdAuthTool,
  HMSNetworkTool,
  HMSReadSMSManager,
} = NativeModules;

const AuthButton = requireNativeComponent("HMSHuaweiIdAuthButton");

export class HMSAuthButton extends React.Component {
  getInfo = () => HMSAccount.getButtonInfo(findNodeHandle(this.buttonView));
  render() {
    const { onPress, ...restProps } = this.props;
    restProps.enabled =
      typeof restProps.enabled == "undefined" ? true : restProps.enabled;
    return (
      <TouchableOpacity onPress={onPress} disabled={!restProps.enabled}>
        <AuthButton {...restProps} ref={(el) => (this.buttonView = el)} />
      </TouchableOpacity>
    );
  }
}

export default HMSAccount;
