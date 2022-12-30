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

import React from "react";
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  Button,
  Modal,
  BackHandler,
  Picker,
  ToastAndroid,
} from "react-native";

import { Colors } from "react-native/Libraries/NewAppScreen";
import HMSAds, {
  HMSBanner,
  HMSInstream,
  HMSNative,
  HMSVastView,
  HMSVast,
  HMSInterstitial,
  HMSOaid,
  HMSInstallReferrer,
  HMSSplash,
  HMSReward,
} from "@hmscore/react-native-hms-ads";

const toast = (tag, message) => {
  ToastAndroid.show(tag, ToastAndroid.SHORT);
  message ? console.log(tag, message) : console.log(tag);
};

let adBannerElement;
let adInstreamElement;
let adNativeElement;
let adVastElement;

class Banner extends React.Component {
  constructor(props) {
    super(props);
    bannerAdIds = {};
    bannerAdIds[HMSAds.BannerMediaTypes.IMAGE] = "testw6vs28auh3";
    this.state = {
      bannerAdSize: HMSAds.BannerAdSizes.B_320_100,
      adId: bannerAdIds[HMSAds.BannerMediaTypes.IMAGE],
    };
  }

  render() {
    return (
      <>
        <View style={styles.sectionContainer}>
          <Picker
            prompt="Select ad size"
            selectedValue={this.state.bannerAdSize}
            onValueChange={(itemValue) => {
              this.setState({
                bannerAdSize: itemValue,
              });
            }}
          >
            {Object.values(HMSAds.BannerAdSizes).map((adSize) => (
              <Picker.Item label={adSize} value={adSize} key={adSize} />
            ))}
          </Picker>
          <Button
            title="Load"
            onPress={() => {
              if (adBannerElement !== null) {
                adBannerElement.loadAd();
              }
            }}
          />
          <Button
            title="Info"
            color="purple"
            onPress={() => {
              if (adBannerElement !== null) {
                adBannerElement
                  .getInfo()
                  .then((res) => toast("HMSBanner, ref.getInfo", res))
                  .catch((err) => alert(err));
              }
            }}
          />
          <Button
            title="Set Refresh"
            color="green"
            onPress={() => {
              if (adBannerElement !== null) {
                adBannerElement.setRefresh(60);
              }
            }}
          />
          <Button
            title="Pause"
            onPress={() => {
              if (adBannerElement !== null) {
                adBannerElement.pause();
              }
            }}
          />
          <Button
            title="Resume"
            color="green"
            onPress={() => {
              if (adBannerElement !== null) {
                adBannerElement.resume();
              }
            }}
          />
          <Button
            title="Destroy"
            color="red"
            onPress={() => {
              if (adBannerElement !== null) {
                adBannerElement.destroy();
              }
            }}
          />
          <HMSBanner
            style={{ height: 100 }}
            bannerAdSize={this.state.bannerAdSize}
            adId={this.state.adId}
            adParam={{
              adContentClassification:
                HMSAds.ContentClassification.AD_CONTENT_CLASSIFICATION_UNKNOWN,
              // appCountry: '',
              // appLang: '',
              // belongCountryCode: '',
              gender: HMSAds.Gender.UNKNOWN,
              nonPersonalizedAd: HMSAds.NonPersonalizedAd.ALLOW_ALL,
              // requestOrigin: '',
              tagForChildProtection:
                HMSAds.TagForChild.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED,
              tagForUnderAgeOfPromise: HMSAds.UnderAge.PROMISE_UNSPECIFIED,
              // targetingContentUrl: '',
              //requestLocation: true,
              location: {
                lat: 15,
                lng: 12
              }
            }}
            onAdLoaded={(_) => toast("HMSBanner onAdLoaded")}
            onAdFailed={(e) => {
              toast("HMSBanner onAdFailed", e.nativeEvent);
            }}
            onAdOpened={(_) => toast("HMSBanner onAdOpened")}
            onAdClicked={(_) => toast("HMSBanner onAdClicked")}
            onAdClosed={(_) => toast("HMSBanner onAdClosed")}
            onAdImpression={(_) => toast("HMSBanner onAdImpression")}
            onAdLeave={(_) => toast("HMSBanner onAdLeave")}
            ref={(el) => {
              adBannerElement = el;
            }}
          />
        </View>
      </>
    );
  }
}

