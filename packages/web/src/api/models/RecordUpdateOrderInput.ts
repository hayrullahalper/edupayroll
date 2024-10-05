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
 * @interface RecordUpdateOrderInput
 */
export interface RecordUpdateOrderInput {
    /**
     * 
     * @type {string}
     * @memberof RecordUpdateOrderInput
     */
    previousId?: string;
}

/**
 * Check if a given object implements the RecordUpdateOrderInput interface.
 */
export function instanceOfRecordUpdateOrderInput(value: object): value is RecordUpdateOrderInput {
    return true;
}

export function RecordUpdateOrderInputFromJSON(json: any): RecordUpdateOrderInput {
    return RecordUpdateOrderInputFromJSONTyped(json, false);
}

export function RecordUpdateOrderInputFromJSONTyped(json: any, ignoreDiscriminator: boolean): RecordUpdateOrderInput {
    if (json == null) {
        return json;
    }
    return {
        
        'previousId': json['previousId'] == null ? undefined : json['previousId'],
    };
}

export function RecordUpdateOrderInputToJSON(value?: RecordUpdateOrderInput | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'previousId': value['previousId'],
    };
}
