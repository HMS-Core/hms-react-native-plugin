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

import React from 'react';
import Combobox from './customViews/ComboBox'
import HmsAnalytics from '@hmscore/react-native-hms-analytics';

import {ScrollView, StyleSheet, Text, TextInput, TouchableOpacity, View} from 'react-native';

const pickerType={
  logLevel:"logLevel",
  eventType:"eventType",
  eventParamType:"eventParamType"
}

const logLevel={
  debug:"DEBUG",
  info:"INFO",
  warn:"WARN",
  error:"ERROR"
}

/**
 * Provides methods to obtain HiAnalytics Kit functions both In Android & IOS Platforms.
 */
export default class App extends React.Component {

  /**
   * Specifies whether to enable event collection.
   * If the function is disabled, no data is recorded.
   */
  async setAnalyticsEnabled(){
    console.log("Calling setAnalyticsEnabled")

    this.setState({isAnalyticsEnabled:true})
    this.createCustomView("setAnalyticsEnabled :  ",true + "")
    try{
      let result = await HmsAnalytics.setAnalyticsEnabled(true)
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: setAnalyticsEnabled '+e)
    }
  }

  /**
   * Specifies whether to enable restriction of HUAWEI Analytics. The default value is false, which indicates that HUAWEI Analytics is enabled by default.
   */
  async setRestrictionEnabled(){
    console.log("Calling setRestrictionEnabled")
    try{
      let result= await HmsAnalytics.setRestrictionEnabled(false)
      this.createCustomView("setRestrictionEnabled:  ", JSON.stringify(result) + "")
      console.log(result)
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: setRestrictionEnabled '+e)
    }
  }

  /**
   * Obtains the restriction status of HUAWEI Analytics.
   * @note Restriction status of HUAWEI Analytics.
   * - true: Restriction of HUAWEI Analytics is enabled.
   * - false: Restriction of HUAWEI Analytics is disabled.
   */
  async isRestrictionEnabled(){
    console.log("Calling isRestrictionEnabled")

    try{
      let result=await HmsAnalytics.isRestrictionEnabled()
      this.createCustomView("isRestrictionEnabled: ", JSON.stringify(result) + "")
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: isRestrictionEnabled '+e)
    }
  }

  /**
   * Enables the log function.
   * @note This function is specifically used by Android Platforms.
   */
  async enableLog(){
    console.log("Calling enableLog")

    // defaultValue= logLevel.debug
    try{
      let result = await HmsAnalytics.enableLog()
      this.createCustomView("enableLogLevel default: ",logLevel.debug)
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: enableLog '+e)
    }
  }

  /**
   * Enables the debug log function and sets the minimum log level.
   * @note This function is specifically used by Android Platforms.
   */
  async enableLogWithLevel(){
    console.log("Calling enableLogWithLevel")

    try{
      let result = await HmsAnalytics.enableLogWithLevel("DEBUG")
      this.createCustomView("enableLogWithLevel : ",this.state.selectedLogLevel )
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: enableLog '+e)
    }
  }

  /**
   * Defines a custom page entry event.
   * @note This function is specifically used by Android Platforms.
   */
  async pageStart(){
    console.log("Calling pageStart")

    var screenName="AppScreen"
    var screenClassOverride="App"

    try{
      let result = await HmsAnalytics.pageStart(screenName,screenClassOverride)
      this.createCustomView("pageStart : ", "screenName : "+screenName+" screenClassOverride : "+ screenClassOverride)
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: pageStart '+e)
    }
  }

  /**
   * Defines a custom page exit event.
   * @note This function is specifically used by Android Platforms.
   */
  async pageEnd(){
    console.log("Calling pageEnd")

    var screenName="AppScreen"
    try{
      let result = await HmsAnalytics.pageEnd(screenName)
      this.createCustomView("pageEnd : ", "screenName : "+screenName)
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: pageStart '+e)
    }
  }

