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

import HmsIapModule from '@hmscore/react-native-hms-iap';

export default {
  DEVELOPER: {
    PAYLOAD: 'testPurchase',
    CHALLENGE: 'developerChallenge',
  },
  CONSUMABLE: {
    TITLE: 'Consumables',
    SUBTITLE:
      'Consumables are used once, are depleted, and can be purchased again. For example, extra lives and gems in a game.',
    PRODUCT_INFO_DATA: {
      priceType: HmsIapModule.PRICE_TYPE_IN_APP_CONSUMABLE,
      skuIds: ['5.0.2.300.1', '5.0.2.300.1.2'],
    },
    OWNED_PURCHASES_DATA: {
      priceType: HmsIapModule.PRICE_TYPE_IN_APP_CONSUMABLE,
    },
  },
  NON_CONSUMABLE: {
    TITLE: 'Non-consumables',
    SUBTITLE:
      'Non-consumables are purchased once and do not expire. For example, extra game levels in a game or permanent membership of an app.',
    PRODUCT_INFO_DATA: {
      priceType: HmsIapModule.PRICE_TYPE_IN_APP_NONCONSUMABLE,
      skuIds: ['5.0.2.300.2', '5.0.2.300.2.2'],
    },
    OWNED_PURCHASES_DATA: {
      priceType: HmsIapModule.PRICE_TYPE_IN_APP_NONCONSUMABLE,
    },
  },
  SUBSCRIPTION: {
    TITLE: 'Subscriptions',
    SUBTITLE:
      'Users can purchase access to value-added functions or content in a specified period of time. The subscriptions are automatically renewed on a recurring basis until users decide to cancel. For example, non-permanent membership of an app, such as a monthly video membership.',
    PRODUCT_INFO_DATA: {
      priceType: HmsIapModule.PRICE_TYPE_IN_APP_SUBSCRIPTION,
      skuIds: ['5.0.2.300.3', '5.0.2.300.3.2'],
    },
    OWNED_PURCHASES_DATA: {
      priceType: HmsIapModule.PRICE_TYPE_IN_APP_SUBSCRIPTION,
    },
  },
  COLORS: {
    PRIMARY_COLOR: '#ADD8E6',
    WHITE: '#FFFFFF',
  },
};
