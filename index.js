/* Dependencies
================================================================== */
import { NativeModules } from 'react-native';

/* NativeModule
================================================================== */
const { RNReactNativeLoginWithAmazon } = NativeModules;

/* AmazonLogin
================================================================== */
export async function requestAuthorization() {
  const authorizationStatus = RNReactNativeLoginWithAmazon.authorizationStatus;
  try {
    const response = await RNReactNativeLoginWithAmazon.requestAuthorization();
    const { authorizationStatus } = this;
    console.log('[Authorization]', JSON.stringify(response, null, 2));
    // onSuccess(response);
    return Promise.resolve(response);
  } catch (e) {
    console.log('[Authorization] - error', JSON.stringify(e, null, 2));
    // switch (e.status) {
    //   case authorizationStatus.CANCEL:
    //     onCancel(response);
    //     break;
    //   case authorizationStatus.ERROR:
    //     onError(response);
    //     break;
    //   default:
    //     break;
    // }

    return Promise.reject(e);
  }
}

export async function getUserInformation() {
  try {
    const userInfoStatus = RNReactNativeLoginWithAmazon.userInfoStatus;
    const response = await RNReactNativeLoginWithAmazon.getUserInformation();
    const { userInfoStatus } = this;
    console.log('[UserInformation]', JSON.stringify(response, null, 2));
    // onSuccess(response);
    return Promise.resolve(response);
  } catch (e) {
    console.log('[UserInformation] - error', JSON.stringify(e, null, 2));
    // onError(e);
    return Promise.reject(e);
  }
}

export async function signOut() {
  try {
    const response = await RNReactNativeLoginWithAmazon.signOut();
    console.log('[Authorization Signout]', JSON.stringify(response, null, 2));
    // onSuccess(response);
    return Promise.resolve(response);
  } catch (e) {
    console.log('[Authorization Signout] - error', JSON.stringify(e, null, 2));
    // onError(e);
    return Promise.reject(e);
  }
}
