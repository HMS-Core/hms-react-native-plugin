import React from "react";
import {
    NativeEventEmitter, 
    Text, 
    TouchableOpacity, 
    View,
    Alert 
} from "react-native";
import { HMSBeacon } from "@hmscore/react-native-hms-nearby";
import { styles } from "./Styles";

export class Beacon extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            register: false,
        }
    }
    getBeaconOption = () => {
        return {
            beaconId: '6bff00f723fdf7471402',
            beaconType: HMSBeacon.BEACON_TYPE_IBEACON,
            type: "type",
            namespace: "namespace"
        }
    }

    componentDidMount() {
        this.eventEmitter = new NativeEventEmitter(HMSBeacon);

        this.eventEmitter.addListener(HMSBeacon.BEACON_RECEIVER, (event) => console.log(event));
    }

    async registerScan() {
        try{
            this.setState({register: true});
            let result = await HMSBeacon.registerScan(this.getBeaconOption());
            console.log('RegisterScan: ',result);
        } catch(e) {
            console.log(e);
        }
    }

    async unRegisterScan() {
        try{
            this.setState({register: false});
            let result = await HMSBeacon.unRegisterScan();
            this.eventEmitter.removeAllListeners("beaconReceiver");
            console.log('unRegisterScan:', result);
            Alert.alert('Success',JSON.stringify(result))
        } catch(e) {
            console.log(e);
            Alert.alert('Error',JSON.stringify(e.message))
        }
    }

    async getBeaconMsgConditions() {
        try{
            let result = await HMSBeacon.getBeaconMsgConditions();
            console.log('getBeaconMsgConditions: ', result);
            Alert.alert('Success',JSON.stringify(result))
        } catch(e) {
            console.log(e);
            Alert.alert('Error',JSON.stringify(e.message))
        }
    }

    async getRawBeaconConditions() {
        try {
            let result = await HMSBeacon.getRawBeaconConditions();
            console.log('getRawBeaconConditions: ', result)
            Alert.alert('Success',JSON.stringify(result))
        } catch (e) {
            console.log(e);
            Alert.alert('Error',JSON.stringify(e.message))
        }
    }

    async getRawBeaconConditionsWithBeaconType() {
        try {
            let result = await HMSBeacon.getRawBeaconConditionsWithBeaconType(1);
            console.log('getRawBeaconConditionsWithBeaconType: ', result)
            Alert.alert('Success',JSON.stringify(result))
        } catch (e) {
            console.log(e);
            Alert.alert('Error',JSON.stringify(e.message))
        }
    }

  render() {
    return (
      <View>
        <View>
            <Text style={styles.beaconHeader}>REGISTER</Text>
            <TouchableOpacity style={styles.beaconButton} onPress={() => this.registerScan()}>
                <Text style={styles.beaconText}>Register Scan : {this.state.register ? "ON" : "OFF"}</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.beaconButton} onPress={() => this.unRegisterScan()}>
                <Text style={styles.beaconText}>unRegister Scan</Text>
            </TouchableOpacity>
            <Text style={styles.beaconHeader}>CONDITIONS</Text>
            <TouchableOpacity style={styles.beaconButton} onPress={() => this.getBeaconMsgConditions()}>
                <Text style={styles.beaconText}>getBeaconMsgConditions</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.beaconButton} onPress={() => this.getRawBeaconConditions()}>
                <Text style={styles.beaconText}>getRawBeaconConditions</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.beaconButton} onPress={() => this.getRawBeaconConditionsWithBeaconType()}>
                <Text style={styles.beaconText}>getRawBeaconConditionsWithBeaconType</Text>
            </TouchableOpacity>
        </View>
      </View>
    );
  }
}

export default Beacon;
