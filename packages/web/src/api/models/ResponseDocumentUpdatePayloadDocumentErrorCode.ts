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
import type { ResponseErrorDocumentErrorCode } from './ResponseErrorDocumentErrorCode';
import {
    ResponseErrorDocumentErrorCodeFromJSON,
    ResponseErrorDocumentErrorCodeFromJSONTyped,
    ResponseErrorDocumentErrorCodeToJSON,
} from './ResponseErrorDocumentErrorCode';
import type { DocumentUpdatePayload } from './DocumentUpdatePayload';
import {
    DocumentUpdatePayloadFromJSON,
    DocumentUpdatePayloadFromJSONTyped,
    DocumentUpdatePayloadToJSON,
} from './DocumentUpdatePayload';

/**
 * 
 * @export
 * @interface ResponseDocumentUpdatePayloadDocumentErrorCode
 */
export interface ResponseDocumentUpdatePayloadDocumentErrorCode {
    /**
     * 
     * @type {DocumentUpdatePayload}
     * @memberof ResponseDocumentUpdatePayloadDocumentErrorCode
     */
    data?: DocumentUpdatePayload;
    /**
     * 
     * @type {Array<ResponseErrorDocumentErrorCode>}
     * @memberof ResponseDocumentUpdatePayloadDocumentErrorCode
     */
    errors: Array<ResponseErrorDocumentErrorCode>;
}

/**
 * Check if a given object implements the ResponseDocumentUpdatePayloadDocumentErrorCode interface.
 */
export function instanceOfResponseDocumentUpdatePayloadDocumentErrorCode(value: object): value is ResponseDocumentUpdatePayloadDocumentErrorCode {
    if (!('errors' in value) || value['errors'] === undefined) return false;
    return true;
}

export function ResponseDocumentUpdatePayloadDocumentErrorCodeFromJSON(json: any): ResponseDocumentUpdatePayloadDocumentErrorCode {
    return ResponseDocumentUpdatePayloadDocumentErrorCodeFromJSONTyped(json, false);
}

export function ResponseDocumentUpdatePayloadDocumentErrorCodeFromJSONTyped(json: any, ignoreDiscriminator: boolean): ResponseDocumentUpdatePayloadDocumentErrorCode {
    if (json == null) {
        return json;
    }
    return {
        
        'data': json['data'] == null ? undefined : DocumentUpdatePayloadFromJSON(json['data']),
        'errors': ((json['errors'] as Array<any>).map(ResponseErrorDocumentErrorCodeFromJSON)),
    };
}

export function ResponseDocumentUpdatePayloadDocumentErrorCodeToJSON(value?: ResponseDocumentUpdatePayloadDocumentErrorCode | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'data': DocumentUpdatePayloadToJSON(value['data']),
        'errors': ((value['errors'] as Array<any>).map(ResponseErrorDocumentErrorCodeToJSON)),
    };
}

