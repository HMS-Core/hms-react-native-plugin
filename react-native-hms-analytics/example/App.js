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
import { apiName, pickerType, logLevel, styles } from './constants/Data'
import { Platform, ScrollView, Text, TouchableOpacity, View } from 'react-native';
import RenderComponent from './customViews/RenderComponent';
import HMSAnalytics from '@hmscore/react-native-hms-analytics'

/**
 * Provides methods to obtain HiAnalytics Kit functions both In Android & IOS Platforms.
 */
export default class App extends RenderComponent {

  constructor(props) {
    super(props)
    this.state = {
      paramId: "",
      eventBundleValue: "",
    }
    super.init(this, "AppScreen")
  }

  componentDidMount() {
    super.pickerView(pickerType.logLevel.toString())
  }

  async setAnalyticsEnabled() {
    const enabled = true
    HMSAnalytics.setAnalyticsEnabled(enabled)
      .then((res) => super.showResult(apiName.setAnalyEnabled, res))
      .catch((err) => super.showResult(apiName.setAnalyEnabled, err))
  }

  async setRestrictionEnabled() {
    HMSAnalytics.setRestrictionEnabled(false)
      .then((res) => super.showResult(apiName.setRestEnabled, res))
      .catch((err) => super.showResult(apiName.setRestEnabled, err))
  }

  async isRestrictionEnabled() {
    HMSAnalytics.isRestrictionEnabled()
      .then((res) => super.showResult(apiName.isRestEnabled, res))
      .catch((err) => super.showResult(apiName.isRestEnabled, err))
  }

  async setCollectAdsIdEnabled() {
    HMSAnalytics.setCollectAdsIdEnabled(false)
      .then((res) => super.showResult(apiName.setCollAdsIdEnabled, res))
      .catch((err) => super.showResult(apiName.setCollAdsIdEnabled, err))
  }

  async addDefaultEventParams() {
    const params = {
      "DefaultEventKey0": false,
      "DefaultEventKey1": 1,
      "DefaultEventKey2": "two",
    }
    HMSAnalytics.addDefaultEventParams(params)
      .then((res) => super.showResult(apiName.addDefEventPar, res))
      .catch((err) => super.showResult(apiName.addDefEventPar, err))
  }

  /**
   * @important: When the setUserId API is called, if the old userId is not empty and is different from the new userId, a new session is generated.
   * If you do not want to use setUserId to identify a user (for example, when a user signs out), set userId to **null**.
   */
  async setUserId(userID) {
    HMSAnalytics.setUserId(userID)
      .then((res) => super.showResult(apiName.setUserId, res))
      .catch((err) => super.showResult(apiName.setUserId, err))
  }

  async setUserProfile() {
    const name = "favor_sport"
    const value = "volleyball"
    HMSAnalytics.setUserProfile(name, value)
      .then((res) => super.showResult(apiName.setUserProf, res))
      .catch((err) => super.showResult(apiName.setUserProf, err))
  }

  async deleteUserProfile() {
    const name = "favor_sport"
    HMSAnalytics.deleteUserProfile(name)
      .then((res) => super.showResult(apiName.deleteUserProf, res))
      .catch((err) => super.showResult(apiName.deleteUserProf, err))
  }

  async setSessionDuration() {
    const sessionDurationValue = 1500000
    HMSAnalytics.setSessionDuration(sessionDurationValue)
      .then((res) => super.showResult(apiName.setSesDuration, res))
      .catch((err) => super.showResult(apiName.setSesDuration, err))
  }

  async onEvent() {
    if (!super.validation())
      return

    const eventId = this.state.eventId
    const bundle = {
      "name": this.state.paramId,
      "value": this.state.eventBundleValue
    }
    HMSAnalytics.onEvent(eventId, bundle)
      .then((res) => super.showResult(apiName.onEvent, res))
      .catch((err) => super.showResult(apiName.onEvent, err))
  }

  async onEventWithBundleList() {
    const eventId = HMSAnalytics.HAEventType.ADDPRODUCT2WISHLIST
    const bundleList = []
    const bundleChild1 = {
      "name": HMSAnalytics.HAParamType.PRODUCTID,
      "value": "itemId_1"
    }

    const bundleChild2 = {
      "name": HMSAnalytics.HAParamType.PRODUCTID,
      "value": "itemId_2"
    }

    bundleList.push(bundleChild1)
    bundleList.push(bundleChild2)

    const bundleChild3 = {
      "name": HMSAnalytics.HAParamType.SEARCHKEYWORDS,
      "value": "phone"
    }
    const bundle = {
      items: bundleList,
      bundleChild: bundleChild3
    }
    HMSAnalytics.onEvent(eventId, bundle)
      .then((res) => super.showResult(apiName.onEvent, res))
      .catch((err) => super.showResult(apiName.onEvent, err))
  }

