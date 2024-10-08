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
import type { DocumentUpdateInputTime } from './DocumentUpdateInputTime';
import {
    DocumentUpdateInputTimeFromJSON,
    DocumentUpdateInputTimeFromJSONTyped,
    DocumentUpdateInputTimeToJSON,
} from './DocumentUpdateInputTime';

/**
 * 
 * @export
 * @interface DocumentUpdateInput
 */
export interface DocumentUpdateInput {
    /**
     * 
     * @type {string}
     * @memberof DocumentUpdateInput
     */
    name?: string;
    /**
     * 
     * @type {DocumentUpdateInputTime}
     * @memberof DocumentUpdateInput
     */
    time?: DocumentUpdateInputTime;
}

/**
 * Check if a given object implements the DocumentUpdateInput interface.
 */
export function instanceOfDocumentUpdateInput(value: object): value is DocumentUpdateInput {
    return true;
}

export function DocumentUpdateInputFromJSON(json: any): DocumentUpdateInput {
    return DocumentUpdateInputFromJSONTyped(json, false);
}

export function DocumentUpdateInputFromJSONTyped(json: any, ignoreDiscriminator: boolean): DocumentUpdateInput {
    if (json == null) {
        return json;
    }
    return {
        
        'name': json['name'] == null ? undefined : json['name'],
        'time': json['time'] == null ? undefined : DocumentUpdateInputTimeFromJSON(json['time']),
    };
}

export function DocumentUpdateInputToJSON(value?: DocumentUpdateInput | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'name': value['name'],
        'time': DocumentUpdateInputTimeToJSON(value['time']),
    };
}

