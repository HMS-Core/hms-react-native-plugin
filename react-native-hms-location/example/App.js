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

import React, { useState, useCallback, useEffect } from 'react';
import {
    SafeAreaView,
    StyleSheet,
    ScrollView,
    View,
    Text,
    Image,
    Button,
    TextInput
} from 'react-native';

import { Colors } from 'react-native/Libraries/NewAppScreen';

import HMSLocation from '@hmscore/react-native-hms-location';

const Header = () => {
    // Initialize Location Kit
    useEffect(() => {
        HMSLocation.LocationKit.Native.init()
            .then(_ => console.log("Done loading"))
            .catch(ex => console.log("Error while initializing." + ex));
    }, []);

    return (
        <>
          <View style={styles.header}>
            <View style={styles.headerTitleWrapper}>
              <Text style={styles.headerTitle}>HMS Location Kit</Text>
            </View>
            <View style={styles.headerLogoWrapper}>
              <Image
                style={styles.headerLogo}
                source={require('./assets/images/hms-rn-logo.png')}
                />
            </View>
          </View>
        </>
    )};


const Permissions = () => {
    const [hasLocationPermission, setHasLocationPermission] = useState(false);
    const [hasActivityIdentificationPermission, setHasActivityIdentificationPermission] = useState(false);

    useEffect(() => {
        // Check location permissions
        HMSLocation.FusedLocation.Native.hasPermission()
            .then(result => setHasLocationPermission(result.hasPermission))
            .catch(ex => console.log("Error while getting location permission info: " + ex));

        // Check ActivityIdentification permissions
        HMSLocation.ActivityIdentification.Native.hasPermission()
            .then(result => setHasActivityIdentificationPermission(result.hasPermission))
            .catch(ex => console.log("Error while getting activity identification permission info: " + ex));
    }, []);

    const requestLocationPermisson = useCallback(() => {
        HMSLocation.FusedLocation.Native.requestPermission()
        .then(result => setHasLocationPermission(result.granted));
    }, []);

    const requestActivityIdentificationPermisson = useCallback(() => {
        HMSLocation.ActivityIdentification.Native.requestPermission()
        .then(result => setHasActivityIdentificationPermission(result.granted));
    }, []);

    return (
        <>
          <View style={styles.sectionContainer}>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionTitle}>Permissions</Text>
            </View>
          </View>
          <View style={styles.sectionContainer}>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionTitle}>Location</Text>
              <Button
                title="Request Permission"
                onPress={requestLocationPermisson}
                />
            </View>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.monospaced}>{JSON.stringify(hasLocationPermission, null, 2)}</Text>
            </View>
          </View>

          <View style={styles.sectionContainer}>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionTitle}>ActivityIdentification</Text>
              <Button
                title="Request Permission"
                onPress={requestActivityIdentificationPermisson}
                />
            </View>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.monospaced}>{JSON.stringify(hasActivityIdentificationPermission, null, 2)}</Text>
            </View>
          </View>
        </>
    )
};


const LocationAvailability = () => {
    const [locationAvailable, setLocationAvailable] = useState(false);

    const getLocationAvailability = useCallback(() => {
        HMSLocation.FusedLocation.Native.getLocationAvailability()
            .then(x => setLocationAvailable(x))
            .catch(err => console.log('Failed to get location availability', err));
    }, []);

    return (
        <>
          <View style={styles.sectionContainer}>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionTitle}>Location Availability</Text>
              <Button title="Check" onPress={getLocationAvailability} />
            </View>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.monospaced}>{JSON.stringify(locationAvailable, null, 2)}</Text>
            </View>
          </View>
        </>
    )
};


