import type {
	GetDocumentsRequest,
	PageResponseDocumentDocumentErrorCode,
} from '../../api';
import {
	client,
} from '../../api';

export async function getDocuments(
	request: GetDocumentsRequest,
	onComplete?: (response: PageResponseDocumentDocumentErrorCode) => void,
) {
	const response = await client.document.getDocuments(request);
	onComplete?.(response);
	return response;
}

export function responseToContext(
	loading: boolean,
	response?: PageResponseDocumentDocumentErrorCode,
) {
	return {
		loading,
		meta: response?.meta || { limit: 0, offset: 0, total: 0 },
		error: !!response?.errors.length,
		documents: response?.data || [],
	};
}
