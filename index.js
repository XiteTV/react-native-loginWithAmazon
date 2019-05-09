/* Dependencies
================================================================== */
import { NativeModules } from 'react-native';

/* NativeModule
================================================================== */
const { RNReactNativeLoginWithAmazon } = NativeModules;

/* AmazonLogin
================================================================== */
const AmazonLogin = {
	authorizationStatus: RNReactNativeLoginWithAmazon.authorizationStatus,
	userInfoStatus: RNReactNativeLoginWithAmazon.userInfoStatus,
	requestAuthorization: async ({
		onSuccess = () => {},
		onError = () => {},
		onCancel = () => {},
	}) => {
		try {
			const response = await RNReactNativeLoginWithAmazon.requestAuthorization();
			const { authorizationStatus } = this;
			console.log('[Authorization]', JSON.stringify(response, null, 2));
			onSuccess(response);
		} catch (e) {
			console.log('[Authorization] - error', JSON.stringify(e, null, 2));
			switch (e.status) {
				case authorizationStatus.CANCEL:
					onCancel(response);
					break;
				case authorizationStatus.ERROR:
					onError(response);
					break;
				default:
					break;
			}
		}
	},
	getUserInformation: async ({ onSuccess = () => {}, onError = () => {} }) => {
		try {
			const response = await RNReactNativeLoginWithAmazon.getUserInformation();
			const { userInfoStatus } = this;
			console.log('[UserInformation]', JSON.stringify(response, null, 2));
			onSuccess(response);
		} catch (e) {
			console.log('[UserInformation] - error', JSON.stringify(e, null, 2));
			onError(e);
		}
	},
	signOut: () => {
		// RNReactNativeLoginWithAmazon.signOut();
	},
};

/* Export
================================================================== */
export default AmazonLogin;
