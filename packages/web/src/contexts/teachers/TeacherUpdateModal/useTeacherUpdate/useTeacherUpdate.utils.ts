import type { QueryClient } from '@tanstack/react-query';

import type {
	PageResponseTeacherTeacherErrorCode,
	ResponseTeacherTeacherErrorCode,
} from '../../../../api';

export function updateTeachersQuery(
	queryClient: QueryClient,
	data: ResponseTeacherTeacherErrorCode,
) {
	const { node, errors } = data;

	if (!node || !!errors.length) {
		return;
	}

	const result
		= queryClient.getQueriesData<PageResponseTeacherTeacherErrorCode>({
			queryKey: ['teachers'],
		});

	if (!result.length) {
		return;
	}

	const [queryKey, teachers] = result[0];

	if (!queryKey || !teachers) {
		return;
	}

	queryClient.setQueryData<PageResponseTeacherTeacherErrorCode>(queryKey, {
		...teachers,
		nodes: teachers.nodes?.map(teacher =>
			teacher.id === node.id ? node : teacher,
		),
	});
}