  /**
   * Set a user ID.
   * @important: When the setUserId API is called, if the old userId is not empty and is different from the new userId, a new session is generated.
   * If you do not want to use setUserId to identify a user (for example, when a user signs out), set userId to **null**.
   */
  async setUserId(){
    console.log("Calling setUserId")

    if(this.state.userId === ""){
        alert("Please, write a user ID")
        return
    }

    try{
      let result = await HmsAnalytics.setUserId(this.state.userId)
      this.createCustomView("setUserId :  ", this.state.userId)
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: setUserId '+e)
    }
  }

  /**
   * User attribute values remain unchanged throughout the app's lifecycle and session.
   * A maximum of 25 user attribute names are supported.
   * If an attribute name is duplicate with an existing one, the attribute names needs to be changed.
   */
  async setUserProfile(){
    console.log("Calling setUserProfile")

    var name="favor_sport"
    var value="volleyball"
    try{
      let result = await HmsAnalytics.setUserProfile(name,value)
      this.createCustomView("setUserProfile :  ", "name: "+name+ "   value: "+value)
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: setUserProfile '+e)
    }
  }

  /**
   * Deletes user profile.
   */
  async deleteUserProfile(){
    console.log("Calling deleteUserProfile")

    let name="favor_sport"
    try{
      let result = await HmsAnalytics.deleteUserProfile(name)
      this.createCustomView("deleteUserProfile :  ", "name: "+name)
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: deleteUserProfile '+e)
    }
  }

  /**
   * Sets the push token, which is obtained using the Push Kit.
   * @note This function is specifically used by Android Platforms.
   */
  async setPushToken(){
    console.log("Calling setPushToken")

    var token="eyjhbGciOijlUzi1Nilshkjkşvbnm56iknyy88t695hdjnbv9csa7ap6g96hh9ıyımuy8020kfjasew63w980uplmvb45"
    try{
      let result = await HmsAnalytics.setPushToken(token)
      this.createCustomView("setPushToken :  ", "token : "+token)
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: setPushToken '+e+ '    token: '+token)
    }
  }

  /**
   * Sets the session timeout interval.
   */
  async setSessionDuration(){
    console.log("Calling setSessionDuration")

    var sessionDurationValue=1500000
    try{
      let result = await HmsAnalytics.setSessionDuration(sessionDurationValue)
      this.createCustomView("setSessionDuration :  ",  sessionDurationValue+ ' millisecond')
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: setSessionDuration '+e)
    }
  }

  /**
   * Sets the minimum interval for starting a new session.
   */
  async setMinActivitySessions(){
    console.log("Calling setMinActivitySessions")

    //param => milisecond, Default value:3000
    var minActivitySessionValue=2500
    try{
      let result = await HmsAnalytics.setMinActivitySessions(minActivitySessionValue)
      this.createCustomView("setMinActivity Sessions :  ",  minActivitySessionValue+ ' millisecond')
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: setMinActivitySession '+e)
    }
  }

  /**
   * Report custom events.
   */
  async onEvent(){
    console.log("Calling onEvent")

    if(!this.validation())
      return

    try{
      var eventId=this.state.eventId
      var bundle= { "name" : this.state.paramId, "value":this.state.eventBundleValue}
      let result = await HmsAnalytics.onEvent(eventId,bundle)
      this.createCustomView("onEvent : " ,"eventId:"+eventId+"   bundle: "+ JSON.stringify(bundle))
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: onEvent '+e)
    }
  }

  /**
   * Delete all collected data in the local cache, including the cached data that fails to be sent.
   */
  async clearCachedData(){
    console.log("Calling clearCachedData")
    try{
      let result= await HmsAnalytics.clearCachedData()
      this.createCustomView("clearCachedData:  ", " successful!")
      console.log(result)
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: clearCacheData '+e)
    }
  }

