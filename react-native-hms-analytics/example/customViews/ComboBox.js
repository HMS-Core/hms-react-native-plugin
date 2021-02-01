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

import React from "react";
import { View,Text,TouchableOpacity,StyleSheet} from "react-native";

export default class App extends React.Component {

  constructor(props){
    super(props)
    this.state={isCustomEvent:false}
  }

  componentDidMount(){
     this.setState({isCustomEvent:this.props.isCustomEvent})
  }

  componentDidUpdate(prevProps){
      if(this.props.select!=prevProps.select){
        this.setState({isCustomEvent:this.props.isCustomEvent})
      }
  }

  changeSelection(isCustom){
    this.props.changeSelection(isCustom)
    this.setState({isCustomEvent:isCustom})
  }


  render() {
    return (
        <View style={styles.comboBox}>
            <TouchableOpacity
                onPress={()=>this.changeSelection(true)}
                style={[styles.comboBtn,{borderRightWidth:1,borderColor:'white',backgroundColor:this.state.isCustomEvent ?'#0b1528':'#2e343b'}]}>
                <Text style={styles.text}> Create Custom Event </Text>

            </TouchableOpacity>

            <TouchableOpacity
                onPress={()=>this.changeSelection(false)}
                style={[styles.comboBtn,{backgroundColor:!this.state.isCustomEvent ?'#0b1528':'#2e343b'}]}>
                <Text style={styles.text}> Use A Preset Event </Text>

            </TouchableOpacity>

      </View>
    )
  }

}

const styles = StyleSheet.create({

  comboBox:{
    backgroundColor:"#0b1528",
    width:'90%',
    alignSelf:'center',
    marginTop:20,
    borderColor:'white',
    borderRadius:10,
    borderWidth:1,
    height:45,
    flexDirection:'row',
    overflow:'hidden'
  },
  comboBtn:{
    flex:1,
    justifyContent:'center'
  },
  text:{
    fontSize:14,
    color:'white',
    marginTop:5,
    marginBottom:5,
    marginLeft:15,
    alignSelf:'center'
  },
});
