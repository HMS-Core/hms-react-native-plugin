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

import React, { useState, useEffect } from "react";
import {
  SafeAreaView,
  StyleSheet,
  Switch,
  View,
  Text,
  Image,
  TextInput,
  TouchableHighlight,
  Button,
  Picker,
  ScrollView,
  NativeEventEmitter,
} from "react-native";
//import Picker from '@react-native-community/picker'
import ScanPlugin from "@hmscore/react-native-hms-scan";
import barcode_images from "./images/images.json";
import FilePickerManager from 'react-native-file-picker';

var imageResult = "";

const colors = {
  BLACK: -16777216,
  BLUE: -16776961,
  CYAN: -16711681,
  DKGRAY: -12303292,
  GRAY: -7829368,
  GREEN: -16711936,
  LTGRAY: -3355444,
  MAGENTA: -65281,
  RED: -65536,
  TRANSPARENT: 0,
  WHITE: -1,
  YELLOW: -256,
};
const base64ImagePng = "data:image/png;base64,";

const errorCorrectionLevel = {
  L: 7,
  M: 15,
  Q: 25,
  H: 30
}


const filteredDecodes = (decodes) => {
  let filteredDecodes = [];
  let originalValues = [];
  decodes.forEach((de) => {
    if (!originalValues.includes(de.originalValue)) {
      originalValues.push(de.originalValue);
      filteredDecodes.push(de);
    }
  });
  return filteredDecodes;
};

const showDecodes = (decodes) => {
  return (
    <ScrollView>
      {decodes.map((de, i) => (
        <View key={i} style={{ backgroundColor: "lightgrey", margin: 2 }}>
          <Text>
            Result Type: {de.scanTypeForm}
            {"\n"}
            Code Format: {de.scanType}
            {"\n"}
            Original Value: {de.originalValue}
          </Text>
        </View>
      ))}
    </ScrollView>
  );
};
class DecodeBitmap extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      decodesBitmap: [],
    };
  }
  render() {
    return (
      <View style={styles.sectionContainer}>
        <Image
          style={styles.mapView}
          source={{
            uri: base64ImagePng + barcode_images.aztecBarcode,
          }}
        />
        <Button
          title="decodeWithBitmap"
          color="purple"
          onPress={() => {
            ScanPlugin.Utils.decodeWithBitmap({
              data: barcode_images.aztecBarcode,
              scanType: ScanPlugin.ScanType,
              additionalScanTypes: [],
            })
              .then((res) =>
                this.setState({
                  decodesBitmap: [...this.state.decodesBitmap, res],
                }),
              )
              .catch((e) => console.log(e));
          }}
        />

        <Button
          title="Unique decodes for Decode Bitmap"
          color="purple"
          onPress={() => {
            this.setState({
              decodesBitmap: filteredDecodes(this.state.decodesBitmap),
            });
          }}
        />
        {showDecodes(this.state.decodesBitmap)}
      </View>
    );
  }
}

class DecodeBitmapMultiSync extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      decodesMultiSync: [],
    };
  }
  render() {
    return (
      <View style={styles.sectionContainer}>
        <Image
          style={styles.mapView}
          source={{
            uri: base64ImagePng + barcode_images.multipleBarcode,
          }}
        />
        <Button
          title="decodeMultiSync"
          onPress={() => {
            ScanPlugin.MultiProcessor.decodeMultiSync({
              data: barcode_images.multipleBarcode,
              scanType: ScanPlugin.ScanType,
              additionalScanTypes: [],
            })
              .then((res) =>
                this.setState({
                  decodesMultiSync: [...this.state.decodesMultiSync, ...res],
                }),
              )
              .catch((e) => console.log(e));
          }}
        />
        <Button
          title="Unique decodes for Decode MultiSync"
          color="purple"
          onPress={() => {
            this.setState({
              decodesMultiSync: filteredDecodes(this.state.decodesMultiSync),
            });
          }}
        />
        {showDecodes(this.state.decodesMultiSync)}
      </View>
    );
  }
}