class Instream extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <>
        <View style={styles.sectionContainer}>
          <Button
            title="Load"
            onPress={() => {
              if (adInstreamElement !== null) {
                adInstreamElement.loadAd();
              }
            }}
          />
          <Button
            title="Info"
            color="purple"
            onPress={() => {
              if (adInstreamElement !== null) {
                adInstreamElement
                  .getInfo()
                  .then((res) => toast("HMSInstream, ref.getInfo", res))
                  .catch((err) => alert(err));
              }
            }}
          />
          <Button
            color="green"
            title="Register"
            onPress={() => {
              if (adInstreamElement !== null) {
                adInstreamElement.register();
              }
            }}
          />
          <Button
            title="Mute"
            onPress={() => {
              if (adInstreamElement !== null) {
                adInstreamElement.mute();
              }
            }}
          />
          <Button
            title="Unmute"
            color="purple"
            onPress={() => {
              if (adInstreamElement !== null) {
                adInstreamElement.unmute();
              }
            }}
          />
          <Button
            title="Stop"
            color="red"
            onPress={() => {
              if (adInstreamElement !== null) {
                adInstreamElement.stop();
              }
            }}
          />
          <Button
            title="Pause"
            onPress={() => {
              if (adInstreamElement !== null) {
                adInstreamElement.pause();
              }
            }}
          />
          <Button
            title="Play"
            color="green"
            onPress={() => {
              if (adInstreamElement !== null) {
                adInstreamElement.play();
              }
            }}
          />
          <Button
            title="Destroy"
            color="red"
            onPress={() => {
              if (adInstreamElement !== null) {
                adInstreamElement.destroy();
              }
            }}
          />
          <HMSInstream
            style={{ height: 189, width: 328 }}
            adId="testy3cglm3pj0"
            maxCount={4}
            totalDuration={60}
            onClick={(_) => toast("HMSInstream onClick")}
            onMute={(_) => toast("HMSInstream onMute")}
            onUnmute={(_) => toast("HMSInstream onUnmute")}
            onAdLoaded={(_) => toast("HMSInstream onAdLoaded")}
            onAdFailed={(e) => toast("HMSInstream onAdFailed", e.nativeEvent)}
            onSegmentMediaChange={(e) =>
              toast("HMSInstream onSegmentMediaChange", e.nativeEvent)
            }
            onMediaProgress={(e) =>
              console.log("HMSInstream onMediaProgress", e.nativeEvent)
            }
            onMediaStart={(e) =>
              toast("HMSInstream onMediaStart", e.nativeEvent)
            }
            onMediaPause={(e) =>
              toast("HMSInstream onMediaPause", e.nativeEvent)
            }
            onMediaStop={(e) => toast("HMSInstream onMediaStop", e.nativeEvent)}
            onMediaCompletion={(e) =>
              toast("HMSInstream onMediaCompletion", e.nativeEvent)
            }
            onMediaError={(e) =>
              toast("HMSInstream onMediaError", e.nativeEvent)
            }
            ref={(el) => {
              adInstreamElement = el;
            }}
          />
        </View>
      </>
    );
  }
}

class Native extends React.Component {
  constructor(props) {
    super(props);
    nativeAdIds = {};
    nativeAdIds[HMSAds.NativeMediaTypes.VIDEO] = "testy63txaom86";
    nativeAdIds[HMSAds.NativeMediaTypes.IMAGE_SMALL] = "testb65czjivt9";
    nativeAdIds[HMSAds.NativeMediaTypes.IMAGE_LARGE] = "testu7m3hc4gvm";
    this.state = {
      displayForm: {
        mediaType: HMSAds.NativeMediaTypes.VIDEO,
        adId: nativeAdIds.video,
      },
    };
  }

