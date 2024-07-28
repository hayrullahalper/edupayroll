import { Flex, Skeleton, Text } from '@mantine/core';

import { Teacher } from '../../../api';
import { i18n } from '../../../locales';
import { DataTableColumn } from '../../../components/DataTable';

import TeachersTableActions from './TeachersTableActions';

export const columns: DataTableColumn<Teacher>[] = [
	{
		id: 'name',
		name: i18n.t('teachers.table.columns.name'),
		render: ({ record, loading }) => {
			if (!record || loading) {
				return <Skeleton h={22} w={138} />;
			}

			return <Text fz="sm">{record?.name}</Text>;
		},
	},
	{
		id: 'id-number',
		name: i18n.t('teachers.table.columns.idNumber'),
		render: ({ record, loading }) => {
			if (!record || loading) {
				return <Skeleton h={22} w={116} />;
			}

			return record?.idNumber;
		},
	},
	{
		id: 'branch',
		name: i18n.t('teachers.table.columns.branch'),
		render: ({ record, loading }) => {
			if (!record || loading) {
				return <Skeleton h={22} w={136} />;
			}

			return record?.branch;
		},
	},
	{
		id: 'description',
		name: i18n.t('teachers.table.columns.description'),
		render: ({ record, loading }) => {
			if (!record || loading) {
				return <Skeleton h={22} w={136} />;
			}

			return record?.description;
		},
	},
	{
		id: 'actions',
		name: i18n.t('teachers.table.columns.actions'),
		width: 192,
		render: ({ record, loading }) => {
			if (!record || loading) {
				return (
					<Flex gap="sm" align="center">
						<Skeleton h={30} w={99} />
						<Skeleton h={30} w={30} />
					</Flex>
				);
			}

			return <TeachersTableActions teacher={record} />;
		},
	},
];
