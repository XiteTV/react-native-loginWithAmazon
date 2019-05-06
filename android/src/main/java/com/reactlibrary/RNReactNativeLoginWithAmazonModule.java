
package com.reactlibrary;

import android.content.Context;
import android.util.Log;

import com.amazon.identity.auth.device.AuthError;
import com.amazon.identity.auth.device.api.Listener;
import com.amazon.identity.auth.device.api.authorization.AuthCancellation;
import com.amazon.identity.auth.device.api.authorization.AuthorizationManager;
import com.amazon.identity.auth.device.api.authorization.AuthorizeListener;
import com.amazon.identity.auth.device.api.authorization.AuthorizeRequest;
import com.amazon.identity.auth.device.api.authorization.AuthorizeResult;
import com.amazon.identity.auth.device.api.authorization.ProfileScope;
import com.amazon.identity.auth.device.api.authorization.User;
import com.amazon.identity.auth.device.api.workflow.RequestContext;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RNReactNativeLoginWithAmazonModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private RequestContext requestContext;
  private DeviceEventManagerModule.RCTDeviceEventEmitter mJSModule = null;
  private UserAuthorizationListener userAuthorizationListener;

  private static final String TAG = "RN_LOGIN_W/AMAZON_MOD";

  public RNReactNativeLoginWithAmazonModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.initLoginWithAmazon();
  }

  @Override
  public String getName() {
    return "RNReactNativeLoginWithAmazon";
  }

  @ReactMethod
  public void initialize() {
    this.initLoginWithAmazon();
  }

  @ReactMethod
  public void requestAuthorization () {
    AuthorizationManager.authorize(
            new AuthorizeRequest.Builder(requestContext)
              .addScopes(ProfileScope.profile(), ProfileScope.postalCode())
              .build()
    );
  }

  @ReactMethod
  public void signOut () {
    // Todo: Handle this
    AuthorizationManager.signOut(getReactApplicationContext(), new Listener<Void, AuthError>() {
      @Override
      public void onSuccess(Void aVoid) {

      }

      @Override
      public void onError(AuthError authError) {

      }
    });
  }

  @ReactMethod
  public void getUserInformation() {
    userAuthorizationListener.fetchUserProfile();
  }

//  /**
//   * Function that initialise the DeviceEventManager to send event out
//   * to react-native
//   *
//   */
//  private void initializeDeviceEmitter() {
//    if (this.mJSModule == null) {
//      this.mJSModule = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
//    }
//  }

  private void initLoginWithAmazon() {
    Log.d(TAG, "initLoginWithAmazon: initializing");

    requestContext = RequestContext.create(this.reactContext.getApplicationContext());
    final UserInformationListener userInformationListener = new UserInformationListener(reactContext);
    userAuthorizationListener = new UserAuthorizationListener(userInformationListener, reactContext);
    requestContext.registerListener(userAuthorizationListener);

//    final WritableMap params = new WritableNativeMap();
//    this.initializeDeviceEmitter();
//    requestContext.registerListener(new AuthorizeListener() {
//      @Override
//      public void onSuccess(AuthorizeResult authorizeResult) {
//        fetchUserProfile();
//        params.putString("status", "SUCCESS");
//        emitToBridge("onAuthorizeListener", params);
//      }
//
//      @Override
//      public void onError(AuthError authError) {
//        Log.e(TAG, "AuthError during authorization", authError);
//        params.putString("status", "ERROR");
//        params.putString("message", "" + authError);
//        emitToBridge("onAuthorizeListener", params);
//      }
//
//      @Override
//      public void onCancel(AuthCancellation authCancellation) {
//        params.putString("status", "CANCEL");
//        params.putString("message", "" + authCancellation);
//        emitToBridge("onAuthorizeListener", params);
//      }
//    });

  }

//  private void fetchUserProfile() {
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
//  }
//
//  public void emitToBridge(String eventName, WritableMap map) {
//    this.initializeDeviceEmitter();
//
//    mJSModule.emit(eventName, map);
//  }
}