  /**
   * Obtains the app instance ID from AppGallery Connect.
   */
  async getAAID(){
    console.log("Calling getAAID")

    try{
      let result=await HmsAnalytics.getAAID()
      this.createCustomView("getAAID: ",JSON.stringify(result) + "")
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: getAAID '+e)
    }
  }

  /**
   * Enables AB Testing. Predefined or custom user attributes are supported.
   */
  async getUserProfiles(){
    console.log("Calling getUserProfiles")

    try{
      var preDefined=true
      let result=await HmsAnalytics.getUserProfiles(preDefined)
      var profile=JSON.stringify(result)
      this.createCustomView("getUserProfiles: ",profile + "")
      console.log(result);
    }catch(e){
      alert(JSON.stringify(e))
      console.log('ERR: getUserProfiles '+e)
    }
  }

  /**
   * Sets data reporting policies.
   */
  async setReportPolicies() {
    console.log("Calling setReportPolicies")
    try {
      let result = await HmsAnalytics.setReportPolicies([{[HmsAnalytics.Constants.REPORT_POLICY_TYPE]: HmsAnalytics.ReportPolicyType.AppLaunchPolicy},
        {[HmsAnalytics.Constants.REPORT_POLICY_TYPE]: HmsAnalytics.ReportPolicyType.ScheduledTimePolicy, [HmsAnalytics.Constants.SECONDS]: 200},
        {[HmsAnalytics.Constants.REPORT_POLICY_TYPE]: HmsAnalytics.ReportPolicyType.MoveBackgroundPolicy},
        {[HmsAnalytics.Constants.REPORT_POLICY_TYPE]: HmsAnalytics.ReportPolicyType.CacheThresholdPolicy, [HmsAnalytics.Constants.THRESHOLD]: 200}])
      this.createCustomView("setReportPolicies :  ", JSON.stringify(result) + "")
      console.log(result);
    } catch (e) {
      alert(JSON.stringify(e))
      console.log('ERR: setReportPolicies ' + e)
    }
  }

  /**
   * Obtains the threshold for event reporting.
   */
  async getReportPolicyThreshold() {
    console.log("Calling getReportPolicyThreshold")
    try {
      let result = await HmsAnalytics.getReportPolicyThreshold(HmsAnalytics.ReportPolicyType.AppLaunchPolicy)
      this.createCustomView("getReportPolicyThreshold :  ", JSON.stringify(result) + "")
      console.log(result);
    } catch (e) {
      alert(JSON.stringify(e))
      console.log('ERR: getReportPolicyThreshold ' + e)
    }
  }

  renderPageStartElement(){
    return <TouchableOpacity activeOpacity={.7} style={styles.btn}
                             onPress={() => this.pageStart()}>
      <Text style={styles.txt}>Page Start</Text>
    </TouchableOpacity>
  }

  renderPageEndElement(){
    return  <TouchableOpacity activeOpacity={.7} style={styles.btn}
                              onPress={() => this.pageEnd()}>
      <Text style={styles.txt}>Page End</Text>
    </TouchableOpacity>
  }

  renderSetAnalyticsEnabledElement(){
    return <TouchableOpacity activeOpacity={.7} style={styles.btn}
                             onPress={() => this.setAnalyticsEnabled()}>
      <Text style={styles.txt}>Set Analytics Enabled</Text>
    </TouchableOpacity>
  }

  renderEnableLogElement(){
    return <TouchableOpacity activeOpacity={.7} style={styles.btn}
                             onPress={() => this.enableLog()}>
      <Text style={styles.txt}>{'Enable log'}</Text>
    </TouchableOpacity>
  }

