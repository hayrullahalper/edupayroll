import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { LoadingOverlay } from '@mantine/core';

import paths from '../paths';
import { useToken } from '../../contexts/token';

export default function Logout() {
	const navigate = useNavigate();
	const { removeToken } = useToken();

	useEffect(() => {
		removeToken();
		navigate(paths.home, { replace: true });
	}, [navigate, removeToken]);

	return <LoadingOverlay />;
}
