import { LoadingOverlay } from '@mantine/core';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { useToken } from '../../contexts/token';
import paths from '../paths';

export default function Logout() {
	const navigate = useNavigate();
	const { removeToken } = useToken();

	useEffect(() => {
		setTimeout(() => {
			removeToken();
			navigate(paths.home, { replace: true });
		}, 1000);
	}, [navigate, removeToken]);

	return <LoadingOverlay />;
}