const MockLocation = () => {
    const [mocked, setMocked] = useState(false);
    const [lat, setLat] = useState("41.3");
    const [long, setLong] = useState("29.1");

    const enableMockLocation = () => {
        HMSLocation.FusedLocation.Native.setMockMode(true)
            .then(res => {
              console.log('Mock mode enabled:', res);
              setMocked(true);
          })
            .catch(err => console.log(err));
    };

    const disableMockLocation = () => {
        HMSLocation.FusedLocation.Native.setMockMode(false)
            .then(res => {
              console.log('Mock mode disabled:', res);
              setMocked(false)
            })
            .catch(err => console.log(err));
    };

    const setMockLocation = () => {
        HMSLocation.FusedLocation.Native.setMockLocation({ latitude: parseFloat(lat), longitude: parseFloat(long) })
            .then(res => { console.log('MOCK SET', res); })
            .catch(err => { console.log(err); });
    };

    return (
        <>
          <View style={styles.sectionContainer}>
            <View style={styles.spaceBetweenRow}>
            <Text style={styles.sectionTitle}>Mock Location</Text>
            {mocked ? 
                  <Button
                    title="Disable"
                    color="red"
                    onPress={disableMockLocation}
                  />
                  :
                  <Button
                    title={"Enable"}
                    onPress={enableMockLocation}
                  />}
            </View>
            <View>
              <Text style={styles.boldText}>Latitude</Text>
              <TextInput style={styles.input}
                         placeholder="LAT"
                         value={lat}
                         keyboardType="numeric"
                         onChangeText={setLat} />
              <Text style={styles.boldText}>Longitude</Text>
              <TextInput style={styles.input}
                         placeholder="LON"
                         value={long}
                         keyboardType="numeric"
                         onChangeText={setLong} />
            </View>
            <View style={styles.centralizeContent}>
              <Button
                disabled={!mocked}
                title="Set Mock Location"
                onPress={setMockLocation}
                />
               
            </View>
          </View>
        </>
    )
};


const LocationSettings = () => {
    const [locationSettings, setLocationSettings] = useState();

    const checkLocationSettings = useCallback(() => {
        const locationRequest = {
            priority: HMSLocation.FusedLocation.PriorityConstants.PRIORITY_HIGH_ACCURACY,
            interval: 5000,
            numUpdates: 20,
            fastestInterval: 6000,
            expirationTime: 100000,
            expirationTimeDuration: 100000,
            smallestDisplacement: 0,
            maxWaitTime: 1000.0,
            needAddress: false,
            language: "en",
            countryCode: "en",
        };

        const locationSettingsRequest = {
            locationRequests: [locationRequest],
            alwaysShow: false,
            needBle: false,
        };

        HMSLocation.FusedLocation.Native.checkLocationSettings(locationSettingsRequest)
            .then(res => setLocationSettings(res))
            .catch(ex => console.log("Error while getting location settings. " + ex))
    });

    return (
        <>
          <View style={styles.sectionContainer}>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionTitle}>Location Settings</Text>
              <Button title="Check" onPress={checkLocationSettings} />
            </View>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionDescription}>
              </Text>
            </View>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.monospaced}>
                {JSON.stringify(locationSettings, null, 2)}
              </Text>
            </View>
          </View>
        </>
    );
};

const LastLocation = () => {
  const [location, setLocation] = useState();

  const getLocation = useCallback(() => {
      HMSLocation.FusedLocation.Native.getLastLocation()
          .then(pos => setLocation(pos))
          .catch(err => console.log('Failed to get last location', err));
  }, []);

  return (
      <>
        <View style={styles.sectionContainer}>
          <View style={styles.spaceBetweenRow}>
            <Text style={styles.sectionTitle}>Last Location</Text>
            <Button title="Get last location" onPress={getLocation} />
          </View>
          <View style={styles.spaceBetweenRow}>
            <Text style={styles.monospaced}>
              {JSON.stringify(location, null, 2)}
            </Text>
          </View>
        </View>
      </>
  )
}

const LocationAddress = () => {
    const LocationRequest = {
      priority: HMSLocation.FusedLocation.PriorityConstants.PRIORITY_HIGH_ACCURACY,
      interval: 3,
      numUpdates: 10,
      fastestInterval: 1000.0,
      expirationTime: 1000.0,
      expirationTimeDuration: 1000.0,
      smallestDisplacement: 0.0,
      maxWaitTime: 10000.0,
      needAddress: true,
      language: 'en',
      countryCode: 'en',
  };
  const [locationAddress, setLocationAddress] = useState();

  const getLocation = useCallback(() => {
      HMSLocation.FusedLocation.Native.getLastLocationWithAddress(LocationRequest)
          .then(pos => setLocationAddress(pos))
          .catch(err => console.log('Failed to get last location', err));
  }, []);

  return (
      <>
        <View style={styles.sectionContainer}>
          <View style={styles.spaceBetweenRow}>
            <Text style={styles.sectionTitle}>Last Location Address</Text>
            <Button title="Get location address" onPress={getLocation} />
          </View>
          <View style={styles.spaceBetweenRow}>
            <Text style={styles.monospaced}>
              {JSON.stringify(locationAddress, null, 2)}
            </Text>
          </View>
        </View>
      </>
  )
}

