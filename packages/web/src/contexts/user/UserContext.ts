import type { User } from '../../api';

import { createContext } from 'react';

export interface UserContextType {
	user: User | null;
}

export default createContext<UserContextType | null>(null);
