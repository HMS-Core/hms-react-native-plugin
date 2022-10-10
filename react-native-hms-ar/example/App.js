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

const configWorldCommon = {
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
  planeFindingMode: HmsARModule.PlaneFindingMode.ENABLE,
  ...configCommon,
};

const configCommon = {
  lightMode: HmsARModule.LightMode.ALL,
  semantic: {
    mode: HmsARModule.SemanticMode.ALL,
    showSemanticModeSupportedInfo: true
  },
  powerMode: HmsARModule.PowerMode.PERFORMANCE_FIRST,
  focusMode: HmsARModule.FocusMode.AUTO_FOCUS,
  updateMode: HmsARModule.UpdateMode.LATEST_CAMERA_IMAGE,
}

const configLinePointCommon = {
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
}

const configAugmentedImagesCommon = {
  augmentedImages: [{
    imgFileFromAsset: "ARAugmentedImageTest.jpg",
    widthInMeters: 0,
    imgName: "ImageTest",
  }]
}

class Hand extends React.Component {
  state = {
    text: ""
  }

  render() {
    return (
      <>
        <ARView
          style={styles.arView}
          onDrawFrame={(e) => console.log(e)}
          messageListener={(text) => this.setState({ text })}
          handleCameraConfig={(e) => console.log(e)}
          handleCameraIntrinsics={(e) => console.log(e)}
          config={{
            hand: {
              boxColor: {
                red: 255,
                blue: 0,
                green: 0,
                alpha: 255,
              },
              drawBox: true,
              lineWidthSkeleton: 30,
              ...configLinePointCommon,
              ...configCommon,
            },
          }}
        />
        <Text style={{ position: "absolute", color: "white", fontSize: 8 }}>{this.state.text}</Text>
      </>
    );
  }
};

class Body extends React.Component {
  state = {
    text: ""
  }

  render() {
    return (
      <>
        <ARView
          style={styles.arView}
          onDrawFrame={(e) => console.log(e)}
          messageListener={(text) => this.setState({ text })}
          handleCameraConfig={(e) => console.log(e)}
          handleCameraIntrinsics={(e) => console.log(e)}
          config={{
            body: {
              ...configLinePointCommon,
              ...configCommon,
            },
          }}
        />
        <Text style={{ position: "absolute", color: "white", fontSize: 8 }}>{this.state.text}</Text>
      </>
    );
  }
};

class Face extends React.Component {
  state = {
    text: "",
    event: "",
    result: "",
    processProgressEvent: "process 0%",
    healty: false
  }

  render() {
    return (
      <>
        <ARView
          style={styles.arView}
          onDrawFrame={(e) => console.log(e)}
          messageListener={(text) => this.setState({ text })}
          handleCameraConfig={(e) => console.log(e)}
          handleCameraIntrinsics={(e) => console.log(e)}
          config={{
            face: {
              enableHealthDevice: this.state.healty,
              healty: {
                handleProcessProgressEvent: (num) => { this.setState({ processProgressEvent: ("process " + num + "%") }) },
                handleEvent: (status) => { this.setState({ event: status }) },
                handleResult: (result) => { this.setState({ result: result }) },
              },
              multiFace: true,
              drawFace: true,
              pointSize: 15,
              depthColor: {
                red: 255,
                blue: 255,
                green: 255,
                alpha: 0,
              },
              texturePath: "blueTexture.png",
              ...configCommon,
            },
          }}
        />
        {!this.state.healty && <Text style={{ position: "absolute", color: "white", fontSize: 8 }}>{this.state.text}</Text>}
        {this.state.healty && (
          <View style={{ justifyContent: "center", alignItems: "center", position: "absolute", height: "100%", width: "100%", backgroundColor: "transparent" }}>
            <Text style={{ top: 150, position: "absolute", top: 20, color: "white", fontSize: 15 }}>{this.state.event}</Text>
            <View style={{
              width: 250,
              height: 250,
              borderColor: 'red',
              borderWidth: 3,
              borderRadius: 200,
              paddingBottom: 20,
              alignItems: "center",
              justifyContent: "flex-end",
              marginTop: -150,
              transform: [
                { scaleY: 1.5 }
              ]
            }} />
            <Text style={{ color: "white", fontSize: 13 }}>{this.state.processProgressEvent}</Text>
            <Text style={{ position: "absolute", bottom: 20, color: "white", fontSize: 10 }}>{this.state.result}</Text>
          </View>
        )}
      </>

    );
  }
};

