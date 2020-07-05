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

import React, { Component } from 'react';
import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  ToastAndroid,
  Image,
} from 'react-native';



import { styles } from './styles';

export default class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      receiveContent: '',
      topic: '',
      disableAutoInit: false,
      enableAutoInit: true,
    };
  }

  componentWillUnmount() { }

  toast(msg) {
    ToastAndroid.show(msg, ToastAndroid.SHORT);
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <View style={styles.containerCenter}>
          <Image style={styles.img} source={require('../assets/ml.png')} />
        </View>

        <Text style={styles.h1}>ML Text/Doc/Speech</Text>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('TextToSpeech')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/tts.png')}
                />
              </View>
              <Text style={styles.buttonText}>TTS</Text>
            </TouchableOpacity>
          </View>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('BankCardRecognition')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/bcr.png')}
                />
              </View>
              <Text style={styles.buttonText}>BCR</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('TextRecognition')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/text.png')}
                />
              </View>
              <Text style={styles.buttonText}>Text</Text>
            </TouchableOpacity>
          </View>
        </View>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('DocumentRecognition')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/doc.png')}
                />
              </View>
              <Text style={styles.buttonText}>Document</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('IdCardRecognition')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/icr.png')}
                />
              </View>
              <Text style={styles.buttonText}>ICR</Text>
            </TouchableOpacity>
          </View>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('Translate')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/trans.png')}
                />
              </View>
              <Text style={styles.buttonText}>Translate</Text>
            </TouchableOpacity>
          </View>



        </View>
        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('LanguageDetection')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/langdetect.png')}
                />
              </View>
              <Text style={styles.buttonText}>Language Detection</Text>
            </TouchableOpacity>
          </View>

        </View>

        <Text style={styles.h1}>ML Vision</Text>

        <View style={styles.containerFlex}>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('Segmentation')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/segmentation.png')}
                />
              </View>
              <Text style={styles.buttonText}>ImgSeg</Text>
            </TouchableOpacity>
          </View>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('FaceRecognition')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/face.png')}
                />
              </View>
              <Text style={styles.buttonText}>Face</Text>
            </TouchableOpacity>
          </View>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ObjectRecognition')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/object.png')}
                />
              </View>
              <Text style={styles.buttonText}>Object</Text>
            </TouchableOpacity>
          </View>
        </View>
        <View style={styles.containerFlex}>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('Classification')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/class.png')}
                />
              </View>
              <Text style={styles.buttonText}>Classify</Text>
            </TouchableOpacity>
          </View>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('Landmark')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/landmark.png')}
                />
              </View>
              <Text style={styles.buttonText}>Landmark</Text>
            </TouchableOpacity>
          </View>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ProductVision')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/productsegment.png')}
                />
              </View>
              <Text style={styles.buttonText}>Product Vision</Text>
            </TouchableOpacity>
          </View>
        </View>
        <Text></Text>
        <Text></Text>
      </ScrollView>
    );
  }
}
