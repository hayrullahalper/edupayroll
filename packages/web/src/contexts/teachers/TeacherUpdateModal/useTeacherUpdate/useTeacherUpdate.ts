import { useCallback, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { notifications } from '@mantine/notifications';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { TeacherUpdateFormInput } from '../TeacherUpdateModal.utils';
import { client, Teacher, TeacherUpdateInput } from '../../../../api';

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
			client('teacher').updateTeacher({
				id,
				teacherUpdateInput,
			}),
		onSuccess: (data) => updateTeachersQuery(queryClient, data),
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

			const { node, errors } = await updateTeacher.mutateAsync({
				id: target.id,
				...input,
			});

			if (!node || !!errors.length) {
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
		} catch (e) {
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
