/*
    Copyright 2020-2024. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

declare module "@hmscore/react-native-hms-account" {

    export const HMSAccount = {
        signIn(request: SignInData): Promise<AuthHuaweiId>;,
        signOut(): Promise<boolean>;,
        cancelAuthorization(): Promise<boolean>;,
        silentSignIn(request: SilentSignInData): Promise<AuthHuaweiId>;,
        enableLogger(): Promise<void>;,
        disableLogger(): Promise<void>;,
    }

    export const HMSHuaweiIdAuthManager = {
        getAuthResult(): Promise<AuthHuaweiId>;,
        getAuthResultWithScopes(request: AuthScopeData): Promise<AuthHuaweiId>;,
        addAuthScopes(request: AuthScopeData): Promise<boolean>;,
        containScopes(request: ContainScopesData): Promise<boolean>;,
    }

    export const HMSAccountAuthService = {
        signIn(request: SignInData): Promise<AuthAccount>;,
        signOut(): Promise<boolean>;,
        silentSignIn(request: SilentSignInData): Promise<AuthAccount>;,
        cancelAuthorization(): Promise<boolean>;,
        getChannel(): Promise<AccountIcon>;,
        getIndependentSignInIntent(accessToken: string): Promise<AuthAccount>;,
    }

    export const HMSAccountAuthManager = {
        getAuthResult(): Promise<AuthAccount>;,
        getAuthResultWithScopes(request: AuthScopeData): Promise<AuthAccount>;,
        addAuthScopes(request: AuthScopeData): Promise<boolean>;,
        containScopes(request: ContainScopesData): Promise<boolean>;,
    }

    export const HMSHuaweiIdAuthTool = {
        deleteAuthInfo(request: AccessTokenData): Promise<boolean>;,
        requestUnionId(request: AccountData): Promise<string>;,
        requestAccessToken(request: RequestAccessTokenData): Promise<string>;,
    }

    export const HMSNetworkTool = {
        buildNetworkCookie(request: CookieData): Promise<string>;,
        buildNetworkUrl(request: UrlData): Promise<string>;,
    }


    export interface SignInData {
        huaweiIdAuthParams: HMSAuthParamConstants[];
        authRequestOption?: HMSAuthRequestOptionConstants[];
        authScopeList?: HMSAuthScopeListConstants[];
    }

    export interface SilentSignInData {
        huaweiIdAuthParams: HMSAuthParamConstants[];
    }

    export interface AuthScopeData {
        authScopeList: HMSAuthParamConstants[];
    }

    export interface ContainScopesData {
        authAccount: AuthBuilder;
        authScopeList: HMSAuthScopeListConstants[];
    }

    export interface AuthBuilder {
        openId?: string;
        uid?: string;
        displayName?: string;
        photoUrl?: string;
        accessToken?: string;
        serviceCountryCode?: string;
        status?: number;
        gender?: number;
        authScopeList: HMSAuthScopeListConstants[];
        serverAuthCode?: string;
        unionId?: string;
        countryCode?: string;
    }

    export interface AuthHuaweiId {
        /** @deprecated accessToken*/
        accessToken: string;
        account: HuaweiAccount; 
        displayName: string;
        email: string;
        familyName: string;
        givenName: string;
        authorizedScopes: HMSAuthScopeListConstants[];
        idToken: string;
        avatarUriString: string;
        authorizationCode: string;
        unionId: string;
        openId: string;
        carrierId: number;
    }

    export interface AuthAccount {
        /** @deprecated accessToken*/
        accessToken: string;
        account: HuaweiAccount; 
        displayName: string;
        serviceCountryCode: string;
        gender: number;
        email: string;
        familyName: string;
        givenName: string;
        authorizedScopes: HMSAuthScopeListConstants[];
        idToken: string;
        avatarUriString: string;
        authorizationCode: string;
        unionId: string;
        openId: string;
        accountFlag: number;
        carrierId: number;
    }

    export interface HuaweiAccount {
        name: string;
        type: string;
    }

    export interface AccountIcon {
        icon: string;
        description: string;
    }
    
    export interface AccessTokenData {
        accessToken: string;
    }

    export interface AccountData {
        huaweiAccountName: string;
    }

    export interface RequestAccessTokenData {
        authScopeList: HMSAuthScopeListConstants[];
        huaweiAccount: HuaweiAccount;
    }

    export interface CookieData {
        cookieName: string;
        cookieValue?: string;
        domain?: string;
        path?: string;
        isHttpOnly?: boolean;
        isSecure?: boolean;
        maxAge?: number;
    }

    export interface UrlData {
        isUseHttps?: boolean;
        domain: string;
    }

    export enum HMSAuthParamConstants  {
        DEFAULT_AUTH_REQUEST_PARAM = "DEFAULT_AUTH_REQUEST_PARAM",
        DEFAULT_AUTH_REQUEST_PARAM_GAME = "DEFAULT_AUTH_REQUEST_PARAM_GAME",
    }

    export enum HMSAuthRequestOptionConstants {
      ID_TOKEN = "idToken",
      ID = "id",
      ACCESS_TOKEN = "accessToken",
      AUTHORIZATION_CODE = "authorizationCode",
      EMAIL = "email",
      PROFILE = "profile",
      MOBILENUMBER = "mobileNumber",
      UID = "uid",
      CARRIERID = "carrierId",
    }

    export  enum HMSAuthScopeListConstants  {
        OPENID = "openid",
        EMAIL = "email",
        PROFILE = "profile",
        GAME = "https://www.huawei.com/auth/games",
    }

} 