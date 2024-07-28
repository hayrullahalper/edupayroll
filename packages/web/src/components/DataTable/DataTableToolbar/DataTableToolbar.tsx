import { useDisclosure } from '@mantine/hooks';
import { forwardRef, PropsWithChildren, useMemo } from 'react';
import { Flex, Stack, Divider, FlexProps, Menu } from '@mantine/core';

import { useDataTable } from '../DataTableContext';

import SelectMenuTarget from './SelectMenuTarget';
import SelectMenuDropdown from './SelectMenuDropdown';

interface DataTableToolbarProps
	extends PropsWithChildren<{}>,
		Omit<FlexProps, 'children'> {}

const DataTableToolbar = forwardRef<HTMLDivElement, DataTableToolbarProps>(
	({ children, ...otherProps }, ref) => {
		const [opened, { open, close }] = useDisclosure();
		const { selections, selectionType, records, keyExtractor } = useDataTable();

		const checked = useMemo(
			() =>
				selectionType === 'include'
					? records.every((record) =>
							selections.includes(keyExtractor(record)),
						) && !!selections.length
					: !records.some((record) =>
							selections.includes(keyExtractor(record)),
						),
			[selectionType, records, selections, keyExtractor],
		);

		const indeterminate = useMemo(
			() => (checked ? false : !!selections.length),
			[checked, selections],
		);

		return (
			<Stack gap={0} ref={ref}>
				<Flex p="sm" gap="xs" align="center">
					<Menu
						offset={4}
						shadow="sm"
						opened={opened}
						onClose={close}
						position="bottom-start"
						transitionProps={{ duration: 0 }}
					>
						<SelectMenuTarget
							checked={checked}
							onRequestOpen={open}
							indeterminate={indeterminate}
						/>

						<SelectMenuDropdown />
					</Menu>

					<Divider orientation="vertical" />

					<Flex gap="xs" flex={1} {...otherProps}>
						{children}
					</Flex>
				</Flex>
				<Divider />
			</Stack>
		);
	},
);

DataTableToolbar.displayName = 'DataTableToolbar';

export default DataTableToolbar;
