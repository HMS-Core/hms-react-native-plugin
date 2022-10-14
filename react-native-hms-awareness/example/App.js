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
  DrawerLayoutAndroid,
  ScrollView,
} from "react-native";
import {
  buttonWidth,
  ScreenWidth,
  Page,
  ScreenHeight,
  captureDesc,
  barrierDesc,
  captureConditions,
  barrierConditions,
} from "./constants/Data.js";
import {
  platinum,
  awesomepink,
  darkJungleGreen,
  green,
} from "./constants/Colors.js";
import BarrierPage from "./components/Barrier";
import CapturePage from "./components/Capture";
import { HMSLoggerModule } from "@hmscore/react-native-hms-awareness";

export default class App extends React.Component {
  constructor() {
    super();
    this.state = {
      showProgress: false,
      drawerState: false,
      page: Page.Capture,
    };
  }

  async enableLogger() {
    try {
      var res = await HMSLoggerModule.enableLogger();
      alert(JSON.stringify(res));
    } catch (e) {
      alert(JSON.stringify(e));
    }
  }

  async disableLogger() {
    try {
      var res = await HMSLoggerModule.disableLogger();
      alert(JSON.stringify(res));
    } catch (e) {
      alert(JSON.stringify(e));
    }
  }

  changeSelectPage(selectPage) {
    this.setState({ page: selectPage });
  }

