import type { Document } from '../../../api';

import type { DataTableColumn } from '../../../components/DataTable';
import { Badge, Flex, Skeleton, Text } from '@mantine/core';
import { format, parse } from 'date-fns';
import { tr } from 'date-fns/locale';
import { i18n } from '../../../locales';
import DocumentsTableActions from './DocumentsTableActions';

export const columns: DataTableColumn<Document>[] = [
	{
		id: 'name',
		name: i18n.t('documents.table.columns.title'),
		render: ({ record, loading }) => {
			if (!record || loading) {
				return <Skeleton h={22} w={138} />;
			}

			return <Text fz="sm">{record.name}</Text>;
		},
	},
	{
		id: 'time',
		name: i18n.t('documents.table.columns.time'),
		render: ({ record, loading }) => {
			if (!record || loading) {
				return <Skeleton h={22} w={136} />;
			}

			const date = parse(record.time, 'yyyy-MM', new Date());

			return (
				<Flex gap=".25rem" align="center">
					<Badge>
						{format(date, 'MMMM', { locale: tr })}
					</Badge>
					<Badge variant="outline">
						{format(date, 'yyyy', { locale: tr })}
					</Badge>
				</Flex>
			);
		},
	},
	{
		id: 'created-at',
		name: i18n.t('documents.table.columns.createdAt'),
		render: ({ record, loading }) => {
			if (!record || loading) {
				return <Skeleton h={22} w={116} />;
			}

			return format(new Date(record.createdAt), 'dd.MM.yyyy HH:mm');
		},
	},
	{
		id: 'updated-at',
		name: i18n.t('documents.table.columns.updatedAt'),
		render: ({ record, loading }) => {
			if (!record || loading) {
				return <Skeleton h={22} w={116} />;
			}

			return format(new Date(record.updatedAt), 'dd.MM.yyyy HH:mm');
		},
	},
	{
		id: 'actions',
		name: i18n.t('documents.table.columns.actions'),
		width: 324,
		render: ({ record, loading }) => {
			if (!record || loading) {
				return (
					<Flex gap="sm" align="center">
						<Skeleton h={30} w={99} />
						<Skeleton h={30} w={30} />
						<Skeleton h={30} w={30} />
					</Flex>
				);
			}

			return <DocumentsTableActions document={record} />;
		},
	},
];
