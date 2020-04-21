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
  status: 'SUCCESS';
}

export interface SignOutError {
  status: 'FAILURE';
}

export function requestAuthorization(): Promise<Authorization>;
export function getUserInformation(): Promise<UserInformation>;
export function signOut(): Promise<any>;