  render() {
    drawerView = (
      <View style={styles.drawerContainer}>
        <TouchableOpacity
          style={styles.closeBtn}
          onPress={() => this._drawer.closeDrawer()}
        >
          <Text
            style={styles.cancelIcon}
          >x</Text>
        </TouchableOpacity>
        {this.state.page == Page.Capture ? (
          <ScrollView>
            <Text style={styles.txt}>Awareness Capture Api</Text>
            <Text style={styles.descTxt}>{captureDesc}</Text>
            <Text style={styles.warning}>Warnings !</Text>
            <Text style={styles.descTxt}>{captureConditions}</Text>
          </ScrollView>
        ) : (
          <ScrollView>
            <Text style={styles.txt}>Awareness Barrier Api</Text>
            <Text style={styles.descTxt}>{barrierDesc}</Text>
            <Text style={styles.warning}>Warnings !</Text>
            <Text style={styles.descTxt}>{barrierConditions}</Text>
          </ScrollView>
        )}
      </View>
    );
    return (
      <DrawerLayoutAndroid
        ref={(component) => (this._drawer = component)}
        drawerWidth={300}
        drawerPosition={DrawerLayoutAndroid.positions.Right}
        renderNavigationView={() => drawerView}
      >
        <View style={styles.mainContainer}>
          <View style={styles.header}>
            <Text style={styles.headerTitle}>HMS Awareness Plugin</Text>
            <TouchableOpacity
              style={styles.logoIcon}
              onPress={() => this._drawer.openDrawer()}
            >
              <Text style={styles.logoText}>{'HUAWEI >'}</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.container}>
            {this.state.page == Page.Capture ? (
              <CapturePage />
            ) : (
              <BarrierPage />
            )}
          </View>

          <View style={styles.navigationBar}>
            <TouchableOpacity
              onPress={() => this.changeSelectPage(Page.Capture)}
              style={
                this.state.page == Page.Capture
                  ? styles.activeButton
                  : styles.passiveButton
              }
            >
              <Text style={styles.txt}>Capture</Text>
              <Text style={styles.captureLogoTxt}>{'| o |'}</Text>

            </TouchableOpacity>

            <TouchableOpacity
              onPress={() => this.changeSelectPage(Page.Barrier)}
              style={
                this.state.page == Page.Barrier
                  ? styles.activeButton2
                  : styles.passiveButton2
              }
            >
              <Text style={styles.txt}>Barrier</Text>
            
              <Text style={styles.barrierLogoTxt}>{'//////'}</Text>
            </TouchableOpacity>
          </View>
        </View>
      </DrawerLayoutAndroid>
    );
  }
}

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: platinum,
    borderColor: darkJungleGreen,
    borderWidth: 1,
    height: 110,
  },
  drawerContainer: {
    flex: 1,
    paddingTop: 30,
    backgroundColor: "gray",
    padding: 8,
  },
  container: {
    flex: 1,
    justifyContent: "center",
    height: ScreenHeight - 160,
    paddingBottom: 50,
    alignSelf: "center",
    width: ScreenWidth,
  },
  header: {
    backgroundColor: "black",
    height: 100,
    width: ScreenWidth,
    flexDirection: "row",
    borderBottomColor: platinum,
    borderBottomWidth: 1,
  },
  headerTitle: {
    fontSize: 22,
    fontWeight: "bold",
    color: platinum,
    flex: 1.1,
    alignSelf: "center",
    marginLeft: 20,
  },
  logoText: {
    fontSize: 17,
    fontWeight: "bold",
    alignSelf: 'center',
    color: green,
    textDecorationLine: 'underline',
    fontVariant: ['small-caps'],
    textDecorationStyle: "dotted",
    fontStyle: 'italic'
  },
  captureLogoTxt: {
    fontSize: 22,
    marginTop: 13,
    fontWeight: "bold",
    textAlignVertical: 'center',
    color: platinum,
    marginLeft: 50,
    height: 35,
    paddingBottom: 8,
    textDecorationStyle: "dotted",
    borderTopColor: platinum,
    borderTopWidth: 2,
    borderBottomColor: platinum,
    borderBottomWidth: 2,
  },
  barrierLogoTxt: {
    fontSize: 25,
    position:'absolute',
    fontWeight:"bold",
    right:20,
    overflow:'hidden',
    color: platinum,
    textAlign:'center',
    alignSelf:'center',
    textDecorationLine:'line-through',
    textDecorationStyle:'dashed',
    textDecorationColor:platinum,
  },
  txt: {
    fontSize: 19,
    color: platinum,
    alignSelf: "center",
    marginLeft: 20,
    fontWeight: "bold",
  },
  warning: {
    fontSize: 18,
    color: "yellow",
    marginLeft: 15,
    marginTop: 15,
    fontWeight: "bold",
  },
  title: {
    fontSize: 18,
    color: awesomepink,
    width: buttonWidth,
    alignSelf: "center",
    borderBottomColor: awesomepink,
    borderBottomWidth: 0.7,
    textAlign: "center",
    marginTop: 30,
  },
  navigationBar: {
    width: ScreenWidth,
    position: "absolute",
    bottom: 0,
    height: 55,
    justifyContent: "center",
    flexDirection: "row",
    borderWidth: 1,
    zIndex: 50,
    backgroundColor: darkJungleGreen,
  },
  activeButton: {
    flex: 1,
    backgroundColor: green,
    flexDirection: "row",
    opacity: 1,
    height: 55,
    borderRightColor: darkJungleGreen,
    borderRightWidth: 1,
  },
  passiveButton: {
    flex: 1,
    height: 55,
    backgroundColor: darkJungleGreen,
    flexDirection: "row",
    borderRightColor: darkJungleGreen,
    borderRightWidth: 1,
  },
  activeButton2: {
    flex: 1,
    height: 55,
    backgroundColor: green,
    flexDirection: "row",
    opacity: 1,
  },
  passiveButton2: {
    flex: 1,
    height: 55,
    backgroundColor: darkJungleGreen,
    flexDirection: "row",
  },
  logoIcon: {
    width: 95,
    height: 95,
    justifyContent: 'center'
  },
  icon: {
    width: 30,
    height: 30,
    right: 20,
    position: "absolute",
    alignSelf: "center",
  },
  cancelIcon: {
    width: 30,
    height: 30,
    marginLeft: 5,
    fontSize: 20,
    color: awesomepink,
    borderRadius: 30,
    borderWidth: 2,
    borderColor: awesomepink,
    textAlign: 'center'
  },
  descTxt: {
    padding: 10,
    color: platinum,
  },
});
