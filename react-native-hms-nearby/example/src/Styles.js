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

import { StyleSheet } from 'react-native';

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

  bg: { backgroundColor: '#E5E5E5' },

  containerCenter: {
    justifyContent: 'center',
    alignItems: 'center',
    width: '100%',
  },

  img: {
    width: '100%',
    height: 200,
  },

  h1: {
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 17,
    borderBottomWidth: 2,
    padding: 20,
    borderBottomColor: '#AAAAAA',
    width: '95%',
    alignSelf: 'center'
  },

  containerFlex: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginHorizontal: 20,
    marginVertical: 20,
  },

  button: {
    width: '32%',
    height: 95,
  },

  buttonRadius: {
    paddingTop: 10,
    paddingBottom: 10,
    backgroundColor: 'white',
    borderRadius: 10,
    borderWidth: 1.5,
    borderColor: '#AAAAAA',
  },

  imgButton: {
    width: 57,
    height: 48,
  },

  buttonText: {
    color: '#000',
    textAlign: 'center',
    paddingLeft: 10,
    paddingRight: 10,
  },

  centerImg: {
    justifyContent: 'center',
    alignItems: 'center',
  },

  baseView: {
    flex: 1,
    backgroundColor: '#E5E5E5'
  },

  connectionInput: {
    flexDirection: 'row',
    width: '100%',
    height: 60,
    backgroundColor: '#E5E5E5',
    alignItems: 'center',
    paddingLeft: 20,
    paddingRight: 10,
    borderWidth: 2,
    borderColor: '#AAAAAA'
  },
  sendButton: {
    width: 35,
    height: 35,
    marginLeft: 12
  },
  toolbar: {
    width: '100%',
    height: 55,
    justifyContent: 'center',
    backgroundColor: '#AAAAAA',
    elevation: 5,
  },
  titleToolbar: {
    color: 'black',
    fontWeight: 'bold',
    fontSize: 17,
    alignSelf: 'center',
  },
  sendedMessageView: {
    alignSelf: 'flex-end',
    marginRight: 10,
    marginBottom: 6,
    marginTop: 6
  },
  sendedMessageContent: {
    borderRadius: 10,
    width: 170,
    backgroundColor: '#000749',
    color: 'white',
    paddingTop: 7,
    paddingBottom: 7,
    paddingLeft: 9,
    paddingRight: 9,
    overflow: 'hidden'
  },
  receivedMessageView: {
    marginLeft: 10,
    marginBottom: 6,
    marginTop: 6,
  },
  receivedMessageContent: {
    borderRadius: 10,
    width: 170,
    backgroundColor: '#420002',
    color: 'white',
    paddingTop: 7,
    paddingBottom: 7,
    paddingLeft: 9,
    paddingRight: 9,
    overflow: 'hidden'
  },

  basicButton: {
    width: '95%',
    height: 50,
    alignSelf: "center",
    marginTop: 5,
  },

  viewdividedtwo: {
    flex: 1,
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-evenly',
    alignSelf: 'center',
    width: '95%',
    marginTop: 10
  },
  halfItem1: {
    width: '80%',
    alignSelf: 'center'
  },
  halfItem4: {
    width: '30%',
    alignSelf: 'center'
  },
  halfItem2: {
    width: '20%',
    alignSelf: 'center'
  },
  halfItem3: {
    width: '50%',
    alignSelf: 'center'
  },
  customInput: {
    height: 50,
    borderColor: 'gray',
    borderWidth: 2,
    width: "95%",
    alignSelf: "center",
    marginTop: 5,
    backgroundColor: "#fff",
    color: "#000"
  },

  basicButtonOpacity: {
    paddingTop: 10,
    paddingBottom: 10,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#888',
    backgroundColor: '#7a7878',
  },

  basicButtonLabel: {
    fontWeight: 'bold',
    color: '#fff',
    textAlign: 'center',
    paddingLeft: 10,
    paddingRight: 10,
  },

});
