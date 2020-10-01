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
import { View, Button, StyleSheet } from 'react-native';
import RNHMSSite from '@hmscore/react-native-hms-site';

const styles = StyleSheet.create({
  btnContainer: {
    marginTop: 20,
  },
});

const App = () => {
  return (
    <View>
      <View>
        <Button title="Text Search" onPress={onTextSearch} />
      </View>

      <View style={styles.btnContainer}>
        <Button title="Detail Search" onPress={onDetailSearch} />
      </View>

      <View style={styles.btnContainer}>
        <Button title="Query Suggestion" onPress={onQuerySuggestion} />
      </View>

      <View style={styles.btnContainer}>
        <Button title="Nearby Search" onPress={onNearbySearch} />
      </View>

      <View style={styles.btnContainer}>
        <Button title="Create Widget" onPress={createWidget} />
      </View>

      <View style={styles.btnContainer}>
        <Button title="Enable the logger" onPress={enableLogger} />
      </View>

      <View style={styles.btnContainer}>
        <Button title="Disable the logger" onPress={disableLogger} />
      </View>
    </View>
  );
};

const config = {
  apiKey: '<api_key>',
};

RNHMSSite.initializeService(config)
  .then(() => {
    console.log('Service is initialized successfully');
  })
  .catch((err) => {
    console.log('Error : ' + err);
  });

const onTextSearch = () => {
  let textSearchReq = {
    query: "Paris",
    location: {
      lat: 48.893478,
      lng: 2.334595,
    },
    radius: 1000,
    countryCode: 'FR',
    language: 'fr',
    pageIndex: 1,
    pageSize: 5,
    hwPoiType: RNHMSSite.HwLocationType.RESTAURANT,
    poiType: RNHMSSite.LocationType.GYM,
    politicalView: "FR"
  };
  RNHMSSite.textSearch(textSearchReq)
    .then((res) => {
      alert(JSON.stringify(res));
      console.log(JSON.stringify(res));
    })
    .catch((err) => {
      alert(JSON.stringify(err));
      console.log(JSON.stringify(err));
    });
};

const onDetailSearch = () => {
  let detailSearchReq = {
    siteId: '16DA89C6962A36CB1752A343ED48B78A',
    language: 'fr',
    politicalView: 'fr'
  };
  RNHMSSite.detailSearch(detailSearchReq)
    .then((res) => {
      alert(JSON.stringify(res));
      console.log(JSON.stringify(res));
    })
    .catch((err) => {
      alert(JSON.stringify(err));
      console.log(JSON.stringify(err));
    });
};

const onQuerySuggestion = () => {
  let querySuggestionReq = {
    query: 'Paris',
    location: {
      lat: 48.893478,
      lng: 2.334595,
    },
    radius: 1000,
    countryCode: 'FR',
    language: 'fr',
    poiTypes: [RNHMSSite.LocationType.ADDRESS, RNHMSSite.LocationType.GEOCODE],
  };
  RNHMSSite.querySuggestion(querySuggestionReq)
    .then((res) => {
      alert(JSON.stringify(res));
    })
    .catch((err) => {
      alert(JSON.stringify(err));
      console.log(JSON.stringify(err));
    });
};

const onNearbySearch = () => {
  let nearbySearchReq = {
    query: 'Paris',
    location:
    {
      lat: 48.893478,
      lng: 2.334595,
    },
    radius: 1000,
    poiType: RNHMSSite.LocationType.ADDRESS,
    countryCode: 'FR',
    language: 'fr',
    pageIndex: 1,
    pageSize: 5,
    hwPoiType: RNHMSSite.HwLocationType.RESTAURANT,
    politicalView: "fr"
  };
  RNHMSSite.nearbySearch(nearbySearchReq)
    .then((res) => {
      alert(JSON.stringify(res));
      console.log(JSON.stringify(res));
    })
    .catch((err) => {
      alert(JSON.stringify(err));
      console.log(JSON.stringify(err));
    });
};

const createWidget = () => {
  let params = {
    searchIntent: {
      apiKey: '<api_key>',
      hint: 'myhint',
    },

    searchFilter: {
      query: 'Leeds',
      language: 'en',
    },
  };

  RNHMSSite.createWidget(params)
    .then((res) => {
      alert(JSON.stringify(res));
      console.log(JSON.stringify(res));
    })
    .catch((err) => {
      alert(JSON.stringify(err));
      console.log(JSON.stringify(err));
    });
};

const enableLogger = () => {
  RNHMSSite.enableLogger()
    .then((res) => {
      alert(JSON.stringify(res));
      console.log(res);
    })
    .catch((err) => {
      alert(JSON.stringify(err));
      console.log(JSON.stringify(err));
    });
};

const disableLogger = () => {
  RNHMSSite.disableLogger()
    .then((res) => {
      alert(JSON.stringify(res));
      console.log(res);
    })
    .catch((err) => {
      alert(JSON.stringify(err));
      console.log(JSON.stringify(err));
    });
};

export default App;