  render() {
    return (
      <>
        <View style={styles.sectionContainer}>
          <Picker
            prompt="Select display form"
            selectedValue={this.state.displayForm.mediaType}
            onValueChange={(itemValue) => {
              this.setState({
                displayForm: {
                  mediaType: itemValue,
                  adId: nativeAdIds[itemValue],
                },
              });
            }}
          >
            {Object.values(HMSAds.NativeMediaTypes).map((mType) => (
              <Picker.Item label={mType} value={mType} key={mType} />
            ))}
          </Picker>
          <Button
            title="Load"
            onPress={() => {
              if (adNativeElement !== null) {
                adNativeElement.loadAd();
              }
            }}
          />
          <Button
            title="Info"
            color="purple"
            onPress={() => {
              if (adNativeElement !== null) {
                adNativeElement
                  .getInfo()
                  .then((res) => toast("HMSNative, ref.getInfo", res))
                  .catch((err) => alert(err));
              }
            }}
          />
          <Button
            title="Dislike"
            color="orange"
            onPress={() => {
              if (adNativeElement !== null) {
                adNativeElement.dislikeAd("Because I dont like it");
              }
            }}
          />
          <Button
            title="Go to Why Page"
            color="purple"
            onPress={() => {
              if (adNativeElement !== null) {
                adNativeElement.gotoWhyThisAdPage();
              }
            }}
          />
          <Button
            title="Destroy"
            color="red"
            onPress={() => {
              if (adNativeElement !== null) {
                adNativeElement.destroy();
              }
            }}
          />
          <Button
            title="Allow custom click"
            onPress={() => {
              if (adNativeElement !== null) {
                adNativeElement.setAllowCustomClick();
              }
            }}
          />
          <Button
            title="Record click event"
            color="green"
            onPress={() => {
              if (adNativeElement !== null) {
                adNativeElement.recordClickEvent();
              }
            }}
          />
          <Button
            title="Record impression"
            color="red"
            onPress={() => {
              if (adNativeElement !== null) {
                adNativeElement.recordImpressionEvent({
                  impressed: true,
                  isUseful: "nope",
                });
              }
            }}
          />
        </View>
        <View>
          <HMSNative
            style={{ height: 322 }}
            displayForm={this.state.displayForm}
            adParam={{
              adContentClassification:
                HMSAds.ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
              // appCountry: '',
              // appLang: '',
              // belongCountryCode: '',
              gender: HMSAds.Gender.UNKNOWN,
              nonPersonalizedAd: HMSAds.NonPersonalizedAd.ALLOW_ALL,
              // requestOrigin: '',
              tagForChildProtection:
                HMSAds.TagForChild.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED,
              tagForUnderAgeOfPromise: HMSAds.UnderAge.PROMISE_UNSPECIFIED,
              // targetingContentUrl: '',
              //requestLocation: true,
              detailedCreativeTypes: [
                HMSAds.DetailedCreativeTypes.BIG_IMG,
                HMSAds.DetailedCreativeTypes.VIDEO,
                HMSAds.DetailedCreativeTypes.SMALL_IMG,
              ],
            }}
            nativeConfig={{
              choicesPosition: HMSAds.ChoicesPosition.TOP_RIGHT,
              mediaDirection: HMSAds.Direction.ANY,
              // mediaAspect: 2,
              // requestCustomDislikeThisAd: false,
              // requestMultiImages: false,
              // returnUrlsForImages: false,
              // adSize: {
              //   height: 100,
              //   width: 100,
              // },
              videoConfiguration: {
                audioFocusType: HMSAds.AudioFocusType.NOT_GAIN_AUDIO_FOCUS_ALL,
                // clickToFullScreenRequested: true,
                // customizeOperateRequested: true,
                startMuted: true,
              },
            }}
            viewOptions={{
              showMediaContent: false,
              mediaImageScaleType: HMSAds.ScaleType.FIT_CENTER,
              // adSourceTextStyle: {color: 'red'},
              // adFlagTextStyle: {backgroundColor: 'red', fontSize: 10},
              // titleTextStyle: {color: 'red'},
              descriptionTextStyle: { visibility: false },
              callToActionStyle: { color: "black", fontSize: 12 },
            }}
            onNativeAdLoaded={(_) => toast("HMSNative onNativeAdLoaded")}
            onAdDisliked={(_) => toast("HMSNative onAdDisliked")}
            onAdFailed={(e) => toast("HMSNative onAdFailed", e.nativeEvent)}
            onAdClicked={(_) => toast("HMSNative onAdClicked")}
            onAdImpression={(_) => toast("HMSNative onAdImpression")}
            onVideoStart={(_) => toast("HMSNative onVideoStart")}
            onVideoPlay={(_) => toast("HMSNative onVideoPlay")}
            onVideoEnd={(_) => toast("HMSNative onVideoEnd")}
            onVideoPause={(_) => toast("HMSNative onVideoPause")}
            onVideoMute={(e) => toast("HMSNative onVideoMute", e.nativeEvent)}
            ref={(el) => {
              adNativeElement = el;
            }}
          />
        </View>
      </>
    );
  }
}
class Vast extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <SafeAreaView>
        <View style={styles.sectionContainer}>
          <Button
            title="Load"
            onPress={() => {
              if (adVastElement !== null) {
                adVastElement.loadAd();
              }
            }}
          />
          <Button
            title="Info"
            color="purple"
            onPress={() => {
              if (adVastElement !== null) {
                adVastElement
                  .getInfo()
                  .then((res) => toast("Hms, ref.getInfo", JSON.stringify(res)))
                  .catch((err) => alert(err));
              }
            }}
          />
          <Button
            title="Destroy"
            color="red"
            onPress={() => {
              if (adVastElement !== null) {
                adVastElement.release();
              }
            }}
          />
          <Button
            title="Resume"
            color="green"
            onPress={() => {
              if (adVastElement !== null) {
                adVastElement.resume();
              }
            }}
          />
          <Button
            title="Pause"
            color="red"
            onPress={() => {
              if (adVastElement !== null) {
                adVastElement.pause();
              }
            }}
          />
          <Button
            title="Start Or Pause"
            color="blue"
            onPress={() => {
              if (adVastElement !== null) {
                adVastElement.startOrPause();
              }
            }}
          />
          <Button
            title="Toggle Mute State"
            color="red"
            onPress={() => {
              if (adVastElement !== null) {
                adVastElement.toggleMuteState(true);
              }
            }}
          />

        </View>
        <View style={{ marginTop: 50, height: 500 }}>
          <HMSVastView style={{ flex: 1 }}
            isTestAd={false}
            isCustomVideoPlayer={false}
            isAdLoadWithAdsData={true}
            adParam={{
              adId: "testy3cglm3pj0",
              totalDuration: 99,
              creativeMatchStrategy: HMSVast.CreativeMatchType.ANY,
              allowMobileTraffic: false,
              adOrientation: HMSVast.Orientation.LANDSCAPE,
              maxAdPods: 1,
              requestOption: {
                adContentClassification: HMSVast.ContentClassification.AD_CONTENT_CLASSIFICATION_A,
                //appCountry: "",
                //appLang: "",
                //consent: "",
                //requestLocation: true,
                nonPersonalizedAd: HMSVast.NonPersonalizedAd.PERSONALIZED,
                tagForChildProtection: HMSVast.TagForChild.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED,
                tagForUnderAgeOfPromise: HMSVast.UnderAge.PROMISE_UNSPECIFIED,
              }
            }}
            playerConfigs={{
              enableRotation: false,
              isEnableCutout: false,
              skipLinearAd: false,
              isEnablePortrait: true
            }}
            onLoadSuccess={(e) => toast("HMSVast onLoadSuccess", JSON.stringify(e.nativeEvent))}
            onLoadFailed={(_) => toast("HMSVast onLoadFailed")}
            onSuccess={(_) => toast("HMSVast onSuccess")}
            onFailed={(e) => toast("HMSVast onFailed", e.nativeEvent)}
            onPlayAdReady={(_) => toast("HMSVast onPlayAdReady")}
            onPlayAdFinish={(_) => toast("HMSVast onPlayAdFinish")}
            onBufferStart={(_) => toast("HMSVast onBufferStart")}
            onBufferEnd={(_) => toast("HMSVast onBufferEnd")}
            onPlayStateChanged={(e) => toast("HMSVast onPlayStateChanged", JSON.stringify(e.nativeEvent))}
            onVolumeChanged={(e) => toast("HMSVast onVolumeChanged", JSON.stringify(e.nativeEvent))}
            onScreenViewChanged={(e) => toast("HMSVast onScreenViewChanged", JSON.stringify(e.nativeEvent))}
            onProgressChanged={(e) => console.log(e.nativeEvent)}
            ref={(el) => {
              adVastElement = el;
            }}
          />
        </View>
      </SafeAreaView>
    );
  }
}


