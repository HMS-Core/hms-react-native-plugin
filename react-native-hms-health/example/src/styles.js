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

import {StyleSheet} from 'react-native';

export const styles = StyleSheet.create({
  containerFlex: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginHorizontal: 30,
    marginVertical: 15,
  },

  buttonDataController: {
    width: '100%',
    alignSelf: 'center',
    marginTop: 15,
  },

  horizontalButtons: {
    flexDirection: 'row',
    margin: 5,
    paddingRight: 15,
    width: '90%',
    height: 50,
  },

  horizontalCenterButtons: {
    flexDirection: 'row',
    justifyContent: 'center',
    width: '100%',
    height: 50,
  },

  h1: {
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 17,
    borderBottomWidth: 1,
    padding: 25,
    borderBottomColor: '#D3D3D3',
  },

  h2: {
    borderTopColor: '#D3D3D3',
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 14,
    padding: 5,
  },

  h3: {
    textAlign: 'center',
    fontSize: 14,
    padding: 20,
    paddingBottom: 0,
  },

  h5: {
    textAlign: 'center',
    fontSize: 14,
    padding: 20,
    paddingTop: 0,
    paddingBottom: 0,
  },

  h3Color: {
    textAlign: 'center',
    fontSize: 14,
    padding: 20,
    paddingTop: 0,
    paddingBottom: 0,
    color: '#42aaf5',
  },

  innerBody: {
    borderBottomWidth: 1,
    borderBottomColor: '#D3D3D3',
  },

  title: {
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 17,
    padding: 15,
  },

  buttonRadius: {
    paddingTop: 10,
    paddingBottom: 10,
    backgroundColor: 'white',
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
  },

  smallButton: {
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
    paddingTop: 10,
    paddingBottom: 10,
    margin: 5,
    backgroundColor: '#42aaf5',
    width: '25%',
  },

  sensorButton: {
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
    backgroundColor: '#42aaf5',
    alignSelf: 'center',
    justifyContent: 'center',
    margin: 10,
    width: '50%',
    height: 44,
  },

  autoRecorderButton: {
    backgroundColor: '#42aaf5',
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
    alignSelf: 'center',
    justifyContent: 'center',
    margin: 5,
    width: '70%',
    height: 40,
  },

  sensorButtonLarge: {
    paddingTop: 10,
    paddingBottom: 10,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
    backgroundColor: '#42aaf5',
    alignSelf: 'center',
    justifyContent: 'center',
    margin: 10,
    width: '80%',
    height: 80,
  },

  mainPageButton: {
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
    backgroundColor: '#42aaf5',
    width: '100%',
    marginBottom: 10,
    alignSelf: 'center',
    justifyContent: 'center',
    height: 40,
  },

  horizontalButton: {
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
    backgroundColor: '#42aaf5',
    width: '80%',
    marginBottom: 10,
    alignSelf: 'center',
    justifyContent: 'center',
    height: 40,
  },

  smallButtonLabel: {
    fontWeight: 'bold',
    color: '#fff',
    textAlign: 'center',
    paddingLeft: 10,
    paddingRight: 10,
  },

  buttonText: {
    color: '#000',
    textAlign: 'center',
    paddingLeft: 10,
    paddingRight: 10,
  },

  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});
