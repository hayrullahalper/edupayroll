import type { PageMeta, Teacher } from '../../api';

import type { DataTableSelectionType } from '../../components/DataTable';
import { createContext } from 'react';

export interface TeachersContextType {
	meta: PageMeta;
	error: boolean;
	loading: boolean;
	teachers: Teacher[];

	create: () => void;
	update: (teacher: Teacher) => void;
	bulkDelete: (ids: string[], type: DataTableSelectionType) => void;
	singleDelete: (teacher: Teacher) => void;
}

export default createContext<TeachersContextType | null>(null);
