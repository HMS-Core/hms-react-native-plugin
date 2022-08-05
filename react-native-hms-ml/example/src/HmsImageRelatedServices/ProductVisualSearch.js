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

import React from 'react';
import {
  Text,
  View,
  ScrollView,
  TextInput,
  TouchableOpacity,
  Image,
  ToastAndroid,
  NativeEventEmitter
} from 'react-native';
import { styles } from '../Styles';
import { HMSProductVisionSearch, HMSApplication } from '@hmscore/react-native-hms-ml';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class ProductVisualSearch extends React.Component {

  componentDidMount() {
    this.eventEmitter = new NativeEventEmitter(HMSProductVisionSearch);

    this.eventEmitter.addListener(HMSProductVisionSearch.PRODUCT_ON_RESULT, (event) => {
      this.parseResult(event);
    });
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSProductVisionSearch.PRODUCT_ON_RESULT);
  }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      type: [],
      productIds: [],
      productUrls: [],
      customContents: [],
      imageUrls: [],
      possibilities: [],
      productSetId: 'mall',
      region: HMSProductVisionSearch.REGION_DR_CHINA
    };
  }

  getProductVisualSearchSetting = () => {
    return { maxResults: 10, productSetId: this.state.productSetId, region: this.state.region };
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  parseResult = (result) => {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
      result.result.forEach(element => {
        this.state.type.push(element.type);
        if (element.products.length > 0) {
          element.products.forEach(product => {
            this.state.productIds.push(product.productId);
            this.state.productUrls.push(product.productUrl);
            this.state.customContents.push(product.customContent);
            this.state.possibilities.push(product.possibility);
            this.state.imageUrls.push(product.images.length > 0 ? product.images[0].imageUrl : '');
          })
        }
      });
      this.setState({
        type: this.state.type,
        productIds: this.state.productIds,
        productUrls: this.state.productUrls,
        customContents: this.state.customContents,
        imageUrls: this.state.imageUrls,
        possibilities: this.state.possibilities,
      });
    }
    else {
      ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
  }

  async startProductVisionSearchCapturePlugin() {
    try {
      var result = HMSProductVisionSearch.startProductVisionSearchCapturePlugin(this.getProductVisualSearchSetting());
      if (result.status == HMSApplication.SUCCESS) {
        ToastAndroid.showWithGravity("Plugin started", ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  async asyncAnalyzeFrame() {
    try {
      ToastAndroid.showWithGravity("Recognition Started. Please wait for result", ToastAndroid.SHORT, ToastAndroid.CENTER);
      var result = await HMSProductVisionSearch.asyncAnalyzeFrame(true, this.getFrameConfiguration(), this.getProductVisualSearchSetting());
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  startAnalyze = (isPlugin) => {
    this.setState({
      type: [],
      productIds: [],
      productUrls: [],
      customContents: [],
      imageUrls: [],
      possibilities: [],
    })
    isPlugin ? this.startProductVisionSearchCapturePlugin() : this.asyncAnalyzeFrame();
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <Text style={styles.h1}>Set Required Information</Text>

        <TextInput
          style={styles.customInput}
          value={this.state.region.toString()}
          placeholder="Region : 1002, 1003, 1004, 1005"
          onChangeText={text => this.setState({ region: text })}
          multiline={false}
          editable={true}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.productSetId}
          placeholder="ProductSet Id"
          onChangeText={text => this.setState({ productSetId: text })}
          multiline={false}
          editable={true}
        />

        <View style={styles.containerCenter}>
          <TouchableOpacity onPress={() => { showImagePicker().then((result) => this.setState({ imageUri: result })) }}
            style={styles.startButton}>
            <Text style={styles.startButtonLabel}>Select Image</Text>
          </TouchableOpacity>
          {this.state.imageUri !== '' &&
            <Image
              style={styles.imageSelectView}
              source={{ uri: this.state.imageUri }}
            />
          }
        </View>
        <TextInput
          style={styles.customInput}
          value={this.state.type.toString()}
          placeholder="type"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.productIds.toString()}
          placeholder="productIds"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.productUrls.toString()}
          placeholder="productUrls"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.customContents.toString()}
          placeholder="customContents"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.imageUrls.toString()}
          placeholder="imageUrls"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.possibilities.toString()}
          placeholder="possibilities"
          multiline={true}
          editable={false}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(false)}
            disabled={this.state.imageUri == '' ? true : false}>
            <Text style={styles.startButtonLabel}> Start Async Analyze </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(true)}>
            <Text style={styles.startButtonLabel}> Start Plugin </Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    );
  }
}