  async clearCachedData() {
    HMSAnalytics.clearCachedData()
      .then((res) => super.showResult(apiName.clearCachedData, res))
      .catch((err) => super.showResult(apiName.clearCachedData, err))
  }

  async getAAID() {
    HMSAnalytics.getAAID()
      .then((res) => super.showResult(apiName.getAAID, res))
      .catch((err) => super.showResult(apiName.getAAID, err))
  }

  async getUserProfiles() {
    const preDefined = true
    HMSAnalytics.getUserProfiles(preDefined)
      .then((res) => super.showResult(apiName.getUserProf, res))
      .catch((err) => super.showResult(apiName.getUserProf, err))
  }

  async setReportPolicies() {
    HMSAnalytics.setReportPolicies([
      {
        [HMSAnalytics.Constants.REPORT_POLICY_TYPE]: HMSAnalytics.ReportPolicyType.AppLaunchPolicy
      },
      {
        [HMSAnalytics.Constants.REPORT_POLICY_TYPE]: HMSAnalytics.ReportPolicyType.ScheduledTimePolicy,
        [HMSAnalytics.Constants.SECONDS]: 200
      },
      {
        [HMSAnalytics.Constants.REPORT_POLICY_TYPE]: HMSAnalytics.ReportPolicyType.MoveBackgroundPolicy
      },
      {
        [HMSAnalytics.Constants.REPORT_POLICY_TYPE]: HMSAnalytics.ReportPolicyType.CacheThresholdPolicy,
        [HMSAnalytics.Constants.THRESHOLD]: 200
      }
    ])
      .then((res) => super.showResult(apiName.setReportPolic, res))
      .catch((err) => super.showResult(apiName.setReportPolic, err))
  }

  /*
   * @note These function ate specifically used by Android Platforms.
   */

  async pageStart() {
    this.checkPlatform()
    const screenName = "AppScreen"
    const screenClassOverride = "App"
    HMSAnalytics.pageStart(screenName, screenClassOverride)
      .then((res) => super.showResult(apiName.startPage, res))
      .catch((err) => super.showResult(apiName.startPage, err))
  }

  async pageEnd() {
    this.checkPlatform()
    const screenName = "AppScreen"
    HMSAnalytics.pageEnd(screenName)
      .then((res) => super.showResult(apiName.endPage, res))
      .catch((err) => super.showResult(apiName.endPage, err))
  }

  async enableLog() {
    this.checkPlatform()
    // defaultValue= logLevel.debug
    HMSAnalytics.enableLog()
      .then((res) => super.showResult(apiName.enableLog, res))
      .catch((err) => super.showResult(apiName.enableLog, err))
  }

  async enableLogWithLevel() {
    this.checkPlatform()
    HMSAnalytics.enableLogWithLevel(logLevel.debug)
      .then((res) => super.showResult(apiName.enableLogWithLevel, res))
      .catch((err) => super.showResult(apiName.enableLogWithLevel, err))
  }

  async setPushToken() {
    this.checkPlatform()
    const token = "eyjhbGciOijlUzi1Nilshkjkşvbnm56iknyy88t695hdjnbv9csa7ap6g96hh9ıyımuy8020kfjasew63w980uplmvb45"
    HMSAnalytics.setPushToken(token)
      .then((res) => super.showResult(apiName.setPushToken, res))
      .catch((err) => super.showResult(apiName.setPushToken, err))
  }

  async setMinActivitySessions() {
    this.checkPlatform()
    //param => milisecond, Default value:3000
    const minActivitySessionValue = 2500
    HMSAnalytics.setMinActivitySessions(minActivitySessionValue)
      .then((res) => super.showResult(apiName.minActSession, res))
      .catch((err) => super.showResult(apiName.minActSession, err))
  }

  async getReportPolicyThreshold() {
    this.checkPlatform()
    HMSAnalytics.getReportPolicyThreshold(HMSAnalytics.ReportPolicyType.AppLaunchPolicy)
      .then((res) => super.showResult(apiName.getRepPolicyT, res))
      .catch((err) => super.showResult(apiName.getRepPolicyT, err))
  }

  checkPlatform() {
    if (Platform.OS === 'ios') {
      alert("This function is not available in iOS platforms.")
      return
    }
  }