const Notification = () => {
  return (
      <>
        <View style={styles.sectionContainer}>
          <View style={styles.spaceBetweenRow}>
            <Text style={styles.sectionTitle}>Set Notification</Text>
          </View>
          <View style={styles.centralizeContent}>
            <Button
              title='Set Notification'
              onPress={() => {
                HMSLocation.LocationKit.Native.setNotification({contentTitle: "Hello", contentText: "You received something", defType: "mipmap", resourceName: "ic_launcher"})
                  .then(x =>console.log('Notification set:', x))
                  .catch(err => console.log('Failed to set notification', err));
              }}
            />
            </View>
        </View>
      </>
  )
};

const LocationResult = () => {
    const [locationResult, setLocationResult] = useState();
    const [reqCode, setReqCode] = useState(2);
    const [autoUpdateEnabled, setAutoUpdateEnabled] = useState(false);

    const requestLocationUpdate = useCallback(() => {
        const LocationRequest = {
            priority: HMSLocation.FusedLocation.PriorityConstants.PRIORITY_HIGH_ACCURACY,
            interval: 1000,
            numUpdates: 1,
            fastestInterval: 1000.0,
            expirationTime: 10000.0,
            expirationTimeDuration: 10000.0,
            smallestDisplacement: 0.0,
            maxWaitTime: 10000.0,
            needAddress: true,
            language: 'en',
            countryCode: 'en',
        };

        HMSLocation.FusedLocation.Native.requestLocationUpdates(reqCode, LocationRequest)
            .then((res) => console.log(res))
            .catch(ex => console.log("Exception while requestLocationUpdates " + ex))
    }, []);

    const handleLocationUpdate = locationResult => {
        console.log(locationResult.lastLocation);
        setLocationResult(locationResult.lastLocation);
    };

    const addFusedLocationEventListener = useCallback(() => {
        requestLocationUpdate();
        HMSLocation.FusedLocation.Events.addFusedLocationEventListener(
            handleLocationUpdate,
        );
        setAutoUpdateEnabled(true);
    }, []);

    const removeFusedLocationEventListener = useCallback(() => {
        HMSLocation.FusedLocation.Events.removeFusedLocationEventListener(
            reqCode,
            handleLocationUpdate,
        );
        setAutoUpdateEnabled(false);
    }, [reqCode]);

    return (
        <>
          <View style={styles.sectionContainer}>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionTitle}>Location Update</Text>
            </View>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.monospaced}>
                {JSON.stringify(locationResult, null, 2)}
              </Text>
            </View>
            <View style={styles.centralizeContent}>
              <Button
                title={
                    autoUpdateEnabled
                        ? 'Disable auto-update'
                        : 'Enable auto-update'
                }
                onPress={() => {
                    if (autoUpdateEnabled) {
                        removeFusedLocationEventListener();
                    } else {
                        addFusedLocationEventListener();
                    }
                }}
                />
            </View>
          </View>
        </>
    )
}

const LocationEnhance = () => {
  const [navigationState, setNavigationState] = useState();

  const getNavigationState = useCallback(() => {
      HMSLocation.FusedLocation.Native.getNavigationContextState(HMSLocation.FusedLocation.NavigationRequestConstants.IS_SUPPORT_EX)
          .then(res => setNavigationState(res))
          .catch(ex => console.log("Error while getting navigation state. " + ex))
  });

  return (
      <>
        <View style={styles.sectionContainer}>
          <View style={styles.spaceBetweenRow}>
            <Text style={styles.sectionTitle}>Location Enhance</Text>
            <Button title="Check" onPress={getNavigationState} />
          </View>
          <View style={styles.spaceBetweenRow}>
            <Text style={styles.sectionDescription}>
            </Text>
          </View>
          <View style={styles.spaceBetweenRow}>
            <Text style={styles.monospaced}>
              {JSON.stringify(navigationState, null, 2)}
            </Text>
          </View>
        </View>
      </>
  );
};

