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
import { AppRegistry, ToastAndroid } from "react-native";
import App from "./App";
import { name as appName } from "./app.json";
import { HMSAwarenessBarrierModule } from "@hmscore/react-native-hms-awareness";
AppRegistry.registerHeadlessTask(HMSAwarenessBarrierModule.TASK_NAME, () => async (taskData) => {
    console.log(taskData);
    ToastAndroid.show(JSON.stringify(taskData), ToastAndroid.SHORT);
});
AppRegistry.registerComponent(appName, () => App);

