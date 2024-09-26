import type { QueryClient } from '@tanstack/react-query';

import type {
	PageResponseTeacherTeacherErrorCode,
	ResponseTeacherTeacherErrorCode,
} from '../../../../api';

export function updateTeachersQuery(
	queryClient: QueryClient,
	response: ResponseTeacherTeacherErrorCode,
) {
	const { data, errors } = response;

	if (!data || !!errors.length) {
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
		data: teachers.data?.map(teacher =>
			teacher.id === data.id ? data : teacher,
		),
	});
}
