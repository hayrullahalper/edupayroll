import type { Document } from '../../../../api';
import { ActionIcon, Button, Flex, Tooltip } from '@mantine/core';
import { IconEdit, IconNotebook, IconTableExport, IconTrash } from '@tabler/icons-react';

import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { generatePath, Link } from 'react-router-dom';
import { useDocuments } from '../../../../contexts/documents';
import { DocumentMode, DocumentSearchParam } from '../../../Document';
import paths from '../../../paths';

interface DocumentsTableActionsProps {
	document: Document;
}

export default function DocumentsTableActions({
	document,
}: DocumentsTableActionsProps) {
	const { t } = useTranslation();
	const { remove } = useDocuments();

	const documentPath = useMemo(() =>
		generatePath(paths.document, { documentId: document.id }), [document.id]);

	const documentShowPath = useMemo(() =>
		`${documentPath}?${DocumentSearchParam.Mode}=${DocumentMode.Show}`, [documentPath]);

	const documentEditPath = useMemo(() =>
		`${documentPath}?${DocumentSearchParam.Mode}=${DocumentMode.Edit}`, [documentPath]);

	return (
		<Flex gap="sm" align="center">
			<Button
				size="xs"
				variant="light"
				component={Link}
				to={documentShowPath}
				leftSection={(
					<Flex justify="center" align="center" mr="-.25rem">
						<IconNotebook size={16} />
					</Flex>
				)}
			>
				{t('documents.table.show')}
			</Button>
			<Button
				size="xs"
				variant="light"
				component={Link}
				to={documentEditPath}
				leftSection={<IconEdit size={16} />}
			>
				{t('documents.table.edit')}
			</Button>

			<Tooltip
				position="top"
				openDelay={300}
				label={t('documents.table.tooltips.export')}
			>
				<ActionIcon
					w={30}
					h={30}
					color="indigo"
					variant="light"
				>
					<IconTableExport size={16} />
				</ActionIcon>
			</Tooltip>

			<Tooltip
				position="top"
				openDelay={300}
				label={t('documents.table.tooltips.delete')}
			>
				<ActionIcon
					w={30}
					h={30}
					color="red"
					variant="light"
					onClick={() => remove(document)}
				>
					<IconTrash size={16} />
				</ActionIcon>
			</Tooltip>
		</Flex>
	);
}
