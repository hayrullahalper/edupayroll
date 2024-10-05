import type { PropsWithChildren } from 'react';
import type {
	GetDocumentsRequest,
	PageResponseDocumentDocumentErrorCode,
} from '../../api';

import { useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

import DocumentDeleteModal from './DocumentDeleteModal';
import useDocumentDelete from './DocumentDeleteModal/useDocumentDelete';
import DocumentsContext from './DocumentsContext';
import { getDocuments, responseToContext } from './DocumentsProvider.utils';

interface DocumentsProviderProps extends PropsWithChildren<GetDocumentsRequest> {
	onRemove?: (id: string) => void;
	onComplete?: (response: PageResponseDocumentDocumentErrorCode) => void;
}

export default function DocumentsProvider({
	children,
	onComplete,
	...request
}: DocumentsProviderProps) {
	const { data, isLoading } = useQuery({
		queryKey: ['documents', request],
		queryFn: () => getDocuments(request, onComplete),
	});

	// const {
	// 	create,
	// 	opened: createOpened,
	// 	...createModalProps
	// } = useTeacherCreate();
	//
	// const {
	// 	update,
	// 	target: updateTarget,
	// 	opened: updateOpened,
	// 	...updateModalProps
	// } = useTeacherUpdate();

	const {
		remove,
		target: deleteTarget,
		opened: deleteOpened,
		...deleteModalProps
	} = useDocumentDelete();

	// const {
	// 	bulkDelete,
	// 	opened: bulkDeleteOpened,
	// 	...bulkDeleteModalProps
	// } = useTeacherBulkDelete();

	const contexValue = useMemo(
		() => ({
			...responseToContext(isLoading, data),
			remove,
			create: () => {},
			update: () => {},
		}),
		[data, isLoading, remove],
	);

	return (
		<>
			<DocumentsContext.Provider value={contexValue}>
				{children}
			</DocumentsContext.Provider>

			{/* {createOpened && <TeacherCreateModal {...createModalProps} />} */}
			{/* {bulkDeleteOpened && <TeacherBulkDeleteModal {...bulkDeleteModalProps} />} */}

			{/* {updateOpened && !!updateTarget && ( */}
			{/*	<TeacherUpdateModal teacher={updateTarget} {...updateModalProps} /> */}
			{/* )} */}

			{deleteOpened && !!deleteTarget && (
				<DocumentDeleteModal document={deleteTarget} {...deleteModalProps} />
			)}
		</>
	);
}
