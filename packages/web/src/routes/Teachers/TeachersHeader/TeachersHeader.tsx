import { Button } from '@mantine/core';
import { useTranslation } from 'react-i18next';

import Header from '../../../components/Header';
import { useTeachers } from '../../../contexts/teachers';

export default function TeachersHeader() {
	const { t } = useTranslation();
	const { create } = useTeachers();

	return (
		<Header title={t('layout.teachers.title')}>
			<Button size="xs" color="indigo" onClick={create}>
				{t('layout.teachers.new')}
			</Button>
		</Header>
	);
}
