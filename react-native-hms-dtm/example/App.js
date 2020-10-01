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

import React from 'react';
import {
  StyleSheet,
  TouchableOpacity,
  Text,
  View,
  ScrollView,
  Image
} from 'react-native';

import HmsDTMModule from '@hmscore/react-native-hms-dtm/src';

/* You can give the path to your DTM config file in this way and pull the conditions, tags and variables in it.
 * import DTMConfig from './android/app/src/main/assets/containers/DTM...'
 */

export default class App extends React.Component {


  async customEvent() {

    var eventId = "Campaign"

    try {
      const bundle = {
        "name": "superOpportunity",
        "discountRate": "30",
        "prerequisite": false,
        "expirationTime": "20.10.2020",
      }
      var res = await HmsDTMModule.onEvent(eventId, bundle)
      alert("Response: " + res)
    } catch (e) {
      alert("Error: " + "onEvent::" + JSON.stringify(e))
    }

  }

  async customTag() {
    /*For customTag to be a trigger, this must be the same as the value written in condition.
     * An example server-side condition => "type":"big"
     */
    var eventId = "Test"
    try {
      
      const params=[
        /*You can pull conditions and variables from your own DTM config file as shown below.
         *number=>your condition number
        const number=5
        const sampleCondition=DTMConfig.resources.conditions[number]
        { 
          value:DTMConfig.resources.variables[sampleCondition[1][1]].params.key ,
          key:sampleCondition[2],
          hasCustom:true
        }
        */
        {
          hasCustom:true,
          value:"type",
          key:"big"
        },
        {
          value:"id",
          key:"123456"
        }
      ]

      var res = await HmsDTMModule.customFunction(eventId, params)
      alert("Response: " + res)
    } catch (e) {
      alert("Error: " + "customFunction::" + JSON.stringify(e))
    }

  }

  async functionCall() {

     /* For customVariable to be a trigger, this must be the same as the value written in condition.
      * An example server-side condition => "price":"40"
      */
   
    try {
      var eventId = "Purchase"
      const params=[
        /*You can pull conditions and variables from your own DTM config file as shown below.
         *number=>your condition number
         
        const number=1
        const sampleCondition=DTMConfig.resources.conditions[number]
        
        { 
          value:DTMConfig.resources.variables[sampleCondition[1][1]].params.key ,
          key:sampleCondition[2],
          hasCustom:true
        },
        */
        {
          hasCustom:true,
          value:"price",
          key:"40"
        },
        {
          value:"name",
          key:"pencil"
        },
        {
          value:"discountPrice",
          key:"40"
        },
        {
          value:"color",
          key:"red"
        }
      ]

      var res = await HmsDTMModule.customFunction(eventId, params)
      alert("Response: " + res)
    } catch (e) {
      alert("Error: " + "customFunction::" + JSON.stringify(e))
    }

  }

  async firebase() {
    var eventId = "Reservation"

    try {
      const bundle = {
        "bedType": "single",
        "room": "big",
        "price": "120"
      }
      var res = await HmsDTMModule.onEvent(eventId, bundle)
      alert("Response: " + res)
    } catch (e) {
      alert("Error: " + "onEvent::" + JSON.stringify(e))
    }

  }

  async appsFlyer() {
    var eventId = "DTM_BOOK"
    try {
      const bundle = {
        "DTM": "true",
        "dtm_room": "300",
        "revenue": "3000",
        "af_revenue": "3000"
      }
      var res = await HmsDTMModule.onEvent(eventId, bundle)
      alert("Response: " + res)
    } catch (e) {
      alert("Error: " + "onEvent::" + JSON.stringify(e))
    }

  }

  //HMS Logger
  async enableLogger() {
    try {
      var res = await HmsDTMModule.enableLogger()
      alert("Response: " + res)
    } catch (e) {
      alert("Error: " + "HMSLogger enabled::" + JSON.stringify(e))
    }
  }

  async disableLogger() {
    try {
      var res = await HmsDTMModule.disableLogger()
      alert("Response: " + res)
    } catch (e) {
      alert("Error: " + "HMSLogger disabled::" + JSON.stringify(e))
    }
  }


  render() {
    return (

      <View style={styles.mainContainer}>

        <View style={styles.header}>
          <Text style={styles.headerTitle}>HMS DTM Plugin</Text>
          <Image
            resizeMode="contain"
            style={styles.headerLogo}
            source={require('./assets/images/huawei_icon.jpg')} />
        </View>


        <ScrollView style={styles.scrollView} nestedScrollEnabled={true}>

          <View style={styles.container}>
            <View style={styles.partialView}>
              <Text style={styles.title}>DTM Module</Text>

              <TouchableOpacity activeOpacity={.8} style={styles.btn}
                onPress={() => this.customEvent()}>
                <Text style={styles.txt}> Basic Custom Event </Text>
              </TouchableOpacity>

              <TouchableOpacity activeOpacity={.8} style={styles.btn}
                onPress={() => this.customTag()}>
                <Text style={styles.txt}> Custom Tag </Text>
              </TouchableOpacity>

              <TouchableOpacity activeOpacity={.8} style={styles.btn}
                onPress={() => this.functionCall()}>
                <Text style={styles.txt}>Function Call</Text>
              </TouchableOpacity>

              <TouchableOpacity activeOpacity={.8} style={styles.btn}
                onPress={() => this.firebase()}>
                <Text style={styles.txt}>FireBase</Text>
              </TouchableOpacity>

              <TouchableOpacity activeOpacity={.8} style={styles.btn}
                onPress={() => this.appsFlyer()}>
                <Text style={styles.txt}>AppsFlyer</Text>
              </TouchableOpacity>

            </View>

          </View>

        </ScrollView>
      </View>
    )
  }

}

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'black',
    borderColor: 'orange',
    borderWidth: 1
  },
  scrollView: {
    flex: 1
  },
  container: {
    flex: 1,
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
    paddingBottom: 50
  },
  partialView: {
    marginTop: 10,
    width: "100%"
  },
  header: {
    backgroundColor: 'black',
    height: 160,
    width: '100%',
    flexDirection: 'row',
    borderBottomColor: 'orange',
    borderBottomWidth: 1
  },
  headerTitle: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#FFB300',
    flex: 1.1,
    alignSelf: 'center',
    marginLeft: 20
  },
  headerLogo: {
    flex: .7,
    height: '100%',
  },
  btn: {
    marginTop: 15,
    width: 125,
    height: 45,
    justifyContent: 'center',
    alignSelf: 'center',
    borderRadius: 10,
    marginLeft: 5,
    marginRight: 5,
    borderColor: "orange",
    borderWidth: 1
  },
  txt: {
    fontSize: 14,
    color: '#FFA500',
    textAlign: 'center'
  },
  title: {
    fontSize: 18,
    color: '#FFB300',
    width: 200,
    alignSelf: 'center',
    borderBottomColor: '#FFB300',
    borderBottomWidth: .7,
    textAlign: 'center',
    marginTop: 30
  },
  input: {
    color: "orange",
    width: 80,
    height: 45,
    alignSelf: 'center',
    borderColor: "yellow",
    borderWidth: .5,
    marginTop: 20

  }
})