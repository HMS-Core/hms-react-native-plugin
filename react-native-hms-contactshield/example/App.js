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
import {
  View,
  StyleSheet,
  Button,
  ScrollView,
  NativeEventEmitter,
} from "react-native";
import HMSContactShieldModule, {
  HMSContactShieldSetting,
  HMSTokenMode,
} from "@hmscore/react-native-hms-contactshield";
import FilePickerManager from "react-native-file-picker";

export default class App extends React.Component {

  filePicker(callback) {
    FilePickerManager.showFilePicker(null, (response) => {
      console.log("Response = ", response);

      if (response.didCancel) {
        console.log("User cancelled file picker");
      } else if (response.error) {
        console.log("FilePickerManager Error: ", response.error);
      } else {
          callback(response);
      } 
    });
  }

putSharedKeyFilesKeys(){
  this.filePicker((response) => {
    const args = {
      token: "TOKEN_TEST",
      publicKeys: ["<public_key_list_of_the_shared_key_file>"],
      diagnosisConfiguration: {},
  };
    HMSContactShieldModule.putSharedKeyFilesKeys([response.path], args)
      .then((res) => {
        alert("putSharedKeyFilesKeys: " + res);
      })
      .catch((err) => {
        alert("Error: " + JSON.stringify(err));
      });
  });
}

putSharedKeyFilesProvider() {
  this.filePicker((response) => {
    HMSContactShieldModule.putSharedKeyFilesProvider([response.path])
      .then((res) => {
        alert("putSharedKeyFilesProvider: " + res);
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  });
}

putSharedKeyFilesKeysProvider() {
  this.filePicker((response) => {
    let publicKeys = ["<public_key_list_of_the_shared_key_file>"]
    HMSContactShieldModule.putSharedKeyFilesKeysProvider([response.path], publicKeys)
      .then((res) => {
        alert("putSharedKeyFilesKeysProvider: " + res);
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  });
}

  getDailySketch() {
    const args = {
      dailySketchConfiguration: {},
    };
    HMSContactShieldModule.getDailySketch(args)
    .then((res) => {
      alert("getDailySketch -> success " + JSON.stringify(res));
    }).catch((err) =>   {
      alert(JSON.stringify(err));
    });
}

  getSharedKeysDataMapping() {
    HMSContactShieldModule.getSharedKeysDataMapping()
    .then((res) => {
      alert("getSharedKeysDataMapping: " + JSON.stringify(res));
    })
    .catch((err) => {
      alert("Error: " + err);
    });
  }

  setSharedKeysDataMapping() {
    const params = {
        daysSinceCreationToContagiousness: {
          1: 2,
        },
        defaultContagiousness: 1,
        defaultReportType: 0  
      }
      HMSContactShieldModule.setSharedKeysDataMapping(params)
      .then((res) => {
        alert("setSharedKeysDataMapping: " + JSON.stringify(res));
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  isSupportScanningWithoutLocation() {
    HMSContactShieldModule.isSupportScanningWithoutLocation()
    .then((res) => {
      alert("isSupportScanningWithoutLocation: " + res);
    })
    .catch((err) => {
      alert("Error: " + err);
    });
  }

  getDeviceCalibrationConfidence() {
    HMSContactShieldModule.getDeviceCalibrationConfidence()
    .then((res) => {
      alert("getDeviceCalibrationConfidence: " + res);
    })
    .catch((err) => {
      alert("Error: " + err);
    });
  }

  getContactShieldVersion() {
    HMSContactShieldModule.getContactShieldVersion()
    .then((res) => {
      alert("getContactShieldVersion: " + res);
    })
    .catch((err) => {
      alert("Error: " + err);
    });
  }

  getStatus() {
    HMSContactShieldModule.getStatus()
    .then((res) => {
      alert("getStatus: " + JSON.stringify(res));
    })
    .catch((err) => {
      alert("Error: " + err);
    });
  }

  startContactShield() {
    HMSContactShieldModule.startContactShield(HMSContactShieldSetting.DEFAULT)
      .then((res) => {
        alert("startContactShield: " + res);
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  startContactShieldCallback() {
    HMSContactShieldModule.startContactShieldCallback(
      HMSContactShieldSetting.DEFAULT
    )
      .then((res) => {
        alert("startContactShieldCallback: " + res);
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  stopContactShield() {
    HMSContactShieldModule.stopContactShield()
      .then((res) => {
        alert("stopContactShield: " + res);
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  startContactShieldNoPersistent() {
    HMSContactShieldModule.startContactShieldNoPersistent(
      HMSContactShieldSetting.DEFAULT
    )
      .then((res) => {
        alert("startContactShieldNoPersistent: " + res);
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  putSharedKeyFilesCallback() {
    this.filePicker((response) => {
      const token = "TOKEN_TEST";
      const diagnosisConfiguration = {}
      HMSContactShieldModule.putSharedKeyFilesCallback([response.path], token, diagnosisConfiguration)
        .then((res) => {
          alert("putSharedKeyFilesCallback: " + res);
        })
        .catch((err) => {
          alert("Error: " + err);
        });
    });
  }

  putSharedKeyFiles() {
    this.filePicker((response) => {
      const token = "TOKEN_TEST";
      const diagnosisConfiguration = {}
      HMSContactShieldModule.putSharedKeyFiles([response.path], token, diagnosisConfiguration)
        .then((res) => {
          alert("putSharedKeyFiles: " + res);
        })
        .catch((err) => {
          alert("Error: " + err);
        });
    });
  }

  getContactSketch() {
    const token = "TOKEN_TEST";
    HMSContactShieldModule.getContactSketch(token)
      .then((res) => {
        alert("getContactSketch: " + JSON.stringify(res));
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  getContactDetail() {
    const token = "TOKEN_TEST";
    HMSContactShieldModule.getContactDetail(token)
      .then((res) => {
        alert("getContactDetail: " + JSON.stringify(res));
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  clearAllData() {
    HMSContactShieldModule.clearAllData()
      .then((res) => {
        alert("clearAllData: " + res);
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  getPeriodicKey() {
    HMSContactShieldModule.getPeriodicKey()
      .then((res) => {
        alert("getPeriodicKey: " + JSON.stringify(res));
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  getContactWindow() {
    HMSContactShieldModule.getContactWindow(HMSTokenMode.TOKEN_A)
      .then((res) => {
        alert("getContactWindow: " + JSON.stringify(res));
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  isContactShieldRunning() {
    HMSContactShieldModule.isContactShieldRunning()
      .then((res) => {
        alert("isContactShieldRunning: " + res);
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  getDiagnosisConfiguration() {
    HMSContactShieldModule.getDiagnosisConfiguration()
      .then((res) => {
        alert("getDiagnosisConfiguration: " + JSON.stringify(res));
      })
      .catch((err) => {
        alert("Error: " + err);
      });
  }

  enableLogger() {
    HMSContactShieldModule.enableLogger();
    alert("HMS Plugin Dotting is Enabled!")
  }

  disableLogger() {
    HMSContactShieldModule.disableLogger();
    alert("HMS Plugin Dotting is Disabled!")
  }

  componentDidMount() {
    const eventEmitter = new NativeEventEmitter(HMSContactShieldModule);
    this.eventListener = eventEmitter.addListener("onHasContact", (event) => {
      console.log(event.token);
      alert(event.token);
    });

    this.eventListener = eventEmitter.addListener("onNoContact", (event) => {
      console.log(event.token);
      alert(event.token);
    });
  }

  render() {
    return (
      <View style={styles.mainContainer}>
        <ScrollView style={styles.scrollView} nestedScrollEnabled={true}>
          <View style={styles.container}>
            <Button
              title="Start Contact Shield"
              color="green"
              onPress={() => this.startContactShield()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Start Contact Shield Callback"
              color="green"
              onPress={() => this.startContactShieldCallback()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Stop Contact Shield"
              color="green"
              onPress={() => this.stopContactShield()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Start Contact Shield No Persistent"
              color="green"
              onPress={() => this.startContactShieldNoPersistent()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Get Periodic Key"
              color="green"
              onPress={() => this.getPeriodicKey()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Put Key Callback"
              color="green"
              onPress={() => this.putSharedKeyFilesCallback()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Put Key"
              color="green"
              onPress={() => this.putSharedKeyFiles()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Put Keys with Provider and Keys"
              color="green"
              onPress={() => this.putSharedKeyFilesKeysProvider()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Get Sketch"
              color="green"
              onPress={() => this.getContactSketch()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Get Detail"
              color="green"
              onPress={() => this.getContactDetail()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Clear All Data"
              color="green"
              onPress={() => this.clearAllData()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Get Contact Window Detail"
              color="green"
              onPress={() => this.getContactWindow()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Contact Shield Running"
              color="green"
              onPress={() => this.isContactShieldRunning()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Get Diagnosis Configuration"
              color="green"
              onPress={() => this.getDiagnosisConfiguration()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Enable Logger"
              color="green"
              onPress={() => this.enableLogger()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Disable Logger"
              color="green"
              onPress={() => this.disableLogger()}
            />
          </View>

          
          <View style={styles.container}>
            <Button
              title="GetStatus"
              color="green"
              onPress={() => this.getStatus()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Get CS Version"
              color="green"
              onPress={() => this.getContactShieldVersion()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Get Device Calibration Conf"
              color="green"
              onPress={() => this.getDeviceCalibrationConfidence()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="IsSupport Scan Without Location"
              color="green"
              onPress={() => this.isSupportScanningWithoutLocation()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Set Shared Keys Data Mapping"
              color="green"
              onPress={() => this.setSharedKeysDataMapping()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Get Shared Keys Data Mapping"
              color="green"
              onPress={() => this.getSharedKeysDataMapping()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Get Daily Sketch"
              color="green"
              onPress={() => this.getDailySketch()}
            />
          </View>

          <View style={styles.container}>
            <Button
              title="Put Shared Key Files Provider"
              color="green"
              onPress={() => this.putSharedKeyFilesProvider()}
            />
          </View>
          
          <View style={styles.container}>
            <Button
              title="Put Shared Key Files Keys"
              color="green"
              onPress={() => this.putSharedKeyFilesKeys()}
            />
          </View>
       
        </ScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    borderColor: "green",
    borderWidth: 1,
  },
  textStyle: {
    margin: 0,
    fontSize: 16,
    fontWeight: "bold",
    textAlign: "center",
    color: "#344953",
  },
  top: {
    flex: 1,
    flexDirection: "row",
    justifyContent: "flex-start",
    alignItems: "center",
    margin: 20,
  },
  container: {
    flexDirection: "column",
    justifyContent: "flex-start",
    alignItems: "stretch",
    margin: 20,
  },
  scrollView: {
    flex: 1,
  },
  btn: {
    justifyContent: "center",
    backgroundColor: "green",
  },
});
