import cx from 'classnames';
import { useMemo } from 'react';
import { Button, Checkbox } from '@mantine/core';

import { useDataTable } from '../../DataTableContext';

import styles from './DataTableRowCheckbox.module.scss';

interface DataTableRowCheckboxProps {
	inputId: string;
	recordKey: string;
}

export default function DataTableRowCheckbox({
	inputId,
	recordKey,
}: DataTableRowCheckboxProps) {
	const {
		disabled,
		selections,
		recordCount,
		setSelections,
		selectionType,
		setSelectionType,
	} = useDataTable();

	const checked = useMemo(
		() =>
			selectionType === 'include'
				? selections.includes(recordKey)
				: !selections.includes(recordKey),
		[recordKey, selectionType, selections],
	);

	const handleChange = () => {
		if (selectionType === 'include') {
			if (checked) {
				setSelections(selections.filter((key) => key !== recordKey));
				return;
			}

			setSelections([...selections, recordKey]);
			return;
		}

		if (checked) {
			const changed = [...selections, recordKey];
			setSelections(changed);

			if (changed.length === recordCount) {
				setSelections([]);
				setSelectionType('include');
			}
			return;
		}

		setSelections(selections.filter((key) => key !== recordKey));
	};

	return (
		<Button
			ml={2}
			px={6}
			size="xs"
			variant="transparent"
			className={cx(styles.button, { disabled })}
		>
			<Checkbox
				size="xs"
				id={inputId}
				checked={checked}
				disabled={disabled}
				onChange={handleChange}
				classNames={{ input: cx(styles.input, { disabled }) }}
			/>
		</Button>
	);
}
