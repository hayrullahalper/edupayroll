import { Flex, Paper, PaperProps, Text } from '@mantine/core';
import { PropsWithChildren } from 'react';

type HeaderProps = PaperProps & {
	title: string;
};

export default function Header({
	title,
	children,
	...otherProps
}: PropsWithChildren<HeaderProps>) {
	return (
		<Paper py="sm" px="md" {...otherProps}>
			<Flex gap="lg" justify="space-between" align="center">
				<Text fz="xl" fw="200">
					{title}
				</Text>
				<Flex justify="center" align="center">
					{children}
				</Flex>
			</Flex>
		</Paper>
	);
}
