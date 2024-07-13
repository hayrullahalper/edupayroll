import { Navigate, Outlet } from 'react-router-dom';

import paths from '../paths';
import { useUser } from '../../contexts/user';

export default function RequireAnonymous() {
	const user = useUser<'optional'>();

	if (!!user) {
		return <Navigate replace to={paths.documents} />;
	}

	return <Outlet />;
}
