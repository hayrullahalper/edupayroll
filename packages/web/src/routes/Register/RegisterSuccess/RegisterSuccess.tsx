import { Button, Divider, Flex, Paper, Stack, Text } from '@mantine/core';
import { useState } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';

interface RegisterSuccessProps {
	onRequestReset: () => void;
	onRequestResend: () => void;
}

export default function RegisterSuccess({
	onRequestReset,
	onRequestResend,
}: RegisterSuccessProps) {
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
							alt={t('auth.register.registerSuccess.alt')}
						/>

						<Stack gap="md" align="center">
							<Text ta="center" lh="1" fz="2rem" ff="var(--ff-title)">
								{t('auth.register.registerSuccess.title')}
							</Text>
							<Stack gap="xs">
								<Trans
									i18nKey="auth.register.registerSuccess.content"
									components={[
										<Text key="0" fz="sm" fw="200" ta="center" />,
										<Text key="1" fz="sm" fw="200" ta="center" />,
									]}
								/>
							</Stack>
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
							{t('auth.register.registerSuccess.openGmail')}
						</Button>
						<Button
							size="sm"
							variant="white"
							target="_blank"
							component={Link}
							to="https://outlook.live.com"
							leftSection={(
								<img
									height={14}
									alt="Outlook"
									src="/assets/brands/outlook.svg"
								/>
							)}
						>
							{t('auth.register.registerSuccess.openOutlook')}
						</Button>
					</Flex>
				</Stack>
			</Paper>

			<Stack gap={2} w={324} align="center">
				<Text key="0" fz="xs" fw="200" ta="center">
					{t('auth.register.registerSuccess.linkNotReceived')}
				</Text>
				<Text key="1" fz="xs" fw="200" ta="center">
					<Trans
						i18nKey="auth.register.registerSuccess.checkSpam"
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
