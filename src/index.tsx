import { NativeModules } from 'react-native';

export enum ScaleFusionResponseError {
  ACTION_NOT_SUPPORTED = 'ACTION_NOT_SUPPORTED',
  NOT_AUTHORIZED = 'NOT_AUTHORIZED',
  DEVICE_CURRENTLY_UNMANAGED = 'DEVICE_CURRENTLY_UNMANAGED',
  ERROR = 'ERROR',
}

export interface ScaleFusionDeviceData {
  mdmDeviceId: string;
  isEnrolled: boolean;
  isManaged: boolean;
  deviceName: string;
  imei: string;
  serialNumber: string;
  gsmSerialNumber: string;
  buildSerialNumber: string;
}

type ScaleFusionType = {
  getDeviceInfo(): Promise<ScaleFusionDeviceData>;
  rebootDevice(): Promise<null>;
  powerOffDevice(): Promise<null>;
  launchWifi(): Promise<null>;
  toggleHotspot(enable: Boolean): Promise<null>;
  toggleMobileData(enable: Boolean): Promise<null>;
  toggleFlightMode(enable: Boolean): Promise<null>;
  toggleStatusBar(enable: Boolean): Promise<null>;
  installPendingAppsNow(): Promise<null>;
};

const { ScaleFusion } = NativeModules;

export default ScaleFusion as ScaleFusionType;
