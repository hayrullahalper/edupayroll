import { createContext } from 'react';

import { User } from '../../api';

export interface UserContextType {
	user: User | null;
}

export default createContext<UserContextType | null>(null);