  renderEnableLogWithLevelElement(){
    return  <View key={"enableLogWithLevel"} style={styles.partialView}>

      <Text style={[styles.pickerItem,{marginLeft:60,marginBottom:-10}]}>Select a logLevel </Text>

      <ScrollView showsVerticalScrollIndicator={true} nestedScrollEnabled={true}
                  style={styles.picker}>

        {this.state.logLevelList}

      </ScrollView>

      <TouchableOpacity activeOpacity={.7} style={styles.btn}
                        onPress={() => this.enableLogWithLevel()}>
        <Text style={styles.txt}>{'Enable Log With Level: '+this.state.selectedLogLevel }</Text>
      </TouchableOpacity>

    </View>
  }

  renderSetPushTokenElement(){
    return <TouchableOpacity activeOpacity={.7} style={styles.btn}
                             onPress={() => this.setPushToken()}>
      <Text style={styles.txt}>Set Push Token</Text>
    </TouchableOpacity>
  }

  renderSetMinActivitySessions(){
    return <TouchableOpacity activeOpacity={.7} style={styles.btn}
                             onPress={() => this.setMinActivitySessions()}>
      <Text style={styles.txt}>Set Minumum Activity Sessions</Text>
    </TouchableOpacity>
  }

  constructor(props){
    super(props)
    this.state={
      customViews:[],
      logLevelList:[],
      eventList:[],
      paramList:[],
      selectedLogLevel:logLevel.debug,
      userId:"",
      eventId:"",
      paramId:"",
      eventBundleValue:"",
      isAnalyticsEnabled:true,
      isCustomEvent:true,
      showHaParamList:false
    }
  }

  // MARK: - UI Helpers

  componentDidMount(){
    this.createPicker(pickerType.logLevel.toString())

  }

  createCustomView(title, description){
    var view=(
        <View key={title+description} style={styles.resultView}>
          <Text style={[styles.txt,{width:110,textAlign:"left"}]}>{title}</Text>
          <Text style={[styles.txt,{color:'white',textAlign:'left',width:186,marginLeft:5}]}>{description}</Text>
        </View>
    )
    var views=[]
    views.push(view)
    this.setState({customViews:views})
  }

  createPicker(type){

    var data=[]
    switch(type){
      case pickerType.logLevel:{
        data=logLevel
        break;
      }
      case pickerType.eventType:{
        data=HmsAnalytics.HAEventType
        break;
      }
      case pickerType.eventParamType :{
        data=HmsAnalytics.HAParamType
        break;
      }
    }

    var list=[]
    for (var i=0 ; i< Object.keys(data).length ; i++){

      let value=Object.values(data)[i]

      var item= (
          <TouchableOpacity
              key={type+""+i} onPress={()=>this.changePickerSelection(value,type)} >
            <Text style={styles.pickerItem}>{value}</Text>
          </TouchableOpacity>
      )
      list.push(item)

    }

    switch(type){
      case pickerType.logLevel:{
        this.setState({logLevelList:list})
        break;
      }
      case pickerType.eventType:{
        this.setState({eventList:list})
        break;
      }
      case pickerType.eventParamType:{

        this.setState({paramList:list})
        break;
      }
    }

  }

  changePickerSelection(value,type){

    switch(type){
      case pickerType.logLevel:{
        this.setState({selectedLogLevel:value})
        break;
      }
      case pickerType.eventType:{
        this.setState({eventId:value})
        break;
      }
      case pickerType.eventParamType:{
        this.setState({paramId:value})
        break;
      }
    }

  }

  changeEventType(selection){

    this.createPicker(pickerType.eventType.toString())
    this.setState({isCustomEvent:selection})
  }

