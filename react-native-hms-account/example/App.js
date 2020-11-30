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
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from "react-native";

import HMSAccount, {
  HMSHuaweiIdAuthManager,
  HMSHuaweiIdAuthTool,
  HMSReadSMSManager,
  HMSNetworkTool,
  HMSAuthButton,
  HMSAuthRequestOptionConstants,
  HMSAuthScopeListConstants,
  HMSAuthParamConstants,
} from "@hmscore/react-native-hms-account";

const styles = StyleSheet.create({
  main: {
    marginBottom: 100,
  },
  logText: {
    margin: 20,
  },
  viewcontainer: {
    marginTop: 20,
    height: 38,
  },
  header: {
    backgroundColor: "red",
    height: 100,
    width: "100%",
    flexDirection: "row",
  },
  headerTitle: {
    flex: 1,
    fontSize: 22,
    color: "white",
    alignSelf: "center",
    textAlign: "center",
  },
  customButton: {
    marginTop: 15,
    width: 200,
    height: 45,
    justifyContent: "center",
    alignSelf: "center",
    borderRadius: 10,
    marginLeft: 5,
    marginRight: 5,
    borderColor: "red",
    borderWidth: 1,
  },
  buttonText: {
    fontSize: 14,
    color: "red",
    textAlign: "center",
  },
});
const CustomButton = (props) => (
  <TouchableOpacity style={styles.customButton} onPress={props.func}>
    <Text style={styles.buttonText}>{props.text}</Text>
  </TouchableOpacity>
);

class App extends React.Component {
  constructor() {
    super();
    this.state = { 
      log: "Initialized.",
    };
  }

  logger = (method, reponse) => {
    this.setState({ log: method + JSON.stringify(reponse) + '\n' + this.state.log});
  };

  errorLogger = (method, reponse) => {
    this.setState({ log: method +  reponse + '\n' + this.state.log});
  }

  signInWithIdToken = () => {
    let signInData = {
      huaweiIdAuthParams: HMSAuthParamConstants.DEFAULT_AUTH_REQUEST_PARAM,
      authRequestOption: [HMSAuthRequestOptionConstants.ID_TOKEN],
    };
    HMSAccount.signIn(signInData)
    .then((response) => {this.logger("Sign In With IdToken -> ", response)})
    .catch((err) =>  {this.errorLogger("Sign In With IdToken -> ",  err)});
  };

  signInWithAuthorizationCode = () => {
    let signInData = {
      huaweiIdAuthParams: HMSAuthParamConstants.DEFAULT_AUTH_REQUEST_PARAM,
      authRequestOption: [HMSAuthRequestOptionConstants.AUTHORIZATION_CODE, HMSAuthRequestOptionConstants.EMAIL],
    };
    HMSAccount.signIn(signInData)
     .then((response) => {this.logger("Sign In With AuthorizaionCode -> ", response)})
     .catch((err) =>  {this.errorLogger("Sign In With AuthorizaionCode -> ",  err)});
  };

  signOut = () =>
    HMSAccount.signOut()
      .then(() => {this.logger("signOut -> ", "Success")})
      .catch((err) =>  {this.errorLogger("signOut -> ",  err)});

  silentSignIn = () => {
    let silentSignInData = {
      huaweiIdAuthParams: HMSAuthParamConstants.DEFAULT_AUTH_REQUEST_PARAM,
    };
    HMSAccount.silentSignIn(silentSignInData)
    .then((response) => {this.logger("silentSignIn -> ", response)})
    .catch((err) =>  {this.errorLogger("silentSignIn -> ",  err)});
  };

  cancelAuthorization = () => {
    HMSAccount.cancelAuthorization()
    .then(() => {this.logger("cancelAuthorization -> ", "Success")})
    .catch((err) =>  {this.errorLogger("cancelAuthorization -> ",  err)});
  };

  getAuthResult = () => {
    HMSHuaweiIdAuthManager.getAuthResult()
    .then((response) => {this.logger("getAuthResult -> ", response)})
    .catch((err) =>  {this.errorLogger("getAuthResult ->  ",  err)});
  };

  containScopes = () => {
    let containScopesData = {
      authHuaweiId: {
        openId: "myopenid",
        uid: "myuid",
        displayName: "mydisplayname",
        photoUrl: "myphotourl",
        accessToken: "myaccesstoken",
        serviceCountryCode: "myservicecountrycode",
        status: 0,
        gender: 0,
        authScopeList: [HMSAuthScopeListConstants.OPENID, HMSAuthScopeListConstants.PROFILE, HMSAuthScopeListConstants.EMAIL],
        serverAuthCode: "myserverAuthCode",
        unionId: "myunionId",
        countryCode: "myCountryCode",
      },
      authScopeList: [HMSAuthScopeListConstants.OPENID, HMSAuthScopeListConstants.PROFILE],
    };
    HMSHuaweiIdAuthManager.containScopes(containScopesData)
      .then((response) => {this.logger("containScopes -> ", response)})
      .catch((err) =>  {this.errorLogger("containScopes -> ",  err)});
  };

  addAuthScopes = () => {
    let authScopeData = {
      authScopeList: [HMSAuthScopeListConstants.EMAIL],
    };
    HMSHuaweiIdAuthManager.addAuthScopes(authScopeData)
    .then((response) => {this.logger("addAuthScopes ->", response)})
    .catch((err) =>  {this.errorLogger("addAuthScopes -> ",  err)});
  };

