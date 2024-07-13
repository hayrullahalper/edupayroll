import { Route, Routes as BaseRoutes } from 'react-router-dom';

import AppLayout from '../layouts/AppLayout';
import AuthLayout from '../layouts/AuthLayout';

import Home from './Home';
import paths from './paths';
import Login from './Login';
import Logout from './Logout';
import Register from './Register';
import Documents from './Documents';
import RequireAuth from './RequireAuth';
import RequireAnonymous from './RequireAnonymous';
import RegisterComplete from './RegisterComplete';

export default function Routes() {
	return (
		<BaseRoutes>
			<Route path={paths.home} element={<Home />} />

			<Route element={<RequireAnonymous />}>
				<Route element={<AuthLayout />}>
					<Route path={paths.login} element={<Login />} />
					<Route path={paths.register} element={<Register />} />
					<Route path={paths.registerComplete} element={<RegisterComplete />} />
				</Route>

				<Route path={paths.resetPassword} element="forgotPassword" />
			</Route>

			<Route element={<RequireAuth />}>
				<Route path={paths.logout} element={<Logout />} />

				<Route element={<AppLayout />}>
					<Route path={paths.document} element="document" />
					<Route path={paths.documents} element={<Documents />} />

					<Route path={paths.teacher} element="teacher" />
					<Route path={paths.teachers} element="teachers" />

					<Route path={paths.exports} element="exports" />

					<Route path={paths.school} element="school" />
					<Route path={paths.profile} element="profile" />
					<Route path={paths.settings} element="settings" />
					<Route path={paths.changePassword} element="changePassword" />
				</Route>
			</Route>
		</BaseRoutes>
	);
}
