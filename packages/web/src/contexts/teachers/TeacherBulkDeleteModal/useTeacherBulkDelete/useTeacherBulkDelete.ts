import type { TeacherDeleteInput } from '../../../../api';
import type { DataTableSelectionType } from '../../../../components/DataTable';
import { notifications } from '@mantine/notifications';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { client } from '../../../../api';
import stringToSelectionType from '../../../../helpers/stringToSelectionType';

export default function useTeacherBulkDelete() {
	const { t } = useTranslation();
	const queryClient = useQueryClient();

	const [opened, setOpened] = useState(false);
	const [loading, setLoading] = useState(false);

	const [targetIds, setTargetIds] = useState<string[]>();
	const [targetType, setTargetType] = useState<DataTableSelectionType>();

	const deleteTeachers = useMutation({
		mutationFn: (teacherDeleteInput: TeacherDeleteInput) =>
			client('teacher').deleteTeachers({ teacherDeleteInput }),
		onSuccess: () => queryClient.invalidateQueries({ queryKey: ['teachers'] }),
	});

	const close = useMemo(() => () => setOpened(false), []);

	const bulkDelete = useMemo(
		() => (ids: string[], type: DataTableSelectionType) => {
			setTargetIds(ids);
			setTargetType(type);

			setOpened(true);
		},
		[],
	);

	const handleSubmit = async () => {
		try {
			if (!targetIds || !targetType) {
				return;
			}

			setLoading(true);

			const { node, errors } = await deleteTeachers.mutateAsync({
				ids: targetIds,
				type: stringToSelectionType(targetType),
			});

			if (!node?.success || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
			}

			setOpened(false);

			notifications.show({
				message: t('teachers.bulkDeleteTeacher.success'),
				color: 'green',
			});
		}
		catch (e) {
			notifications.show({
				message: t('common.error.unknown'),
				color: 'red',
			});
		}
		finally {
			setLoading(false);
		}
	};

	return {
		opened,
		loading,
		bulkDelete,
		onClose: close,
		onSubmit: handleSubmit,
	};
}
