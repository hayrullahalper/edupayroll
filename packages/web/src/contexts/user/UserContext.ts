import { createContext } from 'react';

export type UserContextProps = {
	user: string | null;
	revoke: () => void;
	authenticate: (token: string) => void;
};

export default createContext<UserContextProps | null>(null);
