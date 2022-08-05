import React from 'react';
import {
  Text,
  View,
  TouchableOpacity,
  ToastAndroid,
  Image,
  ScrollView,
  TextInput,
  NativeEventEmitter
} from 'react-native';
import { HMSVietnamCardRecognition, HMSApplication } from '@hmscore/react-native-hms-ml';
import { showImagePickerCustom } from '../HmsOtherServices/Helper';
import { styles } from '../Styles';

export default class VietnamIDCardRecognition extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      log: [],
    };
  }

  componentDidMount() {
    this.eventEmitter = new NativeEventEmitter(HMSVietnamCardRecognition);

    this.eventEmitter.addListener(HMSVietnamCardRecognition.ICRVN_IMAGE_SAVE, (event) => {
      console.log(event);
      if (event.status == HMSApplication.SUCCESS) {
        const image = { uri: event.result }
        this.setState({ imageUri: image });
        ToastAndroid.showWithGravity('Images are saved to gallery', ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
      else {
        ToastAndroid.showWithGravity('Image Save Error :' + event.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    });
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSVietnamCardRecognition.ICRVN_IMAGE_SAVE);
  }

  startImageAnalyzer = (onDevice, async) => {
    showImagePickerCustom()
      .then(async (result) => {
        if (!result) return;
        this.setState({ imageUri: { uri: result.uri }}, async () => { 
          let res = null;
          if (!onDevice) {
            res = await HMSVietnamCardRecognition.captureImage(result.uri)
              .catch(e => e);
          } else {
            if (async) {
              res = await HMSVietnamCardRecognition.asyncAnalyzerImageOnDevice(true, result.uri)
                .catch(e => e);
            } else {
              res = await HMSVietnamCardRecognition.analyzerImageOnDevice(true, result.uri)
                .catch(e => e);
            }
          }
          console.log(res);
          res = this.parseResult(res);
          if (res)
            this.pushLog(res)
        })
      })
  }

  parseResult = (result) => {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
      return result.result;
    }
    else {
      ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
    this.setState({ isAnalyzeEnabled: false });
  }

  pushLog = (str) => {
    if (typeof (str) == 'object')
      str = JSON.stringify(str)
    this.state.log = [`${new Date().toISOString()} ->\n ${str}`].concat(this.state.log)
    this.setState({})
  }

  startCameraAnalyzer = async () => {
    this.setState({ imageUri: "" })
    let res = await HMSVietnamCardRecognition.captureCamera(true)
      .catch(e => e);
    console.log(res);
    res = this.parseResult(res);
    if (res)
      this.pushLog(res)
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <View style={styles.cardRecogOptions}>
          <TouchableOpacity
            style={[styles.startButton, styles.enable]}
            underlayColor="#fff"
            onPress={() => this.startImageAnalyzer(false, true)}
          >
            <Text style={styles.startButtonLabel}> CAPTURE IMG.</Text>
          </TouchableOpacity>
          
          <TouchableOpacity
            style={[styles.startButton, styles.enable]}
            underlayColor="#fff"
            onPress={() => this.startCameraAnalyzer()}
          >
            <Text style={styles.startButtonLabel}> CAPTURE CAM.</Text>
          </TouchableOpacity>
        </View>
          {this.state.imageUri !== '' &&
            <TouchableOpacity style={styles.faceVerificationImage} onPress={this.selectImage}>
              <Image
                style={styles.faceVerificationImage}
                resizeMode='contain'
                source={this.state.imageUri}
              />
            </TouchableOpacity>
          }

        <View style={styles.log}>
          {this.state.log.map((item, index) => (
            <Text key={index}>{item}</Text>
          ))}
        </View>
      </ScrollView>
    );
  }
}