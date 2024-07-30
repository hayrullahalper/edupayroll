import { useContext } from 'react';

import DataTableContext from '../DataTableContext';

export default function useDataTable() {
	const context = useContext(DataTableContext);

	if (!context) {
		throw new Error('useDataTable must be used within a DataTableProvider');
	}

	return context;
}
