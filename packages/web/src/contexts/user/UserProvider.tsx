import { PropsWithChildren, useMemo } from 'react';

import UserContext from './UserContext';

export default function UserProvider({ children }: PropsWithChildren<{}>) {
	const contextValue = useMemo(
		() => ({
			user: null,
			revoke: () => {},
			authenticate: (token: string) => {},
		}),
		[],
	);

	return (
		<UserContext.Provider value={contextValue}>{children}</UserContext.Provider>
	);
}
