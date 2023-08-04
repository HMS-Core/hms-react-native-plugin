/*
 * Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  flexRow: { flexDirection: "row" },
  flexCol: { flexDirection: "column" },
  flex1: { flex: 1 },
  flex2: { flex: 2 },
  width30: { width: 30 },
  width40: { width: 40 },
  width50: { width: 50 },
  width100: { width: 100 },
  width125: { width: 125 },
  height200: { height: 200 },
  height250: { height: 250 },
  height300: { height: 300 },
  height400: { height: 400 },
  height450: { height: 450 },
  fullHeight: { height: "100%" },
  mapView: { height: 300, backgroundColor: "red" },
  snapView: { height: 200, backgroundColor: "yellow" },
  infoWindow: { backgroundColor: "white", alignSelf: "baseline" },
  container: { flexDirection: "column", alignSelf: "flex-start" },
  textInput: {
    flex: 1,
    borderColor: "gray",
    borderWidth: 1,
    margin: 4,
  },
  textBold: { fontWeight: "bold" },
  p4: { padding: 4 },
  p8: { padding: 8 },
  p16: { padding: 16 },
  m1: { margin: 1 },
  m2: { margin: 2 },
  m4: { margin: 4 },
  m8: { margin: 8 },
  m16: { margin: 16 },
});
