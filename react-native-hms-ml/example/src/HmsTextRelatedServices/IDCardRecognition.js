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
import { HMSIDCardRecognition, HMSApplication } from '@hmscore/react-native-hms-ml';
import { showImagePickerCustom } from '../HmsOtherServices/Helper';
import { styles } from '../Styles';

export default class IDCardRecognition extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      data: {
        IDNum: "",
        Name: "",
        Sex: "",
        ValidDate: "",
        Address: "",
        Nation: "",
        Birtday: "",
        Authority: ""
      },
      imageUriFront: "",
      imageUriBack: "",
    };
  }

  componentDidMount() {
    this.eventEmitter = new NativeEventEmitter(HMSIDCardRecognition);

    this.eventEmitter.addListener(HMSIDCardRecognition.IDCARD_IMAGE_SAVE, (event) => {
      console.log(event);
      if (event.status == HMSApplication.SUCCESS) {
        const image = { uri: event.result.image }
        if (event.result.isFront) {
          this.setState({ imageUriFront: image });
        } else {
          this.setState({ imageUriBack: image });
        }
        ToastAndroid.showWithGravity('Images are saved to gallery', ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
      else {
        ToastAndroid.showWithGravity('Image Save Error :' + event.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    });
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSIDCardRecognition.IDCARD_IMAGE_SAVE);
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

  save = (obj) => {
    this.state.data.IDNum = obj?.idNum || this.state.data.IDNum;
    this.state.data.Name = obj?.name || this.state.data.Name;
    this.state.data.Sex = obj?.sex || this.state.data.Sex;
    this.state.data.ValidDate = obj?.validDate || this.state.data.ValidDate;
    this.state.data.Address = obj?.address || this.state.data.Address;
    this.state.data.Nation = obj?.nation || this.state.data.Nation;
    if (obj?.birtday?.length == 8) {
      obj.birtday = obj.birtday.substr(0, 4) + "/" + obj.birtday.substr(4, 2) + "/" + obj.birtday.substr(6, 2);
    }
    this.state.data.Birtday = obj?.birtday || this.state.data.Birtday; 
    this.state.data.Authority = obj?.authority || this.state.data.Authority; 

    this.setState({});
  }

  startImageAnalyzer = async (isFront, onDevice, async) => {
    showImagePickerCustom()
      .then(async (result) => {
        if (!result) return;
        let imgUpdate = {};
        if (isFront) {
          imgUpdate = { imageUriFront: { uri: result.uri }};
        } else {
          imgUpdate = { imageUriBack: { uri: result.uri }};
        }
        this.setState(imgUpdate, async () => { 
          let res = null;
          if (!onDevice) {
            res = await HMSIDCardRecognition.captureImage(result.uri, isFront)
              .catch(e => e);
          } else {
            if (async) {
              res = await HMSIDCardRecognition.asyncAnalyzerImageOnDevice(true, result.uri, isFront)
                .catch(e => e);
            } else {
              res = await HMSIDCardRecognition.analyzerImageOnDevice(true, result.uri, isFront)
                .catch(e => e);
            }
          }

          console.log(res);
          res = this.parseResult(res);
          if (res)
            this.save(res)
        })
      })
  }

  startCameraAnalyzer = async (isFront) => {
    let res = await HMSIDCardRecognition.captureCamera(true, isFront)
      .catch(e => e);
    console.log(res);
    res = this.parseResult(res);
    if (res)
      this.save(res)
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <View style={styles.cardRecogOptions}>
          <TouchableOpacity
            style={[styles.startButton, styles.enable]}
            underlayColor="#fff"
            onPress={() => this.startImageAnalyzer(true, true, false)}
          >
            <Text style={styles.startButtonLabel}> CAPTURE IMG.</Text>
          </TouchableOpacity>
          <Text style={{ fontWeight: 'bold' }}>Front Side</Text>
          <TouchableOpacity
            style={[styles.startButton, styles.enable]}
            underlayColor="#fff"
            onPress={() => this.startCameraAnalyzer(true)}
          >
            <Text style={styles.startButtonLabel}> CAPTURE CAM.</Text>
          </TouchableOpacity>
        </View>
        {this.state.imageUriFront != "" &&
          <Image
            style={{ height: 200 }}
            source={this.state.imageUriFront}
            resizeMode="contain"
          />
        }
        <View style={styles.cardRecogOptions}>
          <TouchableOpacity
            style={[styles.startButton, styles.enable]}
            underlayColor="#fff"
            onPress={() => this.startImageAnalyzer(false)}
          >
            <Text style={styles.startButtonLabel}> CAPTURE IMG.</Text>
          </TouchableOpacity>
          <Text style={{ fontWeight: 'bold' }}>Back Side</Text>
          <TouchableOpacity
            style={[styles.startButton, styles.enable]}
            underlayColor="#fff"
            onPress={() => this.startCameraAnalyzer(false)}
          >
            <Text style={styles.startButtonLabel}> CAPTURE CAM.</Text>
          </TouchableOpacity>
        </View>
        {this.state.imageUriBack != "" &&
          <Image
            style={{ height: 200, width: '100%' }}
            source={this.state.imageUriBack}
            resizeMode="contain"
          />
        }
        <View style={styles.basicButton}>
          <TouchableOpacity
            style={[styles.startButton, styles.enable]}
            underlayColor="#fff"
            onPress={() => this.setState({data: {}, imageUriFront: "", imageUriBack: ""})}
          >
            <Text style={styles.startButtonLabel}> CLEAR </Text>
          </TouchableOpacity>
        </View>
        <View style={styles.log}>
          {this.getText("IDNum")}
          {this.getText("Name")}
          {this.getText("Sex")}
          {this.getText("Address")}
          {this.getText("Nation")}
          {this.getText("Birtday")}
          {this.getText("Authority")}
          {this.getText("ValidDate")}
        </View>
      </ScrollView>
    );
  }

  getText = (field) => {
    if (!this.state.data[field]) return null;
    return (
      <View style={{ flexDirection: 'row' }}>
        <Text>{field} : </Text>
        <Text>{this.state.data[field]}</Text>
      </View>
    )
  }
}