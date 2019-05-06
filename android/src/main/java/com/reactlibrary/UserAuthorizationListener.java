package com.reactlibrary;

import android.util.Log;

import com.amazon.identity.auth.device.AuthError;
import com.amazon.identity.auth.device.api.Listener;
import com.amazon.identity.auth.device.api.authorization.AuthCancellation;
import com.amazon.identity.auth.device.api.authorization.AuthorizeListener;
import com.amazon.identity.auth.device.api.authorization.AuthorizeResult;
import com.amazon.identity.auth.device.api.authorization.User;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class UserAuthorizationListener extends AuthorizeListener {
  private static final String TAG = UserAuthorizationListener.class.getName();
  private final ReactApplicationContext reactContext;
  private final UserInformationListener userInformationListener;
  private DeviceEventManagerModule.RCTDeviceEventEmitter mJSModule = null;

  public UserAuthorizationListener(final UserInformationListener userInformationListener, final ReactApplicationContext reactContext) {
    Log.d(TAG, "Initialising UserAuthorizationListener class");
    this.reactContext = reactContext;
    this.userInformationListener = userInformationListener;
  }

  @Override
  public void onSuccess(AuthorizeResult authorizeResult) {
    fetchUserProfile();
    final WritableMap params = new WritableNativeMap();
    params.putString("status", "SUCCESS");
    emitToBridge("onAuthorizeListener", params);
  }

  @Override
  public void onError(AuthError authError) {
    Log.e(TAG, "AuthError during authorization", authError);
    final WritableMap params = new WritableNativeMap();
    params.putString("status", "ERROR");
    params.putString("message", "" + authError);
    emitToBridge("onAuthorizeListener", params);
  }

  @Override
  public void onCancel(AuthCancellation authCancellation) {
    final WritableMap params = new WritableNativeMap();
    params.putString("status", "CANCEL");
    params.putString("message", "" + authCancellation);
    emitToBridge("onAuthorizeListener", params);
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

  public void fetchUserProfile() {

    User.fetch(this.reactContext.getApplicationContext(), this.userInformationListener);

//    User.fetch(this.reactContext, new Listener<User, AuthError>() {
//      final WritableMap params = new WritableNativeMap();
//
//      /* fetch completed successfully. */
//      @Override
//      public void onSuccess(User user) {
//        final String name = user.getUserName();
//        final String email = user.getUserEmail();
//        final String account = user.getUserId();
//        final String zipCode = user.getUserPostalCode();
//
//        params.putString("name", name);
//        params.putString("email", email);
//        params.putString("id", account);
//        params.putString("zipCode", zipCode);
//
//        Log.d(TAG, "User information: " + name + ", " + email + ", " + account + ", " +  zipCode + "." );
//        emitToBridge("onUserProfileResponse", params);
//      }
//
//      /* There was an error during the attempt to get the profile. */
//      @Override
//      public void onError(AuthError authError) {
//        params.putString("error", "" + authError);
//        emitToBridge("onUserProfileResponse", params);
//      }
//
//    });
  }
}
