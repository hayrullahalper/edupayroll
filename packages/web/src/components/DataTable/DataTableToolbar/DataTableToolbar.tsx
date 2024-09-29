import type { FlexProps } from '@mantine/core';
import type { PropsWithChildren } from 'react';
import { Divider, Flex, Menu, Stack } from '@mantine/core';
import { useDisclosure } from '@mantine/hooks';
import { forwardRef, useMemo } from 'react';

import { useDataTable } from '../DataTableContext';

import SelectMenuDropdown from './SelectMenuDropdown';
import SelectMenuTarget from './SelectMenuTarget';

interface DataTableToolbarProps
	extends PropsWithChildren<any>,
	Omit<FlexProps, 'children'> {}

export default forwardRef<HTMLDivElement, DataTableToolbarProps>(
	({ children, ...otherProps }, ref) => {
		const [opened, { open, close }] = useDisclosure();
		const { selectable, selections, selectionType, records, keyExtractor } = useDataTable();

		const checked = useMemo(
			() =>
				selectionType === 'include'
					? records.every(record =>
						selections.includes(keyExtractor(record)),
					) && !!selections.length
					: !records.some(record =>
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
					{selectable && (
						<>
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
						</>
					)}

					<Flex gap="xs" flex={1} {...otherProps}>
						{children}
					</Flex>
				</Flex>
				<Divider />
			</Stack>
		);
	},
);
