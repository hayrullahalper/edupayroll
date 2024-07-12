import { createContext } from 'react';

export interface TokenContextType {
	token: string | null;

	setToken: (token: string, remember?: boolean) => void;
	removeToken: () => void;
}

export default createContext<TokenContextType | null>(null);