class Interstitial extends React.Component {
  constructor(props) {
    super(props);
    interstitialAdIds = {};
    interstitialAdIds[HMSAds.InterstitialMediaTypes.IMAGE] = "teste9ih9j0rc3";
    interstitialAdIds[HMSAds.InterstitialMediaTypes.VIDEO] = "testb4znbuh3n2";
    this.state = {
      isLoaded: false,
      displayForm: {
        mediaType: HMSAds.InterstitialMediaTypes.VIDEO,
        adId: interstitialAdIds.video,
      },
    };
  }
  componentDidMount() {
    HMSInterstitial.setAdId(this.state.displayForm.adId)
      .then((res) => toast("HMSInterstitial.setAdId", res))
      .catch((err) => alert(err));

    HMSInterstitial.adClosedListenerAdd(() =>
      toast("HMSInterstitial adClosed")
    ); // HMSInterstitial.adClosedListenerRemove();

    HMSInterstitial.adFailedListenerAdd((error) =>
      toast("HMSInterstitial adFailed", error)
    ); // HMSInterstitial.adFailedListenerRemove();

    HMSInterstitial.adLeaveListenerAdd(() => toast("HMSInterstitial adLeave")); // HMSInterstitial.adLeaveListenerRemove();

    HMSInterstitial.adOpenedListenerAdd(() =>
      toast("HMSInterstitial adOpened")
    ); // HMSInterstitial.adOpenedListenerRemove();

    HMSInterstitial.adLoadedListenerAdd((res) =>
      toast("HMSInterstitial adLoaded, result: ", res)
    ); // HMSInterstitial.adLoadedListenerRemove();

    HMSInterstitial.adClickedListenerAdd(() =>
      toast("HMSInterstitial adClicked")
    ); // HMSInterstitial.adClickedListenerRemove();

    HMSInterstitial.adImpressionListenerAdd(() =>
      toast("HMSInterstitial adImpression")
    ); // HMSInterstitial.adImpressionListenerRemove();

    HMSInterstitial.adCompletedListenerAdd(() =>
      toast("HMSInterstitial adCompleted")
    ); // HMSInterstitial.adCompletedListenerRemove();

    HMSInterstitial.adStartedListenerAdd(() =>
      toast("HMSInterstitial adStarted")
    ); // HMSInterstitial.adStartedListenerRemove();
  }

  componentWillUnmount() {
    HMSInterstitial.allListenersRemove();
  }

  render() {
    return (
      <>
        <View>
          <View style={styles.sectionContainer}>
            <Picker
              prompt="Media Type"
              selectedValue={this.state.displayForm.mediaType}
              style={styles.picker}
              onValueChange={(itemValue) => {
                this.setState({
                  displayForm: {
                    mediaType: itemValue,
                    adId: interstitialAdIds[itemValue],
                  },
                });
                HMSInterstitial.setAdId(interstitialAdIds[itemValue])
                  .then((res) => toast("HMSInterstitial.setAdId", res))
                  .catch((err) => alert(err));
              }}
            >
              {Object.values(HMSAds.InterstitialMediaTypes).map((mType) => (
                <Picker.Item label={mType} value={mType} key={mType} />
              ))}
            </Picker>
            <Button
              title="Load"
              onPress={() => {
                HMSInterstitial.loadAd()
                  .then((res) => toast("HMSInterstitial.loadAd", res))
                  .catch((err) => alert(err));
              }}
            />
            <Button
              title="Set Ad Parameter"
              onPress={() => {
                HMSInterstitial.setAdParam({
                  adContentClassification:
                    HMSAds.ContentClassification
                      .AD_CONTENT_CLASSIFICATION_UNKOWN,
                  // appCountry: '',
                  // appLang: '',
                  // belongCountryCode: '',
                  gender: HMSAds.Gender.UNKNOWN,
                  nonPersonalizedAd: HMSAds.NonPersonalizedAd.ALLOW_ALL,
                  // requestOrigin: '',
                  tagForChildProtection:
                    HMSAds.TagForChild.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED,
                  tagForUnderAgeOfPromise: HMSAds.UnderAge.PROMISE_UNSPECIFIED,
                  // targetingContentUrl: '',
                  //requestLocation: true,
                })
                  .then((res) => toast("HMSInterstitial.setAdParam", res))
                  .catch((err) => alert(err));
              }}
            />
            <Button
              color="green"
              title="Check"
              onPress={() => {
                HMSInterstitial.isLoaded()
                  .then((res) => {
                    toast("HMSInterstitial.isLoaded", res);
                    this.setState({ isLoaded: res });
                  })
                  .catch((err) => alert(err));
              }}
            />
            <Button
              title="Show"
              color="purple"
              disabled={!this.state.isLoaded}
              onPress={() => {
                this.setState({ isLoaded: false });
                HMSInterstitial.show()
                  .then((res) => toast("HMSInterstitial.show", res))
                  .catch((err) => alert(err));
              }}
            />
          </View>
        </View>
      </>
    );
  }
}

