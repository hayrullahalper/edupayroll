import { Menu } from '@mantine/core';
import {
	IconChecklist,
	IconFileX,
	IconListCheck,
	IconListDetails,
} from '@tabler/icons-react';
import { useTranslation } from 'react-i18next';

import { useDataTable } from '../../DataTableContext';

export default function SelectMenuDropdown() {
	const { t } = useTranslation();
	const {
		records,
		selections,
		recordCount,
		keyExtractor,
		setSelections,
		selectionType,
		setSelectionType,
	} = useDataTable();

	const selectAllDisabled
		= (selectionType === 'exclude'
			? !selections.length
			: recordCount === selections.length) || !recordCount;

	const selectPageDisabled
		= (selectionType === 'include'
			? records.every(record => selections.includes(keyExtractor(record)))
			: records.every(
				record => !selections.includes(keyExtractor(record)),
			)) || !records.length;

	const clearPageDisabled
		= selectionType === 'include'
			? !records.some(record => selections.includes(keyExtractor(record)))
			|| !selections.length
			: records.every(record => selections.includes(keyExtractor(record)));

	const clearAllSelectionsDisabled
		= selectionType === 'include' ? !selections.length : false;

	const handleSelectPage = () => {
		if (selectionType === 'include') {
			setSelections(
				Array.from(
					new Set([
						...selections,
						...records.map(record => keyExtractor(record)),
					]),
				),
			);
			return;
		}

		setSelections(
			selections.filter(
				key => !records.some(record => keyExtractor(record) === key),
			),
		);
	};

	const handleSelectAll = () => {
		setSelections([]);
		setSelectionType('exclude');
	};

	const handleClearAll = () => {
		setSelections([]);
		setSelectionType('include');
	};

	const handleClearPage = () => {
		if (selectionType === 'include') {
			setSelections(
				selections.filter(
					key => !records.some(record => keyExtractor(record) === key),
				),
			);
			return;
		}

		const changed = Array.from(
			new Set([
				...selections,
				...records.map(record => keyExtractor(record)),
			]),
		);

		setSelections(changed);

		if (changed.length === recordCount) {
			setSelections([]);
			setSelectionType('include');
		}
	};

	return (
		<Menu.Dropdown>
			<Menu.Item
				onClick={handleSelectPage}
				disabled={selectPageDisabled}
				leftSection={<IconChecklist size={16} />}
			>
				{t('components.datatable.menu.selectPage')}
			</Menu.Item>
			<Menu.Item
				onClick={handleSelectAll}
				disabled={selectAllDisabled}
				leftSection={<IconListCheck size={16} />}
			>
				{t('components.datatable.menu.selectAll')}
			</Menu.Item>

			<Menu.Divider />

			<Menu.Item
				c="red"
				onClick={handleClearPage}
				disabled={clearPageDisabled}
				leftSection={<IconFileX size={16} />}
			>
				{t('components.datatable.menu.clearPage')}
			</Menu.Item>
			<Menu.Item
				c="red"
				onClick={handleClearAll}
				disabled={clearAllSelectionsDisabled}
				leftSection={<IconListDetails size={16} />}
			>
				{t('components.datatable.menu.clearAll')}
			</Menu.Item>
		</Menu.Dropdown>
	);
}
