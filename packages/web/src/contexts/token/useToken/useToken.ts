import { useContext } from 'react';

import TokenContext from '../TokenContext';

export default function useToken() {
	const context = useContext(TokenContext);

	if (!context) {
		throw new Error('useToken must be used within an TokenProvider');
	}

	return context;
}
