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
 * @interface SchoolUpdateInput
 */
export interface SchoolUpdateInput {
    /**
     * 
     * @type {string}
     * @memberof SchoolUpdateInput
     */
    name?: string;
    /**
     * 
     * @type {string}
     * @memberof SchoolUpdateInput
     */
    editorName?: string;
    /**
     * 
     * @type {string}
     * @memberof SchoolUpdateInput
     */
    editorTitle?: string;
    /**
     * 
     * @type {string}
     * @memberof SchoolUpdateInput
     */
    principalName?: string;
}

/**
 * Check if a given object implements the SchoolUpdateInput interface.
 */
export function instanceOfSchoolUpdateInput(value: object): value is SchoolUpdateInput {
    return true;
}

export function SchoolUpdateInputFromJSON(json: any): SchoolUpdateInput {
    return SchoolUpdateInputFromJSONTyped(json, false);
}

export function SchoolUpdateInputFromJSONTyped(json: any, ignoreDiscriminator: boolean): SchoolUpdateInput {
    if (json == null) {
        return json;
    }
    return {
        
        'name': json['name'] == null ? undefined : json['name'],
        'editorName': json['editorName'] == null ? undefined : json['editorName'],
        'editorTitle': json['editorTitle'] == null ? undefined : json['editorTitle'],
        'principalName': json['principalName'] == null ? undefined : json['principalName'],
    };
}

export function SchoolUpdateInputToJSON(value?: SchoolUpdateInput | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'name': value['name'],
        'editorName': value['editorName'],
        'editorTitle': value['editorTitle'],
        'principalName': value['principalName'],
    };
}

