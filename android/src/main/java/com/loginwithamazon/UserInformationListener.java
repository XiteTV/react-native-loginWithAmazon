package com.loginwithamazon;

import android.util.Log;

import com.amazon.identity.auth.device.AuthError;
import com.amazon.identity.auth.device.api.Listener;
import com.amazon.identity.auth.device.api.authorization.User;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

public class UserInformationListener implements Listener<User, AuthError> {
  private static final String TAG = UserAuthorizationListener.class.getName();
  private final ReactApplicationContext reactContext;

  private Map<String, String> USER_INFO_STATUS;
  private Promise promise;
  private DeviceEventManagerModule.RCTDeviceEventEmitter mJSModule = null;

  public UserInformationListener(final Map<String, String> status, final ReactApplicationContext reactContext) {
    Log.d(TAG, "Initialising UserInformationListener class");
    this.reactContext = reactContext;
    this.USER_INFO_STATUS = status;
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
    params.putString("status", USER_INFO_STATUS.get("SUCCESS"));

    Log.d(TAG, "User information: " + name + ", " + email + ", " + account + ", " +  zipCode + "." );

    if (this.promise != null) {
      this.promise.resolve(params);
      this.clearPromise();
    }
  }

  @Override
  public void onError(AuthError authError) {
    final WritableMap params = new WritableNativeMap();

    params.putString("status", USER_INFO_STATUS.get("ERROR"));
    params.putString("statusMessage", "" + authError);

    if (this.promise != null) {
      this.promise.reject("error", params);
      this.clearPromise();
    }
  }

  public void setPromise(Promise promise) {
    this.promise = promise;
  }

  private void clearPromise() {
    this.promise = null;
  }
}
