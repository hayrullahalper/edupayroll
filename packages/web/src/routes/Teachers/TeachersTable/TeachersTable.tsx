import debounce from 'debounce';
import { ChangeEvent, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { IconSearch } from '@tabler/icons-react';
import { Button, Paper, Stack, Text, TextInput } from '@mantine/core';

import DataTable, {
	DataTableSelectionType,
} from '../../../components/DataTable';
import { GetTeachersRequest } from '../../../api';
import { useTeachers } from '../../../contexts/teachers';
import prepareTableProps from '../../../helpers/prepareTableProps';

import { columns } from './TeachersTable.columns';

interface TeachersTableProps extends GetTeachersRequest {
	selections: string[];
	onPageChange: (page: number) => void;
	selectionType: DataTableSelectionType;
	onSearchChange: (search: string) => void;
	onSelectionsChange: (selections: string[]) => void;
	onSelectionTypeChange: (selectionType: DataTableSelectionType) => void;
}

export default function TeachersTable({
	query,
	limit,
	offset,
	selections,
	onPageChange,
	selectionType,
	onSearchChange,
	onSelectionsChange,
	onSelectionTypeChange,
}: TeachersTableProps) {
	const { t } = useTranslation();
	const { teachers, loading, meta, bulkDelete } = useTeachers();

	const { page, total, recordCount } = useMemo(
		() => prepareTableProps(limit, offset, meta),
		[limit, offset, meta],
	);

	const search = useMemo(
		() => debounce((query: string) => onSearchChange(query), 300),
		[onSearchChange],
	);

	const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
		const { value } = e.currentTarget;
		search(value);
	};

	return (
		<Paper p="md" h="100%">
			<DataTable
				height="84vh"
				loading={loading}
				columns={columns}
				records={teachers}
				selections={selections}
				recordCount={recordCount}
				selectionType={selectionType}
				keyExtractor={({ id }) => id}
				onSelectionsChange={onSelectionsChange}
				toolbarProps={{ justify: 'space-between' }}
				onSelectionTypeChange={onSelectionTypeChange}
				pagination={{ page, total, onChange: onPageChange }}
				emptyState={
					<Stack align="center" gap={0}>
						<Text fw={300} fz="md" c="gray">
							{t('teachers.table.emptyState.title')}
						</Text>
						<Text fw={300} fz="xs" c="gray">
							{t('teachers.table.emptyState.description')}
						</Text>
					</Stack>
				}
				toolbar={
					<>
						<Button
							size="xs"
							color="red"
							variant="light"
							disabled={!selections.length}
							onClick={() => bulkDelete(selections, selectionType)}
						>
							{t('teachers.table.bulkDelete')}
						</Button>

						<TextInput
							size="xs"
							defaultValue={query}
							onChange={handleChange}
							placeholder={t('teachers.table.search')}
							rightSection={<IconSearch size={14} stroke={1.5} />}
						/>
					</>
				}
			/>
		</Paper>
	);
}
