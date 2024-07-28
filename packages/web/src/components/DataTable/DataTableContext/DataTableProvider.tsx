import { useUncontrolled } from '@mantine/hooks';
import { PropsWithChildren, useMemo } from 'react';

import DataTableContext, {
	DataTableProps,
	DataTableSelectionType,
} from './DataTableContext';

interface DataTableProviderProps<T>
	extends PropsWithChildren<
			Omit<
				DataTableProps<T>,
				'skeleton' | 'toolbar' | 'emptyState' | 'pagination'
			>
		>,
		Required<Pick<DataTableProps<T>, 'skeleton'>> {}

export default function DataTableProvider<T>({
	records,
	columns,
	recordCount,
	keyExtractor,

	loading,
	skeleton,
	children,
	disabled,

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

	const [selectionType, setSelectionType] =
		useUncontrolled<DataTableSelectionType>({
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