class World extends React.Component {
  state = {
    text: ""
  }

  render() {
    return (
      <>
        <ARView
          style={styles.arView}
          onDrawFrame={(e) => console.log(e)}
          messageListener={(text) => {
            console.log("messageListener", text);
            this.setState({ text })
          }}
          handleCameraConfig={(e) => console.log(e)}
          handleCameraIntrinsics={(e) => console.log(e)}
          config={{
            world: {
              ...configWorldCommon,
              maxMapSize: 800,
              ...configAugmentedImagesCommon,
              aiBoxLineWidth: 30,
              aiBoxPointSize: 40,
              aiBoxLineColor: {
                red: 0,
                blue: 0,
                green: 255,
                alpha: 255,
              },
              aiBoxPointColor: {
                red: 255,
                blue: 128,
                green: 255,
                alpha: 192,
              },

              drawLineAI: true,
              drawPointAI: true,
              lineWidthAI: 30,
              pointSizeAI: 40,
              lineColorAI: {
                red: 0,
                blue: 0,
                green: 255,
                alpha: 255,
              },
              pointColorAI: {
                red: 255,
                blue: 128,
                green: 255,
                alpha: 192,
              },
            },
          }}
        />
        <Text style={{ position: "absolute", color: "white", fontSize: 8 }}>{this.state.text}</Text>
      </>

    );
  }
};

const AugmentedImage = () => {
  return (
    <ARView
      style={styles.arView}
      onDrawFrame={(e) => console.log(e)}
      handleCameraConfig={(e) => console.log(e)}
      handleCameraIntrinsics={(e) => console.log(e)}
      config={{
        augmentedImage: {
          ...configAugmentedImagesCommon,
          ...configCommon,
          ...configLinePointCommon,
        }
      }}
    />
  );
};

class WorldBody extends React.Component {
  state = {
    text: ""
  }

  render() {
    return (
      <>
        <ARView
          style={styles.arView}
          onDrawFrame={(e) => console.log(e)}
          messageListener={(text) => this.setState({ text })}
          handleCameraConfig={(e) => console.log(e)}
          handleCameraIntrinsics={(e) => console.log(e)}
          config={{
            worldBody: {
              ...configWorldCommon,
              ...configLinePointCommon,
              maxMapSize: 1800,
            },
          }}
        />
        <Text style={{ position: "absolute", color: "white", fontSize: 8 }}>{this.state.text}</Text>
      </>
    );
  }
};

class SceneMesh extends React.Component {
  state = {
    text: ""
  }

  render() {
    return (
      <>
        <ARView
          style={styles.arView}
          onDrawFrame={(e) => console.log(e)}
          messageListener={(text) => this.setState({ text })}
          handleCameraConfig={(e) => console.log(e)}
          handleCameraIntrinsics={(e) => console.log(e)}
          config={{
            sceneMesh: {
              objectName: "bob.obj",
              objectTexture: "bob_texture.png",
              ...configCommon,
            },
          }}
        />
        <Text style={{ position: "absolute", color: "white", fontSize: 8 }}>{this.state.text}</Text>
      </>
    );
  }
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
  {
    name: "AUGMENTED IMAGE",
    id: 4,
    component: <AugmentedImage key="AugmentedImage" />,
  },
  {
    name: "WORLD BODY",
    id: 5,
    component: <WorldBody key="WorldBody" />,
  },
  {
    name: "SCENE MESH",
    id: 6,
    component: <SceneMesh key="SceneMesh" />,
  }
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
            source={require('./src/assets/hms-rn-logo.png')} />
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