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
 * @interface DocumentDeletePayload
 */
export interface DocumentDeletePayload {
    /**
     * 
     * @type {boolean}
     * @memberof DocumentDeletePayload
     */
    success: boolean;
}

/**
 * Check if a given object implements the DocumentDeletePayload interface.
 */
export function instanceOfDocumentDeletePayload(value: object): value is DocumentDeletePayload {
    if (!('success' in value) || value['success'] === undefined) return false;
    return true;
}

export function DocumentDeletePayloadFromJSON(json: any): DocumentDeletePayload {
    return DocumentDeletePayloadFromJSONTyped(json, false);
}

export function DocumentDeletePayloadFromJSONTyped(json: any, ignoreDiscriminator: boolean): DocumentDeletePayload {
    if (json == null) {
        return json;
    }
    return {
        
        'success': json['success'],
    };
}

export function DocumentDeletePayloadToJSON(value?: DocumentDeletePayload | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'success': value['success'],
    };
}