const Geofence = () => {
    const [reqCode, setReqCode] = useState(1);
    const [activated, setActivated] = useState(false);
    const [subscribed, setSubscribed] = useState(false);
    const [geofenceResponse, setGeofenceResponse] = useState();

    const geofence1 = {
        longitude: 42.0,
        latitude: 29.0,
        radius: 20.0,
        uniqueId: 'e00322',
        conversions: 1,
        validContinueTime: 10000.0,
        dwellDelayTime: 10,
        notificationInterval: 1,
    };

    const geofence2 = {
        longitude: 41.0,
        latitude: 27.0,
        radius: 340.0,
        uniqueId: 'e00491',
        conversions: 2,
        validContinueTime: 1000.0,
        dwellDelayTime: 10,
        notificationInterval: 1,
    };

    /**
     * Geofence List
     */
    const geofenceRequest = {
        geofences: [geofence1, geofence2],
        conversions: 1,
        coordinate: 1,
    };

    const createGeofenceList = useCallback(requestCode => {
        HMSLocation.Geofence.Native.createGeofenceList(
            requestCode,
            geofenceRequest.geofences,
            geofenceRequest.conversions,
            geofenceRequest.coordinate,
        )
            .then(res => {
                console.log(res);
            })
            .catch(err => {
                console.log(err);
            });
    })

    const deleteGeofenceList = useCallback(requestCode => {
        HMSLocation.Geofence.Native.deleteGeofenceList(requestCode)
            .then(res => {
              console.log(res);
              setActivated(false);
            })
            .catch(err => console.log('ERROR: GeofenceList deletion failed', err))
    }, []);

    const handleGeofenceEvent = useCallback(geo => {
        console.log('GEOFENCE : ', geo);
        setGeofenceResponse(geo);
    });

    const addGeofenceEventListener = useCallback(() => {
        HMSLocation.Geofence.Events.addGeofenceEventListener(
            handleGeofenceEvent,
        );
        setSubscribed(true);
    }, []);

    const removeGeofenceEventListener = useCallback(() => {
        HMSLocation.Geofence.Events.removeGeofenceEventListener(
            handleGeofenceEvent,
        )
        setSubscribed(false);
    })

    return (
        <>
          <View style={styles.sectionContainer}>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionTitle}>Geofence</Text>
            </View>
            <View style={styles.centralizeContent}>
              <Button
                title={activated ? "Remove Geofence" : "Create Geofence"}
                onPress={() => {
                    if (activated) {
                        deleteGeofenceList(reqCode)
                    } else {
                        createGeofenceList(reqCode)
                    }
                }} />
                <Button
                  title={subscribed ? "Unsubscribe" : "Subscribe"}
                  onPress={() => {
                      if (subscribed) {
                          removeGeofenceEventListener()
                      } else {
                          addGeofenceEventListener()
                      }
                  }} />
            </View>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionDescription}>
                <Text style={styles.boldText}>Geofence Request Code</Text>:{' '}
                {`${reqCode || ''}`}
              </Text>
            </View>
            <Text style={styles.boldText}>
              {JSON.stringify(geofenceResponse, null, 2)}
            </Text>
          </View>
        </>
    )
}

