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
 * @interface RecordCreateDTO
 */
export interface RecordCreateDTO {
    /**
     * 
     * @type {string}
     * @memberof RecordCreateDTO
     */
    type?: RecordCreateDTOTypeEnum;
    /**
     * 
     * @type {string}
     * @memberof RecordCreateDTO
     */
    information?: string;
    /**
     * 
     * @type {string}
     * @memberof RecordCreateDTO
     */
    teacher?: string;
}


/**
 * @export
 */
export const RecordCreateDTOTypeEnum = {
    Course101: 'Course101',
    Course102: 'Course102',
    Course103: 'Course103',
    Course104: 'Course104',
    Course106: 'Course106',
    Course107: 'Course107',
    Course108: 'Course108',
    Course109: 'Course109',
    Course110: 'Course110',
    Course111: 'Course111',
    Course112: 'Course112',
    Course113: 'Course113',
    Course114: 'Course114',
    Course115: 'Course115',
    Course116: 'Course116',
    Course117: 'Course117',
    Course118: 'Course118',
    Course119: 'Course119',
    Course1070: 'Course1070',
    Course10700: 'Course10700',
    Course107000: 'Course107000',
    Course1070000: 'Course1070000',
    Course121: 'Course121',
    Course122: 'Course122',
    Course123: 'Course123',
    Course212: 'Course212'
} as const;
export type RecordCreateDTOTypeEnum = typeof RecordCreateDTOTypeEnum[keyof typeof RecordCreateDTOTypeEnum];


/**
 * Check if a given object implements the RecordCreateDTO interface.
 */
export function instanceOfRecordCreateDTO(value: object): value is RecordCreateDTO {
    return true;
}

export function RecordCreateDTOFromJSON(json: any): RecordCreateDTO {
    return RecordCreateDTOFromJSONTyped(json, false);
}

export function RecordCreateDTOFromJSONTyped(json: any, ignoreDiscriminator: boolean): RecordCreateDTO {
    if (json == null) {
        return json;
    }
    return {
        
        'type': json['type'] == null ? undefined : json['type'],
        'information': json['information'] == null ? undefined : json['information'],
        'teacher': json['teacher'] == null ? undefined : json['teacher'],
    };
}

export function RecordCreateDTOToJSON(value?: RecordCreateDTO | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'type': value['type'],
        'information': value['information'],
        'teacher': value['teacher'],
    };
}
