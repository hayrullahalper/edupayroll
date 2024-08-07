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
import type { Teacher } from './Teacher';
import {
    TeacherFromJSON,
    TeacherFromJSONTyped,
    TeacherToJSON,
} from './Teacher';
import type { ResponseErrorTeacherErrorCode } from './ResponseErrorTeacherErrorCode';
import {
    ResponseErrorTeacherErrorCodeFromJSON,
    ResponseErrorTeacherErrorCodeFromJSONTyped,
    ResponseErrorTeacherErrorCodeToJSON,
} from './ResponseErrorTeacherErrorCode';

/**
 * 
 * @export
 * @interface ResponseTeacherTeacherErrorCode
 */
export interface ResponseTeacherTeacherErrorCode {
    /**
     * 
     * @type {Teacher}
     * @memberof ResponseTeacherTeacherErrorCode
     */
    node?: Teacher;
    /**
     * 
     * @type {Array<ResponseErrorTeacherErrorCode>}
     * @memberof ResponseTeacherTeacherErrorCode
     */
    errors: Array<ResponseErrorTeacherErrorCode>;
}

/**
 * Check if a given object implements the ResponseTeacherTeacherErrorCode interface.
 */
export function instanceOfResponseTeacherTeacherErrorCode(value: object): value is ResponseTeacherTeacherErrorCode {
    if (!('errors' in value) || value['errors'] === undefined) return false;
    return true;
}

export function ResponseTeacherTeacherErrorCodeFromJSON(json: any): ResponseTeacherTeacherErrorCode {
    return ResponseTeacherTeacherErrorCodeFromJSONTyped(json, false);
}

export function ResponseTeacherTeacherErrorCodeFromJSONTyped(json: any, ignoreDiscriminator: boolean): ResponseTeacherTeacherErrorCode {
    if (json == null) {
        return json;
    }
    return {
        
        'node': json['node'] == null ? undefined : TeacherFromJSON(json['node']),
        'errors': ((json['errors'] as Array<any>).map(ResponseErrorTeacherErrorCodeFromJSON)),
    };
}

export function ResponseTeacherTeacherErrorCodeToJSON(value?: ResponseTeacherTeacherErrorCode | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'node': TeacherToJSON(value['node']),
        'errors': ((value['errors'] as Array<any>).map(ResponseErrorTeacherErrorCodeToJSON)),
    };
}

