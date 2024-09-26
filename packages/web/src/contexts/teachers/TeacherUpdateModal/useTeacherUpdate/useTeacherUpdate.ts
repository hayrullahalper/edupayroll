import type { Teacher, TeacherUpdateInput } from '../../../../api';
import type { TeacherUpdateFormInput } from '../TeacherUpdateModal.utils';
import { notifications } from '@mantine/notifications';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useCallback, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { client } from '../../../../api';

import { updateTeachersQuery } from './useTeacherUpdate.utils';

export default function useTeacherUpdate() {
	const { t } = useTranslation();
	const queryClient = useQueryClient();

	const [target, setTarget] = useState<Teacher>();
	const [opened, setOpened] = useState(false);

	const updateTeacher = useMutation({
		mutationFn: ({
			id,
			...teacherUpdateInput
		}: TeacherUpdateInput & { id: string }) =>
			client.teacher.updateTeacher({
				id,
				teacherUpdateInput,
			}),
		onSuccess: response => updateTeachersQuery(queryClient, response),
	});

	const close = useCallback(() => setOpened(false), []);

	const update = useCallback((teacher: Teacher) => {
		setTarget(teacher);
		setOpened(true);
	}, []);

	const handleSubmit = async (input: TeacherUpdateFormInput) => {
		try {
			if (!target) {
				return;
			}

			const { data, errors } = await updateTeacher.mutateAsync({
				id: target.id,
				...input,
			});

			if (!data || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			setOpened(false);

			notifications.show({
				message: t('teachers.updateTeacher.success'),
				color: 'green',
			});
		}
		catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		}
	};

	return {
		opened,
		update,
		target,
		onClose: close,
		onSubmit: handleSubmit,
	};
}
