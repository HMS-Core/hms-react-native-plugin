/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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
import { StyleSheet, Text, View } from 'react-native';

const TitleView = ({ title, subTitle }) => {
  return (
    <View>
      <Text style={styles.title}>{title}</Text>
      <Text>{subTitle}</Text>
      <View style={styles.separator} />
    </View>
  );
};

const styles = StyleSheet.create({
  title: {
    textAlign: 'left',
    fontSize: 30,
    marginVertical: 8,
  },
  subTitle: {
    textAlign: 'center',
    fontSize: 15,
    marginVertical: 8,
  },
  separator: {
    marginVertical: 8,
    borderBottomColor: '#737373',
    borderBottomWidth: StyleSheet.hairlineWidth,
  },
});

export default TitleView;
