import type { DataTableColumn } from '../../DataTableContext';
import { Flex, Table } from '@mantine/core';
import cx from 'classnames';

import { useMemo } from 'react';
import { useDataTable } from '../../DataTableContext';
import DataTableRowCheckbox from '../DataTableRowCheckbox';

import styles from './DataTableRowCell.module.scss';

interface DataTableRowCellProps<T> {
	index: number;
	column: DataTableColumn<T>;
	record?: T;
}

export default function DataTableRowCell<T>({
	index,
	column,
	record,
}: DataTableRowCellProps<T>) {
	const { render, width } = column;
	const { loading, disabled, selectable, keyExtractor } = useDataTable();

	const key = useMemo(
		() => (loading ? `skeleton-${index}` : keyExtractor(record)),
		[loading, index, keyExtractor, record],
	);

	const label = useMemo(() => index === 0, [index]);
	const content = useMemo(
		() => render({ record, loading }),
		[render, record, loading],
	);

	return (
		<Table.Td w={width} miw={width} maw={width}>
			{!label
				? (
					content
				)
				: (
					<Flex
						gap={4}
						align="center"
						component="label"
						htmlFor={`checkbox-${key}`}
						className={cx(styles.label, { disabled, selectable })}
					>
						{selectable && <DataTableRowCheckbox recordKey={key} inputId={`checkbox-${key}`} />}
						{content}
					</Flex>
				)}
		</Table.Td>
	);
}
