import { Text, TextProps } from '@mantine/core';
import { Link } from 'react-router-dom';

import paths from '../../routes/paths';

import styles from './Logo.module.scss';

type LogoProps = Omit<TextProps, 'fz' | 'ff' | 'className'>;

export default function Logo(props: LogoProps) {
	return (
		<Text
			fz="2.1rem"
			component={Link}
			ff="var(--ff-title)"
			to={paths.documents}
			className={styles.logo}
			{...props}
		>
			edupayroll
		</Text>
	);
}
