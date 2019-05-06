package com.reactlibrary;

import android.util.Log;

import com.amazon.identity.auth.device.AuthError;
import com.amazon.identity.auth.device.api.Listener;
import com.amazon.identity.auth.device.api.authorization.User;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class UserInformationListener implements Listener<User, AuthError> {
  private static final String TAG = UserAuthorizationListener.class.getName();
  private final ReactApplicationContext reactContext;
  private DeviceEventManagerModule.RCTDeviceEventEmitter mJSModule = null;

  public UserInformationListener(final ReactApplicationContext reactContext) {
    Log.d(TAG, "Initialising UserInformationListener class");
    this.reactContext = reactContext;
  }

  @Override
  public void onSuccess(User user) {
    final WritableMap params = new WritableNativeMap();

    final String name = user.getUserName();
    final String email = user.getUserEmail();
    final String account = user.getUserId();
    final String zipCode = user.getUserPostalCode();

    params.putString("name", name);
    params.putString("email", email);
    params.putString("id", account);
    params.putString("zipCode", zipCode);

    Log.d(TAG, "User information: " + name + ", " + email + ", " + account + ", " +  zipCode + "." );
    emitToBridge("onUserProfileResponse", params);
  }

  @Override
  public void onError(AuthError authError) {
    final WritableMap params = new WritableNativeMap();

    params.putString("error", "" + authError);
    emitToBridge("onUserProfileResponse", params);
  }

  /**
   * Function that initialise the DeviceEventManager to send event out
   * to react-native
   *
   */
  private void initializeDeviceEmitter() {
    if (this.mJSModule == null) {
      this.mJSModule = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }
  }

  private void emitToBridge(String eventName, WritableMap map) {
    this.initializeDeviceEmitter();

    mJSModule.emit(eventName, map);
  }
}
