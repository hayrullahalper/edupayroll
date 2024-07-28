import { useTranslation } from 'react-i18next';
import { Button, Divider, Flex, Modal, Stack, Text } from '@mantine/core';

interface TeacherBulkDeleteModalProps {
	loading: boolean;
	onClose: () => void;
	onSubmit: () => Promise<void>;
}

export default function TeacherBulkDeleteModal({
	loading,
	onClose,
	onSubmit,
}: TeacherBulkDeleteModalProps) {
	const { t } = useTranslation();

	return (
		<Modal
			opened
			centered
			size="sm"
			onClose={onClose}
			title={t('teachers.bulkDeleteTeacher.title')}
		>
			<Stack gap="sm" pt="sm">
				<Text fz="sm">{t('teachers.bulkDeleteTeacher.message')}</Text>

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
