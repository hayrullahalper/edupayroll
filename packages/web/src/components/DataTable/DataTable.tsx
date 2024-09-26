import type { DataTableProps } from './DataTableContext';
import { Paper, ScrollArea, Stack, Table } from '@mantine/core';

import { useLayoutEffect, useMemo, useRef, useState } from 'react';
import { DataTableProvider } from './DataTableContext';
import DataTableEmpty from './DataTableEmpty';
import DataTableHead from './DataTableHead';
import DataTablePagination from './DataTablePagination';
import DataTableRow from './DataTableRow';
import DataTableToolbar from './DataTableToolbar';

export default function DataTable<T>(props: DataTableProps<T>) {
	const {
		height,
		records,
		loading,
		toolbar,
		disabled,
		emptyState,
		pagination,
		toolbarProps,
		keyExtractor,
		skeleton = {
			pageCount: 3,
			recordCount: 10,
		},
	} = props;

	const toolbarRef = useRef<HTMLDivElement>(null);
	const paginationRef = useRef<HTMLDivElement>(null);

	const [innerHeight, setHeight] = useState('0px');

	const skeletonRender = useMemo(
		() =>
			!!loading && (!records.length || skeleton.recordCount > records.length),
		[loading, records.length, skeleton.recordCount],
	);

	useLayoutEffect(() => {
		const toolbarHeight = toolbarRef.current?.offsetHeight || 0;
		const paginationHeight = paginationRef.current?.offsetHeight || 0;

		setHeight(`calc(${height} - ${toolbarHeight}px - ${paginationHeight}px)`);
	}, [height]);

	return (
		<DataTableProvider
			{...props}
			loading={loading}
			skeleton={skeleton}
			disabled={disabled || loading}
		>
			<Stack h={height} gap={0}>
				<Paper
					gap={0}
					h="100%"
					bg="#fff"
					withBorder
					component={Stack}
					style={{ overflow: 'hidden' }}
				>
					<DataTableToolbar ref={toolbarRef} {...toolbarProps}>
						{toolbar}
					</DataTableToolbar>

					<ScrollArea h={innerHeight} styles={{ thumb: { zIndex: 1 } }}>
						<Table striped stickyHeader verticalSpacing="sm">
							<DataTableHead />

							<Table.Tbody>
								{skeletonRender
								&& Array.from({ length: skeleton.recordCount }).map((_, i) => (
									// eslint-disable-next-line react/no-array-index-key
									<DataTableRow key={i} index={i} />
								))}

								{!skeletonRender
								&& records.map((record, i) => (
									<DataTableRow
										index={i}
										record={record}
										key={keyExtractor(record)}
									/>
								))}
							</Table.Tbody>
						</Table>

						{!records.length && !skeletonRender && (
							<DataTableEmpty>{emptyState}</DataTableEmpty>
						)}
					</ScrollArea>
				</Paper>

				{!!pagination && (
					<DataTablePagination ref={paginationRef} {...pagination} />
				)}
			</Stack>
		</DataTableProvider>
	);
}
