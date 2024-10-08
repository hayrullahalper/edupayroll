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
 * @interface ExportDeletePayload
 */
export interface ExportDeletePayload {
    /**
     * 
     * @type {boolean}
     * @memberof ExportDeletePayload
     */
    success: boolean;
}

/**
 * Check if a given object implements the ExportDeletePayload interface.
 */
export function instanceOfExportDeletePayload(value: object): value is ExportDeletePayload {
    if (!('success' in value) || value['success'] === undefined) return false;
    return true;
}

export function ExportDeletePayloadFromJSON(json: any): ExportDeletePayload {
    return ExportDeletePayloadFromJSONTyped(json, false);
}

export function ExportDeletePayloadFromJSONTyped(json: any, ignoreDiscriminator: boolean): ExportDeletePayload {
    if (json == null) {
        return json;
    }
    return {
        
        'success': json['success'],
    };
}

export function ExportDeletePayloadToJSON(value?: ExportDeletePayload | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'success': value['success'],
    };
}