const ActivityIdentification = () => {
    const [reqCode, setReqCode] = useState(1);

    const [activated, setActivated] = useState(false);
    const [subscribed, setSubscribed] = useState(false);

    const [identificationResponse, setIdentificationResponse] = useState();

    // Activity Identification
    const createActivityIdentification = useCallback(requestCode => {
        HMSLocation.ActivityIdentification.Native.createActivityIdentificationUpdates(requestCode, 20000)
            .then(res => {
                console.log(res);
                setActivated(true);
            })
            .catch(err => console.log('ERROR: Activity identification failed', err));
    }, []);
    const removeActivityIdentification = useCallback(requestCode => {
        HMSLocation.ActivityIdentification.Native.deleteActivityIdentificationUpdates(requestCode)
            .then(res => {
              console.log(res);
              setActivated(false);
            })
            .catch(err => console.log('ERROR: Activity identification deletion failed', err));
    }, []);

    const handleActivityIdentification = useCallback(act => {
        console.log('ACTIVITY : ', act);
        setIdentificationResponse(act);
    }, []);

    const addActivityIdentificationEventListener = useCallback(() => {
        HMSLocation.ActivityIdentification.Events.addActivityIdentificationEventListener(
            handleActivityIdentification,
        );
        setSubscribed(true);
    }, []);

    const removeActivityIdentificationEventListener = useCallback(() => {
        HMSLocation.ActivityIdentification.Events.removeActivityIdentificationEventListener(
            handleActivityIdentification,
        );
        setSubscribed(false);
    }, []);

    return (
        <>
          <View style={styles.sectionContainer}>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionTitle}>Activity Identification</Text>
            </View>
            <View style={styles.centralizeContent}>
              <Button
                title={
                  activated ?
                        "Remove Identification" :
                        "Get Identification"
                }
                onPress={() => {
                    if (activated) {
                        removeActivityIdentification(reqCode)
                    } else {
                        createActivityIdentification(reqCode)
                    }
                }} />
                <Button
                  title={subscribed ? "Unsubscribe" : "Subscribe"}
                  onPress={() => {
                      if (subscribed) {
                          removeActivityIdentificationEventListener()
                      } else {
                          addActivityIdentificationEventListener()
                      }
                  }} />
            </View>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionDescription}>
                <Text style={styles.boldText}>Activity Request Code</Text>:{' '}
                {`${reqCode || ''}`}
              </Text>
            </View>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.monospaced}>
                {JSON.stringify(identificationResponse, null, 2)}
              </Text>
            </View>
          </View>
        </>
    );
}

const ActivityConversion = () => {
    const [reqCode, setReqCode] = useState(1);
    const [activated, setActivated] = useState(false);
    const [subscribed, setSubscribed] = useState(false);
    const [conversionResponse, setConversionResponse] = useState();

    // Activity Conversion
    const handleActivityConversion = useCallback(conv => {
        console.log('CONVERSION : ', conv);
        setConversionResponse(conv);
    }, []);

    const createConversionUpdates = useCallback(requestCode => {
        HMSLocation.ActivityIdentification.Native.createActivityConversionUpdates(
            requestCode,
            [
                // STILL
                {
                    conversionType: HMSLocation.ActivityIdentification.ActivityConversions.ENTER_ACTIVITY_CONVERSION,
                    activityType: HMSLocation.ActivityIdentification.Activities.STILL
                },
                {
                    conversionType: HMSLocation.ActivityIdentification.ActivityConversions.EXIT_ACTIVITY_CONVERSION,
                    activityType: HMSLocation.ActivityIdentification.Activities.STILL
                },

                // ON FOOT
                {
                    conversionType: HMSLocation.ActivityIdentification.ActivityConversions.ENTER_ACTIVITY_CONVERSION,
                    activityType: HMSLocation.ActivityIdentification.Activities.FOOT
                },
                {
                    conversionType: HMSLocation.ActivityIdentification.ActivityConversions.EXIT_ACTIVITY_CONVERSION,
                    activityType: HMSLocation.ActivityIdentification.Activities.FOOT
                },

                // RUNNING
                {
                    conversionType: HMSLocation.ActivityIdentification.ActivityConversions.ENTER_ACTIVITY_CONVERSION,
                    activityType: HMSLocation.ActivityIdentification.Activities.RUNNING
                },
                {
                    conversionType: HMSLocation.ActivityIdentification.ActivityConversions.EXIT_ACTIVITY_CONVERSION,
                    activityType: HMSLocation.ActivityIdentification.Activities.RUNNING
                }
            ])
            .then(res => {
                console.log(res);
                setActivated(true);
            })
            .catch(err => console.log('ERROR: Activity Conversion creation failed', err));
    }, []);

    const deleteConversionUpdates = useCallback(requestCode => {
        HMSLocation.ActivityIdentification.Native.deleteActivityConversionUpdates(requestCode)
            .then(res => {
              console.log(res);
              setActivated(false);
            })
            .catch(err => console.log('ERROR: Activity Conversion deletion failed', err));
    }, []);

    const addActivityConversionEventListener = useCallback(() => {
        HMSLocation.ActivityIdentification.Events.addActivityConversionEventListener(
            handleActivityConversion,
        );
        setSubscribed(true);
    }, []);

    const removeActivityConversionEventListener = useCallback(() => {
        HMSLocation.ActivityIdentification.Events.removeActivityConversionEventListener(
            handleActivityConversion,
        );
        setSubscribed(false);
    }, []);

    return (
        <>
          <View style={styles.sectionContainer}>
            {/* Conversion */}
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionTitle}>Conversion Update</Text>
            </View>
            <View style={styles.centralizeContent}>
              <Button
                title={
                  activated ?
                        "Remove Update" :
                        "Create Update"
                }
                onPress={() => {
                    if (activated) {
                        console.log('CONV REQ CODE BEFORE REMOVAL', reqCode);
                        deleteConversionUpdates(reqCode)
                    } else {
                        createConversionUpdates(reqCode)
                    }
                }} />
                <Button
                  title={subscribed ? "Unsubscribe" : "Subscribe"}
                  onPress={() => {
                      if (subscribed) {
                          removeActivityConversionEventListener()
                      } else {
                          addActivityConversionEventListener()
                      }
                  }} />
            </View>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.sectionDescription}>
                <Text style={styles.boldText}>Conversion Request Code</Text>:{' '}
                {`${reqCode || ''}`}
              </Text>
            </View>
            <View style={styles.spaceBetweenRow}>
              <Text style={styles.monospaced}>
                {JSON.stringify(conversionResponse, null, 2)}
              </Text>
            </View>
          </View>
        </>
    );
}