class DecodeBitmapMultiAsync extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      decodesMultiAsync: [],
    };
  }
  render() {
    return (
      <View style={styles.sectionContainer}>
        <Image
          style={styles.mapView}
          source={{
            uri: base64ImagePng + barcode_images.multiple2Barcode,
          }}
        />
        <Button
          title="decodeMultiAsync"
          color="purple"
          onPress={() => {
            ScanPlugin.MultiProcessor.decodeMultiAsync({
              data: barcode_images.multiple2Barcode,
              scanType: ScanPlugin.ScanType,
              additionalScanTypes: [],
            })
              .then((res) =>
                this.setState({
                  decodesMultiAsync: [...this.state.decodesMultiAsync, ...res],
                }),
              )
              .catch((e) => console.log(e));
          }}
        />
        <Button
          title="Unique decodes for Decode MultiAsync"
          color="purple"
          onPress={() => {
            this.setState({
              decodesMultiAsync: filteredDecodes(this.state.decodesMultiAsync),
            });
          }}
        />
        {showDecodes(this.state.decodesMultiAsync)}
      </View>
    );
  }
}

class BuildBitmap extends React.Component {
  
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

constructor(props) {
  super(props);
  this.state = {
    content: "Hello",
    type: ScanPlugin.ScanType.All,
    width: 200,
    height: 200,
    margin: 1,
    color: colors.BLACK,
    backgroundColor: colors.WHITE,
    showImage: false,
    qrLogoBitmap: imageResult
  };
}

buildBitmap(){
  this.filePicker((response) => {
    const args = {
      content: this.state.content,
      type: ScanPlugin.ScanType.All,
      width: this.state.width,
      height: this.state.height,
      margin: this.state.margin,
      color: this.state.color,
      backgroundColor: this.state.backgroundColor,
      showImage: false,
      qrErrorCorrectionLevel: errorCorrectionLevel.M,
      qrLogoBitmap: response.uri
  };
    ScanPlugin.Utils.buildBitmap(args)
    .then((res) => {
      this.setState({ showImage: true });
      this.setState({ base64ImageData: res });
    })
    .catch((e) => console.log(e))
});
}

  render() {
    return (
      <View style={styles.sectionContainer}>
        <View style={{ flexDirection: "row" }}>
          <Text style={{ flex: 1 }}>Barcode Content : </Text>
          <TextInput
            style={{ flex: 3 }}
            onChangeText={(text) => this.setState({ content: text })}
            value={this.state.content}
          />
        </View>
        <View style={{ flexDirection: "row" }}>
          <Text style={{ flex: 1 }}>Barcode Width : </Text>
          <TextInput
            style={{ flex: 1 }}
            keyboardType="numeric"
            maxLength={4}
            onChangeText={(text) =>
              this.setState({ width: parseInt(text | "0") })
            }
            value={"" + this.state.width}
          />
          <Text style={{ flex: 1 }}>Barcode Height : </Text>
          <TextInput
            style={{ flex: 1 }}
            keyboardType="numeric"
            maxLength={4}
            onChangeText={(text) =>
              this.setState({ height: parseInt(text | "0") })
            }
            value={"" + this.state.height}
          />
        </View>
        <View style={{ flexDirection: "row" }}>
          <Text style={{ flex: 1 }}>Scan Type : </Text>
          <Picker
            style={{ flex: 2 }}
            prompt="Select Scan Type"
            selectedValue={this.state.type}
            onValueChange={(val) => {
              this.setState({ type: val });
            }}>
            {Object.keys(ScanPlugin.ScanType).map((s_type) => (
              <Picker.Item
                label={s_type}
                value={ScanPlugin.ScanType[s_type]}
                key={s_type}
              />
            ))}
          </Picker>
          <Text style={{ flex: 1 }}>Barcode Margin : </Text>
          <TextInput
            style={{ flex: 1 }}
            keyboardType="numeric"
            maxLength={2}
            onChangeText={(text) =>
              this.setState({ margin: parseInt(text | "0") })
            }
            value={"" + this.state.margin}
          />
        </View>
        <View style={{ flexDirection: "row" }}>
          <Text style={{ flex: 1 }}>Bitmap Color : </Text>
          <Picker
            style={{ flex: 2 }}
            prompt="Color"
            selectedValue={this.state.color}
            onValueChange={(itemValue) => {
              this.setState({ color: itemValue });
            }}>
            {Object.keys(colors).map((c) => (
              <Picker.Item label={c} value={colors[c]} key={c} />
            ))}
          </Picker>
          <Text style={{ flex: 1 }}>Backgroundcolor : </Text>
          <Picker
            style={{ flex: 2 }}
            prompt="Backgroundcolor"
            selectedValue={this.state.backgroundColor}
            onValueChange={(itemValue) => {
              this.setState({ backgroundColor: itemValue });
            }}>
            {Object.keys(colors).map((c) => (
              <Picker.Item label={c} value={colors[c]} key={c} />
            ))}
          </Picker>
        </View>
        <Button
          title="Generate Barcode"
          color="green"
          onPress={() => this.buildBitmap()}
        />
        {this.state.showImage && (
          <TouchableHighlight onPress={() => this.setState({ showImage: false })}>
            <Image
              style={{ height: 300 }}
              source={{
                uri: base64ImagePng + this.state.base64ImageData,
              }}
            />
          </TouchableHighlight>
        )}
      </View>
    );
  }
}

