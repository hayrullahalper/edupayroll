import type { Document } from '../../../api';
import { Button, Divider, Flex, Modal, Stack, Text } from '@mantine/core';

import { Trans, useTranslation } from 'react-i18next';

interface DocumentDeleteModalProps {
	loading: boolean;
	onClose: () => void;
	document: Document;
	onSubmit: () => Promise<void>;
}

export default function DocumentDeleteModal({
	loading,
	onClose,
	document,
	onSubmit,
}: DocumentDeleteModalProps) {
	const { t } = useTranslation();

	return (
		<Modal
			opened
			centered
			size="sm"
			onClose={onClose}
			title={t('documents.deleteDocument.title')}
		>
			<Stack gap="sm" pt="sm">
				<Stack gap="xs">
					<Text fz="sm">
						<Trans
							values={{ name: document.name }}
							i18nKey="documents.deleteDocument.message"
							components={[
								<Text inherit fw={600} key="name" component="span" />,
							]}
						/>
					</Text>

					<Text fz="xs">{t('documents.deleteDocument.warning')}</Text>
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
