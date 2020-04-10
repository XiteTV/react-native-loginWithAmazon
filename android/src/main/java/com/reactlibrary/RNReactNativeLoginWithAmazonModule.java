
package com.reactlibrary;

import android.content.Context;
import android.util.Log;

import com.amazon.identity.auth.device.AuthError;
import com.amazon.identity.auth.device.api.Listener;
import com.amazon.identity.auth.device.api.authorization.AuthorizationManager;
import com.amazon.identity.auth.device.api.authorization.AuthorizeRequest;
import com.amazon.identity.auth.device.api.authorization.ProfileScope;
import com.amazon.identity.auth.device.api.workflow.RequestContext;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.HashMap;
import java.util.Map;

public class RNReactNativeLoginWithAmazonModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

  private final ReactApplicationContext reactContext;
  private RequestContext requestContext;

  private UserAuthorizationListener userAuthorizationListener;
  private UserInformationListener userInformationListener;

  private static final String TAG = "RN_LOGIN_W/AMAZON_MOD";

  private final Map<String, String> AUTHORIZATION_STATUS = new HashMap<>();
  private final Map<String, String> USER_INFO_STATUS = new HashMap<>();

  public RNReactNativeLoginWithAmazonModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.initLoginWithAmazon();

    reactContext.addLifecycleEventListener(this);

    // Setting constants
    AUTHORIZATION_STATUS.put("SUCCESS", "authorizationSuccess");
    AUTHORIZATION_STATUS.put("ERROR", "authorizationError");
    AUTHORIZATION_STATUS.put("CANCEL", "authorizationCancel");

    USER_INFO_STATUS.put("SUCCESS", "userInfoSuccess");
    USER_INFO_STATUS.put("ERROR", "userInfoError");
  }

  @Override
  public String getName() {
    return "RNReactNativeLoginWithAmazon";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();

    constants.put("authorizationStatus", this.AUTHORIZATION_STATUS);
    constants.put("userInfoStatus", this.USER_INFO_STATUS);

    return  constants;
  }

  @Override
  public void onHostResume() {
      this.requestContext.onResume();
  }

  @Override
  public void onHostPause() {
        // Activity `onPause`
  }

  @Override
  public void onHostDestroy() {
        // Activity `onDestroy`
  }

  @ReactMethod
  public void requestAuthorization (Promise promise) {
    this.userAuthorizationListener.setPromise(promise);
    AuthorizationManager.authorize(
            new AuthorizeRequest.Builder(requestContext)
              .addScopes(ProfileScope.profile(), ProfileScope.postalCode())
              .build()
    );
  }

    @ReactMethod
    public void getUserInformation(Promise promise) {
      this.userInformationListener.setPromise(promise);
        userAuthorizationListener.fetchUserProfile();
    }

  @ReactMethod
  public void signOut (final Promise promise) {
    final WritableMap params = new WritableNativeMap();
    // Todo: Handle this
    AuthorizationManager.signOut(getReactApplicationContext(), new Listener<Void, AuthError>() {
      @Override
      public void onSuccess(Void aVoid) {
        params.putString("status", "SUCCESS");
        promise.resolve(params);
      }

      @Override
      public void onError(AuthError authError) {
        params.putString("status", "SUCCESS");
        promise.reject("error", params);
      }
    });
  }

  private void initLoginWithAmazon() {
    Log.d(TAG, "initLoginWithAmazon: initializing");

    requestContext = RequestContext.create(this.reactContext.getApplicationContext());

    this.userInformationListener = new UserInformationListener(
            USER_INFO_STATUS,
            reactContext);

    this.userAuthorizationListener = new UserAuthorizationListener(
            userInformationListener,
            AUTHORIZATION_STATUS,
            reactContext
    );

    requestContext.registerListener(userAuthorizationListener);

  }
}
