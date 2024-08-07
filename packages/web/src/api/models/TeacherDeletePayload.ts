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
 * @interface TeacherDeletePayload
 */
export interface TeacherDeletePayload {
    /**
     * 
     * @type {boolean}
     * @memberof TeacherDeletePayload
     */
    success: boolean;
}

/**
 * Check if a given object implements the TeacherDeletePayload interface.
 */
export function instanceOfTeacherDeletePayload(value: object): value is TeacherDeletePayload {
    if (!('success' in value) || value['success'] === undefined) return false;
    return true;
}

export function TeacherDeletePayloadFromJSON(json: any): TeacherDeletePayload {
    return TeacherDeletePayloadFromJSONTyped(json, false);
}

export function TeacherDeletePayloadFromJSONTyped(json: any, ignoreDiscriminator: boolean): TeacherDeletePayload {
    if (json == null) {
        return json;
    }
    return {
        
        'success': json['success'],
    };
}

export function TeacherDeletePayloadToJSON(value?: TeacherDeletePayload | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'success': value['success'],
    };
}

