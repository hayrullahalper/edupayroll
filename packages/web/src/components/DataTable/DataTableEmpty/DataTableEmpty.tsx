import { PropsWithChildren } from 'react';
import { useTranslation } from 'react-i18next';
import { IconInboxOff } from '@tabler/icons-react';
import { Stack, Text, useMantineTheme } from '@mantine/core';

export default function DataTableEmpty({ children }: PropsWithChildren<{}>) {
	const { t } = useTranslation();
	const theme = useMantineTheme();

	return (
		<Stack justify="center" align="center" pt="6rem" pb="2rem">
			<IconInboxOff size="4rem" stroke={1} color={theme.colors.indigo[2]} />

			{children || (
				<Text ta="center" fw={300} fz="sm">
					{t('components.datatable.emptyState')}
				</Text>
			)}
		</Stack>
	);
}