  getAuthResultWithScopes = () => {
    let authScopeData = {
      authScopeList: [HMSAuthScopeListConstants.OPENID, HMSAuthScopeListConstants.PROFILE],
    };
    HMSHuaweiIdAuthManager.getAuthResultWithScopes(authScopeData)
    .then((response) => {this.logger("getAuthResultWithScopes -> ", response)})
    .catch((err) =>  {this.errorLogger("getAuthResultWithScopes -> ",  err)});
  };

  deleteAuthInfo = () => {
    let accesTokenData = {
      accessToken: "myAccessToken",
    };
    HMSHuaweiIdAuthTool.deleteAuthInfo(accesTokenData)
    .then((response) => {this.logger("deleteAuthInfo -> ", response)})
    .catch((err) =>  {this.errorLogger("deleteAuthInfo -> ",  err)});
  };

  requestUnionId = () => {
    let accountData = {
      huaweiAccountName: "huawei@huawei.com",
    };
    HMSHuaweiIdAuthTool.requestUnionId(accountData)
      .then((response) => {this.logger("requestUnionId -> ", response)})
      .catch((err) =>  {this.errorLogger("requestUnionId -> ",  err)});
  };

  requestAccessToken = () => {
    let requestAccessTokenData = {
      authScopeList: [HMSAuthScopeListConstants.OPENID, HMSAuthScopeListConstants.PROFILE],
      huaweiAccount: {
        name: "huawei@huawei.com",
        type: "com.huawei.hwid",
      },
    };
    HMSHuaweiIdAuthTool.requestAccessToken(requestAccessTokenData)
      .then((response) => {this.logger("requestAccessToken -> ", response)})
      .catch((err) =>  {this.errorLogger("requestAccessToken -> ",  err)});
  };

  getHashCode = () => {
    HMSReadSMSManager.getHashCode()
      .then((response) => {this.logger("getHashCode -> ", response)})
      .catch((err) =>  {this.errorLogger("getHashCode -> ",  err)});
  };

  smsVerificationCode = () => {
    HMSReadSMSManager.smsVerificationCode()
      .then((response) => {this.logger("smsVerificationCode -> ", response)})
      .catch((err) =>  {this.errorLogger("smsVerificationCode -> ",  err)});
  };

  buildNetworkCookie = () => {
    let cookieData = {
      cookieName: "mycookiename",
      cookieValue: "mycookievalue",
      domain: "mydomain",
      path: "mypath",
      isHttpOnly: true,
      isSecure: true,
      maxAge: 130,
    };
    HMSNetworkTool.buildNetworkCookie(cookieData)
      .then((response) => {this.logger("buildNetworkCookie -> ", response)})
      .catch((err) =>  {this.errorLogger("buildNetworkCookie -> ",  err)});
  };

  buildNetworkUrl = () => {
    let urlData = {
      isUseHttps: true,
      domain: "mydomain",
    };
    HMSNetworkTool.buildNetworkUrl(urlData)
      .then((response) => {this.logger("buildNetworkUrl -> ", response)})
      .catch((err) =>  {this.errorLogger("buildNetworkUrl -> ",  err)});
  };

  enableLogger = () => {
    HMSAccount.enableLogger()
  };

  disableLogger = () => {
    HMSAccount.disableLogger()
  };

  getButtonInfo = () => {
    if (buttonView) {
      buttonView.getInfo().then((response) => this.logger("getButtonInfo -> ", response));
    }
  };
  render() {
    return (
      <View style={styles.main}>
        <View style={styles.header}>
          <Text style={styles.headerTitle}>HMS Account Plugin</Text>
        </View>

        <ScrollView>
          <CustomButton func={this.signInWithIdToken} text="Sign in with Id Token" />
          <CustomButton func={this.signInWithAuthorizationCode} text="Sign in with Authorization Code" />
          <CustomButton func={this.signOut} text="Sign Out" />
          <CustomButton func={this.silentSignIn} text="Silent Sign In" />
          <CustomButton func={this.cancelAuthorization} text="Cancel Authorization" />
          <CustomButton func={this.getAuthResult} text="Get Auth Result" />
          <CustomButton func={this.addAuthScopes} text="Add Auth Scopes" />
          <CustomButton func={this.getAuthResultWithScopes} text="Get Auth Result With Scopes" />
          <CustomButton func={this.containScopes} text="Contain Scopes" />
          <CustomButton func={this.buildNetworkCookie} text="Build Network Cookie" />
          <CustomButton func={this.buildNetworkUrl} text="Build Network Url" />
          <CustomButton func={this.deleteAuthInfo} text="Delete Auth Info" />
          <CustomButton func={this.requestUnionId} text="Request Union Id" />
          <CustomButton func={this.requestAccessToken} text="Request Access Token" />
          <CustomButton func={this.smsVerificationCode} text="Start Read SMS" />
          <CustomButton func={this.getHashCode} text="Get Hash Code" />
          <CustomButton func={this.enableLogger} text="Enable Logger" />
          <CustomButton func={this.disableLogger} text="Disable Logger" />
          <CustomButton func={this.getButtonInfo} text="Button Info" />

          <HMSAuthButton
            style={styles.viewcontainer}
            colorPolicy={HMSAccount.HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_RED}
            enabled={true}
            theme={HMSAccount.HUAWEI_ID_AUTH_BUTTON_THEME_FULL_TITLE}
            cornerRadius={HMSAccount.HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_MEDIUM}
            onPress={this.signInWithIdToken}
            ref={(el) => (buttonView = el)}
          />

          <Text style={styles.logText}>{this.state.log}</Text>
        </ScrollView>
      </View>
    );
  }
}

export default App;
