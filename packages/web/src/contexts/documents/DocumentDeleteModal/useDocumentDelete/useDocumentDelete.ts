import type { Document } from '../../../../api';
import { notifications } from '@mantine/notifications';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useMemo, useState } from 'react';

import { useTranslation } from 'react-i18next';
import { client } from '../../../../api';

export default function useDocumentDelete() {
	const { t } = useTranslation();
	const queryClient = useQueryClient();

	const [target, setTarget] = useState<Document>();
	const [opened, setOpened] = useState(false);
	const [loading, setLoading] = useState(false);

	const deleteDocument = useMutation({
		mutationFn: (request: { id: string }) =>
			client.document.deleteDocument(request),
		onSuccess: () => queryClient.invalidateQueries({ queryKey: ['documents'] }),
	});

	const close = useMemo(() => () => setOpened(false), []);

	const remove = useMemo(
		() => (document: Document) => {
			setTarget(document);
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

			const { data, errors } = await deleteDocument.mutateAsync({
				id: target.id,
			});

			if (!data?.success || !!errors.length) {
				notifications.show({
					message: t('common.error.unknown'),
					color: 'red',
				});
			}

			setOpened(false);

			notifications.show({
				message: t('documents.deleteDocument.success'),
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
		target,
		remove,
		loading,
		onClose: close,
		onSubmit: handleSubmit,
	};
}
