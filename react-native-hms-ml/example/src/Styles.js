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

import { StyleSheet, Dimensions } from 'react-native';

const win = Dimensions.get('window');

export const styles = StyleSheet.create({
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

  bg: { backgroundColor: '#EEF2F3' },

  imageSelectView: {
    width: 200,
    height: 200,
  },

  superres: {
    flex: 1,
    alignSelf: 'stretch',
    width: win.width,
    height: win.height,
  },

  h1: {
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 15,
    borderBottomWidth: 1,
    padding: 20,
    borderBottomColor: '#D3D3D3',
    width: '95%',
    alignSelf: 'center'
  },

  boldText: { fontWeight: "bold" },

  normalView: {
    backgroundColor: '#EEF2F3',
    flex: 1,
    flexDirection: 'column',
  },

  baseItemContainer: {
    marginTop: 10,
    alignItems: 'center',
    width: '95%',
    alignSelf: 'center',
    height: '33%'
  },

  viewdividedtwo: {
    flex: 1,
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    alignSelf: 'center',
    width: '95%',
    borderBottomColor: '#D3D3D3',
    borderBottomWidth: 1,
    marginTop: 10,
  },
  itemOfView: {
    width: '70%',
    alignSelf: 'center'
  },

  itemOfView3: {
    width: '30%',
    alignSelf: 'flex-end'
  },

  dividedDropdown: {
    borderWidth: 2,
    borderColor: 'gray',
    height: 40,
    marginTop: 5
  },

  longDropdown: {
    backgroundColor: '#fafafa',
    width: '95%',
    alignSelf: 'center',
    borderWidth: 2,
    borderColor: 'gray',
    marginTop: 5
  },


  container: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    justifyContent: 'flex-end',
    alignItems: 'center',
  },

  spaceBetweenRow: { flexDirection: "row", justifyContent: "space-between" },

  containerCenter: {
    marginTop: 20,
    justifyContent: 'center',
    alignItems: 'center',
  },
  img: {
    width: 100,
    height: 100,
  },

  centerImg: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  imgButton: {
    width: 57,
    height: 48,
  },

  containerFlexCenter: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: "center",
    marginHorizontal: 30,
    marginVertical: 15,
  },

  containerFlex: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginHorizontal: 30,
    marginVertical: 15,
  },

  button: {
    width: '32%',
    height: 80,
  },

  basicButton: {
    width: '95%',
    height: 50,
    alignSelf: "center",
    marginTop: 5,
  },

  menuButton: {
    width: '100%',
    height: 50,
    alignSelf: "center",
    marginTop: 5,
  },

  imageView: {
    flex: 1,
    backgroundColor: '#fff',
    flexDirection: 'column',
  },
  image: {
    justifyContent: 'flex-start',
    width: 400,
    height: 80,
  },
  right: {
    justifyContent: 'flex-end',
  },


  title: {
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 17,
    padding: 15,
  },

  customInput: {
    height: 50,
    borderColor: 'gray',
    borderWidth: 2,
    width: "95%",
    alignSelf: "center",
    marginTop: 10,
    backgroundColor: "#fff",
    color: "#000"
  },

  customInput2: {
    height: 75,
    borderColor: 'gray',
    borderWidth: 2,
    width: "95%",
    alignSelf: "center",
    marginTop: 10,
    backgroundColor: "#fff",
    color: "#000"
  },

  customEditBox: {
    height: 250,
    borderColor: 'gray',
    borderWidth: 2,
    width: "95%",
    alignSelf: "center",
    marginTop: 10,
    backgroundColor: "#fff",
    color: "#000"
  },

  customEditBox2: {
    height: 230,
    width: "95%",
    alignSelf: "center",
    marginTop: 10,
    marginBottom: 10,
    backgroundColor: "#fff",
    color: "#000",
    borderColor: '#D3D3D3',
    borderWidth: 1,
    textAlign: 'center'
  },

  buttonRadius: {
    paddingTop: 10,
    paddingBottom: 10,
    backgroundColor: 'white',
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
  },

  startButton: {
    paddingTop: 10,
    paddingBottom: 10,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
    backgroundColor: '#7a7878',
  },
  startButtonclicked: {
    paddingTop: 10,
    paddingBottom: 10,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
    backgroundColor: 'green',
  },
  startButtonLabel: {
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


  borderedText: {
    paddingHorizontal: 20,
    paddingTop: 10,
    paddingBottom: 10,
    backgroundColor: 'grey',
    color: 'white',
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#fee',
  },

  speakButton: {
    color: '#000',
    width: 200,
    textAlignVertical: "center", textAlign: "center",
    paddingLeft: 10,
    paddingRight: 10,
    borderColor: 'blue'
  },

  log: {
    width: '95%',
    alignSelf: "center",
    marginTop: 5,
  },

  faceVerificationImage: {
    width: "100%",
    height: 200
  },

  enable: {
    backgroundColor: 'green'
  },

  cardRecogOptions: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center',
    marginVertical: 10
  }
});