class Reward extends React.Component {
  constructor(props) {
    super(props);
    rewardAdIds = {};
    rewardAdIds[HMSAds.RewardMediaTypes.VIDEO] = "testx9dtjwj8hp";
    this.state = {
      isLoaded: false,
      displayForm: {
        mediaType: HMSAds.RewardMediaTypes.VIDEO,
        adId: rewardAdIds[HMSAds.RewardMediaTypes.VIDEO],
      },
    };
  }
  componentDidMount() {
    HMSReward.setAdId(this.state.displayForm.adId)
      .then((res) => toast("HMSReward.setAdId", res))
      .catch((err) => alert(err));
    HMSReward.loadWithAdId(true)
      .then((res) => toast("HMSReward.loadWithAdId", res))
      .catch((err) => alert(err));
    HMSReward.setVerifyConfig({ userId: "HMS_User", data: "HMS data" })
      .then((res) => toast("HMSReward.setVerifyConfig", res))
      .catch((err) => alert(err));

    HMSReward.adLoadedListenerAdd((res) =>
      toast("HMSReward adLoaded, result: ", res)
    ); // HMSReward.adLoadedListenerRemove();

    HMSReward.adFailedToLoadListenerAdd((error) =>
      console.warn("HMSReward adFailedToLoad, error: ", error)
    ); // HMSReward.adFailedToLoadListenerRemove();

    HMSReward.adFailedToShowListenerAdd((error) =>
      toast("HMSReward adFailedToShow, error: ", error)
    ); // HMSReward.adFailedToShowListenerRemove();

    HMSReward.adOpenedListenerAdd(() => toast("HMSReward adOpened")); // HMSReward.adOpenedListenerRemove();

    HMSReward.adClosedListenerAdd(() => toast("HMSReward adClosed")); // HMSReward.adClosedListenerRemove();

    HMSReward.adRewardedListenerAdd((reward) =>
      toast("HMSReward adRewarded, reward: ", reward)
    ); // HMSReward.adRewardedListenerRemove();
  }

  componentWillUnmount() {
    HMSReward.allListenersRemove();
  }

  render() {
    return (
      <>
        <View style={styles.sectionContainer}>
          <Picker
            prompt="Media Type"
            selectedValue={this.state.displayForm.mediaType}
            style={styles.picker}
            onValueChange={(itemValue) => {
              this.setState({
                displayForm: {
                  mediaType: itemValue,
                  adId: rewardAdIds[itemValue],
                },
              });
              HMSReward.setAdId(rewardAdIds[itemValue])
                .then((res) => toast("HMSReward.setAdId", res))
                .catch((err) => alert(err));
            }}
          >
            {Object.values(HMSAds.RewardMediaTypes).map((mType) => (
              <Picker.Item label={mType} value={mType} key={mType} />
            ))}
          </Picker>
          <Button
            title="Load"
            onPress={() => {
              HMSReward.loadAd()
                .then((res) => toast("HMSReward.loadAd", res))
                .catch((err) => alert(err));
            }}
          />
          <Button
            color="green"
            title="Check"
            onPress={() => {
              HMSReward.isLoaded()
                .then((res) => {
                  toast("HMSReward.isLoaded", res);
                  this.setState({ isLoaded: res });
                })
                .catch((err) => alert(err));
            }}
          />
          <Button
            title="Show"
            disabled={!this.state.isLoaded}
            onPress={() => {
              this.setState({ isLoaded: false });
              HMSReward.show()
                .then((res) => toast("HMSReward.show", res))
                .catch((err) => alert(err));
            }}
          />
        </View>
      </>
    );
  }
}

class Splash extends React.Component {
  constructor(props) {
    super(props);
    splashAdIds = {};
    splashAdIds[HMSAds.SplashMediaTypes.VIDEO] = "testd7c5cewoj6";
    splashAdIds[HMSAds.SplashMediaTypes.IMAGE] = "testq6zq98hecj";
    this.state = {
      mediaType: HMSAds.SplashMediaTypes.IMAGE,
    };
  }

  componentDidMount() {
    HMSSplash.setAdId(splashAdIds[this.state.mediaType])
      .then((res) => toast("HMSSplash.setAdId", res))
      .catch((err) => alert(err));
    HMSSplash.setLogoText("HMS App")
      .then((res) => toast("HMSSplash.setLogoText", res))
      .catch((err) => alert(err));
    HMSSplash.setCopyrightText("Copyright HMS")
      .then((res) => toast("HMSSplash.setCopyrightText", res))
      .catch((err) => console.log(err));

    HMSSplash.adLoadedListenerAdd(() => toast("HMSSplash adLoaded")); // HMSSplash.adLoadedListenerRemove();
    HMSSplash.adFailedToLoadListenerAdd((e) =>
      toast("HMSSplash adFailedToLoad", e)
    ); // HMSSplash.adFailedToLoadListenerRemove();
    HMSSplash.adDismissedListenerAdd(() => toast("HMSSplash adDismissed")); // HMSSplash.adDismissedListenerRemove();
    HMSSplash.adShowedListenerAdd(() => toast("HMSSplash adShowed")); // HMSSplash.adShowedListenerRemove();
    HMSSplash.adClickListenerAdd(() => toast("HMSSplash adClick")); // HMSSplash.adClickListenerRemove();
  }

  componentWillUnmount() {
    HMSSplash.allListenersRemove();
  }