const App = () => {
  console.log("App initialized")
    return (
        <>
          <SafeAreaView>
            <ScrollView
              contentInsetAdjustmentBehavior="automatic"
              style={styles.scrollView}>
              <Header />
              <View style={styles.body}>
                <Permissions />
                <View style={styles.divider} />
                <LocationAvailability />
                <View style={styles.divider} />
                <LocationSettings />
                <View style={styles.divider} />
                <LastLocation />
                <View style={styles.divider} />
                <LocationAddress />
                <View style={styles.divider} />
                <MockLocation />
                <View style={styles.divider} />
                <LocationEnhance />
                <View style={styles.divider} />
                <Notification />
                <View style={styles.divider} />
                <LocationResult />
                <View style={styles.divider} />
                <Geofence />
                <View style={styles.divider} />
                <ActivityIdentification />
                <View style={styles.divider} />
                <ActivityConversion />
                <View style={styles.divider} />
              </View>
            </ScrollView>
          </SafeAreaView>
        </>
    );
};

const styles = StyleSheet.create({
    scrollView: {
        backgroundColor: Colors.lighter,
    },
    engine: {
        position: 'absolute',
        right: 0,
    },
    body: {
        backgroundColor: Colors.white,
    },
    sectionContainer: {
        marginTop: 32,
        paddingHorizontal: 24,
    },
    sectionTitle: {
        fontSize: 24,
        fontWeight: '600',
        color: Colors.black,
    },
    sectionDescription: {
        marginTop: 8,
        fontSize: 18,
        fontWeight: '400',
        color: Colors.dark,
    },
    activityData: {
        marginTop: 8,
        marginLeft: 5,
        fontSize: 16,
        fontWeight: '400',
        color: Colors.dark,
    },
    highlight: {
        fontWeight: '700',
    },
    footer: {
        color: Colors.dark,
        fontSize: 12,
        fontWeight: '600',
        padding: 4,
        paddingRight: 12,
        textAlign: 'right',
    },
    header: {
        height: 180,
        width: '100%',
    },
    headerTitleWrapper: {
        position: 'absolute',
        justifyContent: 'center',
        top: 0,
        bottom: 0,
        right: 0,
        left: 20,
    },
    headerTitle: { fontSize: 17, fontWeight: '700', color: '#5FD8FF' },
    headerLogoWrapper: { alignItems: 'flex-end', justifyContent: 'center' },
    headerLogo: { height: 200, width: 200 },
    spaceBetweenRow: { flexDirection: 'row', justifyContent: 'space-between' },
    divider: {
        width: '90%',
        alignSelf: 'center',
        height: 1,
        backgroundColor: 'grey',
        marginTop: 20,
    },
    boldText: { fontWeight: 'bold' },
    centralizeSelf: { alignSelf: 'center' },
    centralizeContent: { flexDirection: 'row', justifyContent: 'center' },
    monospaced: { fontFamily: 'monospace' },
});

export default App;
