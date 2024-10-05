import { Button } from '@mantine/core';
import { useTranslation } from 'react-i18next';

import Header from '../../../components/Header';

export default function DocumentsHeader() {
	const { t } = useTranslation();

	return (
		<Header title={t('layout.documents.title')}>
			<Button size="xs" color="indigo">
				{t('layout.documents.new')}
			</Button>
		</Header>
	);
}
