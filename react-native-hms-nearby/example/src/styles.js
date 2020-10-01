/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

import { StyleSheet } from 'react-native';

export const styles = StyleSheet.create({
  bg: { backgroundColor: '#eee' },
  
  container: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    justifyContent: 'flex-end',
    alignItems: 'center',
  },

  containerCenter: {
    marginTop: 20,
    justifyContent: 'center',
    alignItems: 'center',
  },
  img: {
    width: '100%',
    height: 150,
  },

  centerImg: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  imgButton: {
    width: 57,
    height: 48,
  },

  containerList: {
    marginTop:10,
    height:400,
   },
   item: {
     padding: 8,
     fontSize: 14,
     height: 40,
     alignSelf:"center",
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
    marginVertical: 25,
  },

  button: {
    width: '33%',
    height: 80,
  },

  buttonTts: {
    width: '83%',
    height: 30,
    alignSelf: "center",
    marginTop: 10,
    marginBottom:10,
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

  h1: {
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 17,
    borderBottomWidth: 1,
    padding: 25,
    borderBottomColor: '#D3D3D3',
  },

  title: {
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 17,
    padding: 15,
  },

  customInput: {
    height: 40,
    borderColor: '#42aaf5',
    borderWidth: 2,
    width: "50%",
    alignSelf: "center",
    marginTop: 10,
    backgroundColor: "#fff",
    color: "#000"
  },

  customEditBox: {
    height: 450,
    borderColor: 'gray',
    borderWidth: 2,
    width: "95%",
    alignSelf: "center",
    marginTop: 10,
    backgroundColor: "#fff",
    color: "#000"
  },

  customEditBox3: {
    height: 80,
    borderColor: 'gray',
    borderWidth: 2,
    width: "83%",
    alignSelf: "center",
    marginTop: 10,
    backgroundColor: "#fff",
    color: "#000"
  },

  customEditBox2: {
    height: 250,
    borderColor: 'gray',
    borderWidth: 2,
    width: "95%",
    alignSelf: "center",
    marginTop: 10,
    backgroundColor: "#fff",
    color: "#000"
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
    backgroundColor: 'white',
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
    backgroundColor: '#42aaf5',
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
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});