  render() {
    return (
      <>
        <View style={styles.sectionContainer}>
          <Picker
            prompt="Media Type"
            selectedValue={this.state.mediaType}
            style={styles.picker}
            onValueChange={(itemValue) => {
              this.setState({ mediaType: itemValue });
              HMSSplash.setAdId(splashAdIds[itemValue])
                .then((res) => toast("HMSSplash.setAdId", res))
                .catch((err) => alert(err));
            }}
          >
            {Object.values(HMSAds.SplashMediaTypes).map((mType) => (
              <Picker.Item label={mType} value={mType} key={mType} />
            ))}
          </Picker>
          <Button
            title="Splash"
            color="green"
            onPress={() =>
              HMSSplash.show()
                .then((res) => toast("HMSSplash.show", res))
                .catch((err) => alert(err))
            }
          />
          <Button
            title="Set Ad Parameter"
            onPress={() => {
              HMSSplash.setAdParam({
                adContentClassification:
                  HMSAds.ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
                // appCountry: '',
                // appLang: '',
                // belongCountryCode: '',
                gender: HMSAds.Gender.UNKNOWN,
                nonPersonalizedAd: HMSAds.NonPersonalizedAd.ALLOW_ALL,
                // requestOrigin: '',
                tagForChildProtection:
                  HMSAds.TagForChild.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED,
                tagForUnderAgeOfPromise: HMSAds.UnderAge.PROMISE_UNSPECIFIED,
                // targetingContentUrl: '',
                //requestLocation: true,
              })
                .then((res) => toast("HMSSplash.setAdParam", res))
                .catch((err) => alert(err));
            }}
          />
        </View>
      </>
    );
  }
}

class AdvertisingId extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoaded: false,
      advertisingInfo: {
        id: "-",
        isLimitAdTrackingEnabled: false,
      },
      callMode: HMSAds.CallMode.SDK,
    };
  }

  render() {
    return (
      <>
        <View style={styles.sectionContainer}>
          <Picker
            prompt="Select Call Mode"
            selectedValue={this.state.callMode}
            onValueChange={(itemValue) => {
              this.setState({ callMode: itemValue });
            }}
          >
            {Object.values(HMSAds.CallMode).map((callMode) => (
              <Picker.Item label={callMode} value={callMode} key={callMode} />
            ))}
          </Picker>
          <Button
            title="Get Advertising Id Info"
            onPress={() =>
              HMSOaid.getAdvertisingIdInfo(this.state.callMode)
                .then((res) => {
                  toast("HMSOaid.getAdvertisingIdInfo, result:", res);
                  this.setState({ advertisingInfo: res });
                })
                .catch((e) => toast("HMSOaid.getAdvertisingIdInfo, error:", e))
            }
          />
          <Button
            title="Clear"
            color="red"
            onPress={() =>
              this.setState({
                advertisingInfo: {
                  id: "-",
                  isLimitAdTrackingEnabled: false,
                },
              })
            }
          />
          <Text title="Advertising Id">
            Advertising Id : {this.state.advertisingInfo.id}
          </Text>
          <Text title="Limit Ad Tracking Enabled">
            Limit Ad Tracking Enabled :
            {this.state.advertisingInfo.isLimitAdTrackingEnabled
              ? "True"
              : "False"}
          </Text>

          <Button
            color="green"
            title="Verify Advertising Id"
            onPress={() =>
              HMSOaid.verifyAdvertisingId(this.state.advertisingInfo)
                .then((res) =>
                  alert("HMSOaid.verifyAdvertisingId, result:", res)
                )
                .catch((err) => alert(err))
            }
          />
        </View>
      </>
    );
  }
}

class InstallReferrer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isTest: true,
      callMode: HMSAds.CallMode.SDK,
    };
  }

  componentDidMount() {
    HMSInstallReferrer.serviceConnectedListenerAdd((response) =>
      toast("HMSInstallReferrer serviceConnected, response:", response)
    ); // HMSInstallReferrer.serviceConnectedListenerRemove();
    HMSInstallReferrer.serviceDisconnectedListenerAdd(() =>
      toast("HMSInstallReferrer serviceDisconnected")
    ); // HMSInstallReferrer.serviceDisconnectedListenerRemove();
  }

  componentWillUnmount() {
    HMSInstallReferrer.allListenersRemove();
  }

  render() {
    return (
      <>
        <View>
          <View style={styles.sectionContainer}>
            <Picker
              prompt="Select Call Mode"
              selectedValue={this.state.callMode}
              onValueChange={(itemValue) => {
                this.setState({ callMode: itemValue });
              }}
            >
              {Object.values(HMSAds.CallMode).map((cMode) => (
                <Picker.Item label={cMode} value={cMode} key={cMode} />
              ))}
            </Picker>
            <Button
              title="Start Install Referer with given call mode"
              onPress={() =>
                HMSInstallReferrer.startConnection(
                  this.state.callMode,
                  this.state.isTest
                )
                  .then((res) =>
                    toast("HMSInstallReferrer.startConnection, result:", res)
                  )
                  .catch((err) =>
                    toast("HMSInstallReferrer.startConnection, error:", err)
                  )
              }
            />
            <Button
              color="green"
              title="Ready?"
              onPress={() =>
                HMSInstallReferrer.isReady()
                  .then((res) =>
                    toast("HMSInstallReferrer.isReady, result:", res)
                  )
                  .catch((e) => toast("HMSInstallReferrer.isReady, error:", e))
              }
            />
            <Button
              color="purple"
              title="Get Referrer Details"
              onPress={() =>
                HMSInstallReferrer.getReferrerDetails("test channel")
                  .then((res) =>
                    toast("HMSInstallReferrer.getReferrerDetails, result:", res)
                  )
                  .catch((err) =>
                    toast("HMSInstallReferrer.getReferrerDetails, error:", err)
                  )
              }
            />
            <Button
              color="red"
              title="End Install Referer connection"
              onPress={() =>
                HMSInstallReferrer.endConnection()
                  .then(() => toast("HMSInstallReferrer.endConnection"))
                  .catch((e) =>
                    toast("HMSInstallReferrer.endConnection, error:", e)
                  )
              }
            />
          </View>
        </View>
      </>
    );
  }
}

