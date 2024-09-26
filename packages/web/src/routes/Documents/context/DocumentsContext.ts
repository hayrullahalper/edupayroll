import { createContext } from 'react';

export interface DocumentsContextProps {
	create: () => void;
	update: (id: string) => void;
	remove: (ids: string[]) => void;
}

export default createContext<DocumentsContextProps | null>(null);
