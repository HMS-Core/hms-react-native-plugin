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
  StyleSheet,
  TouchableOpacity,
  Text,
  View,
  ActivityIndicator,
  ScrollView,
} from "react-native";

import { platinum, darkJungleGreen, green } from "../constants/Colors.js";
import {
  ScreenWidth,
  ScreenHeight,
  buttonWidth,
  buttonHeight,
} from "../constants/Data.js";

import { HMSAwarenessCaptureModule } from "@hmscore/react-native-hms-awareness";

export default class Capture extends React.Component {
  constructor() {
    super();
    this.state = {
      showProgress: false,
      drawerState: false,
    };
  }

  changeStateProgress(isProgressShowing) {
    this.setState({ showProgress: isProgressShowing });
  }

  /**
   * @param  BeaconStatusReq : Object
   * Uses a variable number of the filters parameters to obtain beacon information.
   * Try it out with a beacon device registered on App Gallery Connect.
   */
  async getBeaconStatus() {
    try {
      this.changeStateProgress(true);
      const BeaconStatusReq = {
        namespace: "dev736430079244559178",
        type: "ibeacon",
        content: "content",
      };
      var BeaconStatusResponse = await HMSAwarenessCaptureModule.getBeaconStatus(
        BeaconStatusReq
      );
      alert(JSON.stringify(BeaconStatusResponse));
      this.changeStateProgress(false);
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains current user behavior, for example, running, walking, cycling, or driving.
   */
  async getBehavior() {
    try {
      this.changeStateProgress(true);
      var BehaviorResponse = await HMSAwarenessCaptureModule.getBehavior();
      alert(JSON.stringify(BehaviorResponse));
      this.changeStateProgress(false);
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains headset connection status.
   */
  async getHeadsetStatus() {
    try {
      this.changeStateProgress(true);
      var HeadsetStatusResponse = await HMSAwarenessCaptureModule.getHeadsetStatus();
      this.changeStateProgress(false);
      alert(JSON.stringify(HeadsetStatusResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Re-obtains the latest device location information (latitude and longitude).
   */
  async getLocation() {
    try {
      this.changeStateProgress(true);
      var LocationResponse = await HMSAwarenessCaptureModule.getLocation();
      this.changeStateProgress(false);
      alert(JSON.stringify(LocationResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the current location (latitude and longitude) of a device.
   */
  async getCurrentLocation() {
    try {
      this.changeStateProgress(true);
      var LocationResponse = await HMSAwarenessCaptureModule.getCurrentLocation();
      this.changeStateProgress(false);
      alert(JSON.stringify(LocationResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the current time.
   */
  async getTimeCategories() {
    try {
      this.changeStateProgress(true);
      var TimeCategoriesResponse = await HMSAwarenessCaptureModule.getTimeCategories();
      this.changeStateProgress(false);
      alert(JSON.stringify(TimeCategoriesResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * TimeCategoriesByUserReq : Object
   * Obtains the current time of a specified location.
   */
  async getTimeCategoriesByUser() {
    try {
      this.changeStateProgress(true);
      const TimeCategoriesByUserReq = {
        latitude: 36.314909917631724,
        longitude: 30.166312199567606,
      };
      var TimeCategoriesResponse = await HMSAwarenessCaptureModule.getTimeCategoriesByUser(
        TimeCategoriesByUserReq
      );
      this.changeStateProgress(false);
      alert(JSON.stringify(TimeCategoriesResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * @param  TimeCategoriesByCountryCode : Object
   * Obtains the current time by country/region code that complies with ISO 3166-1 alpha-2.
   */
  async getTimeCategoriesByCountryCode() {
    try {
      this.changeStateProgress(true);
      const TimeCategoriesByCountryCodeReq = {
        countryCode: "TR",
      };

      var TimeCategoriesResponse = await HMSAwarenessCaptureModule.getTimeCategoriesByCountryCode(
        TimeCategoriesByCountryCodeReq
      );
      this.changeStateProgress(false);
      alert(JSON.stringify(TimeCategoriesResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the current time by IP address.
   */
  async getTimeCategoriesByIP() {
    try {
      this.changeStateProgress(true);
      var TimeCategoriesResponse = await HMSAwarenessCaptureModule.getTimeCategoriesByIP();
      this.changeStateProgress(false);
      alert(JSON.stringify(TimeCategoriesResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the time of a specified date by IP address.
   */
  async getTimeCategoriesForFuture() {
    try {
      this.changeStateProgress(true);
      const TimeCategoriesForFutureReq = {
        futureTimestamp: 1606906743000.0,
      };
      var TimeCategoriesResponse = await HMSAwarenessCaptureModule.getTimeCategoriesForFuture(
        TimeCategoriesForFutureReq
      );
      this.changeStateProgress(false);
      alert(JSON.stringify(TimeCategoriesResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the illuminance.
   */
  async getLightIntensity() {
    try {
      this.changeStateProgress(true);
      var AmbientLightResponse = await HMSAwarenessCaptureModule.getLightIntensity();
      this.changeStateProgress(false);
      alert(JSON.stringify(AmbientLightResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the weather of the current location of a device.
   */
  async getWeatherByDevice() {
    try {
      this.changeStateProgress(true);
      var WeatherStatusResponse = await HMSAwarenessCaptureModule.getWeatherByDevice();
      this.changeStateProgress(false);
      alert(JSON.stringify(WeatherStatusResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the weather status response by IP address.
   */
  async getWeatherByPosition() {
    try {
      this.changeStateProgress(true);
      const WeatherByPositionReq = {
        city: "London",
        locale: "en_GB",
        country: "United Kingdom",
        province: "London",
      };
      var WeatherStatusResponse = await HMSAwarenessCaptureModule.getWeatherByPosition(
        WeatherByPositionReq
      );
      this.changeStateProgress(false);
      alert(JSON.stringify(WeatherStatusResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the Bluetooth connection status.
   */
  async getBluetoothStatus() {
    try {
      this.changeStateProgress(true);
      var BluetoothStatusResponse = await HMSAwarenessCaptureModule.getBluetoothStatus();
      this.changeStateProgress(false);
      alert(JSON.stringify(BluetoothStatusResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains capabilities supported by Awareness Kit on the current device.
   */
  async querySupportingCapabilities() {
    try {
      this.changeStateProgress(true);
      var CapabilityResponse = await HMSAwarenessCaptureModule.querySupportingCapabilities();
      this.changeStateProgress(false);
      alert(JSON.stringify(CapabilityResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the screen status response of a device.
   */
  async getScreenStatus() {
    try {
      this.changeStateProgress(true);
      var ScreenStatusResponse = await HMSAwarenessCaptureModule.getScreenStatus();
      this.changeStateProgress(false);
      alert(JSON.stringify(ScreenStatusResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the Wi-Fi connection status of a device.
   */
  async getWifiStatus() {
    try {
      this.changeStateProgress(true);
      var WifiStatusResponse = await HMSAwarenessCaptureModule.getWifiStatus();
      this.changeStateProgress(false);
      alert(JSON.stringify(WifiStatusResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the app status of a device.
   */
  async getApplicationStatus() {
    try {
      this.changeStateProgress(true);
      var ApplicationStatusResponse = await HMSAwarenessCaptureModule.getApplicationStatus();
      this.changeStateProgress(false);
      alert(JSON.stringify(ApplicationStatusResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Obtains the dark mode status of a device.
   */
  async getDarkModeStatus() {
    try {
      this.changeStateProgress(true);
      var DarkModeStatusResponse = await HMSAwarenessCaptureModule.getDarkModeStatus();
      this.changeStateProgress(false);
      alert(JSON.stringify(DarkModeStatusResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Indicates whether to display a dialog box before Awareness Kit or HMS Core (APK) starts an upgrade in your app.
   */
  async enableUpdateWindow() {
    try {
      this.changeStateProgress(true);
      const isEnabled = true;
      var res = await HMSAwarenessCaptureModule.enableUpdateWindow(isEnabled);
      this.changeStateProgress(false);
      alert(JSON.stringify(res));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  render() {
    return (
      <View style={styles.captureContainer}>
        {this.state.showProgress ? (
          <View style={styles.disableOverlay}>
            <ActivityIndicator
              size={"large"}
              color={darkJungleGreen}
              style={styles.activityIndicator}
            />
          </View>
        ) : null}

        <ScrollView style={styles.scrollView}>
          <View style={styles.container}>
            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.querySupportingCapabilities()}
            >
              <Text style={styles.txt}>querySupportingCapabilities</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getBeaconStatus()}
            >
              <Text style={styles.txt}>getBeaconStatus</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getBehavior()}
            >
              <Text style={styles.txt}>getBehavior</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getHeadsetStatus()}
            >
              <Text style={styles.txt}>getHeadsetStatus</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getLocation()}
            >
              <Text style={styles.txt}>getLocation</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getCurrentLocation()}
            >
              <Text style={styles.txt}>getCurrentLocation</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getTimeCategories()}
            >
              <Text style={styles.txt}>getTimeCategories</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getTimeCategoriesByUser()}
            >
              <Text style={styles.txt}>getTimeCategoriesByUser</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getTimeCategoriesByCountryCode()}
            >
              <Text style={styles.txt}>getTimeCategoriesByCountryCode</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getTimeCategoriesByIP()}
            >
              <Text style={styles.txt}>getTimeCategoriesByIP</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getTimeCategoriesForFuture()}
            >
              <Text style={styles.txt}>getTimeCategoriesForFuture</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getLightIntensity()}
            >
              <Text style={styles.txt}>getLightIntensity</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getWeatherByDevice()}
            >
              <Text style={styles.txt}>getWeatherByDevice</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getWeatherByPosition()}
            >
              <Text style={styles.txt}>getWeatherByPosition</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getBluetoothStatus()}
            >
              <Text style={styles.txt}>getBluetoothStatus</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.enableUpdateWindow()}
            >
              <Text style={styles.txt}>enableUpdateWindow</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getScreenStatus()}
            >
              <Text style={styles.txt}>getScreenStatus</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getWifiStatus()}
            >
              <Text style={styles.txt}>getWifiStatus</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getApplicationStatus()}
            >
              <Text style={styles.txt}>getApplicationStatus</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.captureBtn}
              onPress={() => this.getDarkModeStatus()}
            >
              <Text style={styles.txt}>getDarkModeStatus</Text>
            </TouchableOpacity>
          </View>
        </ScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  captureContainer: {
    flex: 1,
    backgroundColor: "white",
    borderColor: darkJungleGreen,
    borderWidth: 1,
  },
  disableOverlay: {
    zIndex: 10,
    backgroundColor: "gray",
    opacity: 0.8,
    position: "absolute",
    width: ScreenWidth,
    height: ScreenHeight,
  },
  activityIndicator: {
    top: 100,
    alignSelf: "center",
    position: "absolute",
    zIndex: 11,
  },
  scrollView: {
    flex: 1,
  },
  container: {
    flex: 1,
    justifyContent: "center",
    paddingBottom: 50,
    alignSelf: "center",
  },
  captureBtn: {
    marginTop: 15,
    width: buttonWidth,
    height: buttonHeight,
    justifyContent: "center",
    alignSelf: "center",
    borderRadius: 10,
    marginLeft: 5,
    marginRight: 5,
    borderColor: darkJungleGreen,
    borderWidth: 1.5,
  },
  txt: {
    fontSize: 14,
    color: green,
    textAlign: "center",
  },
});
