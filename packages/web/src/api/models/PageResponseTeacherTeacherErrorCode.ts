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
import type { PageMeta } from './PageMeta';
import {
    PageMetaFromJSON,
    PageMetaFromJSONTyped,
    PageMetaToJSON,
} from './PageMeta';
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
 * @interface PageResponseTeacherTeacherErrorCode
 */
export interface PageResponseTeacherTeacherErrorCode {
    /**
     * 
     * @type {Array<Teacher>}
     * @memberof PageResponseTeacherTeacherErrorCode
     */
    data?: Array<Teacher>;
    /**
     * 
     * @type {PageMeta}
     * @memberof PageResponseTeacherTeacherErrorCode
     */
    meta?: PageMeta;
    /**
     * 
     * @type {Array<ResponseErrorTeacherErrorCode>}
     * @memberof PageResponseTeacherTeacherErrorCode
     */
    errors: Array<ResponseErrorTeacherErrorCode>;
}

/**
 * Check if a given object implements the PageResponseTeacherTeacherErrorCode interface.
 */
export function instanceOfPageResponseTeacherTeacherErrorCode(value: object): value is PageResponseTeacherTeacherErrorCode {
    if (!('errors' in value) || value['errors'] === undefined) return false;
    return true;
}

export function PageResponseTeacherTeacherErrorCodeFromJSON(json: any): PageResponseTeacherTeacherErrorCode {
    return PageResponseTeacherTeacherErrorCodeFromJSONTyped(json, false);
}

export function PageResponseTeacherTeacherErrorCodeFromJSONTyped(json: any, ignoreDiscriminator: boolean): PageResponseTeacherTeacherErrorCode {
    if (json == null) {
        return json;
    }
    return {
        
        'data': json['data'] == null ? undefined : ((json['data'] as Array<any>).map(TeacherFromJSON)),
        'meta': json['meta'] == null ? undefined : PageMetaFromJSON(json['meta']),
        'errors': ((json['errors'] as Array<any>).map(ResponseErrorTeacherErrorCodeFromJSON)),
    };
}

export function PageResponseTeacherTeacherErrorCodeToJSON(value?: PageResponseTeacherTeacherErrorCode | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'data': value['data'] == null ? undefined : ((value['data'] as Array<any>).map(TeacherToJSON)),
        'meta': PageMetaToJSON(value['meta']),
        'errors': ((value['errors'] as Array<any>).map(ResponseErrorTeacherErrorCodeToJSON)),
    };
}

