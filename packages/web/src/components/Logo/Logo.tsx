import { Text, TextProps } from '@mantine/core';

import styles from './Logo.module.scss';

type LogoProps = Omit<TextProps, 'fz' | 'ff' | 'className'>;

export default function Logo(props: LogoProps) {
	return (
		<Text fz="2.1rem" ff="var(--ff-title)" className={styles.logo} {...props}>
			edupayroll
		</Text>
	);
}
