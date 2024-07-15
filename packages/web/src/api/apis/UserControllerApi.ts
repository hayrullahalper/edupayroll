// @ts-nocheck

/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import * as runtime from '../runtime';
import type {
  ResponseUserChangePasswordPayloadAuthErrorCode,
  ResponseUserUserErrorCode,
  UserChangePasswordInput,
} from '../models/index';
import {
    ResponseUserChangePasswordPayloadAuthErrorCodeFromJSON,
    ResponseUserChangePasswordPayloadAuthErrorCodeToJSON,
    ResponseUserUserErrorCodeFromJSON,
    ResponseUserUserErrorCodeToJSON,
    UserChangePasswordInputFromJSON,
    UserChangePasswordInputToJSON,
} from '../models/index';

export interface ChangePasswordRequest {
    userChangePasswordInput: UserChangePasswordInput;
}

/**
 * 
 */
export class UserControllerApi extends runtime.BaseAPI {

    /**
     */
    async changePasswordRaw(requestParameters: ChangePasswordRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<ResponseUserChangePasswordPayloadAuthErrorCode>> {
        if (requestParameters['userChangePasswordInput'] == null) {
            throw new runtime.RequiredError(
                'userChangePasswordInput',
                'Required parameter "userChangePasswordInput" was null or undefined when calling changePassword().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/user/change-password`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: UserChangePasswordInputToJSON(requestParameters['userChangePasswordInput']),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => ResponseUserChangePasswordPayloadAuthErrorCodeFromJSON(jsonValue));
    }

    /**
     */
    async changePassword(requestParameters: ChangePasswordRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<ResponseUserChangePasswordPayloadAuthErrorCode> {
        const response = await this.changePasswordRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async getUserRaw(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<ResponseUserUserErrorCode>> {
        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/user`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => ResponseUserUserErrorCodeFromJSON(jsonValue));
    }

    /**
     */
    async getUser(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<ResponseUserUserErrorCode> {
        const response = await this.getUserRaw(initOverrides);
        return await response.value();
    }

}