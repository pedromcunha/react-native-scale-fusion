package com.reactnativescalefusion;

import android.database.Cursor;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.Promise;

import com.mdminfo.mdmsdk.MDMSDK;

@ReactModule(name = ScaleFusionModule.NAME)
public class ScaleFusionModule extends ReactContextBaseJavaModule {
  public static final String NAME = "ScaleFusion";

  public ScaleFusionModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void getDeviceInfo(Promise promise) {
    ReactApplicationContext context = getReactApplicationContext();
    MDMSDK.Response response = MDMSDK.getMDMInfo(context);
    if (response.apiResult() == MDMSDK.APIResult.NOT_AUTHORIZED) {
      promise.reject("UNAUTHORIZED", "Not authorized");
    } else if (response.apiResult() == MDMSDK.APIResult.MDM_APP_NOT_INSTALLED) {
      promise.reject("MDM_APP_NOT_INSTALLED", "MDM not configured in dashboard");
    } else if (response.result() != null) {
      Cursor result = response.result();
      result.moveToFirst();
      WritableMap data = Arguments.createMap();
      data.putString("mdmDeviceId", MDMSDK.mdmDeviceID(context));
      data.putBoolean("isEnrolled", MDMSDK.isEnrolled(context));
      data.putBoolean("isManaged", MDMSDK.isManaged(context));
      data.putString("deviceName", MDMSDK.deviceName(context));
      data.putString("imei", MDMSDK.imeiNumber(context));
      data.putString("serialNumber", MDMSDK.serialNumber(context));
      data.putString("gsmSerialNumber", MDMSDK.gsmSerialNumber(context));
      data.putString("buildSerialNumber", MDMSDK.buildSerialNumber(context));
      promise.resolve(data);
    } else {
      promise.reject("RESULT_EMPTY", "No data returned");
    }
  }

  @ReactMethod
  public void rebootDevice(Promise promise) {
    ReactApplicationContext context = getReactApplicationContext();
    if (MDMSDK.rebootSupported(context)) {
      MDMSDK.Response response = MDMSDK.rebootDevice(context);
      String responseError = parseResponseError(response);
      if (responseError != null) {
        promise.reject(responseError, "Could not reboot device");
      } else {
        promise.resolve(null);
      }
    } else {
      promise.reject("ACTION_NOT_SUPPORTED", "Reboot unsupported on device");
    }
  }

  @ReactMethod
  public void powerOffDevice(Promise promise) {
    ReactApplicationContext context = getReactApplicationContext();
    if (MDMSDK.powerOffSupported(context)) {
      MDMSDK.Response response = MDMSDK.powerOffDevice(context);
      String responseError = parseResponseError(response);
      if (responseError != null) {
        promise.reject(responseError, "Could not power off device");
      } else {
        promise.resolve(null);
      }
    } else {
      promise.reject("ACTION_NOT_SUPPORTED", "Power off unsupported on device");
    }
  }

  @ReactMethod
  public void launchWifi(Promise promise) {
    ReactApplicationContext context = getReactApplicationContext();
    if (MDMSDK.launchWifiSupported(context)) {
      MDMSDK.Response response = MDMSDK.launchWifiScreen(context);
      String responseError = parseResponseError(response);
      if (responseError != null) {
        promise.reject(responseError, "Could not launch wifi");
      } else {
        promise.resolve(null);
      }
    } else {
      promise.reject("ACTION_NOT_SUPPORTED", "Launch Wifi unsupported on device");
    }
  }

  @ReactMethod
  public void toggleHotspot(Boolean enable, Promise promise) {
    ReactApplicationContext context = getReactApplicationContext();
    if (MDMSDK.toggleHotspotSupported(context)) {
      MDMSDK.Response response = MDMSDK.toggleHotspot(context, enable);
      String responseError = parseResponseError(response);
      if (responseError != null) {
        promise.reject(responseError, "Could not toggle hotspot on device");
      } else {
        promise.resolve(null);
      }
    } else {
      promise.reject("ACTION_NOT_SUPPORTED", "Toggle hotspot unsupported on device");
    }
  }

  @ReactMethod
  public void toggleMobileData(Boolean enable, Promise promise) {
    ReactApplicationContext context = getReactApplicationContext();
    if (MDMSDK.toggleMobileDataSupported(context)) {
      MDMSDK.Response response = MDMSDK.toggleMobileData(context, enable);
      String responseError = parseResponseError(response);
      if (responseError != null) {
        promise.reject(responseError, "Could not toggle mobile data on device");
      } else {
        promise.resolve(null);
      }
    } else {
      promise.reject("ACTION_NOT_SUPPORTED", "Toggle mobile data unsupported on device");
    }
  }

  @ReactMethod
  public void toggleFlightMode(Boolean enable, Promise promise) {
    ReactApplicationContext context = getReactApplicationContext();
    if (MDMSDK.toggleFlightModeSupported(context)) {
      MDMSDK.Response response = MDMSDK.toggleFlightMode(context, enable);
      String responseError = parseResponseError(response);
      if (responseError != null) {
        promise.reject(responseError, "Could not toggle flight mode on device");
      } else {
        promise.resolve(null);
      }
    } else {
      promise.reject("ACTION_NOT_SUPPORTED", "Toggle flight mode unsupported on device");
    }
  }

  @ReactMethod
  public void toggleStatusBar(Boolean enable, Promise promise) {
    ReactApplicationContext context = getReactApplicationContext();
    if (MDMSDK.toggleStatusBarSupported(context)) {
      MDMSDK.Response response = MDMSDK.toggleStatusBar(context, enable);
      String responseError = parseResponseError(response);
      if (responseError != null) {
        promise.reject(responseError, "Could not toggle status bar on device");
      } else {
        promise.resolve(null);
      }
    } else {
      promise.reject("ACTION_NOT_SUPPORTED", "Toggle stats bar unsupported on device");
    }
  }

  @ReactMethod
  public void installPendingAppsNow(Promise promise) {
    ReactApplicationContext context = getReactApplicationContext();
    MDMSDK.Response response = MDMSDK.installPendingAppsNow(context);
    String responseError = parseResponseError(response);
    if (responseError != null) {
      promise.reject(responseError, "Could not install pending apps on device");
    } else {
      promise.resolve(null);
    }
  }

  private String parseResponseError(MDMSDK.Response response) {
    if (response != null) {
      if (response.apiResult() == MDMSDK.APIResult.ACTION_NOT_SUPPORTED) {
        return "ACTION_NOT_SUPPORTED";
      } else if (response.apiResult() == MDMSDK.APIResult.NOT_AUTHORIZED) {
        return "NOT_AUTHORIZED";
      } else if (response.apiResult() == MDMSDK.APIResult.DEVICE_CURRENTLY_UNMANAGED) {
        return "DEVICE_CURRENTLY_UNMANAGED";
      } else if (response.apiResult() != MDMSDK.APIResult.SUCCESS) {
        return "ERROR";
      }
    }
    return null;
  }
}
