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
import {Text, View, Image} from 'react-native';

import {styles} from './styles';

export default class SecondPage extends React.Component {
  render() {
    return (
      <View style={styles.imageView}>
        <Image
          source={{
            uri:
              'https://developer.huawei.com/dev_index/img/bbs_en_logo.png?v=123',
          }}
          style={styles.image}
        />
        <Text style={styles.paddingX}>
          Example Data:{' '}
          {this.props.navigation.getParam('item', {key: 'value'}).key}
        </Text>
      </View>
    );
  }
}