class DefaultView extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      decodesDefault: [],
    };
  }

  componentDidMount() {
    const eventEmitter = new NativeEventEmitter(ScanPlugin);
    this.eventListener = eventEmitter.addListener("returnButtonClicked", (event) => {
      console.log(event.NOT_OK);
      alert(event.NOT_OK);
    });

  }

  render() {
    return (
      <View style={styles.sectionContainer}>
        <Button
          title="defaultView"
          onPress={() =>
            ScanPlugin.Utils.startDefaultView({
              scanType: ScanPlugin.ScanType,
              additionalScanTypes: [],
            }).then((res) =>
              this.setState({
                decodesDefault: [...this.state.decodesDefault, res],
              }),
            )
          }
        />
        <Button
          title="Unique decodes for DefaultView"
          color="purple"
          onPress={() => {
            this.setState({
              decodesDefault: filteredDecodes(this.state.decodesDefault),
            });
          }}
        />
        {showDecodes(this.state.decodesDefault)}
      </View>
    );
  }
}

class CustomizedView extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      decodesCustom: [],
      imageList: []
    };
  }


  componentDidMount() {
    ScanPlugin.CustomizedView.onResponseListenerAdd((res) => {
      console.log("onResponse event triggered");
      this.setState({
        decodesCustom: [...this.state.decodesCustom, res],
      });
    });
    ScanPlugin.CustomizedView.onStartListenerAdd(() =>
      console.log("onStart event triggered"),
    );
    ScanPlugin.CustomizedView.onResumeListenerAdd(() =>
      console.log("onResume event triggered"),
    );
    ScanPlugin.CustomizedView.onPauseListenerAdd(() =>
      console.log("onPause event triggered"),
    );
    ScanPlugin.CustomizedView.onDestroyListenerAdd(() =>
      console.log("onDestroy event triggered"),
    );
    ScanPlugin.CustomizedView.onStopListenerAdd(() =>
      console.log("onStop event triggered"),
    );
    ScanPlugin.CustomizedView.onOriginalScanLoadListenerAdd((res) =>
      this.createImageList(res)
    );
  }

  createImageList(image) {
    var list=this.state.imageList
    var image = (
          <Image 
            style={{ width:250 ,height: 150, backgroundColor: "black", alignItems: "center" }}
            source={{
              uri: base64ImagePng + image,
            }}
          />
        )
      list.push(image)
      this.setState({imageList:list})
  }

  componentWillUnmount() {
    ScanPlugin.CustomizedView.allListenersRemove();
  }

  render() {
    return (
      <View style={styles.sectionContainer}>
        <Button
          title="CustomizedView"
          onPress={() => {
            ScanPlugin.CustomizedView.startCustomizedView({
              scanType: ScanPlugin.ScanType,
              additionalScanTypes: [],
              rectHeight: 200,
              rectWidth: 200,
              continuouslyScan: true,
              isFlashAvailable: false,
              flashOnLightChange: false,
              isGalleryAvailable: false,
              enableReturnOriginalScan: true
            })
              .then((res) =>
                this.setState({
                  decodesCustom: [...this.state.decodesCustom, res],
                }),
              )
              .catch((e) => console.log(e));

            setTimeout(function () {
              console.log("pauseContinuouslyScan func called");
              ScanPlugin.CustomizedView.pauseContinuouslyScan()
                .then((res) => console.log("pauseContinuouslyScan", res))
                .catch((e) => console.log(e));
            }, 5000);

            setTimeout(function () {
              console.log("switchLight func called");
              ScanPlugin.CustomizedView.switchLight()
                .then((res) => console.log("switchLight:", res))
                .catch((e) => console.log(e));
            }, 3000);

            setTimeout(function () {
              console.log("resumeContinuouslyScan func called");
              ScanPlugin.CustomizedView.resumeContinuouslyScan()
                .then((res) => console.log("resumeContinuouslyScan again", res))
                .catch((e) => console.log(e));
            }, 10000);

            setTimeout(function () {
              console.log("getLightStatus func called");
              ScanPlugin.CustomizedView.getLightStatus()
                .then((res) => console.log("getLightStatus:", res))
                .catch((e) => console.log(e));
            }, 6000);
          }}
        />
        <Button
          title="Unique decodes for Customized View"
          color="purple"
          onPress={() => {
            this.setState({
              decodesCustom: filteredDecodes(this.state.decodesCustom),
            });
          }}
        />
        <ScrollView contentContainerStyle={{flexGrow:1, height: 200}}>
            {showDecodes(this.state.decodesCustom)}
        </ScrollView>

       <ScrollView contentContainerStyle={{flexGrow:1, height: 150}}>
            {this.state.imageList}
        </ScrollView>
  
       </View>
    );
  }
}

