import type { PaperProps } from '@mantine/core';
import type { PropsWithChildren } from 'react';
import { Flex, Paper, Text } from '@mantine/core';

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
				<Text fz="xl" fw="300">
					{title}
				</Text>
				<Flex justify="center" align="center">
					{children}
				</Flex>
			</Flex>
		</Paper>
	);
}
