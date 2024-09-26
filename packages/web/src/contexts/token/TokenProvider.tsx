import type { PropsWithChildren } from 'react';
import Cookies from 'js-cookie';
import { useMemo, useState } from 'react';

import TokenContext from './TokenContext';

const ACCESS_TOKEN_KEY = 'access_token';

export default function TokenProvider({ children }: PropsWithChildren<any>) {
	const [accessToken, setAccessToken] = useState<string | null>(
		Cookies.get(ACCESS_TOKEN_KEY) ?? null,
	);

	const contextValue = useMemo(
		() => ({
			token: accessToken,
			setToken: (token: string, remember?: boolean) => {
				setAccessToken(token);
				Cookies.set(ACCESS_TOKEN_KEY, token, {
					expires: remember ? 7 : undefined,
				});
			},
			removeToken: () => {
				setAccessToken(null);
				Cookies.remove(ACCESS_TOKEN_KEY);
			},
		}),
		[accessToken],
	);

	return (
		<TokenContext.Provider value={contextValue}>
			{children}
		</TokenContext.Provider>
	);
}
