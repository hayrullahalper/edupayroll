import { useCallback, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { notifications } from '@mantine/notifications';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { client, TeacherCreateInput } from '../../../../api';
import { TeacherCreateFormInput } from '../TeacherCreateModal.utils';

export default function useTeacherCreate() {
	const { t } = useTranslation();
	const queryClient = useQueryClient();

	const [opened, setOpened] = useState(false);

	const createTeacher = useMutation({
		mutationFn: (teacherCreateInput: TeacherCreateInput) =>
			client('teacher').createTeacher({ teacherCreateInput }),
		onSuccess: () => queryClient.invalidateQueries({ queryKey: ['teachers'] }),
	});

	const close = useCallback(() => setOpened(false), []);
	const create = useCallback(() => setOpened(true), []);

	const handleSubmit = async (input: TeacherCreateFormInput) => {
		try {
			const { node, errors } = await createTeacher.mutateAsync(input);

			if (!node || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
				return;
			}

			setOpened(false);

			notifications.show({
				message: t('teachers.createTeacher.success'),
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
		create,
		onClose: close,
		onSubmit: handleSubmit,
	};
}
