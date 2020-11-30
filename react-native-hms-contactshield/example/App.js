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

  filePicker(callback) {
    FilePickerManager.showFilePicker(null, (response) => {
      console.log("Response = ", response);

      if (response.didCancel) {
        console.log("User cancelled file picker");
      } else if (response.error) {
        console.log("FilePickerManager Error: ", response.error);
      } else {
        if (response.type === "application/zip") {
          callback(response);
        }
      }
    });
  }

  putSharedKeyFilesCallback() {
    this.filePicker((response) => {
      const token = "TOKEN_TEST";
      HMSContactShieldModule.putSharedKeyFilesCallback(response.path, token)
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
      HMSContactShieldModule.putSharedKeyFiles(response.path, token)
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
  }

  disableLogger() {
    HMSContactShieldModule.disableLogger();
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
