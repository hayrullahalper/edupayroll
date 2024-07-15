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

import { mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface UserChangePasswordInput
 */
export interface UserChangePasswordInput {
    /**
     * 
     * @type {string}
     * @memberof UserChangePasswordInput
     */
    currentPassword: string;
    /**
     * 
     * @type {string}
     * @memberof UserChangePasswordInput
     */
    newPassword: string;
}

/**
 * Check if a given object implements the UserChangePasswordInput interface.
 */
export function instanceOfUserChangePasswordInput(value: object): value is UserChangePasswordInput {
    if (!('currentPassword' in value) || value['currentPassword'] === undefined) return false;
    if (!('newPassword' in value) || value['newPassword'] === undefined) return false;
    return true;
}

export function UserChangePasswordInputFromJSON(json: any): UserChangePasswordInput {
    return UserChangePasswordInputFromJSONTyped(json, false);
}

export function UserChangePasswordInputFromJSONTyped(json: any, ignoreDiscriminator: boolean): UserChangePasswordInput {
    if (json == null) {
        return json;
    }
    return {
        
        'currentPassword': json['currentPassword'],
        'newPassword': json['newPassword'],
    };
}

export function UserChangePasswordInputToJSON(value?: UserChangePasswordInput | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'currentPassword': value['currentPassword'],
        'newPassword': value['newPassword'],
    };
}
