import { Button, Checkbox, Flex, Menu, useMantineTheme } from '@mantine/core';
import { IconChevronDown } from '@tabler/icons-react';
import cx from 'classnames';

import { useDataTable } from '../../DataTableContext';

import styles from './SelectMenuTarget.module.scss';

interface SelectMenuTargetProps {
	checked: boolean;
	indeterminate: boolean;
	onRequestOpen: () => void;
}

export default function SelectMenuTarget({
	checked,
	indeterminate,
	onRequestOpen,
}: SelectMenuTargetProps) {
	const theme = useMantineTheme();
	const { disabled } = useDataTable();

	return (
		<Menu.Target>
			<Button
				px={6}
				size="xs"
				variant="light"
				component="label"
				htmlFor="datatable-select-target"
				color={`${theme.primaryColor}.2`}
				className={cx(styles.button, { disabled })}
			>
				<Flex justify="center" align="center" gap={4}>
					<Checkbox
						size="xs"
						checked={checked}
						disabled={disabled}
						id="datatable-select-target"
						indeterminate={indeterminate}
						onChange={() => onRequestOpen()}
						styles={{ input: { cursor: 'pointer' } }}
						classNames={{ input: cx(styles.input, { disabled }) }}
					/>
					<IconChevronDown
						size={16}
						stroke={2}
						color={theme.colors.indigo[4]}
					/>
				</Flex>
			</Button>
		</Menu.Target>
	);
}
