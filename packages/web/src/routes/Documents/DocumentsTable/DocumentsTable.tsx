import type { ChangeEvent } from 'react';
import type { GetDocumentsRequest } from '../../../api';

import { Paper, Stack, Text, TextInput } from '@mantine/core';
import { IconSearch } from '@tabler/icons-react';
import debounce from 'debounce';

import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import DataTable from '../../../components/DataTable';
import { useDocuments } from '../../../contexts/documents';

import prepareTableProps from '../../../helpers/prepareTableProps';
import { columns } from './DocumentsTable.columns';

interface DocumentsTableProps extends GetDocumentsRequest {
	onPageChange: (page: number) => void;
	onSearchChange: (search: string) => void;
}

export default function DocumentsTable({
	name,
	limit,
	offset,
	onPageChange,
	onSearchChange,
}: DocumentsTableProps) {
	const { t } = useTranslation();
	const { documents, loading, meta } = useDocuments();

	const { page, total, recordCount } = useMemo(
		() => prepareTableProps(limit, offset, meta),
		[limit, offset, meta],
	);

	const search = useMemo(
		() => debounce((name: string) => onSearchChange(name), 300),
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
				selectable={false}
				records={documents}
				recordCount={recordCount}
				keyExtractor={({ id }) => id}
				toolbarProps={{ justify: 'flex-end' }}
				pagination={{ page, total, onChange: onPageChange }}
				emptyState={(
					<Stack align="center" gap={0}>
						<Text fw={300} fz="md" c="gray">
							{t('documents.table.emptyState.title')}
						</Text>
						<Text fw={300} fz="xs" c="gray">
							{t('documents.table.emptyState.description')}
						</Text>
					</Stack>
				)}
				toolbar={(
					<TextInput
						size="xs"
						defaultValue={name}
						onChange={handleChange}
						placeholder={t('documents.table.search')}
						rightSection={<IconSearch size={14} stroke={1.5} />}
					/>
				)}
			/>
		</Paper>
	);
}