  validation(){

    if(!this.state.isCustomEvent &&  this.state.eventId==""){
      alert("Please select a eventID")
      return false
    }
    else if(this.state.isCustomEvent && this.state.eventId=="" ){
      alert("Please enter a eventID")
      return false
    }
    else if(this.state.paramId=="" || this.state.paramId==null){
      alert("Please enter a bundleName")
      return false
    }
    else if(this.state.eventBundleValue=="" || this.state.eventBundleValue==null){
      alert("Please enter a bundleValue")
      return false
    }
    return true
  }

render() {
    return (
      <ScrollView style={styles.scrollView} nestedScrollEnabled={true}>
        <View style={styles.container}>
          {this.renderPageStartElement()}
          {this.renderPageEndElement()}
          {this.renderSetAnalyticsEnabledElement()}
          {this.renderEnableLogElement()}
          {this.renderEnableLogWithLevelElement()}

          <TouchableOpacity activeOpacity={.7} style={styles.btn}
                            onPress={() => this.setReportPolicies()}>
            <Text style={styles.txt}>Set Report Policies</Text>
          </TouchableOpacity>

          <TouchableOpacity activeOpacity={.7} style={styles.btn}
                            onPress={() => this.getReportPolicyThreshold()}>
            <Text style={styles.txt}>Get ReportPolicy Threshold</Text>
          </TouchableOpacity>

          <TouchableOpacity activeOpacity={.7} style={styles.btn}
            onPress={() => this.clearCachedData()}>
            <Text style={styles.txt}>Clear Cached Data</Text>
          </TouchableOpacity>

          <TouchableOpacity activeOpacity={.7} style={styles.btn}
                            onPress={() => this.setRestrictionEnabled()}>
            <Text style={styles.txt}>setRestrictionEnabled</Text>
          </TouchableOpacity>

          <TouchableOpacity activeOpacity={.7} style={styles.btn}
                            onPress={() => this.isRestrictionEnabled()}>
            <Text style={styles.txt}>isRestrictionEnabled</Text>
          </TouchableOpacity>

          <TouchableOpacity activeOpacity={.7} style={styles.btn}
            onPress={() => this.setUserProfile()}>
            <Text style={styles.txt}>Set User Profile</Text>
          </TouchableOpacity>

          <TouchableOpacity activeOpacity={.7} style={styles.btn}
                            onPress={() => this.deleteUserProfile()}>
            <Text style={styles.txt}>Delete User Profile</Text>
          </TouchableOpacity>

          {this.renderSetPushTokenElement()}

          <TouchableOpacity activeOpacity={.7} style={styles.btn}
            onPress={() => this.setSessionDuration()}>
            <Text style={styles.txt}>Set Session Duration</Text>
          </TouchableOpacity>

          {this.renderSetMinActivitySessions()}

          <View key={"setUserID"} style={styles.partialView}>
            <Text style={[styles.pickerItem,{alignSelf:'center',marginTop:10}]}>User ID </Text>
            <TextInput
            style={styles.input}
            placeholder={"examp: f84137265"}
            placeholderTextColor={"gray"}
            numberOfLines={1} value={this.state.userId}
            onChangeText={text => this.setState({userId:text})}/>
            <TouchableOpacity activeOpacity={.7} style={styles.btn}
              onPress={() => this.setUserId()}>
              <Text style={styles.txt}>Set UserID</Text>
            </TouchableOpacity>
          </View>

          <View key={"onEvent"} style={[styles.partialView,{marginTop:20}]} >

            <Combobox changeSelection={(selection)=>this.changeEventType(selection)} select={false}/>

            {this.state.isCustomEvent?
              <View>
                <Text style={[styles.pickerItem,{alignSelf:'center',marginTop:10}]}>EventID </Text>
                <TextInput
                style={styles.input}
                placeholder={"examp: HelpDesk"}
                placeholderTextColor={"gray"}
                numberOfLines={1}
                value={this.state.eventId}
                onChangeText={text => this.setState({eventId:text})}/>
              </View>
              :
              <View>

              <Text style={[styles.pickerItem,{alignSelf:'center',marginTop:10}]}>Please, select a eventID </Text>
              <ScrollView showsVerticalScrollIndicator={true} nestedScrollEnabled={true}
                style={[styles.picker,{marginTop:0}]}>
                {this.state.eventList}
              </ScrollView>

              </View>
            }

          <View>

          </View>

          <TouchableOpacity activeOpacity={.7} style={styles.btn}
            onPress={() =>[
              this.setState(prevState => ({
              showHaParamList: !prevState.showHaParamList
              })),

             this.createPicker(pickerType.eventParamType.toString())
            ]
            }>
            <Text style={styles.txt}>{this.state.showHaParamList ?"Visible HaParams": "Invisible HaParams"}</Text>
           </TouchableOpacity>


          {this.state.showHaParamList?
            <ScrollView showsVerticalScrollIndicator={true} nestedScrollEnabled={true}
              style={styles.picker}>

              {this.state.paramList}

            </ScrollView>
            :null}

            <Text style={[styles.pickerItem,{alignSelf:'center',marginTop:20}]}>BundleName </Text>
            <TextInput
             placeholder={"examp: 'Coin'"}
             placeholderTextColor={"gray"}
             style={[styles.input,{marginTop:0}]}
             numberOfLines={1}
             value={this.state.paramId}
             onChangeText={text => this.setState({paramId:text})}/>

            <Text style={[styles.pickerItem,{alignSelf:'center',marginTop:20}]}>BundleValue </Text>
            <TextInput
             placeholder={"examp: '130'"}
             placeholderTextColor={"gray"}
             style={[styles.input,{marginTop:0}]}
             numberOfLines={1}
             value={this.state.eventBundleValue}
             onChangeText={text => this.setState({eventBundleValue:text})}/>

           <TouchableOpacity activeOpacity={.7} style={[styles.btn,{width:180}]}
            onPress={() => this.onEvent()}>
            <Text style={styles.txt}>{'On Event \n'+this.state.eventId}</Text>
           </TouchableOpacity>

        </View>

          <TouchableOpacity activeOpacity={.7} style={styles.btn}
            onPress={() => this.getAAID()}>
            <Text style={styles.txt}>Get AAID</Text>
          </TouchableOpacity>

          <TouchableOpacity activeOpacity={.7} style={styles.btn}
            onPress={() => this.getUserProfiles()}>
            <Text style={styles.txt}>Get User Profiles</Text>
          </TouchableOpacity>

          <Text style={[styles.title]}>    Results    </Text>
          {this.state.customViews}

         </View>
      </ScrollView>
    )
  }

}

