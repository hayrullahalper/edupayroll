import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Text, Flex, Paper, Stack, Button, Divider } from '@mantine/core';

interface RegisterSuccessProps {
	onRequestReset: () => void;
	onRequestResend: () => void;
}

export default function RegisterSuccess({
	onRequestReset,
	onRequestResend,
}: RegisterSuccessProps) {
	const [resend, setResend] = useState(false);

	return (
		<Stack gap="0.25rem">
			<Paper w={324} bg="gray.1" p="lg">
				<Stack gap="md">
					<Stack gap="0" align="center">
						<img
							width={160}
							height={160}
							src="/assets/email-verify.png"
							alt="E-posta Adresinizi Doğrulayın"
						/>

						<Stack gap="md" align="center">
							<Text ta="center" lh="1" fz="2rem" ff="var(--ff-title)">
								E-posta Adresinizi Doğrulayın
							</Text>
							<Stack gap="xs">
								<Text fz="sm" fw="200" ta="center">
									E-posta adresinize kayıt işleminizi tamamlamanızı sağlayacak
									bir bağlantı gönderdik.
								</Text>
								<Text fz="sm" fw="200" ta="center">
									Kaydınızı tamamlamak için lütfen e-postanızı kontrol edin ve
									içindeki bağlantıya tıklayın.
								</Text>
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
							Gmail&apos;i Aç
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
							Outlook&apos;u Aç
						</Button>
					</Flex>
				</Stack>
			</Paper>

			<Stack gap={2} w={324} align="center">
				<Text fz="xs" fw="200" ta="center">
					E-posta adresinize bağlantı gelmedi mi?
				</Text>
				<Text fz="xs" fw="200" ta="center">
					Spam klasörünüzü kontrol edin,&nbsp;
					<Text
						inherit
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
					>
						tekrar gönderin
					</Text>
					&nbsp;veya&nbsp;
					<Text
						inherit
						fz="xs"
						fw="500"
						c="indigo"
						component="span"
						onClick={onRequestReset}
						style={{ cursor: 'pointer' }}
					>
						adresinizi değiştirin
					</Text>
					.
				</Text>
			</Stack>
		</Stack>
	);
}
