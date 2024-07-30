import { Table } from '@mantine/core';

import { useDataTable } from '../DataTableContext';

import DataTableRowCell from './DataTableRowCell';

interface DataTableRowProps<T> {
	index: number;
	record?: T;
}

export default function DataTableRow<T>({
	index,
	record,
}: DataTableRowProps<T>) {
	const { columns } = useDataTable();

	return (
		<Table.Tr>
			{columns.map((column, ci) => (
				<DataTableRowCell
					index={ci}
					record={record}
					column={column}
					key={`row-${index}-cell-${ci}`}
				/>
			))}
		</Table.Tr>
	);
}
