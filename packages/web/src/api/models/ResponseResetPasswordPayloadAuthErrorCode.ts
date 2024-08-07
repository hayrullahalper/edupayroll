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
import type { ResetPasswordPayload } from './ResetPasswordPayload';
import {
    ResetPasswordPayloadFromJSON,
    ResetPasswordPayloadFromJSONTyped,
    ResetPasswordPayloadToJSON,
} from './ResetPasswordPayload';
import type { ResponseErrorAuthErrorCode } from './ResponseErrorAuthErrorCode';
import {
    ResponseErrorAuthErrorCodeFromJSON,
    ResponseErrorAuthErrorCodeFromJSONTyped,
    ResponseErrorAuthErrorCodeToJSON,
} from './ResponseErrorAuthErrorCode';

/**
 * 
 * @export
 * @interface ResponseResetPasswordPayloadAuthErrorCode
 */
export interface ResponseResetPasswordPayloadAuthErrorCode {
    /**
     * 
     * @type {ResetPasswordPayload}
     * @memberof ResponseResetPasswordPayloadAuthErrorCode
     */
    node?: ResetPasswordPayload;
    /**
     * 
     * @type {Array<ResponseErrorAuthErrorCode>}
     * @memberof ResponseResetPasswordPayloadAuthErrorCode
     */
    errors: Array<ResponseErrorAuthErrorCode>;
}

/**
 * Check if a given object implements the ResponseResetPasswordPayloadAuthErrorCode interface.
 */
export function instanceOfResponseResetPasswordPayloadAuthErrorCode(value: object): value is ResponseResetPasswordPayloadAuthErrorCode {
    if (!('errors' in value) || value['errors'] === undefined) return false;
    return true;
}

export function ResponseResetPasswordPayloadAuthErrorCodeFromJSON(json: any): ResponseResetPasswordPayloadAuthErrorCode {
    return ResponseResetPasswordPayloadAuthErrorCodeFromJSONTyped(json, false);
}

export function ResponseResetPasswordPayloadAuthErrorCodeFromJSONTyped(json: any, ignoreDiscriminator: boolean): ResponseResetPasswordPayloadAuthErrorCode {
    if (json == null) {
        return json;
    }
    return {
        
        'node': json['node'] == null ? undefined : ResetPasswordPayloadFromJSON(json['node']),
        'errors': ((json['errors'] as Array<any>).map(ResponseErrorAuthErrorCodeFromJSON)),
    };
}

export function ResponseResetPasswordPayloadAuthErrorCodeToJSON(value?: ResponseResetPasswordPayloadAuthErrorCode | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'node': ResetPasswordPayloadToJSON(value['node']),
        'errors': ((value['errors'] as Array<any>).map(ResponseErrorAuthErrorCodeToJSON)),
    };
}

