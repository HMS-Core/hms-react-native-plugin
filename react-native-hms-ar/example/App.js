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
  StyleSheet,
  PermissionsAndroid,
  View,
  Text,
  BackHandler,
  TouchableHighlight,
  Image,
} from "react-native";

import ARView, {
  HmsARModule,
} from "@hmscore/react-native-hms-ar";

const Hand = () => {
  return (
    <ARView
      style={styles.arView}
      onDrawFrame={(e) => console.log(e)}
      config={{
        hand: {
          boxColor: {
            red: 255,
            blue: 0,
            green: 0,
            alpha: 255,
          },
          lineWidth: 30,
          drawBox: true,
        },
      }}
    />
  );
};

const Body = () => {
  return (
    <ARView
      style={styles.arView}
      onDrawFrame={(e) => console.log(e)}
      config={{
        body: {
          drawLine: true,
          drawPoint: true,
          lineWidth: 30,
          pointSize: 40,
          lineColor: {
            red: 0,
            blue: 0,
            green: 255,
            alpha: 255,
          },
          pointColor: {
            red: 255,
            blue: 128,
            green: 255,
            alpha: 192,
          },
        },
      }}
    />
  );
};

const Face = () => {
  return (
    <ARView
      style={styles.arView}
      onDrawFrame={(e) => console.log(e)}
      config={{
        face: {
          drawFace: true,
          pointSize: 0,
          depthColor: {
            red: 255,
            blue: 255,
            green: 255,
            alpha: 0,
          },
          texturePath: "blueTexture.png",
        },
      }}
    />
  );
};

const World = () => {
  return (
    <ARView
      style={styles.arView}
      onDrawFrame={(e) => console.log(e)}
      config={{
        world: {
          objectName: "bob.obj",
          objectTexture: "bob_texture.png",
          showPlanes: true,
          planeOther: {
            image: "blueTexture.png",
          },
          planeWall: {
            text: "Wall",
            red: 255,
            blue: 255,
            green: 0,
            alpha: 255,
          },
          planeFloor: {
            text: "Floor",
            red: 255,
            blue: 255,
            green: 0,
            alpha: 255,
          },
          planeSeat: {
            text: "Seat",
            red: 255,
            blue: 255,
            green: 0,
            alpha: 255,
          },
          planeTable: {
            image: "blueTexture.png",
          },
          planeCeiling: {
            image: "blueTexture.png",
          },
        },
      }}
    />
  );
};

const scenes = [
  {
    name: "HAND",
    id: 0,
    component: <Hand key="Hand" />,
  },
  {
    name: "BODY",
    id: 1,
    component: <Body key="Body" />,
  },
  {
    name: "FACE",
    id: 2,
    component: <Face key="Face" />,
  },
  {
    name: "WORLD",
    id: 3,
    component: <World key="World" />,
  },
];
class App extends React.Component {
  constructor() {
    super();
    this.state = {
      isMain: true,
      sceneId: 0,
    };
    this.requestPermissions();
  }

  handleBackButton = () => {
    this.setState({ isMain: true });
    return true;
  };

  componentDidMount() {
    BackHandler.addEventListener("hardwareBackPress", this.handleBackButton);
    HmsARModule.isAREngineReady().then((response) => {
      // Checks weather HUAWEI AR Engine is installed.
      console.log(response);
      if (response === false) {
        HmsARModule.navigateToAppMarket();
      }
    });
  }

  componentWillUnmount() {
    BackHandler.removeEventListener("hardwareBackPress", this.handleBackButton);
  }

  requestPermissions = async () => {
    try {
      const granted = await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.CAMERA
      );
      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
        console.log("You can use the camera");
      } else {
        console.log("Camera permission denied");
      }
    } catch (err) {
      console.warn(err);
    }
  };

  render() {
    return this.state.isMain ? (
      <View style={styles.inputsContainer}>
        <View style={styles.header}>
            <Text style={styles.headerTitle}>HMS React Native AR Plugin</Text>
            <Image 
              style={styles.headerImage}
              source={require('./src/assets/hms-rn-logo.png')}/>
        </View>
        <View style={styles.buttonsContainer}>
          {scenes.map((scene) => (
            <TouchableHighlight
              key={scene.id}
              style={styles.button}
              onPress={() => this.setState({ isMain: false, sceneId: scene.id })}
            >
              <Text style={styles.buttonText}>{scene.name}</Text>
            </TouchableHighlight>
          ))}
        </View>
      </View>
    ) : (
      <View style={styles.container}>
        {scenes[this.state.sceneId].component}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  inputsContainer: {
    flex: 1,
    backgroundColor: "#1c1c1c",
  },
  header: {
    flex: 1,
    justifyContent: "space-between",
    alignItems: "center",
    flexDirection: "row",
    margin: 10,
    marginHorizontal: 30,
  },
  headerTitle: {
    fontWeight: "bold",
    color: "#909090"
  },
  headerImage: {
    resizeMode: "center",
    width: 100,
    height: 100,
  },
  buttonsContainer: {
    flex: 4,
  },
  button: {
    flex: 1,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#2e2e2e",
    margin: 10,
    elevation: 5,
    borderRadius: 5,
  },
  buttonText: {
    fontSize: 24,
    fontWeight: "bold",
    color: "#909090",
  },
  container: {
    flex: 1,
    alignItems: "stretch",
  },
  arView: {
    height: "100%",
  },
});

export default App;