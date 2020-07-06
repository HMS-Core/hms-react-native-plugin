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

import React, { useState } from "react";
import { View, Button, Text, StyleSheet } from "react-native";

import RNHMSAccount from "react-native-hms-account";
const styles = StyleSheet.create({
  main: {
    padding: 30,
  },
  btnContainer: {
    marginTop: 20,
  },
  viewcontainer: {
    marginTop: 20,
    height: 38,
  },
});
const App = () => {
  const [log, setLog] = useState("Initialized.");
  const logger = (x) => {
    const json = JSON.stringify(x);
    console.log(json);
    setLog(json + "\n" + log);
  };

  const onSignIn = () => {
    let signInData = {
      huaweiIdAuthParams:
        RNHMSAccount.HmsAccount
          .CONSTANT_HUAWEI_ID_AUTH_PARAMS_DEFAULT_AUTH_REQUEST_PARAM,
      scopes: [RNHMSAccount.HmsAccount.SCOPE_ID_TOKEN],
    };
    RNHMSAccount.HmsAccount.signIn(signInData)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onStartReadSMSManager = () => {
    RNHMSAccount.SMSManager.startReadSMSManager()
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onSignOut = () => {
    RNHMSAccount.HmsAccount.signOut()
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onSilentSignIn = () => {
    RNHMSAccount.HmsAccount.silentSignIn()
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onCancelAuthorization = () => {
    RNHMSAccount.HmsAccount.cancelAuthorization()
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onBuild = () => {
    let buildData = {
      openId: "myopenid",
      uid: "myuid",
      displayName: "mydisplayname",
      photoUrl: "myphotourl",
      accessToken: "myaccesstoken",
      serviceCountryCode: "myservicecountrycode",
      status: 0,
      gender: 0,
      scopes: [RNHMSAccount.HmsAccount.SCOPE_ID_TOKEN],
      serverAuthCode: "myserverAuthCode",
      unionId: "myunionId",
      countryCode: "myCountryCode",
    };
    RNHMSAccount.AuthHuaweiId.build(buildData)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onRequestUnionId = () => {
    let data = {
      huaweiAccountName: "huawei@huawei.com ",
    };
    RNHMSAccount.HuaweiIdAuthTool.requestUnionId(data)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onCreateDefault = () => {
    RNHMSAccount.AuthHuaweiId.createDefault()
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onGetRequestedScopes = () => {
    let buildData = {
      openId: "myopenid",
      uid: "myuid",
      displayName: "mydisplayname",
      photoUrl: "myphotourl",
      accessToken: "myaccesstoken",
      serviceCountryCode: "myservicecountrycode",
      status: 0,
      gender: 0,
      scopes: [RNHMSAccount.HmsAccount.SCOPE_ID_TOKEN],
      serverAuthCode: "myserverAuthCode",
      unionId: "myunionId",
      countryCode: "myCountryCode",
    };
    RNHMSAccount.AuthHuaweiId.getRequestedScopes(buildData)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onGetAuthResult = () => {
    RNHMSAccount.HuaweiIdAuthManager.getAuthResult()
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onContainScopes = () => {
    let buildData = {
      authHuaweiId: {
        openId: "myopenid",
        uid: "myuid",
        displayName: "mydisplayname",
        photoUrl: "myphotourl",
        accessToken: "myaccesstoken",
        serviceCountryCode: "myservicecountrycode",
        status: 0,
        gender: 0,
        scopes: [RNHMSAccount.HmsAccount.SCOPE_ID_TOKEN],
        serverAuthCode: "myserverAuthCode",
        unionId: "myunionId",
        countryCode: "myCountryCode",
      },
      scopes: [RNHMSAccount.HmsAccount.SCOPE_ID_TOKEN],
    };
    RNHMSAccount.HuaweiIdAuthManager.containScopes(buildData)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onAddAuthScopes = () => {
    let buildData = {
      requestCode: 888,
      scopes: [RNHMSAccount.HmsAccount.SCOPE_ID_TOKEN],
    };
    RNHMSAccount.HuaweiIdAuthManager.addAuthScopes(buildData)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onGetAuthResultWithScopes = () => {
    let buildData = {
      scopes: [RNHMSAccount.HmsAccount.SCOPE_ID_TOKEN],
    };
    RNHMSAccount.HuaweiIdAuthManager.getAuthResultWithScopes(buildData)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onDeleteAuthInfo = () => {
    let buildData = {
      accessToken: "myAccessToken",
    };
    RNHMSAccount.HuaweiIdAuthTool.deleteAuthInfo(buildData)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onRequestAccessToken = () => {
    let data = {
      scopes: ["profile"],
      huaweiAccount: {
        name: "huawei@huawei.com",
        type: "com.huawei.hwid",
      },
    };
    RNHMSAccount.HuaweiIdAuthTool.requestAccessToken(data)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onGetExtendedAuthResult = () => {
    let buildData = {
      huaweiIdExtendedAuthResult: [
        RNHMSAccount.HmsAccount.CONSTANT_HUAWEI_ID_AUTH_EXTENDED_PARAMS_FITNESS,
      ],
    };
    RNHMSAccount.HuaweiIdAuthManager.getExtendedAuthResult(buildData)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onGetHashCode = () => {
    RNHMSAccount.SMSManager.getHashCode()
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onBuildNetworkCookie = () => {
    let buildData = {
      cookieName:"mycookiename",
      cookieValue: "mycookievalue",
      domain: "mydomain",
      path: "mypath",
      isHttpOnly: true,
      isSecure: true,
      maxAge: 130.0,
    };
    RNHMSAccount.NetworkTool.buildNetWorkCookie(buildData)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  const onBuildNetworkUrl = () => {
    let buildData = {
      isUseHttps:true,
      domain:"mydomain",
    };
    RNHMSAccount.NetworkTool.buildNetworkUrl(buildData)
      .then((response) => {
        logger(JSON.stringify(response));
      })
      .catch((err) => {
        logger(err);
      });
  };

  return (
    <View style={styles.main}>
      <View style={styles.btnContainer}>
        <Button title="Sign in" onPress={onSignIn} />
      </View>

      <View style={styles.btnContainer}>
        <Button title="Sign out" onPress={onSignOut} />
      </View>

      <View style={styles.btnContainer}>
        <Button title="Silent sign in" onPress={onSilentSignIn} />
      </View>

      <View style={styles.btnContainer}>
        <Button title="onCancelAuthorization" onPress={onCancelAuthorization} />
      </View>

      <View style={styles.btnContainer}>
        <Button title="onAddAuthScopes" onPress={onAddAuthScopes} />
      </View>

      <View style={styles.btnContainer}>
        <Button
          title="Retrieve SMS verification code"
          onPress={onStartReadSMSManager}
        />
      </View>

      <RNHMSAccount.HuaweiIdAuthButton
        style={styles.viewcontainer}
        colorPolicy={
          RNHMSAccount.HmsAccount
            .CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_WHITE_WITH_BORDER
        }
        enabled={false}
        theme={
          RNHMSAccount.HmsAccount
            .CONSTANT_HUAWEI_ID_AUTH_BUTTON_THEME_FULL_NO_TITLE
        }
        cornerRadius={
          RNHMSAccount.HmsAccount
            .CONSTANT_HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_MEDIUM
        }
        onPress={onSignIn}
      />
      <Text style={styles.btnContainer}>{log}</Text>
    </View>
  );
};

export default App;
