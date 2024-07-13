import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Trans, useTranslation } from 'react-i18next';
import { Button, Divider, Flex, Paper, Stack, Text } from '@mantine/core';

interface ResetPasswordSuccessProps {
	onRequestReset: () => void;
	onRequestResend: () => void;
}

export default function ResetPasswordSuccess({
	onRequestReset,
	onRequestResend,
}: ResetPasswordSuccessProps) {
	const { t } = useTranslation();
	const [resend, setResend] = useState(false);

	return (
		<Stack gap="0.25rem">
			<Paper w={324} bg="gray.1" p="lg">
				<Stack gap="md">
					<Stack gap="0" align="center">
						<img
							width={160}
							height={160}
							src="/assets/email-sent.png"
							alt={t('auth.resetPassword.resetPasswordSuccess.alt')}
						/>

						<Stack gap="md" align="center">
							<Text ta="center" lh="1.25" fz="1.75rem" ff="var(--ff-title)">
								{t('auth.resetPassword.resetPasswordSuccess.title')}
							</Text>
							<Text fz="sm" fw="200" ta="center">
								{t('auth.resetPassword.resetPasswordSuccess.content')}
							</Text>
						</Stack>
					</Stack>

					<Divider />

					<Flex gap="md">
						<Button
							size="sm"
							variant="white"
							target="_blank"
							component={Link}
							to="https://mail.google.com"
							leftSection={
								<img alt="Gmail" height={12} src="/assets/brands/gmail.svg" />
							}
						>
							{t('auth.resetPassword.resetPasswordSuccess.openGmail')}
						</Button>
						<Button
							size="sm"
							variant="white"
							target="_blank"
							component={Link}
							to="https://outlook.live.com"
							leftSection={
								<img
									height={14}
									alt="Outlook"
									src="/assets/brands/outlook.svg"
								/>
							}
						>
							{t('auth.resetPassword.resetPasswordSuccess.openOutlook')}
						</Button>
					</Flex>
				</Stack>
			</Paper>

			<Stack gap={2} w={324} align="center">
				<Text key="0" fz="xs" fw="200" ta="center">
					{t('auth.resetPassword.resetPasswordSuccess.linkNotReceived')}
				</Text>
				<Text key="1" fz="xs" fw="200" ta="center">
					<Trans
						i18nKey="auth.resetPassword.resetPasswordSuccess.checkSpam"
						components={[
							<Text
								inherit
								key="0"
								fz="xs"
								fw="500"
								component="span"
								c={resend ? 'gray' : 'indigo'}
								style={{ cursor: resend ? 'not-allowed' : 'pointer' }}
								onClick={() => {
									if (!resend) {
										onRequestResend();
										setResend(true);
									}
								}}
							/>,
							<Text
								inherit
								key="1"
								fz="xs"
								fw="500"
								c="indigo"
								component="span"
								onClick={onRequestReset}
								style={{ cursor: 'pointer' }}
							/>,
						]}
					/>
				</Text>
			</Stack>
		</Stack>
	);
}
