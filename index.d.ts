export type AuthorizationStatus =
  | 'authorizationSuccess'
  | 'authorizationError'
  | 'authorizationCancel';

export interface Authorization {
  status: AuthorizationStatus;
  accessToken: string;
}

export interface AuthorizationError {
  status: AuthorizationStatus;
  message: string;
}

export interface UserInformation {
  name: string;
  email: string;
  id: string;
  zipCode: string;
  status: 'userInfoSuccess';
}

export interface UserInformationError {
  status: 'userInfoError';
  statusMessage: string;
}

export interface SignOut {
  status: 'SUCCESS'
}

export interface SignOutError {
  status: 'FAILURE'
}

declare module 'react-native-loginWithAmazon' {
  function requestAuthorization(): Promise<Authorization>;
  function getUserInformation(): Promise<UserInformation>;
  function signOut(): Promise<any>;
}
