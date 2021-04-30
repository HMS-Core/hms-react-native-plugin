/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

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
    if (typeof this.props.onDrawFrame === "function") {
      const eventEmitter = new NativeEventEmitter(ARSurfaceView);
      eventEmitter.addListener("onDrawFrame", (event) => {
        this.props.onDrawFrame(event);
      });
    }
    if (this.props.config.face) {
      if (this.props.config.face.enableHealthDevice) {
        if (typeof this.props.config.face.handleProcessProgressEvent === "function") {
          const eventEmitter = new NativeEventEmitter(ARSurfaceView);
          eventEmitter.addListener("handleProcessProgressEvent", (event) => {
            this.props.config.face.handleProcessProgressEvent(event);
          });
        }
      }
    }
  }

  render() {
    return <ARSurfaceView {...this.props} />;
  }
}
