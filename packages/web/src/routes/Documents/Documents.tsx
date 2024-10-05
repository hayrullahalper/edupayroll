import type {
	GetDocumentsRequest,
	PageResponseDocumentDocumentErrorCode,
} from '../../api';
import { Stack } from '@mantine/core';
import { useState } from 'react';
import { DocumentsProvider } from '../../contexts/documents';
import DocumentsHeader from './DocumentsHeader';
import DocumentsTable from './DocumentsTable';

const DEFAULT_LIMIT = 10;

export default function Documents() {
	const [request, setRequest] = useState<GetDocumentsRequest>({
		limit: DEFAULT_LIMIT,
		offset: 0,
	});

	const handlePageChange = (page: number) => {
		setRequest(prev => ({
			...prev,
			offset: (page - 1) * DEFAULT_LIMIT,
		}));
	};

	const handleSearchChange = (search: string) => {
		setRequest(prev => ({
			...prev,
			query: search.trim() || undefined,
		}));
	};

	const handleComplete = ({
		meta,
		data,
	}: PageResponseDocumentDocumentErrorCode) => {
		if (!!data && !!meta && data?.length === 0 && meta?.total > 0) {
			const offset = (Math.floor(meta.total / meta.limit) - 1) * meta.limit;
			setRequest(prev => ({ ...prev, offset: offset < 0 ? 0 : offset }));
		}
	};

	return (
		<DocumentsProvider
			{...request}
			onRemove={() => { /* todo: implement this */ }}
			onComplete={handleComplete}
		>
			<Stack gap="xs" py="xs" pr="xs" h="100%">
				<DocumentsHeader />
				<DocumentsTable
					{...request}
					onPageChange={handlePageChange}
					onSearchChange={handleSearchChange}
				/>
			</Stack>
		</DocumentsProvider>
	);
}
