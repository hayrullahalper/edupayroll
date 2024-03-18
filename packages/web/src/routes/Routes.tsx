import Div100vh from 'react-div-100vh';
import { Navigate, Route, Routes as BaseRoutes } from 'react-router-dom';

import AppLayout from '../layouts/AppLayout';
import AuthLayout from '../layouts/AuthLayout';

import paths from './paths';
import Login from './Login';
import Logout from './Logout';
import Register from './Register';
import Documents from './Documents';
import RequireAuth from './RequireAuth';
import RequireAnonymous from './RequireAnonymous';

export default function Routes() {
	return (
		<Div100vh>
			<BaseRoutes>
				<Route element={<RequireAuth />}>
					<Route path="/" element={<Navigate replace to={paths.documents} />} />

					<Route element={<RequireAnonymous />}>
						<Route element={<AuthLayout />}>
							<Route path={paths.login} element={<Login />} />
							<Route path={paths.register} element={<Register />} />
						</Route>

						<Route path={paths.forgotPassword} element="forgotPassword" />
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
				</Route>
			</BaseRoutes>
		</Div100vh>
	);
}
