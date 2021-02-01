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

import React, { Component } from "react";
import {
  View,
  Button,
  Switch,
  ScrollView,
  Text,
  TextInput,
} from "react-native";
import RNHMSSite from "@hmscore/react-native-hms-site";

import { styles } from "./src/styles";

const defaultLocation = {
  location: {
    lat: 48.8815,
    lng: 2.4444,
  },
  bounds: {
    northeast: {
      lat: 49,
      lng: 2.47,
    },
    southwest: {
      lat: 47.8815,
      lng: 2.0,
    },
  },
};

class App extends Component {
  constructor() {
    super();

    this.state = {
      strictBounds: false,
      query: "France",
      radius: 1000,
    };
  }

  componentDidMount() {
    const config = {
      apiKey: "<api_key>",
    };

    RNHMSSite.initializeService(config)
      .then(() => {
        console.log("Service is initialized successfully");
      })
      .catch((err) => {
        console.log("Error : " + err);
      });
  }

  render() {
    return (
      <ScrollView>
        <View style={[styles.container]}>
          <TextInput
            value={this.state.query}
            style={[styles.input, styles.width35]}
            placeholder="query"
            onChangeText={(e) => this.changeInputValue("query", e)}
          />
          <TextInput
            value={this.state.radius ? this.state.radius.toString() : null}
            keyboardType="number-pad"
            maxLength={5}
            style={[styles.input, styles.width35]}
            placeholder="radius"
            onChangeText={(e) => this.changeRadiusValue(e)}
          />
          <View style={[styles.switchContainer]}>
            <Text>Strict Bounds</Text>
            <Switch
              trackColor={{ false: "#767577", true: "#81b0ff" }}
              thumbColor={this.state.strictBounds ? "#f5dd4b" : "#f4f3f4"}
              onValueChange={this.toggleSwitch}
              value={this.state.strictBounds}
            />
          </View>
        </View>
        <View>
          <Button title="Text Search" onPress={this.onTextSearch} />
        </View>

        <View style={styles.btnContainer}>
          <Button title="Detail Search" onPress={this.onDetailSearch} />
        </View>

        <View style={styles.btnContainer}>
          <Button title="Query Suggestion" onPress={this.onQuerySuggestion} />
        </View>

        <View style={styles.btnContainer}>
          <Button
            title="Query AutoComplete"
            onPress={this.onQueryAutocomplete}
          />
        </View>

        <View style={styles.btnContainer}>
          <Button title="Nearby Search" onPress={this.onNearbySearch} />
        </View>

        <View style={styles.btnContainer}>
          <Button title="Create Widget" onPress={this.createWidget} />
        </View>

        <View style={styles.btnContainer}>
          <Button title="Enable the logger" onPress={this.enableLogger} />
        </View>

        <View style={styles.btnContainer}>
          <Button title="Disable the logger" onPress={this.disableLogger} />
        </View>
      </ScrollView>
    );
  }

  changeInputValue(key, data) {
    data.length < 1 ? (data = "") : data;
    this.setState({
      [key]: data,
    });
  }

  changeRadiusValue(data) {
    data = data.replace(/[^0-9]/g, "");
    data == "" ? data : (data = Number(data));

    this.setState({
      radius: data,
    });
  }

  toggleSwitch = () => {
    this.setState({ strictBounds: !this.state.strictBounds });
    alert("StrictBounds: " + !this.state.strictBounds);
  };

  onTextSearch = () => {
    let textSearchReq = {
      query: this.state.query,
      location: {
        lat: 48.8815,
        lng: 2.4444,
      },
      radius: this.state.radius,
      countryCode: "FR",
      language: "fr",
      pageIndex: 1,
      pageSize: 5,
      hwPoiType: RNHMSSite.HwLocationType.RESTAURANT,
      poiType: RNHMSSite.LocationType.GYM,
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

  onDetailSearch = () => {
    let detailSearchReq = {
      siteId: "2116626084C8358C26700F373E49B9EF",
      language: "",
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

  onQuerySuggestion = () => {
    let querySuggestionReq = {
      ...defaultLocation,
      query: this.state.query,
      radius: this.state.radius,
      countryCode: "FR",
      language: "fr",
      poiTypes: [
        RNHMSSite.LocationType.GEOCODE,
        RNHMSSite.LocationType.ADDRESS,
        RNHMSSite.LocationType.ESTABLISHMENT,
        RNHMSSite.LocationType.REGIONS,
        RNHMSSite.LocationType.CITIES,
      ],
      strictBounds: this.state.strictBounds,
      children: false,
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

  onQueryAutocomplete = () => {
    let queryAutocompleteReq = {
      query: this.state.query,
      location: {
        lat: 48.8815,
        lng: 2.4444,
      },
      radius: this.state.radius,
      language: "fr",
      children: false,
    };
    RNHMSSite.queryAutocomplete(queryAutocompleteReq)
      .then((res) => {
        alert(JSON.stringify(res));
        console.log(JSON.stringify(res));
      })
      .catch((err) => {
        alert(JSON.stringify(err));
        console.log(JSON.stringify(err));
      });
  };

  onNearbySearch = () => {
    let nearbySearchReq = {
      query: this.state.query,
      location: {
        lat: 48.8815,
        lng: 2.4444,
      },
      radius: this.state.radius,
      hwPoiType: RNHMSSite.HwLocationType.RESTAURANT,
      poiType: RNHMSSite.LocationType.GYM,
      language: "fr",
      pageIndex: 1,
      pageSize: 5,
      strictBounds: this.state.strictBounds,
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

  createWidget = () => {
    let params = {
      searchIntent: {
        apiKey: "<api_key>",
        hint: "myhint",
      },

      searchFilter: {
        ...defaultLocation,
        query: this.state.query,
        radius: this.state.radius,
        language: "fr",
        countryCode: "FR",
        poiTypes: [
          RNHMSSite.LocationType.GEOCODE,
          RNHMSSite.LocationType.ADDRESS,
          RNHMSSite.LocationType.ESTABLISHMENT,
          RNHMSSite.LocationType.REGIONS,
          RNHMSSite.LocationType.CITIES,
        ],
        strictBounds: this.state.strictBounds,
        children: false,
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

  enableLogger = () => {
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

  disableLogger = () => {
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
}

export default App;
