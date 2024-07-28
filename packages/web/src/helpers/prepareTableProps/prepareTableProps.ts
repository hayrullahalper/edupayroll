import { PageMeta } from '../../api';

export default function prepareTableProps(
	limit: number,
	offset: number,
	meta?: PageMeta,
) {
	return {
		page: Math.floor(offset / limit) + 1,
		total: Math.ceil((meta?.total || 0) / limit),
		recordCount: meta?.total || 0,
	};
}
