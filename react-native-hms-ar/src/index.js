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

import { requireNativeComponent, NativeEventEmitter, NativeModules } from "react-native";
import React from "react";

const ARSurfaceView = requireNativeComponent("ARSurfaceView");
export const { HmsARModule } = NativeModules;

export default class ARView extends React.Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    const eventEmitter = new NativeEventEmitter(ARSurfaceView);
    if (typeof this.props.onDrawFrame === "function")
      eventEmitter.addListener("onDrawFrame", this.props.onDrawFrame);
    if (typeof this.props.messageListener === "function")
      eventEmitter.addListener("messageListener", this.props.messageListener);
    if (typeof this.props.handleCameraConfig === "function")
      eventEmitter.addListener("handleCameraConfig", this.props.handleCameraConfig);
    if (typeof this.props.handleCameraIntrinsics === "function")
      eventEmitter.addListener("handleCameraIntrinsics", this.props.handleCameraIntrinsics);
    if (this.props.config.face && this.props.config.face.enableHealthDevice) {
      if (typeof this.props.config.face.healty.handleProcessProgressEvent === "function")
        eventEmitter.addListener("handleProcessProgressEvent", this.props.config.face.healty.handleProcessProgressEvent);
      if (typeof this.props.config.face.healty.handleEvent === "function")
        eventEmitter.addListener("handleEvent", this.props.config.face.healty.handleEvent);
      if (typeof this.props.config.face.healty.handleResult === "function")
        eventEmitter.addListener("handleResult", this.props.config.face.healty.handleResult);
    }
  }

  render() {
    return <ARSurfaceView {...this.props} />;
  }
}
