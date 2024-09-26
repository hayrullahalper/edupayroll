import type { PropsWithChildren } from 'react';
import { LoadingOverlay } from '@mantine/core';
import { useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

import { client } from '../../api';
import { useToken } from '../token';

import UserContext from './UserContext';

export default function UserProvider({ children }: PropsWithChildren<any>) {
	const { token } = useToken();

	const { isLoading, data: { data: user } = {} } = useQuery({
		enabled: !!token,
		queryKey: ['user'],
		refetchOnMount: 'always',
		refetchOnWindowFocus: true,
		queryFn: () => client.user.getUser(),
	});

	const contextValue = useMemo(
		() => ({
			user: !token ? null : user || null,
		}),
		[token, user],
	);

	if (isLoading) {
		return <LoadingOverlay />;
	}

	return (
		<UserContext.Provider value={contextValue}>{children}</UserContext.Provider>
	);
}
