import { Navigate, Outlet } from 'react-router-dom';

import { useUser } from '../../contexts/user';
import paths from '../paths';

export default function RequireAuth() {
	const user = useUser<'optional'>();

	if (!user) {
		return <Navigate replace to={paths.login} />;
	}

	return <Outlet />;
}
