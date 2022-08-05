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
  ScrollView
} from 'react-native';
import { styles } from '../Styles';

export default class CustomModel extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <Text style={styles.h1}>Prerequisites</Text>
        <Text style={styles.h1}>1- Prepare your local or remote(downloaded using HMSModelDownload) model.</Text>
        <Text style={styles.h1}>2- Configure method settings</Text>
        <Text style={styles.h1}>3- Call exec method to perform inference using input and output configurations and content</Text>
        <Text style={styles.h1}>4- Call close method to stop an inference task to release resources</Text>
        <Text style={styles.h1}>5- Call getOutputIndex to obtain the channel index based on the output channel name</Text>
        <Text style={styles.h1}>Note That : For details and model preparation please explore Huawei Developer Website </Text>

      </ScrollView >
    );
  }
}