class Consent extends React.Component {
  render() {
    return (
      <>
        <View>
          <View style={styles.sectionContainer}>
            <Button
              title="Set Consent"
              onPress={() =>
                HMSAds.setConsent({
                  consentStatus: HMSAds.ConsentStatus.NON_PERSONALIZED,
                  debugNeedConsent: HMSAds.DebugNeedConsent.DEBUG_NEED_CONSENT,
                  underAgeOfPromise: HMSAds.UnderAge.PROMISE_UNSPECIFIED,
                  // testDeviceId: '********',
                })
                  .then((res) => toast("HMSAds.setConsent, result:", res))
                  .catch((e) => toast("HMSAds.setConsent, error:", e))
              }
            />
            <Button
              color="green"
              title="Check Consent"
              onPress={() =>
                HMSAds.checkConsent()
                  .then((res) => toast("HMSAds.checkConsent, result:", res))
                  .catch((e) => toast("HMSAds.checkConsent, error:", e))
              }
            />
          </View>
        </View>
      </>
    );
  }
}

class RequestOptions extends React.Component {
  render() {
    return (
      <>
        <View>
          <View style={styles.sectionContainer}>
            <Button
              title="Set Request"
              onPress={() =>
                HMSAds.setRequestOptions({
                  adContentClassification:
                    HMSAds.ContentClassification.AD_CONTENT_CLASSIFICATION_A,
                  // appCountry: AppCountry,
                  // appLang: AppLang,
                  nonPersonalizedAd: HMSAds.NonPersonalizedAd.ALLOW_ALL,
                  tagForChildProtection:
                    HMSAds.TagForChild.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED,
                  tagForUnderAgeOfPromise: HMSAds.UnderAge.PROMISE_UNSPECIFIED,
                  //requestLocation: true,
                })
                  .then((res) => toast("HMSAds.setRequestOptions, res:", res))
                  .catch((err) =>
                    toast("HMSAds.setRequestOptions, error:", err)
                  )
              }
            />
            <Button
              title="Get Request"
              color="green"
              onPress={() =>
                HMSAds.getRequestOptions()
                  .then((res) =>
                    toast("HMSAds.getRequestOptions, result:", res)
                  )
                  .catch((err) =>
                    toast("HMSAds.getRequestOptions, error:", err)
                  )
              }
            />
          </View>
        </View>
      </>
    );
  }
}


const pages = [
  { name: "Splash Ad", id: "splash", component: <Splash key="splash" /> },
  { name: "Reward Ad", id: "reward", component: <Reward key="reward" /> },
  {
    name: "Interstitial Ad",
    id: "interstitial",
    component: <Interstitial key="interstitial" />,
  },
  { name: "Native Ad", id: "native", component: <Native key="native" /> },
  { name: "Banner", id: "banner", component: <Banner key="banner" /> },
  { name: "Instream", id: "instream", component: <Instream key="instream" /> },
  {
    name: "Advertising Id",
    id: "advertisingInfo",
    component: <AdvertisingId key="advertisingInfo" />,
  },

  {
    name: "Install Referrer",
    id: "installReferrer",
    component: <InstallReferrer key="installReferrer" />,
  },
  { name: "VAST", id: "vast", component: <Vast key="vast" /> },
  { name: "Consent", id: "consent", component: <Consent key="consent" /> },
  {
    name: "Request Options",
    id: "requestOptions",
    component: <RequestOptions key="requestOptions" />,
  },
];

const initAppState = {
  privacyEnabled: true,
  consentEnabled: true,
  consentIgnored: false,
  showNotifyButton: false,
  pageId: pages[0].id,
};

const ModalText = () => (
  <>
    <Text style={styles.sectionDescription}>Privacy Example of HUAWEI X</Text>
    <Text style={styles.sectionContainer}>
      1.Privacy description {"\n"}
      {"\n"} The RNHmsAdsDemois software providing a code demo for the HUAWEI
      Ads SDK. Connecting to the network, the software collects and processes
      information to identify devices, providing customized services or ads. If
      you do not agree to collect the preceding information or do not agree to
      call related permissions or functions of your mobile phones, the software
      cannot run properly. You can stop data collection and uploading by
      uninstalling or exiting this software. {"\n"}
      {"\n"} 2.Demo description
      {"\n"}
      {"\n"} This demo is for reference only. Modify the content based on the
      user protocol specifications. {"\n"}
      {"\n"} 3.Advertising and marketing {"\n"}
      {"\n"} We will create a user group based on your personal information,
      collect your device information, usage information, and ad interaction
      information in this app, and display more relevant personalized ads and
      other promotion content. In this process, we will strictly protect your
      privacy. You can learn more about how we collect and use your personal
      information in personalized ads based on Ads and Privacy. If you want to
      restrict personalized ads, you can tap here to open the ad setting page
      and enable the function of restricting personalized ads. After the
      function is enabled, you will still receive equivalent number of ads.
      However, the ad relevance will be reduced.
    </Text>
  </>
);

