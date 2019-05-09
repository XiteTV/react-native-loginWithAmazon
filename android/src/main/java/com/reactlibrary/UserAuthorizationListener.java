package com.reactlibrary;

import android.util.Log;

import com.amazon.identity.auth.device.AuthError;
import com.amazon.identity.auth.device.api.authorization.AuthCancellation;
import com.amazon.identity.auth.device.api.authorization.AuthorizeListener;
import com.amazon.identity.auth.device.api.authorization.AuthorizeResult;
import com.amazon.identity.auth.device.api.authorization.User;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Map;

public class UserAuthorizationListener extends AuthorizeListener {
  private static final String TAG = UserAuthorizationListener.class.getName();
  private final ReactApplicationContext reactContext;

  private Map<String, String> AUTHORIZATION_STATUS;
  private Promise promise;
  private final UserInformationListener userInformationListener;
  private DeviceEventManagerModule.RCTDeviceEventEmitter mJSModule = null;

  public UserAuthorizationListener(
          final UserInformationListener userInformationListener,
          final Map<String, String> status,
          final ReactApplicationContext reactContext) {
    Log.d(TAG, "Initialising UserAuthorizationListener class");
    this.reactContext = reactContext;
    this.AUTHORIZATION_STATUS = status;
    this.userInformationListener = userInformationListener;
  }

  @Override
  public void onSuccess(AuthorizeResult authorizeResult) {
    final String accessToken = authorizeResult.getAccessToken();
    final WritableMap params = new WritableNativeMap();

    params.putString("accessToken", accessToken);
    params.putString("status", AUTHORIZATION_STATUS.get("SUCCESS"));

    this.promise.resolve(params);
    this.clearPromise();
  }

  @Override
  public void onError(AuthError authError) {
    Log.e(TAG, "AuthError during authorization", authError);
    final WritableMap params = new WritableNativeMap();
    params.putString("status", AUTHORIZATION_STATUS.get("ERROR"));
    params.putString("message", "" + authError);

    this.promise.reject("error", params);
    this.clearPromise();
  }

  @Override
  public void onCancel(AuthCancellation authCancellation) {
    final WritableMap params = new WritableNativeMap();
    params.putString("status", AUTHORIZATION_STATUS.get("CANCEL"));
    params.putString("message", "" + authCancellation);

    this.promise.reject("error", params);
    this.clearPromise();
  }

  public void setPromise(Promise promise) {
    this.promise = promise;
  }

  private void clearPromise() {
    this.promise = null;
  }

  public void fetchUserProfile() {
    User.fetch(this.reactContext.getApplicationContext(), this.userInformationListener);
  }

}
