import type {
	GetTeachersRequest,
	PageResponseTeacherTeacherErrorCode,
} from '../../api';
import type { DataTableSelectionType } from '../../components/DataTable';

import { Stack } from '@mantine/core';
import { useState } from 'react';
import { TeachersProvider } from '../../contexts/teachers';

import TeachersHeader from './TeachersHeader';
import TeachersTable from './TeachersTable';

const DEFAULT_LIMIT = 10;

export default function Teachers() {
	const [selections, setSelections] = useState<string[]>([]);
	const [selectionType, setSelectionType]
		= useState<DataTableSelectionType>('include');

	const [request, setRequest] = useState<GetTeachersRequest>({
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
	}: PageResponseTeacherTeacherErrorCode) => {
		setSelections([]);
		setSelectionType('include');

		if (!!data && !!meta && data?.length === 0 && meta?.total > 0) {
			const offset = (Math.floor(meta.total / meta.limit) - 1) * meta.limit;
			setRequest(prev => ({ ...prev, offset: offset < 0 ? 0 : offset }));
		}
	};

	return (
		<TeachersProvider {...request} onComplete={handleComplete}>
			<Stack gap="xs" py="xs" pr="xs" h="100%">
				<TeachersHeader />
				<TeachersTable
					{...request}
					selections={selections}
					selectionType={selectionType}
					onPageChange={handlePageChange}
					onSelectionsChange={setSelections}
					onSearchChange={handleSearchChange}
					onSelectionTypeChange={setSelectionType}
				/>
			</Stack>
		</TeachersProvider>
	);
}