const ModalText2 = () => (
  <>
    <Text style={styles.sectionDescription}>Content Example of HUAWEI X</Text>
    <Text style={styles.sectionContainer}>
      The Ads in HUAWEI X is provided in collaboration with our partners. You
      can find a full list of our partners for each country/region here.{"\n"}
      {"\n"} In order to provide you with personalized advertisements, we need
      to share the following information with our partners:{"\n"}
      {"\n"} • User information, including advertising ID, city of residence,
      country, and language.{"\n"}
      {"\n"} • Device information, including device name and model, operating
      system version, screen size, and network type.{"\n"}
      {"\n"} • Service usage information, including news ID and records of
      views, clicks, dislikes, shares, and comments for news content and
      advertisements.{"\n"}
      {"\n"} With your consent, the above information will be shared with our
      partners so that they can provide you with personalized advertisements on
      behalf of their customers, based on interests and preferences identified
      or predicted through analysis of your personal information.{"\n"}
      {"\n"} You can withdraw your consent at any time by going to app settings.
      {"\n"}
      {"\n"} Without your consent, no data will be shared with our partners
    </Text>
  </>
);
class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = initAppState;
  }

  componentDidMount() {
    console.log("componentDidMount initAppState", initAppState.pageId);
  }
  render() {
    const usingHermes =
      typeof global.HermesInternal === "object" &&
      global.HermesInternal !== null;

    const {
      privacyEnabled,
      consentEnabled,
      consentIgnored,
      pageId
    } = this.state;

    return (
      <>
        <Modal
          animationType="slide"
          transparent={false}
          visible={!privacyEnabled}
          onRequestClose={() => {
            BackHandler.exitApp();
          }}
        >
          <ModalText />
          <Button title="Close" onPress={() => BackHandler.exitApp()} />
          <Button
            title="Ok"
            onPress={() => {
              this.setState({ privacyEnabled: true });
            }}
          />
        </Modal>
        <Modal
          animationType="slide"
          transparent={false}
          visible={privacyEnabled && !consentIgnored && !consentEnabled}
          onRequestClose={() => {
            this.setState({ consentIgnored: true });
          }}
        >
          <ModalText2 />
          <Button
            title="Ignore"
            onPress={() => {
              this.setState({ consentIgnored: true });
            }}
          />
          <Button
            title="Ok"
            onPress={() => {
              this.setState({ consentEnabled: true });
            }}
          />
        </Modal>
        <StatusBar barStyle="dark-content" />
        <SafeAreaView>
          <ScrollView
            contentInsetAdjustmentBehavior="automatic"
            style={styles.scrollView}
          >
            {!usingHermes ? null : (
              <View style={styles.engine}>
                <Text style={styles.footer}>Engine: Hermes</Text>
              </View>
            )}
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionHeader} title="General">
                Huawei Ads Kit
              </Text>
              <Button
                title="Ask Permissions"
                color="red"
                onPress={() => {
                  if (pageId == 'vast') {
                    HMSVast.userAcceptAdLicense(true)
                      .then((res) => toast("userAcceptAdLicense, result:", res))
                      .catch((err) => alert(err));
                  }
                  else {
                    this.setState({
                      privacyEnabled: false,
                      consentEnabled: false,
                    });
                  }
                }}
              />
              {pageId == 'vast' ? (<Button
                title="Init Vast"
                color="green"
                onPress={() =>
                  HMSVast.init(null)
                    .then((res) => {
                      toast("HMS init, result: " + res, res);
                      this.setState({ showNotifyButton: true });
                    })
                    .catch((err) => alert(err))
                }
              />) : (
                <Button
                  title="Init"
                  color="green"
                  onPress={() =>
                    HMSAds.init()
                      .then((res) => {
                        toast("HMS init, result: " + res, res);
                        this.setState({ showNotifyButton: true });
                      })
                      .catch((err) => alert(err))
                  }
                />
              )}
              {this.state.showNotifyButton && (
                <Button
                  title="App Installed Notify"
                  color="blue"
                  onPress={() => {
                    HMSAds.appInstalledNotify(true, HMSAds.ActivateStyle.BOTTOM_BANNER)
                      .then((res) => toast("App Installed Notify, result: " + JSON.stringify(res), res))
                      .catch((err) => alert(err))
                  }}
                />
              )}
            </View>
            {!privacyEnabled ? null : (
              <View style={styles.sectionContainer}>
                <Text style={styles.sectionHeader} title="Functions">
                  Functions:
                </Text>
                <Picker
                  prompt="Select Function"
                  selectedValue={pageId}
                  onValueChange={(itemValue) =>
                    this.setState({ pageId: itemValue })
                  }
                >
                  {pages.map((page) => (
                    <Picker.Item
                      label={page.name}
                      value={page.id}
                      key={page.id}
                    />
                  ))}
                </Picker>
                {pages
                  .filter((page) => page.id === pageId)
                  .map((page) => page.component)}
              </View>
            )}
          </ScrollView>
        </SafeAreaView>
      </>
    );
  }
}

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
    paddingBottom: 20,
  },
  engine: {
    position: "absolute",
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: "600",
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: "400",
    color: Colors.dark,
  },
  highlight: {
    fontWeight: "700",
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: "600",
    padding: 4,
    paddingRight: 12,
    textAlign: "right",
  },
  buttons: {
    flex: 1,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "stretch",
  },
  picker: { height: 50, width: 110 },
  sectionHeader: {
    fontSize: 20,
    fontWeight: "600",
    color: Colors.black,
    paddingVertical: 12,
    paddingHorizontal: 0,
  },
  yellowText: { backgroundColor: "yellow" },
});

export default App;
