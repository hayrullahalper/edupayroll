import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { notifications } from '@mantine/notifications';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { client, Teacher } from '../../../../api';

export default function useTeacherDelete() {
	const { t } = useTranslation();
	const queryClient = useQueryClient();

	const [target, setTarget] = useState<Teacher>();
	const [opened, setOpened] = useState(false);
	const [loading, setLoading] = useState(false);

	const deleteTeacher = useMutation({
		mutationFn: (request: { id: string }) =>
			client('teacher').deleteTeacher(request),
		onSuccess: () => queryClient.invalidateQueries({ queryKey: ['teachers'] }),
	});

	const close = useMemo(() => () => setOpened(false), []);

	const singleDelete = useMemo(
		() => (teacher: Teacher) => {
			setTarget(teacher);
			setOpened(true);
		},
		[],
	);

	const handleSubmit = async () => {
		try {
			if (!target) {
				return;
			}

			setLoading(true);

			const { node, errors } = await deleteTeacher.mutateAsync({
				id: target.id,
			});

			if (!node?.success || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
			}

			setOpened(false);

			notifications.show({
				message: t('teachers.deleteTeacher.success'),
				color: 'green',
			});
		} catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		} finally {
			setLoading(false);
		}
	};

	return {
		opened,
		target,
		loading,
		singleDelete,
		onClose: close,
		onSubmit: handleSubmit,
	};
}