const styles = StyleSheet.create({
   scrollView:{
     flex:1,
     backgroundColor:'#2e343b'
   },
   container: {
     paddingTop:30,
     paddingBottom:30,
     flex:1,
     flexDirection:'row',
     flexWrap:'wrap',
     justifyContent:'center'
   },
   btn: {
     marginTop: 20,
     backgroundColor:'#0b1528',
     width:125,
     height:45,
     justifyContent:'center',
     alignSelf:'center',
     borderRadius:10,
     marginLeft:5,
     marginRight:5
   },
   txt: {
     fontSize:14,
     color:'#00ffad',
     textAlign:'center'
   },
   resultView:{
     flexDirection:'row',
     width:250,
     alignSelf:'center',
     marginLeft:5,
     marginRight:5,
     marginTop: 20
   },
   picker:{
     marginTop: 20,
     alignSelf:'center',
     backgroundColor:'#0b1528',
     width:250,
     borderRadius:10,
     marginLeft:5,
     marginRight:5,
     maxHeight:100
   },
  pickerItem:{
    fontSize:14,
    color:'white',
    marginTop:5,
    marginBottom:5,
    marginLeft:25
  },
  input: {
    fontSize:14,
    color:'white',
    width:135,
    borderWidth:1,
    alignSelf:'center',
    paddingLeft:10
  },
  partialView:{
    width:'100%',
    borderColor:'#00ffad',
    borderWidth:1,
    paddingBottom:10
  },
  title:{
    fontSize:20,
    textAlign:'center',
    color:'white',
    marginTop:30,
    width:'100%',
    textDecorationLine:"underline"
  }
 });
