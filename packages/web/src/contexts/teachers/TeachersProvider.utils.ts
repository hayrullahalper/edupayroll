import type {
	GetTeachersRequest,
	PageResponseTeacherTeacherErrorCode,
} from '../../api';
import {
	client,
} from '../../api';

export async function getTeachers(
	request: GetTeachersRequest,
	onComplete?: (response: PageResponseTeacherTeacherErrorCode) => void,
) {
	const response = await client.teacher.getTeachers(request);
	onComplete?.(response);
	return response;
}

export function responseToContext(
	loading: boolean,
	response?: PageResponseTeacherTeacherErrorCode,
) {
	return {
		loading,
		meta: response?.meta || { limit: 0, offset: 0, total: 0 },
		error: !!response?.errors.length,
		teachers: response?.data || [],
	};
}
