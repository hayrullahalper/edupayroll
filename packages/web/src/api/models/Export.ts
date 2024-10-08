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
 * @interface Export
 */
export interface Export {
    /**
     * 
     * @type {string}
     * @memberof Export
     */
    id: string;
    /**
     * 
     * @type {string}
     * @memberof Export
     */
    name: string;
    /**
     * 
     * @type {string}
     * @memberof Export
     */
    status: ExportStatusEnum;
    /**
     * 
     * @type {Date}
     * @memberof Export
     */
    createdAt: Date;
    /**
     * 
     * @type {Date}
     * @memberof Export
     */
    updatedAt: Date;
}


/**
 * @export
 */
export const ExportStatusEnum = {
    Failed: 'FAILED',
    Pending: 'PENDING',
    Completed: 'COMPLETED',
    InProgress: 'IN_PROGRESS'
} as const;
export type ExportStatusEnum = typeof ExportStatusEnum[keyof typeof ExportStatusEnum];


/**
 * Check if a given object implements the Export interface.
 */
export function instanceOfExport(value: object): value is Export {
    if (!('id' in value) || value['id'] === undefined) return false;
    if (!('name' in value) || value['name'] === undefined) return false;
    if (!('status' in value) || value['status'] === undefined) return false;
    if (!('createdAt' in value) || value['createdAt'] === undefined) return false;
    if (!('updatedAt' in value) || value['updatedAt'] === undefined) return false;
    return true;
}

export function ExportFromJSON(json: any): Export {
    return ExportFromJSONTyped(json, false);
}

export function ExportFromJSONTyped(json: any, ignoreDiscriminator: boolean): Export {
    if (json == null) {
        return json;
    }
    return {
        
        'id': json['id'],
        'name': json['name'],
        'status': json['status'],
        'createdAt': (new Date(json['createdAt'])),
        'updatedAt': (new Date(json['updatedAt'])),
    };
}

export function ExportToJSON(value?: Export | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'id': value['id'],
        'name': value['name'],
        'status': value['status'],
        'createdAt': ((value['createdAt']).toISOString()),
        'updatedAt': ((value['updatedAt']).toISOString()),
    };
}

