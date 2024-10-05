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
import type { ResponseErrorExportErrorCode } from './ResponseErrorExportErrorCode';
import {
    ResponseErrorExportErrorCodeFromJSON,
    ResponseErrorExportErrorCodeFromJSONTyped,
    ResponseErrorExportErrorCodeToJSON,
} from './ResponseErrorExportErrorCode';
import type { Export } from './Export';
import {
    ExportFromJSON,
    ExportFromJSONTyped,
    ExportToJSON,
} from './Export';

/**
 * 
 * @export
 * @interface PageResponseExportExportErrorCode
 */
export interface PageResponseExportExportErrorCode {
    /**
     * 
     * @type {Array<Export>}
     * @memberof PageResponseExportExportErrorCode
     */
    data?: Array<Export>;
    /**
     * 
     * @type {PageMeta}
     * @memberof PageResponseExportExportErrorCode
     */
    meta?: PageMeta;
    /**
     * 
     * @type {Array<ResponseErrorExportErrorCode>}
     * @memberof PageResponseExportExportErrorCode
     */
    errors: Array<ResponseErrorExportErrorCode>;
}

/**
 * Check if a given object implements the PageResponseExportExportErrorCode interface.
 */
export function instanceOfPageResponseExportExportErrorCode(value: object): value is PageResponseExportExportErrorCode {
    if (!('errors' in value) || value['errors'] === undefined) return false;
    return true;
}

export function PageResponseExportExportErrorCodeFromJSON(json: any): PageResponseExportExportErrorCode {
    return PageResponseExportExportErrorCodeFromJSONTyped(json, false);
}

export function PageResponseExportExportErrorCodeFromJSONTyped(json: any, ignoreDiscriminator: boolean): PageResponseExportExportErrorCode {
    if (json == null) {
        return json;
    }
    return {
        
        'data': json['data'] == null ? undefined : ((json['data'] as Array<any>).map(ExportFromJSON)),
        'meta': json['meta'] == null ? undefined : PageMetaFromJSON(json['meta']),
        'errors': ((json['errors'] as Array<any>).map(ResponseErrorExportErrorCodeFromJSON)),
    };
}

export function PageResponseExportExportErrorCodeToJSON(value?: PageResponseExportExportErrorCode | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'data': value['data'] == null ? undefined : ((value['data'] as Array<any>).map(ExportToJSON)),
        'meta': PageMetaToJSON(value['meta']),
        'errors': ((value['errors'] as Array<any>).map(ResponseErrorExportErrorCodeToJSON)),
    };
}
