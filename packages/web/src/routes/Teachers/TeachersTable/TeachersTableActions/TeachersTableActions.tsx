import type { Teacher } from '../../../../api';
import { ActionIcon, Button, Flex } from '@mantine/core';
import { IconEdit, IconTrash } from '@tabler/icons-react';

import { useTranslation } from 'react-i18next';
import { useTeachers } from '../../../../contexts/teachers';

interface TeachersTableActionsProps {
	teacher: Teacher;
}

export default function TeachersTableActions({
	teacher,
}: TeachersTableActionsProps) {
	const { t } = useTranslation();
	const { update, singleDelete } = useTeachers();

	return (
		<Flex gap="sm" align="center">
			<Button
				size="xs"
				color="teal"
				variant="light"
				onClick={() => update(teacher)}
				leftSection={<IconEdit size={16} />}
			>
				{t('teachers.table.edit')}
			</Button>

			<ActionIcon
				w={30}
				h={30}
				color="red"
				variant="light"
				onClick={() => singleDelete(teacher)}
			>
				<IconTrash size={16} />
			</ActionIcon>
		</Flex>
	);
}
