import { Table, useMantineTheme } from '@mantine/core';
import { useTranslation } from 'react-i18next';

import { useDataTable } from '../DataTableContext';

import styles from './DataTableHead.module.scss';

export default function DataTableHead() {
	const theme = useMantineTheme();
	const { i18n } = useTranslation();
	const { columns } = useDataTable();

	return (
		<Table.Thead>
			<Table.Tr>
				{columns.map(({ id, name }) => (
					<Table.Th
						h={52}
						fz="xs"
						fw={500}
						key={id}
						c={theme.primaryColor}
						className={styles.cell}
					>
						{name.toLocaleUpperCase(i18n.language)}
					</Table.Th>
				))}
			</Table.Tr>
		</Table.Thead>
	);
}
