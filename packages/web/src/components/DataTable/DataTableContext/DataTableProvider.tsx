import type { PropsWithChildren } from 'react';
import type {
	DataTableProps,
	DataTableSelectionType,
} from './DataTableContext';
import { useUncontrolled } from '@mantine/hooks';

import { useMemo } from 'react';
import DataTableContext from './DataTableContext';

interface DataTableProviderProps<T>
	extends PropsWithChildren<
		Omit<
			DataTableProps<T>,
				'skeleton' | 'selectable' | 'toolbar' | 'emptyState' | 'pagination'
		>
	>,
	Required<Pick<DataTableProps<T>, 'skeleton' | 'selectable'>> {}

export default function DataTableProvider<T>({
	records,
	columns,
	recordCount,
	keyExtractor,

	loading,
	skeleton,
	children,
	disabled,

	selectable,
	selections: controlledSelections,
	selectionType: controlledSelectionType,
	onSelectionsChange,
	onSelectionTypeChange,
}: DataTableProviderProps<T>) {
	const [selections, setSelections] = useUncontrolled<string[]>({
		defaultValue: [],
		value: controlledSelections,
		onChange: onSelectionsChange,
	});

	const [selectionType, setSelectionType]
		= useUncontrolled<DataTableSelectionType>({
			defaultValue: 'include',
			value: controlledSelectionType,
			onChange: onSelectionTypeChange,
		});

	const contextValue = useMemo(
		() => ({
			records,
			columns,
			loading,
			disabled,
			skeleton,
			selectable,
			selections,
			recordCount,
			keyExtractor,
			selectionType,
			setSelections,
			setSelectionType,
		}),
		[
			records,
			columns,
			loading,
			disabled,
			skeleton,
			selectable,
			selections,
			recordCount,
			keyExtractor,
			selectionType,
			setSelections,
			setSelectionType,
		],
	);

	return (
		<DataTableContext.Provider value={contextValue}>
			{children}
		</DataTableContext.Provider>
	);
}