  render() {
    return (
      <View style={styles.mainContainer}>
        <View style={styles.header}>
          <Text style={styles.headerText}>HMS RN Analytics Kit</Text>
        </View>

        <ScrollView style={styles.scrollView} nestedScrollEnabled={true}>
          <View style={styles.container}>

            <TouchableOpacity activeOpacity={.7} style={styles.btn}
              onPress={() => this.setAnalyticsEnabled()}>
              <Text style={styles.txt}>{apiName.setAnalyEnabled}</Text>
            </TouchableOpacity>

            <TouchableOpacity activeOpacity={.7} style={styles.btn}
              onPress={() => this.setReportPolicies()}>
              <Text style={styles.txt}>Set Report Policies</Text>
            </TouchableOpacity>

            <TouchableOpacity activeOpacity={.7} style={styles.btn}
              onPress={() => this.clearCachedData()}>
              <Text style={styles.txt}>{apiName.clearCachedData}</Text>
            </TouchableOpacity>

            <TouchableOpacity activeOpacity={.7} style={styles.btn}
              onPress={() => this.setRestrictionEnabled()}>
              <Text style={styles.txt}>{apiName.setRestEnabled}</Text>
            </TouchableOpacity>

            <TouchableOpacity activeOpacity={.7} style={styles.btn}
              onPress={() => this.isRestrictionEnabled()}>
              <Text style={styles.txt}>{apiName.isRestEnabled}</Text>
            </TouchableOpacity>

            <TouchableOpacity activeOpacity={.7} style={styles.btn}
              onPress={() => this.setUserProfile()}>
              <Text style={styles.txt}>{apiName.setUserProf}</Text>
            </TouchableOpacity>

            <TouchableOpacity activeOpacity={.7} style={styles.btn}
              onPress={() => this.deleteUserProfile()}>
              <Text style={styles.txt}>{apiName.deleteUserProf}</Text>
            </TouchableOpacity>

            <TouchableOpacity activeOpacity={.7} style={styles.btn}
              onPress={() => this.setSessionDuration()}>
              <Text style={styles.txt}>{apiName.setSesDuration}</Text>
            </TouchableOpacity>

            {super.setUserIdView()}

            {super.onEventView()}

            <View style={{ flexWrap: 'wrap', flexDirection: 'row', }}>

              <TouchableOpacity activeOpacity={.7} style={styles.btn}
                onPress={() => this.onEventWithBundleList()}>
                <Text style={styles.txt}>{apiName.onEvent + "WithBundleList"}</Text>
              </TouchableOpacity>

              <TouchableOpacity activeOpacity={.7} style={styles.btn}
                onPress={() => this.addDefaultEventParams()}>
                <Text style={styles.txt}>{apiName.addDefEventPar}</Text>
              </TouchableOpacity>

              <TouchableOpacity activeOpacity={.7} style={styles.btn}
                onPress={() => this.getAAID()}>
                <Text style={styles.txt}>{apiName.getAAID}</Text>
              </TouchableOpacity>

              <TouchableOpacity activeOpacity={.7} style={styles.btn}
                onPress={() => this.getUserProfiles()}>
                <Text style={styles.txt}>{apiName.getUserProf}</Text>
              </TouchableOpacity>

            </View>

            {Platform.OS === "android" ?
              <View style={{ flexWrap: 'wrap', flexDirection: 'row', }}>

                <TouchableOpacity activeOpacity={.7} style={styles.btn}
                  onPress={() => this.pageStart()}>
                  <Text style={styles.txt}>{apiName.startPage}</Text>
                </TouchableOpacity>

                <TouchableOpacity activeOpacity={.7} style={styles.btn}
                  onPress={() => this.pageEnd()}>
                  <Text style={styles.txt}>{apiName.endPage}</Text>
                </TouchableOpacity>

                {super.enableLogWithLevelView()}

                <TouchableOpacity activeOpacity={.7} style={styles.btn}
                  onPress={() => this.enableLog()}>
                  <Text style={styles.txt}>{apiName.enableLog}</Text>
                </TouchableOpacity>

                <TouchableOpacity activeOpacity={.7} style={styles.btn}
                  onPress={() => this.setPushToken()}>
                  <Text style={styles.txt}>{apiName.setPushToken}</Text>
                </TouchableOpacity>

                <TouchableOpacity activeOpacity={.7} style={styles.btn}
                  onPress={() => this.setMinActivitySessions()}>
                  <Text style={styles.txt}>{apiName.minActSession}</Text>
                </TouchableOpacity>

                <TouchableOpacity activeOpacity={.7} style={styles.btn}
                  onPress={() => this.getReportPolicyThreshold()}>
                  <Text style={styles.txt}>{apiName.getRepPolicyT}</Text>
                </TouchableOpacity>
              </View>
              : null
            }

          </View>
        </ScrollView>
      </View>
    )
  }
}
