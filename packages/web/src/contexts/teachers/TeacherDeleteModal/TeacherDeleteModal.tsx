import { Button, Divider, Flex, Modal, Stack, Text } from '@mantine/core';
import { Trans, useTranslation } from 'react-i18next';

import { Teacher } from '../../../api';

interface TeacherDeleteModalProps {
	teacher: Teacher;
	loading: boolean;
	onClose: () => void;
	onSubmit: () => Promise<void>;
}

export default function TeacherDeleteModal({
	teacher,
	loading,
	onClose,
	onSubmit,
}: TeacherDeleteModalProps) {
	const { t } = useTranslation();

	return (
		<Modal
			opened
			centered
			size="sm"
			onClose={onClose}
			title={t('teachers.deleteTeacher.title')}
		>
			<Stack gap="sm" pt="sm">
				<Stack gap="xs">
					<Text fz="sm">
						<Trans
							values={{ name: teacher.name }}
							i18nKey="teachers.deleteTeacher.message"
							components={[
								<Text inherit fw={600} key="name" component="span" />,
							]}
						/>
					</Text>

					<Text fz="xs">{t('teachers.deleteTeacher.warning')}</Text>
				</Stack>

				<Divider />

				<Flex gap="sm" justify="flex-end">
					<Button
						color="gray"
						type="button"
						variant="subtle"
						onClick={onClose}
						disabled={loading}
					>
						{t('common.form.cancel')}
					</Button>

					<Button
						color="red"
						type="submit"
						variant="light"
						loading={loading}
						onClick={onSubmit}
					>
						{t('common.form.delete')}
					</Button>
				</Flex>
			</Stack>
		</Modal>
	);
}
