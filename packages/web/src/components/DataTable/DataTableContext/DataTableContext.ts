import type { FlexProps } from '@mantine/core';
import type { ReactNode } from 'react';
import { createContext } from 'react';

export type DataTableSelectionType = 'include' | 'exclude';

export interface DataTableColumn<T> {
	id: string;
	name: string;
	width?: number;
	render: (props: { record?: T; loading?: boolean }) => ReactNode;
}

export interface DataTablePagination {
	page: number;
	total: number;
	onChange: (page: number) => void;
}

export interface DataTableProps<T> {
	height: FlexProps['h'];

	records: T[];
	recordCount: number;

	columns: DataTableColumn<T>[];
	keyExtractor: (record: T) => string;

	loading?: boolean;
	disabled?: boolean;

	selections?: string[];
	onSelectionsChange?: (selections: string[]) => void;

	selectionType?: DataTableSelectionType;
	onSelectionTypeChange?: (type: DataTableSelectionType) => void;

	toolbar?: ReactNode;
	emptyState?: ReactNode;
	toolbarProps?: FlexProps;

	skeleton?: {
		pageCount: number;
		recordCount: number;
	};

	pagination?: DataTablePagination;
}

export interface DataTableContextType<T>
	extends Omit<
		DataTableProps<T>,
		| 'height'
		| 'toolbar'
		| 'skeleton'
		| 'emptyState'
		| 'pagination'
		| 'selections'
		| 'toolbarProps'
		| 'selectionType'
		| 'onSelectionsChange'
		| 'onSelectionTypeChange'
	>,
	Required<
		Pick<DataTableProps<T>, 'skeleton' | 'selections' | 'selectionType'>
	> {
	setSelections: (selections: string[]) => void;
	setSelectionType: (type: DataTableSelectionType) => void;
}

export default createContext<DataTableContextType<any> | null>(null);
