import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { Text, Paper, Stack, Button, Divider, Box } from '@mantine/core';

import paths from '../../paths';

export default function ResetPasswordCompleteSuccess() {
	const { t } = useTranslation();

	return (
		<Paper w={324} bg="gray.1" p="lg">
			<Stack gap="md">
				<Stack gap="0" align="center">
					<Box
						mt="-1rem"
						width={160}
						height={160}
						component="img"
						src="/assets/reset-password-complete-success.png"
						alt={t(
							'auth.resetPasswordComplete.resetPasswordCompleteSuccess.alt',
						)}
					/>

					<Stack gap="md" align="center">
						<Text ta="center" lh="1" fz="2rem" ff="var(--ff-title)">
							{t(
								'auth.resetPasswordComplete.resetPasswordCompleteSuccess.title',
							)}
						</Text>
						<Text fz="sm" fw="200" ta="center">
							{t(
								'auth.resetPasswordComplete.resetPasswordCompleteSuccess.content',
							)}
						</Text>
					</Stack>
				</Stack>

				<Divider />

				<Button
					color="indigo"
					variant="light"
					to={paths.login}
					component={Link}
				>
					{t('auth.resetPasswordComplete.resetPasswordCompleteSuccess.login')}
				</Button>
			</Stack>
		</Paper>
	);
}