class MultiProcessorCamera extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      type: ScanPlugin.ScanType.All,
      color1: colors.YELLOW,
      color2: colors.BLUE,
      color3: colors.RED,
      color4: colors.GREEN,
      textColor: colors.WHITE,
      textBackgroundColor: colors.BLACK,
      isSync: true,
      isGallery: true,
      showText: true,
      showTextOutBounds: false,
      autoSizeText: true,
      strokeWidth: 4,
      textSize: 35,
      minTextSize: 24,
      granularity: 2,
      margin: 1,
      decodesMulti: [],
    };
  }

  componentDidMount() {
    ScanPlugin.MultiProcessor.onMultiProcessorResponseListenerAdd((res) => {
      console.log("onMultiProcessorResponse event triggered");
      this.setState({
        decodesMulti: [...this.state.decodesMulti, res],
      });
    });
  }

  componentWillUnmount() {
    ScanPlugin.MultiProcessor.allListenersRemove();
  }

  render() {
    return (
      <View style={styles.sectionContainer}>
        <View style={{ flexDirection: "row" }}>
          <Text style={{ flex: 1 }}>Scan Type : </Text>
          <Picker
            style={{ flex: 2 }}
            prompt="Select Scan Type"
            selectedValue={this.state.type}
            onValueChange={(itemValue) => {
              this.setState({ type: itemValue });
            }}>
            {Object.keys(ScanPlugin.ScanType).map((s_type) => (
              <Picker.Item
                label={s_type}
                value={ScanPlugin.ScanType[s_type]}
                key={s_type}
              />
            ))}
          </Picker>
        </View>
        <View style={{ flexDirection: "row" }}>
          <Text style={{ flex: 1 }}>Barcode Margin</Text>
          <TextInput
            style={{ flex: 1 }}
            keyboardType="numeric"
            maxLength={2}
            onChangeText={(text) =>
              this.setState({ margin: parseInt(text | "0") })
            }
            value={"" + this.state.margin}
          />
          <Switch
            style={{ flex: 1 }}
            onValueChange={() => this.setState({ isSync: !this.state.isSync })}
            value={this.state.isSync}
          />
          <Text style={{ flex: 1 }}>Scan Mode</Text>
          <Switch
            style={{ flex: 1 }}
            onValueChange={() =>
              this.setState({ isGallery: !this.state.isGallery })
            }
            value={this.state.isGallery}
          />
          <Text style={{ flex: 1 }}>Gallery</Text>
        </View>
        <View style={{ flexDirection: "row" }}>
          <Switch
            style={{ flex: 1 }}
            onValueChange={() =>
              this.setState({ showText: !this.state.showText })
            }
            value={this.state.showText}
          />
          <Text style={{ flex: 1 }}>Show Text</Text>
          <Switch
            style={{ flex: 1 }}
            onValueChange={() =>
              this.setState({
                showTextOutBounds: !this.state.showTextOutBounds,
              })
            }
            value={this.state.showTextOutBounds}
          />
          <Text style={{ flex: 1 }}>Show Text Out Bounds </Text>
          <Switch
            style={{ flex: 1 }}
            onValueChange={() =>
              this.setState({ autoSizeText: !this.state.autoSizeText })
            }
            value={this.state.autoSizeText}
          />
          <Text style={{ flex: 1 }}>Auto Size Text </Text>
        </View>
        <View style={{ flexDirection: "row" }}>
          <Text style={{ flex: 1 }}>Color1 :</Text>
          <Picker
            style={{ flex: 2 }}
            prompt="Color1"
            selectedValue={this.state.color1}
            onValueChange={(itemValue) => {
              this.setState({ color1: itemValue });
            }}>
            {Object.keys(colors).map((c) => (
              <Picker.Item label={c} value={colors[c]} key={c} />
            ))}
          </Picker>

          <Text style={{ flex: 1 }}>Color2 : </Text>
          <Picker
            style={{ flex: 2 }}
            prompt="Color2"
            selectedValue={this.state.color2}
            onValueChange={(itemValue) => {
              this.setState({ color2: itemValue });
            }}>
            {Object.keys(colors).map((c) => (
              <Picker.Item label={c} value={colors[c]} key={c} />
            ))}
          </Picker>
        </View>
        <View style={{ flexDirection: "row" }}>
          <Text style={{ flex: 1 }}>Color3 : </Text>
          <Picker
            style={{ flex: 2 }}
            prompt="Color3"
            selectedValue={this.state.color3}
            onValueChange={(itemValue) => {
              this.setState({ color3: itemValue });
            }}>
            {Object.keys(colors).map((c) => (
              <Picker.Item label={c} value={colors[c]} key={c} />
            ))}
          </Picker>
          <Text style={{ flex: 1 }}>Color4 : </Text>
          <Picker
            style={{ flex: 2 }}
            prompt="Color4"
            selectedValue={this.state.color4}
            onValueChange={(itemValue) => {
              this.setState({ color4: itemValue });
            }}>
            {Object.keys(colors).map((c) => (
              <Picker.Item label={c} value={colors[c]} key={c} />
            ))}
          </Picker>
        </View>
        <View style={{ flexDirection: "row" }}>
          <Text style={{ flex: 1 }}>Text Color : </Text>
          <Picker
            style={{ flex: 2 }}
            prompt="TextColor"
            selectedValue={this.state.textColor}
            onValueChange={(itemValue) => {
              this.setState({ textColor: itemValue });
            }}>
            {Object.keys(colors).map((c) => (
              <Picker.Item label={c} value={colors[c]} key={c} />
            ))}
          </Picker>
          <Text style={{ flex: 1 }}>Text Background Color : </Text>
          <Picker
            style={{ flex: 2 }}
            prompt="TextBackgroundcolor"
            selectedValue={this.state.textBackgroundColor}
            onValueChange={(itemValue) => {
              this.setState({ textBackgroundColor: itemValue });
            }}>
            {Object.keys(colors).map((c) => (
              <Picker.Item label={c} value={colors[c]} key={c} />
            ))}
          </Picker>
        </View>
        <View style={{ flexDirection: "row" }}>
          <Text style={{ flex: 1 }}>Text Size : </Text>
          <TextInput
            style={{ flex: 1 }}
            keyboardType="numeric"
            maxLength={3}
            onChangeText={(text) =>
              this.setState({ textSize: parseInt(text | "0") })
            }
            value={"" + this.state.textSize}
          />
          <Text style={{ flex: 1 }}>Min Text Size : </Text>
          <TextInput
            style={{ flex: 1 }}
            keyboardType="numeric"
            maxLength={3}
            onChangeText={(text) =>
              this.setState({ minTextSize: parseInt(text | "0") })
            }
            value={"" + this.state.minTextSize}
          />
          <Text style={{ flex: 1 }}>Granularity : </Text>
          <TextInput
            style={{ flex: 1 }}
            keyboardType="numeric"
            maxLength={3}
            onChangeText={(text) =>
              this.setState({ granularity: parseInt(text | "0") })
            }
            value={"" + this.state.granularity}
          />
        </View>

        <Button
          title="Multi Processor Camera"
          onPress={() =>
            ScanPlugin.MultiProcessor.startMultiProcessorCamera({
              scanMode: this.state.isSync
                ? ScanPlugin.ScanMode.Sync
                : ScanPlugin.ScanMode.Async,
              scanType: this.state.type,
              additionalScanTypes: [],
              colorList: [
                this.state.color1,
                this.state.color2,
                this.state.color3,
                this.state.color4,
              ],
              strokeWidth: this.state.strokeWidth,
              scanTextOptions: {
                textColor: this.state.textColor,
                textSize: this.state.textSize,
                showText: this.state.showText,
                showTextOutBounds: this.state.showTextOutBounds,
                textBackgroundColor: this.state.textBackgroundColor,
                autoSizeText: this.state.autoSizeText,
                minTextSize: this.state.minTextSize,
                granularity: this.state.granularity,
              },
              isGalleryAvailable: this.state.isGallery,
            })
              .then((res) =>
                this.setState({
                  decodesMulti: [...this.state.decodesMulti, ...res],
                }),
              )
              .catch((e) => console.log(e))
          }
        />
        <Button
          title="Unique decodes for MultiProcessor Camera"
          color="purple"
          onPress={() => {
            this.setState({
              decodesMulti: filteredDecodes(this.state.decodesMulti),
            });
          }}
        />
        {showDecodes(this.state.decodesMulti)}
      </View>
    );
  }
}

