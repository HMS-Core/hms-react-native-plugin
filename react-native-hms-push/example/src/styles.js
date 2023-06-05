/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  containerShowResultMsg: {
    flexDirection: "row",
    margin: 15,
    borderTopWidth: 1,
    paddingTop: 10,
    borderColor: "#aaa",
  },

  inputTopic: {
    textAlign: "center",
    padding: 10,
    width: "100%",
    color: "#000",
    fontSize: 20,
    fontWeight: "bold",
    fontFamily: "Roboto",
    borderWidth: 1,
    borderRadius: 5,
    borderColor: "#aaa",
  },
  width70: {
    width: "70%",
  },
  width35: {
    width: "35%",
  },
  width30: {
    width: "30%",
  },
  paddingTop20: {
    paddingTop: 12,
  },

  imageView: {
    flex: 1,
    backgroundColor: "#fff",
    flexDirection: "column",
  },
  image: {
    justifyContent: "flex-start",
    width: 400,
    height: 80,
  },

  paddingX: {
    paddingTop: 75,
  },

  container: {
    margin: 5,
    flex: 1,
    flexDirection: "row",
  },

  containerSlim: {
    marginBottom: 0,
    marginTop: 2,
  },

  buttonContainer: {
    flex: 1,
    margin: 5,
    justifyContent: "center",
    alignItems: "center",
    height: 35,
    borderRadius: 5,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 3,
    },
    shadowOpacity: 0.27,
    shadowRadius: 4.65,
    elevation: 6,
  },

  buttonContainerSlim: {
    marginBottom: 2,
    marginTop: 2,
  },

  primaryButton: {
    backgroundColor: "#c9c9c9",
  },

  secondaryButton: {
    backgroundColor: "#5ea6ff",
  },

  tertiaryButton: {
    backgroundColor: "#ff825c",
  },

  buttonText: {
    color: "#000",
    fontSize: 20,
    fontWeight: "bold",
    fontFamily: "Roboto",
  },

  buttonTextSmall: {
    fontSize: 16,
  },

  buttonTextSmallest: {
    fontSize: 14,
  },

  fontSizeSmall: {
    fontSize: 16,
  },
  
  header: {
    height: 55,
    backgroundColor: 'white',
    elevation: 5,
    alignItems: 'center', 
    paddingLeft: 15, 
    flexDirection: 'row'
  },
 
  headerImage: { 
    width: 30, 
    height: 30 
  },
 
  headerTitle: { 
    fontWeight: '600', 
    fontSize: 20 
  },
});
