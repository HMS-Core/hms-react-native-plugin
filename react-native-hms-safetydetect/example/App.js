/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

import React from 'react'
import { View, Text, Image, StyleSheet, ScrollView, TouchableOpacity, Alert } from 'react-native'
import { HMSHuaweiApi, HMSSysIntegrity, HMSUserDetect, HMSWifiDetect, HMSUrlCheck, HMSAppsCheck } from '@hmscore/react-native-hms-safetydetect';

const App = () => {
  const checkHMSStatus = () => {
    HMSHuaweiApi.isHuaweiMobileServicesAvailable().then(response => {
      console.log(response)
      Alert.alert("Is Huawei Mobile Services Available", response.toString())
    }).catch(error => {
      console.log(error)
      Alert.alert("Error", error.toString())
    });
  }

  const checkSysIntegrity = () => {
    const appId = "<Your_App_Id>";
    const nonce = "Sample" + Date.now();
    HMSSysIntegrity.sysIntegrity(nonce, appId).then(response => {
      console.log("sysIntegrity: " + response);
      Alert.alert("sysIntegrity", response)
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const checkSysIntegrityRequest = () => {
    const args = {
      appId: "<Your_App_Id>",
      nonce: "Sample" + Date.now(),
      alg: "RS256",
    };
    HMSSysIntegrity.sysIntegrityWithRequest(args).then(response => {
      console.log("sysIntegrityWithRequest: " + response);
      Alert.alert("sysIntegrityWithRequest", response)
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const initCheckUser = () => {
    HMSUserDetect.initUserDetect().then(response => {
      console.log("initUserDetect: " + response);
      Alert.alert("initUserDetect", response.toString())
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const userDetection = () => {
    const appId = "<Your_App_Id>";
    HMSUserDetect.userDetection(appId).then(response => {
      console.log("userDetection: " + response);
      Alert.alert("userDetection", response.toString())
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const shutdownCheckUser = () => {
    HMSUserDetect.shutdownUserDetect().then(response => {
      console.log("shutdownUserDetect: " + response);
      Alert.alert("shutdownUserDetect", response.toString())
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const checkWifi = () => {
    HMSWifiDetect.getWifiDetectStatus().then(response => {
      console.log(response);
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    });
  }

  const initUrlCheck = () => {
    HMSUrlCheck.initUrlCheck().then(response => {
      console.log("initUrlCheck: " + response);
      Alert.alert("initUrlCheck", response.toString())
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const checkUrl = () => {
    const params = {
      "appId": "<Your_App_Id>",
      "url": " http://example.com/hms/safetydetect/malware",
      "UrlCheckThreat": [HMSUrlCheck.MALWARE, HMSUrlCheck.PHISHING]
    }
    HMSUrlCheck.urlCheck(params).then(response => {
      console.log("urlCheck: " + response);
      Alert.alert("urlCheck", JSON.stringify(response))
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const shutdownUrlCheck = () => {
    HMSUrlCheck.shutdownUrlCheck().then(response => {
      console.log("shutdownUrlCheck: " + response);
      Alert.alert("shutdownUrlCheck", response.toString())
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const enableAppsCheck = () => {
    HMSAppsCheck.enableAppsCheck().then(response => {
      console.log("enableAppsCheck: " + response);
      Alert.alert("enableAppsCheck", response.toString())
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const isVerifyAppsCheck = () => {
    HMSAppsCheck.isVerifyAppsCheck().then(response => {
      console.log("isVerifyAppsCheck: " + response);
      Alert.alert("isVerifyAppsCheck", response.toString())
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const checkApps = () => {
    HMSAppsCheck.getMaliciousAppsList().then(response => {
      console.log(response);
      Alert.alert("getMaliciousAppsList", JSON.stringify(response))
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    });
  }

  const initAntiFraud = () => {
    const appId = "<Your_App_Id>";
    HMSUserDetect.initAntiFraud(appId).then(response => {
      console.log("initAntiFraud: " + response);
      Alert.alert("initAntiFraud", response.toString())
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const getRiskToken = () => {
    HMSUserDetect.getRiskToken().then(response => {
      console.log("getRiskToken: " + response);
      Alert.alert("getRiskToken", response.toString())
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const releaseAntiFraud = () => {
    HMSUserDetect.releaseAntiFraud().then(response => {
      console.log("releaseAntiFraud: " + response);
      Alert.alert("releaseAntiFraud", response.toString())
    }).catch(error => {
      console.log(error);
      Alert.alert("Error", error.toString())
    })
  }

  const data = [
    {
      id: 0,
      title: "HMS Status",
      methods: [
        {
          id: 0,
          buttonText: "Check HMS Status",
          buttonClick: () => checkHMSStatus()
        }
      ]
    },
    {
      id: 1,
      title: "System Integrity",
      methods: [
        {
          id: 0,
          buttonText: "Check System Integrity",
          buttonClick: () => checkSysIntegrity()
        }
      ]
    },
    {
      id: 7,
      title: "System Integrity With Request",
      methods: [
        {
          id: 0,
          buttonText: "Check System Integrity With Request",
          buttonClick: () => checkSysIntegrityRequest()
        }
      ]
    },
    {
      id: 2,
      title: "Check Malicious Apps",
      methods: [
        {
          id: 0,
          buttonText: "Enable",
          buttonClick: () => enableAppsCheck()
        },
        {
          id: 1,
          buttonText: "Check Apps",
          buttonClick: () => checkApps()
        },
        {
          id: 2,
          buttonText: "Verify App Check",
          buttonClick: () => isVerifyAppsCheck()
        },
      ]
    },
    {
      id: 3,
      title: "Check Malicious Url",
      methods: [
        {
          id: 0,
          buttonText: "Initialize",
          buttonClick: () => initUrlCheck()
        },
        {
          id: 1,
          buttonText: "Check Url",
          buttonClick: () => checkUrl()
        },
        {
          id: 2,
          buttonText: "Shutdown",
          buttonClick: () => shutdownUrlCheck()
        },
      ]
    },
    {
      id: 4,
      title: "Fake User Identification",
      methods: [
        {
          id: 0,
          buttonText: "Initialize",
          buttonClick: () => initCheckUser()
        },
        {
          id: 1,
          buttonText: "User Detection",
          buttonClick: () => userDetection()
        },
        {
          id: 2,
          buttonText: "Shutdown",
          buttonClick: () => shutdownCheckUser()
        },
      ]
    },
    {
      id: 5,
      title: "Imperceptible Fake User Identification",
      methods: [
        {
          id: 0,
          buttonText: "Initialize",
          buttonClick: () => initAntiFraud()
        },
        {
          id: 1,
          buttonText: "Get Risk Token",
          buttonClick: () => getRiskToken()
        },
        {
          id: 2,
          buttonText: "Shutdown",
          buttonClick: () => releaseAntiFraud()
        },
      ]
    },
    {
      id: 6,
      title: "Check WiFi",
      methods: [
        {
          id: 0,
          buttonText: "Check WiFi",
          buttonClick: () => checkWifi()
        },
      ]
    }
  ]

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <View style={{ flex: 1, justifyContent: "center", alignItems: "center", }}>
          <Text style={styles.headerTitle}>HMS React Native Safety Detect Plugin</Text>
        </View>
        <Image
          style={styles.headerImage}
          source={require('./src/assets/hms-rn-logo.png')} />
      </View>
      <ScrollView>
        <View style={styles.buttonsContainer}>
          {data.map((section) => (
            <View
              key={section.id}
              style={styles.section}
            >
              <Text style={styles.sectionTitle} >{section.title}</Text>
              {section.methods.map((method) => (
                <TouchableOpacity
                  key={method.id}
                  onPress={method.buttonClick}
                  style={styles.button}
                >
                  <Text style={styles.buttonText} >{method.buttonText}</Text>
                </TouchableOpacity>
              ))}
            </View>
          ))}
        </View>
      </ScrollView>
    </View >
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#1c1c1c",
  },
  header: {
    height: 60,
    justifyContent: "space-between",
    alignItems: "center",
    flexDirection: "row",
    marginVertical: 40,
    marginHorizontal: 20,
  },
  headerTitle: {
    fontWeight: "bold",
    color: "#909090",
    fontSize: 20,
  },
  headerImage: {
    resizeMode: "center",
    width: 100,
    height: 100,
  },
  buttonsContainer: {
    flex: 1
  },
  section: {
    flex: 1,
    padding: 10,
  },
  sectionTitle: {
    fontSize: 14,
    fontWeight: "bold",
    color: "#909090",
  },
  button: {
    height: 50,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#2e2e2e",
    marginVertical: 8,
    elevation: 5,
    borderRadius: 5,
  },
  buttonText: {
    fontSize: 18,
    fontWeight: "bold",
    color: "#909090",
  },
});



export default App
