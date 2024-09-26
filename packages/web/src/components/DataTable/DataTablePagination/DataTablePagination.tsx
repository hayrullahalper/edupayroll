import type {
	DataTablePagination as DataTablePaginationProps,
} from '../DataTableContext';
import { Button, Flex, Skeleton, Text } from '@mantine/core';
import { usePagination } from '@mantine/hooks';
import { IconChevronLeft, IconChevronRight } from '@tabler/icons-react';

import { forwardRef } from 'react';
import {
	useDataTable,
} from '../DataTableContext';

const DataTablePagination = forwardRef<
	HTMLDivElement,
	DataTablePaginationProps
>(({ page, total, onChange }, ref) => {
	const { loading, skeleton, recordCount } = useDataTable();
	const { range, active, previous, next, setPage } = usePagination({
		page,
		total,
		onChange,
	});

	return (
		<Flex justify="center" align="center" gap="xs" pt="md" ref={ref}>
			<Button
				w={32}
				h={32}
				px={0}
				variant="light"
				onClick={previous}
				disabled={active === 1 || loading}
			>
				<IconChevronLeft size={14} />
			</Button>

			{loading
				? Array.from({ length: skeleton.pageCount }, (_, index) => (
					<Skeleton key={`pagination-${index}`} w={32} h={32} />
				))
				: range.map((number, index) => {
					if (number === 'dots') {
						return (
							<Text
								w={32}
								fw={300}
								c="indigo"
								ta="center"
								mt="-.5rem"
								key={`pagination-${index}-dots`} // eslint-disable-line react/no-array-index-key
							>
								...
							</Text>
						);
					}

					return (
						<Button
							w={32}
							h={32}
							px={0}
							fw={500}
							size="xs"
							onClick={() => setPage(number)}
							key={`pagination-${index}-${number}`} // eslint-disable-line react/no-array-index-key
							variant={number === active ? 'filled' : 'light'}
						>
							{number}
						</Button>
					);
				})}

			<Button
				w={32}
				h={32}
				px={0}
				onClick={next}
				variant="light"
				disabled={active === total || loading || recordCount === 0}
			>
				<IconChevronRight size={14} />
			</Button>
		</Flex>
	);
});

DataTablePagination.displayName = 'DataTablePagination';

export default DataTablePagination;
