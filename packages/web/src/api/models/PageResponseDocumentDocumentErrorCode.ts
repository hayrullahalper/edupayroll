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
import type { ResponseErrorDocumentErrorCode } from './ResponseErrorDocumentErrorCode';
import {
    ResponseErrorDocumentErrorCodeFromJSON,
    ResponseErrorDocumentErrorCodeFromJSONTyped,
    ResponseErrorDocumentErrorCodeToJSON,
} from './ResponseErrorDocumentErrorCode';
import type { Document } from './Document';
import {
    DocumentFromJSON,
    DocumentFromJSONTyped,
    DocumentToJSON,
} from './Document';

/**
 * 
 * @export
 * @interface PageResponseDocumentDocumentErrorCode
 */
export interface PageResponseDocumentDocumentErrorCode {
    /**
     * 
     * @type {Array<Document>}
     * @memberof PageResponseDocumentDocumentErrorCode
     */
    data?: Array<Document>;
    /**
     * 
     * @type {PageMeta}
     * @memberof PageResponseDocumentDocumentErrorCode
     */
    meta?: PageMeta;
    /**
     * 
     * @type {Array<ResponseErrorDocumentErrorCode>}
     * @memberof PageResponseDocumentDocumentErrorCode
     */
    errors: Array<ResponseErrorDocumentErrorCode>;
}

/**
 * Check if a given object implements the PageResponseDocumentDocumentErrorCode interface.
 */
export function instanceOfPageResponseDocumentDocumentErrorCode(value: object): value is PageResponseDocumentDocumentErrorCode {
    if (!('errors' in value) || value['errors'] === undefined) return false;
    return true;
}

export function PageResponseDocumentDocumentErrorCodeFromJSON(json: any): PageResponseDocumentDocumentErrorCode {
    return PageResponseDocumentDocumentErrorCodeFromJSONTyped(json, false);
}

export function PageResponseDocumentDocumentErrorCodeFromJSONTyped(json: any, ignoreDiscriminator: boolean): PageResponseDocumentDocumentErrorCode {
    if (json == null) {
        return json;
    }
    return {
        
        'data': json['data'] == null ? undefined : ((json['data'] as Array<any>).map(DocumentFromJSON)),
        'meta': json['meta'] == null ? undefined : PageMetaFromJSON(json['meta']),
        'errors': ((json['errors'] as Array<any>).map(ResponseErrorDocumentErrorCodeFromJSON)),
    };
}

export function PageResponseDocumentDocumentErrorCodeToJSON(value?: PageResponseDocumentDocumentErrorCode | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'data': value['data'] == null ? undefined : ((value['data'] as Array<any>).map(DocumentToJSON)),
        'meta': PageMetaToJSON(value['meta']),
        'errors': ((value['errors'] as Array<any>).map(ResponseErrorDocumentErrorCodeToJSON)),
    };
}

