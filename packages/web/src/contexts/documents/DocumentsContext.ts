import type { Document, PageMeta } from '../../api';

import { createContext } from 'react';

export interface DocumentsContextType {
	meta: PageMeta;
	error: boolean;
	loading: boolean;
	documents: Document[];

	create: () => void;
	update: (document: Document) => void;
	remove: (document: Document) => void;
}

export default createContext<DocumentsContextType | null>(null);
