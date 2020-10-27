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
import { StyleSheet, View } from 'react-native';
import GLOBALS from '../utils/Globals';
import ProductTypes from '../foundation/ProductTypes';
import TitleView from './TitleView';
import PurchasedProductListView from './PurchasedProductListView';
import AvailableProductsListView from './AvailableProductsListView';
import PurchaseRecordListView from './PurchaseRecordListView';

export default class PartialView extends React.Component {
  constructor() {
    super()
    this.state = {
      refresh: false,
      purchasedProducts:0,
      availableProducts:0,
      purchaseRecordProducts:0
    }
  }

  back() {
    var currentRefresh = this.state.refresh
    this.setState({ refresh: !currentRefresh })
  }

  sizeChange(size, type) {
    switch(type){
      case 0:{
        this.setState({purchasedProducts:size})
        break;
      }
      case 1:{
        this.setState({availableProducts:size})
        break;
      }
      case 2:{
        this.setState({purchaseRecordProducts:size})
        break;
      }
    }
  }


  getTitle(productType) {
    switch (productType) {
      case ProductTypes.CONSUMABLE:
        return (
          <TitleView
            title={GLOBALS.CONSUMABLE.TITLE}
            subTitle={GLOBALS.CONSUMABLE.SUBTITLE}
          />
        );
      case ProductTypes.NON_CONSUMABLE:
        return (
          <TitleView
            title={GLOBALS.NON_CONSUMABLE.TITLE}
            subTitle={GLOBALS.NON_CONSUMABLE.SUBTITLE}
          />
        );
      case ProductTypes.SUBSCRIPTION:
        return (
          <TitleView
            title={GLOBALS.SUBSCRIPTION.TITLE}
            subTitle={GLOBALS.SUBSCRIPTION.SUBTITLE}
          />
        );
    }
  }

  render() {
    const type = this.props.productType
    var totalSize=this.state.availableProducts+ this.state.purchaseRecordProducts+ this.state.purchasedProducts
    var height = totalSize*100+100
    return (
      <View style={styles.sectionContainer}>
        {this.getTitle(this.props.productType)}

          <View style={{height:height}} key={this.state.refresh}>
            <PurchasedProductListView sizeChange={(size) => this.sizeChange(size, 0)} onRefresh={(response) => this.back()} productType={type} />
            <AvailableProductsListView sizeChange={(size) => this.sizeChange(size, 1)} onRefresh={(response) => this.back()} productType={type} />
            <PurchaseRecordListView sizeChange={(size) => this.sizeChange(size, 2)} productType={type} />
          </View>

      </View>
    );
  }
}

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  }
});
