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

import React from "react";
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Image } from "react-native";

import HMSAccount, {
  HMSAccountAuthService,
  HMSAccountAuthManager,
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
    fontSize: 25,
    color: "white",
    fontWeight: "bold",
    alignSelf: "center",
    textAlign: "center",
  },
  subTitle: {
    flex: 1,
    fontSize: 15,
    marginTop: 15,
    fontWeight: "bold",
    color: "black",
    alignSelf: "center",
    textAlign: "center",
  },
  row: {
    flex: 1, 
    height: 1, 
    backgroundColor: 'black'
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
const Button = (props) => (
  <TouchableOpacity style={styles.customButton} onPress={props.func}>
    <Text style={styles.buttonText}>{props.text}</Text>
  </TouchableOpacity>
);

class App extends React.Component {
  constructor() {
    super();
    this.state = {
      icon: "",
      log: "",
    };
  }

  logger = (method, response) => {
    this.setState({ log: method + JSON.stringify(response) + '\n' + this.state.log });
  };

  errorLogger = (method, response) => {
    this.setState({ log: method + response + '\n' + this.state.log });
  }

  signInWithIdToken = () => {
    let signInData = {
      accountAuthParams: HMSAuthParamConstants.DEFAULT_AUTH_REQUEST_PARAM,
      authRequestOption: [HMSAuthRequestOptionConstants.ID_TOKEN, HMSAuthRequestOptionConstants.ACCESS_TOKEN, HMSAuthRequestOptionConstants.CARRIERID],
      authScopeList: [HMSAuthScopeListConstants.EMAIL]
    };
    HMSAccountAuthService.signIn(signInData)
      .then((response) => { this.logger("Sign In With IdToken -> ", response) })
      .catch((err) => { this.errorLogger("Sign In With IdToken -> ", err) });
  };

  signInWithAuthorizationCode = () => {
    let signInData = {
      accountAuthParams: HMSAuthParamConstants.DEFAULT_AUTH_REQUEST_PARAM,
      authRequestOption: [HMSAuthRequestOptionConstants.AUTHORIZATION_CODE, HMSAuthRequestOptionConstants.ACCESS_TOKEN],
    };
    HMSAccountAuthService.signIn(signInData)
      .then((response) => { this.logger("Sign In With AuthorizaionCode -> ", response) })
      .catch((err) => { this.errorLogger("Sign In With AuthorizaionCode -> ", err) });
  };

  signOut = () =>
    HMSAccountAuthService.signOut()
      .then((response) => { this.logger("signOut -> ", response) })
      .catch((err) => { this.errorLogger("signOut -> ", err) });

  silentSignIn = () => {
    let silentSignInData = {
      accountAuthParams: HMSAuthParamConstants.DEFAULT_AUTH_REQUEST_PARAM,
    };
    HMSAccountAuthService.silentSignIn(silentSignInData)
      .then((response) => { this.logger("silentSignIn -> ", response) })
      .catch((err) => { this.errorLogger("silentSignIn -> ", err) });
  };

  cancelAuthorization = () => {
    HMSAccountAuthService.cancelAuthorization()
      .then((response) => { this.logger("cancelAuthorization -> ", response) })
      .catch((err) => { this.errorLogger("cancelAuthorization -> ", err) });
  };

  getIndependentSignIn = () => {
    HMSAccountAuthService.getIndependentSignInIntent("CwHLQFU9k3D4f...")
      .then((response) => { this.logger("getIndependentSignIn -> ", response) })
      .catch((err) => { this.errorLogger("getIndependentSignIn -> ", err) });
  };

  getChannel = () =>
    HMSAccountAuthService.getChannel()
      .then((response) => {
        this.logger("getChannel -> ", response)
        this.setState({
          icon: response.icon
        })
      })
      .catch((err) => { this.errorLogger("getChannel ->  ", err) });


  getAuthResult = () => {
    HMSAccountAuthManager.getAuthResult()
      .then((response) => { this.logger("getAuthResult -> ", response) })
      .catch((err) => { this.errorLogger("getAuthResult ->  ", err) });
  };

  containScopes = () => {
    let containScopesData = {
      authAccount: {
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
    HMSAccountAuthManager.containScopes(containScopesData)
      .then((response) => { this.logger("containScopes -> ", response) })
      .catch((err) => { this.errorLogger("containScopes -> ", err) });
  };

  addAuthScopes = () => {
    let authScopeData = {
      authScopeList: [HMSAuthScopeListConstants.EMAIL],
    };
    HMSAccountAuthManager.addAuthScopes(authScopeData)
      .then((response) => { this.logger("addAuthScopes ->", response) })
      .catch((err) => { this.errorLogger("addAuthScopes -> ", err) });
  };

  getAuthResultWithScopes = () => {
    let authScopeData = {
      authScopeList: [HMSAuthScopeListConstants.OPENID, HMSAuthScopeListConstants.PROFILE],
    };
    HMSAccountAuthManager.getAuthResultWithScopes(authScopeData)
      .then((response) => { this.logger("getAuthResultWithScopes -> ", response) })
      .catch((err) => { this.errorLogger("getAuthResultWithScopes -> ", err) });
  };

  deleteAuthInfo = () => {
    let accesTokenData = {
      accessToken: "myAccessToken",
    };
    HMSHuaweiIdAuthTool.deleteAuthInfo(accesTokenData)
      .then((response) => { this.logger("deleteAuthInfo -> ", response) })
      .catch((err) => { this.errorLogger("deleteAuthInfo -> ", err) });
  };

  requestUnionId = () => {
    let accountData = {
      huaweiAccountName: "test@test.com",
    };
    HMSHuaweiIdAuthTool.requestUnionId(accountData)
      .then((response) => { this.logger("requestUnionId -> ", response) })
      .catch((err) => { this.errorLogger("requestUnionId -> ", err) });
  };

  requestAccessToken = () => {
    let requestAccessTokenData = {
      authScopeList: [HMSAuthScopeListConstants.OPENID, HMSAuthScopeListConstants.PROFILE],
      huaweiAccount: {
        name: "test@test.com",
        type: "com.huawei.hwid",
      },
    };
    HMSHuaweiIdAuthTool.requestAccessToken(requestAccessTokenData)
      .then((response) => { console.log("requestAccessToken -> ", JSON.stringify(response)) })
      .catch((err) => { this.errorLogger("requestAccessToken -> ", err) });
  };

  getHashCode = () => {
    HMSReadSMSManager.getHashCode()
      .then((response) => { this.logger("getHashCode -> ", response) })
      .catch((err) => { this.errorLogger("getHashCode -> ", err) });
  };

  smsVerificationCode = () => {
    HMSReadSMSManager.smsVerificationCode()
      .then((response) => { this.logger("smsVerificationCode -> ", response) })
      .catch((err) => { this.errorLogger("smsVerificationCode -> ", err) });
  };

  startConsent = () => {
    HMSReadSMSManager.startConsent("...")
      .then((response) => { this.logger("smsWithPhoneNumber -> ", response) })
      .catch((err) => { this.errorLogger("smsWithPhoneNumber -> ", err) });
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
      .then((response) => { this.logger("buildNetworkCookie -> ", response) })
      .catch((err) => { this.errorLogger("buildNetworkCookie -> ", err) });
  };

  buildNetworkUrl = () => {
    let urlData = {
      isUseHttps: true,
      domain: "mydomain",
    };
    HMSNetworkTool.buildNetworkUrl(urlData)
      .then((response) => { this.logger("buildNetworkUrl -> ", response) })
      .catch((err) => { this.errorLogger("buildNetworkUrl -> ", err) });
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
          <Text style={styles.subTitle}>HMSAccountAuthService</Text>
          <Button func={this.signInWithIdToken} text="Sign In With IdToken" />
          <Button func={this.signInWithAuthorizationCode} text="Sign In With AuthorizationCode" />
          <Button func={this.signOut} text="Sign Out" />
          <Button func={this.silentSignIn} text="Silent Sign In" />
          <Button func={this.cancelAuthorization} text="Cancel Authorization" />
          <Button func={this.getIndependentSignIn} text=" Get Independent SignIn" />
          <Button func={this.getChannel} text="Get Channel" />
          <Button func={this.enableLogger} text="Enable logger" />
          <Button func={this.disableLogger} text="Disable logger" />

          <Image style={{ height: 50, width: 50, position: "absolute", top: 10, right: 20 }}
          resizeMode={'contain'} source={{ uri: `data:image/gif;base64,${this.state.icon}` }} />

          <Text style={styles.subTitle}>HMSAccountAuthManager</Text>
          <Button func={this.getAuthResult} text="Get Auth Result" />
          <Button func={this.addAuthScopes} text="Add Auth Scopes" />
          <Button func={this.getAuthResultWithScopes} text="Get Auth Result With Scopes" />
          <Button func={this.containScopes} text="Contain Scopes" />

          <Text style={styles.subTitle}>HMSNetworkTool</Text>
          <Button func={this.buildNetworkCookie} text="Build Network Cookie" />
          <Button func={this.buildNetworkUrl} text="Build Network Url" />

          <Text style={styles.subTitle}>HMSHuaweiIdAuthTool</Text>
          <Button func={this.deleteAuthInfo} text="Delete Auth Info" />
          <Button func={this.requestUnionId} text="Request Union Id" />
          <Button func={this.requestAccessToken} text="Request Access Token" />

          <Text style={styles.subTitle}>HMSReadSMSManager</Text>
          <Button func={this.getHashCode} text="Get Hash Code" />
          <Button func={this.smsVerificationCode} text="Start Read SMS" />
          <Button func={this.startConsent} text="smsWithPhoneNumber" />

          <Text style={styles.subTitle}>HMSAuthButton</Text>
          <Button func={this.getButtonInfo} text="Button Info" />

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
