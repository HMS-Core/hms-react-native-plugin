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
import { Text, View, ScrollView, TextInput, TouchableOpacity, Switch } from 'react-native';

import { styles } from './styles';
import { HmsProductVision, HmsFrame } from 'react-native-hms-ml';

import ImagePicker from 'react-native-image-picker';


const options = {
  title: 'Choose Method',
  storageOptions: {
    skipBackup: true,
    path: 'images',
  },
};


export default class ProductVision extends React.Component {


  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      type: [],
      bottom: [],
      top: [],
      left: [],
      right: [],
      productId: [],
      possibility: [],
    };
  }

  async setMLFrame() {
    try {
      var result = await HmsFrame.fromBitmap(this.state.imageUri);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }


  async createProductSettings() {
    try {
      var result = await HmsProductVision.create({});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async analyze() {
    try {

      this.state.type.length = 0;
      this.state.bottom.length = 0;
      this.state.top.length = 0;
      this.state.left.length = 0;
      this.state.right.length = 0;
      this.state.productId.length = 0;
      this.state.possibility.length = 0;

      var result = await HmsProductVision.analyze();
      console.log(result);
      for (var i = 0; i < result.length; i++) {
        this.state.type.push(result[i]['type']);
        this.state.bottom.push(result[i]['bottom'].toString());
        this.state.top.push(result[i]['top'].toString());
        this.state.left.push(result[i]['left'].toString());
        this.state.right.push(result[i]['right'].toString());
        var products = result[i]['products'];
        console.log(products);
        for (let j = 0; j < products.length; j++) {
          this.state.productId.push(products[j]['productId']);
          this.state.possibility.push(products[j]['possibility'].toString());
        }

      }

      this.setState({
        type: this.state.type,
        bottom: this.state.bottom,
        top: this.state.top,
        left: this.state.left,
        right: this.state.right,
        productId: this.state.productId,
        possibility: this.state.possibility,
      });

    } catch (e) {
      console.error(e);
    }
  }

  async close() {
    try {
      var result = await HmsProductVision.stop();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }


  showImagePicker() {
    ImagePicker.showImagePicker(options, (response) => {
      
      if (response.didCancel) {
        console.log('User cancelled image picker');
      } else if (response.error) {
        console.log('ImagePicker Error: ', response.error);
      } else {
        this.setState({
          imageUri: response.uri ,
        });

        this.startAnalyze();
      }
    });
  }

  startAnalyze() {
    this.createProductSettings()
      .then(() => this.setMLFrame())
      .then(() => this.analyze())
      .then(() => this.close());
  }


  render() {
    return (
      <ScrollView style={styles.bg}>

        <TextInput
          style={styles.customInput}
          value={this.state.type.toString()}
          placeholder="Type"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.bottom.toString()}
          placeholder="Bottom Corner"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.left.toString()}
          placeholder="Left Corner"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.top.toString()}
          placeholder="Top Corner"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={`${this.state.right}`}
          placeholder="Right Corner"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.productId.toString()}
          placeholder="Product Ids"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.possibility.toString()}
          placeholder="Possibilities"
          multiline={true}
          editable={false}
        />


        <View style={styles.buttonTts}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.showImagePicker.bind(this)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Start Analyze </Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    );
  }
}
