import { useContext } from 'react';

import TeachersContext from '../TeachersContext';

export default function useTeachers() {
	const context = useContext(TeachersContext);

	if (!context) {
		throw new Error('useTeachers must be used within a TeachersProvider');
	}

	return context;
}
