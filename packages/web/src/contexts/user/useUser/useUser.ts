import type { User } from '../../../api';

import { useContext } from 'react';
import UserContext from '../UserContext';

type UserFetchType = 'exact' | 'optional';

export default function useUser<
	T extends UserFetchType = 'exact',
>(): T extends 'optional' ? User | null : User {
	const context = useContext(UserContext);

	if (!context) {
		throw new Error('useUser must be used within a UserProvider');
	}

	return context.user as any;
}
