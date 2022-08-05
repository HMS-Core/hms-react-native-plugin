import React from 'react';
import {
  Text,
  View,
  TouchableOpacity,
  ToastAndroid,
  Image,
  ScrollView,
  TextInput
} from 'react-native';
import { HMSFaceVerification, HMSApplication } from '@hmscore/react-native-hms-ml';
import { showImagePickerCustom } from '../HmsOtherServices/Helper';
import { styles } from '../Styles';

export default class FaceVerification extends React.Component {
  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      imageUriSecond: '',
      btnEnableSecond: false,
      btnCompareEnable: false,
      log: [],
      imageLayout: null,
      imageRectangle: [],
      imageTwoResult: null,
      imageLayoutTwo: null,
      imageRectangleTwo: [],
    };

    HMSFaceVerification.setMaxFaceDetected(3)
      .then(() => {
        HMSFaceVerification.getMaxFaceDetected().then((res) => console.log(res))
      });
  }

  getFrameConfiguration = (uri) => {
    return { filePath: uri };
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

  start = async (isAsync) => {
    let res = null;
    if (isAsync) {
      res = await HMSFaceVerification
        .asyncCompare(false, this.getFrameConfiguration(this.state.imageUriSecond))
        .catch(e => e);
    } else {
      res = await HMSFaceVerification
        .compare(false, this.getFrameConfiguration(this.state.imageUriSecond))
        .catch(e => e);
    }
    res = this.parseResult(res);
    if (res.success && res.faces?.length > 0) {
      this.calculate(
        res.faces,
        this.state.imageTwoResult.width,
        this.state.imageTwoResult.height,
        this.state.imageLayoutTwo,
        (obj) => this.setState({ imageRectangleTwo: obj })
      )
    } else {
      this.setState({imageRectangleTwo: []})
    }
    this.pushLog(`result ${JSON.stringify(res)}`);
  }

  pushLog = (str) => {
    this.state.log = [`${new Date().toISOString()} -> ${str}`].concat(this.state.log)
    this.setState({})
  }

  calculate = (faces, fullWidth, fullHeight, layout, setState) => {
    let array = [];
    faces.forEach(element => {
      let arry = element.face.substring(5, element.face.length - 1).split('-');
      let [beginPositionX, beginPositionY] = arry[0].split(',');
      let [endPositionX, endPositionY] = arry[1].split(',');

      let imageWidth = (fullWidth / fullHeight) * layout.height;
      let increaseX = (layout.width - imageWidth) / 2;

      let y = (imageWidth * beginPositionY / fullWidth) + layout.y;
      let x = (layout.height * beginPositionX / fullHeight) + increaseX;
      let height = layout.height * (endPositionY - beginPositionY) / fullHeight;
      let width = imageWidth * (endPositionX - beginPositionX) / fullWidth;

      array.push({ x, y, height, width })
    });

    setState(array);
  }

  selectImage = () => {
    showImagePickerCustom()
      .then(async (result) => {

        if (!result) return;
        this.state.imageRectangle = [];
        this.setState({ imageUri: result.uri, imageRectangle: [] }, async () => {
          setTimeout(async () => {
            let res = await HMSFaceVerification
              .loadTemplatePic(this.getFrameConfiguration(result.uri))
              .catch(e => e);
            res = await this.parseResult(res);
            console.log("res", res)
            if (res.success && res.faces) {
              this.state.btnEnableSecond = true;
              this.calculate(
                res.faces,
                result.width,
                result.height,
                this.state.imageLayout,
                (obj) => this.setState({ imageRectangle: obj })
              )
            }
            else {
              this.state.btnEnableSecond = false;
              this.state.btnCompareEnable = false;
            }
            this.pushLog(`image ${JSON.stringify(res)}`);
          }, 500);
        })
      })
  }

  selectImageTwo = () => {
    showImagePickerCustom()
      .then(async (result) => {
        if (!result) return;
        this.state.imageTwoResult = result;
        this.setState({ imageUriSecond: result?.uri, btnCompareEnable: true, imageRectangleTwo: [] })
      })
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <View style={styles.containerCenter}>
          <TouchableOpacity
            onPress={this.selectImage}
            style={[styles.startButton, styles.enable]}>
            <Text style={styles.startButtonLabel}>Select Image</Text>
          </TouchableOpacity>
          {this.state.imageUri !== '' &&
            <TouchableOpacity style={styles.faceVerificationImage} onPress={this.selectImage}>
              <Image
                style={styles.faceVerificationImage}
                resizeMode='contain'
                source={{ uri: this.state.imageUri }}
                onLayout={(e) => {
                  this.state.imageLayout = e.nativeEvent.layout
                }}
              />
              {this.state.imageRectangle.length > 0 && this.state.imageRectangle.map((item, index) => (
                <View
                  key={index}
                  style={{
                    borderWidth: 1,
                    borderColor: 'red',
                    height: item?.height,
                    width: item?.width,
                    position: 'absolute',
                    top: item?.y,
                    left: item?.x,
                  }}
                />
              ))}
            </TouchableOpacity>
          }
        </View>
        <View style={styles.containerCenter}>
          <TouchableOpacity
            onPress={this.selectImageTwo}
            style={[styles.startButton, this.state.btnEnableSecond && styles.enable]}
            disabled={!this.state.btnEnableSecond}
          >
            <Text style={styles.startButtonLabel}>Select Image Two</Text>
          </TouchableOpacity>
          {this.state.imageUriSecond !== '' &&
            <TouchableOpacity style={styles.faceVerificationImage} onPress={this.selectImageTwo}>
              <Image
                style={styles.faceVerificationImage}
                resizeMode='contain'
                source={{ uri: this.state.imageUriSecond }}
                onLayout={(e) => {
                  this.state.imageLayoutTwo = e.nativeEvent.layout
                }}
              />
              {this.state.imageRectangleTwo.length > 0 && this.state.imageRectangleTwo.map((item, index) => (
                <View
                  key={index}
                  style={{
                    borderWidth: 1,
                    borderColor: 'red',
                    height: item?.height,
                    width: item?.width,
                    position: 'absolute',
                    top: item?.y,
                    left: item?.x,
                  }}
                />
              ))}
            </TouchableOpacity>
          }
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={[styles.startButton, this.state.btnCompareEnable && styles.enable]}
            underlayColor="#fff"
            disabled={!this.state.btnCompareEnable}
            onPress={() => this.start(false)}
          >
            <Text style={styles.startButtonLabel}> COMPARE </Text>
          </TouchableOpacity>
        </View>
        <View style={styles.basicButton}>
          <TouchableOpacity
            style={[styles.startButton, this.state.btnCompareEnable && styles.enable]}
            underlayColor="#fff"
            disabled={!this.state.btnCompareEnable}
            onPress={() => this.start(true)}
          >
            <Text style={styles.startButtonLabel}> ASYNC COMPARE </Text>
          </TouchableOpacity>
        </View>
        <View style={styles.log}>
          {this.state.log.map((item, index) => (
            <Text key={index}>{item}</Text>
          ))}
        </View>
      </ScrollView>
    );
  }
}