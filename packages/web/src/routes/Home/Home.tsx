import { Flex } from '@mantine/core';
import { Link } from 'react-router-dom';

import paths from '../paths';

export default function Home() {
	return (
		<div>
			<h1>Home</h1>

			<Flex gap="md">
				<Link to={paths.login}>Login</Link>
				<Link to={paths.register}>Register</Link>
			</Flex>
		</div>
	);
}
