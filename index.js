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
    console.log('[Authorization]', JSON.stringify(response, null, 2));
    return Promise.resolve(response);
  } catch (e) {
    console.error('[Authorization] - error', JSON.stringify(e, null, 2));
    return Promise.reject(e);
  }
}

export async function getUserInformation() {
  try {
    const userInfoStatus = RNReactNativeLoginWithAmazon.userInfoStatus;
    const response = await RNReactNativeLoginWithAmazon.getUserInformation();
    console.log('[UserInformation]', JSON.stringify(response, null, 2));
    return Promise.resolve(response);
  } catch (e) {
    console.error('[UserInformation] - error', JSON.stringify(e, null, 2));
    return Promise.reject(e);
  }
}

export async function signOut() {
  try {
    const response = await RNReactNativeLoginWithAmazon.signOut();
    console.log('[Authorization Signout]', JSON.stringify(response, null, 2));
    return Promise.resolve(response);
  } catch (e) {
    console.error(
      '[Authorization Signout] - error',
      JSON.stringify(e, null, 2)
    );
    return Promise.reject(e);
  }
}
