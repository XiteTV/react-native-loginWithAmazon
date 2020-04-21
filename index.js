/* Dependencies
================================================================== */
import { NativeModules } from 'react-native';

/* NativeModule
================================================================== */
const { RNReactNativeLoginWithAmazon } = NativeModules;

/* AmazonLogin
================================================================== */
export async function requestAuthorization() {
  return RNReactNativeLoginWithAmazon.requestAuthorization();
}

export async function getUserInformation() {
  return RNReactNativeLoginWithAmazon.getUserInformation();
}

export async function signOut() {
  return RNReactNativeLoginWithAmazon.signOut();
}
