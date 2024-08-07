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
 * @interface ResponseErrorSchoolErrorCode
 */
export interface ResponseErrorSchoolErrorCode {
    /**
     * 
     * @type {string}
     * @memberof ResponseErrorSchoolErrorCode
     */
    code: ResponseErrorSchoolErrorCodeCodeEnum;
    /**
     * 
     * @type {string}
     * @memberof ResponseErrorSchoolErrorCode
     */
    message?: string;
}


/**
 * @export
 */
export const ResponseErrorSchoolErrorCodeCodeEnum = {
    SchoolNotFound: 'SCHOOL_NOT_FOUND'
} as const;
export type ResponseErrorSchoolErrorCodeCodeEnum = typeof ResponseErrorSchoolErrorCodeCodeEnum[keyof typeof ResponseErrorSchoolErrorCodeCodeEnum];


/**
 * Check if a given object implements the ResponseErrorSchoolErrorCode interface.
 */
export function instanceOfResponseErrorSchoolErrorCode(value: object): value is ResponseErrorSchoolErrorCode {
    if (!('code' in value) || value['code'] === undefined) return false;
    return true;
}

export function ResponseErrorSchoolErrorCodeFromJSON(json: any): ResponseErrorSchoolErrorCode {
    return ResponseErrorSchoolErrorCodeFromJSONTyped(json, false);
}

export function ResponseErrorSchoolErrorCodeFromJSONTyped(json: any, ignoreDiscriminator: boolean): ResponseErrorSchoolErrorCode {
    if (json == null) {
        return json;
    }
    return {
        
        'code': json['code'],
        'message': json['message'] == null ? undefined : json['message'],
    };
}

export function ResponseErrorSchoolErrorCodeToJSON(value?: ResponseErrorSchoolErrorCode | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'code': value['code'],
        'message': value['message'],
    };
}

