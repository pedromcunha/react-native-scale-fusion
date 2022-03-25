import * as React from 'react';

import { StyleSheet, View, Text, Button } from 'react-native';
import ReactNativeScaleFusion from 'react-native-scale-fusion';

export default function App() {
  const [deviceName, setDeviceName] = React.useState<string | undefined>();
  const [mdmDeviceId, setMdmDeviceId] = React.useState<string | undefined>();
  const [isEnrolled, setIsEnrolled] = React.useState<boolean | undefined>();
  const [isManaged, setIsManaged] = React.useState<boolean | undefined>();
  const [gsmSerialNumber, setGsmSerialNumber] = React.useState<
    string | undefined
  >();
  const [buildSerialNumber, setBuildSerialNumber] = React.useState<
    string | undefined
  >();
  const [serialNumber, setSerialNumber] = React.useState<string | undefined>();
  const [imei, setImei] = React.useState<string | undefined>();

  const getDeviceInfo = () => {
    ReactNativeScaleFusion.getDeviceInfo()
      .then((data) => {
        setDeviceName(data.deviceName);
        setMdmDeviceId(data.mdmDeviceId);
        setIsEnrolled(data.isEnrolled);
        setIsManaged(data.isManaged);
        setGsmSerialNumber(data.gsmSerialNumber);
        setSerialNumber(data.serialNumber);
        setBuildSerialNumber(data.buildSerialNumber);
        setImei(data.imei);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  React.useEffect(() => {
    getDeviceInfo();
  }, []);

  return (
    <View style={styles.container}>
      <Text>Device Name: {deviceName}</Text>
      <Text>MDM Device Id: {mdmDeviceId}</Text>
      <Text>Is Enrolled: {isEnrolled ? 'Yes' : 'No'}</Text>
      <Text>Is Managed: {isManaged ? 'Yes' : 'No'}</Text>
      <Text>GSM Serial Number: {gsmSerialNumber}</Text>
      <Text>Build Serial Number: {buildSerialNumber}</Text>
      <Text>Serial Number: {serialNumber}</Text>
      <Text>imei: {imei}</Text>
      <Button title="Refresh Device Data" onPress={getDeviceInfo} />
      <Button
        title="Reboot device"
        onPress={() => {
          ReactNativeScaleFusion.rebootDevice(null);
        }}
      />
      <Button
        title="Power Off Device"
        onPress={() => {
          ReactNativeScaleFusion.powerOffDevice(null);
        }}
      />
      <Button
        title="Install Pending Apps"
        onPress={() => {
          ReactNativeScaleFusion.installPendingAppsNow();
        }}
      />
      <Button
        title="Launch Wifi"
        onPress={() => {
          ReactNativeScaleFusion.launchWifi();
        }}
      />
      <Button
        title="Enable Flight Mode"
        onPress={() => {
          ReactNativeScaleFusion.toggleFlightMode(true);
        }}
      />
      <Button
        title="Disabled Flight Mode"
        onPress={() => {
          ReactNativeScaleFusion.toggleFlightMode(false);
        }}
      />
      <Button
        title="Enable Hot Spot"
        onPress={() => {
          ReactNativeScaleFusion.toggleHotspot(true);
        }}
      />
      <Button
        title="Disabled Hot Spot"
        onPress={() => {
          ReactNativeScaleFusion.toggleHotspot(false);
        }}
      />
      <Button
        title="Enable Mobile Data"
        onPress={() => {
          ReactNativeScaleFusion.toggleMobileData(true);
        }}
      />
      <Button
        title="Disabled Mobile Data"
        onPress={() => {
          ReactNativeScaleFusion.toggleMobileData(true);
        }}
      />
      <Button
        title="Enable Status Bar"
        onPress={() => {
          ReactNativeScaleFusion.toggleStatusBar(true);
        }}
      />
      <Button
        title="Disabled Status Bar"
        onPress={() => {
          ReactNativeScaleFusion.toggleStatusBar(false);
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
