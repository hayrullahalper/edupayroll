import { useQuery } from '@tanstack/react-query';
import { PropsWithChildren, useMemo } from 'react';

import {
	GetTeachersRequest,
	PageResponseTeacherTeacherErrorCode,
} from '../../api';
import { DataTableSelectionType } from '../../components/DataTable';

import TeacherBulkDeleteModal, {
	useTeacherBulkDelete,
} from './TeacherBulkDeleteModal';
import TeachersContext from './TeachersContext';
import { getTeachers, responseToContext } from './TeachersProvider.utils';
import TeacherCreateModal, { useTeacherCreate } from './TeacherCreateModal';
import TeacherUpdateModal, { useTeacherUpdate } from './TeacherUpdateModal';
import TeacherDeleteModal, { useTeacherDelete } from './TeacherDeleteModal';

interface TeachersProviderProps extends PropsWithChildren<GetTeachersRequest> {
	onDelete?: (ids: string[], type: DataTableSelectionType) => void;
	onComplete?: (response: PageResponseTeacherTeacherErrorCode) => void;
}

export default function TeachersProvider({
	children,
	onComplete,
	...request
}: TeachersProviderProps) {
	const { data, isLoading } = useQuery({
		queryKey: ['teachers', request],
		queryFn: () => getTeachers(request, onComplete),
	});

	const {
		create,
		opened: createOpened,
		...createModalProps
	} = useTeacherCreate();

	const {
		update,
		target: updateTarget,
		opened: updateOpened,
		...updateModalProps
	} = useTeacherUpdate();

	const {
		singleDelete,
		target: deleteTarget,
		opened: deleteOpened,
		...deleteModalProps
	} = useTeacherDelete();

	const {
		bulkDelete,
		opened: bulkDeleteOpened,
		...bulkDeleteModalProps
	} = useTeacherBulkDelete();

	const contexValue = useMemo(
		() => ({
			...responseToContext(isLoading, data),
			create,
			update,
			bulkDelete,
			singleDelete,
		}),
		[bulkDelete, create, data, isLoading, singleDelete, update],
	);

	return (
		<>
			<TeachersContext.Provider value={contexValue}>
				{children}
			</TeachersContext.Provider>

			{createOpened && <TeacherCreateModal {...createModalProps} />}
			{bulkDeleteOpened && <TeacherBulkDeleteModal {...bulkDeleteModalProps} />}

			{updateOpened && !!updateTarget && (
				<TeacherUpdateModal teacher={updateTarget} {...updateModalProps} />
			)}

			{deleteOpened && !!deleteTarget && (
				<TeacherDeleteModal teacher={deleteTarget} {...deleteModalProps} />
			)}
		</>
	);
}