const pages = [
  {
    name: "Build Bitmap",
    id: "BuildBitmap",
    component: <BuildBitmap key="BuildBitmap" />,
  },
  {
    name: "Decode Bitmap",
    id: "DecodeBitmap",
    component: <DecodeBitmap key="DecodeBitmap" />,
  },
  {
    name: "Decode Bitmap Multi Sync",
    id: "DecodeBitmapMultiSync",
    component: <DecodeBitmapMultiSync key="DecodeBitmapMultiSync" />,
  },
  {
    name: "Decode Bitmap MultiAsync",
    id: "DecodeBitmapMultiAsync",
    component: <DecodeBitmapMultiAsync key="DecodeBitmapMultiAsync" />,
  },
  {
    name: "Default View",
    id: "DefaultView",
    component: <DefaultView key="DefaultView" />,
  },
  {
    name: "Customized View",
    id: "CustomizedView",
    component: <CustomizedView key="CustomizedView" />,
  },
  {
    name: "MultiProcessor Camera",
    id: "MultiProcessorCamera",
    component: <MultiProcessorCamera key="MultiProcessorCamera" />,
  },
];

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      permissionGranted: false,
      pageId: "BuildBitmap",
      showImage: false,
      base64ImageData: "",
      imageList: null
    };
  }

  async componentDidMount() {
    const response = await ScanPlugin.Permission.hasCameraAndStoragePermission();
    this.setState({ permissionGranted: response });
  }

  render() {
    return (
      <SafeAreaView>
        <View
          contentInsetAdjustmentBehavior="automatic"
          style={{ backgroundColor: "lightgrey", flex: 1 }}></View>
        {this.state.permissionGranted ? (
          <View>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionHeader} title="Functions">
                Functions:
              </Text>

              <Picker
                prompt="Select Function"
                selectedValue={this.state.pageId}
                onValueChange={(itemValue) =>
                  this.setState({ pageId: itemValue })
                }>
                {pages.map((page) => (
                  <Picker.Item
                    label={page.name}
                    value={page.id}
                    key={page.id}
                  />
                ))}
              </Picker>
              {pages
                .filter((page) => page.id === this.state.pageId)
                .map((page) => page.component)}
            </View>
          </View>
        ) : (
            <View>
              <Button
                color="green"
                title="Request Permission"
                onPress={() =>
                  ScanPlugin.Permission.requestCameraAndStoragePermission().then(
                    (res) => {
                      console.log("result", res);
                      this.setState({ permissionGranted: res });
                    },
                  )
                }
              />
            </View>
          )}
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  flexRow: { flexDirection: "row" },
  flexCol: { flexDirection: "column" },
  flex1: { flex: 1 },
  flex2: { flex: 2 },
  width30: { width: 30 },
  width40: { width: 40 },
  width100: { width: 100 },
  mapView: {
    height: 200,
    backgroundColor: "red",
  },
  snapView: {
    height: 100,
    backgroundColor: "yellow",
  },
  infoWindow: {
    backgroundColor: "white",
    alignSelf: "baseline",
  },
  container: {
    flexDirection: "column",
    alignSelf: "flex-start",
  },
  sectionContainer: {
    margin: 12,
    paddingHorizontal: 8,
  },
  sectionHeader: {
    fontSize: 20,
    fontWeight: "600",
    paddingVertical: 12,
    paddingHorizontal: 0,
  },
});

export default App;
