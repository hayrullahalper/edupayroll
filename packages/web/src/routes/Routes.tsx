import { Route, Routes as BaseRoutes } from 'react-router-dom';

import AppLayout from '../layouts/AppLayout';
import AuthLayout from '../layouts/AuthLayout';
import SettingsLayout from '../layouts/SettingsLayout';

import Home from './Home';
import paths from './paths';
import Login from './Login';
import School from './School';
import Logout from './Logout';
import Profile from './Profile';
import Register from './Register';
import Teachers from './Teachers';
import Documents from './Documents';
import RequireAuth from './RequireAuth';
import ResetPassword from './ResetPassword';
import UpdatePassword from './UpdatePassword';
import RequireAnonymous from './RequireAnonymous';
import RegisterComplete from './RegisterComplete';
import ResetPasswordComplete from './ResetPasswordComplete';

export default function Routes() {
	return (
		<BaseRoutes>
			<Route path={paths.home} element={<Home />} />

			<Route element={<RequireAnonymous />}>
				<Route element={<AuthLayout />}>
					<Route path={paths.login} element={<Login />} />
					<Route path={paths.register} element={<Register />} />
					<Route path={paths.resetPassword} element={<ResetPassword />} />
					<Route path={paths.registerComplete} element={<RegisterComplete />} />
					<Route
						path={paths.resetPasswordComplete}
						element={<ResetPasswordComplete />}
					/>
				</Route>
			</Route>

			<Route element={<RequireAuth />}>
				<Route path={paths.logout} element={<Logout />} />

				<Route element={<AppLayout />}>
					<Route path={paths.document} element="document" />
					<Route path={paths.documents} element={<Documents />} />

					<Route path={paths.exports} element="exports" />
					<Route path={paths.teachers} element={<Teachers />} />

					<Route element={<SettingsLayout />}>
						<Route path={paths.school} element={<School />} />
						<Route path={paths.settings} element={<Profile />} />
						<Route path={paths.changePassword} element={<UpdatePassword />} />
					</Route>
				</Route>
			</Route>
		</BaseRoutes>
	);
}
